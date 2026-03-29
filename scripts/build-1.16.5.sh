#!/bin/bash
# Build Forge 1.16.5 using Gradle 8 + FG6 + Java 17
# Gradle 9 + FG7 cannot produce a working jar for 1.16.5 (no reobfJar support).
# Requires: mise with gradle 8.x and java temurin-17.x installed

set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROJECT_DIR="$(dirname "$SCRIPT_DIR")"

# Use Java 17 (Gradle 8 does not support Java 25)
JAVA17_HOME="$(mise where java temurin-17.0.17+10 2>/dev/null)" || {
    echo "Error: Java 17 not found. Install with: mise install java temurin-17"
    exit 1
}
export JAVA_HOME="$JAVA17_HOME"

# Use Gradle 8 from mise (not the project wrapper which is Gradle 9)
GRADLE_CMD="$(mise which gradle 2>/dev/null)" || {
    echo "Error: Gradle not found in mise. Install with: mise install gradle 8"
    exit 1
}

echo "Building 1.16.5 with:"
echo "  JAVA_HOME=$JAVA_HOME"
echo "  Gradle: $GRADLE_CMD ($(gradle --version 2>&1 | grep '^Gradle' || true))"
echo ""

cd "$PROJECT_DIR"
"$GRADLE_CMD" clean build -Ptarget_mc_version=1.16.5 -x test "$@"
