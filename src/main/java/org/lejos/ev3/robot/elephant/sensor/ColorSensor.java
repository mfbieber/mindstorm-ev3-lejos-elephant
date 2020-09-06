package org.lejos.ev3.robot.elephant.sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;

public class ColorSensor {

    private final SensorMode sensorSampleProvider;

    public ColorSensor(Port port) {
        EV3ColorSensor colorSensor = new EV3ColorSensor(port);
        sensorSampleProvider = colorSensor.getRedMode();
    }

    public void waitForRed() {
        //noinspection StatementWithEmptyBody
        while (!isRed()) {
            //waiting for sensor to detect red
        }
        System.out.println("red");
    }

    /**
     * The sample contains one element containing the intensity level (Normalized between 0 and 1) of reflected light.
     * @return if the detected color is red.
     */
    public boolean isRed() {
        float[] sample = new float[sensorSampleProvider.sampleSize()];
        sensorSampleProvider.fetchSample(sample, 0);
        System.out.println(sample[0]);
        return sample[0] > 0.03;
    }
}
