package com.gwt.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

public class JsonSermonData extends HttpServlet {

	private static final String url = new URLSP().getUrl();
	  
	@Override
	  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws ServletException, IOException {	  
		
			HttpClient httpclient = new DefaultHttpClient();
			String complementoURL = url+"/getSermones";
			JSONArray jsonArray = null;
			HttpGet httppost = new HttpGet(complementoURL);
			try 
			{
				HttpResponse response = httpclient.execute(httppost);
				String jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
				jsonArray = new JSONArray(jsonResult);
				PrintWriter out = resp.getWriter();
				out.println(jsonArray);
			    out.flush();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			} catch (JSONException e) {				
				e.printStackTrace();
			} 	
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
