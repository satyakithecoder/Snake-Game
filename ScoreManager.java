import java.io.*;

public class ScoreManager {
    private static final String FILE_NAME = "highscore.dat";

    public static void saveHighScore(int score) {
        if (score > getHighScore()) {
            try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
                out.println(score);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static int getHighScore() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            return Integer.parseInt(br.readLine());
        } catch (IOException | NumberFormatException e) {
            return 0; // Return 0 if no file exists yet
        }
    }
}