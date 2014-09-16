package com.gwt.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("SubmitHimno")
public interface SubmitHimnoService extends RemoteService{
	String SubmitService(String numero, String name,ArrayList<String> lyrics) throws IllegalArgumentException;
}
