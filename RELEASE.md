# Release Process

This document outlines the manual release process for the Fancy Ice Cream mod.

## Build Process

### 1. Stop Existing Processes
```bash
./gradlew --stop
```
Stop any existing Gradle daemon processes to ensure a clean build environment.

### 2. Clean Build and Publish
```bash
./gradlew clean publish
```
This command will:
- Clean previous build artifacts
- Build the mod JAR file
- Publish to the local Maven repository (`mcmodsrepo/`)

### 3. Verify JAR Contents
Check the generated JAR file to ensure it contains the correct files:

```bash
jar tvf mcmodsrepo/com/ksoichiro/mcmod/fancyicecream/fancyicecream-<MinecraftVersion>/<ModVersion>/fancyicecream-<MinecraftVersion>-<ModVersion>.jar
```

Verify that:
- No unnecessary files are included
- All required files are present (assets, data, META-INF, class files)

## Testing Process

### 1. Setup Test Environment
1. Create a new instance in Prism Launcher (or similar) with:
   - Target Minecraft version
   - Corresponding Forge version
2. Add the generated JAR file as a mod to the instance

### 2. World Creation and Basic Testing
1. Create a new world with the following settings:
   - **Game Mode**: Survival
   - **Difficulty**: Peaceful
   - **Allow Commands**: Enabled
   - **World Type**: Superflat

### 3. Item Testing
1. **Give Items and Check Advancements**:
   ```
   /give @s crafting_table
   ```
   - Give yourself various items using commands
   - Observe advancement notifications as items are obtained
   - Collect all mod items systematically

2. **Recipe Verification**:
   - Open inventory and verify recipes exist
   - Craft each item type to ensure recipes work correctly

3. **Advancement Completion**:
   - Open the advancements screen
   - Verify all mod advancements are achieved
   - Check that advancement icons and background images display correctly

### 4. Item Functionality Testing
1. **Ice Cream Items**:
   - Use (eat) ice cream items to verify they are consumable
   - For Golden Apple ice cream: verify that effects are applied when consumed

2. **Ice Cream Stands**:
   - Place Ice Cream Stand and Triple Ice Cream Stand items
   - Verify models display correctly
   - Place ice cream items on the stands
   - Confirm ice cream models appear correctly on stands
   - Rotate the stands and verify models display properly at different angles

### 5. Persistence Testing
1. **Save and Reload Test**:
   - Rotate stands to different angles
   - Save the world and exit
   - Reload the world
   - Verify that stand rotations are preserved correctly

## Distribution

Once all testing is completed successfully, the JAR file is ready for distribution.

## Notes

- This is currently a manual process performed by maintainers
- All testing steps must be completed before release
- Pay special attention to visual elements (textures, models, UI) during testing
- Ensure persistence of entity states (rotation angles) works correctly