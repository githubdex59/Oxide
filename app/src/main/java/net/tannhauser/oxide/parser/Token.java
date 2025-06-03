package net.tannhauser.oxide.parser;

public class Token {
    public String LOC;
    public Token(String loc) {
        LOC = loc;
    }
    public String[] tokenize() {
        return LOC.split(" ");
    }
}
