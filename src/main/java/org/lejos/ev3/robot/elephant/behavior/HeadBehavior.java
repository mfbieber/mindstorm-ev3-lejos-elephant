package org.lejos.ev3.robot.elephant.behavior;

import lejos.hardware.Button;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.robotics.subsumption.Behavior;
import org.lejos.ev3.robot.elephant.event.SensorEvent;
import org.lejos.ev3.robot.elephant.sensor.Color;
import org.lejos.ev3.robot.elephant.sensor.Command;
import org.lejos.ev3.robot.elephant.event.Dispatcher;
import org.lejos.ev3.robot.elephant.event.ColorChangedEvent;
import org.lejos.ev3.robot.elephant.event.RemoteButtonPressedEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class HeadBehavior implements Behavior {

    private final EV3LargeRegulatedMotor headMotor;
    private final AtomicBoolean up = new AtomicBoolean(true);
    private final AtomicBoolean run = new AtomicBoolean(false);

    public HeadBehavior(EV3LargeRegulatedMotor headMotor, Dispatcher<SensorEvent> dispatcher) {
        this.headMotor = headMotor;
        dispatcher.listen(ColorChangedEvent.class, this::colorChanged);
        dispatcher.listen(RemoteButtonPressedEvent.class, this::buttonPressed);
    }

    private void buttonPressed(SensorEvent event) {
        if (event instanceof RemoteButtonPressedEvent &&
                ((RemoteButtonPressedEvent) event).getCommand().equals(Command.HEAD)
        ) {
              run.set(true);
              return;
        }
        run.set(false);
        this.headMotor.stop();
    }

    private void colorChanged(SensorEvent event) {
        if (event instanceof ColorChangedEvent &&
                ((ColorChangedEvent) event).getColor().equals(Color.RED))
        {
            this.headMotor.stop();
            up.set(false);
            return;
        }
        up.set(true);
    }

    @Override
    public boolean takeControl() {
        int button = Button.readButtons();
        return (button == Button.ID_DOWN || run.get());
    }

    @Override
    public void action() {
        run.set(false);
        System.out.println("Head " + (up.get() ? "up" : "down"));
        this.headMotor.setAcceleration(1000);
        this.headMotor.setSpeed(800);
        int fullMoveRotation = 6000;
        this.headMotor.rotate(getDirection(up) * fullMoveRotation, true);
    }

    public int getDirection(AtomicBoolean up) {
        return up.get() ? 1 : -1;
    }

    @Override
    public void suppress() {
        //nothing to do here
    }
}
