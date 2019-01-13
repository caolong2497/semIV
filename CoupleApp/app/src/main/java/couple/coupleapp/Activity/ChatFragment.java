package couple.coupleapp.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import couple.coupleapp.Adapter.MessageAdapter;
import couple.coupleapp.Common.Constant;
import couple.coupleapp.R;
import couple.coupleapp.entity.MessageModel;


public class ChatFragment extends Fragment {
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;
//    Dialog dialog;
//    Button open_gallery_btn, open_capture_btn, exit_btn;
//    ImageView image_post;
//    private static final int REQUEST_CODE_ALBUM = 200;
//    private static final int REQUEST_CODE_CAMERA = 100;
//    String linkimage;
    StorageReference storageRef;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChildEventListener;
    View view;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        //Firebase instance variable
        init();
        IntialAction();


        //sự kiện lắng nghe
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("message");
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MessageModel friendlyMessage = dataSnapshot.getValue(MessageModel.class);
                mMessageAdapter.add(friendlyMessage);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        Query data = mMessageDatabaseReference.orderByChild("coupleid").equalTo(Constant.MY_COUPLE_ID);
        data.addChildEventListener(mChildEventListener);


        // Initialize message ListView and its adapter
        List<MessageModel> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(getActivity(), R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        return view;
    }

    private void IntialAction() {
        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long time = System.currentTimeMillis();
                // TODO: Send messages on click
                MessageModel friendlyMessage = new MessageModel(Constant.MY_USER_ID, mMessageEditText.getText().toString(), null, time, Constant.MY_COUPLE_ID);

                mMessageDatabaseReference.push().setValue(friendlyMessage);
                // Clear input box
                mMessageEditText.setText("");
            }
        });

        // ImagePickerButton shows an image picker to upload a image for a message
//        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDialogOptionUpload();
//            }
//        });

        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void init() {
        // Initialize references to views
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mMessageListView = (ListView) view.findViewById(R.id.messageListView);
        mPhotoPickerButton = (ImageButton) view.findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) view.findViewById(R.id.messageEditText);
        mSendButton = (Button) view.findViewById(R.id.sendButton);
//        image_post=(ImageView) view.findViewById(R.id.);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

//
//    private void showDialogOptionUpload() {
//        dialog = new Dialog(getActivity());
//        dialog.setContentView(R.layout.dialog_optionupload);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        open_gallery_btn = (Button) dialog.findViewById(R.id.dialog_update_gallery);
//        open_capture_btn = (Button) dialog.findViewById(R.id.dialog_update_capture);
//        exit_btn = (Button) dialog.findViewById(R.id.dialog_update_exit);
//        exit_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.hide();
//            }
//        });
//        open_gallery_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.hide();
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ALBUM);
//            }
//        });
//        open_capture_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.hide();
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
//            }
//        });
//        dialog.show();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CODE_CAMERA:
//                Log.e("Long", "onRequestPermissionsResult: den duoc day");
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Intent capture_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(capture_intent, REQUEST_CODE_CAMERA);
//                } else {
//                    Toast.makeText(getActivity(), "Không có quyền truy cập camera", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case REQUEST_CODE_ALBUM:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                            MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//                    startActivityForResult(galleryIntent, REQUEST_CODE_ALBUM);
//                } else {
//                    Toast.makeText(getActivity(), "Không có quyền truy cập ALBUM", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                break;
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        switch (requestCode) {
//            case REQUEST_CODE_CAMERA:
//                if (resultCode == Activity.RESULT_OK && data != null) {
//                    Log.e("Long", "onActivityResult: set anh");
//                    Bitmap img = (Bitmap) data.getExtras().get("data");
//                    image_post.setImageBitmap(img);
//                    image_post.setScaleType(ImageView.ScaleType.FIT_XY);
//                    uploadimage();
//                }
//                break;
//            case REQUEST_CODE_ALBUM:
//                if (resultCode == Activity.RESULT_OK && data != null) {
//                    Uri uri = data.getData();
//                    image_post.setImageURI(uri);
//                    image_post.setScaleType(ImageView.ScaleType.FIT_XY);
//                    uploadimage();
//                }
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    private void uploadimage() {
//        Calendar cal = Calendar.getInstance();
//        // Get the data from an ImageView as bytes
//        StorageReference mountainsRef = storageRef.child("chat/image" + cal.getTimeInMillis() + ".png");
//
//        image_post.setDrawingCacheEnabled(true);
//        image_post.buildDrawingCache();
//        Bitmap bitmap = ((BitmapDrawable) image_post.getDrawable()).getBitmap();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] data = baos.toByteArray();
//
//        UploadTask uploadTask = mountainsRef.putBytes(data);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                Log.e("memory", "upload anh: upload ảnh thất bại");
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Log.e("memory", "upload anh: upload ảnh thành công");
//                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
//                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//                        linkimage = uri.toString();
//                        Log.e("memory", "link image: " + linkimage);
//                        long time = System.currentTimeMillis();
//                        // TODO: Send messages on click
//                        MessageModel friendlyMessage = new MessageModel(Constant.MY_USER_ID, null, linkimage, time, Constant.MY_COUPLE_ID);
//                        mMessageDatabaseReference.push().setValue(friendlyMessage);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        Log.e("long", "get link fail ");
//                    }
//                });
//            }
//        });
//    }
}