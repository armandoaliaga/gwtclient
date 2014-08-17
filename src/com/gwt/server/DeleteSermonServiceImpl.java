package com.gwt.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.DeleteSermonService;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class DeleteSermonServiceImpl extends RemoteServiceServlet implements DeleteSermonService{

	private static final String url = new URLSP().getUrl();
	
	@Override
	public String DeleteService(String id) throws IllegalArgumentException {	
				
		HttpClient httpclient = new DefaultHttpClient();				
		String complementoURL = url+"/deleteSermon/"+id;
		HttpGet httppost = new HttpGet(complementoURL);	
		try 
		{
			HttpResponse response = httpclient.execute(httppost);			
			return "Sermon eliminado con exito!";
		} 
		catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 		
		return "Error al elminar el sermon!";
	}

}
