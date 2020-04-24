package com.app.vgs.vgimagesticker.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.vgs.vgimagesticker.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static com.app.vgs.vgimagesticker.activity.ShareActivity.imgReferences;

public class FaceBookActivity extends AppCompatActivity {

    ProfilePictureView proFile;
    LoginButton btnLog;
    Button btnLogout,btnChucNang;
    TextView txtName;
    CallbackManager callbackManager;
    String name="";
    ShareDialog shareDialog;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_face_book);

        initView();
        initData();
    }

    private void initData() {
        troubleshootingFacebook();
        btnChucNang.setVisibility(View.INVISIBLE);
        btnLogout.setVisibility(View.INVISIBLE);
        txtName.setVisibility(View.INVISIBLE);
        btnLog.setReadPermissions(Arrays.asList("public_profile","email"));
        setLogin_Button();
        setLogout_Button();
        readDataImage();
        shareImage();
        clickChucNang();
    }

    private void initView() {
        proFile         =   findViewById(R.id.friendProfilePicture);
        btnLog          =   findViewById(R.id.login_button);
        btnLogout       =   findViewById(R.id.btnLogout);
        btnChucNang     =   findViewById(R.id.btnlogChucNang);
        txtName         =   findViewById(R.id.txtName);
        shareDialog     = new ShareDialog(FaceBookActivity.this);
    }

    private void setLogout_Button() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                btnLogout.setVisibility(View.INVISIBLE);
                btnChucNang.setVisibility(View.INVISIBLE);
                txtName.setVisibility(View.INVISIBLE);
                txtName.setText("");
                proFile.setProfileId(null);
                btnLog.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setLogin_Button() {
        btnLog.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                btnLog.setVisibility(View.INVISIBLE);
                btnLogout.setVisibility(View.VISIBLE);
                btnChucNang.setVisibility(View.VISIBLE);
                txtName.setVisibility(View.VISIBLE);
                result();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void result() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON",response.getJSONObject().toString());
                try{

                    name = object.getString("name");
                    proFile.setProfileId(Profile.getCurrentProfile().getId());

                    txtName.setText(name);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fileds","name,email,first_name");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }
    // tìm địa chỉ tới nguồn ảnh để liên kết facebook
    public void troubleshootingFacebook(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.app.vgs.vgimagesticker",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NoSuchAlgorithmException e) {

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }

    //chia ser hinh anh
    public void shareImage(){
        btnChucNang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bitmap)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content);
            }
        });
    }

    public void readDataImage(){
        try {
            InputStream inputStream = getContentResolver().openInputStream(Uri.parse(imgReferences));
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // click chức năng
    private void clickChucNang(){
        btnChucNang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(bitmap)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                shareDialog.show(content);
            }
        });
    }
}
