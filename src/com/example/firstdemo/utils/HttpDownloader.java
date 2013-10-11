package com.example.firstdemo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class HttpDownloader {
	private URL url = null;
	private static final String TAG = "HttpDownloader";

	/**
	 * 根据URL下载文件，前提是这个文件当中的内容是文本，函数的返回值就是文件当中的内容 1.创建一个URL对象
	 * 2.通过URL对象，创建一个HttpURLConnection对象 3.得到InputStram 4.从InputStream当中读取数据
	 * 
	 * @param urlStr
	 * @return
	 */
	public String download(String urlStr) {
		HttpURLConnection urlConn = null;
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		String payload = null;
		try {
			// 创建一个URL对象
			url = new URL(urlStr);
			// 创建一个Http连接
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(3000);
			urlConn.setReadTimeout(5000);
			// 使用IO流读取数据
			/*
			 * buffer = new BufferedReader(new InputStreamReader(urlConn
			 * .getInputStream(),"GBK"));
			 */
			int length = urlConn.getInputStream().available();
			buffer = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream(), "UTF-8"));
			// .getInputStream(),"GBK"));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}

		} catch (Exception e) {
			Log.e(TAG, "未收到数据," + sb.toString());
		} finally {
			try {
				urlConn.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.e(TAG, "数据：" + sb.toString());
		}
		return sb.toString();
	}

	public String postform(String urlStr, String payload) {
		HttpURLConnection urlConn = null;
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			// 创建一个URL对象
			url = new URL(urlStr);
			// 创建一个Http连接
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(3000);
			urlConn.setReadTimeout(5000);
			urlConn.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(
					urlConn.getOutputStream());
			out.write(payload);
			out.flush();
			out.close();
			// 使用IO流读取数据
			/*
			 * buffer = new BufferedReader(new InputStreamReader(urlConn
			 * .getInputStream(),"GBK"));
			 */
			buffer = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream(), "UTF-8"));
			// .getInputStream(),"GBK"));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
			Log.e(TAG, "反馈收到" + sb.toString());
		} catch (Exception e) {
			Log.e(TAG, "反馈收到catch" + sb.toString());
		} finally {

			try {
				urlConn.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.e(TAG, "反馈收到finally" + sb.toString());
		}
		return sb.toString();
	}

	/**
	 * 根据URL下载文件，前提是这个文件当中的内容是文本，函数的返回值就是文件当中的内容 1.创建一个URL对象
	 * 2.通过URL对象，创建一个HttpURLConnection对象 3.得到InputStram 4.从InputStream当中读取数据
	 * 
	 * @param urlStr
	 * @return
	 */
	public String downloadlog(String urlStr) {
		HttpURLConnection urlConn = null;
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			// 创建一个URL对象
			url = new URL(urlStr);
			// 创建一个Http连接
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(3000);
			urlConn.setReadTimeout(5000);
			// 使用IO流读取数据
			buffer = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream(), "UTF-8"));
			// .getInputStream(),"GBK"));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (FileNotFoundException e) {

		} catch (Exception e) {
			sb.append("404");
		} finally {
			try {
				urlConn.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 该函数返回整形 -1：代表下载文件出错; 0：代表下载文件成功 ;1：代表文件已经存在
	 */
	public int downFile(String urlStr, String path, String fileName) {
		InputStream inputStream = null;
		try {
			FileUtils fileUtils = new FileUtils();
			if (fileUtils.isFileExist(fileName, path)) {
				return 1;
			} else {
				inputStream = getInputStreamFromUrl(urlStr);
				File resultFile = fileUtils.write2SDFromInput(path, fileName,
						inputStream);
				if (resultFile == null) {
					return -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 根据URL得到输入流
	 * 
	 * @param urlStr
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public InputStream getInputStreamFromUrl(String urlStr)
			throws MalformedURLException, IOException {
		url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}

	public static String getPublic_key3() {
		return "l/VXz2H3E/CS5e7Lj7PbNVc9HUB1fpaJ5Qeklv1ExO/nqaebOWGAfBSVcMcPCHDCs3+BAgMBAAEC";
	}
}
