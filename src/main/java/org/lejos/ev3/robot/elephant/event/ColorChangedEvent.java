package org.lejos.ev3.robot.elephant.event;

import org.lejos.ev3.robot.elephant.sensor.Color;

public class ColorChangedEvent implements SensorEvent {

    private final Color color;

    public ColorChangedEvent(boolean isRed) {
        if (isRed) {
            color = Color.RED;
        } else {
            color = Color.OTHER;
        }
    }

    public Color getColor() {
        return color;
    }

}
