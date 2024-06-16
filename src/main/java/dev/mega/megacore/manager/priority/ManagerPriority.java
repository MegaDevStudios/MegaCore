package dev.mega.megacore.manager.priority;

import lombok.Getter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ManagerPriority {
    PriorityLevel enumPriority() default PriorityLevel.NORMAL;
    
    int priority() default -1;

    @Getter
    enum PriorityLevel {
        HIGH(2),
        NORMAL(1),
        LOW(0);

        private final int slot;

        PriorityLevel(int priority) {
            this.slot = priority;
        }
    }

}
