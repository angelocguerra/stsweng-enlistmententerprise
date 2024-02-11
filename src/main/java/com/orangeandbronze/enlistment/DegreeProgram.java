package com.orangeandbronze.enlistment;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

class DegreeProgram {
    private final String degreeProgramName;
    private final Collection<Subject> degreeProgramSubjects = new HashSet<>();

    DegreeProgram(String degreeProgramName, Collection<Subject> degreeProgramSubjects) {
        notBlank(degreeProgramName);
        requireNonNull(degreeProgramSubjects);
        this.degreeProgramSubjects.addAll(degreeProgramSubjects);
        this.degreeProgramSubjects.removeIf(Objects::isNull);
        this.degreeProgramName= degreeProgramName;
    }

    void checkIfSubjectPartOfProgram(Subject subject){
        if (!degreeProgramSubjects.contains(subject)){
            throw new RuntimeException(
                    "Subject " + subject + " doesn't belong to degree " + degreeProgramName );
        }
    }
}