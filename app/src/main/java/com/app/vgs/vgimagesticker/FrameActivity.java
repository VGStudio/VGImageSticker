package com.app.vgs.vgimagesticker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.FloatMath;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.vgs.vgimagesticker.adapter.FrameAdapter;
import com.app.vgs.vgimagesticker.utils.Const;
import com.app.vgs.vgimagesticker.utils.FileUtils;
import com.app.vgs.vgimagesticker.utils.JsonUtils;
import com.app.vgs.vgimagesticker.utils.LogUtils;
import com.app.vgs.vgimagesticker.utils.NetworkUtils;
import com.app.vgs.vgimagesticker.vo.FrameGroup;
import com.app.vgs.vgimagesticker.vo.FrameSubGroup;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.xiaopo.flying.sticker.DrawableSticker;

import java.io.File;
import java.util.List;

public class FrameActivity extends BaseActivity {
    LinearLayout mLLFrameGroup;
    List<FrameGroup> mLstFrameGroup;
    public static String KEY_FRAME_GROUP_ID = "KEY_FRAME_GROUP_ID";

    private String mFrameGroupId = "";
    private String mFileSavedpath = null;

    GridView mGridViewFrame;
    RelativeLayout mRlHeader;
    ImageView mIvPreview;

    View mExitPopUp;
    AdView mBannerAdView;

