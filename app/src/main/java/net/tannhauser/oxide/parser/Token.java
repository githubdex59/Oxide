package net.tannhauser.oxide.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Token {
    public String LOC;

    private static final Pattern VAR_DECL_PATTERN = Pattern.compile(
            "^\\s*(public|private|protected)?\\s*(static\\s+)?([A-Za-z_$][\\w\\[\\]<>]*)\\s+([A-Za-z_$][\\w]*)\\s*(=\\s*.+?)?\\s*;\\s*$"
    );

    public boolean isVar() {
        Matcher matcher = VAR_DECL_PATTERN.matcher(LOC);
        return matcher.matches();
    }

    public Token(String loc) {
        LOC = loc;
    }
    public String[] tokenize() {
        return LOC.split(" ");
    }
}
