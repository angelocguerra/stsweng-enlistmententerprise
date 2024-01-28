package com.orangeandbronze.enlistment;

import org.apache.commons.lang3.*;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.StringUtils.*;
import java.util.Objects;

class Section {
    private final String sectionId;
    private final Schedule schedule;
    private final Room room;
    private int numberOfEnlisted;

    // New Section, no Enlisted Students
    Section(String sectionID, Schedule schedule, Room room) {
        notBlank(sectionID, "sectionID cannot be null or blank");
        isTrue(isAlphanumeric(sectionID), "sectionID must be alphanumeric, was: " + sectionID);
        requireNonNull(schedule, "Schedule cannot be null");

        requireNonNull(room, "Room cannot be null");

        this.sectionId = sectionID;
        this.schedule = schedule;
        this.room = room;
        this.numberOfEnlisted = 0;
    }

    void checkForConflict(Section other) {
        if (this.schedule.equals(other.getSchedule())) {
            throw new ScheduleConflictException("This section " + this + "has conflict with section " + other
                    + "having same schedule at " + schedule);
        }
    }

    int getNumberOfEnlisted() {
        return numberOfEnlisted;
    }

    void addCurrentCapacity() {
        numberOfEnlisted++;
    }

    Schedule getSchedule() {
        return schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Section section = (Section) o;
        return Objects.equals(sectionId, section.sectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionId);
    }

    @Override
    public String toString() {
        return sectionId;
    }
}
