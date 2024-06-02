package dev.mega.megacore.util;

import dev.mega.megacore.MegaCore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {

//    public static Set<Class<?>> findSubclasses(MegaCore megaCore, String packageName, Class<?> superClass) {
//        Set<Class<?>> subclasses = new HashSet<>();
//        try {
//            ClassLoader classLoader = megaCore.getClass().getClassLoader();
//            URL resource = classLoader.getResource(packageName.replace('.', '/'));
//            if (resource == null) {
//                throw new IllegalArgumentException("Package not found: " + packageName);
//            }
//            File directory = new File(megaCore.getClass().getResource(packageName.replace('.', '/')).toExternalForm());
////            File f = new File(getClass().getResource("/MyResource").toExternalForm());
//            if (!directory.exists()) {
//                throw new IllegalArgumentException("Directory does not exist: " + directory);
//            }
//            for (File file : directory.listFiles()) {
//                if (file.getName().endsWith(".class")) {
//                    String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
//                    Class<?> clazz = Class.forName(className, true, classLoader);
//                    if (superClass.isAssignableFrom(clazz) && !clazz.equals(superClass)) {
//                        subclasses.add(clazz);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return subclasses;
//    }

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
