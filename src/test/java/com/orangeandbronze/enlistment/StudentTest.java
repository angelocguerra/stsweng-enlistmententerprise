package com.orangeandbronze.enlistment;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    final Schedule MTH_0830 = new Schedule(Days.MTH, Period.H0830);
    final Schedule TF_1000 = new Schedule(Days.TF, Period.H1000);

    Student newDefaultStudent() {
        return new Student(1);
    }

    @Test
    void enlist_same_student_in_2_sections_with_no_same_schedule() {
        // Given a student w/ no sections and 2 sections with no sked conflict
        Student student = newDefaultStudent();
        Section sec1 = new Section("A", MTH_0830);
        Section sec2 = new Section("B", TF_1000);

        // When student enlists in both sections
        student.enlist(sec1);
        student.enlist(sec2);

        // Then the 2 sections should be found in the student & only 2 sections
        var sections = student.getSections();
        assertAll(
                () -> assertTrue(sections.containsAll(List.of(sec1, sec2))),
                () ->  assertEquals(2, sections.size())
        );
    }

    @Test
    void enlist_same_student_in_2_sections_with_same_schedule() {
        // Given a student w/ no sections and 2 sections with same sked
        Student student = newDefaultStudent();
        Section sec1 = new Section("A", MTH_0830);
        Section sec2 = new Section("B", MTH_0830);

        // When student enlists in the both sections
        student.enlist(sec1);

        // Then an exception will be thrown at the second enlistment
        assertThrows(ScheduleConflictException.class, () -> student.enlist(sec2));
    }
}