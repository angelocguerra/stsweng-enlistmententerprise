package com.orangeandbronze.enlistment;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Represents a schedule with specific days and periods.
 */
class Schedule {
    private final Days days;
    private final Period period;

    /**
     * Creates a schedule with the specified days and period.
     * @param days      The days for the schedule
     * @param period    The period for the schedule
     */
    Schedule(Days days, Period period) {
        requireNonNull(days, "Days cannot be null");
        requireNonNull(period, "Period cannot be null");
        this.days = days;
        this.period = period;
    }

    boolean hasConflictWith(Schedule other) {
        if (days != other.getDays()) {
            return false;
        }

        return period.overlaps(other.getPeriod());
    }

    Days getDays() {
        return days;
    }

    Period getPeriod() { return period; }

    @Override
    public String toString() {
        return days + " " + period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return days == schedule.days && period == schedule.period;
    }

    @Override
    public int hashCode() {
        return Objects.hash(days, period);
    }
}

/**
 * Represents the days of the week for the schedule.
 */
enum Days {
    MTH, TF, WS
}

