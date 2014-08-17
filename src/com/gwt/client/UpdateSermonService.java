package com.gwt.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UpdateSermon")
public interface UpdateSermonService extends RemoteService{
	String UpdateService(int id,String name, String name_of_predicador,String serie, String Descripcion,Date fecha) throws IllegalArgumentException;
}