import java.util.Random;
import java.util.Scanner;

class RPSLS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        String[] moves = {
            "rock", "paper", "scissors", "lizard", "spock"
        };

        int playerScore = 0;
        int computerScore = 0;

        for (int round = 1; round <= 5; round++) {
            System.out.print("Round " + round + " - Enter move: ");
            String player = sc.next().toLowerCase();

            String computer = moves[random.nextInt(5)];
            System.out.println("Computer: " + computer);

            if (player.equals(computer)) {
                System.out.println("Tie");
            } else if (
                (player.equals("rock") &&
                 (computer.equals("scissors") || computer.equals("lizard"))) ||

                (player.equals("paper") &&
                 (computer.equals("rock") || computer.equals("spock"))) ||

                (player.equals("scissors") &&
                 (computer.equals("paper") || computer.equals("lizard"))) ||

                (player.equals("lizard") &&
                 (computer.equals("paper") || computer.equals("spock"))) ||

                (player.equals("spock") &&
                 (computer.equals("rock") || computer.equals("scissors")))
            ) {
                System.out.println("You win this round");
                playerScore++;
            } else {
                System.out.println("Computer wins this round");
                computerScore++;
            }
        }

        System.out.println("Player score: " + playerScore);
        System.out.println("Computer score: " + computerScore);

        sc.close();
    }
}