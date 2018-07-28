import com.qianfeng.etl.util.IpParserUtil;
import com.qianfeng.etl.util.ip.IPSeeker;

import java.util.List;

public class IpTest {
    public static void main(String[] args) throws Exception{
      /* String country = IPSeeker.getInstance().getCountry("59.67.194.5");
       int index = country.indexOf("уюЂ");
       System.out.println(country.substring(0,index+1));
       System.out.println(IPSeeker.getInstance().getCountry("192.168.216.111"));
        */
//        System.out.println(IPSeeker.getInstance().getCountry("192.168.112.18"));
//        List<String> ips = IPSeeker.getInstance().getAllIp();
//        for (String ip:ips) {
//            System.out.println(ip+"==="+IPSeeker.getInstance().getCountry(ip));
//        }
        List<String> ips = IPSeeker.getInstance().getAllIp();
        for (String ip:ips) {
//            System.out.println(ip+"=="+ new IpParserUtil().parserIp(ip));
            System.out.println(ip+"===="+new IpParserUtil().parserIp1("http://ip.taobao.com/service/getIpInfo.php?ip="+ip,"utf-8"));

        }


    }
}
