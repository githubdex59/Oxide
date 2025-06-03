package net.tannhauser.oxide;

import net.tannhauser.oxide.parser.ParseLine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("You buffoon, you didn't set a .pl to be compiled.");
            return;
        }
        File newFile = new File(args[0]);
        String OutputName = newFile.getName().replaceFirst("[.][^.]+$", "") + ".java";
        File Temp = new File(OutputName);
        Scanner scanner = new Scanner(Paths.get(args[0]), StandardCharsets.UTF_8);
        String content = scanner.useDelimiter("\\A").next();
        scanner.close();


        String[] lines = content.split("\\r?\\n");
        String[] newLines = new String[lines.length];
        for (int i = 0; i < lines.length; i++) {
            newLines[i] = ParseLine.parse(lines[i]) + "\n";
            System.out.println(newLines[i]);
        }

        if (Temp.createNewFile()) {
            FileWriter myWriter = new FileWriter(Temp.getName());
            myWriter.write(String.join("", newLines));
            myWriter.close();

        } else {
            System.out.println("Internal error, compiling files still exist, removing Temp.java");
            boolean delete = Temp.delete();
            System.out.println(delete);
            // Continue
            FileWriter myWriter = new FileWriter(Temp.getName());
            myWriter.write(String.join("", newLines));
            myWriter.close();
        }
        try {
            compileJavaFile(OutputName);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        boolean delete = Temp.delete();
        System.out.println(delete);
    }
    public static void compileJavaFile(String javaFilePath) throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("javac", "-cp", "/usr/lib/sail-software/oxidec.jar", javaFilePath);
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
