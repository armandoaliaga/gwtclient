package com.gwt.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWriteMode;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.gwt.client.UploadDropboxService;

public class UploadDropboxServiceImpl extends RemoteServiceServlet implements UploadDropboxService {
	private static final String url = new URLSP().getUrl();
	
	@Override
	public String SubmitService(String id, String path, String name, String HoS)
			throws IllegalArgumentException {
		 // Get your app key and secret from the Dropbox developers website.
        final String APP_KEY = "4rng2d5ohq7m81y";
        final String APP_SECRET = "clzga4t5xy79u20";

        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

        DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());        
        /*DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        
     // Have the user sign in and authorize your app.
        String authorizeUrl = webAuth.start();
        System.out.println("1. Go to: " + authorizeUrl);
        System.out.println("2. Click \"Allow\" (you might have to log in first)");
        System.out.println("3. Copy the authorization code.");
        String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();

        // This will fail if the user enters an invalid authorization code.
        DbxAuthFinish authFinish = webAuth.finish(code);*/
        String accessToken = "FWVemrNp-3EAAAAAAAAAHRUEzQP-HRfLwcXonyzA7QvvYGG6g68AGJai2LYZrw22";//authFinish.accessToken;
       
        DbxClient client = new DbxClient(config, accessToken);

        try {
			System.out.println("Linked account: " + client.getAccountInfo().displayName);
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
        File inputFile = new File(path);
        FileInputStream inputStream=null;
		try {
			inputStream = new FileInputStream(inputFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fname="";
        try {        	
            DbxEntry.File uploadedFile=null;
			try {				
				uploadedFile = client.uploadFile("/"+name,
				    DbxWriteMode.add(), inputFile.length(), inputStream);
			} catch (DbxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
            System.out.println("Uploaded: " + uploadedFile.toString());      
            fname=uploadedFile.name;        
        } finally {
            try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
      /*  String ruta="";
		try {
			ruta = client.createShareableUrl("/"+name);
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(ruta);
    	String rutaa[] =ruta.split("/s/");
    	String surl=rutaa[1];  
    	surl=surl.replaceAll("/", "%2F");
    	surl=surl.replace("?", "%3F");
    	surl=surl.replaceAll("=", "%3D");
    	surl=surl.substring(0,surl.length()-1)+"1";
    	*/        
        fname=fname.replaceAll(" ", "%20");
    	String resp=SendSpShareableUrl(id,fname);
     
        return "siii XD";
	}

	private String SendSpShareableUrl(String id,String filename) {

		HttpClient httpclient = new DefaultHttpClient();		
	    String complementoURL = url+"/seturlSermon/"+id+"/"+filename;
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
			return "Audio del Sermon ya disponible";
		else
			return "Error! al subir el audio del archivo";
		
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
