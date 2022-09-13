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
                case "3" -> sortHourCheapestFirst(hourObject);
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
                case "e", "E" -> {
                    input = "e";
                    validInput = true;
                }
            }
        }
        return input;
    }

    private static void printMenu() {
        System.out.println("\n========\nElpriser\n========");
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

            getUserInput(hourObject, sc);
            showInput(hourObject);
            continueLoop = checkInputCorrect(sc);
        }

    }

    private static boolean checkInputCorrect(Scanner sc) {
        boolean contLoop = false;
        boolean validInput = false;

        do {
            System.out.println("Vill du ange priserna på nytt? (Y/N)");
            String pricesAgain = sc.next().toUpperCase();
            switch (pricesAgain) {
                case "Y" -> {
                    contLoop = true;
                    validInput = true;
                }
                case "N" -> {

                    validInput = true;
                }

                default -> {

                    System.out.println("===========================================");
                    System.out.println("Fel input försök igen!");
                    System.out.println("===========================================");
                    System.out.println();

                }
            }
        }
        while (!validInput);

        return contLoop;
    }

    private static void getUserInput(HourObject[] hourObject, Scanner sc) {
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
        System.out.println("Stämmer detta?");
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

    public static void sortHourCheapestFirst(HourObject[] hourObjects) {

        // Sort array and print cheapest objects
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
            if (i == hourObjects.length - 1)
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

        // Sort objects by name then calculate best 4 hour in a row to charge
        sortByName(hourObjects);


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

    private static void sortByName(HourObject[] hourObjects) {
        Arrays.sort(hourObjects, new Comparator<HourObject>() {
            @Override
            public int compare(HourObject o1, HourObject o2) {
                return CharSequence.compare(o1.getName(), o2.getName());
            }
        });
    }

    private static void printBest4h(double averagePriceBest4h, String nameBestPrice4h) {
        System.out.println("Bästa laddningstiden under 4 timmar är " + nameBestPrice4h);
        System.out.println("Medelpriset är då " + averagePriceBest4h + " öre kW/h");
    }

    public static void display(HourObject[] hourObjects) {

        int graphHeight = 20;
        int graphWidth = 29;
        String[][] graph = new String[graphHeight][graphWidth];


        sortByName(hourObjects);
        fillGraphArray(graph);


        addTop20(hourObjects, graph);
        addTop40(hourObjects, graph);
        addTop60(hourObjects, graph);
        addTop80(hourObjects, graph);
        addIfNot0(hourObjects, graph);

        addMaxPriceHour(hourObjects, graph);
        addXYToGraph(graphHeight, graph);
        addMinMaxToGraph(hourObjects, graph);
        addHourToGraph(hourObjects, graphHeight, graph);

        printGraph(graph);


    }

    private static void addIfNot0(HourObject[] hourObjects, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 4; column < graph[row].length; column++) {
                if (column < 28 && row > 15 && hourObjects[column - 4].getPrice() < getMaxPrice(hourObjects)) {
                    if ((hourObjects[column - 4].getPrice()) > 1) {
                        graph[row][column] = " # ";
                    } else graph[row][column] = "    ";
                }


            }
        }
    }

    private static void addTop80(HourObject[] hourObjects, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 4; column < graph[row].length; column++) {
                if (column < 28 && row > 12 && hourObjects[column - 4].getPrice() < getMaxPrice(hourObjects)) {
                    if ((hourObjects[column - 4].getPrice()) > 20) {
                        graph[row][column] = " # ";
                    } else graph[row][column] = "    ";
                }


            }
        }
    }

    private static void addTop60(HourObject[] hourObjects, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 4; column < graph[row].length; column++) {
                if (column < 28 && row > 9 && hourObjects[column - 4].getPrice() < getMaxPrice(hourObjects)) {
                    if ((hourObjects[column - 4].getPrice()) > 50) {
                        graph[row][column] = " # ";
                    } else graph[row][column] = "    ";
                }


            }
        }
    }

    private static void addTop40(HourObject[] hourObjects, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 4; column < graph[row].length; column++) {
                if (column < 28 && row > 6 && hourObjects[column - 4].getPrice() < getMaxPrice(hourObjects)) {
                    if ((hourObjects[column - 4].getPrice()) > 80) {
                        graph[row][column] = " # ";
                    } else graph[row][column] = "    ";
                }


            }
        }
    }

    private static void addTop20(HourObject[] hourObjects, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 4; column < graph[row].length; column++) {
                if (column < 28 && row > 3 && hourObjects[column - 4].getPrice() < getMaxPrice(hourObjects)) {
                    if ((hourObjects[column - 4].getPrice()) > 100) {
                        graph[row][column] = " # ";
                    } else graph[row][column] = "    ";
                }


            }
        }
    }


    private static void addMaxPriceHour(HourObject[] hourObjects, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 4; column < graph[row].length; column++) {
                if (column < 28 && row < graph.length - 2)
                    if (hourObjects[column - 4].getPrice() == getMaxPrice(hourObjects)) {
                        graph[row][column] = " # ";
                    }
            }
        }
    }

    private static void addHourToGraph(HourObject[] hourObjects, int graphHeight, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 0; column < graph[row].length; column++) {

                if (row == graphHeight - 1 && column <= 4)
                    graph[row][column] = " ";
                else if (row == graphHeight - 1 && column < 28) {
                    graph[row][column] = " " + hourObjects[column - 4].getName();


                }
            }
        }
    }

    private static void fillGraphArray(String[][] graph) {
        for (int rows = 0; rows < graph.length; rows++) {
            Arrays.fill(graph[rows], "");
        }
    }

    private static void addXYToGraph(int graphHeight, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 0; column < graph[row].length; column++) {
                if (row == graphHeight - 2 && column < 28)
                    graph[row][column] = "---";
                else if (column == 3 && row < graph.length - 2) {
                    graph[row][column] = "|";
                }
            }
        }
    }

    private static void addMinMaxToGraph(HourObject[] hourObjects, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 0; column < graph[row].length; column++) {
                if (column == 0)
                    if (row == 0) {
                        graph[row][column] = Integer.toString(getMaxPrice(hourObjects));
                    } else if (row == graph.length - 3) {
                        graph[row][column] = Integer.toString(getMinPrice(hourObjects)) + "  ";
                    } else graph[row][column] = "   ";

            }


        }
    }


    private static void printGraph(String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            System.out.println();
            for (int collumn = 0; collumn < graph[row].length; collumn++) {
                System.out.print(graph[row][collumn]);
            }
        }
    }
}










