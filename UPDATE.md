# Update

This document explains the update process for adapting this Mod to new versions of Minecraft.

## Multi-Version Structure

This project uses a multi-version build structure. Each Minecraft version has its own module directory (`forge-<VERSION>/`) with version-specific source code and resources. Version configuration is stored in `props/<VERSION>.properties`.

## Adding a New Minecraft Version

### 1. Create Version Properties

Create `props/<NEW_VERSION>.properties`:

```properties
minecraft_version=<NEW_VERSION>
java_version=21
forge_version=<NEW_VERSION>-<FORGE_VERSION>
forge_major_version=<FORGE_MAJOR>
enabled_platforms=forge
```

### 2. Forge Version Selection

The Mod is based on [Minecraft Forge](https://files.minecraftforge.net/net/minecraftforge/forge/), so the Forge version corresponding to the Minecraft version needs to be specified.
Usually, multiple Forge versions are provided for a given Minecraft version.
If there is a version marked as "Download Recommended", select that version.
If no such version exists, select the latest version.

### 3. ParchmentMC (Optional)

ParchmentMC is currently disabled for all versions due to compilation issues (Direction switch expressions fail) in the multi-project setup. If this is resolved in the future, add `parchment_mc_version=YYYY.MM.DD-<MC_VERSION>` to the props file.

To get the exact version, check the Maven metadata XML:
```
https://ldtteam.jfrog.io/artifactory/parchmentmc-internal/org/parchmentmc/data/parchment-<MC_VERSION>/maven-metadata.xml
```

### 4. Create Module Directory

Create `forge-<NEW_VERSION>/` by copying from the nearest existing version:

```bash
cp -r forge-<NEAREST_VERSION>/src forge-<NEW_VERSION>/src
cp forge-<NEAREST_VERSION>/build.gradle forge-<NEW_VERSION>/build.gradle
```

### 5. Update Source Code

Adapt the source code for API changes. Key differences between version groups:
- **Forge 56+** (1.21.6+): Uses BusGroup, requires eventbus-validator annotation processor
- **Forge 53-55** (1.21.3-1.21.5): Uses IEventBus, IceCreamStandRenderState
- **Forge 52** (1.21.1): Old rendering system, old food system, ModelEvent.RegisterAdditional

### 6. Update Mod Metadata

Update `forge-<NEW_VERSION>/src/main/resources/META-INF/mods.toml`:

1. **loaderVersion**: Match the Forge major version (e.g., `"[59,)"` for Forge 59.x.x)
2. **Forge dependency versionRange**: Match the Forge major version
3. **Minecraft dependency versionRange**: Match the target Minecraft version

### 7. Register in Multi-Version Tasks

Add the new version to `supportedVersions` in `gradle/multi-version-tasks.gradle`.

### 8. Build and Test

```bash
./gradlew clean build -Ptarget_mc_version=<NEW_VERSION>
./gradlew :forge:runClient -Ptarget_mc_version=<NEW_VERSION>
```

Verify:
- Minecraft starts properly
- No mod errors are displayed
- Create a new Creative Mode world or open an existing world and verify normal operation
  - Items are registered in the Creative Tab
  - Icons and titles are displayed in the Creative Tab
  - Items can be selected from the Creative Tab and placed/used
  - Some placeable and rotatable items should maintain their rotation state when the world is saved after rotation and then reopened

## Changes Due to Minecraft Forge Updates

With Minecraft Forge updates, APIs may change even with minor version updates.
As needed, update existing code referencing the [Minecraft Forge documentation](https://docs.minecraftforge.net/en/latest/).
This documentation is separated by version.

Also, comparing the implementation of related classes before and after version upgrades can sometimes help find implementation methods.
For example, the `IceCreamStand` class has many implementations similar to the `ItemFrame` class.
Therefore, if compilation errors occur in the `IceCreamStand` class, comparing the implementation changes of the `ItemFrame` class before and after the update can sometimes help find the appropriate update method.

## Troubleshooting

If the build doesn't work well and it seems to be a build issue rather than a problem with the Mod's source code, it's good to download the MDK zip file for the corresponding version and compare build-related files like build.gradle.
Settings may have been significantly updated.
MDK can be downloaded from the following location.
Note that pages are separated by version.
https://files.minecraftforge.net/net/minecraftforge/forge/
Extract the downloaded MDK zip file and compare files like build.gradle and gradle/wrapper/gradle-wrapper.properties contained within.
