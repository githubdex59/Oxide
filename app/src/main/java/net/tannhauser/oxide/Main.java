package net.tannhauser.oxide;

import net.tannhauser.oxide.parser.ParseLine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static ParseLine parseLine = new ParseLine();

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("You buffoon, you didn't set a .pl to be compiled.");
            return;
        }
        String OutputName = "";
        if (args[1] != "1") {
            OutputName = "Main.java";
        } else if (args[1] == "0") {
            OutputName = "Temp.java";
        } else {
            System.out.println("Set IsMain to 1 if the file is your main file else set to 0");
        }

        File Temp = new File(OutputName);
        Scanner scanner = new Scanner(Paths.get(args[0]), StandardCharsets.UTF_8.name());
        String content = scanner.useDelimiter("\\A").next();
        scanner.close();

        String[] lines = content.split("\\r?\\n");
        String[] newLines = new String[lines.length];
        for (int i = 0; i < lines.length; i++) {
            newLines[i] = parseLine.parse(lines[i]) + "\n";
            System.out.println(newLines[i]);
        }

        if (Temp.createNewFile()) {
            FileWriter myWriter = new FileWriter(Temp.getName());
            WriteToJava writer = new WriteToJava();
            writer.writeToJava(newLines, myWriter);

        } else {
            System.out.println("Internal error, compiling files still exist, removing Temp.java");
            Temp.delete();
            // Continue
            FileWriter myWriter = new FileWriter(Temp.getName());
            WriteToJava writer = new WriteToJava();
            writer.writeToJava(newLines, myWriter);
        }
        try {
            compileJavaFile(OutputName);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void compileJavaFile(String javaFilePath) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("javac", "-cp", "/home/r3pr/Projects/coffee/Oxide/app/build/libs/app.jar", javaFilePath);
        builder.redirectErrorStream(true);

        Process process = builder.start();

        // Print output (compiler errors etc.)
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("\u001B[32m✅ Success\u001B[0m");
        } else {
            System.out.println("❌ Compilation failed with exit code: " + exitCode);
        }
    }

}
