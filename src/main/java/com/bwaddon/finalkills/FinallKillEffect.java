package com.esycho.bwaddon.finalkills;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Base interface for every Final Kill Cosmetic.
 */
public interface FinalKillEffect {

    /**
     * Unique cosmetic id.
     */
    String getId();

    /**
     * Display name.
     */
    String getName();

    /**
     * Play the effect.
     *
     * @param killer Player who got the final kill (can be null)
     * @param victim Player who died
     * @param location Death location
     */
    void play(Player killer, Player victim, Location location);

}