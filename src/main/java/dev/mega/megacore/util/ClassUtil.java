package dev.mega.megacore.util;

import dev.mega.megacore.MegaCore;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Represents the util for reflection interaction with classes.
 */
public class ClassUtil {

    public static <T> List<Class<T>> findSubclasses(MegaCore instance, String basePackage, Class<T> type) {
        JarFile jar = getJar(instance);
        if (jar == null)
            return new ArrayList<>();
        basePackage = basePackage.replace('.', '/') + "/";
        List<Class<T>> classes = new ArrayList<>();
        try {
            for (Enumeration<JarEntry> jarEntry = jar.entries(); jarEntry.hasMoreElements();) {
                String name = jarEntry.nextElement().getName();
                if (name.startsWith(basePackage) && name.endsWith(".class")) {
                    String className = name.replace("/", ".").substring(0, name.length() - 6);
                    Class<?> clazz = null;
                    try {
                        clazz = Class.forName(className, true, instance.getClass().getClassLoader());
                    } catch (ExceptionInInitializerError | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (clazz == null)
                        continue;
                    if (type.isAssignableFrom(clazz))
                        classes.add((Class<T>) clazz);
                }
            }
        } finally {
            try {
                jar.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return classes;
    }

    private static JarFile getJar(MegaCore instance) {
        try {
            Method method = JavaPlugin.class.getDeclaredMethod("getFile");
            method.setAccessible(true);
            File file = (File) method.invoke(instance);
            return new JarFile(file);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
