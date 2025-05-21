# Capix
- A small API mod that allows Mod Developers to add capes to their mods

### Fabric Setup Example

```java

public class ExampleModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CapixApi.registerCape(ExampleMod.MOD_ID, "Example Cape", ResourceLocation.fromNamespaceAndPath("textures/example_cape.png"), "https://raw.githubusercontent.com/ExampleTeam/Example/master/namelist.txt");
    }
}
```

### NeoForge Setup Example
```java


@Mod("example_mod")
public class ExampleMod {

    public ExampleMod(IEventBus eventBus) {
        CapixApi.registerCape(ExampleMod.MOD_ID, "Example Cape", ResourceLocation.fromNamespaceAndPath("textures/example_cape.png"), "https://raw.githubusercontent.com/ExampleTeam/Example/master/namelist.txt");
    }
}
```

### Source
* [Source](https://github.com/ChorusTeam/Capix)

### Report Issues
* [Source](https://github.com/ChorusTeam/Capix/issues)

# This mod is not affiliated with Mojang, Optifine, LabyMod, Capes, or the MinecraftCapes Mod