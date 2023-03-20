package me.hsgamer.gamesinthebox.kingofthehill;

import me.hsgamer.gamesinthebox.expansion.SingleGameExpansion;
import me.hsgamer.gamesinthebox.game.Game;

public class KingOfTheHill extends SingleGameExpansion {
    @Override
    protected Game getGame() {
        return null;
    }

    @Override
    protected String[] getGameType() {
        return new String[]{"king-of-the-hill", "koth"};
    }
}
