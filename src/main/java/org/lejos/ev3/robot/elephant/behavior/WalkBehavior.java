package org.lejos.ev3.robot.elephant.behavior;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import org.lejos.ev3.robot.elephant.event.Dispatcher;
import org.lejos.ev3.robot.elephant.event.RemoteButtonPressedEvent;
import org.lejos.ev3.robot.elephant.event.SensorEvent;
import org.lejos.ev3.robot.elephant.sensor.Command;
import org.lejos.ev3.robot.elephant.sensor.IRSensor;

import java.util.concurrent.atomic.AtomicBoolean;

public class WalkBehavior  implements Behavior {
	
	public AtomicBoolean run = new AtomicBoolean(false);

	public EV3LargeRegulatedMotor motor;

	public WalkBehavior(EV3LargeRegulatedMotor motor, Dispatcher<SensorEvent> dispatcher) {
		this.motor = motor;
		dispatcher.listen(RemoteButtonPressedEvent.class, this::buttonPressed);
	}

	private void buttonPressed(SensorEvent event) {
		if (event instanceof RemoteButtonPressedEvent &&
				((RemoteButtonPressedEvent) event).getCommand().equals(Command.WALK)
		) {
			run.set(true);
			return;
		} else if (event instanceof RemoteButtonPressedEvent &&
				((RemoteButtonPressedEvent) event).getCommand().equals(Command.STOP)
		) {
			motor.stop();
			run.set(false);
			return;
		}
		run.set(false);
	}

	@Override
	public boolean takeControl() {
		return (Button.readButtons() == Button.ID_ENTER || run.get());
	}

	@Override
	public void suppress() {
		//nothing to do here
	}

	@Override
	public void action() {
		Button.LEDPattern(6);
		Button.discardEvents();
		motor.setAcceleration(100);
		System.out.println("Walk");
		motor.setSpeed(300);
		motor.backward();
	}

}
