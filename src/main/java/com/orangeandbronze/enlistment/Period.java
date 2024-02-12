package com.orangeandbronze.enlistment;

import java.util.Objects;
import static org.apache.commons.lang3.Validate.*;

class Period {
    private final int startHour;
    private final boolean startIsAtBottomOfHour;
    private final int endHour;
    private final boolean endIsAtBottomOfHour;

    /**
     * Creates a period with the specified start and end times.
     * @param startHour                The hour in military time that the period starts
     * @param startIsAtBottomOfHour    Whether the period starts at the bottom of the hour
     * @param endHour                  The hour in military time that the period ends
     * @param endIsAtBottomOfHour      Whether the period ends at the bottom of the hour
     */
    Period(int startHour, boolean startIsAtBottomOfHour, int endHour, boolean endIsAtBottomOfHour) {
        String within0830to1730Msg = "Period must be within the hours 8:30 to 17:30";
        String periodStartsBeforeEndsMsg = "Period must start before it ends";

        inclusiveBetween(8, 17, startHour, within0830to1730Msg);
        inclusiveBetween(8, 17, endHour, within0830to1730Msg);
        isTrue(startHour != 8 || startIsAtBottomOfHour, within0830to1730Msg);
        isTrue(endHour != 8 || endIsAtBottomOfHour, within0830to1730Msg);

        isTrue(startHour <= endHour, periodStartsBeforeEndsMsg);
        isTrue(startHour < endHour || (!startIsAtBottomOfHour && endIsAtBottomOfHour), periodStartsBeforeEndsMsg);

        this.startHour = startHour;
        this.startIsAtBottomOfHour = startIsAtBottomOfHour;
        this.endHour = endHour;
        this.endIsAtBottomOfHour = endIsAtBottomOfHour;
    }

    @Override
    public String toString() {
        String startMinutes = startIsAtBottomOfHour ? "30" : "00";
        String endMinutes = endIsAtBottomOfHour ? "30" : "00";

        String startHourAsString = startHour >= 0 && startHour <= 9 ? "0" + startHour : Integer.toString(startHour);
        String endHourAsString = endHour >= 0 && endHour <= 9 ? "0" + endHour : Integer.toString(endHour);

        String start = startHourAsString + ":" + startMinutes;
        String end = endHourAsString + ":" + endMinutes;

        return start + " - " + end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return startHour == period.startHour && startIsAtBottomOfHour == period.startIsAtBottomOfHour && endHour == period.endHour && endIsAtBottomOfHour == period.endIsAtBottomOfHour;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startHour, startIsAtBottomOfHour, endHour, endIsAtBottomOfHour);
    }
}
