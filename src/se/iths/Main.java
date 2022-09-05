package se.iths;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        HourObject[] hourObject = new HourObject[24];

        createHourObj(hourObject);
        userInput(hourObject);
        minMax(hourObject);
        sortering(hourObject);
        charge4h(hourObject);
        //System.out.println(menu());
        // int[] userData = new int[]{2,43,5,6,7,4,54,76,67,34,4,65,65,87,4,4,32,432,45,45,12,324,43,};
        //int[] userData = userInput();
        //userInput();
        //minMax(userInput());
        //  sortering(userData);
    }

    public static void createHourObj(HourObject[] hourObject) {

        for (int i = 0; i < hourObject.length; i++) {
            hourObject[i] = new HourObject();
        }
        for (int i = 0; i < hourObject.length; i++) {
            hourObject[i].setName(i + "-" + (i + 1));
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

    public static void userInput(HourObject[] hourObject) {
//        Senaste året har elpriserna blivit högre och varierar mycket. Det här programmet ska kunna hjälpa till
//        med att analysera elpriser för ett dygn. När man väljer alternativet inmatning från menyn ska
//        programmet fråga efter priserna under dygnets timmar. Inmatningen av värden ska ske med hela
//        ören. Tex kan priser vara 50 102 eller 680 öre per kW/h. Priset sätts per intervall mella
//        två hela
//        timmar. Dygnets första pris är då mellan 00-01, andra intervallet är mellan 01-02 osv

        // Måste lägga till felhandtering för out of bounds. samt flytta saker till metoder.
//

        // Asks user for data. Adds data to each hour.

        Scanner sc = new Scanner(System.in);

        while (true) {

            for (int i = 0; i < hourObject.length; i++) {

                System.out.println("Ange priset per kW/h för varje timme " + hourObject[i].getName() + " (ange i hela ören.)");
                hourObject[i].setPrice(sc.nextInt());
            }
            for (int i = 0; i < hourObject.length; i++) {
                System.out.println("Du angav: " + hourObject[i].getPrice() + " öre per kW/h för timme " + hourObject[i].getName());
            }

            System.out.println("Stämmer detta?\nVill du ange priserna på nytt? (Y/N)");
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

    }

    public static void minMax(HourObject[] hourObjects) {
//        När alternativ 2 väljs på menyn så ska programmet skriva ut lägsta priset, högsta priset samt vilka
//        timmar som detta infaller under dygnet. Dygnets medelpris ska också räknas fram och presenteras på
//        skärmen.

        //Import hourObjects and find min,max and average prices and print them.

        int minPrice = Integer.MAX_VALUE;
        int maxPrice = Integer.MIN_VALUE;
        double totalPrice = 0;

        minPrice = getMinPrice(hourObjects, minPrice);
        maxPrice = getMaxPrice(hourObjects, maxPrice);
        totalPrice = getTotalPrice(hourObjects, totalPrice);

        double averagePrice = (double) totalPrice / 24.0;

        System.out.println("Lägsta priset är " + minPrice);
        System.out.println("Högsta priset är " + maxPrice);
        System.out.println("Medelpriset är " + (Math.round(averagePrice)));

    }

    public static void sortering(HourObject[] hourObjects) {
//        Skriv ut timmarna och priset för dessa sorterade efter billigast till dyrast pris. Ex:
//        00-01 23 öre
//        01-02 26 öre
//        05-06 30 öre
//        02-03 40 öre

        // Sort array and print cheapest 4 objects
        Arrays.sort(hourObjects, new Comparator<HourObject>() {
            @Override
            public int compare(HourObject o1, HourObject o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });

        System.out.println(hourObjects[0].getName() + " -> " + hourObjects[0].getPrice() + " öre");
        System.out.println(hourObjects[1].getName() + " -> " + hourObjects[1].getPrice() + " öre");
        System.out.println(hourObjects[2].getName() + " -> " + hourObjects[2].getPrice() + " öre");
        System.out.println(hourObjects[3].getName() + " -> " + hourObjects[3].getPrice() + " öre");


    }

    private static double getTotalPrice(HourObject[] hourObjects, double totalPrice) {
        for (int i = 0; i < hourObjects.length; i++) {
            totalPrice = hourObjects[i].getPrice() + totalPrice;
        }
        return totalPrice;
    }

    private static int getMaxPrice(HourObject[] hourObjects, int maxPrice) {
        for (int i = 0; i < hourObjects.length; i++) {
            for (int j = 0; j < hourObjects.length; j++) {
                if (hourObjects[i].getPrice() > maxPrice) {
                    maxPrice = hourObjects[i].getPrice();
                }
            }
        }
        return maxPrice;
    }

    private static int getMinPrice(HourObject[] hourObjects, int minPrice) {
        for (int i = 0; i < hourObjects.length; i++) {
            for (int j = 0; j < hourObjects.length; j++) {
                if (hourObjects[i].getPrice() < minPrice) {
                    minPrice = hourObjects[i].getPrice();
                }
            }
        }
        return minPrice;
    }

    public static void charge4h(HourObject[] hourObjects) {
//        Om man har en elbil som man vill ladda så vill man kanske göra det när priset är som billigast på
//        dygnet. Då batteriet behöver värmas/kylas så blir det minst förluster om man genomför laddningen
//        sammanhängande under ett antal timmar. Låt programmet hitta de 4 billigaste timmarna som ligger i
//        följd och skriva ut vid vilket klockslag man ska börja ladda för att få lägst totalpris samt vilket
//        medelpris det blir under dessa 4 timmar.}

        int bestPrice4hTotal = Integer.MAX_VALUE;
        int next4hTotal = Integer.MAX_VALUE - 1;
        double averagePrice4h = 0;
        String name4hTotal = "";

            for (int i = 0; i < hourObjects.length; i++) {
                if (bestPrice4hTotal > next4hTotal)
                    bestPrice4hTotal = next4hTotal;

                for (int j = 0; j < 24; j++) {
                        next4hTotal = hourObjects[i].getPrice() + hourObjects[i + 1].getPrice() + hourObjects[i + 2].getPrice() + hourObjects[i + 3].getPrice();
                        name4hTotal = hourObjects[i].getName() + hourObjects[i + 3].getName();
                }

                averagePrice4h = next4hTotal / 4.0;
                System.out.println("Bästa laddningstiden under 4 timmar är " + name4hTotal);
                System.out.println("Medelpriset är då" + averagePrice4h + " öre kW/h");
            }
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
