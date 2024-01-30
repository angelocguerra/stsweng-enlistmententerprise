package com.orangeandbronze.enlistment;

import java.util.*;

import static java.util.Objects.requireNonNull;

import static org.apache.commons.lang3.Validate.*;

/**
 * Represents a student that is identified by its Student Number, and Sections.
 */
class Student {
    private final int studentNo;
    private final Collection<Section> sections = new HashSet<>();

    /**
     * Instantiate a new student with a given student number and a set of sections.
     * @param studentNo     a unique student number
     * @param sections      a set of sections
     */
    Student(int studentNo, Collection<Section> sections) {
        isTrue(studentNo >= 0, "Student number cannot be negative" + studentNo);
        requireNonNull(sections, "Sections cannot be null");

        this.studentNo = studentNo;
        this.sections.addAll(sections);
        isTrue(!this.sections.contains(null), "Sections cannot contain null elements");
    }

    /**
     * Instantiate a new student with a given student number, but with an empty section.
     * @param studentNo     a unique student number
     */
    Student(int studentNo) {
        this(studentNo, Collections.emptyList());
    }

    /**
     * Enlist a Student to new Section.
     * @param newSection    Section Object that represents a new section
     */
    void enlist(Section newSection) {
        requireNonNull(newSection, "Section cannot be null");
        // loop through all existing sections, check if schedule conflicts with new section
        sections.forEach(existingSection -> {
            existingSection.checkForConflict(newSection); });
        sections.add(newSection);
        newSection.addNumberOfEnlisted();
    }

    /**
     * Cancels a Student's enlisted Section.
     * @param other     Section Object to be canceled
     */
    void cancelEnlistment(Section other) {
        requireNonNull(other, "Section cannot be null");
        if (!sections.contains(other)) {
            throw new CancellingUnenlistedSectionException("Cannot cancel enlistment for a section that hasn't been enlisted");
        }

        sections.remove(other);
    }

    /**
     * Gets a Student's Sections.
     * @return      defensive copy of an arrayList of a student's sections
     */
    Collection<Section> getSections() {
        return new ArrayList<>(sections);
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
