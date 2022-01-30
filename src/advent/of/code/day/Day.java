package advent.of.code.day;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Day {
    public static Stream<String> readFileIntoStream(String file_name) {
        Path path = Paths.get("E:\\adventOfCodeJava\\src\\resources\\" + file_name);
        Stream<String> lines = null;
        try {
            lines = Files.lines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static List<String> readFileIntoList(String file_name) {
        Path path = Paths.get("E:\\adventOfCodeJava\\src\\resources\\" + file_name);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
