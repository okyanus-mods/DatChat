package club.issizler.datchat;

import club.issizler.okyanus.api.event.EventHandler;
import club.issizler.okyanus.api.event.ReadyEvent;

public class ReadyListener implements EventHandler<ReadyEvent> {

    private final DiscordBot bot;

    ReadyListener(DiscordBot bot) {
        this.bot = bot;
    }

    @Override
    public void handle(ReadyEvent event) {
        bot.run();
    }

}
