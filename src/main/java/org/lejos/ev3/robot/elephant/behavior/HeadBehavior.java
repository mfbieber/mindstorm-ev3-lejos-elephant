package org.lejos.ev3.robot.elephant.behavior;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.subsumption.Behavior;
import org.lejos.ev3.robot.elephant.sensor.ColorSensor;

public class HeadBehavior implements Behavior {

    private final EV3LargeRegulatedMotor headMotor;
    private final ColorSensor headColorSensor;

    public HeadBehavior(EV3LargeRegulatedMotor headMotor, ColorSensor headColorSensor) {
        this.headMotor = headMotor;
        this.headColorSensor = headColorSensor;
    }

    @Override
    public boolean takeControl() {
        int button = Button.readButtons();
        return button == Button.ID_DOWN;
    }

    @Override
    public void action() {
        boolean up = !headColorSensor.isRed();
        System.out.println("Head " + (up ? "up" : "down"));
        this.headMotor.setAcceleration(1000);
        this.headMotor.setSpeed(800);
        int fullMoveRotation = 1200;
        this.headMotor.rotate(getDirection(up) * fullMoveRotation, true);
        if (up) {
            this.headColorSensor.waitForRed();
            this.headMotor.stop();
        }
    }

    public int getDirection(boolean up) {
        return up ? 1 : -1;
    }

    @Override
    public void suppress() {
        //nothing to do here
    }
}