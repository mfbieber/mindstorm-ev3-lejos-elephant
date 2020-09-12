package org.lejos.ev3.robot.elephant.sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;

public class IRSensor {

    private final EV3IRSensor irSensor;

    public IRSensor(Port p) {
        irSensor = new EV3IRSensor(p);
    }

    public Command nextCommand() {
        return Command.fromButtonValue(irSensor.getRemoteCommand(0));
    }
}
