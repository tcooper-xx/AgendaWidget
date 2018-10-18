package org.redout.agendawidget;

import android.Manifest;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link AgendaWidgetConfigureActivity AgendaWidgetConfigureActivity}
 */
public class AgendaWidget extends AppWidgetProvider {

    private EventRecyclerAdapter eventAdapter;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                         int appWidgetId) {

        CharSequence widgetText = AgendaWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.agenda_widget);

        CalUtil calUtil = CalUtil.getInstance(context);
        Map calendars = calUtil.getCalendars();
        List<AgendaItem> agendaItems = calUtil.getEvents(calendars.keySet());
        Collections.sort(agendaItems);
        for (AgendaItem item : agendaItems) {
            System.out.println(item.toString());
        }

        for(int i=0; i < 3; i++) {
            if (i < agendaItems.size()) {
                AgendaItem item = agendaItems.get(i);
                int titleId = context.getResources().getIdentifier("agendaTitle" + (i + 1), "id", context.getPackageName());
                int dayId = context.getResources().getIdentifier("agendaDay" + (i + 1), "id", context.getPackageName());
                int timeId = context.getResources().getIdentifier("agendaTime" + (i + 1), "id", context.getPackageName());
                int bulletId = context.getResources().getIdentifier("bullet" + (i+1), "id", context.getPackageName());

                views.setTextViewText(titleId, item.getTitle());
                Calendar startTime = Calendar.getInstance();
                startTime.setTimeZone(TimeZone.getTimeZone(item.getEventTimeZone()));
                startTime.setTimeInMillis(item.getDtStart());
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEE MMM dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                timeFormat.setTimeZone(startTime.getTimeZone());
                dayFormat.setTimeZone(startTime.getTimeZone());
                views.setTextViewText(dayId, dayFormat.format(startTime.getTime()));
                views.setTextViewText(timeId, timeFormat.format(startTime.getTime()));

                if (!(dayFormat.format(startTime.getTime()).equals(dayFormat.format(Calendar.getInstance(TimeZone.getTimeZone(item.getEventTimeZone())).getTime())))) {
                    System.out.println(bulletId + "NotToday");
                    views.setImageViewResource(bulletId, R.drawable.circle_icon_empty);
                } else {
                    System.out.println("IsToday");
                }

            }
            else {
                int unusedViewGroup = context.getResources().getIdentifier("agenda" + (i+1), "id", context.getPackageName());
                //views.removeAllViews(unusedViewGroup);
                views.setViewVisibility(unusedViewGroup, View.INVISIBLE);

                int lastDivider = context.getResources().getIdentifier("divider" + (i), "id", context.getPackageName());
                views.setViewVisibility(lastDivider, View.INVISIBLE);
            }
        }

        Intent intentUpdate = new Intent(context, AgendaWidget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(context, appWidgetId, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button, pendingUpdate);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            AgendaWidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

