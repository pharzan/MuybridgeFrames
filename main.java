import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

class Main {

    public static ArrayList<ArrayList<Integer>> imageData;
    public static String[] filesList = new String[] { "input01.txt", "input02.txt", "input03.txt", "input04.txt",
            "input05.txt" };

    public static void readData(String fileName) {
        Scanner x;
        List<String[]> lines = new ArrayList<String[]>();
        imageData = new ArrayList<ArrayList<Integer>>();
        try {
            x = new Scanner(new File(fileName));
            x.useDelimiter("[,]");

            // Read file to list
            while (x.hasNextLine()) {
                lines.add(x.nextLine().split(","));
            }
            ArrayList<Integer> list = new ArrayList<Integer>();
            int j = 0;
            // Convert to number
            for (int i = 0; i < lines.size(); i++) {
                String[] objArr = (String[]) lines.get(i);
                imageData.add(new ArrayList<Integer>());
                j = 0;
                for (String obj : objArr) {
                    try {
                        j += 1;
                        int val = Integer.parseInt(obj.trim());
                        imageData.get(i).add(val);
                        list.add(i, val);
                        // System.out.println(val);

                    } catch (NumberFormatException e) {
                        // DO Nothing if not a number
                    }
                }
            }
            x.close();

        } catch (IOException e) {
            System.out.println("Error:" + e);
        }

    }

    public static void draw() {
        int rows = imageData.size();
        for (int i = 0; i < rows; i++) {
            ArrayList<Integer> currentRow = imageData.get(i);
            int cols = currentRow.size();
            for (int j = 0; j < cols; j++) {
                int currentVal = currentRow.get(j);
                System.out.print(currentVal);
            }
            System.out.print("\n");

        }
    }

    public static void animate(int times) {
        for (int i = 0; i < times; i++) {
            try {
                for (int j = 0; j < filesList.length; j++) {
                    String currentFile = filesList[j];
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println(currentFile);
                    readData(currentFile);
                    draw();
                    TimeUnit.MILLISECONDS.sleep(100);
                }

            } catch (InterruptedException e) {

            }
        }
    }

    public static int getVoidCount() {
        int voidCount = 0;
        int rows = imageData.size();
        for (int i = 0; i < rows; i++) {
            ArrayList<Integer> currentRow = imageData.get(i);
            int cols = currentRow.size();
            for (int j = 0; j < cols; j++) {
                int currentVal = currentRow.get(j);
                if (currentVal > 0) {
                    voidCount += 1;
                }
            }
        }
        return voidCount;
    }

    public static int getSolidCount() {

        int solidCount = 0;
        int rows = imageData.size();
        for (int i = 0; i < rows; i++) {
            ArrayList<Integer> currentRow = imageData.get(i);
            int cols = currentRow.size();
            for (int j = 0; j < cols; j++) {
                int currentVal = currentRow.get(j);
                if (currentVal == 0) {
                    solidCount += 1;
                }
            }
        }
        return solidCount;
    }

    public static int getTotalCellCount() {
        return imageData.size() * imageData.get(0).size();
    }

    public static void superimposition() {

        int[][] result = new int[imageData.size()][imageData.get(0).size()];

        for (String file : filesList) {
            System.out.println("Reading: " + file);
            readData(file);

            int rows = imageData.size();
            for (int i = 0; i < rows; i++) {
                ArrayList<Integer> currentRow = imageData.get(i);
                int cols = currentRow.size();
                for (int j = 0; j < cols; j++) {
                    int currentVal = currentRow.get(j);
                    result[i][j] += currentVal;
                }
            }
        }
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                System.out.print(result[i][j]);
            }
            System.out.print("\n");
        }

    }

    public static void main(String args[]) {
        System.out.println("Hello Java");
        String file = filesList[0]; // change index for file
        readData(file); // create matrix

        // animate(500);
        int voidCount = getVoidCount();
        int solidCount = getSolidCount();
        int totalCellsCount = getTotalCellCount();

        System.out.println("Void Count: " + voidCount);
        System.out.println("Solid Count: " + solidCount);
        System.out.println("Total Count: " + totalCellsCount);
        superimposition();

    }

}