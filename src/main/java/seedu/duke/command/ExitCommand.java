package seedu.duke.command;

import seedu.duke.utils.State;
import seedu.duke.utils.Storage;
import seedu.duke.utils.Ui;

public class ExitCommand extends Command {
    public static final String COMMAND_WORD = "bye";
    public static final String FORMAT = "bye";
    public static final String HELP_DISPLAY = COMMAND_WORD
            + ": exit Yet Another Module Organiser / Manager (YAMOM)!\n"
            + "\tUsage:\t"
            + FORMAT
            + System.lineSeparator();

    public ExitCommand(String[] input) {
        super(input);
    }

    @Override
    public void execute(State state, Ui ui, Storage storage) {

    }

    @Override
    public boolean isExit() {
        return true;
    }

    @Override
    public String getExecutionMessage() {
        return null;
    }
}
