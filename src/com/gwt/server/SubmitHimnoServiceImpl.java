package com.gwt.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.SubmitHimnoService;
import com.gwt.client.SubmitSermonService;

public class SubmitHimnoServiceImpl extends RemoteServiceServlet implements SubmitHimnoService {
	private static final String url = new URLSP().getUrl();
	
	@Override
	public String SubmitService(String numero, String name,
			ArrayList<String> lyrics) throws IllegalArgumentException {

		HttpClient httpclient = new DefaultHttpClient();		
		String s_lyrics="";
		for (int i = 0; i < lyrics.size(); i++) {
			s_lyrics=s_lyrics+lyrics.get(i).replace("\n", "%3C").replace("\r", "%3C")+"%3E";
		}
		s_lyrics=s_lyrics.replaceAll("/", "%2F");
		s_lyrics=s_lyrics.replaceAll(";", "%3B");		
		String complementoURL = url+"/himnos/addHimno/"+numero.replaceAll(" ", "%20")+"/"+name.replaceAll(" ", "%20")+"/"+s_lyrics.replaceAll(" ", "%20");
		JSONArray jsonArray = null;
		HttpGet httppost = new HttpGet(complementoURL);
		try 
		{
			HttpResponse response = httpclient.execute(httppost);
			String jsonResult = inputStreamToString(
					response.getEntity().getContent()).toString();

			jsonArray = new JSONArray(jsonResult);		
		} 
		catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 
		catch (JSONException e) 
		{
			e.printStackTrace();
		}
		if(jsonArray.length()!=0)
			return "Himno guardado con exito!";
		else
			return "Error! al guardar el himno";
	}
	private StringBuilder inputStreamToString(InputStream is) {
		String rLine = "";
		StringBuilder answer = new StringBuilder();

		InputStreamReader isr = new InputStreamReader(is);

		BufferedReader rd = new BufferedReader(isr);

		try 
		{
			while ((rLine = rd.readLine()) != null) 
			{
				answer.append(rLine);
			}
		}

		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return answer;
	}
}
