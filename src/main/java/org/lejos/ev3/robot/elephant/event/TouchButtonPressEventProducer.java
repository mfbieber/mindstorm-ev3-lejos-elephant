package org.lejos.ev3.robot.elephant.event;

import lejos.utility.Delay;
import org.lejos.ev3.robot.elephant.sensor.TouchSensor;

public class TouchButtonPressEventProducer implements Runnable {

    private final TouchSensor trumpTouchSensor;
    private final Dispatcher<SensorEvent> dispatcher;

    public TouchButtonPressEventProducer(TouchSensor trumpTouchSensor, Dispatcher<SensorEvent> dispatcher) {
        this.trumpTouchSensor = trumpTouchSensor;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        boolean isPushed = trumpTouchSensor.isPushed();
        Delay.msDelay(10000); //TODO: revise: wait for arbitrator to initialize, otherwise behaviours don't receive events
        dispatcher.dispatchEvent(new TouchChangedEvent(isPushed));
        //noinspection InfiniteLoopStatement
        while (true) {
            boolean isNowPushed = trumpTouchSensor.isPushed();
            if (isPushed != isNowPushed) {
                isPushed = isNowPushed;
                dispatcher.dispatchEvent(new TouchChangedEvent(isPushed));
            }
        }
    }
}
