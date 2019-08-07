package cn.wode490390.mcbe.botclient;

import net.minecrell.terminalconsole.SimpleTerminalConsole;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;

public class ClientConsole extends SimpleTerminalConsole {

    private final Main main;

    public ClientConsole(Main main) {
        this.main = main;
    }

    @Override
    protected boolean isRunning() {
        return main.isRunning();
    }

    @Override
    protected void runCommand(String command) {
        main.getCommandManager().dispatch(command);
    }

    @Override
    protected void shutdown() {
        main.shutdown();
    }

    @Override
    protected LineReader buildReader(LineReaderBuilder builder) {
        builder.completer(new ConsoleCompleter(main));
        builder.appName("BotClient");
        builder.option(LineReader.Option.HISTORY_BEEP, false);
        builder.option(LineReader.Option.HISTORY_IGNORE_DUPS, true);
        builder.option(LineReader.Option.HISTORY_IGNORE_SPACE, true);
        return super.buildReader(builder);
    }
}
