package org.redout.agendawidget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class EventRecyclerAdapter extends RecyclerView.Adapter <EventRecyclerAdapter.MyViewHolder> {
    List<AgendaItem> agendaItems;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView event_title, event_day, event_time;

        public MyViewHolder(View view) {
            super(view);

            event_title = view.findViewById(R.id.event_title);
            event_day = view.findViewById(R.id.event_day);
            event_time = view.findViewById(R.id.event_time);
        }
    }

    public EventRecyclerAdapter(List<AgendaItem> eventList) { this.agendaItems = eventList;}

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AgendaItem event = agendaItems.get(position);
        Calendar startTime = Calendar.getInstance();
        startTime.setTimeZone(TimeZone.getTimeZone(event.getEventTimeZone()));
        startTime.setTimeInMillis(event.getDtStart());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE MMM dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma");

        holder.event_title.setText(event.getTitle());
        holder.event_day.setText(dayFormat.format(startTime.getTime()));
        holder.event_time.setText(timeFormat.format(startTime.getTime()));
    }

    @Override
    public int getItemCount() {return agendaItems.size();}
}
