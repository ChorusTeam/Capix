package net.yeoxuhang.capix.api;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Supplier;

public class ModCape {
    public final String modId;
    public final String texture;
    private final Supplier<List<String>> nameSupplier;
    private List<String> nameList = List.of();

    public ModCape(String modId, String texture, Supplier<List<String>> nameSupplier) {
        this.modId = modId;
        this.texture = texture;
        this.nameSupplier = nameSupplier;
    }

    public ModCape(String modId, String texture, String url) {
        this(modId, texture, () -> fetchNameList(url));
    }

    public boolean shouldRenderFor(String name) {
        return nameList.contains(name.toLowerCase());
    }

    public void reload() {
        nameList = nameSupplier.get();
    }

    public static List<String> fetchNameList(String url) {
        try (InputStream in = new URL(url).openStream();
             InputStreamReader reader = new InputStreamReader(in)) {
            if (url.endsWith(".txt")) {
                // Read .txt file line-by-line
                List<String> names = new ArrayList<>();
                Scanner scanner = new Scanner(reader);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    if (!line.isEmpty() && !line.startsWith("#")) {
                        names.add(line.toLowerCase());
                    }
                }
                return names;
            } else {
                // Default: JSON string array
                return new Gson().fromJson(reader, new TypeToken<List<String>>() {}.getType());
            }

        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
