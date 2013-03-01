package tpg.googleapi.authentication;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import tpg.googleapi.helper.AuthConstants;
import tpg.googleapi.helper.GoogleCalendarDataStore;
import tpg.googleapi.ui.CalendarListActivity;
import tpg.googleapi.ui.GoogleCalendarBase;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AuthCodeWebview extends GoogleCalendarBase {

	static String TAG="AuthCodeWebview";
			
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		WebView webview=new WebView(this);
		setContentView(webview);        
		
		String request= addParamsToUrl(AuthConstants.AUTH_CODE_URL);
		Log.d(TAG,request);

		webview.loadUrl(request); 
		webview.getSettings().setJavaScriptEnabled(true);
		
		webview.setWebViewClient(new OAuthWebViewClient(this, handler));
	}

	protected String addParamsToUrl(String url){
		if(!url.endsWith("?"))
			url += "?";

		List<NameValuePair> params = new LinkedList<NameValuePair>();

		params.add(new BasicNameValuePair("client_id",AuthConstants.CLIENT_ID ));
		params.add(new BasicNameValuePair("redirect_uri",AuthConstants.REDIRECT_URI ));
		params.add(new BasicNameValuePair("response_type", AuthConstants.AUTH_CODE_RESPONSE_TYPE));
		params.add(new BasicNameValuePair("scope",AuthConstants.SCOPE));	

		String paramString = URLEncodedUtils.format(params, "utf-8");

		url += paramString;
		return url;
	}
	
	Handler handler=new Handler(){

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			if(msg==null){
				Log.d(TAG,"Message object is null. Please login to google account again");
				return;
			}
			
			Object obj=msg.obj;
			
			if(obj==null || !(obj instanceof JSONObject)){
				Log.d(TAG,"AuthCodeWebView Handler has null message body or not an instance of JSONObject");
				return;
			}
			JSONObject responseJSONObject=(JSONObject)obj;
			
			/*Handling status code 401 from Google server*/
			if(msg.what==401){
				String message=getResources().getString(R.string.login_required_401_error);
				try {
					JSONObject error=responseJSONObject.getJSONObject("error");
					if(error!=null && error.has("message")){
						message=null;
						message=error.getString("message");
					}
				} catch (JSONException e) {
					Log.d(TAG,e.toString());
				}
				//showOkAlert(message, true, AuthCodeWebview.this);
				return;
			}
			
			/*Handling status code 200 from Google server*/
			String accessToken="", refreshToken="", tokenType="";
			int expireTime=0;
			try {
				accessToken = responseJSONObject.getString("access_token");
				tokenType =responseJSONObject.getString("token_type");  //Bearer		//not saved as of now
				expireTime=responseJSONObject.getInt("expires_in");	//3600
				refreshToken=responseJSONObject.getString("refresh_token");
				
			} catch (JSONException e) {
				Log.d(TAG,e.toString());
			}
			GoogleCalendarDataStore dataStore=GoogleCalendarDataStore.getInstance();
			dataStore.setAccessToken(accessToken);
			dataStore.setExpirationTime(expireTime);
			dataStore.setRefreshToken(refreshToken);
			
			Intent intent=new Intent(AuthCodeWebview.this, CalendarListActivity.class);
			startActivity(intent);
		}
	};

	
	
}
