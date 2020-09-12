package org.lejos.ev3.robot.elephant.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Dispatcher<T extends SensorEvent> {

    private final Map<Class<? extends SensorEvent>, List<Consumer<T>>> consumers;

    public Dispatcher() {
        this.consumers = new HashMap<>();
    }

    public void dispatchEvent(T sensorEvent) {
        consumers.forEach((eventType, consumerList) -> {
            if (eventType.equals(sensorEvent.getClass())) {
                consumerList.forEach(consumer -> consumer.accept(sensorEvent));
            }
        });
     }

    public void listen(Class<? extends SensorEvent> eventType, Consumer<T> eventConsumer) {
        consumers.putIfAbsent(eventType, new ArrayList<>());
        consumers.get(eventType).add(eventConsumer);
    }

}
