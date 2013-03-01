package tpg.googleapi.helper;

public class AuthConstants {
	static public final String CONTENT_TYPE_ENCODING="application/x-www-form-urlencoded";
	static public final String CONTENT_TYPE_JSON="application/json";
	static public final String AUTH_CODE_URL="https://accounts.google.com/o/oauth2/auth";
	static public final String AUTH_CODE_RESPONSE_TYPE="code";
	static public final String SCOPE="https://www.googleapis.com/auth/calendar";	//https://www.googleapis.com/auth/calendar.readonly
	
	/*These two details are to be retrieved from the Google Developer Console*/
	static public final String CLIENT_ID="54957922365-u54a1sfi1rgi3inbnu6j0qinp59q5qul.apps.googleusercontent.com";
	static public final String REDIRECT_URI="urn:ietf:wg:oauth:2.0:oob";
	
	static public final String ACCESS_TOKEN_URL="https://accounts.google.com/o/oauth2/token";
	static public final String GRANT_TYPE="authorization_code";
	
	static public final String CALENDAR_LIST_URI="https://www.googleapis.com/calendar/v3/users/me/calendarList";
	static public final String CALENDAR_EVENTS_URI="https://www.googleapis.com/calendar/v3/calendars/%s/events";				//calendarId
	//static public final String CALENDAR_GET_EVENT_DETAIL_URI="https://www.googleapis.com/calendar/v3/calendars/%s/events/%s";	//calendarId,eventId
	static public final String CALENDAR_INSERT_EVENT_URI="https://www.googleapis.com/calendar/v3/calendars/%s/events";			//calendarId
	static public final String CALENDAR_DELETE_EVENT_URI="";
}
