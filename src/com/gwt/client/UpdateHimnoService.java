package com.gwt.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UpdateHimno")
public interface UpdateHimnoService extends RemoteService{
	String UpdateService(int id,String numero, String name,ArrayList<String> lyrics) throws IllegalArgumentException;
}
