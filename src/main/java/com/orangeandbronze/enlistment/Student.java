package com.orangeandbronze.enlistment;

class Student {
    private final int studentNumber;

    Student(int studentNumber) {
        if (studentNumber < 0) {
            throw new IllegalArgumentException(
                    "studentNumber should be non-negative, was:" + studentNumber);
        }
        this.studentNumber = studentNumber;
    }
}
