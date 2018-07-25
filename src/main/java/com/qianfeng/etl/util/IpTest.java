package com.qianfeng.etl.util;

import com.qianfeng.etl.util.ip.IPSeeker;

import java.util.List;

public class IpTest {
    public static void main(String[] args) throws Exception{
        System.out.println(IPSeeker.getInstance().getCountry("192.168.112.18"));
        List<String> ips = IPSeeker.getInstance().getAllIp();
        for (String ip:ips) {
            System.out.println(ip+"==="+IPSeeker.getInstance().getCountry(ip));
        }
    }
}
