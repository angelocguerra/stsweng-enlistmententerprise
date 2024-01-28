package com.orangeandbronze.enlistment;

import java.util.Objects;

import static org.apache.commons.lang3.Validate.isTrue;

class Student {
    private final int studentNo;

    Student(int studentNo) {
        isTrue(studentNo >= 0,
                "studentNumber should be non-negative, was:" + studentNo);
        this.studentNo = studentNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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