/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vucaolong
 */
public class Utils {
    /**
     * convert string to sqldate
     * @param str_Date chuỗi ngày tháng năm định dạng dd/MM/yyyy
     * @return đối tượng java.sql.Date
     * @throws ParseException 
     */
    public static java.sql.Date  StringToSQLDate(String str_Date) {
        java.sql.Date sqlDate=null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = simpleDateFormat.parse(str_Date);
             sqlDate= new java.sql.Date(date.getTime());
            return sqlDate;
        } catch (ParseException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sqlDate;
    }
    /**
     * convert dinh dang chuoi ngay thang nam
     * @param time chuoi time
     * @param oldFormat format hien tai
     * @param newFormat format mong muon
     * @return chuoi ngay thang nam da duoc format lai
     */
    public static String convertFormatStringDate(String time,String oldFormat,String newFormat){
        String ResultDate=null;
        try {
            Date date1=new SimpleDateFormat(oldFormat).parse(time);  
            ResultDate =  new SimpleDateFormat(newFormat).format(date1);
        } catch (ParseException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ResultDate;
    }
}
