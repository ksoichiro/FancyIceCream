# ![](assets/logo.png) Fancy Ice Cream

[![Buy Me a Coffee](https://img.shields.io/badge/Buy%20Me%20a%20Coffee-support-orange?style=flat&logo=buymeacoffee)](https://www.buymeacoffee.com/ksoichiro)

A Minecraft mod to add ice cream recipes.

![](assets/screenshot.png)

## Prerequisites

- [Minecraft Forge](https://files.minecraftforge.net/net/minecraftforge/forge/)

## Supported Minecraft Versions

This mod supports the following Minecraft versions (all managed in a single `main` branch):

1.16.5, 1.17.1, 1.18, 1.18.2, 1.19, 1.19.3, 1.19.4, 1.20.1, 1.20.2, 1.20.4, 1.20.6, 1.21.1, 1.21.3, 1.21.4, 1.21.5, 1.21.6, 1.21.7, 1.21.8, 1.21.9

Each version has its own module directory (`forge/<VERSION>/`) with version-specific source code and resources. The build system dynamically selects the appropriate module based on the `target_mc_version` property.

## Build

```bash
# Build for a specific version (default: 1.21.9)
./gradlew build -Ptarget_mc_version=1.21.9

# Build all supported versions
./gradlew buildAll
```

## Items

### Ice Cream

| Item | Name | Description |
| --- |--- | --- |
| ![](forge/1.21.9/src/main/resources/assets/fancyicecream/textures/item/apple_ice_cream.png)| Apple Ice Cream | |
| ![](forge/1.21.9/src/main/resources/assets/fancyicecream/textures/item/choco_chip_ice_cream.png) | Choco Chip Ice Cream | |
| ![](forge/1.21.9/src/main/resources/assets/fancyicecream/textures/item/chocolate_ice_cream.png) | Chocolate Ice Cream | |
| ![](forge/1.21.9/src/main/resources/assets/fancyicecream/textures/item/glow_berry_ice_cream.png) | Glow Berry Ice Cream | Available from Minecraft 1.17 |
| ![](forge/1.21.9/src/main/resources/assets/fancyicecream/textures/item/golden_apple_ice_cream.png) | Golden Apple Ice Cream | |
| ![](forge/1.21.9/src/main/resources/assets/fancyicecream/textures/item/honey_ice_cream.png) | Honey Ice Cream | |
| ![](forge/1.21.9/src/main/resources/assets/fancyicecream/textures/item/vanilla_ice_cream.png) | Vanilla Ice Cream | |
| ![](forge/1.21.9/src/main/resources/assets/fancyicecream/textures/item/sweet_berry_ice_cream.png)| Sweet Berry Ice Cream | |

### Ice Cream Stand

| Item | Name | Description |
| --- | --- | --- |
| ![](forge/1.21.9/src/main/resources/assets/fancyicecream/textures/item/ice_cream_stand.png) | Ice Cream Stand | It can hold one ice cream. |
| ![](forge/1.21.9/src/main/resources/assets/fancyicecream/textures/item/triple_ice_cream_stand.png)| Triple Ice Cream Stand | It can hold 3 ice creams. |

