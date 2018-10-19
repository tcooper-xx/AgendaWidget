
package org.redout.agendawidget;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalUtil {
    private static final CalUtil instance = new CalUtil();
    private static Context context;

    private CalUtil() {}

    public static CalUtil getInstance(Context appContext) {
        context = appContext;
        return instance;
    }

    public List<AgendaItem> getEvents(Set calendarIds) {
        Iterator idIterator = calendarIds.iterator();
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        //startTime.add(Calendar.DATE,-5);
        endTime.add(Calendar.DATE, 7);
        long c_start = startTime.getTimeInMillis();
        long c_end = endTime.getTimeInMillis();
        List<AgendaItem> agendaItems = new ArrayList();

        while (idIterator.hasNext()) {

            String id = idIterator.next().toString();
            String selection = "((dtend >= ?) AND (dtend <= ?) AND CALENDAR_ID = ? )";
            String[] selectionCriteria = new String[] {Long.toString(c_start), Long.toString(c_end), id};
            String[] projection = new String[] { "_id", "title", "description",
                    "dtstart", "dtend", "eventLocation", "eventTimezone", "calendar_id", CalendarContract.EXTRA_EVENT_ALL_DAY};

            Cursor cursor = context.getContentResolver().query(Uri.parse("content://com.android.calendar/events"),
                    projection,
                    selection,
                    selectionCriteria,
                    null);
            if(cursor.moveToFirst()) {
                do {
                    AgendaItem item = new AgendaItem();
                    item.setId(cursor.getLong(0));
                    item.setTitle(cursor.getString(1));
                    item.setDescription(cursor.getString(2));
                    item.setDtStart(cursor.getLong(3));
                    item.setDtEnd(cursor.getLong(4));
                    item.setEventLocation(cursor.getString(5));
                    item.setEventTimeZone(cursor.getString(6));
                    item.setCalendarId(cursor.getLong(7));
                    item.setAllDay(cursor.getString(8));
                    agendaItems.add(item);
                } while (cursor.moveToNext());
            }

        }
        return agendaItems;
    }


    public Map getCalendars() {
        HashMap calMap = new HashMap();

        String[] projection = new String[] {
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.NAME,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.ACCOUNT_TYPE
        };

        Cursor calCursor = context.getContentResolver().query(CalendarContract.Calendars.CONTENT_URI,
                projection,
                CalendarContract.Calendars.VISIBLE + " =1",
                null,
                CalendarContract.Calendars._ID + " ASC");

        if(calCursor.moveToFirst()) {
            do {
                long id = calCursor.getLong(0);
                String displayName = calCursor.getString(1);
                calMap.put(id,displayName);
            } while (calCursor.moveToNext());
        }
        return calMap;
    }

}
