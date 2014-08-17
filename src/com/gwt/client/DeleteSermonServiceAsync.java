package com.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DeleteSermonServiceAsync {

	void DeleteService(String id, AsyncCallback<String> callback);

}
