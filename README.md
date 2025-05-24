# Capix
- A small API mod that allows Mod Developers to add capes to their mods

![Config Screen](https://github.com/ChorusTeam/Capix/raw/master/config_screen.png)
![Example Cape](https://github.com/ChorusTeam/Capix/raw/master/cape_example.png)
----
# Mod Developer
### Fabric Setup Example

```java
import net.yeoxuhang.capix.api.CapixApi;

public class ExampleModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Using default
        CapixApi.registerCape(ExampleMod.MOD_ID, "Example Cape", "textures/example_cape.png", "https://raw.githubusercontent.com/ExampleTeam/Example/master/namelist.txt");
        // Texture from url
        CapixApi.registerCape(ExampleMod.MOD_ID, "Example Cape", "https://github.com/ExampleTeam/Capix/blob/master/example_cape.png?raw=true", "https://raw.githubusercontent.com/ExampleTeam/Example/master/namelist.txt");
    }
}
```

### NeoForge Setup Example
```java
import net.yeoxuhang.capix.api.CapixApi;

@Mod("example_mod")
public class ExampleMod {

    public ExampleMod(IEventBus eventBus) {
        // Using default
        CapixApi.registerCape(ExampleMod.MOD_ID, "Example Cape", "textures/example_cape.png", "https://raw.githubusercontent.com/ExampleTeam/Example/master/namelist.txt");
        // Texture from url
        CapixApi.registerCape(ExampleMod.MOD_ID, "Example Cape", "https://github.com/ExampleTeam/Capix/blob/master/example_cape.png?raw=true", "https://raw.githubusercontent.com/ExampleTeam/Example/master/namelist.txt");
    }
}
```
---
# ModPacker

You can create cape using DataPack and ResourcePack

For DataPack Example
```
data/example_cape/capes/example_cape.json
pack.mcmeta
```
```json
{
  "name": "Example Cape",
  "texture": "example_cape:textures/capes/example_cape.png"
}
```

For ResourcePack Example
```
assets/example_cape/textures/capes/example_cape.png
pack.mcmeta
```

---
### Source
* [Source](https://github.com/ChorusTeam/Capix)

### Report Issues
* [Report Issues](https://github.com/ChorusTeam/Capix/issues)

# This mod is not affiliated with Mojang, Optifine, LabyMod, Capes, or the MinecraftCapes Mod
