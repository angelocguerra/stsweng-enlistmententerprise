package com.orangeandbronze.enlistment;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isAlphanumeric;
import static org.apache.commons.lang3.Validate.*;
import static java.util.Objects.requireNonNull;

/**
 * Represents a subject with is subjectId, number of units,
 * laboratory indicator, and prerequisites.
 */
class Subject {
    private final String subjectId;
    private final int units;
    private final boolean isLaboratory;
    private final Collection<Subject> prereqSubjects = new HashSet<>();

    /**
     * Creates a new Subject with specified parameters
     * @param subjectId         The subject identifier
     * @param units             The number of units of the subject
     * @param isLaboratory      The boolean indicator of whether the subject is a laboratory subject.
     * @param prereqSubjects    The collection of prerequisites of the subject
     */
    Subject(String subjectId, int units, boolean isLaboratory, Collection<Subject> prereqSubjects) {
        notBlank(subjectId, "subjectId cannot be null or blank");
        isTrue(isAlphanumeric(subjectId), "subjectId must be alphanumeric, was: " + subjectId);

        isTrue(units >= 0, "Units cannot be a negative number" + units);

        this.subjectId = subjectId;
        this.units = units;
        this.isLaboratory = isLaboratory;
        this.prereqSubjects.addAll(prereqSubjects);
        isTrue(!this.prereqSubjects.contains(null), "Prerequisite subjects cannot contain null elements");
    }

    /**
     * Creates a new Subject with no prerequisite subjects
     * @param subjectId         The subject identifier
     * @param units             The number of units of the subject
     * @param isLaboratory      The boolean indicator of whether the subject is a laboratory subject.
     */
    Subject(String subjectId, int units, boolean isLaboratory) {
        this(subjectId, units, isLaboratory, Collections.emptyList());
    }

    /**
     * Gets the number of units for the subject.
     *
     * @return      The number of units for the subject.
     */
    int getUnits() {
        return units;
    }

    /**
     * Checks if the subject is a laboratory subject.
     *
     * @return      The boolean indicator of whether the subject is a laboratory subject.
     */
    boolean getIsLaboratory(){
        return isLaboratory;
    }
    

    /**
     * Checks if the student has met the prerequisites for the subject.
     * @param subjectsTaken     The collection of subjects taken by the student.
     */
    void checkPrerequisites(Collection<Subject> subjectsTaken) {
        requireNonNull(subjectsTaken, "Subjects taken cannot be null");
        Collection<Subject> copySubjectsTaken = new HashSet<>(subjectsTaken); // sets are quicker to search through
        if (!copySubjectsTaken.containsAll(prereqSubjects)) {
            Collection<Subject> copyOfPrereqs = new HashSet<>(prereqSubjects);
            copyOfPrereqs.removeAll(copySubjectsTaken);
            throw new PrerequisitesNotMetException(
                    "Unmet Prerequisites: " + copyOfPrereqs);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(subjectId, subject.subjectId);
    }

    @Override
    public String toString() {
        return subjectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectId);
    }
}
