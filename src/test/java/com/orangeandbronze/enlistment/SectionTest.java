package com.orangeandbronze.enlistment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SectionTest {
    final Subject MTH101A = new Subject("MTH101A", 3, false);
    final Subject CCICOMP = new Subject("CCICOMP", 3, true);
    final Schedule MTH_0830 = new Schedule(Days.MTH, new Period(8, true, 10, false));

    @Test
    void instantiating_two_sections_with_schedule_conflict_to_same_room() {
        Room X = new Room("X", 10);
        SectionGroup allSections = new SectionGroup();
        Section sec1 = new Section("A", MTH_0830, X, MTH101A, allSections);
        Section sec2 = new Section("B", MTH_0830, X, CCICOMP, allSections);

        assertEquals(1, allSections.getSections().size());
    }
}