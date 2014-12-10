package com.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void Login(String username, String pass, AsyncCallback<String> callback);

}
