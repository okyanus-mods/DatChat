package club.issizler.datchat;

import club.issizler.okyanus.api.Okyanus;
import com.electronwill.nightconfig.core.file.FileConfig;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import org.apache.logging.log4j.Logger;

import javax.security.auth.login.LoginException;

class DiscordBot implements EventListener {

    private final FileConfig config;
    private final Logger logger;

    private JDA bot;

    DiscordBot(FileConfig config, Logger logger) {
        this.config = config;
        this.logger = logger;
    }

    void run() {
        String token = config.get("discord.token");

        if (token.isEmpty()) {
            logger.info("Please edit config/datchat.toml and put your bot token inside to enable Discord integration");
            return;
        }

        logger.info("Starting DatChat Discord bot...");

        try {
            bot = new JDABuilder(token).addEventListener(this).build();
        } catch (LoginException e) {
            logger.error("Could not log into discord!");
            e.printStackTrace();
        }
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof ReadyEvent) {
            logger.info("Discord integration ready!");
            return;
        }

        if (!(event instanceof MessageReceivedEvent)) {
            return;
        }
        MessageReceivedEvent messageReceivedEvent = (MessageReceivedEvent) event;

        if ((messageReceivedEvent.getAuthor().getId().equals(bot.getSelfUser().getId())))
            return;

        if (!messageReceivedEvent.isFromType(ChannelType.TEXT))
            return;

        if (messageReceivedEvent.getChannel().getIdLong() != config.getLong("discord.channelID"))
            return;

        String message = String.format((String) config.get("minecraft.format"), messageReceivedEvent.getMember().getEffectiveName(), messageReceivedEvent.getMessage().getContentDisplay());

        // Switch these whenever Okyanus 0.3.0 comes out
        Okyanus.getServer().getPlayerList().forEach(player -> player.send(message));
        //Okyanus.getServer().broadcast(message);
    }

    void sendToDC(String author, String message) {
        if (bot == null)
            return;

        bot
                .getTextChannelById(config.getLong("discord.channelID"))
                .sendMessageFormat(config.get("discord.format"), author, message)
                .queue();
    }

    void stop() {
        bot.shutdown();
    }

}
