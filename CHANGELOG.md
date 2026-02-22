# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.1.0] - 2026-02-22

### Added

#### New Items
- **Sweet Berry Ice Cream**: New ice cream item available across all supported versions

#### Minecraft Version Support
- Minecraft 1.16.5, 1.17.1, 1.18, 1.18.2, 1.19, 1.19.3, 1.19.4
- Minecraft 1.20.1, 1.20.2, 1.20.4, 1.20.6
- Minecraft 1.21.10, 1.21.11

#### Multi-Loader Support
- Fabric support for Minecraft 1.20.1, 1.20.2, 1.20.4, 1.20.6, 1.21.1-1.21.11
- NeoForge support for Minecraft 1.21.1-1.21.11

#### Development & Build
- Multi-version build structure supporting 21 Minecraft versions in a single branch
- `buildAll`, `cleanAll`, `collectJars`, `release` Gradle tasks
- DataGenerator-based resource generation for 1.20.x versions

### Changed
- Restructured project from single-version to multi-version, multi-loader architecture
- Restructured loader directories into hierarchical layout (`forge/1.21.9/` instead of `forge-1.21.9/`)

[Unreleased]: https://github.com/ksoichiro/FancyIceCream/compare/v0.1.0...HEAD
[0.1.0]: https://github.com/ksoichiro/FancyIceCream/compare/1.21.9-0.0.2...v0.1.0
