package com.example.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.util.Enumeration;


public class ServerInfoUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerInfoUtils.class);

    private ServerInfoUtils() {
        //default
    }


    public static String getServerIp() {
        try {
            final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                final NetworkInterface cur = interfaces.nextElement();
                if (cur.isLoopback()||cur.isVirtual()) {
                    continue;
                }

                for (final InterfaceAddress addr : cur.getInterfaceAddresses()) {
                    final InetAddress inetAddress = addr.getAddress();

                    if (inetAddress instanceof Inet4Address && inetAddress.getHostAddress().startsWith("192.168.")) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            LOGGER.error("获取服务器IP 地址错误",e);
        }
        return "没有找到IP地址";
    }

    public static void main(String[] args) {
        String serverIp = getServerIp();
        LOGGER.debug("服务器IP 地址:{}",serverIp);
        String ipLast = serverIp.substring(serverIp.lastIndexOf('.')+1);
        LOGGER.debug("ipLast:{}",ipLast);
    }
}
