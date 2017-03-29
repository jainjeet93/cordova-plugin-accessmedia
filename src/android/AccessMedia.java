package cordova.plugin.accessmedia;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.cordova.LOG;
import android.provider.MediaStore;
import android.database.Cursor;
import java.util.*;
import android.net.Uri;

public class AccessMedia extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("getAllImages")) {
			String message = args.getString(0);
			this.getAllImages(message, callbackContext);
			return true;
		}else if (action.equals("getAllAudio")) {
			String message = args.getString(0);
			this.getAllAudio(message, callbackContext);
			return true;
		}
		return false;
	}

	private void getAllImages(String message, CallbackContext callbackContext) {
		final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
		final String orderBy = MediaStore.Images.Media._ID;
		Cursor cursor = this.cordova.getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
		int count = cursor.getCount();
		JSONArray arrPath = new JSONArray();

		for (int i = 0; i < count; i++) {
			cursor.moveToPosition(i);
			int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
			JSONObject pathObj = new JSONObject();
			try{
				pathObj.put("path", "" + cursor.getString(dataColumnIndex));
				pathObj.put("isSelected", new Boolean(false));
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			arrPath.put(pathObj);
		}

		JSONObject obj = new JSONObject();

		try{
			obj.put("imageList", arrPath);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		callbackContext.success(obj);
	}

	private void getAllAudio(String message, CallbackContext callbackContext) {

		Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		final String[] columns = { MediaStore.Audio.Media.DATA, MediaStore.Audio.Media._ID };
		Cursor c = this.cordova.getActivity().getContentResolver().query(uri, columns, null, null, null);

		int count = c.getCount();
		JSONArray tempAudioList = new JSONArray();

		for (int i = 0; i < count; i++) {
			c.moveToPosition(i);
			int dataColumnIndex = c.getColumnIndex(MediaStore.Audio.Media.DATA);
			JSONObject pathObj = new JSONObject();
			try{
				pathObj.put("path", "" + c.getString(dataColumnIndex));
				pathObj.put("fileName", "" + c.getString(dataColumnIndex).split("/")[c.getString(dataColumnIndex).split("/").length - 1]);
				pathObj.put("isSelected", new Boolean(false));
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			tempAudioList.put(pathObj);
		}

		JSONObject obj = new JSONObject();

		try{
			obj.put("audioList", tempAudioList);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		callbackContext.success(obj);
	}
}
