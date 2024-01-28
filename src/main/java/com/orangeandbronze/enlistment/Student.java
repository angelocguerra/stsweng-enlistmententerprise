package com.orangeandbronze.enlistment;

import java.util.ArrayList;
import java.util.*;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.*;

class Student {
    private final int studentNo;
    private final Collection<Section> sections = new HashSet<>();

    Student(int studentNo, Collection<Section> sections) {
        isTrue(studentNo >= 0, "Student number cannot be negative" + studentNo);
        requireNonNull(sections, "Sections cannot be null");

        this.studentNo = studentNo;
        this.sections.addAll(sections);
        isTrue(!this.sections.contains(null), "Sections cannot contain null elements");
    }

    Student(int studentNo) {
        this(studentNo, Collections.emptyList());
    }

    void enlist(Section newSection) {
        requireNonNull(newSection, "Section cannot be null");
        sections.forEach(existingSection -> existingSection.checkForConflict(newSection));
        sections.add(newSection);
        newSection.addCurrentCapacity();
    }

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
