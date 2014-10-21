package com.gwt.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UploadDropbox")
public interface UploadDropboxService extends RemoteService{
	String SubmitService(String id,String path,String name,String HoS) throws IllegalArgumentException;

}
