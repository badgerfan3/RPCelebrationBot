import io.github.cdimascio.dotenv.Dotenv;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class RPCelebrationBot {
    static String commandPrefix;

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String token = dotenv.get("BOT_TOKEN");

        commandPrefix = dotenv.get("BOT_PREFIX");

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        api.addListener(new Appreciate());
    }
}
