package org.lejos.ev3.robot.elephant.sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class ColorSensor {

    private final SensorMode sensorSampleProvider;

    public ColorSensor(Port port) {
        EV3ColorSensor colorSensor = new EV3ColorSensor(port);
        sensorSampleProvider = colorSensor.getRedMode(); //TODO: maybe this is even not the proper mode
    }

    /**
     * The sample contains one element containing the intensity level (Normalized between 0 and 1) of reflected light.
     * @return if the detected color is red.
     */
    public boolean isRed() {
        float[] sample = new float[sensorSampleProvider.sampleSize()];
        sensorSampleProvider.fetchSample(sample, 0);
        return sample[0] > 0.03; //TODO: find proper threshold value
    }
}
