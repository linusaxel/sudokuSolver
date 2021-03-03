package sudokusolver;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
        String paths = Files.readString(Path.of("src/main/resources/paths.json"), StandardCharsets.US_ASCII);
        JSONObject object = new JSONObject(paths);
        String loadPath = object.getString("loadPath");
        String savePath = object.getString("savePath");

        Sudoku sudoku = new Sudoku();
        sudoku.load(loadPath);
        sudoku.solve();
        sudoku.save(savePath);
    }

}
