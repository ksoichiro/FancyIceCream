# ![](assets/logo.png) Fancy Ice Cream

[![Buy Me a Coffee](https://img.shields.io/badge/Buy%20Me%20a%20Coffee-support-orange?style=flat&logo=buymeacoffee)](https://www.buymeacoffee.com/ksoichiro)

A Minecraft mod to add ice cream recipes and decorative ice cream stands.

![](assets/screenshot.png)

## Supported Loaders

- [Minecraft Forge](https://files.minecraftforge.net/net/minecraftforge/forge/) (all versions)
- [Fabric](https://fabricmc.net/) (1.20.4+)
- [NeoForge](https://neoforged.net/) (1.21.1+)

## Supported Minecraft Versions

This mod supports the following Minecraft versions (all managed in a single branch):

| Minecraft Version | Forge | Fabric | NeoForge |
| --- | :---: | :---: | :---: |
| 1.21.11 | Yes | Yes | Yes |
| 1.21.10 | Yes | Yes | Yes |
| 1.21.9 | Yes | Yes | Yes |
| 1.21.8 | Yes | Yes | Yes |
| 1.21.7 | Yes | Yes | Yes |
| 1.21.6 | Yes | Yes | Yes |
| 1.21.5 | Yes | Yes | Yes |
| 1.21.4 | Yes | Yes | Yes |
| 1.21.3 | Yes | Yes | Yes |
| 1.21.1 | Yes | Yes | Yes |
| 1.20.6 | Yes | Yes | - |
| 1.20.4 | Yes | Yes | - |
| 1.20.2 | Yes | - | - |
| 1.20.1 | Yes | - | - |
| 1.19.4 | Yes | - | - |
| 1.19.3 | Yes | - | - |
| 1.19 | Yes | - | - |
| 1.18.2 | Yes | - | - |
| 1.18 | Yes | - | - |
| 1.17.1 | Yes | - | - |
| 1.16.5 | Yes | - | - |

## Build

```bash
# Build for a specific version (default: 1.21.9)
./gradlew build -Ptarget_mc_version=1.21.9

# Build all supported versions
./gradlew buildAll

# Collect release JARs from all versions
./gradlew collectJars

# Full release (clean, build all, collect)
./gradlew release
```

## Project Structure

```
FancyIceCream/
├── common/
│   ├── shared/          # Shared source across versions
│   └── <VERSION>/       # Version-specific common modules
├── forge/
│   └── <VERSION>/       # Forge loader modules per version
├── fabric/
│   ├── base/            # Shared Fabric base module
│   └── <VERSION>/       # Fabric loader modules per version (1.20.6+)
├── neoforge/
│   ├── base/            # Shared NeoForge base module
│   └── <VERSION>/       # NeoForge loader modules per version (1.21.1+)
└── props/
    └── <VERSION>.properties  # Per-version build properties
```

The build system dynamically selects the appropriate modules based on the `target_mc_version` property. Each version's `props/<VERSION>.properties` specifies `enabled_platforms` to determine which loaders are built.

## Items

### Ice Cream

| Item | Name | Description |
| --- |--- | --- |
| ![](common/1.21.9/src/main/resources/assets/fancyicecream/textures/item/apple_ice_cream.png)| Apple Ice Cream | |
| ![](common/1.21.9/src/main/resources/assets/fancyicecream/textures/item/choco_chip_ice_cream.png) | Choco Chip Ice Cream | |
| ![](common/1.21.9/src/main/resources/assets/fancyicecream/textures/item/chocolate_ice_cream.png) | Chocolate Ice Cream | |
| ![](common/1.21.9/src/main/resources/assets/fancyicecream/textures/item/glow_berry_ice_cream.png) | Glow Berry Ice Cream | Available from Minecraft 1.17 |
| ![](common/1.21.9/src/main/resources/assets/fancyicecream/textures/item/golden_apple_ice_cream.png) | Golden Apple Ice Cream | |
| ![](common/1.21.9/src/main/resources/assets/fancyicecream/textures/item/honey_ice_cream.png) | Honey Ice Cream | |
| ![](common/1.21.9/src/main/resources/assets/fancyicecream/textures/item/vanilla_ice_cream.png) | Vanilla Ice Cream | |
| ![](common/1.21.9/src/main/resources/assets/fancyicecream/textures/item/sweet_berry_ice_cream.png)| Sweet Berry Ice Cream | |

### Ice Cream Stand

| Item | Name | Description |
| --- | --- | --- |
| ![](common/1.21.9/src/main/resources/assets/fancyicecream/textures/item/ice_cream_stand.png) | Ice Cream Stand | It can hold one ice cream. |
| ![](common/1.21.9/src/main/resources/assets/fancyicecream/textures/item/triple_ice_cream_stand.png)| Triple Ice Cream Stand | It can hold 3 ice creams. |

## License

LGPL-3.0 - See [LICENSE.txt](LICENSE.txt) for details.
