package org.redout.agendawidget;

public class AgendaItem implements Comparable<AgendaItem> {
    private Long id;
    private String title;
    private String eventLocation;
    private String description;
    private Long dtStart;
    private Long dtEnd;
    private String eventTimeZone;
    private Long calendarId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDtStart() {
        return dtStart;
    }

    public void setDtStart(Long dtStart) {
        this.dtStart = dtStart;
    }

    public Long getDtEnd() {
        return dtEnd;
    }

    public void setDtEnd(Long dtEnd) {
        this.dtEnd = dtEnd;
    }

    public String getEventTimeZone() {
        return eventTimeZone;
    }

    public void setEventTimeZone(String eventTimeZone) {
        this.eventTimeZone = eventTimeZone;
    }

    public Long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }

    @Override
    public String toString() {
        return "AgendaItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", eventLocation='" + eventLocation + '\'' +
                ", description='" + description + '\'' +
                ", dtStart=" + dtStart +
                ", dtEnd=" + dtEnd +
                ", eventTimeZone='" + eventTimeZone + '\'' +
                ", calendarId=" + calendarId +
                '}';
    }

    @Override
    public int compareTo(AgendaItem another) {
        return this.dtStart.compareTo(another.dtStart);
    }
}
