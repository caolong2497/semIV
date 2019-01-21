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
    private static final int RC_PHOTO_PICKER = 2;
    //    private static final int REQUEST_CODE_CAMERA = 100;
    String linkimage;
    StorageReference storageRef;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChildEventListener;
    List<MessageModel> friendlyMessages;
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        //Firebase intial
        init();
        IntialAction();


        //sự kiện lắng nghe
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("message");
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MessageModel friendlyMessage = dataSnapshot.getValue(MessageModel.class);
                friendlyMessages.add(friendlyMessage);
                mMessageAdapter.notifyDataSetChanged();
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

//         ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ALBUM);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

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
        friendlyMessages = new ArrayList<>();

        // Initialize references to views
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mMessageListView = (ListView) view.findViewById(R.id.messageListView);
        mPhotoPickerButton = (ImageButton) view.findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) view.findViewById(R.id.messageEditText);
        mSendButton = (Button) view.findViewById(R.id.sendButton);
        // Initialize message ListView and its adapter
        mMessageAdapter = new MessageAdapter(getActivity(), R.layout.item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == RC_PHOTO_PICKER && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            startActivityForResult(intent, RC_PHOTO_PICKER);
        } else {
            Toast.makeText(getActivity(), "Không có quyền truy cập ALBUM", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == RC_PHOTO_PICKER && resultCode == Activity.RESULT_OK && data != null) {
            Calendar cal = Calendar.getInstance();
            Uri uri = data.getData();
            StorageReference photoref = storageRef.child("chat/image" + cal.getTimeInMillis() + ".png");
            photoref.putFile(uri).addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            linkimage = uri.toString();
                            long time = System.currentTimeMillis();
                            // TODO: Send messages on click
                            MessageModel friendlyMessage = new MessageModel(Constant.MY_USER_ID, null, linkimage, time, Constant.MY_COUPLE_ID);
                            mMessageDatabaseReference.push().setValue(friendlyMessage);
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

        super.onActivityResult(requestCode, resultCode, data);
    }
}