    ImageView mIvFrame;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame);
        initView();
        initData();
        addFrameGroupIconView();
    }

    @Override
    public void setShowInterstitial() {
        mShowInterstitial = false;
    }

    @Override
    public void closeInterstitial() {
        goBackMainActionCategory();
    }

    private void initData() {
        initAds();
        mFrameGroupId = getIntent().getStringExtra(KEY_FRAME_GROUP_ID);
        mLstFrameGroup = JsonUtils.getFrameGroupFromJsonData(this, Const.FRAME_DATA_FILE_PATH);
        LogUtils.d(mLstFrameGroup.size()+"");
    }

    private void initAds() {
        if (NetworkUtils.isInternetConnected(this)) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mBannerAdView.loadAd(adRequest);
        }
    }

    private void initView() {
        mLLFrameGroup = findViewById(R.id.llFrameGroup);
        mGridViewFrame = findViewById(R.id.gridViewFrame);
        mRlHeader = findViewById(R.id.rlHeader);
        mExitPopUp = findViewById(R.id.exitPopUp);
        mBannerAdView = findViewById(R.id.bannerAdView);
        mIvPreview = findViewById(R.id.ivPreview);
        mIvFrame = findViewById(R.id.ivFrame);


        mIvPreview.setOnTouchListener(new ImageTouch());



    }


    private void goBackMainActionCategory() {
        Intent intent = new Intent();
        if (mFileSavedpath != null) {
            intent.putExtra(MainActionActivity.KEY_IMAGE_PATH_UPDATE, mFileSavedpath);
            setResult(Activity.RESULT_OK, intent);
        } else {
            setResult(Activity.RESULT_CANCELED, intent);
        }
        finish();
    }


    private void addFrameGroupIconView() {
        try {
            LayoutInflater layoutInflater = getLayoutInflater();
            FrameGroup frame = null;

            for (FrameGroup sframeGroup : mLstFrameGroup) {
                if (sframeGroup.getId().equals(mFrameGroupId)) {
                    frame = sframeGroup;
                }
            }

            mLLFrameGroup.setWeightSum(frame.getLstSubGroup().size());


            for (FrameSubGroup subGroup : frame.getLstSubGroup()) {
                final View view = layoutInflater.inflate(R.layout.layout_sub_button, mLLFrameGroup, false);
                ImageButton imgButton = view.findViewById(R.id.ibIcon);
                TextView textView = view.findViewById(R.id.tvDes);
                Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open(subGroup.getIcon()));
                imgButton.setImageBitmap(bitmap);
                imgButton.setTag(subGroup.getFolder());
                textView.setText(subGroup.getTitle());
                imgButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFrameSubGroupView(v);
                        unSelecteFrameGroupButtonState();
                        view.setBackgroundColor(getResources().getColor(R.color.button_pressed));
                    }
                });
                mLLFrameGroup.addView(view);
            }

        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }

    private void unSelecteFrameGroupButtonState() {
        try {
            for (int i = 0; i < mLLFrameGroup.getChildCount(); i++) {
                View childView = mLLFrameGroup.getChildAt(i);
                childView.setBackgroundColor(getResources().getColor(R.color.button_default));
            }
        } catch (Exception exp) {
            LogUtils.e(exp);
        }
    }


    public void openFrameSubGroupView(View view) {
        String folderPath = view.getTag().toString();
        String dataPath = folderPath + "/data.json";
        showFrames(dataPath);
        LogUtils.d(folderPath);
    }

    public void hideFrameGroup() {
        mGridViewFrame.setVisibility(View.GONE);
        unSelecteFrameGroupButtonState();
        mRlHeader.setVisibility(View.VISIBLE);
    }


    private void showExitPopUp() {
        mExitPopUp.setVisibility(View.VISIBLE);
    }

    private void hideExitPopUp() {
        mExitPopUp.setVisibility(View.GONE);
    }


    private void showFrames(String dataPath) {
        try {
            final List<String> lstFramePath = JsonUtils.getImagesPathFromJson(this, dataPath);
            FrameAdapter frameAdapter = new FrameAdapter(this, lstFramePath);
            mGridViewFrame.setAdapter(frameAdapter);
            mGridViewFrame.setVisibility(View.VISIBLE);
            mRlHeader.setVisibility(View.GONE);

            mGridViewFrame.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    try {
                        String path = lstFramePath.get(i);
                        hideFrameGroup();
                        Bitmap bitmap = BitmapFactory.decodeStream(getAssets().open(path));
                        mIvFrame.setImageBitmap(bitmap);


                        LogUtils.d(path);
                    } catch (Exception exp) {
                        LogUtils.e(exp);
                    }
                }
            });
        } catch (Exception exp) {
            LogUtils.e(exp);
        }

    }



    private void saveFile() {
        try {
            File fTemp = FileUtils.createEmptyFile(this);
            //mStickerView.save(fTemp);
            mFileSavedpath = fTemp.getAbsolutePath();
        } catch (Exception exp) {
            LogUtils.e(exp);
        }

    }

    public void backClick(View view){
        showExitPopUp();
    }

    public void saveImageClick(View view) {
        SaveFileTask task = new SaveFileTask(this);
        task.execute();
    }

    public void noExitClick(View view) {
        hideExitPopUp();
    }

    public void yesExitClick(View view) {
        if (!showInterstitial()) {
            goBackMainActionCategory();
        }
    }

    @Override
    public void onBackPressed() {
        if (mExitPopUp.getVisibility() == View.VISIBLE) {
            hideExitPopUp();
            return;
        }
        if (mGridViewFrame.getVisibility() == View.VISIBLE) {
            hideFrameGroup();
            return;
        }


        showExitPopUp();

    }


    class SaveFileTask extends AsyncTask<Void, Void, Void> {
        Context context;
        ProgressDialog pd;

        public SaveFileTask(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            saveFile();
            return null;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(context, "Please wait", "Image is processing");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            if (!showInterstitial()) {
                goBackMainActionCategory();
            }
        }
    }


    class ImageTouch implements View.OnTouchListener{

        // These matrices will be used to move and zoom image
        public Matrix matrix = new Matrix();
        public Matrix savedMatrix = new Matrix();

        // We can be in one of these 3 states
        static final int NONE = 0;
        static final int DRAG = 1;
        static final int ZOOM = 2;
        private static final float MAX_ZOOM = (float) 3;
        private static final float MIN_ZOOM = 1;
        int mode = NONE;

        // Remember some things for zooming
        PointF start = new PointF();
        PointF mid = new PointF();
        float oldDist = 1f;

        int width,height;

        @Override
        public boolean onTouch(View v, MotionEvent event) {


            ImageView view = (ImageView) v;
            Rect bounds = view.getDrawable().getBounds();

            width = bounds.right - bounds.left;
            height = bounds.bottom - bounds.top;
            // Dump touch event to log
            dumpEvent(event);

            // Handle touch events here...
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    savedMatrix.set(matrix);
                    start.set(event.getX(), event.getY());
                    mode = DRAG;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    oldDist = spacing(event);
                    if (oldDist > 10f) {
                        savedMatrix.set(matrix);
                        midPoint(mid, event);
                        mode = ZOOM;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                    mode = NONE;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mode == DRAG) {
                        // ...
                        matrix.set(savedMatrix);
                        matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                    } else if (mode == ZOOM) {
                        float newDist = spacing(event);
                        if (newDist > 10f) {
                            matrix.set(savedMatrix);
                            float scale = newDist / oldDist;
                            matrix.postScale(scale, scale, mid.x, mid.y);
                        }
                    }
                    break;
            }
//----------------------------------------------------
            limitZoom(matrix);
            limitDrag( matrix);
//----------------------------------------------------
            view.setImageMatrix(matrix);
            return true; // indicate event was handled
        }

        /** Show an event in the LogCat view, for debugging */
        private void dumpEvent(MotionEvent event) {
            String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE",
                    "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
            StringBuilder sb = new StringBuilder();
            int action = event.getAction();
            int actionCode = action & MotionEvent.ACTION_MASK;
            sb.append("event ACTION_").append(names[actionCode]);
            if (actionCode == MotionEvent.ACTION_POINTER_DOWN
                    || actionCode == MotionEvent.ACTION_POINTER_UP) {
                sb.append("(pid ").append(
                        action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
                sb.append(")");
            }
            sb.append("[");
            for (int i = 0; i < event.getPointerCount(); i++) {
                sb.append("#").append(i);
                sb.append("(pid ").append(event.getPointerId(i));
                sb.append(")=").append((int) event.getX(i));
                sb.append(",").append((int) event.getY(i));
                if (i + 1 < event.getPointerCount())
                    sb.append(";");
            }
            sb.append("]");
        }

        /** Determine the space between the first two fingers */
        private float spacing(MotionEvent event) {
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);

            return (float)Math.sqrt(x * x + y * y);
        }

        /** Calculate the mid point of the first two fingers */
        private void midPoint(PointF point, MotionEvent event) {
            float x = event.getX(0) + event.getX(1);
            float y = event.getY(0) + event.getY(1);
            point.set(x / 2, y / 2);
        }

        private void limitZoom(Matrix m) {

            float[] values = new float[9];
            m.getValues(values);
            float scaleX = values[Matrix.MSCALE_X];
            float scaleY = values[Matrix.MSCALE_Y];
            if(scaleX > MAX_ZOOM) {
                scaleX = MAX_ZOOM;
            } else if(scaleX < MIN_ZOOM) {
                scaleX = MIN_ZOOM;
            }

            if(scaleY > MAX_ZOOM) {
                scaleY = MAX_ZOOM;
            } else if(scaleY < MIN_ZOOM) {
                scaleY = MIN_ZOOM;
            }

            values[Matrix.MSCALE_X] = scaleX;
            values[Matrix.MSCALE_Y] = scaleY;
            m.setValues(values);
        }


        private void limitDrag(Matrix m) {

            float[] values = new float[9];
            m.getValues(values);
            float transX = values[Matrix.MTRANS_X];
            float transY = values[Matrix.MTRANS_Y];
            float scaleX = values[Matrix.MSCALE_X];
            float scaleY = values[Matrix.MSCALE_Y];
//--- limit moving to left ---
            float minX = (-width + 0) * (scaleX-1);
            float minY = (-height + 0) * (scaleY-1);
//--- limit moving to right ---
            float maxX=minX+width*(scaleX-1);
            float maxY=minY+height*(scaleY-1);
            if(transX>maxX){transX = maxX;}
            if(transX<minX){transX = minX;}
            if(transY>maxY){transY = maxY;}
            if(transY<minY){transY = minY;}
            values[Matrix.MTRANS_X] = transX;
            values[Matrix.MTRANS_Y] = transY;
            m.setValues(values);
        }
    }
}
