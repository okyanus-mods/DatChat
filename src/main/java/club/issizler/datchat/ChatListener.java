package club.issizler.datchat;

import club.issizler.okyanus.api.event.ChatEvent;
import club.issizler.okyanus.api.event.EventHandler;
import com.electronwill.nightconfig.core.file.FileConfig;

public class ChatListener implements EventHandler<ChatEvent> {

    private final FileConfig config;
    private final DiscordBot bot;

    ChatListener(FileConfig config, DiscordBot bot) {
        this.config = config;
        this.bot = bot;
    }

    @Override
    public void handle(ChatEvent event) {
        bot.sendToDC(event.getPlayer().getCustomName(), event.getMessage());
    }

}
