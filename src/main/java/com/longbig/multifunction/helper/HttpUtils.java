//package com.longbig.multifunction.helper;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.http.Header;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpHead;
//import org.apache.http.client.methods.RequestBuilder;
//import org.apache.http.client.utils.URIBuilder;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
//import java.net.URI;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// * @author Wiker Yong Email:<a href="mailto:wikeryong@gmail.com">wikeryong@gmail.com</a>
// * @date 2013-11-8 下午7:22:43
// * @version 1.0-SNAPSHOT
// */
//@Slf4j
//public class HttpUtils {
//    /**
//     * 向指定URL发送GET方法的请求
//     *
//     * @param url
//     *            发送请求的URL
//     * @param param
//     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
//     * @return URL 所代表远程资源的响应结果
//     */
//    public static String sendGet(String url, String param) {
//        String result = "";
//        BufferedReader in = null;
//        try {
//            String urlNameString = url + "?" + param;
//            URL realUrl = new URL(urlNameString);
//            // 打开和URL之间的连接
//            URLConnection connection = realUrl.openConnection();
//            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E217 MicroMessenger/6.8.0(0x16080000) NetType/WIFI Language/en Branch/Br_trunk MiniProgramEnv/Mac");
//            connection.setRequestProperty("Accept-Encoding", "br, gzip, deflate");
//            connection.setRequestProperty("zftsl", "be7fdf6cd771ef17e03b59a9b5a43214");
//            connection.setRequestProperty("Cookie", "ASP.NET_SessionId=mseaorwj55bqqnentv41hgtp");
//            connection.setRequestProperty("Referer", "https://servicewechat.com/wx2c7f0f3c30d99445/86/page-frame.html");
//            connection.setRequestProperty("Cache-Control", "no-cache");
//            connection.setRequestProperty("Content-Type", "application/json");
//
//            // 建立实际的连接
//            connection.connect();
//            // 获取所有响应头字段
//            Map<String, List<String>> map = connection.getHeaderFields();
//            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                log.info(key + "--->" + map.get(key));
//            }
//
//            // 定义 BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//        } catch (Exception e) {
//            log.info("发送GET请求出现异常！" + e);
//            e.printStackTrace();
//        }
//        // 使用finally块来关闭输入流
//        finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
//        return result;
//    }
//
//
//    public  static String get(String url){
//        org.apache.http.client.HttpClient httpClient= HttpClients.createDefault();
//        HttpResponse httpResponse = null;
//        String result="";
//        HttpGet httpGet=new HttpGet(url);
//        httpGet.addHeader("accept", "*/*");
//        httpGet.addHeader("connection", "Keep-Alive");
//        httpGet.addHeader("Accept-Encoding", "br, gzip, deflate");
//        httpGet.addHeader("zftsl", "be7fdf6cd771ef17e03b59a9b5a43214");
//        httpGet.addHeader("Cookie", "ASP.NET_SessionId=mseaorwj55bqqnentv41hgtp");
//        httpGet.addHeader("Cache-Control", "no-cache");
//        httpGet.addHeader("Content-Type", "application/json");
//        httpGet.addHeader("Referer", "https://servicewechat.com/wx2c7f0f3c30d99445/86/page-frame.html");
//        httpGet.addHeader("user-agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E217 MicroMessenger/6.8.0(0x16080000) NetType/WIFI Language/en Branch/Br_trunk MiniProgramEnv/Mac");
//
//        try{
//
//            httpResponse=httpClient.execute(httpGet);
//            HttpEntity httpEntity=httpResponse.getEntity();
//            if(httpEntity!=null){
//                result= EntityUtils.toString(httpEntity,"utf-8");
//
//            }
//
//        }catch (IOException e){
//            e.printStackTrace();
//
//        }
//        return  result;
//    }
//
//
//    /**
//     * 向指定 URL 发送POST方法的请求
//     *
//     * @param url
//     *            发送请求的 URL
//     * @param param
//     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
//     * @return 所代表远程资源的响应结果
//     */
////    public static String sendPost(String url, List<String> param) {
////        PrintWriter out = null;
////        BufferedReader in = null;
////        String result = "";
////        try {
////            URL realUrl = new URL(url);
////            // 打开和URL之间的连接
////            URLConnection conn = realUrl.openConnection();
////            // 设置通用的请求属性
////            conn.setRequestProperty("accept", "*/*");
////            conn.setRequestProperty("connection", "Keep-Alive");
////            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
////            conn.setRequestProperty("Content-Type", "application/json");
////            // 发送POST请求必须设置如下两行
////            conn.setDoOutput(true);
////            conn.setDoInput(true);
////            // 获取URLConnection对象对应的输出流
////            out = new PrintWriter(conn.getOutputStream());
////            // 发送请求参数
////            out.append(JSONUtils.toJSONString(param));
////            // flush输出流的缓冲
////            out.flush();
////            // 定义BufferedReader输入流来读取URL的响应
////            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
////            String line;
////            while ((line = in.readLine()) != null) {
////                result += line;
////            }
////        } catch (Exception e) {
////            System.out.println("发送 POST 请求出现异常！" + e);
////            e.printStackTrace();
////        }
////        // 使用finally块来关闭输出流、输入流
////        finally {
////            try {
////                if (out != null) {
////                    out.close();
////                }
////                if (in != null) {
////                    in.close();
////                }
////            } catch (IOException ex) {
////                ex.printStackTrace();
////            }
////        }
////        return result;
////    }
//    //name1=value1&name2=value2
////    public static String param(JSONObject json){
////        String str = "";
////        Map<String, Object> map = JSONUtils.toHashMap(json);
////        Set set = map.keySet();
////        for(Iterator iter = set.iterator(); iter.hasNext();)
////        {
////            String key = (String)iter.next();
////            String value = (String)map.get(key).toString();
////            str+= key+"="+value+"&";
////        }
////        if(StringUtils.isNotBlank(str)){
////            str = str.substring(0, str.length()-1);
////        }
////        return str;
////    }
//
//}