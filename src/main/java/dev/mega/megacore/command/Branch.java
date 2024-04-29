package dev.mega.megacore.command;

import java.util.LinkedList;

public record Branch(String name, LinkedList<Arg> args) {}
