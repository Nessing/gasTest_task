package ru.nessing.test_task.enums;

public enum Commands {
    LOAD ("/load "),
    RESULT ("/result "),
    EXIT ("/exit");

    private final String command;

    Commands(String command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return command;
    }
}
