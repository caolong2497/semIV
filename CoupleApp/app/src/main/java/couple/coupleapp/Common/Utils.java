package couple.coupleapp.Common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    /**
     *
     * @param date ngày bắt đầu format dd/MM/yyyy
     * @return số lượng ngày
     */
    public static long countDate(String date){
        long result=0;
        Date startDate=null;
        Date toDate=null;
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd-MM-yyyy");
        Date currentDate=new Date();
        String str_currentDate=simpleDateFormat.format(currentDate);
        try {
            startDate=simpleDateFormat.parse(date);
            toDate=simpleDateFormat.parse(str_currentDate);
            result =(toDate.getTime()-startDate.getTime())/(24*60*60*1000);
        } catch (ParseException e) {
            System.out.println("Lỗi đếm ngày");
        }
        return result;
    }
}
