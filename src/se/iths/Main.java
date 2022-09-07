package se.iths;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        HourObject[] hourObject = new HourObject[24];

        createHourObj(hourObject);
//        menu();
        userInput(hourObject);
//        minMax(hourObject);
//        sortering(hourObject);
        charge4h(hourObject);
        // display(hourObject);
    }

    public static void createHourObj(HourObject[] hourObject) {
// Creates one object for each hour.
        for (int i = 0; i < hourObject.length; i++) {
            hourObject[i] = new HourObject();
        }
        for (int i = 0; i < hourObject.length; i++) {
            hourObject[i].setName(i + "-" + (i + 1));
        }
    }

    public static String menu() {

        // Presents menu in console and returns the choise;

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

        // TODO !!! Måste lägga till felhantering för out of bounds. samt flytta saker till metoder.

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

        // Sort array and print cheapest 4 objects
        Arrays.sort(hourObjects, new Comparator<HourObject>() {
            @Override
            public int compare(HourObject o1, HourObject o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });

        //For-loop
        System.out.println(hourObjects[0].getName() + " -> " + hourObjects[0].getPrice() + " öre");
        System.out.println(hourObjects[1].getName() + " -> " + hourObjects[1].getPrice() + " öre");
        System.out.println(hourObjects[2].getName() + " -> " + hourObjects[2].getPrice() + " öre");
        System.out.println(hourObjects[3].getName() + " -> " + hourObjects[3].getPrice() + " öre");


    }

    private static double getTotalPrice(HourObject[] hourObjects) {
        // Calculate total price
        double totalPrice = 0;
        for (int i = 0; i < hourObjects.length; i++) {
            totalPrice = hourObjects[i].getPrice() + totalPrice;
        }
        return totalPrice;
    }

    private static int getMaxPrice(HourObject[] hourObjects) {
        // Calculate max price
        int maxPrice = 0;
        for (int i = 0; i < hourObjects.length; i++) {
            for (int j = 0; j < hourObjects.length; j++) {
                if (hourObjects[i].getPrice() > maxPrice) {
                    maxPrice = hourObjects[i].getPrice();
                }
            }
        }
        return maxPrice;
    }

    private static int getMinPrice(HourObject[] hourObjects) {
        // Calculate min price
        int minPrice = 0;
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

        // Calculate best 4 hour in a row to charge

        int next4hPrice;
        double averagePriceBest4h = 0;
        int bestPrice4hTotal = Integer.MAX_VALUE;
        String nameBestPrice4h = "";


        for (int i = 0; i < hourObjects.length; i++) {  // Find best 4 hours
            if (i == hourObjects.length - 3) {
                break;
            }
            next4hPrice = hourObjects[i].getPrice() + hourObjects[i + 1].getPrice() + hourObjects[i + 2].getPrice() + hourObjects[i + 3].getPrice();
            if (next4hPrice < bestPrice4hTotal) {
                bestPrice4hTotal = next4hPrice;
                nameBestPrice4h = hourObjects[i].getName() + "-" + hourObjects[i + 2].getName();
            }
        }
        averagePriceBest4h = bestPrice4hTotal / 4.0;

        printBest4h(averagePriceBest4h, nameBestPrice4h);
    }

    private static void printBest4h(double averagePriceBest4h, String nameBestPrice4h) {
        System.out.println("Bästa laddningstiden under 4 timmar är " + nameBestPrice4h);
        System.out.println("Medelpriset är då " + averagePriceBest4h + " öre kW/h");
    }

    public static void display(HourObject[] hourObjects) {
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

//int maxPrice = Integer.MAX_VALUE;
//        for (int row = 0; row < getMaxPrice(hourObjects,maxPrice)/ 10; row++) {
//            System.out.println();
//            for (int collumn = 0; collumn < 75; collumn++) {
//                if (collumn == 0 && row < getMaxPrice(hourObjects,maxPrice) / 10){
//                    System.out.println("|");
//                } else if (row == getMaxPrice(hourObjects,maxPrice)/ 10) {
//                    System.out.print("-");
//                }else {
//                    System.out.print("");
//            }

//            }

//        }

    }
}
