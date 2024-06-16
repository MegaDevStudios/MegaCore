package dev.mega.megacore.manager;

import dev.mega.megacore.MegaCore;
import dev.mega.megacore.manager.priority.ManagerPriority;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public abstract class Manager implements Reloadable, Comparable<Manager> {
    protected final MegaCore megaCore;
    @Getter @Setter
    private boolean isRunning = false;

    public Manager(MegaCore megaCore) {
        this.megaCore = megaCore;
    }

    @Override
    public int compareTo(@NotNull Manager manager) {
        ManagerPriority otherManagerPriority = manager.getClass().getAnnotation(ManagerPriority.class);
        ManagerPriority thisManagerPriority = this.getClass().getAnnotation(ManagerPriority.class);

        if (otherManagerPriority == null && thisManagerPriority == null)
            return 0;
        else if (otherManagerPriority == null)
            return 1;
        else if (thisManagerPriority == null)
            return -1;

        int thisPriority = thisManagerPriority.priority();
        int otherPriority = otherManagerPriority.priority();
        int thisEnumPriority = thisManagerPriority.enumPriority().getSlot();
        int otherEnumPriority = otherManagerPriority.enumPriority().getSlot();

        if (thisPriority != -1 && otherPriority != -1) {
            return thisPriority - otherPriority;
        } else if (thisPriority != -1) {
            return thisPriority - otherEnumPriority;
        } else if (otherPriority != -1) {
            return thisEnumPriority - otherPriority;
        } else {
            return thisEnumPriority - otherEnumPriority;
        }
    }
}
