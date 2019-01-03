package couple.coupleapp.Common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class Utils {

    /**
     * Mã hóa chuỗi theo md5+sha1+base64
     *
     * @param text chuỗi muốn mã hóa
     * @return chuỗi đã mã hóa theo md5+sha1+base64
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String encodeText(String text) {
        String passEncrypted = null;
        try {
            passEncrypted = encodeMD5(encodeSHA1(text));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Lỗi mã hóa");
        }
        return passEncrypted;
    }

    /**
     * Mã hóa MD5
     *
     * @param text  chuỗi cần mã hóa
     * @return chuỗi được mã hóa MD5
     * @throws NoSuchAlgorithmException
     */
    public static String encodeMD5(String text) throws NoSuchAlgorithmException {
        // MessageDigest này cung cấp các phương thức mã hóa theo thuật toán
        // getInstance method để truyền vào kiểu muốn mã hóa
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        // đối tượng lưu trữ lớn để lưu các giá trị vừa mã hóa
        BigInteger bigInt = new BigInteger(1, messageDigest.digest(text.getBytes()));
        // chuyển về hệ thập lục phân để đồng bộ với mysql
        return bigInt.toString(16);
    }

    /**
     * Mã hóa SHA1
     * @param text chuỗi cần mã hóa
     * @return chuỗi đã mã hóa SHA1
     * @throws NoSuchAlgorithmException
     */
    public static String encodeSHA1(String text) throws NoSuchAlgorithmException {
        // lấy kiểu mã hóa sha-1
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        // đối tượng lưu trữ lớn để lưu các giá trị vừa mã hóa
        BigInteger bigInt = new BigInteger(1, messageDigest.digest(text.getBytes()));
        // chuyển về hệ thập lục phân để đồng bộ với mysql
        return bigInt.toString(16);
    }


    /**
     * set full độ cao cho listview
     * @param listView
     *
     */
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
     * Tính số ngày chênh lệch từ ngày truyền vào đến ngày hiện tại
     * @param date ngày bắt đầu format dd/MM/yyyy
     * @return số lượng ngày tính từ ngày truyền vào
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
