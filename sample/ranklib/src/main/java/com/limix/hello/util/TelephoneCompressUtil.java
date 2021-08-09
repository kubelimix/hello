package com.limix.hello.util;

public class TelephoneCompressUtil {

    /**
     * 电话号码采用62进制压缩方法工具
     */
    private static char[] bit62 = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * @param args
     */
    public static void main(String[] args) {
        String telephoneNumberStr = "18957124766";
        long telephoneNumber = Long.parseLong(telephoneNumberStr);
        System.out.println(telephoneNumber);
        String abc = digit10to16(telephoneNumber);
        System.out.println("十进制数转换成六十二进制数结尾为:" + abc);
        System.out.println("六十二进制数转换成十进制数结尾为:" + digit16to10(abc));
    }

    /**
     * 十进制数(18957124766)转换成六十二进制数(kGWa8S) * 18957124766除以62等于305760076; 18957124766除以62取余54 * 305760076除以62等于4931614; 305760076除以62取余8 * 4931614除以62等于79542; 4931614除以62取余10 * 79542除以62等于1282; 79542除以62取余58 * 1282除以62等于20; 1282除以62取余42 * 20除以62等于0; 20除以62取余20 * @param source10digit * @return
     */
    public static String digit10to16(long source10digit) {
        StringBuffer sb = new StringBuffer();
        long b = divisionBy62(source10digit);
        int a = modBy62(source10digit);
        long temp = 0;
        System.out.println(source10digit + "除以62等于" + b + "; " + source10digit + "除以62取余" + a);
        while (b > 0) {
            sb.append(bit62[a]);
            temp = b;
            a = modBy62(b);
            b = divisionBy62(b);
            System.out.println(temp + "除以62等于" + b + "; " + temp + "除以62取余" + a);
        }
        sb.append(bit62[a]);
        String abc = sb.reverse().toString();
        return abc;
    }

    /**
     * 对十进制数除以62 * @param dividend * @return
     */
    private static long divisionBy62(long dividend) {
        return dividend / 62;
    }

    /**
     * 对十进制数除以62取余 * @param dividend * @return
     */
    private static int modBy62(long dividend) {
        return (int) (dividend % 62);
    }

    /**
     * 获取六十二进制数字符在该进制规范中的顺序编号 * @param a * @return
     */
    private static int digit16by10(char a) {
        for (int i = 0; i < bit62.length; i++) {
            if (a == bit62[i]) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 六十二进制数(kGWa8S)转换成十进制数结(18957124766) * 20(k)*62^5=18322656640 * 42(G)*62^4=620606112 * 58(W)*62^3=13823024 * 10(a)*62^2=38440 * 8(8)*62^1=496 * 54(S)*62^0=54 * @param str * @return
     */
    public static long digit16to10(String str) {
        if (str == null || "".equals(str.trim())) {
            return 0;
        } else {
            long a = 0;
            int strLen = str.length();
            for (int i = 0; i < strLen; i++) {
                System.out.println(digit16by10(str.charAt(i)) + "(" + str.charAt(i) + ")*" + "62^" + (strLen - i - 1) + "=" + (digit16by10(str.charAt(i)) * (long) Math.pow(62, strLen - i - 1)));
                a = a + digit16by10(str.charAt(i)) * (long) Math.pow(62, strLen - i - 1);
            }
            return a;
        }
    }
}
