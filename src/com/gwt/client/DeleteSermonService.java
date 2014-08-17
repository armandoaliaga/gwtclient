package com.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("DeleteSermon")
public interface DeleteSermonService extends RemoteService{
	String DeleteService(String id) throws IllegalArgumentException;
}