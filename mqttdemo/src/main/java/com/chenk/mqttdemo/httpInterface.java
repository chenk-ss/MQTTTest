package com.chenk.mqttdemo;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.google.gson.Gson;

import okhttp3.*;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class httpInterface {


	public static void main(String[] args){
		String serverSessionId =  a();
		System.out.println("a: ==="+serverSessionId);
		String code = b(serverSessionId);
		System.out.println("b: ==="+code);
		String fjRestSessionId = c(code);
		System.out.println("c: ==="+fjRestSessionId);
		d(fjRestSessionId);
	}

	public static void d(String fjRestSessionId) {
		String url = "https://tf.railshj.cn:9004/app/fjsczh-rest/api/leaveapply?fjRestSessionId="+fjRestSessionId;
		Gson gson = new Gson();
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");

		OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
		mBuilder.sslSocketFactory(createSSLSocketFactory());
		mBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
		OkHttpClient okHttpClient = mBuilder.build();


		Map<String, String> map = new HashMap<String, String>();
		//map.put("type", "\\u5a5a\\u5047");
		map.put("type", "婚假");
		map.put("content", "ppppp");
		map.put("startTime", "2020-08-16 00:00:00");
		map.put("endTime", "2020-08-16 01:00:00");
		map.put("duration", "1");
		map.put("unit", "天");
		//map.put("unit", "\\u5929");

		String param= gson.toJson(map);

		RequestBody requestBody = RequestBody.create(JSON, param);

		final Request request = new Request.Builder()
				.url(url)
				.post(requestBody)
				.build();

		Call call = okHttpClient.newCall(request);
		try {
			Response response = call.execute();

			System.out.println("d==============: "+new String(response.body().bytes(),"UTF-8"));

		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String c(String code) {
		String fjRestSessionId = "";
		//String url = "https://tf.railshj.cn:9004/app/fjsczh-rest/api/authentication/restoauth?code="+code;
		String url = "https://tf.railshj.cn:9004/app/fjsczh-rest/api/authentication/oauthLogin?code="+code;

		OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
		mBuilder.sslSocketFactory(createSSLSocketFactory());
		mBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
		OkHttpClient okHttpClient = mBuilder.build();

		//OkHttpClient okHttpClient = new OkHttpClient();
		final Request request = new Request.Builder()
				.url(url)
				.get()//默认就是GET请求，可以不写
				.build();

		Call call = okHttpClient.newCall(request);
		try {
			Response response = call.execute();


			System.out.println("c==============: "+new String(response.body().bytes(),"UTF-8"));

			Headers headers = response.headers();


			List<String> cookies = headers.values("Set-Cookie");
			for(String cookie : cookies){
				System.out.println("onResponse: ==="+cookie);
				if(cookie.contains("fjRestSessionId")){
					//
					String tmp = cookie.split(";")[0];
					//System.out.println(tmp.split("=")[1]);
					fjRestSessionId = tmp.split("=")[1];
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return fjRestSessionId;
	}

	public static String b(String serverSessionId){
		String code="";
		String url = "https://tf.railshj.cn:9004/app/fjsczh-server/oauth!authorize2.action?clientId="+serverSessionId+"&responseType=code&serverSessionId="+serverSessionId;

		OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
		mBuilder.sslSocketFactory(createSSLSocketFactory());
		mBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
		OkHttpClient okHttpClient = mBuilder.build();

		//OkHttpClient okHttpClient = new OkHttpClient();
		final Request request = new Request.Builder()
				.url(url)
				.get()//默认就是GET请求，可以不写
				.build();

		Call call = okHttpClient.newCall(request);
		try {
			Response response = call.execute();
			code = new String(response.body().bytes());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return code;
	}


	public static String a(){
		String serverSessionId = "";
		String url = "https://tf.railshj.cn:9004/app/fjsczh-server/oauth!loginapp.action?username=admin&password=admin";

		OkHttpClient.Builder mBuilder = new OkHttpClient.Builder();
		mBuilder.sslSocketFactory(createSSLSocketFactory());
		mBuilder.hostnameVerifier(new TrustAllHostnameVerifier());
		OkHttpClient okHttpClient = mBuilder.build();

		//OkHttpClient okHttpClient = new OkHttpClient();
		final Request request = new Request.Builder()
				.url(url)
				.get()//默认就是GET请求，可以不写
				.build();

		Call call = okHttpClient.newCall(request);
		try {
			Response response = call.execute();
			Headers headers = response.headers();


			List<String> cookies = headers.values("Set-Cookie");
			for(String cookie : cookies){
				if(cookie.contains("serverSessionId")){
					//System.out.println("onResponse: ==="+cookie);
					String tmp = cookie.split(";")[0];
					//System.out.println(tmp.split("=")[1]);
					serverSessionId = tmp.split("=")[1];
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return serverSessionId;
	}


	//取消验证
	private static SSLSocketFactory createSSLSocketFactory() {
		SSLSocketFactory ssfFactory = null;

		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null,  new TrustManager[] { new TrustAllCerts() }, new SecureRandom());

			ssfFactory = sc.getSocketFactory();
		} catch (Exception e) {
		}

		return ssfFactory;
	}


	private static class TrustAllCerts implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

		@Override
		public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
	}

	private static class TrustAllHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
}
