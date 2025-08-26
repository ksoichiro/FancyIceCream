# Update

This document explains the update process for adapting this Mod to new versions of Minecraft.

## Minecraft Version Update

The Minecraft version is specified as `minecraftVersion` in @gradle/props.gradle, which needs to be updated.

## Forge Version Update

The Mod is based on [Minecraft Forge](https://files.minecraftforge.net/net/minecraftforge/forge/), so the Forge version corresponding to the Minecraft version also needs to be updated.
The Forge version is specified as `forgeVersion` in @gradle/props.gradle, which needs to be updated.
Usually, multiple Forge versions are provided for a given Minecraft version.
If there is a version marked as "Download Recommended", select that version.
If no such version exists, select the latest version.

## ParchmentMC Version Update

This repository uses [ParchmentMC](https://parchmentmc.org/), and can only be updated to versions listed in the [mappings](https://parchmentmc.org/docs/getting-started).
The ParchmentMC version is specified as `parchmentMCVersion` in @gradle/props.gradle, which needs to be updated.

## Build

For building, use gradlew. Clean previous build results with `clean`, then build the mod and run Minecraft with `runClient`.

Command examples:
```
./gradlew clean
./gradlew runClient
```

Execute runClient and verify that Minecraft starts and performs a functionality check.
Check the following points and debug if there are issues:

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
For example, explanations about Items for 1.20.x are written on [this page](https://docs.minecraftforge.net/en/1.20.x/items/).

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
