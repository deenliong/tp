package seedu.duke.parser;

import seedu.duke.command.AddModuleCommand;
import seedu.duke.command.Command;
import seedu.duke.command.DeleteModuleCommand;
import seedu.duke.command.ExitCommand;
import seedu.duke.command.GetModuleCommand;
import seedu.duke.command.HelpCommand;
import seedu.duke.command.IncompleteCommand;
import seedu.duke.command.InvalidModuleCommand;
import seedu.duke.command.UnknownCommand;
import seedu.duke.command.ViewTimetableCommand;
import seedu.duke.command.SelectSlotCommand;
import seedu.duke.command.SelectSemesterCommand;
import seedu.duke.command.SearchModuleCommand;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public static Command parse(String userInput) {
        String[] keywords = userInput.split("\\s+");
        switch (keywords[0]) {
        case (SearchModuleCommand.COMMAND_WORD):
            return searchCommand(userInput);
        case (GetModuleCommand.COMMAND_WORD):
            return getAddDeleteCommand(keywords, new GetModuleCommand(keywords));
        case (AddModuleCommand.COMMAND_WORD):
            return getAddDeleteCommand(keywords, new AddModuleCommand(keywords));
        case (DeleteModuleCommand.COMMAND_WORD):
            return getAddDeleteCommand(keywords, new DeleteModuleCommand(keywords));
        case (ViewTimetableCommand.COMMAND_WORD):
            return new ViewTimetableCommand(userInput);
        case (HelpCommand.COMMAND_WORD):
            return viewHelpExitCommand(keywords, new HelpCommand(keywords));
        case (SelectSlotCommand.COMMAND_WORD):
            return new SelectSlotCommand(userInput);
        case (ExitCommand.COMMAND_WORD):
            return viewHelpExitCommand(keywords, new ExitCommand(keywords));
        case (SelectSemesterCommand.COMMAND_WORD):
            return selectSemesterCommand(keywords, new SelectSemesterCommand(keywords));
        default:
            return new UnknownCommand(keywords);
        }
    }

    // private static Command getCommand(String[] keywords) {
    //     if (isValidTwoWordCommand(keywords)) {
    //         return new GetModuleCommand(keywords);
    //     } else {
    //         // System.out.println("Invalid module code");
    //         return determineWrongCommand(keywords);
    //     }
    // }

    public static boolean isPartialModuleCode(String moduleCode) {
        Pattern pattern = Pattern.compile("[a-z]{0,3}\\d{0,4}[a-z]?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(moduleCode);
        return (matcher.find() && matcher.group().length() == moduleCode.length());
    }

    public static boolean isModuleCode(String moduleCode) {
        Pattern pattern = Pattern.compile("[a-z]{2,3}\\d{4}[a-z]?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(moduleCode);
        return (matcher.find() && matcher.group().length() == moduleCode.length());
    }

    public static boolean isPartialModuleFaculty(String moduleFaculty) {
        Pattern pattern = Pattern.compile("[a-z]{2,3}\\d{4}[a-z]?", Pattern.CASE_INSENSITIVE); // logic update needed
        Matcher matcher = pattern.matcher(moduleFaculty);
        return (matcher.find() && matcher.group().length() == moduleFaculty.length());
    }

    public static boolean isModuleFaculty(String moduleFaculty) {
        Pattern pattern = Pattern.compile("[a-z]{2,3}\\d{4}[a-z]?", Pattern.CASE_INSENSITIVE); // logic update needed
        Matcher matcher = pattern.matcher(moduleFaculty);
        return (matcher.find() && matcher.group().length() == moduleFaculty.length());
    }

    public static boolean isMultiWordsCommand(String[] keywords) {
        return keywords.length > 2;
    }

    public static boolean isTwoWordsCommand(String[] keywords) {
        return keywords.length == 2;
    }

    public static boolean isOneWordCommand(String[] keywords) {
        return keywords.length == 1;
    }

    public static boolean isValidModuleCode(String moduleCode) {
        return isModuleCode(moduleCode) || isPartialModuleCode(moduleCode);
    }

    public static boolean isValidModuleFaculty(String moduleFaculty) {
        return isModuleFaculty(moduleFaculty) || isPartialModuleFaculty(moduleFaculty);
    }

    private static boolean containsValidModuleCode(String[] keywords) {
        for (int i = 1; i < keywords.length; i++) {
            if (isValidModuleCode(keywords[i])) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidTwoWordCommand(String[] keywords) {
        return isTwoWordsCommand(keywords) && isValidModuleCode(keywords[1]);
    }

    private static boolean isValidSemester(String[] keywords) {
        int semesterInput = Integer.parseInt(keywords[1]);
        return semesterInput > 0 && semesterInput <= 4;
    }

    public static Command searchCommand(String userInput) {
        return new SearchModuleCommand(userInput);
    }

    /**
     * Checks if the user entered a valid search or add or delete command in the
     * format
     * "search|add|delete MODULE_CODE".
     * 
     * @param keywords contains the user input split by spaces
     * @param command  the command that the user wants to execute
     * @return type of command
     */
    public static Command getAddDeleteCommand(String[] keywords, Command command) {
        if (isValidTwoWordCommand(keywords)) {
            return command;
        } else {
            // System.out.println("Invalid module code");
            return determineWrongCommand(keywords);
        }
    }

    private static Command determineWrongCommand(String[] keywords) {
        if (isOneWordCommand(keywords)) {
            return new IncompleteCommand(keywords);
        } else if (isTwoWordsCommand(keywords) && !isValidModuleCode(keywords[1])) {
            return new InvalidModuleCommand(keywords);
        } else {
            return new UnknownCommand(keywords);
        }
    }

    public static Command viewHelpExitCommand(String[] keywords, Command command) {
        if (isOneWordCommand(keywords)) {
            return command;
        } else {
            return new UnknownCommand(keywords);
        }
    }

    public static Command selectSemesterCommand(String[] keywords, Command command) {
        if (isValidSemester(keywords)) {
            return command;
        } else {
            return new UnknownCommand(keywords);
        }
    }

    public static Map<String, String> parseParams(String description) {
        Map<String, String> paramsMap = new TreeMap<>();
        int firstSlash = description.indexOf('/');
        if (firstSlash == -1) {
            return paramsMap;
        }
        String paramsString = description.substring(firstSlash + 1);
        for (String param : paramsString.split(" /")) {
            int firstSpace = param.indexOf(' ');
            String key = firstSpace == -1 ? param : param.substring(0, firstSpace).trim();
            String value = firstSpace == -1 ? "" : param.substring(firstSpace + 1).trim();
            paramsMap.put(key, value);
        }
        return paramsMap;
    }
}
