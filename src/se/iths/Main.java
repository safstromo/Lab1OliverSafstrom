package se.iths;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        HourObject[] hourObject = new HourObject[24];

        menu();
        createHourObj(hourObject);
        userInput(hourObject);
        minMax(hourObject);
        sortering(hourObject);
        charge4h(hourObject);

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

    private static double getTotalPrice(HourObject[] hourObjects, double totalPrice) {
        // Calculate total price

        for (int i = 0; i < hourObjects.length; i++) {
            totalPrice = hourObjects[i].getPrice() + totalPrice;
        }
        return totalPrice;
    }

    private static int getMaxPrice(HourObject[] hourObjects, int maxPrice) {
        // Calculate max price
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
        // Calculate min price
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

            for (int i = 0; i < hourObjects.length -3; i++) {
                if (bestPrice4hTotal > next4hTotal)
                    bestPrice4hTotal = next4hTotal;

                for (int j = 0; j < 20; j++) {
                        next4hTotal = hourObjects[i].getPrice() + hourObjects[i + 1].getPrice() + hourObjects[i + 2].getPrice() + hourObjects[i + 3].getPrice();
                        name4hTotal = hourObjects[i].getName() + hourObjects[i + 3].getName();
                }

                averagePrice4h = next4hTotal / 4.0;
                System.out.println("Bästa laddningstiden under 4 timmar är " + name4hTotal);
                System.out.println("Medelpriset är då " + averagePrice4h + " öre kW/h");
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
