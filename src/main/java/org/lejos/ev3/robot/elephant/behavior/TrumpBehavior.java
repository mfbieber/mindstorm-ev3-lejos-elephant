package org.lejos.ev3.robot.elephant.behavior;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.subsumption.Behavior;
import org.lejos.ev3.robot.elephant.event.Dispatcher;
import org.lejos.ev3.robot.elephant.event.RemoteButtonPressedEvent;
import org.lejos.ev3.robot.elephant.event.SensorEvent;
import org.lejos.ev3.robot.elephant.event.TouchChangedEvent;
import org.lejos.ev3.robot.elephant.sensor.Command;

import java.util.concurrent.atomic.AtomicBoolean;

public class TrumpBehavior implements Behavior {

	private final EV3LargeRegulatedMotor motor;
	private final AtomicBoolean up = new AtomicBoolean(true);
	private final AtomicBoolean run = new AtomicBoolean(false);

	public TrumpBehavior(EV3LargeRegulatedMotor motor, Dispatcher<SensorEvent> dispatcher) {
		this.motor = motor;
		dispatcher.listen(TouchChangedEvent.class, this::touchChanged);
		dispatcher.listen(RemoteButtonPressedEvent.class, this::buttonPressed);
	}

	private void buttonPressed(SensorEvent event) {
		if (event instanceof RemoteButtonPressedEvent &&
				((RemoteButtonPressedEvent) event).getCommand().equals(Command.TRUMP)
		) {
			run.set(true);
			return;
		}
		run.set(false);
		this.motor.stop();
	}

	private void touchChanged(SensorEvent event) {
		if (event instanceof TouchChangedEvent &&
				((TouchChangedEvent) event).isPushed())
		{
			this.motor.stop();
			up.set(false);
			return;
		}
		up.set(true);
	}
	
	public boolean takeControl() {
		int button = Button.readButtons();
		return (button == Button.ID_UP || run.get());
	}

	public void suppress() {
		//nothing to do here
	}

	public void action() {
		run.set(false);
		System.out.println("Trump " + (up.get() ? "up" : "down"));
		this.motor.setAcceleration(1000);
		this.motor.setSpeed(800);
		int fullMoveRotation = 1200;
		this.motor.rotate(getDirection(up) * fullMoveRotation, true);
	}
	
	public int getDirection(AtomicBoolean up) {
		return up.get() ? -1 : 1;
	}
}
