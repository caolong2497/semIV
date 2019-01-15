package couple.coupleapp.Activity;

import android.Manifest;
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import couple.coupleapp.Common.Constant;
import couple.coupleapp.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateUserActivity extends AppCompatActivity {
    ImageButton updateuser_back_btn, updateuser_save_btn;
    Dialog dialog;
    Button open_gallery_btn, open_capture_btn, exit_btn;
    CircleImageView user_image;
    private static final int REQUEST_CODE_ALBUM = 200;
    private static final int REQUEST_CODE_CAMERA = 100;
    TextView label, birthday;
    EditText name, gmail;
    RequestQueue requestQueue;
    RadioButton radio_male, radio_female;
    String linkimage;
    private int year_lastchoise, month_lastchoise, day_lastchoise;
    private Calendar cal;
    private StorageReference storageRef;
    private SimpleDateFormat simpleDateFormat;
    private long timenow;
    private int flag_image_upload; //nếu flag=0 ,không upload ảnh,flag=1 upload ảnh

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        anhxa();
        init();
        Intent intent = getIntent();
        final int userId = intent.getIntExtra("userId", 0);

        //check userid nhận được để hiện thị màn hình chi tiết hay update
        if (userId == Constant.MY_USER_ID) {
            //userid = myid thì thêm sự kiện chọn ảnh
            user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogOptionUpload();
                }
            });
            updateuser_save_btn.setVisibility(View.VISIBLE);
            label.setVisibility(View.VISIBLE);
            name.setEnabled(true);
            radio_male.setClickable(true);
            radio_female.setClickable(true);
            user_image.setImageDrawable(Constant.MYSELF.getAvatar());
            updateuser_save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("".equals(name.getText().toString().trim())) {
                        Toast.makeText(UpdateUserActivity.this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
                    }else{
                        if(flag_image_upload==0){
                            updateUser();
                        }else {
                            uploadImage();
                        }
                    }

                }
            });
            birthday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getDatePicker();
                }
            });
        } else {
            updateuser_save_btn.setVisibility(View.GONE);
            label.setVisibility(View.GONE);
            user_image.setImageDrawable(Constant.PARTNER.getAvatar());
            name.setFocusable(false);
            radio_male.setClickable(false);
            radio_female.setClickable(false);
        }
        gmail.setFocusable(false);
        updateuser_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getData(userId);
    }

    private void anhxa() {
        user_image = (CircleImageView) findViewById(R.id.update_user_avatar);
        updateuser_back_btn = (ImageButton) findViewById(R.id.userinfor_back);
        updateuser_save_btn = (ImageButton) findViewById(R.id.userinfor_save);
        label = (TextView) findViewById(R.id.label_uploadimg);
        name = (EditText) findViewById(R.id.username_edittext);
        gmail = (EditText) findViewById(R.id.gmail_edittext);
        birthday = (TextView) findViewById(R.id.birthday_edittext);
        radio_male = (RadioButton) findViewById(R.id.rd_male);
        radio_female = (RadioButton) findViewById(R.id.rd_female);
    }

    private void init() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        requestQueue = Volley.newRequestQueue(this);
        cal = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        year_lastchoise = cal.get(Calendar.YEAR);
        month_lastchoise = cal.get(Calendar.MONTH);
        day_lastchoise = cal.get(Calendar.DAY_OF_MONTH);
        timenow = cal.getTimeInMillis();
        flag_image_upload = 0;

    }

    //hiển thị dialog chọn ảnh
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
                ActivityCompat.requestPermissions(UpdateUserActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ALBUM);
            }
        });
        open_capture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
                ActivityCompat.requestPermissions(UpdateUserActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
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
                Log.e("Long", "onRequestPermissionsResult: den duoc day2");
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
                    Bitmap img = (Bitmap) data.getExtras().get("data");
                    user_image.setImageBitmap(img);
                    flag_image_upload=1;
                }
                break;
            case REQUEST_CODE_ALBUM:
                if (resultCode == RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    user_image.setImageURI(uri);
                    flag_image_upload=1;
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getData(int userId) {
        String url = Constant.URL_HOSTING + Constant.URL_GETUSERINFOR + "/" + userId;
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //lấy thông tin từ response
                    String str_name = response.getString("name");
                    String str_birthday = response.getString("birthday");
                    String str_email = response.getString("gmail");
                    boolean gender = response.getBoolean("gender");
                    linkimage = response.getString("avatar");
                    String[] date = str_birthday.split("/");
                    year_lastchoise = Integer.parseInt(date[2]);
                    month_lastchoise = Integer.parseInt(date[1]) - 1;
                    day_lastchoise = Integer.parseInt(date[0]);

                    //hiển thị lên màn hình
                    if (Constant.STATE_IMAGE_DEFAULT.equals(linkimage)) {
                        user_image.setImageResource(R.drawable.no_avatar);
                    } else {
                        Picasso.get().load(linkimage).into(user_image);
                    }
                    name.setText(str_name);
                    birthday.setText(str_birthday);
                    gmail.setText(str_email);
                    if (gender) {
                        radio_male.setChecked(true);
                    } else {
                        radio_female.setChecked(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(objectRequest);
    }

    private void uploadImage() {
        Calendar cal = Calendar.getInstance();
        // Get the data from an ImageView as bytes
        StorageReference mountainsRef = storageRef.child("user/image" + cal.getTimeInMillis() + ".png");

        user_image.setDrawingCacheEnabled(true);
        user_image.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) user_image.getDrawable()).getBitmap();
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
                        linkimage = uri.toString();
                        Log.e("memory", "link image: " + linkimage);
                        updateUser();
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

    private void updateUser() {
        Log.e("update user", "updateUser: comehere" );
        String url = Constant.URL_HOSTING + Constant.URL_UPDATEUSER;
        JSONObject object = new JSONObject();

        //tạo đối tượng data post lên server
        try {
            object.put("userID", Constant.MY_USER_ID);
            object.put("name", name.getText());
            object.put("birthday", birthday.getText());
            boolean gender = true;
            if (radio_female.isChecked()) {
                gender = false;
            }
            object.put("gender", gender);
            object.put("avatar", linkimage);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //tạo request
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (Constant.RESULT_TRUE.equals(response.getString("result"))) {
                        onBackPressed();
                    } else {
                        Toast.makeText(UpdateUserActivity.this, "Lỗi update userinfor", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(UpdateUserActivity.this, "Lỗi parase json", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UpdateUserActivity.this, "Lỗi server", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(objectRequest);
    }

    private void getDatePicker() {

        DatePickerDialog pickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                cal.set(year, month, dayOfMonth);
                year_lastchoise = year;
                month_lastchoise = month;
                day_lastchoise = dayOfMonth;
                birthday.setText(simpleDateFormat.format(cal.getTime()));
            }
        }, year_lastchoise, month_lastchoise, day_lastchoise);
        pickerDialog.getDatePicker().setMaxDate(timenow);
        pickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pickerDialog.show();
    }
}
