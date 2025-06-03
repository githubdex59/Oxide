package net.tannhauser.oxide.parser;


import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseLine {
    public ParseLine() {
        System.out.println("ParseLine initialized");
    }
   public static String parse(String line) {
        Token token = new Token(line);
       if (token.isVar()) {
           return parseVariable(token);
       } else {
           return line;
       }

   }
    public static String parseVariable(Token token) {
        // Assume token.getLine() returns the original matched line
        String line = token.LOC.trim();

        // Regex to extract visibility, static, type, name, value
        Pattern pattern = Pattern.compile(
                "^\\s*(public|private|protected)?\\s*(static)?\\s*([A-Za-z_$][\\w\\[\\]<>]*)\\s+([A-Za-z_$][\\w]*)\\s*=\\s*(.+?)\\s*;\\s*$"
        );
        Matcher matcher = pattern.matcher(line);

        if (!matcher.matches()) {
            return "// Error: invalid variable declaration → " + line;
        }

        String visibility = matcher.group(1) != null ? matcher.group(1) : "";
        String staticism = matcher.group(2) != null ? matcher.group(2) : "";
        String type = matcher.group(3);
        String name = matcher.group(4);
        String value = matcher.group(5);

        // Clean up spacing
        visibility = visibility.isEmpty() ? "" : visibility + " ";
        staticism = staticism.isEmpty() ? "" : staticism + " ";

        // Build formatted output
        return String.format(
                "%s%sVariable %s = new Variable<%s>(\"%s\", \"%s\", %s);",
                visibility, staticism, name, type, name, type, value
        );
    }


}