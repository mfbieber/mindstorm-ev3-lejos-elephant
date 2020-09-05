package org.lejos.ev3.robot.elephant.behavior;

import lejos.robotics.subsumption.Behavior;

import java.util.Objects;

public class KeepControlDecorator implements Behavior {

    private final Behavior behavior;
    private boolean keepControl = false;

    public KeepControlDecorator(Behavior behavior) {
        this.behavior = Objects.requireNonNull(behavior);
    }

    @Override
    public boolean takeControl() {
        if (keepControl) {
            return true;
        }
        boolean takeControl = behavior.takeControl();
        if (!takeControl) {
            return false;
        }
        keepControl = true;
        return true;
    }

    @Override
    public void action() {
        behavior.action();
        keepControl = false;
    }

    @Override
    public void suppress() {
        behavior.suppress();
        keepControl = false;
    }
}
