package dev.mega.megacore.command.matcher;

public class StringArg implements ArgumentMatcher {
    private String value;

    public StringArg(String value) {
        this.value = value;
    }

    public StringArg() {
    }

    @Override
    public boolean matches(String argument) {
        return value == null || argument.equals(value);
    }

    @Override
    public String getValue() {
        return value;
    }
}
