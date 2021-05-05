package com.tsoft.civilization.world;

import lombok.Getter;

import java.util.Arrays;

public enum MapConfiguration {

    AMAZON("A region similar to the Amazon Jungle of South America, but with random elements"),
    AMAZON_PLUS("A region similar to the Amazon Jungle of South America, but with random elements"),
    AMERICAS("A large map spanning from Peru in the South to America in the north"),
    ANCIENT_LAKES("An all-land region featuring a large crater lake in the middle"),
    ARBOREA("A world almost completely covered in forest"),
    ARCHIPELAGO("The world will consist of many islands"),
    ASIA("A large map dominated by the Himalayas and jungles of south east Asia"),
    BERING_STRAIT("The Bering Strait and land surrounding, including the Russian Far East, Kamchatka, and Alaska"),
    BOREAL("A large region that is all tundra and well forested"),
    BRITISH_ISLES("Great Britain, Ireland, and countless smaller islands"),
    CARIBBEAN("Explore the Caribbean, in all its tropical splendor"),
    CONTINENTS("The world will consist of a few large landmasses and some smaller islands"),
    CONTINENTS_PLUS("Similar to Continents, but with extended scripting for chains of islands and city-state placement"),
    DONUT("A circular Pangaea. You can choose what type of terrain is in the middle"),
    EARTH("A map that spans the entire planet"),
    EUROPE("A randomly generated map that resembles Europe"),
    EASTERN_UNITED_STATES("A map that spans the southern tip of Texas and all the way up to the eastern tip of Nova Scotia"),
    FOUR_CORNERS("This map is sectioned into 4 equal sections, separated by water, with land in the very middle"),
    FRACTAL("A highly random map that will often form thin, snaky continents"),
    FRONTIER("A single continent scattered with mountains to simulate the rough environment of the Frontier"),
    GREAT_PLAINS("A region similar to the Great Plains of North America, but with random elements"),
    GREAT_PLAINS_PLUS("A region similar to the Great Plains of North America, but with random elements"),
    HEMISPHERES("A world with two large, fairly balanced continents. Suitable for duels or two-teams multiplayer"),
    HIGHLANDS("A large region dominated by hills and mountain ranges"),
    ICE_AGE("A severely glaciated world where only regions near the equator are habitable"),
    INLAND_SEA("An all-land region that has a large salt water sea in the center"),
    JAPANESE_MAINLAND("This map focuses exclusively on Japan, with no surrounding countries in play"),
    LAKES("A world without oceans, only lesser bodies of water"),
    LARGE_ISLANDS("A world with numerous large islands, roughly equal in size but varying in terrain. Civs are placed on the best available islands"),
    MEDITERRANEAN("A large map covering the entire Mediterranean basin"),
    MESOPOTAMIA("A large map spanning the Nile River valley to the west and extending east down the Tigris and Euphrates"),
    NORTH_VS_SOUTH("A map split horizontally by desert"),
    OVAL("An oval-shaped Pangaea with random bays carving in to the sides"),
    PANGAEA("All players start on one large landmass. There may be small islands off of the main continent"),
    PANGAEA_PLUS("Similar to Pangaea, but with extended scripting for chains of island and city-state placement"),
    RAINFOREST("A world almost completely covered in jungle"),
    RING("Each civilization gets its own subcontinent. All are connected in clockwise fashion to adjacent neighbors"),
    SANDSTORM("A harsh world composed primarily of desert with smaller patches of plains and grassland"),
    SHUFFLE("A truly random map, including all core settings like world age, rainfall, etc"),
    SKIRMISH("A balanced map designed for 2 players"),
    SMALL_CONTINENTS("The world will consist of a few medium landmasses and some smaller islands"),
    TERRA("A very large world, similar to Earth, where all civilizations start on the continent akin to Asia-Europe-Africa (more accurately known as Afro-Eurasia)"),
    TILTED_AXIS("A very large world, tilted on its side with its south pole always locked in to facing the sun"),
    TINY_ISLANDS("This world will consist of many tiny islands"),
    WEST_VS_EAST("A region split vertically down the middle by a wide channel of water");
    
    @Getter
    private final String description;

    MapConfiguration(String description) {
        this.description = description;
    }

    public static MapConfiguration find(String name) {
        return Arrays.stream(MapConfiguration.values())
            .filter(e -> e.name().equalsIgnoreCase(name))
            .findAny()
            .orElse(null);
    }
}
