package com.qianfeng.etl.util;

import com.qianfeng.common.EventLogsConstant;
import org.apache.commons.lang.StringUtils;

import java.net.URLDecoder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogUtil {
    public Map parserLog(String log) throws Exception{
        //定义一个map
        Map<String,String> info = new ConcurrentHashMap<>();
        if (StringUtils.isNotEmpty(log)){
            String[] fields = log.split(EventLogsConstant.COLUMN_SEPARTOR);
            if (fields.length == 4){
                //向info赋值
                info.put(EventLogsConstant.EVENT_COLUMN_NAME_IP,fields[0]);
                info.put(EventLogsConstant.EVENT_COLUMN_NAME_SERVER_TIME,
                        fields[1].replaceAll(".",""));
                //判断是否有参数列表
                int index = fields[3].indexOf("?");
                if (index > 0){
                    String params = fields[3].substring(index+1);
                    handleParams(info,params);
                    //解析ip
                    handleIp(info);
                    //解析userAgent
                    handleUserAgent(info);
                }
            }

        }

        return info;
    }

    /**
     * 解析userAgent
     * @param info
     */
    private void handleUserAgent(Map<String, String> info) {
        if(info.containsKey(EventLogsConstant.EVENT_COLUMN_NAME_USERAGENT)){
            UserAgentUtil.UserAgentInfo userAgent = new UserAgentUtil()
                    .parserUserAgent(info.get(EventLogsConstant.EVENT_COLUMN_NAME_USERAGENT));
            if(userAgent != null){
                info.put(EventLogsConstant.EVENT_COLUMN_NAME_BROWSER_NAME,userAgent.getBrowserName());
                info.put(EventLogsConstant.EVENT_COLUMN_NAME_BROWSER_VERSION,userAgent.getBrowserVersion());
                info.put(EventLogsConstant.EVENT_COLUMN_NAME_OS_NAME,userAgent.getOsName());
                info.put(EventLogsConstant.EVENT_COLUMN_NAME_OS_VERSION,userAgent.getOsVersion());
            }
        }
    }

    /**
     * 解析ip
     * @param info
     */
    private void handleIp(Map<String, String> info) {
        if (info.containsKey(EventLogsConstant.EVENT_COLUMN_NAME_IP)){
            IpParserUtil.RegionInfo region = new IpParserUtil().parserIp(info
                    .get(EventLogsConstant.EVENT_COLUMN_NAME_IP));
            if (region != null){
                info.put(EventLogsConstant.EVENT_COLUMN_NAME_COUNTRY,region.getCountry());
                info.put(EventLogsConstant.EVENT_COLUMN_NAME_PROVINCE,region.getProvince());
                info.put(EventLogsConstant.EVENT_COLUMN_NAME_CITY,region.getCity());
            }
        }
    }

    /**
     * 将参数列表的k-v存储到info中
     * @param info
     * @param params
     */
    private void handleParams(Map<String, String> info, String params) throws Exception{
        if (StringUtils.isNotEmpty(params)){
            String[] paramkvs = params.split("&");
            for (String kv:paramkvs) {
                String[] kvs = kv.split("=");
                String k = kvs[0];
                String v = URLDecoder.decode(kvs[1],"utf-8");
                if (StringUtils.isNotEmpty(kvs[0])){
                    info.put(k,v);
                }
            }
        }
    }
}
