package com.gwt.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.SubmitSermonService;

public class SubmitSermonServiceImpl extends RemoteServiceServlet implements SubmitSermonService{
	
	private static final String url = new URLSP().getUrl();
	
	@Override
	public String SubmitService(String name, String name_of_predicador,
			String serie, String Descripcion,Date fecha) throws IllegalArgumentException {	
			
		HttpClient httpclient = new DefaultHttpClient();
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String s = formatter.format(fecha);
		String complementoURL = url+"/addSermon/"+name.replaceAll(" ", "%20")+"/"+name_of_predicador.replaceAll(" ", "%20")+"/"+serie.replaceAll(" ", "%20")+"/"+Descripcion.replaceAll(" ", "%20")+"/"+s;
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
				return a.getString("id");	
			} catch (JSONException e) {			
				e.printStackTrace();
			}			
		}		
		return "Error! al guardar el sermon";
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
