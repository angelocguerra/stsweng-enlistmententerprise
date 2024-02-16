package com.orangeandbronze.enlistment;

import java.util.*;
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
     * @param subject       The subject identifier for the section.
     * @param allSections   The allSections contains all currently available sections
     */
    Section(String sectionID, Schedule schedule, Room room, Subject subject, SectionGroup allSections) {
        notBlank(sectionID, "sectionID cannot be null or blank");
        isTrue(isAlphanumeric(sectionID), "sectionID must be alphanumeric, was: " + sectionID);

        requireNonNull(schedule, "Schedule cannot be null");

        requireNonNull(room, "Room cannot be null");

        requireNonNull(subject, "subjectId cannot be null");

        checkForRoomConflict(allSections.getSections());

        this.sectionId = sectionID;
        this.schedule = schedule;
        this.room = room;
        this.subject = subject;
        this.numberOfEnlisted = 0;
        allSections.addSection(this);
    }

    /**
     * Checks for a schedule conflict with another section.
     * @param other     The other section to check for conflicts.
     */
    void checkForConflict(Section other) {
        if (schedule.hasConflictWith(other.getSchedule())) {
            throw new ScheduleConflictException(
                    "This section " + this + " has overlapping schedule with section " + other
                    + " which has the schedule " + other.getSchedule()
            );
        }
    }

    void checkForRoomConflict(Collection<Section> otherSections) {
        for (Section other: otherSections) {
            if (schedule.hasConflictWith(other.getSchedule())) {
                if (this.room.toString().equals(other.room.toString())) {
                    throw new ScheduleRoomConflictException(
                            "This section " + this + " has overlapping schedule and room with section " +
                            other + " which has the schedule " + other.getSchedule() +
                            "and room " + other.room.toString()
                    );
                }
            }
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
     * Checks if the student has met the prerequisites for the subject.
     * @param subjectsTaken    The collection of subjects taken by the student.
     */
    void checkPrerequisites(Collection<Subject> subjectsTaken) {
        requireNonNull(subjectsTaken, "Subjects taken cannot be null");
        Collection<Subject> copy = new HashSet<>(subjectsTaken);
        subject.checkPrerequisites(copy);
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

    int getSubjectUnits() { return subject.getUnits(); }

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
