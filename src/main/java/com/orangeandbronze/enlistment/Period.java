package com.orangeandbronze.enlistment;

import java.util.Objects;
import static org.apache.commons.lang3.Validate.*;

class Period {
    final int startHour;
    final boolean startIsAtBottomOfHour;
    final int endHour;
    final boolean endIsAtBottomOfHour;

    /**
     * Creates a period with the specified start and end times.
     * @param startHour                The hour in military time that the period starts
     * @param startIsAtBottomOfHour    Whether the period starts at the bottom of the hour
     * @param endHour                  The hour in military time that the period ends
     * @param endIsAtBottomOfHour      Whether the period ends at the bottom of the hour
     */
    Period(int startHour, boolean startIsAtBottomOfHour, int endHour, boolean endIsAtBottomOfHour) {
        inclusiveBetween(8, 17, startHour, "Period must be within the hours 8:30 to 17:30");
        inclusiveBetween(8, 17, endHour, "Period must be within the hours 8:30 to 17:30");
        isTrue(startHour != 8 || startIsAtBottomOfHour);
        isTrue(endHour != 8 || endIsAtBottomOfHour);

        isTrue(startHour <= endHour, "Period must start before it ends");
        isTrue(startHour < endHour || (!startIsAtBottomOfHour && endIsAtBottomOfHour), "Period must start before it ends");

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
