package com.susanne.fingerprint.SamsungPass;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaArgs;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;
import android.util.SparseArray;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;
import com.samsung.android.sdk.pass.SpassInvalidStateException;


public class SamsungPass extends CordovaPlugin {
	
	private SpassFingerprint mSpassFingerprint;
    private Spass mSpass;
	private boolean isFeatureEnabled_fingerprint = false;
	private boolean hasRegisteredFinger = false;
	private Context mContext;
	private final String TAG = "SamsungPass Cordova";
	
	public SamsungPass() {
	}
	
	
	@Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
		mContext = this.cordova.getActivity().getApplicationContext();
		sSpass = new Spass();

        try {
            sSpass.initialize(mContext);
        } catch (SsdkUnsupportedException e) {
            Log.e(TAG, "Exception: " + e);
        } catch (UnsupportedOperationException e) {
            Log.e(TAG, "Fingerprint Service is not supported in the device");
        }
		
		isFeatureEnabled_fingerprint = sSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT);

        if (isFeatureEnabled_fingerprint) {
            mSpassFingerprint = new SpassFingerprint(mContext);
            Log.i(TAG, "Fingerprint Service is supported in the device.");
            Log.i(TAG, "SDK version : " + sSpass.getVersionName());
        } else {
            Log.e(TAG, "Fingerprint Service is not supported in the device.");
            return;
        }
		
		isFeatureEnabled_index = sSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT_FINGER_INDEX);
        isFeatureEnabled_custom = sSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT_CUSTOMIZED_DIALOG);
        isFeatureEnabled_uniqueId = sSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT_UNIQUE_ID);
        isFeatureEnabled_backupPw = sSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT_AVAILABLE_PASSWORD);
    }
	
    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
		if ("isSamsungPassSupported".equals(action)) {
            isSamsungPassSupported(args, callbackContext);
        }
		else if ("hasRegisteredFingers".equals(action)){
			hasRegisteredFingers(args, callbackContext);
		}
		else if ("startIdentify".equals(action)){
            startIdentify(args, callbackContext);
		}
        else if ("getFingerprintName".equals(action)){
            getFingerprintName(args, callbackContext);
        }
		else{
            return false;
        }
        return true;
    }
	
	private void isSamsungPassSupported(JSONArray args, CallbackContext callbackContext) {
        Log.i(TAG, "Method: isSamsungPassSupported");

        if (isFeatureEnabled_fingerprint) {
            callbackContext.success();
        } else {
            callbackContext.error("Error: Feature not enable");
        }
    }
	
	private void hasRegisteredFingers(JSONArray args, CallbackContext callbackContext) {
        Log.i(TAG, "Method: hasRegisteredFingers");
		
		hasRegisteredFinger = mSpassFingerprint.hasRegisteredFinger();
		
        if (hasRegisteredFinger) {
            callbackContext.success();
        } else {
            callbackContext.error("Error: No fingerprints are registered");
        }
    }

    private void getFingerprintName(JSONArray args, CallbackContext callbackContext) {
        Log.i(TAG, "Method: getFingerprintName");
        String fingerprints = "";
        SparseArray<String> mList = null;
        Log.i("=Fingerprint Name=");
        if (mSpassFingerprint != null) {
            mList = mSpassFingerprint.getRegisteredFingerprintName();
        }
        if (mList == null) {
            Log.e("Registered fingerprint is not existed.");
            callbackContext.error("Registered fingerprint is not existed.");
        } else {
            for (int i = 0; i < mList.size(); i++) {
                int index = mList.keyAt(i);
                String name = mList.get(index);
                fingerprints = "Index: " + index + " Name: " + name + ",";
            }
            callbackContext.success(fingerprints);
        }
        
    }
	
	private void startIdentify(JSONArray args, CallbackContext callbackContext) {
        Log.i(TAG, "Method: startIdentify");
        SpassFingerprint.IdentifyListener mIdentifyListenerDialog = new SpassFingerprint.IdentifyListener() {
        @Override
        public void onFinished(int eventStatus) {
            Log.i("identify finished : reason =" + getEventStatusName(eventStatus));
            int FingerprintIndex = 0;
            try {
                FingerprintIndex = mSpassFingerprint.getIdentifiedFingerprintIndex();
            } catch (IllegalStateException ise) {
                log(ise.getMessage());
            }
            if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS) {
                Log.i("onFinished() : Identify authentification Success with FingerprintIndex : " + FingerprintIndex);
                callbackContext.success();
            } else if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS) {
                Log.i("onFinished() : Password authentification Success");
                callbackContext.success();
            } else if (eventStatus == SpassFingerprint.STATUS_USER_CANCELLED
                    || eventStatus == SpassFingerprint.STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE) {
                Log.i("onFinished() : User cancel this identify.");
                callbackContext.error("cancel");
            } else if (eventStatus == SpassFingerprint.STATUS_TIMEOUT_FAILED) {
                Log.i("onFinished() : The time for identify is finished.");
                callbackContext.error("timeout");
            } else {
                Log.e("onFinished() : Authentification Fail for identify");
                callbackContext.error("failed");
            }
        }

        @Override
        public void onReady() {
            Log.i("identify state is ready");
        }

        @Override
        public void onStarted() {
            Log.i("User touched fingerprint sensor");
        }

        @Override
        public void onCompleted() {
            Log.i("the identify is completed");
        }
    };

    mSpassFingerprint.startIdentifyWithDialog(mContext, mIdentifyListenerDialog, false);


	}
}
