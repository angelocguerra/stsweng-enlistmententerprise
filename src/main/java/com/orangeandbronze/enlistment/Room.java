package com.orangeandbronze.enlistment;

import static org.apache.commons.lang3.Validate.*;
import static org.apache.commons.lang3.StringUtils.*;

public class Room {
    private final String roomName;
    private final int maxCapacity;

    Room(String roomName, int maxCapacity) {
        notBlank(roomName);
        isTrue(isAlphanumeric(roomName), "roomName must be alphanumeric, was: " + roomName);
        isTrue(maxCapacity > 0, "maxCapacity must be greater than 0, was: " + maxCapacity);
        this.roomName = roomName;
        this.maxCapacity = maxCapacity;
    }
}
