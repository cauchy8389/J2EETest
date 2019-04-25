package common;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.ParseException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

/**
 * 
 * @author haiyunzhang
 * 2015-04-03
 */
public class CryptUtil {
    
    //将 s 进行 BASE64 编码 
    public static String encryptHex(byte[] s) {      
        return new String(Hex.encodeHex(s));
    }

    // 将 BASE64 编码的字符串 s 进行解码 
    public static byte[] decryptHex(String s) throws DecoderException {
        return Hex.decodeHex(s.toCharArray());
    }
    
    private static String password = "nmGssfgrRtY3iDe2";
    private static SecretKeyFactory keyFactory;
    
    
    public static byte[] encryptCore(byte[] datasource) {
        
        //byte[] datasource = original.getBytes();
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            //创建一个密匙工厂，然后用它把DESKeySpec转换成  

            if(keyFactory == null){
                synchronized (password) {
                    keyFactory = SecretKeyFactory.getInstance("DES");
                }
            }
            
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作  
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象  
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密  
            //正式执行加密操作  
            return cipher.doFinal(datasource);
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decryptCore(byte[] src) throws Exception {
        
        //byte[] src = source.getBytes();
        // DES算法要求有一个可信任的随机数源  
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象  
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        // 创建一个密匙工厂  
        if(keyFactory == null){
            synchronized (password) {
                keyFactory = SecretKeyFactory.getInstance("DES");
            }
        }
        // 将DESKeySpec对象转换成SecretKey对象  
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作  
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象  
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作  
        return cipher.doFinal(src);
    }
    
    public static String encrypt(String original){
        return encryptHex(encryptCore(original.getBytes()));
    }
    
    public static String decrypt(String source){
        try {
            return new String(decryptCore(decryptHex(source)));
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return "";
    }

    public static void main(String[] args){
        String tmp = CryptUtil.encrypt("asffggg");

        System.out.println(tmp);

        System.out.println(StringUtils.overlay(tmp, "123", 2,5));

        Random rd = new Random();
        int rdBound = 10000;

//        for(int i = 0; i < 100; i ++)
//            System.out.println(rd.nextInt(rdBound));


        System.out.println(rd.nextInt(rdBound));
        System.out.println(rd.nextInt(rdBound));
        System.out.println(rd.nextInt(rdBound));
        System.out.println(rd.nextInt(rdBound));
        System.out.println(rd.nextInt(rdBound));

        System.out.println(rd.nextInt(rdBound));
        System.out.println(rd.nextInt(rdBound));
        System.out.println(rd.nextInt(rdBound));
        System.out.println(rd.nextInt(rdBound));
        System.out.println(rd.nextInt(rdBound));

        Optional<String> emptyOptional = Optional.empty();
        boolean present = emptyOptional.isPresent();


        System.out.println(present);


        //MethodUtils.in
        String tmp1 = "fkdfigjg";
        MessageDigest md5Digest = DigestUtils.getMd5Digest();
        System.out.println(Hex.encodeHex(tmp1.getBytes(Charsets.UTF_8)));
        System.out.println(Hex.encodeHex(md5Digest.digest(tmp1.getBytes(Charsets.UTF_8))));

        String tmp2 = "fkdfigjg";
        System.out.println(Hex.encodeHex(tmp2.getBytes(Charsets.UTF_8)));
        System.out.println(DigestUtils.md5Hex(tmp2.getBytes(Charsets.UTF_8)));

        //[B@5594a1b5
        //[B@6a5fc7f7

        String tmp3 = "gghhh\n\r" +
                "ggffgggff\n" +
                "fgggggggg\r\n";

        System.out.println(tmp3);
        System.out.println(StringUtils.chomp(tmp3));
        System.out.println(StringUtils.chop(tmp3));

        try {
            LocalDate holiday = LocalDate.parse("2018-12-25", DateTimeFormatter.ISO_DATE);
            LocalDate holiday_next = holiday.plusDays(1);
            ZoneId zoneId = ZoneId.systemDefault();

            LocalDateTime ldtHoliday = holiday.atStartOfDay();
            ZonedDateTime zdt = ldtHoliday.atZone(zoneId);
            Date date = Date.from(zdt.toInstant());

            Instant instant = date.toInstant();
            LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
            System.out.println(localDateTime.format(DateTimeFormatter.ISO_DATE));

            System.out.println(date);
            System.out.println(holiday_next.format(DateTimeFormatter.ISO_DATE));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
