package com.orangeandbronze.enlistment;

import org.apache.commons.lang3.*;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.StringUtils.*;
import java.util.Objects;

class Section {
    private final String sectionId;
    private final Schedule schedule;

    Section(String sectionID, Schedule schedule) {
        notBlank(sectionID, "sectionID cannot be null or blank");
        isTrue(isAlphanumeric(sectionID), "sectionID must be alphanumeric, was: " + sectionID);
        requireNonNull(schedule ,"Schedule cannot be null");

        this.sectionId = sectionID;
        this.schedule = schedule;
    }

    void checkForConflict(Section other) {
        if (this.schedule.equals(other.getSchedule())) {
            throw new ScheduleConflictException("This section " + this + "has conflict with section " + other + "having same schedule at " + schedule);
        }
    }

    Schedule getSchedule() {
        return schedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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
