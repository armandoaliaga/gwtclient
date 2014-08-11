package com.gwt.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SubmitSermonServiceAsync {

	void SubmitService(String name, String name_of_predicador, String serie,
			String Descripcion, Date fecha, AsyncCallback<String> callback);

}
