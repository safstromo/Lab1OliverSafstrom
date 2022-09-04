package se.iths;


import java.util.Arrays;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hour[] hour = new Hour[24];

        createHourObj(hour);


        System.out.println(hour[0].getName());
        System.out.println(hour[5].getName());

        //System.out.println(menu());
        // int[] userData = new int[]{2,43,5,6,7,4,54,76,67,34,4,65,65,87,4,4,32,432,45,45,12,324,43,};
        //int[] userData = userInput();
        //userInput();
        //minMax(userInput());
        //  sortering(userData);
    }

    public static void createHourObj(Hour[] hour) {

        for (int i = 0; i < hour.length; i++) {
            hour[i] = new Hour();
        }
        for (int i = 0; i < hour.length; i++) {
            hour[i].setName("hour" + i);
        }
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

    public static int[] userInput() {
//        Senaste året har elpriserna blivit högre och varierar mycket. Det här programmet ska kunna hjälpa till
//        med att analysera elpriser för ett dygn. När man väljer alternativet inmatning från menyn ska
//        programmet fråga efter priserna under dygnets timmar. Inmatningen av värden ska ske med hela
//        ören. Tex kan priser vara 50 102 eller 680 öre per kW/h. Priset sätts per intervall mella
//        två hela
//        timmar. Dygnets första pris är då mellan 00-01, andra intervallet är mellan 01-02 osv

        // Måste lägga till felhandtering för out of bounds. samt flytta saker till metoder.
//

        // Creates a new array and asks user for data.

        Scanner sc = new Scanner(System.in);
        int[] newUserData = new int[23];


        while (true) {

            for (int i = 0; i < newUserData.length; i++) {

                System.out.println("Ange priset för timme " + i + " (ange i hela ören.)");
                newUserData[i] = sc.nextInt();
            }
            for (int i = 0; i < newUserData.length; i++) {
                System.out.println("Du angav: " + newUserData[i] + " for timme " + i);
            }

            System.out.println("Vill du ange priserna på nytt? (Y/N)");
            String pricesAgain = sc.next().toUpperCase();

            if (pricesAgain.equals("Y"))
                continue;
            else if (pricesAgain.equals("N"))
                break;
            else {
                System.out.println("Fel input (Y/N) du åker tillbaka till menyn");
                break;
            }
        }
        return newUserData;
    }

   /* private static int[] userData() {
        int[] prices = new int[22];
        return prices;
    }*/

    private static int[] testUserData() {
        int[] prices = new int[]{1, 2, 3, 4, 5, 6};
        return prices;
    }

    public static void minMax(int[] userData) {
//        När alternativ 2 väljs på menyn så ska programmet skriva ut lägsta priset, högsta priset samt vilka
//        timmar som detta infaller under dygnet. Dygnets medelpris ska också räknas fram och presenteras på
//        skärmen.

        // Flytta saker till egna methoder.

        //Import userData and find min,max and average prices and print them.

        int minPrice = Arrays.stream(userData).min().getAsInt();
        int maxPrice = Arrays.stream(userData).max().getAsInt();
        double averagePrice = Arrays.stream(userData).average().getAsDouble();

        System.out.println("Lägsta priset är " + minPrice);
        System.out.println("Högsta priset är " + maxPrice);
        System.out.println("Medelpriset är " + (Math.round(averagePrice)));

    }

    public static void sortering(int[] userData) {
//        Skriv ut timmarna och priset för dessa sorterade efter billigast till dyrast pris. Ex:
//        00-01 23 öre
//        01-02 26 öre
//        05-06 30 öre
//        02-03 40 öre
        Arrays.sort(userData);
        for (int price : userData) {
            System.out.println(price);
        }

    }
//        for (int i = 0; i < userData.length ; i++) {
//
//            if (i <= i + 1) {
//                System.out.println(userData[i] + i);
//                i++;
//            }
//        }


    public static void ladda4h() {
//        Om man har en elbil som man vill ladda så vill man kanske göra det när priset är som billigast på
//        dygnet. Då batteriet behöver värmas/kylas så blir det minst förluster om man genomför laddningen
//        sammanhängande under ett antal timmar. Låt programmet hitta de 4 billigaste timmarna som ligger i
//        följd och skriva ut vid vilket klockslag man ska börja ladda för att få lägst totalpris samt vilket
//        medelpris det blir under dessa 4 timmar.}
    }

    public static void display() {
//            Ett normalt konsol fönster har 80 teckens bredd och här vill vi utnyttja det för att visualisera priserna
//            under dygnet. Lägg till ett 5:e alternativ för detta i din meny eller utöka alternativ 2 till att visa alla
//            priser utöver min, max och medel. Exempel på hur visualiseringen kan se ut med 75 teckens bredd.
//            Beroende på vilket prisintervall som gäller kan skalan behöva justeras dynamiskt.
//            530|
//                    x
//                    |
//                    x
//            x
//                    xx
//            xx
//                    xxxxxxxxxxxxxx
//            xxxx
//                    |xx|xxxxxxxxxxxxxxxxxxxxxxxxx
//            xxxxxxxxxxxxxx
//                    | xxxxxxxxx
//            40| xxxxxxxxx
//            x
//                    |-----------------------------------------------------------------------
//                    |00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19 20 21 22 23

    }
}
