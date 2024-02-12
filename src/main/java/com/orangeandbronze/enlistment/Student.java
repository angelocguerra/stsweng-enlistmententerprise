package com.orangeandbronze.enlistment;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private final DegreeProgram studentDegreeProgram;

    private int totalUnitsEnlisted;

    /**
     * Creates a student with the specified student number, enrolled sections, and subjects taken.
     * @param studentNo     Specific student number for each student.
     * @param sections      The collection of sections in which a student is enrolled.
     * @param subjectsTaken The collection of subjects taken by the student.
     */
    Student(int studentNo, Collection<Section> sections, Collection<Subject> subjectsTaken, DegreeProgram studentDegreeProgram) {
        isTrue(studentNo >= 0, "Student number cannot be negative" + studentNo);
        requireNonNull(sections, "Sections cannot be null");
        requireNonNull(subjectsTaken, "Subjects taken cannot be null");
        requireNonNull(studentDegreeProgram, "Degree Program cannot be null");

        this.studentNo = studentNo;
        this.studentDegreeProgram = studentDegreeProgram;
        this.sections.addAll(sections);
        this.subjectsTaken.addAll(subjectsTaken);
        this.subjectsTaken.removeIf(Objects::isNull); // subjectsTaken can be null
        this.totalUnitsEnlisted = 0;

        isTrue(!this.sections.contains(null), "Sections cannot contain null elements");
    }

    /**
     * Creates a student with the specified student number with no enrolled sections.
     * @param studentNo     Specific student number for each student.
     */
    Student(int studentNo, DegreeProgram degreeProgram) {
        this(studentNo, Collections.emptyList(), Collections.emptyList(), degreeProgram);
    }

    /**
     * Enlists the student in a new section, checking for schedule conflicts with existing sections and for duplicate subject.
     * @param newSection    The section to be enlisted.
     */
    void enlist(Section newSection) {
        requireNonNull(newSection, "Section cannot be null");
        int newTotalUnitsEnlisted;

        // check for schedule conflicts
        sections.forEach(existingSection -> existingSection.checkForConflict(newSection));

        // check if subject is part of degree program
        studentDegreeProgram.checkIfSubjectPartOfProgram(newSection.getSubject());

        // check for prerequisites
        newSection.checkPrerequisites(subjectsTaken);

        // check for duplicate subjects
        if (sections.stream().anyMatch(existingSection -> existingSection.hasSameSubject(newSection))) {
            throw new DuplicateSubjectEnlistmentException("Cannot enlist in two sections with the same subject");
        }

        // check if total units is not more than 24
        newTotalUnitsEnlisted = totalUnitsEnlisted + newSection.getSubjectUnits();
        if (newTotalUnitsEnlisted > 24) {
            throw new MaxUnitsPerStudentLimitExceededException("Cannot enlist in more than 24 units. Current total units enlisted: " + newTotalUnitsEnlisted + " units");
        }

        sections.add(newSection);
        newSection.addNumberOfEnlisted();
        totalUnitsEnlisted = newTotalUnitsEnlisted;
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


    BigDecimal requestAssessment() {
        // declare totalUnits as big decimal data type
        final int UNIT_COST = 2000;
        final BigDecimal LAB_FEE = new BigDecimal(1000);
        final BigDecimal MISC_FEE = new BigDecimal(3000);
        final BigDecimal VAT = new BigDecimal(1.12);

        BigDecimal total = BigDecimal.ZERO;
        for (Section section : sections) {
            int units = section.getSubject().getUnits();
            BigDecimal subjectCost = new BigDecimal(UNIT_COST * units);
            total = total.add(subjectCost);
            if (section.getSubject().getIsLaboratory()) {
                total = total.add(LAB_FEE);
            }
        }

        total = total.add(MISC_FEE);
        total = total.multiply(VAT);

        total = total.setScale(2, RoundingMode.HALF_UP);

        return total;

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
