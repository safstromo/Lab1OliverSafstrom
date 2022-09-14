package se.iths;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Hour[] hour = new Hour[24];
        createHourObj(hour);

        boolean continueLoop = true;
        while (continueLoop) {


            switch (menu()) {
                case "1" -> userInput(hour);
                case "2" -> printMinMax(hour);
                case "3" -> sortHourCheapestFirst(hour);
                case "4" -> charge4h(hour);
                case "5" -> display(hour);
                case "e" -> continueLoop = false;
            }
        }
    }

    public static void createHourObj(Hour[] hour) {
// Creates one object for each hour.
        for (int i = 0; i < hour.length; i++) {
            hour[i] = new Hour();
        }
        for (int i = 0; i < hour.length; i++) {
            if (i <= 9) {
                hour[i].setName("0" + Integer.toString(i));
            } else {
                hour[i].setName(Integer.toString(i));
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

    public static void userInput(Hour[] hour) {
        // Asks user for data. Adds data to each hour.

        Scanner sc = new Scanner(System.in);
        boolean continueLoop = true;
        while (continueLoop) {

            getUserInput(hour, sc);
            showInput(hour);
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

    private static void getUserInput(Hour[] hour, Scanner sc) {
        for (int i = 0; i < hour.length; i++) {

            System.out.println("Ange priset per kW/h för varje timme " + hour[i].getName() + " (ange i hela ören.)");
            try {
                hour[i].setPrice(sc.nextInt());
            } catch (Exception e) {
                break;
            }
        }
    }

    private static void showInput(Hour[] hour) {
        for (int i = 0; i < hour.length; i++) {
            System.out.println("Du angav: " + hour[i].getPrice() + " öre per kW/h för timme " + hour[i].getName());
        }
        System.out.println("Stämmer detta?");
    }

    public static void printMinMax(Hour[] hours) {
//TODO add hours in print!
        //Import hourObjects and find min,max and average prices and print them.

        int minPrice = getMinPrice(hours);
        int maxPrice = getMaxPrice(hours);
        double totalPrice = getTotalPrice(hours);

        double averagePrice = (double) totalPrice / 24.0;

        System.out.println("Lägsta priset är " + minPrice);
        System.out.println("Högsta priset är " + maxPrice);
        System.out.println("Medelpriset är " + (Math.round(averagePrice)));

    }

    public static void sortHourCheapestFirst(Hour[] hours) {

        // Sort array and print cheapest objects
        Arrays.sort(hours, new Comparator<Hour>() {
            @Override
            public int compare(Hour o1, Hour o2) {
                return Double.compare(o1.getPrice(), o2.getPrice());
            }
        });

        printSorted(hours);

    }

    private static void printSorted(Hour[] hours) {
        for (int i = 0; i < hours.length; i++) {
            if (i == hours.length - 1)
                break;
            System.out.println(hours[i].getName() + "-" + hours[i + 1].getName() + " -> " + hours[i].getPrice() + " öre");
        }
    }

    private static double getTotalPrice(Hour[] hours) {
        // Calculate total price
        double totalPrice = 0;
        for (int i = 0; i < hours.length; i++) {
            totalPrice = hours[i].getPrice() + totalPrice;
        }
        return totalPrice;
    }

    private static int getMaxPrice(Hour[] hours) {
        // Calculate max price
        int maxPrice = hours[0].getPrice();
        for (int i = 0; i < hours.length; i++) {
            for (int j = 0; j < hours.length; j++) {
                if (hours[i].getPrice() > maxPrice) {
                    maxPrice = hours[i].getPrice();
                }
            }
        }
        return maxPrice;
    }

    private static int getMinPrice(Hour[] hours) {
        // Calculate min price
        int minPrice = hours[0].getPrice();
        for (int i = 0; i < hours.length; i++) {
            for (int j = 0; j < hours.length; j++) {
                if (hours[i].getPrice() < minPrice) {
                    minPrice = hours[i].getPrice();
                }
            }
        }
        return minPrice;
    }

    public static void charge4h(Hour[] hours) {

        // Sort objects by name then calculate best 4 hour in a row to charge
        sortByName(hours);


        int next4hPrice;
        double averagePriceBest4h = 0;
        int bestPrice4hTotal = Integer.MAX_VALUE;
        String nameBestPrice4h = "";


        for (int i = 0; i < hours.length; i++) {  // Find best 4 hours
            if (i == hours.length - 3) {
                break;
            }
            next4hPrice = hours[i].getPrice() + hours[i + 1].getPrice() + hours[i + 2].getPrice() + hours[i + 3].getPrice();
            if (next4hPrice < bestPrice4hTotal) {
                bestPrice4hTotal = next4hPrice;
                nameBestPrice4h = hours[i].getName() + " - " + hours[i + 4].getName();
            }
        }
        averagePriceBest4h = bestPrice4hTotal / 4.0;

        printBest4h(averagePriceBest4h, nameBestPrice4h);
    }

    private static void sortByName(Hour[] hours) {
        Arrays.sort(hours, new Comparator<Hour>() {
            @Override
            public int compare(Hour o1, Hour o2) {
                return CharSequence.compare(o1.getName(), o2.getName());
            }
        });
    }

    private static void printBest4h(double averagePriceBest4h, String nameBestPrice4h) {
        System.out.println("Bästa laddningstiden under 4 timmar är " + nameBestPrice4h);
        System.out.println("Medelpriset är då " + averagePriceBest4h + " öre kW/h");
    }

    public static void display(Hour[] hours) {

        int graphHeight = 20;
        int graphWidth = 29;
        String[][] graph = new String[graphHeight][graphWidth];


        sortByName(hours);
        fillGraphArray(graph);


        addPriceOver0(hours, graph);
        addPricesOver50(hours, graph);
        addPricesOver100(hours, graph);
        addPricesOver200(hours, graph);
        addPricesOver300(hours, graph);
        addMaxPriceHour(hours, graph);
        addXYToGraph(graphHeight, graph);
        addMinMaxToGraph(hours, graph);
        addHourToGraph(hours, graphHeight, graph);

        printGraph(graph);


    }

    private static void addPriceOver0(Hour[] hours, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 4; column < graph[row].length; column++) {
                if (column < 28 && row > 15 && hours[column - 4].getPrice() < getMaxPrice(hours)) {
                    if (hours[column - 4].getPrice() > 1) {
                        graph[row][column] = " # ";
                    }
                } else graph[row][column] = "   ";
            }


        }
    }

    private static void addPricesOver50(Hour[] hours, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 4; column < graph[row].length; column++) {
                if (column < 28 && row > 12 && hours[column - 4].getPrice() < getMaxPrice(hours)) {
                    if (hours[column - 4].getPrice() > 50) {
                        graph[row][column] = " # ";
                    }
                } //else graph[row][column] = "   ";
            }


        }
    }


    private static void addPricesOver100(Hour[] hours, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 4; column < graph[row].length; column++) {
                if (column < 28 && row > 9 && hours[column - 4].getPrice() < getMaxPrice(hours)) {
                    if (hours[column - 4].getPrice() > 100) {
                        graph[row][column] = " # ";
                    }
                } //else graph[row][column] = "   ";
            }


        }
    }

    private static void addPricesOver200(Hour[] hours, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 4; column < graph[row].length; column++) {
                if (column < 28 && row > 6 && hours[column - 4].getPrice() < getMaxPrice(hours)) {
                    if (hours[column - 4].getPrice() > 200) {
                        graph[row][column] = " # ";
                    }
                }// else graph[row][column] = "   ";
            }


        }
    }


    private static void addPricesOver300(Hour[] hours, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 4; column < graph[row].length; column++) {
                if (column < 28 && row > 3 && hours[column - 4].getPrice() < getMaxPrice(hours)) {

                    if (hours[column - 4].getPrice() > 300) {
                        graph[row][column] = " # ";
                    }
                }//else graph[row][column] = "   ";


            }
        }
    }


    private static void addMaxPriceHour(Hour[] hours, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 4; column < graph[row].length; column++) {
                if (column < 28 && row < graph.length - 2)
                    if (hours[column - 4].getPrice() == getMaxPrice(hours)) {
                        graph[row][column] = " # ";
                    }
            }
        }
    }

    private static void addHourToGraph(Hour[] hours, int graphHeight, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 0; column < graph[row].length; column++) {

                if (row == graphHeight - 1 && column < 4)
                    graph[row][column] = " ";
                else if (row == graphHeight - 1 && column < 28) {
                    graph[row][column] = hours[column - 4].getName() + " ";


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

    private static void addMinMaxToGraph(Hour[] hours, String[][] graph) {
        for (int row = 0; row < graph.length; row++) {
            for (int column = 0; column < graph[row].length; column++) {
                if (column == 0)
                    if (row == 0) {
                        graph[row][column] = Integer.toString(getMaxPrice(hours));
                    } else if (row == graph.length - 3) {
                        graph[row][column] = Integer.toString(getMinPrice(hours)) + "  ";
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










