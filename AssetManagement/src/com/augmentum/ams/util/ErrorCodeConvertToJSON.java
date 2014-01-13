package com.augmentum.ams.util;

import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ErrorCodeConvertToJSON {

	public static JSONArray convertToJSONArray(Map<String, ExceptionHelper> errorCodes) {
		JSONArray jsonArray = new JSONArray();
		for (Entry<String, ExceptionHelper> entry : errorCodes.entrySet()) {
			JSONObject errorCode = new JSONObject();
			errorCode.put("id", entry.getKey());
			errorCode.put("errorCode", entry.getValue().getErrorCode());
			jsonArray.add(errorCode);
		}
		return jsonArray;
	}

	public static JSONObject convertToJSONObject(String errorCode) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("errorCode", errorCode);
		return jsonObject;
	}
	
	@SuppressWarnings("unchecked")
	public static Object convertToJSON(Object o) {
		if (null != o && o instanceof Map) {
			Map<String, ExceptionHelper> errorCodes = (Map<String, ExceptionHelper>)o;
			JSONArray jsonArray = new JSONArray();
			for (Entry<String, ExceptionHelper> entry : errorCodes.entrySet()) {
				JSONObject errorCode = new JSONObject();
				errorCode.put("id", entry.getKey());
				errorCode.put("errorCode", entry.getValue().getErrorCode());
				jsonArray.add(errorCode);
			}
			return jsonArray;
		} else if (null != o && o instanceof String){
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("errorCode", (String)o);
			return jsonObject;
		} else {
			return null;
		}
	}
	
}
