package org.lejos.ev3.robot.elephant.behavior;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

import org.lejos.ev3.robot.elephant.sensor.TouchSensor;

public class TrumpBehavior implements Behavior {
	private boolean run = false;

	private final EV3LargeRegulatedMotor motor;
	private final TouchSensor touchsensor;
	
	public TrumpBehavior(EV3LargeRegulatedMotor motor, TouchSensor touchsensor) {
		this.motor = motor;
		this.touchsensor = touchsensor;
	}
	
	public boolean takeControl() {
		if (!run)
		{
			run = (Button.readButtons() == Button.ID_UP);
		}
		return run;
	}

	public void suppress() {
		run = false;
	}

	public void action() {
		boolean up = !touchsensor.isPushed();
		if (run) {
			System.out.println("Trump " + (up ? "up" : "down"));
			this.motor.setAcceleration(1000);
			this.motor.setSpeed(800);
			int fullMoveRotation = 1200;
			this.motor.rotate(getDirection(up) * fullMoveRotation, true);
			if (up) {
				this.touchsensor.waitForPush();
				this.motor.stop();
			}
			run = false;
		}
	}
	
	public int getDirection(boolean up) {
		return up ? -1 : 1;
	}
}
