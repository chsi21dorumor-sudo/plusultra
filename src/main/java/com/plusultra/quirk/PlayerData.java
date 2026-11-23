package com.plusultra.quirk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.plusultra.core.ModAttachments;
import java.util.List;
import net.minecraft.world.entity.player.Player;

/** Mutable per-player state, stored as a NeoForge data attachment. */
public class PlayerData {
    public static final Codec<PlayerData> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.STRING.optionalFieldOf("quirk", "").forGetter(d -> d.quirk),
            Codec.INT.optionalFieldOf("level", 1).forGetter(d -> d.level),
            Codec.INT.optionalFieldOf("xp", 0).forGetter(d -> d.xp),
            Codec.FLOAT.optionalFieldOf("stamina", 100f).forGetter(d -> d.stamina),
            Codec.LONG.listOf().optionalFieldOf("cooldowns", List.of(0L, 0L, 0L))
                    .forGetter(d -> List.of(d.cd[0], d.cd[1], d.cd[2])),
            Codec.STRING.optionalFieldOf("mission", "").forGetter(d -> d.mission),
            Codec.INT.optionalFieldOf("mission_progress", 0).forGetter(d -> d.missionProgress),
            Codec.INT.optionalFieldOf("mission_goal", 0).forGetter(d -> d.missionGoal),
            Codec.INT.optionalFieldOf("points", 0).forGetter(d -> d.points)
    ).apply(i, PlayerData::new));

    public String quirk = "";
    public int level = 1;
    public int xp = 0;
    public float stamina = 100f;
    /** Game-time tick at which each ability slot is ready again. */
    public final long[] cd = new long[3];
    public String mission = "";
    public int missionProgress = 0;
    public int missionGoal = 0;
    public int points = 0;

    /** Not saved: set every second from nearby agency members. */
    public double synergy = 1.0;

    public PlayerData() {}

    public PlayerData(String quirk, int level, int xp, float stamina, List<Long> cooldowns,
                      String mission, int missionProgress, int missionGoal, int points) {
        this.quirk = quirk;
        this.level = level;
        this.xp = xp;
        this.stamina = stamina;
        for (int k = 0; k < 3 && k < cooldowns.size(); k++) this.cd[k] = cooldowns.get(k);
        this.mission = mission;
        this.missionProgress = missionProgress;
        this.missionGoal = missionGoal;
        this.points = points;
    }

    public static PlayerData get(Player p) {
        return p.getData(ModAttachments.PLAYER_DATA);
    }

    public boolean hasQuirk() {
        return !quirk.isEmpty() && Quirk.byId(quirk) != null;
    }

    public float maxStamina() {
        return 100f + 10f * (level - 1);
    }

    public int xpNeeded() {
        return 50 * level;
    }

    public void setQuirk(String id) {
        this.quirk = id;
        this.level = 1;
        this.xp = 0;
        this.stamina = maxStamina();
        this.cd[0] = this.cd[1] = this.cd[2] = 0;
    }

    public void clearQuirk() {
        setQuirk("");
    }
}
