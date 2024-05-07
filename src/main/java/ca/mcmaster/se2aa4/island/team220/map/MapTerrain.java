package ca.mcmaster.se2aa4.island.team220.map;

/**
 * Enum representing the different terrains, that concern the exploration
 */
public enum MapTerrain {
    OCEAN,
    LAND,
    OUTOFBOUNDS,
    UNKNOWN; // Used when drone discovers a new coordinate, but hasn't scanned to discover the terrain yet
}
