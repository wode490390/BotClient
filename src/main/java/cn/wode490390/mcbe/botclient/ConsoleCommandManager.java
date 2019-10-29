package cn.wode490390.mcbe.botclient;

import com.nukkitx.protocol.bedrock.BedrockClient;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConsoleCommandManager {

    //private final Main main;

    private final Map<String, ConsoleCommand> commandMap;

    public ConsoleCommandManager(Main main) {
        //this.main = main;
        commandMap = new HashMap<String, ConsoleCommand>(){
            {
                put("stop", (args) -> {
                    log.info("Stopping the client...");
                    main.shutdown();
                });
                put("version", (args) -> {
                    log.info("wodeFakeClient (MCBE) Version: " + Main.VERSION);
                });
                put("add", (args) -> {
                    int count = 1;
                    if (args.length > 0) {
                        try {
                            count = Integer.parseInt(args[1]);
                        } catch (Exception e) {
                            log.info("Usage: /add [count]");
                        }
                    }
                    for (int i = 0; i < count; ++i) {
                        main.getClientManager().newClient();
                    }
                });
                put("close", (args) -> {
                    int count = 1;
                    if (args.length > 0) {
                        try {
                            count = Integer.parseInt(args[1]);
                        } catch (Exception e) {
                            log.info("Usage: /close [count]");
                        }
                    }
                    Iterator<BedrockClient> iterator = main.getClientManager().getClients().iterator();
                    for (int i = 0; i < count; ++i) {
                        BedrockClient client = iterator.next();
                        client.close();
                        main.getClientManager().getClients().remove(client);
                    }
                });
            }
        };
    }

    public void dispatch(String command) {
        String[] commandLine = command.split(" ");
        ConsoleCommand cmd = getCommandMap().get(commandLine[0]);
        if (cmd != null) {
            try {
                cmd.dispatch(commandLine);
            } catch (Exception e) {
                log.error("An unknown error occurred while attempting to perform this command: " + cmd, e);
            }
        } else {
            log.info("Unknown command");
        }
    }

    public Map<String, ConsoleCommand> getCommandMap() {
        return commandMap;
    }
}
