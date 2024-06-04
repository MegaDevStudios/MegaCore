package dev.mega.megacore.config.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import dev.mega.megacore.util.MegaCoreUtil;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class JsonSerializer {

    public static void serialize(File file, Object serializable) {
        Gson gs = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gs.toJson(serializable);

        createFile(file);

        try {
            FileWriter writer = new FileWriter(file);

            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            MegaCoreUtil.getLogger().severe("Cannot serialize: " + file.getPath());
        }
    }

    public static Object deserialize(File file, Class<?> clazz) {
        Gson gson = new Gson();

        createFile(file);

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, clazz);
        } catch (JsonIOException | JsonSyntaxException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void createFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                MegaCoreUtil.getLogger().severe("Cannot create file: " + file.getAbsolutePath());
            }

        }
    }
}
