package com.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UploadDropboxServiceAsync {

	void SubmitService(String id, String path, String name, String HoS,
			AsyncCallback<String> callback);

}
