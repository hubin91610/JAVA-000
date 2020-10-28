package com.hubin.httputil;

public class HttpClinetUtilsTest {



    public static void main (String[] agrs) {
        String  result = null;
        try {
            result = HttpClientUtils.get("http://localhost:8088/api/hello","UTF-8",null,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
