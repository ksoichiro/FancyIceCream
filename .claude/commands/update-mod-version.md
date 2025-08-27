---
description: Update Minecraft Mod to specified version
argument-hint: <minecraft_version>
---

# Update Mod to Minecraft $ARGUMENTS

Follow the comprehensive update process detailed in @UPDATE.md for Minecraft version $ARGUMENTS.

## Quick Steps Summary

1. **Get Version Information** - Check ParchmentMC and Forge versions for $ARGUMENTS
2. **Update Configuration** - Modify @gradle/props.gradle with new versions  
3. **Update Metadata** - Update @src/main/resources/META-INF/mods.toml dependencies
4. **Build & Test** - Run `./gradlew clean && ./gradlew runClient`

## Key Files to Update

- @gradle/props.gradle: `minecraftVersion`, `forgeVersion`, `parchmentMCVersion`
- @src/main/resources/META-INF/mods.toml: `loaderVersion` and dependency ranges

For detailed instructions, version confirmation steps, and troubleshooting, see @UPDATE.md.
