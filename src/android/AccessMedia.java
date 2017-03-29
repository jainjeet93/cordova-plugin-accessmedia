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

public class AccessMedia extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("getAllImages")) {
			String message = args.getString(0);
			this.getAllImages(message, callbackContext);
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
}
