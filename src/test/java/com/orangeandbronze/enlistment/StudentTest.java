package com.orangeandbronze.enlistment;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    final Schedule MTH_0830 = new Schedule(Days.MTH, Period.H0830);
    final Schedule TF_1000 = new Schedule(Days.TF, Period.H1000);
    final Schedule TF_0830 = new Schedule(Days.TF, Period.H0830);

    final Subject MTH101A = new Subject("MTH101A", 3, false);
    final Subject CCICOMP = new Subject("CCICOMP", 3, true);
    final Subject CCPROG1 = new Subject("CCPROG3", 3, true);
    final DegreeProgram BS_CS_ST = new DegreeProgram("BS CS-ST", new HashSet<>(List.of(MTH101A, CCICOMP, CCPROG1)));

    Student newDefaultStudent() {
        return new Student(1, BS_CS_ST);
    }

    Student newDefaultStudent(int studentNo, DegreeProgram degreeProgram) {
        return new Student(studentNo, degreeProgram);
    }

    @Test
    void enlist_same_student_in_2_sections_with_no_same_schedule() {
        // Given a student w/ no sections and 2 sections with no sched conflict
        Student student = newDefaultStudent();
        Room X = new Room("X", 10);
        Room Y = new Room("Y", 10);
        Section sec1 = new Section("A", MTH_0830, X, new Subject("GESPORT", 2, false));
        Section sec2 = new Section("B", TF_1000, Y, new Subject("GETEAMS", 2, false));

        // When student enlists in both sections
        student.enlist(sec1);
        student.enlist(sec2);

        // Then the 2 sections should be found in the student & only 2 sections
        var sections = student.getSections();
        assertAll(
                () -> assertTrue(sections.containsAll(List.of(sec1, sec2))),
                () -> assertEquals(2, sections.size()));
    }

    @Test
    void enlist_same_student_in_2_sections_with_same_schedule() {
        // Given a student w/ no sections and 2 sections with same sched
        Student student = newDefaultStudent();
        Room X = new Room("X", 10);
        Room Y = new Room("Y", 10);
        Section sec1 = new Section("A", MTH_0830, X, new Subject("GESPORT", 2, false));
        Section sec2 = new Section("B", MTH_0830, Y, new Subject("GETEAMS", 2, false));

        // When student enlists in the both sections
        student.enlist(sec1);

        // Then an exception will be thrown at the second enlistment
        assertThrows(ScheduleConflictException.class, () -> student.enlist(sec2));
    }

    @Test
    void enlist_within_room_capacity() {
        // Given 2 students and 1 section with room X of capacity 10
        Student student1 = newDefaultStudent(1, BS_CS_ST);
        Student student2 = newDefaultStudent(2, BS_CS_ST);
        final int CAP = 10;
        Room X = new Room("X", CAP);

        Section section = new Section("A", MTH_0830, X, new Subject("GESPORT", 2, false));
        // Both students enlist in same section
        student1.enlist(section);
        student2.enlist(section);

        // Number of students in section should be 2
        assertEquals(2, section.getNumberOfEnlisted());
    }

    @Test
    void enlist_exceeds_room_capacity() {
        // Given 2 students and 1 section with room X of capacity 1
        Student student1 = newDefaultStudent(1, BS_CS_ST);
        Student student2 = newDefaultStudent(2, BS_CS_ST);

        final int CAP = 1;
        Room X = new Room("X", CAP);

        Section section = new Section("A", MTH_0830, X, new Subject("GESPORT", 2, false));

        // Both students enlist in same section
        student1.enlist(section);
        // Exception should occur when 2nd student enlists in Section A
        assertThrows(RoomCapacityReachedException.class, () -> student2.enlist(section));
    }

    @Test
    void enlist_students_in_2_sections_with_max_capacity_sharing_the_same_room() {
        // Given 2 students and 2 sections with same room X of capacity 1
        Student student1 = newDefaultStudent(1, BS_CS_ST);
        Student student2 = newDefaultStudent(2, BS_CS_ST);

        final int CAP = 1;
        Room X = new Room("X", CAP);

        Section section1 = new Section("A", MTH_0830, X, new Subject("GESPORT", 2, false));
        Section section2 = new Section("B", TF_1000, X, new Subject("GETEAMS", 2, false));

        // Both students enlist in different sections
        student1.enlist(section1);
        student2.enlist(section2);

        // No exceptions should occur
    }

    // CANCELLING ENLISTMENT
    @Test
    void student_cancels_their_only_enlisted_section() {
        Student student = newDefaultStudent();
        Room X = new Room("X", 10);
        Section section = new Section("A", MTH_0830, X, new Subject("GESPORT", 2, false));
        student.enlist(section);
        student.cancelEnlistment(section);
        assertTrue(student.getSections().isEmpty());
    }

    @Test
    void student_cancels_one_of_their_two_enlisted_sections() {
        Student student = newDefaultStudent();
        Room X = new Room("X", 10);
        Room Y = new Room("Y", 10);
        Section section1 = new Section("A", MTH_0830, X, new Subject("GESPORT", 2, false));
        Section section2 = new Section("B", TF_1000, Y, new Subject("GETEAMS", 2, false));
        student.enlist(section1);
        student.enlist(section2);
        assertTrue(student.getSections().containsAll(List.of(section1, section2)));
        student.cancelEnlistment(section1);
        assertFalse(student.getSections().containsAll(List.of(section1, section2)));

    }

    @Test
    void student_cancels_their_only_two_enlisted_sections() {
        Student student = newDefaultStudent();
        Room X = new Room("X", 10);
        Room Y = new Room("Y", 10);
        Section section1 = new Section("A", MTH_0830, X, new Subject("GESPORT", 2, false));
        Section section2 = new Section("B", TF_1000, Y, new Subject("GETEAMS", 2, false));
        student.enlist(section1);
        student.enlist(section2);
        assertTrue(student.getSections().containsAll(List.of(section1, section2)));
        student.cancelEnlistment(section1);
        assertFalse(student.getSections().containsAll(List.of(section1, section2)));
        student.cancelEnlistment(section2);
        assertTrue(student.getSections().isEmpty());

    }

    @Test
    void student_cancels_enlistment_of_section_not_enlisted() {
        Student student = newDefaultStudent();
        Room X = new Room("X", 10);
        Room Y = new Room("Y", 10);
        Section section1 = new Section("A", MTH_0830, X, new Subject("GESPORT", 2, false));
        Section section2 = new Section("B", TF_1000, Y, new Subject("GETEAMS", 2, false));
        student.enlist(section1);
        // assert that an exception is thrown
        assertThrows(CancellingUnenlistedSectionException.class, () -> student.cancelEnlistment(section2));
    }

    @Test
    void student_enlist_2_sections_with_different_subjects() {
        Student student = newDefaultStudent();
        Room X = new Room("X", 10);
        Room Y = new Room("Y", 10);
        // Sections with  different subjects
        Section section1 = new Section("A", MTH_0830, X, new Subject("GESPORT", 2, false));
        Section section2 = new Section("B", TF_1000, Y, new Subject("GETEAMS", 2, false));

        student.enlist(section1);
        student.enlist(section2);

        // assert that the student is enlisted in both sections
        var sections = student.getSections();
        System.out.println("Sections: " + sections);
        assertAll(
                () -> assertTrue(sections.containsAll(List.of(section1, section2))),
                () -> assertEquals(2, sections.size())
        );
    }

    @Test
    void student_enlist_2_sections_with_same_subjects() {
        Student student = newDefaultStudent();
        Room X = new Room("X", 10);
        Room Y = new Room("Y", 10);

        // Sections with the same subject
        Section section1 = new Section("A", MTH_0830, X, new Subject("GESPORT", 2, false));
        Section section2 = new Section("B", TF_1000, Y, new Subject("GESPORT", 2, false));

        student.enlist(section1);

        // assert exception is thrown when the student enlists in the second
        assertThrows(DuplicateSubjectEnlistmentException.class, () -> student.enlist(section2));
    }

    @Test
    void student_enlists_in_section_with_correct_prerequisites() {
        // Given section and student where prereqs taken
        Subject subject1_withno_prereq = new Subject("CCPROG1", 3, true);
        Subject subject2_withno_prereq = new Subject("CCPROG2", 3, false);
        List<Subject> subjectsTaken =  List.of(subject1_withno_prereq, subject2_withno_prereq);

        // Subject 3 has a prereq of subject 1
        Subject subject3_with_prereq = new Subject("CCPROG3", 3, true, List.of(subject1_withno_prereq));

        DegreeProgram BS_IT = new DegreeProgram("BS IT", new HashSet<>(List.of(subject1_withno_prereq, subject2_withno_prereq, subject3_with_prereq)));
        Student student = new Student(2, Collections.emptyList(), subjectsTaken, BS_IT);

        // Section with subject 3
        Section section_with_prereq = new Section("CSADPRG", MTH_0830, new Room("X", 10), subject3_with_prereq);

        // When student enlists
        student.enlist(section_with_prereq);
        // Then enlistment is successful
        assertAll(
                ()-> assertTrue(student.getSections().contains(section_with_prereq)),
                () -> assertEquals(1, section_with_prereq.getNumberOfEnlisted())
        );
    }

    @Test
    void student_enlists_in_section_with_incorrect_prerequisites() {
        // Given section and student where some prerequisites are unmet
        Subject subject1_withno_prereq = new Subject("CSMATH1", 3, true);
        Subject subject2_withno_prereq = new Subject("CSMATH2", 3, true);

        // Subject 3 has a prereq of subject 1
        Subject subject3_with_prereq = new Subject("CSMATH3", 3, true, List.of(subject1_withno_prereq));

        DegreeProgram BS_CS_CSE = new DegreeProgram("BS CS-CSE", new HashSet<>(List.of(subject1_withno_prereq, subject2_withno_prereq, subject3_with_prereq)));
        Student student = new Student(2, Collections.emptyList(), Collections.emptyList(), BS_CS_CSE);

        // Section with subject 3
        Section section_with_prereq = new Section("A", MTH_0830, new Room("X", 10), subject3_with_prereq);

        // When student enlists
        // Then exception thrown
        assertThrows(PrerequisitesNotMetException.class, ()-> student.enlist(section_with_prereq));
    }

    @Test
    void assessment_when_there_are_no_enlisted_sections() {
        Student student = newDefaultStudent();

        BigDecimal assessment = student.requestAssessment();
        assertEquals(new BigDecimal("3360.00"), assessment);
    }

    @Test
    void assessment_with_only_non_lab_subjects() {
        Student student = newDefaultStudent();
        Room X = new Room("X", 10);
        Room Y = new Room("Y", 10);

        Section section1 = new Section("A", MTH_0830, X, new Subject("GESPORT", 2, false));
        Section section2 = new Section("B", TF_1000, Y, new Subject("GETEAMS", 2, false));

        student.enlist(section1);
        student.enlist(section2);

        BigDecimal assessment = student.requestAssessment();
        assertEquals(new BigDecimal("12320.00"), assessment);
    }

    @Test
    void assessment_with_only_lab_subjects() {
        Student student = newDefaultStudent();
        Room X = new Room("X", 10);
        Room Y = new Room("Y", 10);

        Section section1 = new Section("A", MTH_0830, X, new Subject("LBYARCH", 3, true));
        Section section2 = new Section("B", TF_1000, Y, new Subject("LABCCS", 3, true));

        student.enlist(section1);
        student.enlist(section2);

        BigDecimal assessment = student.requestAssessment();
        assertEquals(new BigDecimal("19040.00"), assessment);
    }

    @Test
    void assessment_with_mix_of_lab_and_non_lab_subjects() {
        Student student = newDefaultStudent();
        Room X = new Room("X", 10);
        Room Y = new Room("Y", 10);

        Section section1 = new Section("A", MTH_0830, X, new Subject("GESPORT", 2, false));
        Section section2 = new Section("B", TF_1000, Y, new Subject("STSWENG", 3, false));
        Section section3 = new Section("C", TF_0830, Y, new Subject("LBYARCH", 2, true));

        student.enlist(section1);
        student.enlist(section2);
        student.enlist(section3);

        BigDecimal assessment = student.requestAssessment();
        assertEquals(new BigDecimal("20160.00"), assessment);
    }

    @Test
    void assessment_when_only_0_unit_sections_are_enlisted() {
        Student student = newDefaultStudent();
        Room X = new Room("X", 10);
        Room Y = new Room("Y", 10);

        Section section1 = new Section("A", MTH_0830, X, new Subject("LCLSONE", 0, false));
        Section section2 = new Section("B", TF_1000, Y, new Subject("LCLSTWO", 0, false));
        Section section3 = new Section("C", TF_0830, Y, new Subject("LCLSTRI", 0, false));

        student.enlist(section1);
        student.enlist(section2);
        student.enlist(section3);

        BigDecimal assessment = student.requestAssessment();
        assertEquals(new BigDecimal("3360.00"), assessment);
    }

    @Test
    void assessment_with_mix_of_0_unit_and_non_zero_unit_subjects() {
        Student student = newDefaultStudent();
        Room X = new Room("X", 10);
        Room Y = new Room("Y", 10);

        Section section1 = new Section("A", MTH_0830, X, new Subject("LCLSONE", 0, false));
        Section section2 = new Section("B", TF_1000, Y, new Subject("MTH101", 5, false));
        Section section3 = new Section("C", TF_0830, Y, new Subject("LBYARCH", 2, true));

        student.enlist(section1);
        student.enlist(section2);
        student.enlist(section3);

        BigDecimal assessment = student.requestAssessment();
        assertEquals(new BigDecimal("20160.00"), assessment);
    }
}
