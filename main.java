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
            // x.useDelimiter("[,]");

            // Read file to list
            while (x.hasNextLine()) {
                lines.add(x.nextLine().split(","));
            }
            // System.out.println(lines);

            // ArrayList<Integer> list = new ArrayList<Integer>();
            int j;
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
                        // list.add(i, val);
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
                System.out.print(" " + imageData[i][j]);
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
                    ArrayList<ArrayList<Integer>> data = readData(currentFile);
                    draw(data);
                    TimeUnit.MILLISECONDS.sleep(100);
                }

            } catch (InterruptedException e) {

            }
        }
    }

    public static int getSolidCount(ArrayList<ArrayList<Integer>> imageData) {
        int solidCount = 0;
        int rows = imageData.size(); // get the number of rows
        for (int i = 0; i < rows; i++) {
            ArrayList<Integer> currentRow = imageData.get(i);
            int cols = currentRow.size();
            for (int j = 0; j < cols; j++) {
                int currentVal = currentRow.get(j);
                if (currentVal > 0) {
                    solidCount += 1;
                }
            }
        }
        return solidCount;
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

    public static void superimposition() {

        int[][] result = new int[25][30];

        for (String file : filesList) {
            System.out.println("Reading: " + file);
            ArrayList<ArrayList<Integer>> imageData = readData(file);

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

    public static int[][] compare(String firstFile, String secondFile) {

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
        // draw(result);
        return result;
    }

    public static void compareAll() {

        for (int i = 0; i < filesList.length - 1; i++) {
            System.out.println("Compare Start..." + filesList[i] + " with " + filesList[i + 1]);
            compare(filesList[i], filesList[i + 1]);
        }
        System.out.println("Compare END...");
    }

    public static void similarityAll() {

        for (int i = 0; i < filesList.length - 1; i++) {
            System.out.println("Compare Start..." + filesList[i] + " with " + filesList[i + 1]);
            compare(filesList[i], filesList[i + 1]);
        }
        System.out.println("Compare END...");
    }

    public static void similarityRatio(String firstFile, String secondFile) {

        ArrayList<ArrayList<Integer>> firstFileData = readData(firstFile);
        ArrayList<ArrayList<Integer>> secondFileData = readData(secondFile);

        int firstFileSolidCount = getSolidCount(firstFileData);
        int firstFileTotal = getTotalCellCount(firstFileData);
        float solidPerA = ((float) firstFileSolidCount / (float) firstFileTotal);
        int secondFileSolidCount = getSolidCount(secondFileData);
        int secondFileTotal = getTotalCellCount(secondFileData);
        float solidPerB = ((float) secondFileSolidCount / (float) secondFileTotal);
        // System.out.println(ratio);
        // System.out.println(ratio * 100);
        float sum = ifChange(firstFile, secondFile);
        double similarityRatio = (((float)solidPerB * 100) / (float)solidPerA) - ((float)sum * 0.75);
        System.out.println("Similarity: "+firstFile +" and "+ secondFile +" = "+ similarityRatio);

        // System.out.println(firstFileTotal);

    }

    public static float ifChange(String file1, String file2) {
        int[][] result = compare(file1, file2);
        int sum = 0;
        for (int i = 0; i < result.length; i++) {

            for (int j = 0; j < result.length; j++) {
                if (result[i][j] == 1) {
                    sum += 1;
                }
            }
        }

        return (float)sum/750;

    }

    public static void main(String args[]) {
        // System.out.println("Hello Java");
        String file = filesList[0]; // change index for file
        ArrayList<ArrayList<Integer>> data = readData(file); // create matrix
        // int[][] a = {{1,2,3},{2,3,5},{1,1,1}};
        // draw(data);
        // System.out.println(Integer.parseInt(" 0")+ Integer.parseInt("1"));
        // animate(500);

        System.out.println("Question 1:");
        int voidCount = getVoidCount(data);
        System.out.println("Void Count: " + voidCount);
        int solidCount = getSolidCount(data);
        System.out.println("Solid Count: " + solidCount);
        int totalCellsCount = getTotalCellCount(data);
        System.out.println("Total Count: " + totalCellsCount);
        float solidRatio = (float) solidCount / (float) totalCellsCount;
        float voidRatio = (float) voidCount / (float) totalCellsCount;
        System.out.println("Solid Ratio: " + solidRatio);
        System.out.println("Void Ratio: " + voidRatio);
        System.out.println("----------------------------------------------");

        System.out.println("Question 2:");
        superimposition();
        System.out.println("----------------------------------------------");

        System.out.println("Question 3:");
        compareAll();

        System.out.println("Question 4:");

        similarityRatio("input02.txt", "input01.txt");
        similarityRatio("input03.txt", "input01.txt");
        similarityRatio("input04.txt", "input01.txt");
        similarityRatio("input05.txt", "input01.txt");

    }

}