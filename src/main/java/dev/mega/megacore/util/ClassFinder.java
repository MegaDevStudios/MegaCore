package dev.mega.megacore.util;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ClassFinder {

    public static Set<Class<?>> findSubclasses(String packageName, Class<?> superClass) {
        Set<Class<?>> subclasses = new HashSet<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource(packageName.replace('.', '/'));
            if (resource == null) {
                throw new IllegalArgumentException("Package not found: " + packageName);
            }
            File directory = new File(resource.toURI());
            if (!directory.exists()) {
                throw new IllegalArgumentException("Directory does not exist: " + directory);
            }
            for (File file : directory.listFiles()) {
                if (file.getName().endsWith(".class")) {
                    String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
                    Class<?> clazz = Class.forName(className);
                    if (superClass.isAssignableFrom(clazz) && !clazz.equals(superClass)) {
                        subclasses.add(clazz);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subclasses;
    }
}
