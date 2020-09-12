package org.lejos.ev3.robot.elephant.event;

import org.lejos.ev3.robot.elephant.sensor.Command;
import org.lejos.ev3.robot.elephant.sensor.IRSensor;

public class RemoteButtonPressEventProducer implements Runnable {

   private final IRSensor irSensor;
   private final Dispatcher<SensorEvent> dispatcher;

    public RemoteButtonPressEventProducer(IRSensor irSensor, Dispatcher<SensorEvent> dispatcher) {
        this.irSensor = irSensor;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        Command command = irSensor.nextCommand();
        //noinspection InfiniteLoopStatement
        while(true) {
            Command nextCommand = irSensor.nextCommand();
            if (command != nextCommand) {
                command = nextCommand;
                dispatcher.dispatchEvent(new RemoteButtonPressedEvent(command));
            }
        }
    }
}
