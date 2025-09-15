# FancyIceCream Architectury Migration Plan

## Overview

This document outlines the migration plan for transforming the current FancyIceCream project from Forge-only to a multi-platform setup using Architectury, supporting Forge, NeoForge, and potentially Fabric/Quilt in the future.

**Goals:**
- Simultaneous support for multiple mod loaders (Forge, NeoForge)
- Multi-version Minecraft support within the same branch
- Foundation for future Fabric/Quilt support
- Reduced code duplication and improved maintainability

## Current Project Analysis

### Current Structure
```
src/main/java/com/ksoichiro/mcmod/fancyicecream/
├── main/FancyIceCreamMod.java          # Mod initialization class (Forge-dependent)
├── registry/FancyIceCreamModItems.java # Item registration (using DeferredRegister)
├── item/                               # Item implementations
├── entity/                             # Entity implementations
└── common/ModTags.java                 # Common definitions
```

### Forge-Specific Dependencies
- `@Mod` annotation
- `FMLJavaModLoadingContext`
- `DeferredRegister` system
- `ForgeRegistries`

## New Project Structure

### 3-Layer Multi-Module Configuration (Architectury Only)
```
FancyIceCream/
├── build.gradle                       # ルートbuild.gradle（Architectury設定）
├── settings.gradle                    # サブプロジェクト定義
├── gradle.properties                  # 共通プロパティ
├── common/                           # Fully common code (version & loader independent)
│   ├── build.gradle
│   └── src/main/java/com/ksoichiro/mcmod/fancyicecream/
│       ├── core/                     # Basic definitions, utilities
│       ├── item/                     # Version-independent item definitions
│       └── entity/                   # Version-independent entity definitions
└── versions/                         # Version-specific implementations
    ├── v1.20.2/                     # For Minecraft 1.20.2
    │   ├── common/                  # 1.20.2 common (loader-independent)
    │   │   ├── build.gradle
    │   │   ├── src/main/java/       # 1.20.2-specific API adaptations
    │   │   └── src/main/resources/  # assets, data
    │   ├── forge/                   # 1.20.2 Forge implementation
    │   │   ├── build.gradle
    │   │   └── src/main/java/
    │   └── neoforge/               # 1.20.2 NeoForge implementation
    │       ├── build.gradle
    │       └── src/main/java/
    ├── v1.21.3/                     # For Minecraft 1.21.3
    │   ├── common/
    │   ├── forge/
    │   └── neoforge/
    └── v1.21.8/                     # Current 1.21.8 (migration starting point)
        ├── common/
        ├── forge/
        ├── neoforge/
        └── fabric/                  # Future support
```

### 3-Layer Structure Explanation

1. **Common Layer (common/)**
   - Code shared across all versions and loaders
   - Basic item definitions, utility classes, etc.
   - Parts that don't depend on Minecraft API

2. **Version-Specific Layer (versions/vX.Y.Z/common/)**
   - Common code for specific Minecraft versions
   - Adaptation to version-specific API changes
   - Resource placement (assets, data)

3. **Loader-Specific Layer (versions/vX.Y.Z/{forge,neoforge}/)**
   - Implementation for specific loaders
   - Entry points, registration system implementations

## Work TODO (Progress Management)

