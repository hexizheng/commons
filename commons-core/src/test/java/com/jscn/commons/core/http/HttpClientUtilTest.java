/**
 * JSCN APPLIANCE CHAINS.
 * Copyright (c) 2012-2012 All Rights Reserved.
 */
package com.jscn.commons.core.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * httpclient test
 * 
 * @author 袁兵 2012-2-20
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = { "/application-test.xml" })
public class HttpClientUtilTest {
    @BeforeClass
    public static void setup() {
    }

    /**
     * 
     */
    //@Test
    public void testGet() {
        for (int i = 0; i < 10; i++) {
            System.out.println(HttpClientUtil.get2Bytes("http://192.168.100.10/trac/tvmall/wiki"));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testAsyncGet() {
        for (int i = 0; i < 10; i++) {
            HttpAsyncClientUtil.get("http://192.168.100.10/trac/tvmall/wiki",
                    new FutureCallback<HttpResponse>() {

                        @Override
                        public void completed(HttpResponse result) {
                            // TODO Auto-generated method stub
                            System.out.println(result);
                        }

                        @Override
                        public void failed(Exception ex) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void cancelled() {
                            // TODO Auto-generated method stub

                        }
                    });
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    //@Test
    public void testGetUri(){
        String uri = "http://localhost/cart/?ad=汉子&bc=dasjfa&na=顶顶顶";
        HttpGet get = new HttpGet(uri);
        get.addHeader("contentType", "text/html;charset=gbk");
        System.out.println(get.getURI());
        
        System.out.println(System.getProperty("file.encoding"));
    }
}
