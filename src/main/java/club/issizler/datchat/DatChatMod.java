package club.issizler.datchat;

import club.issizler.okyanus.api.Mod;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.file.FileConfig;

public class DatChatMod extends Mod {

    @Override
    public void init() {
        FileConfig config = CommentedFileConfig.builder("config/datchat.toml").defaultResource("/datchat.toml").autosave().build();
        config.load();

        DiscordBot bot = new DiscordBot(config, getServer().getLogger());

        registerEvent(new ChatListener(config, bot));

        registerEvent(new ReadyListener(bot));
        registerEvent(new StopListener(bot));
    }

}