### Phase 1: Project Foundation Setup
- [ ] **1.1** Create 3-layer directory structure
- [ ] **1.2** Create settings.gradle (multi-version, multi-loader setup)
- [ ] **1.3** Create root build.gradle (Architectury Plugin setup)
- [ ] **1.4** Create build.gradle for each layer (common, versions/v1.21.8/*)
- [ ] **1.5** Update gradle.properties (Architectury support)

### Phase 2: Common Layer Construction
- [ ] **2.1** Extract and move fully common code to common/
- [ ] **2.2** Create version-independent item definitions
- [ ] **2.3** Create version-independent entity definitions
- [ ] **2.4** Organize common utility classes
- [ ] **2.5** Configure common/build.gradle

### Phase 3: Version-Specific Layer Construction (1.21.8)
- [ ] **3.1** Build versions/v1.21.8/common/
- [ ] **3.2** Move and adapt current source code to v1.21.8/common/
- [ ] **3.3** Move resources (assets, data) to v1.21.8/common/
- [ ] **3.4** Separate 1.21.8-specific API adaptation code
- [ ] **3.5** Configure v1.21.8/common/build.gradle

### Phase 4: Loader-Specific Implementation (1.21.8)
- [ ] **4.1** Create versions/v1.21.8/forge/ entry point
- [ ] **4.2** Create versions/v1.21.8/neoforge/ entry point
- [ ] **4.3** Implementation separation using @ExpectPlatform annotation
- [ ] **4.4** Implement Forge registration system
- [ ] **4.5** Implement NeoForge registration system

### Phase 5: Build Configuration and Testing
- [ ] **5.1** Configure dependencies for each module
- [ ] **5.2** Unify Mappings configuration (ParchmentMC support)
- [ ] **5.3** Platform-specific Run configuration
- [ ] **5.4** Migrate Data Generation configuration
- [ ] **5.5** Verify selective build functionality

### Phase 6: Validation and Testing
- [ ] **6.1** Build and runtime test on 1.21.8 Forge
- [ ] **6.2** Build and runtime test on 1.21.8 NeoForge
- [ ] **6.3** Verify functionality of all items
- [ ] **6.4** Verify entity behavior (ice cream stands)
- [ ] **6.5** Verify Data Generation functionality

### Phase 7: Additional Version Support (Optional)
- [ ] **7.1** Add versions/v1.21.3/
- [ ] **7.2** Add versions/v1.20.2/
- [ ] **7.3** Handle differences between versions
- [ ] **7.4** Test on each version

### Phase 8: Documentation Updates
- [ ] **8.1** Update README.md (multi-version, multi-platform support explanation)
- [ ] **8.2** Update CLAUDE.md (new build commands, etc.)
- [ ] **8.3** Update UPDATE.md (update procedures in Architectury environment)

## Technical Details

### Key Changes

#### Build Configuration
- **Before:** ForgeGradle + net.minecraftforge.gradle plugin
- **After:** Architectury Plugin + dev.architectury.loom plugin

#### Project Structure
- **Before:** Single module (Forge-only)
- **After:** 3-layer multi-module (common, version-specific, loader-specific)

#### Version Management
- **Before:** Branch-based management (1.19, 1.20.2, 1.21.8, etc.)
- **After:** Multi-version support within single branch

### Gradle Configuration Examples

#### settings.gradle
```gradle
rootProject.name = 'FancyIceCream'

// Fully common module
include 'common'

// Version-specific modules (starting with 1.21.8)
include 'versions:v1.21.8:common'
include 'versions:v1.21.8:forge'
include 'versions:v1.21.8:neoforge'

// Additional versions (future)
include 'versions:v1.21.3:common'
include 'versions:v1.21.3:forge'
include 'versions:v1.21.3:neoforge'
```

#### Root build.gradle
```gradle
plugins {
    id 'architectury-plugin' version '3.4-SNAPSHOT'
    id 'dev.architectury.loom' version '1.7-SNAPSHOT' apply false
}

allprojects {
    apply plugin: 'java'
    apply plugin: 'architectury-plugin'
    apply plugin: 'maven-publish'

    group = 'com.ksoichiro.mcmod.fancyicecream'
    version = '0.0.3'
    base.archivesName = 'fancyicecream'
}

subprojects {
    apply plugin: 'dev.architectury.loom'

    loom {
        silentMojangMappingsLicense()
    }

    java.toolchain.languageVersion = JavaLanguageVersion.of(21)
}
```

#### versions/v1.21.8/forge/build.gradle
```gradle
architectury {
    platformSetupLoomIde()
    forge()
}

dependencies {
    forge "net.minecraftforge:forge:1.21.8-58.1.0"

    common(project(path: ":common", configuration: "namedElements")) { transitive false }
    shadowCommon(project(path: ":common", configuration: "transformProductionForge")) { transitive false }

    common(project(path: ":versions:v1.21.8:common", configuration: "namedElements")) { transitive false }
    shadowCommon(project(path: ":versions:v1.21.8:common", configuration: "transformProductionForge")) { transitive false }
}
```

### Code Structure Examples

#### Common Layer (common/)
```java
// Fully version-independent
public class FancyIceCreamItems {
    public static final String VANILLA_ICE_CREAM = "vanilla_ice_cream";
    public static final String APPLE_ICE_CREAM = "apple_ice_cream";
    // Item name constant definitions only
}
```

#### Version-Specific Layer (versions/v1.21.8/common/)
```java
// Using 1.21.8-specific Minecraft API
public class ItemRegistry {
    @ExpectPlatform
    public static void registerItems() {
        throw new AssertionError();
    }
}
```

#### Loader-Specific Layer (versions/v1.21.8/forge/)
```java
// Forge-specific implementation
@Mod(FancyIceCreamMod.MOD_ID)
public class FancyIceCreamModForge {
    public FancyIceCreamModForge() {
        ItemRegistry.registerItems();
    }
}

public class ItemRegistryImpl {
    public static void registerItems() {
        // Forge DeferredRegister implementation
    }
}
```

### Version Management Benefits

1. **Gradual Migration Possible**
   - Start from current 1.21.8
   - Add other versions as needed
   - Maintain independence of each version

2. **Selective Build**
   - Build only specific version/loader combinations
   - `./gradlew :versions:v1.21.8:forge:build`
   - `./gradlew :versions:v1.21.8:neoforge:build`

3. **Optimized Code Sharing**
   - Truly common parts in `common/`
   - Version-specific changes in `versions/vX.Y.Z/common/`
   - Loader-specific in respective loader modules

4. **Maintainability**
   - Easy to end support for old versions
   - Easy to add new versions
   - Minimize impact between versions

## Risks and Considerations

### Potential Issues
1. **API Differences:** Handling differences between Forge and NeoForge APIs
2. **Version Differences:** Adapting to Minecraft API changes
3. **Build Complexity:** Increased complexity from multi-module configuration
4. **Dependency Management:** Complex dependencies in 3-layer structure

### Mitigation Strategies
1. Abstraction using Architectury API's @ExpectPlatform
2. Risk minimization through gradual migration (starting with 1.21.8)
3. Clear separation of responsibilities at each layer
4. Thorough testing and documentation

## Completion Criteria

✅ **Project Completion Definition:**
- [ ] All current functionality works on 1.21.8 Forge
- [ ] All current functionality works on 1.21.8 NeoForge
- [ ] Selective build works (specific version/loader only)
- [ ] 3-layer structure functions correctly
- [ ] Build process is stable
- [ ] Documentation is updated
- [ ] Foundation for future version additions and Fabric support is established

## Implementation Strategy

### Gradual Migration Approach
1. **Stage 1**: Migrate current 1.21.8 to Architectury 3-layer structure
2. **Stage 2**: Add NeoForge support (1.21.8)
3. **Stage 3**: Add other Minecraft versions (1.21.3, 1.20.2, etc.)
4. **Stage 4**: Prepare for future Fabric support

### Selective Execution During Development
```bash
# Development with specific version/loader
./gradlew :versions:v1.21.8:forge:runClient
./gradlew :versions:v1.21.8:neoforge:runClient

# Build specific version/loader
./gradlew :versions:v1.21.8:forge:build
./gradlew :versions:v1.21.8:neoforge:build

# Build all versions/loaders
./gradlew build
```

## Work Log

### Progress Records
*Before work begins*

### Issues and Decisions
*Record issues discovered during work and technical decisions made*

### Reference Links
- [Architectury API](https://github.com/architectury/architectury-api)
- [Architectury Plugin](https://github.com/architectury/architectury-plugin)
- [Template Generator](https://generate.architectury.dev/)
- [Architectury Tutorial 2025](https://larsensmods.de/2025/architectury-introduction/)
