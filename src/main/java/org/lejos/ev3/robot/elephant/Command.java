package org.lejos.ev3.robot.elephant;

import java.util.Arrays;

public enum Command {

    UNKNOWN (0),
    TRUMP (1),
    HEAD (2),
    WALK (3),
    STOP (4);

    private final int buttonValue;

    Command(int buttonValue) {
        this.buttonValue = buttonValue;
    }

    public static Command fromButtonValue(int value) {
        return Arrays.stream(Command.values())
                .filter(command -> command.buttonValue == value)
                .findFirst()
                .orElse(Command.UNKNOWN);
    }
}
