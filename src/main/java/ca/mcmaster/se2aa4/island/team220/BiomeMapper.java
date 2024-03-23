package ca.mcmaster.se2aa4.island.team220;

import java.util.HashMap;
import java.util.Map;

public class BiomeMapper {
    private Map<String, MapFeature> biomeMap = new HashMap<>();

    public BiomeMapper() {
        // Treat all the following biomes as LAND
        biomeMap.put("GRASSLAND", MapFeature.LAND);
        biomeMap.put("BEACH", MapFeature.LAND);
        biomeMap.put("MANGROVE", MapFeature.LAND);
        biomeMap.put("TROPICAL_RAIN_FOREST", MapFeature.LAND);
        biomeMap.put("TROPICAL_SEASONAL_FOREST", MapFeature.LAND);
        biomeMap.put("TEMPERATE_DECIDUOUS_FOREST", MapFeature.LAND);
        biomeMap.put("TEMPERATE_RAIN_FOREST", MapFeature.LAND);
        biomeMap.put("TEMPERATE_DESERT", MapFeature.LAND);
        biomeMap.put("TAIGA", MapFeature.LAND);
        biomeMap.put("SNOW", MapFeature.LAND);
        biomeMap.put("TUNDRA", MapFeature.LAND);
        biomeMap.put("ALPINE", MapFeature.LAND);
        biomeMap.put("GLACIER", MapFeature.LAND);
        biomeMap.put("SHRUBLAND", MapFeature.LAND);
        biomeMap.put("SUB_TROPICAL_DESERT", MapFeature.LAND);
        // Treat all the following biomes as OCEAN
        biomeMap.put("OCEAN", MapFeature.OCEAN);
        biomeMap.put("LAKE", MapFeature.OCEAN);
    }

    public Boolean isLand(String biome) {
        if (this.biomeMap.containsKey(biome)) {
            if (this.biomeMap.get(biome) == MapFeature.LAND) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public Boolean isOCEAN(String biome) {
        if (this.biomeMap.containsKey(biome)) {
            if (this.biomeMap.get(biome) == MapFeature.OCEAN) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

}