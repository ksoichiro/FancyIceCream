# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Minecraft Forge mod called "Fancy Ice Cream" that adds various ice cream items and decorative stands to Minecraft. The mod supports multiple Minecraft versions (1.20.1 - 1.21.9) using a multi-version build structure where each version has its own module directory.

## Build System

- **Build tool**: Gradle with ForgeGradle plugin (multi-project)
- **Java version**: Java 21
- **Build configuration**: Root `build.gradle` + per-version `forge-<VERSION>/build.gradle`
- **Version properties**: `props/<VERSION>.properties` for each supported Minecraft version

### Key Build Commands

```bash
# Build for a specific version (default: 1.21.9)
./gradlew build -Ptarget_mc_version=1.21.9

# Clean and build
./gradlew clean build -Ptarget_mc_version=1.21.9

# Run Minecraft client with mod for testing
./gradlew :forge:runClient -Ptarget_mc_version=1.21.9

# Build all supported versions
./gradlew buildAll

# Collect release JARs from all versions
./gradlew collectJars

# Clean all versions
./gradlew cleanAll

# Full release (clean, build all, collect)
./gradlew release
```

## Project Structure

### Multi-Version Layout
```
FancyIceCream/
├── settings.gradle              # Dynamic module selection based on target_mc_version
├── build.gradle                 # Root: plugin declarations, subproject common settings
├── gradle.properties            # Default target, plugin versions, mod info
├── gradle/multi-version-tasks.gradle  # buildAll, cleanAll, collectJars, release
├── props/
│   ├── 1.21.1.properties       # Version-specific: minecraft_version, forge_version, etc.
│   └── ...
├── forge-1.21.1/
│   ├── build.gradle
│   └── src/main/java/...
│   └── src/main/resources/...
├── forge-1.21.3/ ... forge-1.21.9/
└── mcmodsrepo/                  # Local Maven repository
```

### Core Package Structure
- `com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod` - Main mod class with mod initialization
- `com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems` - Item registration using DeferredRegister
- `com.ksoichiro.mcmod.fancyicecream.item.*` - Individual ice cream item classes
- `com.ksoichiro.mcmod.fancyicecream.entity.decoration.*` - Entity renderers for ice cream stands

### Configuration Files
- `gradle.properties` - JVM settings, mod info, plugin versions, default target version
- `props/<VERSION>.properties` - Per-version: minecraft_version, forge_version, forge_major_version, enabled_platforms
- `forge-<VERSION>/src/main/resources/META-INF/mods.toml` - Mod metadata and dependencies (per-version)

## Mod Architecture

The mod follows standard Forge patterns:

1. **Registration System**: Uses DeferredRegister for items and entities
2. **Item System**: Extends vanilla Item class with custom food properties and effects
3. **Entity System**: Custom decoration entities (IceCreamStand, TripleIceCreamStand) with renderers
4. **Creative Tab**: Custom creative tab for grouping mod items

### Version Groups
- **1.21.6-1.21.9** (Forge 56-59): BusGroup, eventbus-validator, latest API
- **1.21.3-1.21.5** (Forge 53-55): IceCreamStandRenderState, IEventBus
- **1.21.1** (Forge 52): Old rendering, old food system, ModelEvent.RegisterAdditional
- **1.20.6** (Forge 50): Java 21, sourceSets merge, no reobf
- **1.20.1-1.20.4** (Forge 47-49): Java 17, reobfJar required

### Key Differences Between Versions (conditional in build.gradle)
- Forge 56+ (`forge_major_version >= 56`): `eventbus-validator` annotation processor, `eventbus.api.strictRuntimeChecks`
- Forge 50+ (`forge_major_version >= 50`): `reobf = false` (no reobfuscation needed)
- Forge 49+ (`forge_major_version >= 49`): `sourceSets.each` merge, `copyGeneratedResources` task, `copyIdeResources`
- Forge < 50: `jar.finalizedBy('reobfJar')`
- Forge < 49: `sourceSets.main.resources { srcDir }` for generated resources
- ParchmentMC currently disabled for all versions due to compilation issues in multi-project setup

## Development Notes

- All ice cream items provide food effects using the Effects class
- Stand entities use custom renderers for 3D display
- Recipes are auto-generated through data generation
- The project publishes to a local maven repository at `mcmodsrepo/`

## Testing

Test the mod using the run configurations:
- `./gradlew :forge:runClient -Ptarget_mc_version=<VERSION>` for client-side testing
- `./gradlew :forge:runServer -Ptarget_mc_version=<VERSION>` for server-side testing

## Version Updates

For updating the mod to new Minecraft versions, see the detailed instructions in @UPDATE.md. The update process involves:

1. Create `props/<NEW_VERSION>.properties` with appropriate forge_version and forge_major_version
2. Create `forge-<NEW_VERSION>/` directory with source code adapted from the nearest existing version
3. Create `forge-<NEW_VERSION>/build.gradle` (copy from existing version)
4. Update `forge-<NEW_VERSION>/src/main/resources/META-INF/mods.toml` with correct version ranges
5. Add the new version to `gradle/multi-version-tasks.gradle` supportedVersions list
6. Build and test: `./gradlew clean build -Ptarget_mc_version=<NEW_VERSION>`

Key points for version updates:
- Use recommended Forge versions when available
- Compare with ItemFrame class implementation when fixing IceCreamStand issues
- Reference version-specific Forge documentation for API changes
- Compare with MDK build files if build issues occur
