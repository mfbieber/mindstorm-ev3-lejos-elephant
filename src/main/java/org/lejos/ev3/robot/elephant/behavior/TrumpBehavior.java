package org.lejos.ev3.robot.elephant.behavior;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.subsumption.Behavior;
import org.lejos.ev3.robot.elephant.Command;
import org.lejos.ev3.robot.elephant.sensor.IRSensor;
import org.lejos.ev3.robot.elephant.sensor.TouchSensor;

public class TrumpBehavior implements Behavior {

	private final EV3LargeRegulatedMotor motor;
	private final TouchSensor touchsensor;
	private final IRSensor irSensor;

	public TrumpBehavior(EV3LargeRegulatedMotor motor, TouchSensor touchsensor, IRSensor irSensor) {
		this.motor = motor;
		this.touchsensor = touchsensor;
		this.irSensor = irSensor;
	}
	
	public boolean takeControl() {
		int button = Button.readButtons();
		return (button == Button.ID_UP || irSensor.nextCommand().equals(Command.TRUMP));
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
