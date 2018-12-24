package couple.coupleapp.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import couple.coupleapp.R;

public class CreateMemoryActivity extends AppCompatActivity {
    int year_lastchoise, month_lastchoise, day_lastchoise;
    Calendar cal;
    long selectedDate, timenow;
    TextView dateofMemory;
    SimpleDateFormat simpleDateFormat;
    ImageButton memory_close_btn;
    LinearLayout open_option_upload;
    Dialog dialog;
    ImageView imageOfMemory;
    Button open_gallery_btn, open_capture_btn, exit_btn;
    private static final int REQUEST_CODE_ALBUM = 200;
    private static final int REQUEST_CODE_CAMERA = 100;

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
    }

    private void anhxa() {
        dateofMemory = (TextView) findViewById(R.id.memory_date);
        memory_close_btn = (ImageButton) findViewById(R.id.memory_close);
        open_option_upload = (LinearLayout) findViewById(R.id.memory_addImage);
        imageOfMemory = (ImageView) findViewById(R.id.memory_image);
    }

    private void init() {
        cal = Calendar.getInstance();
        year_lastchoise = cal.get(Calendar.YEAR);
        month_lastchoise = cal.get(Calendar.MONTH);
        day_lastchoise = cal.get(Calendar.DAY_OF_MONTH);
        timenow = cal.getTimeInMillis();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateofMemory.setText(simpleDateFormat.format(timenow));
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
                }
                break;
            case REQUEST_CODE_ALBUM:
                if (resultCode == RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    imageOfMemory.setImageURI(uri);
                    imageOfMemory.setScaleType(ImageView.ScaleType.FIT_XY);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
