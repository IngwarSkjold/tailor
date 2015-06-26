package com.sleekbyte.tailor.utils;

import com.sleekbyte.tailor.common.MaxLengths;
import com.sleekbyte.tailor.common.Messages;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentParser {

    public static final String HELP_SHORT_OPT = "h";
    public static final String HELP_LONG_OPT = "help";
    private static final String MAX_CLASS_LENGTH_OPT = "max-class-length";
    private static final String MAX_CLOSURE_LENGTH_OPT = "max-closure-length";
    public static final String MAX_FILE_LENGTH_OPT = "max-file-length";
    private static final String MAX_FUNCTION_LENGTH_OPT = "max-function-length";
    private static final String MAX_LINE_LENGTH_SHORT_OPT = "l";
    private static final String MAX_LINE_LENGTH_LONG_OPT = "max-line-length";
    private static final String MAX_NAME_LENGTH_OPT = "max-name-length";
    private static final String MAX_STRUCT_LENGTH_OPT = "max-struct-length";
    private static final String DEFAULT_INT_ARG = "0";
    private static final int DEFAULT_INT_ARG_VALUE = 0;

    private String[] args;
    private Options options;
    private CommandLine cmd;

    public ArgumentParser(String[] args) {
        this.args = args;
    }

    public CommandLine parseCommandLine() throws ParseException {
        addOptions();
        cmd = new DefaultParser().parse(this.options, this.args);
        if (cmd.hasOption(HELP_SHORT_OPT)) {
            printHelp();
            System.exit(0);
        }
        return cmd;
    }

    public void printHelp() {
        new HelpFormatter().printHelp(Messages.CMD_LINE_SYNTAX, this.options);
    }

    public MaxLengths parseMaxLengths() {
        MaxLengths maxLengths = new MaxLengths();
        maxLengths.setMaxClassLength(getIntegerArgument(MAX_CLASS_LENGTH_OPT));
        maxLengths.setMaxClosureLength(getIntegerArgument(MAX_CLOSURE_LENGTH_OPT));
        maxLengths.setMaxFileLength(getIntegerArgument(MAX_FILE_LENGTH_OPT));
        maxLengths.setMaxFunctionLength(getIntegerArgument(MAX_FUNCTION_LENGTH_OPT));
        maxLengths.setMaxLineLength(getIntegerArgument(MAX_LINE_LENGTH_LONG_OPT));
        maxLengths.setMaxNameLength(getIntegerArgument(MAX_NAME_LENGTH_OPT));
        maxLengths.setMaxStructLength(getIntegerArgument(MAX_STRUCT_LENGTH_OPT));
        return maxLengths;
    }

    private void addOptions() {
        Option help = Option.builder(HELP_SHORT_OPT)
            .longOpt(HELP_LONG_OPT)
            .desc(Messages.HELP_DESC)
            .build();
        Option maxClassLength = addIntegerArgument(MAX_CLASS_LENGTH_OPT, Messages.MAX_CLASS_LENGTH_DESC);
        Option maxClosureLength = addIntegerArgument(MAX_CLOSURE_LENGTH_OPT, Messages.MAX_CLOSURE_LENGTH_DESC);
        Option maxFileLength = addIntegerArgument(MAX_FILE_LENGTH_OPT, Messages.MAX_FILE_LENGTH_DESC);
        Option maxFunctionLength = addIntegerArgument(MAX_FUNCTION_LENGTH_OPT, Messages.MAX_FUNCTION_LENGTH_DESC);
        Option maxLineLength = addIntegerArgument(MAX_LINE_LENGTH_SHORT_OPT, MAX_LINE_LENGTH_LONG_OPT,
            Messages.MAX_LINE_LENGTH_DESC);
        Option maxNameLength = addIntegerArgument(MAX_NAME_LENGTH_OPT, Messages.MAX_NAME_LENGTH_DESC);
        Option maxStructLength = addIntegerArgument(MAX_STRUCT_LENGTH_OPT, Messages.MAX_STRUCT_LENGTH_DESC);

        options = new Options();
        options.addOption(help);
        options.addOption(maxClassLength);
        options.addOption(maxClosureLength);
        options.addOption(maxFileLength);
        options.addOption(maxFunctionLength);
        options.addOption(maxLineLength);
        options.addOption(maxNameLength);
        options.addOption(maxStructLength);
    }

    private Option addIntegerArgument(String shortOpt, String longOpt, String desc) {
        return Option.builder(shortOpt).longOpt(longOpt).hasArg().desc(desc).build();
    }

    private Option addIntegerArgument(String longOpt, String desc) {
        return Option.builder().longOpt(longOpt).hasArg().desc(desc).build();
    }

    private int getIntegerArgument(String opt) {
        try {
            return Integer.parseInt(this.cmd.getOptionValue(opt, DEFAULT_INT_ARG));
        } catch (NumberFormatException e) {
            System.err.println("Invalid integer argument value: " + e.getMessage());
        }
        return DEFAULT_INT_ARG_VALUE;
    }

}
