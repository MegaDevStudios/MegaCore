package dev.mega.megacore.command.matcher;

public interface ArgumentMatcher {
    boolean matches(String argument);

    String getValue();
}

// new ArgumentIpl(new StringArg("give"));
// new ArgumentIpl(new PlayerArg());