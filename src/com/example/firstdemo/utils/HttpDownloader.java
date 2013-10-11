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
	 * ����URL�����ļ���ǰ��������ļ����е��������ı��������ķ���ֵ�����ļ����е����� 1.����һ��URL����
	 * 2.ͨ��URL���󣬴���һ��HttpURLConnection���� 3.�õ�InputStram 4.��InputStream���ж�ȡ����
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
			// ����һ��URL����
			url = new URL(urlStr);
			// ����һ��Http����
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(3000);
			urlConn.setReadTimeout(5000);
			// ʹ��IO����ȡ����
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
			Log.e(TAG, "δ�յ�����," + sb.toString());
		} finally {
			try {
				urlConn.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.e(TAG, "���ݣ�" + sb.toString());
		}
		return sb.toString();
	}

	public String postform(String urlStr, String payload) {
		HttpURLConnection urlConn = null;
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			// ����һ��URL����
			url = new URL(urlStr);
			// ����һ��Http����
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(3000);
			urlConn.setReadTimeout(5000);
			urlConn.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(
					urlConn.getOutputStream());
			out.write(payload);
			out.flush();
			out.close();
			// ʹ��IO����ȡ����
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
			Log.e(TAG, "�����յ�" + sb.toString());
		} catch (Exception e) {
			Log.e(TAG, "�����յ�catch" + sb.toString());
		} finally {

			try {
				urlConn.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.e(TAG, "�����յ�finally" + sb.toString());
		}
		return sb.toString();
	}

	/**
	 * ����URL�����ļ���ǰ��������ļ����е��������ı��������ķ���ֵ�����ļ����е����� 1.����һ��URL����
	 * 2.ͨ��URL���󣬴���һ��HttpURLConnection���� 3.�õ�InputStram 4.��InputStream���ж�ȡ����
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
			// ����һ��URL����
			url = new URL(urlStr);
			// ����һ��Http����
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(3000);
			urlConn.setReadTimeout(5000);
			// ʹ��IO����ȡ����
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
	 * �ú����������� -1�����������ļ�����; 0�����������ļ��ɹ� ;1�������ļ��Ѿ�����
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
	 * ����URL�õ�������
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