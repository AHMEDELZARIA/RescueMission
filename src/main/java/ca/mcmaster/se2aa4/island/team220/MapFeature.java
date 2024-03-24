package ca.mcmaster.se2aa4.island.team220;

import java.util.Map;

// Need some sort of mechanism that groups all different land biomes under the enum constant LAND for example
public enum MapFeature {
    OCEAN,
    LAND,
    OUTOFBOUNDS,
    UNKNOWN; // Used when we discover a new coordinate, but haven't scanned to know what
    // biome it is
}
