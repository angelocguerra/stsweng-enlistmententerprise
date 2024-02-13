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

    /**
     * Checks if the period overlaps with another period.
     * One period starting exactly when the other period ends is not considered overlap.
     * @param other     The other period to check for overlap with
     * @return          true if there is overlap, false otherwise
     */
    boolean overlaps(Period other) {
        int start = this.getStart();
        int end = this.getEnd();
        int otherStart = other.getStart();
        int otherEnd = other.getEnd();

        return Math.min(end, otherEnd) > Math.max(start, otherStart);
    }

    /**
     * Returns an order-preserving numerical representation of the start of the period.
     * e.g. if Period A starts before Period B, A.getStart() will be less than B.getStart().
     * @return      The numerical representation
     */
    int getStart() { return startHour*100 + (startIsAtBottomOfHour ? 50 : 0); }

    /**
     * Returns an order-preserving numerical representation of the end of the period.
     * e.g. if Period A ends before Period B, A.getEnd() will be less than B.getEnd().
     * @return      The numerical representation
     */
    int getEnd() { return endHour*100 + (endIsAtBottomOfHour ? 50 : 0); }

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
