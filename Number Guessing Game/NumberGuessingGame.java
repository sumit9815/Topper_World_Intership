project 1 import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int minRange = 1; 
        int maxRange = 100; 
        int numberOfTries = 10; 
        int randomNumber = random.nextInt(maxRange - minRange + 1) + minRange;
        int guess;
        int attempts = 0;

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("I'm thinking of a number between " + minRange + " and " + maxRange + ".");
        System.out.println("You have " + numberOfTries + " tries to guess it.");

        while (attempts < numberOfTries) {
            System.out.print("Enter your guess: ");
            guess = scanner.nextInt();
            attempts++;

            if (guess < minRange || guess > maxRange) {
                System.out.println("Your guess is out of the range.");
                continue;
            }

            if (guess < randomNumber) {
                System.out.println("Try a higher number.");
            } else if (guess > randomNumber) {
                System.out.println("Try a lower number.");
            } else {
                System.out.println("Congratulations! You guessed the correct number (" + randomNumber + ") in " + attempts + " attempts.");
                break;
            }

            if (attempts == numberOfTries) {
                System.out.println("Sorry, you've run out of attempts. The correct number was " + randomNumber + ".");
            } else {
                System.out.println("You have " + (numberOfTries - attempts) + " tries left.");
            }
        }

        scanner.close();
    }
}
