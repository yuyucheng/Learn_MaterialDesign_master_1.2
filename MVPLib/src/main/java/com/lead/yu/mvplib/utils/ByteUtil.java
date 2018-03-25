package com.lead.yu.mvplib.utils;

import android.annotation.SuppressLint;

import java.util.Arrays;

/**
 * new
 * Created by yuyucheng on 2018/3/25.
 */

public class ByteUtil {

    public static final byte ZERO = "0".getBytes()[0];
    private static String numbers = "0123456789abcdef";

    final static char[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };

    // ascii -> int
    final static int[] digits0 = {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
            2, 3, 4, 5, 6, 7, 8, 9
    };

    public static String htos(byte[] bytes) {
        if (bytes == null)
            return null;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String tmp = Integer.toHexString(((int) bytes[i]) & 0xFF);
            while (tmp.length() < 2)
                tmp = "0" + tmp;
            // if (i!=bytes.length-1)
            // sb.append(tmp + " ");
            // else
            sb.append(tmp);

        }
        return sb.toString().toUpperCase();
    }

    /**
     * Hex TO Integer
     */
    public static Integer strHexToInteger(String str) {
        return Integer.parseInt(str, 16);
    }

    public static String getBCDCode(byte[] bytes) {

        String bcdCode = "";

        for (int i = 0; i < bytes.length; i++) {
            byte big = (byte) ((bytes[i] & 0xf0) >> 4);
            byte low = (byte) (bytes[i] & 0x0f);

            bcdCode = bcdCode + (big * 10 + low);
        }

        return bcdCode;
    }

    public static int BytesToint(byte[] buff) {
        int num = (((buff[0] & 0xff) << 24) | ((buff[1] & 0xff) << 16) | ((buff[2] & 0xff) << 8) | (buff[3] & 0xff));
        return num;
    }

    public static double byteTodouble(byte[] money) {
        int d = BytesToint(money);
        double mon = 0.00;
        mon = (double) d / 100.00;
        return roundDouble(mon, 2);
    }

    private static Double roundDouble(double val, int precision) {
        Double ret = null;
        try {
            double factor = Math.pow(10, precision);
            ret = Math.floor(val * factor + 0.5) / factor;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    @SuppressLint("DefaultLocale")
    public static byte[] stoh(String s) {
        s = s.replaceAll(" ", "").toLowerCase();
        System.out.println(s);
        // s=s.toLowerCase();
        // System.out.println("s= "+s);
        if (s == null)
            return null;
        if (s.length() % 2 != 0)
            throw new RuntimeException("invalid length");
        byte[] result = new byte[s.length() / 2];
        for (int i = 0; i < s.length(); i += 2) {
            int i1 = numbers.indexOf(s.charAt(i));
            if (i1 == -1)
                throw new RuntimeException("invalid number");
            int i2 = numbers.indexOf(s.charAt(i + 1));
            if (i2 == -1)
                throw new RuntimeException("invalid number");
            result[i / 2] = (byte) ((i1 << 4) | i2);
        }
        return result;
    }

    public static short getShort(byte[] bytes) {
        return (short) (((bytes[0] & 0xFF) << 8) | bytes[1] & 0xFF);
    }

    public static short getShort(byte upperBit, byte lowerBit) {
        return (short) (((upperBit & 0xFF) << 8) | lowerBit & 0xFF);
    }

    public static byte[] ntoh4Short(short num) {
        byte[] out = new byte[2];

        out[0] = (byte) (((num & 0xffff) >> 12) * 10 + ((num & 0x0fff) >> 8));
        out[1] = (byte) (((num & 0x00ff) >> 4) * 10 + ((num & 0x000f)));

        return out;
    }

    private static String getFixedLengthNumberString(String str, int num) {
        int strLen = str.length();
        if (strLen > num) {
            return str.substring(0, num);
        }

        if (strLen == num) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num - strLen; i++) {
            sb.append("0");
        }
        sb.append(str);
        return sb.toString();
    }

    /**
     * 带符号的16进制
     */
    public static String hexToDec(Integer value) {
        return Integer.toHexString(value);
    }

    public static String ntohs(short num) {
        /*
        byte[] out = new byte[4];

		out[0] = (byte) ((num & 0xffff) >> 12);
		out[1] = (byte) ((num & 0x0fff) >> 8);
		out[2] = (byte) ((num & 0x00ff) >> 4);
		out[3] = (byte) (num & 0x000f);

		return Byte.toString(out[0]) +
			   Byte.toString(out[1]) +
			   Byte.toString(out[2]) +
			   Byte.toString(out[3]) ;
		*/

        String tmp = Integer.toString(num, 16);
        return getFixedLengthNumberString(tmp, 4);
    }


    public static byte[] shortToByteArray(short num) {
        byte[] out = new byte[2];

        out[0] = (byte) (num & 0xFF00);
        out[1] = (byte) (num & 0x00FF);

        return out;
    }

    public static short byteArrayToShort(byte[] bytes) {
        return (short) (bytes[0] | bytes[1]);
    }

    public static short byteArrayToShort(byte upperBit, byte lowerBit) {
        return (short) (upperBit | (lowerBit & 0x000000FF));
    }


    public static short hston(byte[] bytes) {
        if (bytes.length != 4)
            throw new IllegalArgumentException("the length of hexBytes must be 4, but " + bytes.length);

        return (short) (
                (digits0[bytes[0]] << 12) +
                        (digits0[bytes[1]] << 8) +
                        (digits0[bytes[2]] << 4) +
                        (digits0[bytes[3]])
        );
    }

    public static short ston4Short(String hexStr) {
        if (hexStr.length() != 4)
            throw new IllegalArgumentException("the length of hexBytes must be 4, but " + hexStr.length());

        return Short.valueOf(hexStr, 16);
    }

    public static String bcdToAscHex(String str) {
        String asc = "";
        if (str == null || str.length() == 0) {
            return asc;
        }
        for (int i = 0; i < str.length(); i++) {
            int t = str.charAt(i);
            asc = asc + hexToDec(t);
        }
        return asc;
    }

    /**
     * ASC码String 转十六进制
     *
     * @param str ASC码String
     * @return
     */
    public static String ascToBcdByHex(String str) {
        try {
            String s = "";
            if (str.length() % 2 != 0) {
                throw new RuntimeException("�ַ���Ϊ˫���쳣 str.length=" + str.length() + " str =" + str);
            }
            for (int i = 0; i <= str.length() - 2; i += 2) {
                String temp = str.substring(i, i + 2);
                char c = (char) Integer.parseInt(temp, 16);
                s = s + c + "";
            }
            return s;
        } catch (Exception e) {
            return str;
        }
    }

    /**
     * ASC码String 转十进制
     *
     * @param str ASC码
     * @return
     */
    public static String ascToBcdByDecimal(String str) {
        try {
            String s = "";
            if (str.length() % 2 != 0) {
                throw new RuntimeException("异常数据str.length=" + str.length() + " str =" + str);
            }
            for (int i = 0; i <= str.length() - 2; i += 2) {
                String temp = str.substring(i, i + 2);
                char c = (char) Integer.parseInt(temp);
                s = s + c + "";
            }
            return s;
        } catch (Exception e) {
            return str;
        }
    }

    /**
     * 16进制字符串
     *
     * @param hexStr
     * @return
     */
    public static String hexOrStr(String hexStr) {
        if (hexStr.length() % 2 != 0) {
            hexStr = "0" + hexStr;
        }
        String tempStr = "";
        int s;
        String t;
        for (int i = 0; i < hexStr.length(); i += 2) {
            s = Integer.parseInt(hexStr.substring(i, i + 2), 16);
            t = Integer.toHexString(s ^ 0xff);
            tempStr = tempStr + (t.length() == 2 ? t : "0" + t);
        }
        return tempStr.toUpperCase();
    }

    /**
     * 从高到低排序
     *
     * @return
     */
    public static Integer getHignPreLowEndDecimalist(String str) {
        if (str == null || str.length() % 2 != 0) {
            return 0;
        }
        StringBuffer temp = new StringBuffer();
        int status = 0;
        for (int i = str.length() - 2; i >= 0; i -= 2) {
            temp.append(str.substring(i, i + 2) + ",");
        }
        String[] t = temp.toString().split(",");
        StringBuffer retHexValue = new StringBuffer();
        for (int i = 0; i < t.length; i++) {
            if (t[i].equals("00") && status == 0) {
                continue;
            } else {
                status = 1;
                retHexValue.append(t[i]);
            }
        }
        if (retHexValue.toString() == null || retHexValue.toString().equals("")) {
            retHexValue.append("0");
        }
        return strHexToInteger(retHexValue.toString());
    }

    /**
     * Hex 16位 String类型
     *
     * @param hex
     * @return
     */
    public static String hexToDecimal(String hex) {
        int str = Integer.parseInt(hex, 16);
        return str + "";
    }

    public static void main(String[] args) {
        short s = 20;
        byte[] b = ntoh4Short(s);
        System.out.println(Arrays.toString(b));

        System.out.println((char) 48);
        System.out.println("0".getBytes()[0]);


        System.out.println(new String(ntohs(s).getBytes()));
        System.out.println(new String(ntohs(s)));
        System.out.println(Byte.toString((byte) 32));


        System.out.println(hston(new byte[]{0, 0, 1, 4}));
        System.out.println(ston4Short("0014"));


        byte[] bytes = new byte[]{0x00, 0x14};
        System.out.println(new String(new String(bytes).getBytes()));

        System.out.println(ntohs((short) 20));

        System.out.println(Integer.toString(20, 16));

    }
}
