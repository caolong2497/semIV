package couple.coupleapp.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import couple.coupleapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class UploadBackgroundActivity extends AppCompatActivity {
    LinearLayout uploadbackground_open;
    Dialog dialog;
    Button open_gallery_btn, open_capture_btn, exit_btn;
    ImageButton upload_background_back;
    ImageView image_choise;
    private static final int REQUEST_CODE_ALBUM=200;
    private static final int REQUEST_CODE_CAMERA=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_background);
        anhxa();
        upload_background_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        uploadbackground_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogOptionUpload();
            }
        });
    }

    private void anhxa() {
        upload_background_back=(ImageButton) findViewById(R.id.uploadbackground_back);
        image_choise= (ImageView) findViewById(R.id.image_selected);
        uploadbackground_open=(LinearLayout) findViewById(R.id.uploadbackground_open);
    }

    private void showDialogOptionUpload() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_optionupload);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        open_gallery_btn = (Button) dialog.findViewById(R.id.dialog_update_gallery);
        open_capture_btn = (Button) dialog.findViewById(R.id.dialog_update_capture);
        exit_btn = (Button) dialog.findViewById(R.id.dialog_update_exit);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        open_gallery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                ActivityCompat.requestPermissions(UploadBackgroundActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ALBUM);
            }
        });
        open_capture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                ActivityCompat.requestPermissions(UploadBackgroundActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
            }
        });
        dialog.show();
    }
    private void  openAlbum(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,REQUEST_CODE_ALBUM);
    }
    private void  openCapture(){
        Intent capture_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(capture_intent,REQUEST_CODE_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                Log.e("Long", "onRequestPermissionsResult: den duoc day" );
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCapture();
                }else{
                    Toast.makeText(this, "Không có quyền truy cập camera", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_ALBUM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                }else{
                    Toast.makeText(this, "Không có quyền truy cập ALBUM", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case REQUEST_CODE_CAMERA:
                if (resultCode == RESULT_OK && data != null) {
                    Log.e("Long", "onActivityResult: set anh" );
                    Bitmap img = (Bitmap) data.getExtras().get("data");
                    image_choise.setImageBitmap(img);
                }
                break;
            case REQUEST_CODE_ALBUM:
                if (resultCode == RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    image_choise.setImageURI(uri);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
