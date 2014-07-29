package com.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SubmitSermonServiceAsync {

	void SubmitService(String name, String name_of_predicador, String serie,
			String Descripcion, AsyncCallback<String> callback);

}
