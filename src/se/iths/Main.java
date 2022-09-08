package se.iths;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        HourObject[] hourObject = new HourObject[24];
        createHourObj(hourObject);
        boolean continueLoop = true;
        while (continueLoop) {


            switch (menu()) {
                case "1" -> userInput(hourObject);
                case "2" -> printMinMax(hourObject);
                case "3" -> sortering(hourObject);
                case "4" -> charge4h(hourObject);
                case "5" -> display(hourObject);
                case "e" -> continueLoop = false;
            }
        }
    }

    public static void createHourObj(HourObject[] hourObject) {
// Creates one object for each hour.
        for (int i = 0; i < hourObject.length; i++) {
            hourObject[i] = new HourObject();
        }
        for (int i = 0; i < hourObject.length; i++) {
            if (i <= 9) {
                hourObject[i].setName("0" + Integer.toString(i));
            } else {
                hourObject[i].setName(Integer.toString(i));
            }
        }
    }

    public static String menu() {

        // Presents menu in console and returns the choise;

        Scanner sc = new Scanner(System.in);
        String input = "";

        boolean validInput = false;

        // Loop until valid input.
        while (!validInput) {

            printMenu();

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
                case "5" -> {
                    input = "5";
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

    private static void printMenu() {
        System.out.println("========\nElpriser\n========");
        System.out.println("1. Inmatning");
        System.out.println("2. Min, Max och Medel");
        System.out.println("3. Sortera");
        System.out.println("4. Bästa Laddningstid (4h)");
        System.out.println("5. Visa stapeldiagram.");
        System.out.println("e. Avsluta");
    }

    public static void userInput(HourObject[] hourObject) {
        // Asks user for data. Adds data to each hour.

        Scanner sc = new Scanner(System.in);
        boolean continueLoop = true;
        while (continueLoop) {

            getUserinput(hourObject, sc);
            showInput(hourObject);

            continueLoop = checkInputCorrect(sc);
        }

    }

    private static boolean checkInputCorrect(Scanner sc) {
        boolean contLoop;
        System.out.println("Stämmer detta?\nVill du ange priserna på nytt? (Y/N)");
        String pricesAgain = sc.next().toUpperCase();

        if (pricesAgain.equals("Y"))
            contLoop = true;
        else if (pricesAgain.equals("N"))
            contLoop = false;
        else {
            System.out.println("===========================================");
            System.out.println("Fel input (Y/N) du åker tillbaka till menyn");
            System.out.println("===========================================");
            System.out.println();
            contLoop = false;
        }
        return contLoop;
    }

    private static void getUserinput(HourObject[] hourObject, Scanner sc) {
        for (int i = 0; i < hourObject.length; i++) {

            System.out.println("Ange priset per kW/h för varje timme " + hourObject[i].getName() + " (ange i hela ören.)");
            try {
                hourObject[i].setPrice(sc.nextInt());
            } catch (Exception e) {
                break;
            }
        }
    }

    private static void showInput(HourObject[] hourObject) {
        for (int i = 0; i < hourObject.length; i++) {
            System.out.println("Du angav: " + hourObject[i].getPrice() + " öre per kW/h för timme " + hourObject[i].getName());
        }
    }

    public static void printMinMax(HourObject[] hourObjects) {

        //Import hourObjects and find min,max and average prices and print them.

        int minPrice = getMinPrice(hourObjects);
        int maxPrice = getMaxPrice(hourObjects);
        double totalPrice = getTotalPrice(hourObjects);

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

        printSorted(hourObjects);

    }

    private static void printSorted(HourObject[] hourObjects) {
        for (int i = 0; i < hourObjects.length; i++) {
            if (i == hourObjects.length -1)
                break;
            System.out.println(hourObjects[i].getName() + "-" + hourObjects[i + 1].getName() + " -> " + hourObjects[i].getPrice() + " öre");
        }
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
        int maxPrice = hourObjects[0].getPrice();
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
        int minPrice = hourObjects[0].getPrice();
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
        Arrays.sort(hourObjects, new Comparator<HourObject>() {
            @Override
            public int compare(HourObject o1, HourObject o2) {
                return CharSequence.compare(o1.getName(), o2.getName());
            }
        });




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
                nameBestPrice4h = hourObjects[i].getName() + " - " + hourObjects[i + 4].getName();
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


        for (int row = 0; row <= 24; row++) {
            System.out.println();

            for (int collumn = 0; collumn < 75; collumn++) {
                if (collumn == 0 && row < 24) {
                    System.out.print(row);
                } else if (row == 0) {
                    for (int i = hourObjects[0].getPrice(); i > 0; i--) {
//                        String julia = Integer.toString(hourObjects[0].getPrice());
                        System.out.print("x");

                    }
                } else if (row == 24) {
                    System.out.print("-");
                } else if (collumn == 1) {
                    System.out.print("|");
                } else {
                    System.out.print("");
                }

            }

        }
        System.out.println();

    }
}
