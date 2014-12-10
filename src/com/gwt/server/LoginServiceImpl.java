package com.gwt.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.LoginService;
import com.gwt.client.SubmitHimnoService;

public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
	private static final String url = new URLSP().getUrl();
	
	@Override
	public String Login(String username, String pass)throws IllegalArgumentException {
	
		HttpClient httpclient = new DefaultHttpClient();				
		String complementoURL = url+"/users/getUsers";
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
		{			
			try {
				JSONObject a = jsonArray.getJSONObject(0);
				String n=a.getString("username");
				String p=a.getString("password");
				if(n.equals(username) && p.equals(pass))
					return a.getString("id");
				else
					return "";
			} catch (JSONException e) {			
				e.printStackTrace();
			}			
		}		
		return "";
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