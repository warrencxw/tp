package seedu.duke.ui;

/**
 * Contains hardcoded strings such as error codes.
 *
 * @author Saurav
 */
public abstract class Message {
    public static final String ERRORCODE_EXAMPLE = "";
    public static final String LOGO = " $$$$$$\\            $$\\ $$\\   $$\\     $$\\           $$\\\n"
            + "$$  $$\\           $$ |\\|  $$ |    $$ |          $$ |\n"
            + "$$ /  \\__| $$$$$$\\  $$ |$$\\ $$$$$$\\   $$ | $$$$$$\\  $$$$$$$\\\n"
            + "\\$$$$$$\\  $$  $$\\ $$ |$$ |\\_$$  _|  $$ | \\____$$\\ $$  __$$\\\n"
            + " \\____$$\\ $$ /  $$ |$$ |$$ |  $$ |    $$ | $$$$$$$ |$$ |  $$ |\n"
            + "$$\\   $$ |$$ |  $$ |$$ |$$ |  $$ |$$\\ $$ |$$  __$$ |$$ |  $$ |\n"
            + "\\$$$$$$  |$$$$$$$  |$$ |$$ |  \\$$$$  |$$ |\\$$$$$$$ |$$ |  $$ |\n"
            + " \\______/ $$  ____/ \\|\\__|   \\____/ \\__| \\_______|\\__|  \\__|\n"
            + "          $$ |\n"
            + "          $$ |\n"
            + "          \\__|";
    
    public static final String ERROR_PARSER_DELIMITER_NOT_FOUND =
            "Please include the following delimiter in your input: ";
    public static final String ERROR_PARSER_MISSING_ARGUMENT =
            "Please include an argument after the following delimiter: ";
    public static final String ERROR_PARSER_NON_INTEGER_ARGUMENT =
            "Please enter a valid integer after the following delimiter: ";
    public static final String ERROR_PARSER_NON_MONETARY_VALUE_ARGUMENT =
            "Please enter a valid monetary value(s) after the following delimiter: ";
    public static final String ERROR_PARSER_INVALID_GST_SURCHARGE =
            "Please enter a valid GST surcharge in % after the delimiter: ";
    public static final String ERROR_PARSER_INVALID_SERVICE_CHARGE =
            "Please enter a valid service charge in % after the delimiter: ";
    public static final String ERROR_PARSER_INVALID_COMMAND =
            "Please enter a valid command.";
}