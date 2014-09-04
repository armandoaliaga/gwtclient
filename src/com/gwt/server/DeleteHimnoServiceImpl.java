package com.gwt.server;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.DeleteHimnoService;
import com.gwt.client.DeleteSermonService;

public class DeleteHimnoServiceImpl extends RemoteServiceServlet implements DeleteHimnoService {
	private static final String url = new URLSP().getUrl();
	
	@Override
	public String DeleteService(String id) throws IllegalArgumentException {
		HttpClient httpclient = new DefaultHttpClient();				
		String complementoURL = url+"/himnos/deleteHimno/"+id;
		HttpGet httppost = new HttpGet(complementoURL);	
		try 
		{
			HttpResponse response = httpclient.execute(httppost);			
			return "Himno eliminado con exito!";
		} 
		catch (ClientProtocolException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} 		
		return "Error al elminar el Himno!";
	}

}
