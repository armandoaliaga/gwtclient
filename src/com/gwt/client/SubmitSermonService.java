package com.gwt.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("SubmitSermon")
public interface SubmitSermonService extends RemoteService{
	String SubmitService(String name, String name_of_predicador,String serie, String Descripcion,Date fecha) throws IllegalArgumentException;
}
