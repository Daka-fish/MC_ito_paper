package net.tv.twitch.chrono_fish.ito_paper.game;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class OpenNumberTask extends BukkitRunnable {

    private final ItoGame itoGame;
    private final ArrayList<ItoPlayer> field;
    int count;

    public OpenNumberTask(ItoGame itoGame){
        this.itoGame = itoGame;
        this.field = itoGame.getCallList();
        count = 0;
    }

    @Override
    public void run() {
        if(count == field.size()){
            cancel();
            itoGame.check();
            return;
        }
        ItoPlayer itoPlayer = field.get(count);
        itoGame.sendMessage(itoPlayer.getNumber()+" : "+itoPlayer.getName());
        count++;
    }
}
