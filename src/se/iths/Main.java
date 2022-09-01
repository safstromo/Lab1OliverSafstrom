package se.iths;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println(menu());

    }


    public static String menu() {

        // Presents menu in console and returns choise.

        Scanner sc = new Scanner(System.in);
        String input = "";

        boolean validInput = false;

        // Loop until valid input.
        while (!validInput) {

            System.out.println("Elpriser\n========");
            System.out.println("1. Inmatning");
            System.out.println("2. Min, Max och Medel");
            System.out.println("3. Sortera");
            System.out.println("4. Bästa Laddningstid (4h)");
            System.out.println("e. Avsluta");

            input = sc.nextLine();
            switch (input) {
                case "1" -> {
                    input = "1";
                    validInput = true;
                }
                case "2" -> {
                    input = "2";
                    validInput = true;
                }
                case "3" -> {
                    input = "3";
                    validInput = true;
                }
                case "4" -> {
                    input = "4";
                    validInput = true;
                }
                case "e" -> {
                    input = "e";
                    validInput = true;
                }
            }
        }
        return input;
    }
}