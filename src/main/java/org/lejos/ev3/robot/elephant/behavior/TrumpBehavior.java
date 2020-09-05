package org.lejos.ev3.robot.elephant.behavior;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.subsumption.Behavior;
import org.lejos.ev3.robot.elephant.sensor.TouchSensor;

import java.time.Duration;

public class TrumpBehavior implements Behavior {

	private final EV3LargeRegulatedMotor motor;
	private final TouchSensor touchsensor;

	private long lastControl;
	
	public TrumpBehavior(EV3LargeRegulatedMotor motor, TouchSensor touchsensor) {
		this.motor = motor;
		this.touchsensor = touchsensor;
	}
	
	public boolean takeControl() {
		int button = Button.readButtons();
		if (button != Button.ID_UP) {
			return false;
		}
		Duration passedTimeAfterLastControl = Duration.ofNanos(System.nanoTime() - lastControl);
		if (passedTimeAfterLastControl.minus(Duration.ofMillis(500)).isNegative()) {
			return false;
		}
		lastControl = System.nanoTime();
		return true;
	}

	public void suppress() {
		//nothing to do here
	}

	public void action() {
		boolean up = !touchsensor.isPushed();
		System.out.println("Trump " + (up ? "up" : "down"));
		this.motor.setAcceleration(1000);
		this.motor.setSpeed(800);
		int fullMoveRotation = 1200;
		this.motor.rotate(getDirection(up) * fullMoveRotation, true);
		if (up) {
			this.touchsensor.waitForPush();
			this.motor.stop();
		}
	}
	
	public int getDirection(boolean up) {
		return up ? -1 : 1;
	}
}
