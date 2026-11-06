import java.util.*;

public class Leaderboard {
    public static void printTopPlayers(Map<String, Integer> scores, int topN) {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(scores.entrySet());

        // Sort: highest score first; if tie, alphabetical order by name
        list.sort((e1, e2) -> {
            int scoreDiff = e2.getValue().compareTo(e1.getValue());
            if (scoreDiff != 0) {
                return scoreDiff;
            }
            return e1.getKey().compareToIgnoreCase(e2.getKey());
        });

        System.out.println("=== GAMING LEADERBOARD (Top " + topN + ") ===");
        System.out.printf("%-6s | %-15s | %s%n", "Rank", "Player Name", "Score");
        System.out.println("----------------------------------------");

        int rank = 1;
        for (int i = 0; i < Math.min(topN, list.size()); i++) {
            Map.Entry<String, Integer> entry = list.get(i);
            System.out.printf("#%-5d | %-15s | %d%n", rank++, entry.getKey(), entry.getValue());
        }
    }

    public static void main(String[] args) {
        Map<String, Integer> playerScores = new HashMap<>();
        playerScores.put("DragonSlayer", 4500);
        playerScores.put("ShadowNinja", 5200);
        playerScores.put("PixelKnight", 4500); // Tied score with DragonSlayer
        playerScores.put("Valkyrie", 6100);
        playerScores.put("IronClad", 3800);
        playerScores.put("CyberSamurai", 5200); // Tied score with ShadowNinja
        playerScores.put("NeonRacer", 4950);

        printTopPlayers(playerScores, 5);
    }
}

