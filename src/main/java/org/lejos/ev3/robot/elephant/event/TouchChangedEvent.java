package org.lejos.ev3.robot.elephant.event;

public class TouchChangedEvent implements SensorEvent {

    private final boolean isPushed;

    public TouchChangedEvent(boolean isPushed) {
        this.isPushed = isPushed;
    }

    public boolean isPushed() {
        return isPushed;
    }
}
