package com.orangeandbronze.enlistment;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PeriodTest {
    @Test
    void periods_without_overlap() {
        var period1 = new Period(8, true, 10, false);
        var period2 = new Period(11, false, 11, true);

        assertFalse(period1.overlapsWith(period2));
    }

    @Test
    void back_to_back_periods_do_not_overlap() {
        var period1 = new Period(14, false, 15, true);
        var period2 = new Period(15, true, 16, true);

        assertFalse(period1.overlapsWith(period2));
    }

    @Test
    void equal_periods_overlap() {
        var period1 = new Period(8, true, 10, false);
        var period2 = new Period(8, true, 10, false);

        assertTrue(period1.overlapsWith(period2));
    }

    @Test
    void overlapping_but_unequal_periods() {
        var period1 = new Period(8, true, 10, false);
        var period2 = new Period(9, true, 11, false);

        assertTrue(period1.overlapsWith(period2));
    }

    @Test
    void one_period_encompasses_another() {
        var period1 = new Period(8, true, 11, false);
        var period2 = new Period(9, true, 10, false);

        assertTrue(period1.overlapsWith(period2));
    }
}
