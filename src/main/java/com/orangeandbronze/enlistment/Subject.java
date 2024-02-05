package com.orangeandbronze.enlistment;

import java.util.*;

import static org.apache.commons.lang3.StringUtils.isAlphanumeric;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notBlank;

class Subject {
    private final String subjectId;
    private final int units;
    private final boolean isLaboratory;
    private final Collection<Subject> prereqSubjects = new HashSet<>();

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

    Subject(String subjectId, int units, boolean isLaboratory) {
        this(subjectId, units, isLaboratory, Collections.emptyList());
    }

    double getUnits() {
        return units;
    }

    boolean getIsLaboratory(){
        return isLaboratory;
    }

    Collection<Subject> getprereqSubjects() {
        return new ArrayList<>(prereqSubjects);
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
