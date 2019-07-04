package club.issizler.datchat;

import club.issizler.okyanus.api.event.EventHandler;
import club.issizler.okyanus.api.event.StopEvent;

public class StopListener implements EventHandler<StopEvent> {

    private final DiscordBot bot;

    StopListener(DiscordBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(StopEvent event) {
        bot.stop();
    }

}
