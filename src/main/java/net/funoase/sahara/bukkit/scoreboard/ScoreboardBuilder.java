package net.funoase.sahara.bukkit.scoreboard;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

@SuppressWarnings({"ConstantConditions", "unused"})
public abstract class ScoreboardBuilder {

    protected final Scoreboard scoreboard;
    protected final Objective objective;
    protected final Player player;

    public ScoreboardBuilder(Player player, Component displayName) {
        this.player = player;

        if(player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard()))
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        this.scoreboard = player.getScoreboard();
        Objective oldObjective = this.scoreboard.getObjective("display");

        if(oldObjective != null)
            oldObjective.unregister();

        this.objective = this.scoreboard.registerNewObjective("display", Criteria.DUMMY, displayName);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        createScoreboard();
    }

    public abstract void createScoreboard();

    public void setDisplayName(Component displayName) {
        this.objective.displayName(displayName);
    }

    public void setScore(Component content, int score) {
        Team team = getTeamByScore(score);

        team.prefix(content);
        showScore(score);
    }

    public void removeScore(int score) {
        hideScore(score);
    }

    @SuppressWarnings("deprecation")
    private String getEntry(int score) {
        return ChatColor.values()[score].toString();
    }

    private Team getTeamByScore(int score) {
        String entry = getEntry(score);

        Team team = scoreboard.getEntryTeam(entry);

        if(team != null)
            return team;

        team = scoreboard.registerNewTeam(entry);
        team.addEntry(entry);

        return team;
    }

    private void showScore(int score) {
        String entry = getEntry(score);

        if(objective.getScore(entry).isScoreSet())
            return;

        objective.getScore(entry).setScore(score);
    }

    private void hideScore(int score) {
        String entry = getEntry(score);

        if(!objective.getScore(entry).isScoreSet())
            return;

        scoreboard.resetScores(entry);
    }
}