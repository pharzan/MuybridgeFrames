import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

class Main {

    public static String[] filesList = new String[] { "input01.txt", "input02.txt", "input03.txt", "input04.txt",
            "input05.txt" };

    public static ArrayList<ArrayList<Integer>> readData(String fileName) {
        Scanner x;
        List<String[]> lines = new ArrayList<String[]>();
        ArrayList<ArrayList<Integer>> imageData = new ArrayList<ArrayList<Integer>>();
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
        return imageData;

    }

    public static void draw(ArrayList<ArrayList<Integer>> imageData) {
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

    public static void draw(int[][] imageData) {
        for (int i = 0; i < imageData.length; i++) {
            for (int j = 0; j < imageData[i].length; j++) {
                System.out.print(imageData[i][j]);
            }
            System.out.print("\n");
        }

        System.out.print("----end \n");
    }

    public static void animate(int times) {
        for (int i = 0; i < times; i++) {
            try {
                for (int j = 0; j < filesList.length; j++) {
                    String currentFile = filesList[j];
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    System.out.println(currentFile);
                    ArrayList<ArrayList<Integer>> data = readData(currentFile);
                    draw(data);
                    TimeUnit.MILLISECONDS.sleep(100);
                }

            } catch (InterruptedException e) {

            }
        }
    }

    public static int getSolidCount(ArrayList<ArrayList<Integer>> imageData) {
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

    public static int getVoidCount(ArrayList<ArrayList<Integer>> imageData) {

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

    public static int getTotalCellCount(ArrayList<ArrayList<Integer>> imageData) {
        return imageData.size() * imageData.get(0).size();
    }

    public static void superimposition(ArrayList<ArrayList<Integer>> imageData) {

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
        draw(result);

    }

    public static void compare(String firstFile, String secondFile) {

        ArrayList<ArrayList<Integer>> firsData = readData(firstFile);
        ArrayList<ArrayList<Integer>> secondData = readData(secondFile);
        int[][] result = new int[firsData.size()][firsData.get(0).size()];

        int firstRows = firsData.size();
        for (int i = 0; i < firstRows; i++) {
            ArrayList<Integer> firstFileRow = firsData.get(i);
            ArrayList<Integer> secondFileRow = secondData.get(i);
            int cols = firstFileRow.size();
            for (int j = 0; j < cols; j++) {
                int a = firstFileRow.get(j);
                int b = secondFileRow.get(j);
                if (a == b) {
                    result[i][j] = 0;
                } else {
                    result[i][j] = 1;
                }
            }
        }
        draw(result);
    }

    public static void compareAll() {
        System.out.println("Compare Start...");

        for (int i = 0; i < filesList.length - 1; i++) {
            compare(filesList[i], filesList[i + 1]);
        }
        System.out.println("Compare END...");
    }

    public static void similarityRatio(String firstFile, String secondFile) {

    }

    public static void main(String args[]) {
        System.out.println("Hello Java");
        String file = filesList[0]; // change index for file
        ArrayList<ArrayList<Integer>> data = readData(file); // create matrix

        // animate(500);
        int voidCount = getVoidCount(data);
        int solidCount = getSolidCount(data);
        int totalCellsCount = getTotalCellCount(data);

        System.out.println("Void Count: " + voidCount);
        System.out.println("Solid Count: " + solidCount);
        System.out.println("Total Count: " + totalCellsCount);
        superimposition(data);
        compareAll();
    }

}