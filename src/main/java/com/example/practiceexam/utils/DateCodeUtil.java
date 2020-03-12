package com.example.practiceexam.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ShiQing_Chen  2020/3/12  10:41
 **/
public class DateCodeUtil {
    private static int sequence = 0;

    private static int length = 3;

    /**
     * yyyyMMddHHmmssSSS+3位自增长码(20位)
     * @return 编码字符串
     */
    public static synchronized String getLocalTrmSeqNum() {
        sequence = sequence >= 999 ? 1 : sequence + 1;
        String datetime = new SimpleDateFormat("yyyyMMddHHmmssSSS")
                .format(new Date());
        String s = Integer.toString(sequence);
        return datetime + addLeftZero(s, length);
    }

    /**
     * 左填0
     * @param s      原字符串
     * @param length 补位长度
     * @return 补位后的字符串
     */
    private static String addLeftZero(String s, int length) {
        int old = s.length();
        if (length > old) {
            char[] c = new char[length];
            char[] x = s.toCharArray();
            if (x.length > length) {
                throw new IllegalArgumentException(
                        "Numeric value is larger than intended length: " + s
                                + " LEN " + length);
            }
            int lim = c.length - x.length;
            for (int i = 0; i < lim; i++) {
                c[i] = '0';
            }
            System.arraycopy(x, 0, c, lim, x.length);
            return new String(c);
        }
        return s.substring(0, length);

    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000000; i++) {
            System.out.println(DateCodeUtil.getLocalTrmSeqNum());
        }

    }
}
