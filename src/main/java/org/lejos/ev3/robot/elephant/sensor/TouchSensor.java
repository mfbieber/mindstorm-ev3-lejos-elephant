package org.lejos.ev3.robot.elephant.sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

public class TouchSensor {

	private final SensorMode sensorSampleProvider;

	public TouchSensor(Port p) {
		EV3TouchSensor sensor = new EV3TouchSensor(p);
		sensorSampleProvider = sensor.getTouchMode();
	}

	public boolean isPushed() {
		float[] sample = new float[sensorSampleProvider.sampleSize()];
		sensorSampleProvider.fetchSample(sample, 0);
		return sample[0] > 0;
	}
}
