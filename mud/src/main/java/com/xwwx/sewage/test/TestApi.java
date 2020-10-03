package com.xwwx.sewage.test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.xwwx.sewage.bean.PickList;
public class TestApi {
	/**
	 * 以form表单形式提交数据，发送post请求
	 * @explain 
	 *   1.请求头：httppost.setHeader("Content-Type","application/x-www-form-urlencoded")
	 *   2.提交的数据格式：key1=value1&key2=value2...
	 * @param url 请求地址
	 * @param paramsMap 具体数据
	 * @return 服务器返回数据
	 */
	public static String httpPostWithForm(String url,Map<String, String> paramsMap){
	    // 用于接收返回的结果
	    String resultData ="";
	     try {
	            HttpPost post = new HttpPost(url);
	            List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
	            // 迭代Map-->取出key,value放到BasicNameValuePair对象中-->添加到list中
	            for (String key : paramsMap.keySet()) {
	                pairList.add(new BasicNameValuePair(key, paramsMap.get(key)));
	            }
	            UrlEncodedFormEntity uefe = new UrlEncodedFormEntity(pairList, "utf-8");
	            post.setEntity(uefe); 
	            // 创建一个http客户端
	            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
	            // 发送post请求
	            HttpResponse response = httpClient.execute(post);
	            
	            // 状态码为：200
	            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	                // 返回数据：
	                resultData = EntityUtils.toString(response.getEntity(),"UTF-8");
	            }else{
	                throw new RuntimeException("接口连接失败！");
	            }
	        } catch (Exception e) {
	            throw new RuntimeException("接口连接失败！");
	        }
	     return resultData;
	}
	public static void main(String[] args) {	
		//MyThread mt = new MyThread();
		//mt.start();
		
		PickList pickList1 = new PickList();
		PickList pickList2 = new PickList();
		System.out.println(pickList1.equals(pickList2));
		
		MyThread m1 = new MyThread();
		MyThread m2 = new MyThread();
		System.out.println(m1.equals(m1));
		System.out.println(m1.hashCode() + " " + m1.hashCode());
		
		try {
			throw new B("b");
		}catch (A e) {
			System.out.println("A");
		}catch (Exception e) {
			System.out.println("e");
		}
		
		System.out.println(Math.round(-1.5));
		
		char a1 = 100;
		byte b1 = -128;
		System.out.println("aaaa--------"+a1);
		
		String str1="aaa",str2 = "aaa";
		String str3 = "aaa";
		String str4 = new String("aaa");
		String str5 = str4;
		System.out.println(str1 == str2);
		System.out.println(str1 == str3);
		System.out.println(str1 == str4);
		System.out.println(str5 == str4);
		int s1 = 3;
		double s2 = 0.1;
		
		System.out.println(s1*s2);
		
		System.out.println(Math.round(-1.5));
	}
	
	static class MyThread extends Thread{
		@Override
		public void run() {
			int sum = 0;
			while(true) {
				try {
					Thread.sleep(500);
					sum++;
					String url = "http://127.0.0.1:8080/mud/api/setcarweighdata";
					Map<String,String> paramsMap = new HashMap<String,String>();
					String str = "{\"accesskeyid\":\"20:72:0d:39:2c:62\",\"carnum\":\"辽A123456\",\"datatype\":0,\"consignee\":\"收货人\",\"consignor\":\"发货人\",\"goodstype\":\"测试货物\",\"goodsweight\":123,\"manualflag\":0,\"rgoodsdept\":\"收货部门\",\"sgoodsdept\":\"发货部门\",\"sgoodstime\":\"2019-09-12 10:59:33\",\"snum\":\""+sum+"\"}";
					paramsMap.put("param", str);	
					httpPostWithForm(url,paramsMap);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class A extends Exception{
		public A(String str){
			
		}
		public final void AA(String str) {
			
		}
		public void AA(int str) {
			
		}
		public static void AAA() {
			
		}
	}
	static class B extends A{

		public B(String str) {
			super(str);
		}

	}
}
