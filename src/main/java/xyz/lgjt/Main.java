package xyz.lgjt;

import static xyz.lgjt.Consts.BOT_PLAYER;

public class Main {
    public static void main(String[] args) {
        Game g = new Game(BOT_PLAYER);
        g.play();
    }
}