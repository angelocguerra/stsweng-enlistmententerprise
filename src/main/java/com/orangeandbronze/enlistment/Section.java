package com.orangeandbronze.enlistment;

import org.apache.commons.lang3.*;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.StringUtils.*;
import java.util.Objects;

/**
 * Represents a class section with its Section ID, schedule,
 * room, number of enrolled students, and subjectId.
 */
class Section {
    private final String sectionId;
    private final Schedule schedule;
    private final Room room;
    private int numberOfEnlisted;
    private final Subject subject;

    /**
     * Creates a new Section with no enrolled students
     * @param sectionID     The section identifier.
     * @param schedule      The schedule for the section.
     * @param room          The room where section is held.
     * @param subject     The subject identifier for the section.
     */
    Section(String sectionID, Schedule schedule, Room room, Subject subject) {
        notBlank(sectionID, "sectionID cannot be null or blank");
        isTrue(isAlphanumeric(sectionID), "sectionID must be alphanumeric, was: " + sectionID);

        requireNonNull(schedule, "Schedule cannot be null");

        requireNonNull(room, "Room cannot be null");

        requireNonNull(subject, "subjectId cannot be null");

        this.sectionId = sectionID;
        this.schedule = schedule;
        this.room = room;
        this.subject = subject;
        this.numberOfEnlisted = 0;
    }

    /**
     * Creates a new section with enrolled students
     * @param sectionID         The section identifier.
     * @param schedule          The schedule for the section.
     * @param room              The room where section is held.
     * @param numberOfEnlisted  The number of students already enrolled in the section.
     * @param subject         The subject identifier for the section.
     */
    Section(String sectionID, Schedule schedule, Room room, int numberOfEnlisted, Subject subject) {
        this(sectionID, schedule, room, subject);
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
        return this.subject.equals(other.subject);
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

    /**
     * Gets the subject for the section
     * @return     The subject for the section.
     */
    Subject getSubject() {
        return subject;
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
