package Body;

import java.io.*;
import java.util.*;

public class HighScoresManager {
    private static final String FILE_NAME = "high_scores.txt";

    public void addHighScore(String playerName, int score) {
        List<String> highScores = loadHighScores();
        //I use this code to rank high score, use lambda function 
        //Collections.sort() in lambda function style is Collections.sort(names, (a, b) -> a.compareTo(b))
        highScores.add("   "+playerName + ":" + score);
        Collections.sort(highScores, (a, b) -> Integer.compare(Integer.parseInt(b.split(":")[1]), Integer.parseInt(a.split(":")[1])));

        if (highScores.size() > 20) {
            highScores.remove(highScores.size() - 1);
        }

        saveHighScores(highScores);
    }

    public List<String> loadHighScores() {
        List<String> highScores = new ArrayList<>();

        try {
            File file = new File(FILE_NAME);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));

                String line;
                while ((line = br.readLine()) != null) {
                    highScores.add(line);
                }
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return highScores;
    }

    private void saveHighScores(List<String> highScores) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME));

            for (String score : highScores) {
                bw.write(score);
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
