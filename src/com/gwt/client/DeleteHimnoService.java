package com.gwt.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("DeleteHimno")
public interface DeleteHimnoService extends RemoteService{
	String DeleteService(String id) throws IllegalArgumentException;
}
