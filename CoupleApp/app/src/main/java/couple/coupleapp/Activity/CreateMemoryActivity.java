package couple.coupleapp.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.R;

public class CreateMemoryActivity extends AppCompatActivity {
    int year_lastchoise, month_lastchoise, day_lastchoise;
    Calendar cal;
    long selectedDate, timenow;
    TextView dateofMemory;
    SimpleDateFormat simpleDateFormat;
    ImageButton memory_close_btn, memory_save_btn, clear_image_btn;
    LinearLayout open_option_upload;
    Dialog dialog;
    EditText caption;
    ImageView imageOfMemory;
    Button open_gallery_btn, open_capture_btn, exit_btn;
    private static final int REQUEST_CODE_ALBUM = 200;
    private static final int REQUEST_CODE_CAMERA = 100;
    StorageReference storageRef;
    String url_create_memory;
    String str_caption, str_createDate, linkImage;
    int flag_image; //=0:không có ảnh được chọn, =1 có ảnh được chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_memory);
        anhxa();
        init();
        dateofMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDatePicker();
            }
        });
        memory_close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        open_option_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogOptionUpload();
            }
        });
        clear_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag_image = 0;
                imageOfMemory.setImageResource(R.drawable.icon_picture_2);
                imageOfMemory.setScaleType(ImageView.ScaleType.CENTER);
            }
        });
        memory_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_caption = caption.getText().toString();
                str_createDate = dateofMemory.getText().toString();
                if(flag_image==0){
                    createMemory(url_create_memory);
                }else{
                    uploadimage();

                }

            }
        });
    }

    private void anhxa() {
        dateofMemory = (TextView) findViewById(R.id.memory_date);
        memory_close_btn = (ImageButton) findViewById(R.id.memory_close);
        open_option_upload = (LinearLayout) findViewById(R.id.memory_addImage);
        imageOfMemory = (ImageView) findViewById(R.id.memory_image);
        caption = (EditText) findViewById(R.id.memory_caption);
        memory_save_btn = (ImageButton) findViewById(R.id.memory_save);
        clear_image_btn = (ImageButton) findViewById(R.id.clear_image);
    }

    private void init() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        cal = Calendar.getInstance();
        year_lastchoise = cal.get(Calendar.YEAR);
        month_lastchoise = cal.get(Calendar.MONTH);
        day_lastchoise = cal.get(Calendar.DAY_OF_MONTH);
        timenow = cal.getTimeInMillis();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateofMemory.setText(simpleDateFormat.format(timenow));
        url_create_memory = Constant.URL_HOSTING + Constant.URL_CREATE_MEMORY;
        str_caption = "";
        str_createDate = "";
        linkImage = "";
        flag_image = 0;
    }

    private void getDatePicker() {
        DatePickerDialog pickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(year, month, dayOfMonth);
                year_lastchoise = year;
                month_lastchoise = month;
                day_lastchoise = dayOfMonth;
                selectedDate = cal.getTimeInMillis();
                dateofMemory.setText(simpleDateFormat.format(cal.getTime()));
            }
        }, year_lastchoise, month_lastchoise, day_lastchoise);
        pickerDialog.getDatePicker().setMaxDate(timenow);
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
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
                ActivityCompat.requestPermissions(CreateMemoryActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ALBUM);
            }
        });
        open_capture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                ActivityCompat.requestPermissions(CreateMemoryActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
            }
        });
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                Log.e("Long", "onRequestPermissionsResult: den duoc day");
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent capture_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(capture_intent, REQUEST_CODE_CAMERA);
                } else {
                    Toast.makeText(this, "Không có quyền truy cập camera", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_CODE_ALBUM:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, REQUEST_CODE_ALBUM);
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
                    imageOfMemory.setImageBitmap(img);
                    imageOfMemory.setScaleType(ImageView.ScaleType.FIT_XY);
                    flag_image = 1;
                }
                break;
            case REQUEST_CODE_ALBUM:
                if (resultCode == RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    imageOfMemory.setImageURI(uri);
                    imageOfMemory.setScaleType(ImageView.ScaleType.FIT_XY);
                    flag_image = 1;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadimage() {
        Calendar cal = Calendar.getInstance();
        // Get the data from an ImageView as bytes
        StorageReference mountainsRef = storageRef.child("memory/image" + cal.getTimeInMillis() + ".png");

        imageOfMemory.setDrawingCacheEnabled(true);
        imageOfMemory.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageOfMemory.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.e("memory", "upload anh: upload ảnh thất bại");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("memory", "upload anh: upload ảnh thành công");
                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        linkImage = uri.toString();
                        Log.e("memory", "link image: "+linkImage );
                        createMemory(url_create_memory);
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

    /**
     * gọi service tạo kỉ niệm
     *
     * @param url link api create memory post method
     */
    private void createMemory(String url) {

        JSONObject postparams = new JSONObject();
        try {
            Log.e("uploadDB", "url : "+linkImage );
            //check nếu memory không có ảnh thì gán giá trị mặc định
            if(flag_image==0){
                linkImage=Constant.STATE_IMAGE_DEFAULT;
            }
            //set variable to post
            postparams.put("image", linkImage);
            postparams.put("time", str_createDate);
            postparams.put("caption", str_caption);
            postparams.put("userId", Constant.MY_USER_ID);
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, postparams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        String result = response.getString("result");
                        if (Constant.RESULT_TRUE.equals(result)) {
                            Toast.makeText(CreateMemoryActivity.this, "Tạo kỉ niệm thành công", Toast.LENGTH_SHORT).show();

                            // nếu tạo kỉ niệm thành công quay về trang timeline
                            onBackPressed();
                        } else {

                            Toast.makeText(CreateMemoryActivity.this, "Có lỗi xảy ra, thử lại sau", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("memmory", "onResponse: Lỗi json");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CreateMemoryActivity.this, "Lỗi server thử lại sau", Toast.LENGTH_SHORT).show();
                }
            });
            //đẩy request
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void goToTimelineFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TimelineFragment timelineFragment = new TimelineFragment();
        fragmentTransaction.replace(R.id.frame_content, timelineFragment);
        fragmentTransaction.commit();
    }
}
