package com.orangeandbronze.enlistment;

import static org.apache.commons.lang3.Validate.isTrue;

class Student {
    private final int studentNo;

    Student(int studentNo) {
        isTrue(studentNo >= 0,
                "studentNumber should be non-negative, was:" + studentNo);
        this.studentNo = studentNo;
    }
}