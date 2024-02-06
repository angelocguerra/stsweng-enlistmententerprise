package com.orangeandbronze.enlistment;

import java.util.ArrayList;
import java.util.*;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.*;

/**
 * Represents a student with a student number and their enrolled sections.
 */
class Student {
    private final int studentNo;
    private final Collection<Section> sections = new HashSet<>();

    private final Collection<Subject> subjectsTaken = new HashSet<>();

    /**
     * Creates a student with their own student number and sections.
     * @param studentNo     Specific student number for each student.
     * @param sections      The collection of sections in which a student is enrolled.
     */
    Student(int studentNo, Collection<Section> sections, Collection<Subject> subjectsTaken) {
        isTrue(studentNo >= 0, "Student number cannot be negative" + studentNo);
        requireNonNull(sections, "Sections cannot be null");

        this.studentNo = studentNo;
        this.sections.addAll(sections);
        this.subjectsTaken.addAll(subjectsTaken);
        //subjectstaken can be null
        this.subjectsTaken.removeIf(Objects::isNull);


        isTrue(!this.sections.contains(null), "Sections cannot contain null elements");
    }

    /**
     * Creates a student with the specified student number with no enrolled sections.
     * @param studentNo     Specific student number for each student.
     */
    Student(int studentNo) {
        this(studentNo, Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Enlists the student in a new section, checking for schedule conflicts with existing sections and for duplicate subject.
     * @param newSection    The section to be enlisted.
     */
    void enlist(Section newSection) {
        requireNonNull(newSection, "Section cannot be null");

        //check for schedule conflicts
        sections.forEach(existingSection -> existingSection.checkForConflict(newSection));

        //check for duplicate subjects
        if (sections.stream().anyMatch(existingSection -> existingSection.hasSameSubject(newSection))) {
            throw new DuplicateSubjectEnlistmentException("Cannot enlist in two sections with the same subject");
        }

        sections.add(newSection);
        newSection.addNumberOfEnlisted();
    }

    /**
     * Cancels the student's enlistment in a specific section
     * @param other The section from which the student's enlistment will be canceled.
     */
    void cancelEnlistment(Section other) {
        requireNonNull(other, "Section cannot be null");
        if (!sections.contains(other)) {
            throw new CancellingUnenlistedSectionException("Cannot cancel enlistment for a section that hasn't been enlisted");
        }

        sections.remove(other);
    }
    /**
     * Requests the assessment of the student's tuition fees.
     * @return      The total amount of tuition fees to be paid by the student.
     */
    double requestAssessment() {
        double totalUnits = 0;
        double totalLabFees = 0;
        for (Section section : sections) {
            Subject subject = section.getSubject();
            totalUnits += subject.getUnits();
            if (subject.getIsLaboratory()) {
                totalLabFees += 1000;
            }
        }

        double totalFees = totalUnits * 2000;
        double totalMiscFees = 3000;
        double totalVAT = 0.12 * (totalFees + totalLabFees + totalMiscFees);
        return totalFees + totalLabFees + totalMiscFees + totalVAT;
    }

    /**
     * Retrieves a copy of the sections in which the student is currently enrolled.
     * @return      A copy of the sections in which the student is enrolled.
     */
    Collection<Section> getSections() {
        return new ArrayList<>(sections);
    }


    /**
     * Retrieves a copy of the subjects taken by the student.
     * @return      A copy of the subjects taken by the student.
     */
    Collection<Subject> getSubjectsTaken() {
        return new ArrayList<>(subjectsTaken);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Student student = (Student) o;
        return studentNo == student.studentNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentNo);
    }

    @Override
    public String toString() {
        return "Student# " + studentNo;
    }
}
