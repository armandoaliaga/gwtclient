package com.gwt.client;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SubmitHimnoServiceAsync {

	void SubmitService(String numero, String name, ArrayList<String> lyrics,
			AsyncCallback<String> callback);

}
