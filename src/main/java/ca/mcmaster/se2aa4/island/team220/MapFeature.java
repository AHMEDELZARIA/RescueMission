package ca.mcmaster.se2aa4.island.team220;

// Need some sort of mechanism that groups all different land biomes under the enum constant LAND for example
public enum MapFeature {
    OCEAN,
    CREEK,
    EMERGENCYSITE,
    LAND,
    UNKNOWN; // Used when we discover a new coordinate, but haven't scanned to know what biome it is
}
