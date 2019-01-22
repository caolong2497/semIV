/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author vucaolong
 */
public class Utils {

    /**
     * convert string to sqldate
     *
     * @param str_Date chuỗi ngày tháng năm định dạng dd/MM/yyyy
     * @return đối tượng java.sql.Date
     * @throws ParseException
     */
    public static java.sql.Date StringToSQLDate(String str_Date) {
        java.sql.Date sqlDate = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = simpleDateFormat.parse(str_Date);
            sqlDate = new java.sql.Date(date.getTime());
            return sqlDate;
        } catch (ParseException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sqlDate;
    }

    /**
     * convert string to sqldate
     *
     * @param str_Date chuỗi ngày tháng năm định dạng dd-MM-yyyy
     * @return đối tượng java.sql.Date
     * @throws ParseException
     */
    public static java.sql.Date StringToSQLDateFormat2(String str_Date) {
        java.sql.Date sqlDate = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            Date date = simpleDateFormat.parse(str_Date);
            sqlDate = new java.sql.Date(date.getTime());
            return sqlDate;
        } catch (ParseException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sqlDate;
    }

    /**
     * convert dinh dang chuoi ngay thang nam
     *
     * @param time chuoi time
     * @param oldFormat format hien tai
     * @param newFormat format mong muon
     * @return chuoi ngay thang nam da duoc format lai
     */
    public static String convertFormatStringDate(String time, String oldFormat, String newFormat) {
        String ResultDate = null;
        try {
            Date date1 = new SimpleDateFormat(oldFormat).parse(time);
            ResultDate = new SimpleDateFormat(newFormat).format(date1);
        } catch (ParseException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ResultDate;
    }

    /**
     * gửi mail báo mã để đổi lại mật khẩu
     *
     * @param mailAddress
     * @param code
     * @return
     */
    public static boolean sendMail(String mailAddress, String code) {
        try {
            String host = "smtp.gmail.com";
            String user = "botizshop@gmail.com";
            String pass = "botiz123";
            String to = mailAddress;
            String from = "botizshop@gmail.com";
            String subject = "Forget Password";
            String messageText = "Code to create new password : " + code;
            boolean sessionDebug = false;
            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            InternetAddress[] address = {new InternetAddress(to)};
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(messageText);

           Transport transport=mailSession.getTransport("smtp");
           transport.connect(host, user, pass);
           transport.sendMessage(msg, msg.getAllRecipients());
           transport.close();
           return true;
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }

    public static String codeDefault() {
        String code = (10000 + new Random().nextInt(999999)) + "";
        return code;
    }
}
