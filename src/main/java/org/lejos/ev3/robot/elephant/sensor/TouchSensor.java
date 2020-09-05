package org.lejos.ev3.robot.elephant.sensor;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

public class TouchSensor {

	private final EV3TouchSensor sensor;
	private final SensorMode sensorSampleProvider;

	public TouchSensor(Port p) {
		sensor = new EV3TouchSensor(p);
		sensorSampleProvider = sensor.getTouchMode();
	}

	public void waitForPush() {
		boolean pushed = false;
		while (!pushed) {
			pushed = isPushed();
			if (!pushed) {
				continue;
			}
			System.out.println("touched");
		}
	}

	public boolean isPushed() {
		float[] sample = new float[sensorSampleProvider.sampleSize()];
		sensorSampleProvider.fetchSample(sample, 0);
		return sample[0] > 0;
	}
}
