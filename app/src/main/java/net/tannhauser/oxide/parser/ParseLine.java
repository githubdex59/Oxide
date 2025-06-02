package net.tannhauser.oxide.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseLine {
    public ParseLine() {
        System.out.println("ParseLine initialized");
    }
    public String parse(String line) {
        if (line == "private static String string = \"IT WORKS!\";") {
            return "private static Variable string = new Variable(\"string\", \"String\", \"IT WORKS!\");";
        }
        return line;
    }

    public static Matcher useRegex(final String input) {
        // Compile regular expression
        final Pattern pattern = Pattern.compile("(private|public)+\\s[A-Za-z0-9]+\\s[A-Za-z0-9]+\\s=\\s[A-Za-z0-9]+;");
        // Match regex against input
        final Matcher matcher = pattern.matcher(input);
        // Use results...
        return matcher;
    }
}