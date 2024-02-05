package com.orangeandbronze.enlistment;

import org.apache.commons.lang3.*;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.StringUtils.*;
import java.util.Objects;

/**
 * Represents a class section with its Section ID, schedule,
 * room, and the number of enrolled students.
 */
class Section {
    private final String sectionId;
    private final Schedule schedule;
    private final Room room;
    private int numberOfEnlisted;
    private final String subjectId;

    /**
     * Creates a new Section with no enrolled students
     * @param sectionID     The section identifier.
     * @param schedule      The schedule for the section.
     * @param room          The room where section is held.
     * @param subjectId     The subject identifier for the section.
     */
    Section(String sectionID, Schedule schedule, Room room, String subjectId) {
        notBlank(sectionID, "sectionID cannot be null or blank");
        isTrue(isAlphanumeric(sectionID), "sectionID must be alphanumeric, was: " + sectionID);

        requireNonNull(schedule, "Schedule cannot be null");

        requireNonNull(room, "Room cannot be null");

        requireNonNull(subjectId, "subjectId cannot be null");

        this.sectionId = sectionID;
        this.schedule = schedule;
        this.room = room;
        this.subjectId = subjectId;
        this.numberOfEnlisted = 0;
    }

    /**
     * Creates a new section with enrolled students
     * @param sectionID         The section identifier.
     * @param schedule          The schedule for the section.
     * @param room              The room where section is held.
     * @param numberOfEnlisted  The number of students already enrolled in the section.
     * @param subjectId         The subject identifier for the section.
     */
    Section(String sectionID, Schedule schedule, Room room, int numberOfEnlisted, String subjectId) {
        this(sectionID, schedule, room, subjectId);
        isTrue(numberOfEnlisted >= 0, "numberOfEnlisted cannot be negative");
        this.numberOfEnlisted = numberOfEnlisted;
    }

    /**
     * Checks for a schedule conflict with another section.
     * @param other     The other section to check for conflicts.
     */
    void checkForConflict(Section other) {
        if (this.schedule.equals(other.getSchedule())) {
            throw new ScheduleConflictException("This section " + this + "has conflict with section " + other
                    + "having same schedule at " + schedule);
        }
    }

    /**
     * Checks if the section has the same subject as another section.
     * @param other     The other section to compare subjects.
     * @return true if subjects are the same, false otherwise.
     */
    boolean hasSameSubject(Section other) {
        return this.subjectId.equals(other.subjectId);
    }

    /**
     * Gets the number of students enrolled in the section.
     * @return          The number of enrolled students.
     */
    int getNumberOfEnlisted() {
        return numberOfEnlisted;
    }

    /**
     * Increases enrolled student count by one, checks for room overcapacity.
     */
    void addNumberOfEnlisted() {
        room.checkForOverCapacity(numberOfEnlisted);
        numberOfEnlisted++;
    }

    /**
     * Gets the schedule for the section.
     * @return      The schedule for the section.
     */
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
