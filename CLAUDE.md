# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Minecraft Forge mod called "Fancy Ice Cream" that adds various ice cream items and decorative stands to Minecraft. The mod supports multiple Minecraft versions (1.19 - 1.20.2) and uses Minecraft Forge as its modding framework.

## Build System

- **Build tool**: Gradle with ForgeGradle plugin
- **Java version**: Java 17 (required for Minecraft 1.18+)
- **Build configuration**: `build.gradle` with external properties in `gradle/props.gradle`

### Key Build Commands

```bash
# Build the mod
./gradlew build

# Clean build directory  
./gradlew clean

# Run Minecraft client with mod for testing
./gradlew runClient

# Run Minecraft server with mod for testing
./gradlew runServer

# Generate data files (recipes, models, etc.)
./gradlew runData

# Run game test server
./gradlew runGameTestServer

# Publish to local maven repository
./gradlew publish
```

## Project Structure

### Core Package Structure
- `com.ksoichiro.mcmod.fancyicecream.main.FancyIceCreamMod` - Main mod class with mod initialization
- `com.ksoichiro.mcmod.fancyicecream.registry.FancyIceCreamModItems` - Item registration using DeferredRegister
- `com.ksoichiro.mcmod.fancyicecream.item.*` - Individual ice cream item classes
- `com.ksoichiro.mcmod.fancyicecream.entity.decoration.*` - Entity renderers for ice cream stands

### Configuration Files
- `gradle/props.gradle` - Version configuration (Minecraft, Forge, Parchment mappings)
- `src/main/resources/META-INF/mods.toml` - Mod metadata and dependencies
- `gradle.properties` - JVM settings (3GB heap required for decompilation)

### Resource Structure
- `src/main/resources/assets/fancyicecream/` - Client-side assets (textures, models, lang files)
- `src/main/resources/data/fancyicecream/` - Server-side data (recipes, tags, advancements)

## Mod Architecture

The mod follows standard Forge patterns:

1. **Registration System**: Uses DeferredRegister for items and entities
2. **Item System**: Extends vanilla Item class with custom food properties and effects
3. **Entity System**: Custom decoration entities (IceCreamStand, TripleIceCreamStand) with renderers
4. **Creative Tab**: Custom creative tab for grouping mod items

### Version Management
- Current branch supports Minecraft 1.20.2 with Forge 48.1.0
- Version-specific run configurations in `run/` directory
- Mod supports multiple Minecraft versions through different branches

## Development Notes

- The mod uses ParchmentMC mappings for better parameter names
- All ice cream items provide food effects using the Effects class
- Stand entities use custom renderers for 3D display
- Recipes are auto-generated through data generation
- The project publishes to a local maven repository at `mcmodsrepo/`

## Testing

Test the mod using the run configurations:
- `runClient` for client-side testing in development environment
- `runServer` for server-side testing
- Game saves are stored in version-specific directories under `run/`

## Version Updates

For updating the mod to new Minecraft versions, see the detailed instructions in @UPDATE.md. The update process involves:

1. **Version Configuration Updates**: Update `minecraftVersion`, `forgeVersion`, and `parchmentMCVersion` in `gradle/props.gradle`
2. **Build and Test**: Use `./gradlew clean` followed by `./gradlew runClient` to verify the update
3. **Functionality Testing**: Ensure all mod features work correctly in the new version
4. **API Compatibility**: Check for and resolve any Forge API changes that may affect the mod

Key points for version updates:
- Use recommended Forge versions when available
- **CRITICAL: Verify ParchmentMC mappings availability and get exact version**
  - The ParchmentMC website displays versions as image badges which makes it difficult to read version information programmatically
  - Follow the detailed steps in @UPDATE.md for accurate version confirmation via Maven metadata XML
- Compare with ItemFrame class implementation when fixing IceCreamStand issues
- Reference version-specific Forge documentation for API changes
- Compare with MDK build files if build issues occur
