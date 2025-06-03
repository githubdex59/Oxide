package net.tannhauser.oxide.parser;


import java.util.Arrays;
import java.util.regex.Pattern;

public class ParseLine {
    public ParseLine() {
        System.out.println("ParseLine initialized");
    }
   public static String parse(String line) {
        Token token = new Token(line);
       if (token.isVar()) {
           return transpileVariable(token);
       } else {
           return line;
       }

   }



    private static String transpileVariable(Token token) {
        String[] tokens = token.tokenize();
       String visibility = "";
       String staticness = "";
       String type = "";
       String name = "";
       String value = "";
        System.out.println(Arrays.toString(tokens));
       int i = 0;

       // Step 1: Visibility (optional)
       if (tokens[i].equals("public") || tokens[i].equals("private") || tokens[i].equals("protected")) {
           visibility = tokens[i];
           i++;
       }

       // Step 2: Static (optional)
       if (tokens[i].equals("static")) {
           staticness = "static";
           i++;
       }

       // Step 3: Type
       type = tokens[i];
       i++;

       // Step 4: Variable name
       name = tokens[i];
       i++;

       // Step 5: Skip '='
       if (tokens[i].equals("=")) {
           i++;
       }

       // Step 6: Value (support values with spaces, e.g. strings)
       StringBuilder valueBuilder = new StringBuilder();
       while (i < tokens.length) {
           String tok = tokens[i];
           if (tok.endsWith(";")) {
               valueBuilder.append(tok, 0, tok.length() - 1); // strip semicolon
               break;
           } else {
               valueBuilder.append(tok).append(" ");
           }
           i++;
       }
       value = valueBuilder.toString().trim();

       // Step 7: Build new LOC
       StringBuilder newLine = new StringBuilder();
       if (!visibility.isEmpty()) newLine.append(visibility).append(" ");
       if (!staticness.isEmpty()) newLine.append(staticness).append(" ");
       newLine.append("Variable ").append(name)
               .append(" = new Variable(\"").append(name)
               .append("\", \"").append(type)
               .append("\", ").append(value).append(");");

       return newLine.toString();
   }

}