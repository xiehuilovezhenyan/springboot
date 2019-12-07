package com.xiehui.common.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

public class IpUtil {

    public static final List<String> hostIpList = new ArrayList<>();
    private static String innerIp = "127.0.0.1";
    private static String outerIp = "";
    static {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress nextElement = inetAddresses.nextElement();
                    String hostAddress = nextElement.getHostAddress();
                    if (isIp(hostAddress)) {
                        hostIpList.add(hostAddress);
                        if (isInner(hostAddress)) {
                            innerIp = hostAddress;
                        }
                        if (isOuter(hostAddress)) {
                            outerIp = hostAddress;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static boolean isIp(String ip) {
        Pattern pattern = Pattern.compile(
            "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]" + "|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$");
        return pattern.matcher(ip).matches();
    }

    public static boolean isOuter(String ip) {
        return !isInner(ip) && !ip.equals("127.0.0.1");
    }

    public static boolean isInner(String ip) {
        long ipNum = ipToLong(ip);
        /**
         * 私有IP：A类 10.0.0.0-10.255.255.255 B类 172.16.0.0-172.31.255.255 C类 192.168.0.0-192.168.255.255 当然，还有127这个网段是环回地址
         **/
        long aBegin = ipToLong("10.0.0.0");
        long aEnd = ipToLong("10.255.255.255");
        long bBegin = ipToLong("172.16.0.0");
        long bEnd = ipToLong("172.31.255.255");
        long cBegin = ipToLong("192.168.0.0");
        long cEnd = ipToLong("192.168.255.255");
        boolean isInnerIp =
            isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd);
        return isInnerIp;
    }

    private static boolean isInner(long userIp, long begin, long end) {
        return (userIp >= begin) && (userIp <= end);
    }

    public static long getInnerLongIp() {
        return ipToLong(innerIp);
    }

    public static String getInnerIp() {
        return innerIp;
    }

    public static long getOuterLongIp() {
        if (isIp(outerIp)) {
            return ipToLong(outerIp);
        } else {
            return -1;
        }
    }

    public static String getOuterIp() {
        return outerIp;
    }

    public static long ipToLong(String strIp) {
        long[] ip = new long[4];
        // 先找到IP地址字符串中.的位置
        int position1 = strIp.indexOf(".");
        int position2 = strIp.indexOf(".", position1 + 1);
        int position3 = strIp.indexOf(".", position2 + 1);
        // 将每个.之间的字符串转换成整型
        ip[0] = Long.parseLong(strIp.substring(0, position1));
        ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
        ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
        ip[3] = Long.parseLong(strIp.substring(position3 + 1));
        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    public static String longToIp(long longIp) {
        StringBuffer sb = new StringBuffer("");
        // 直接右移24位
        sb.append(String.valueOf((longIp >>> 24)));
        sb.append(".");
        // 将高8位置0，然后右移16位
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));
        sb.append(".");
        // 将高16位置0，然后右移8位
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));
        sb.append(".");
        // 将高24位置0
        sb.append(String.valueOf((longIp & 0x000000FF)));
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("getInnerIp=" + getInnerIp());
        System.out.println("getInnerLongIp=" + getInnerLongIp());
        System.out.println("getInnerIp=" + longToIp(getInnerLongIp()));

        System.out.println("getOuterIp=" + getOuterIp());
        System.out.println("getOuterLongIp=" + getOuterLongIp());
    }
}
