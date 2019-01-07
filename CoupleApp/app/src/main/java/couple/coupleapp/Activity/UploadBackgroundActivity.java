package couple.coupleapp.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class UploadBackgroundActivity extends AppCompatActivity {
    LinearLayout uploadbackground_open;
    Dialog dialog;
    Button open_gallery_btn, open_capture_btn, exit_btn;
    ImageButton upload_background_back, upload_background_save;
    ImageView image_choise;
    int flag;
    private static final int REQUEST_CODE_ALBUM = 200;
    private static final int REQUEST_CODE_CAMERA = 100;
    StorageReference storageRef;
    String url_update_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_background);
        anhxa();
        init();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
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
        upload_background_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) {
                    Toast.makeText(UploadBackgroundActivity.this, "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
                } else {
                    uploadimage();
                }

            }
        });
    }

    private void init() {
        url_update_image=Constant.URL_HOSTING+Constant.URL_UPDATE_IMG_COUPLE;
        flag = 0;
    }

    private void anhxa() {
        upload_background_back = (ImageButton) findViewById(R.id.uploadbackground_back);
        image_choise = (ImageView) findViewById(R.id.image_selected);
        uploadbackground_open = (LinearLayout) findViewById(R.id.uploadbackground_open);
        upload_background_save = (ImageButton) findViewById(R.id.image_save);
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

    private void openAlbum() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_CODE_ALBUM);
    }

    private void openCapture() {
        Intent capture_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(capture_intent, REQUEST_CODE_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                Log.e("Long", "onRequestPermissionsResult: den duoc day");
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCapture();
                } else {
                    Toast.makeText(this, "Không có quyền truy cập camera", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_ALBUM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
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
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                if (resultCode == RESULT_OK && data != null) {
                    Log.e("Long", "onActivityResult: set anh");
                    Bitmap img = (Bitmap) data.getExtras().get("data");
                    image_choise.setImageBitmap(img);
                    flag = 1;
                }
                break;
            case REQUEST_CODE_ALBUM:
                if (resultCode == RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    image_choise.setImageURI(uri);
                    flag = 1;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadimage() {
        Calendar cal = Calendar.getInstance();
        // Get the data from an ImageView as bytes
        StorageReference mountainsRef = storageRef.child("images/image" + cal.getTimeInMillis() + ".png");

        image_choise.setDrawingCacheEnabled(true);
        image_choise.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) image_choise.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(UploadBackgroundActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(UploadBackgroundActivity.this, "Thành công", Toast.LENGTH_SHORT).show();

                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String result_image_link = uri.toString();
                        updatelinkimage(url_update_image,Constant.MY_COUPLE_ID,result_image_link);
//                        Picasso.get().load(photoStringLink).into(output_view);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("long", "get link fail ");
                    }
                });
            }
        });
    }

    private void updatelinkimage(String url, int coupleid, String linkimage) {
        JSONObject postparams = new JSONObject();
        try {
            postparams.put("coupleID", coupleid);
            postparams.put("image", linkimage);
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String result = response.getString("result");
                        if (Constant.RESULT_TRUE.equals(result)) {
                            Intent intent = new Intent(UploadBackgroundActivity.this, CountDateActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(UploadBackgroundActivity.this, "Update link thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("update backgroud", "fail get response ");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(UploadBackgroundActivity.this, "Có lỗi xảy ra,thử lại sau", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
