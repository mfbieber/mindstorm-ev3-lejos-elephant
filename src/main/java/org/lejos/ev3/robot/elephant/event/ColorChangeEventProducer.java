package org.lejos.ev3.robot.elephant.event;

import lejos.utility.Delay;
import org.lejos.ev3.robot.elephant.sensor.ColorSensor;

public class ColorChangeEventProducer implements Runnable {

    private final ColorSensor headColorSensor;
    private final Dispatcher<SensorEvent> dispatcher;

    public ColorChangeEventProducer(ColorSensor headColorSensor, Dispatcher<SensorEvent> dispatcher) {
        this.headColorSensor = headColorSensor;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        boolean isRed = headColorSensor.isRed();
        Delay.msDelay(10000); //TODO: revise: wait for arbitrator to initialize, otherwise behaviours don't receive events
        dispatcher.dispatchEvent(new ColorChangedEvent(isRed));
        //noinspection InfiniteLoopStatement
        while (true) {
            boolean isNowRed = headColorSensor.isRed();
            if (isRed != isNowRed) {
                isRed = isNowRed;
                dispatcher.dispatchEvent(new ColorChangedEvent(isRed));
            }
        }
    }
}
