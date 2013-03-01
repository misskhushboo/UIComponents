List of URIs:   https://developers.google.com/google-apps/calendar/v3/reference/

CalendarList.java
	- ArrayList<Calendar>				//This has list of all calendars
	
Calendar.java							//Description of a particular calendar
	-String Id
	-String summary
	-String description
	
---------------------------------------------------------------------------------------------------
CalendarEvents.java
	-String summary
	-String timezone
	-ArrayList <CalendarEventItems>		//This has the list of events in the particular calendar.
	
CalendarEventItems						//Description of a single Event in the particular Calendar
	-String Id
	-String summary
	-String location
	-String startDateTime
	-String EndDateTime
---------------------------------------------------------------------------------------------------
		