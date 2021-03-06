package org.lejos.ev3.robot.elephant;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.SensorPort;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import org.lejos.ev3.robot.elephant.behavior.*;
import org.lejos.ev3.robot.elephant.event.*;
import org.lejos.ev3.robot.elephant.sensor.ColorSensor;
import org.lejos.ev3.robot.elephant.sensor.IRSensor;
import org.lejos.ev3.robot.elephant.sensor.TouchSensor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Elephant {

	public static void main(String[] args) {

		EV3LargeRegulatedMotor mainMotor = new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("A"));
		EV3LargeRegulatedMotor trumpMotor = new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("B"));
		EV3LargeRegulatedMotor headMotor = new EV3LargeRegulatedMotor(BrickFinder.getDefault().getPort("D"));

		introMessage();
		mainMotor.resetTachoCount();
		mainMotor.rotateTo(0);
		mainMotor.setAcceleration(0);
		mainMotor.setSpeed(0);

		TouchSensor trumpTouchSensor = new TouchSensor(SensorPort.S1);
		ColorSensor headColorSensor = new ColorSensor(SensorPort.S4);
		IRSensor irSensor = new IRSensor(SensorPort.S3);

		Dispatcher<SensorEvent> dispatcher = new Dispatcher<>();

		ExecutorService executorService = Executors.newFixedThreadPool(3);
		executorService.submit(new ColorChangeEventProducer(headColorSensor, dispatcher));
		executorService.submit(new RemoteButtonPressEventProducer(irSensor, dispatcher));
		executorService.submit(new TouchButtonPressEventProducer(trumpTouchSensor, dispatcher));

		Behavior b1 = new KeepControlDecorator(new WalkBehavior(mainMotor, dispatcher));
		Behavior b2 = new KeepControlDecorator(new TrumpBehavior(trumpMotor, dispatcher));
		Behavior b3 = new KeepControlDecorator(new HeadBehavior(headMotor, dispatcher));
		Behavior b4 = new QuitBehavior();
		Behavior[] behaviorList = { b1, b2, b3, b4 };

		Arbitrator arbitrator = new Arbitrator(behaviorList);
		Button.LEDPattern(6);
		arbitrator.go();
	}

	private static void introMessage() {
		GraphicsLCD g = LocalEV3.get().getGraphicsLCD();
		g.setFont(Font.getSmallFont());
		g.drawString("Elephant", 2, 0, 0);
		g.drawString("Requires : ", 2, 10, 0);
		g.drawString(" A - Legs motor", 2, 20, 0);
		g.drawString(" B - Trump motor", 2, 30, 0);
		g.drawString(" D - Head motor", 2, 40, 0);
		g.drawString(" 1 - Trump sensor", 2, 50, 0);
		g.drawString(" 4 - Head sensor", 2, 60, 0);
		g.drawString("Actions : ", 2, 70, 0);
		g.drawString(" Enter - Walk", 2, 80, 0);
		g.drawString(" Up - Toggle Trump", 2, 90, 0);
		g.drawString(" Down - Toggle Head", 2, 100, 0);

		// Quit GUI button:
		g.setFont(Font.getSmallFont()); // can also get specific size using
		// Font.getFont()
		int y_quit = 110;
		int width_quit = 45;
		int height_quit = width_quit / 2;
		int arc_diam = 6;
		g.drawString("QUIT", 9, y_quit + 7, 0);
		g.drawLine(0, y_quit, 45, y_quit); // top line
		g.drawLine(0, y_quit, 0, y_quit + height_quit - arc_diam / 2); // left
		// line
		g.drawLine(width_quit, y_quit, width_quit, y_quit + height_quit / 2); // right
		// line
		g.drawLine(0 + arc_diam / 2, y_quit + height_quit, width_quit - 10,
				y_quit + height_quit); // bottom line
		g.drawLine(width_quit - 10, y_quit + height_quit, width_quit, y_quit
				+ height_quit / 2); // diagonal
		g.drawArc(0, y_quit + height_quit - arc_diam, arc_diam, arc_diam, 180,
				90);

		// Enter GUI button:
		g.fillRect(width_quit + 10, y_quit, height_quit, height_quit);
		g.drawString("GO", width_quit + 15, y_quit + 7, 0, true);

		Button.waitForAnyPress();
		if (Button.ESCAPE.isDown())
			System.exit(0);
		g.clear();
	}

}
