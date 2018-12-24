package couple.coupleapp.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import couple.coupleapp.R;

public class CountdateFragment extends Fragment {
    TextView countDate;
    ImageView myAvatar;
    View view;
    long result_date;
    ImageButton close_dialog_btn;
    Button update_btn;
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_countdate, container, false);
        Bundle bundle = getArguments();
        anhxa();
        if (bundle != null) {
            String timefirst =  bundle.getString("date");
            long firstday = Long.parseLong(timefirst);
            Calendar calendar = Calendar.getInstance();
            long today = calendar.getTimeInMillis();
            result_date = (today - firstday) / (1000 * 60 * 60 * 24);
        }
        countDate.setText(result_date + "");
        myAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUser();
            }
        });

        return view;
    }

    private void anhxa() {
        result_date = 0;
        countDate = (TextView) view.findViewById(R.id.count_date);
        myAvatar = (ImageView) view.findViewById(R.id.count_myimg);


    }

    private void showDialogUser() {
         dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_userinfor);
        close_dialog_btn=(ImageButton) dialog.findViewById(R.id.dialog_close);
        update_btn=(Button) dialog.findViewById(R.id.dialog_updateinfor);
        close_dialog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.hide();
            }
        });
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),UpdateUserActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }

//    public void setCountDate(String date){
//        countDate.setText(date+"");
//    }
}
