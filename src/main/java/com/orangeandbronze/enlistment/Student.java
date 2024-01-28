package com.orangeandbronze.enlistment;

import static org.apache.commons.lang3.Validate.isTrue;
import java.util.Objects;

/**
 * The Domain Model
 * This class contains the student's ID Number and set of sections enlisted
 */
class Student {
    private final int studentNo;

    /**
     *
     * @param studentNo - Student's id number
     */
    Student(int studentNo) {
        isTrue(studentNo >= 0,
                "studentNumber should be non-negative, was:" + studentNo);
        this.studentNo = studentNo;
    }

    /**
     *
     * @param o - object generated from the hashCode
     * @return true if the student numbers match
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentNo == student.studentNo;
    }

    /**
     *
     * @return generated object that serves as the student's id number
     */
    @Override
    public int hashCode() {
        return Objects.hash(studentNo);
    }

    /**
     *
     * @return typecasted studentNo from object to string
     */
    @Override
    public String toString() {
        return "Student No.:" + studentNo;
    }
}