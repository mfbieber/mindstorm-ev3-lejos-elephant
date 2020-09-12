package org.lejos.ev3.robot.elephant.event;

import org.lejos.ev3.robot.elephant.sensor.Command;

public class RemoteButtonPressedEvent implements SensorEvent {

    private final Command command;

    public RemoteButtonPressedEvent(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
