package ca.mcmaster.se2aa4.island.team220.map;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps all possible biomes to either LAND or OCEAN MapTerrain for simplicity
 */
public class BiomeMapper {
    private Map<String, MapTerrain> biomeMap = new HashMap<>();

    /**
     * Create a BiomeMapper that maps all possible biomes to a MapTerrain
     */
    public BiomeMapper() {
        // Treat all the following biomes as LAND
        biomeMap.put("GRASSLAND", MapTerrain.LAND);
        biomeMap.put("BEACH", MapTerrain.LAND);
        biomeMap.put("MANGROVE", MapTerrain.LAND);
        biomeMap.put("TROPICAL_RAIN_FOREST", MapTerrain.LAND);
        biomeMap.put("TROPICAL_SEASONAL_FOREST", MapTerrain.LAND);
        biomeMap.put("TEMPERATE_DECIDUOUS_FOREST", MapTerrain.LAND);
        biomeMap.put("TEMPERATE_RAIN_FOREST", MapTerrain.LAND);
        biomeMap.put("TEMPERATE_DESERT", MapTerrain.LAND);
        biomeMap.put("TAIGA", MapTerrain.LAND);
        biomeMap.put("SNOW", MapTerrain.LAND);
        biomeMap.put("TUNDRA", MapTerrain.LAND);
        biomeMap.put("ALPINE", MapTerrain.LAND);
        biomeMap.put("GLACIER", MapTerrain.LAND);
        biomeMap.put("SHRUBLAND", MapTerrain.LAND);
        biomeMap.put("SUB_TROPICAL_DESERT", MapTerrain.LAND);
        // Treat all the following biomes as OCEAN
        biomeMap.put("OCEAN", MapTerrain.OCEAN);
        biomeMap.put("LAKE", MapTerrain.OCEAN);
    }

    /**
     * Given a biome, determines whether it is a LAND MapTerrain or not.
     * @param biome String name of a biome
     * @return true if biome is LAND or false otherwise
     */
    public Boolean isLand(String biome) {
        return this.biomeMap.get(biome) == MapTerrain.LAND;
    }

    /**
     * Given a biome, determines whether it is a OCEAN MapTerrain or not.
     * @param biome String name of a biome
     * @return true if biome is OCEAN or false otherwise
     */
    public Boolean isOCEAN(String biome) {
        return this.biomeMap.get(biome) == MapTerrain.OCEAN;
    }
}