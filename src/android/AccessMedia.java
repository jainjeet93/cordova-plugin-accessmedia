package cordova.plugin.accessmedia;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.cordova.LOG;
import android.provider.MediaStore;
import android.database.Cursor;

public class AccessMedia extends CordovaPlugin {

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		if (action.equals("coolMethod")) {
			String message = args.getString(0);
			this.coolMethod(message, callbackContext);
			return true;
		}
		return false;
	}

	private void coolMethod(String message, CallbackContext callbackContext) {
		// if (message != null && message.length() > 0) {
		// 	callbackContext.success(message);
		// } else {
		// 	callbackContext.error("Expected one non-empty string argument.");
		// }
		final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
		final String orderBy = MediaStore.Images.Media._ID;
		Cursor cursor = this.cordova.getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
		int count = cursor.getCount();

		String[] arrPath = new String[count];

		for (int i = 0; i < count; i++) {
			cursor.moveToPosition(i);
			int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
			arrPath[i]= cursor.getString(dataColumnIndex);
			// Log.d("PATH", arrPath[i]);
		}
		callbackContext.success(arrPath[0]);
	}
}
