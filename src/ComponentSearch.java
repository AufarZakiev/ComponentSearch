import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by asus on 01.04.2014.
 */
public class ComponentSearch {
    static int[][] matrix;

    public static void main(String[] args) {
        //readGraphFromConsole();
        //generateTests();
        //generateTestsForBFS();
        try {
            FileWriter timeFile = new FileWriter("BFSstats(ForBFS).txt");
            PrintWriter out = new PrintWriter(timeFile);
            for (int i = 1; i < 100; i++) {
                readGraphFromFile(i);

                long BFSstart = System.currentTimeMillis();
                int answerBFS = bfs();
                long BFStime = System.currentTimeMillis() - BFSstart;

                out.println(BFStime);
                System.out.println("Test " + i + "\nBFS says: " + answerBFS);

            }
            out.close();
            timeFile.close();

            FileWriter timeFile2 = new FileWriter("DFSstats(ForBFS).txt");
            PrintWriter out2 = new PrintWriter(timeFile2);
            for (int i = 1; i < 100; i++) {
                readGraphFromFile(i);

                long DFSstart = System.currentTimeMillis();
                int answerDFS = dfs();
                long DFStime = System.currentTimeMillis() - DFSstart;

                out2.println(DFStime);
                System.out.println("Test " + i + "\nDFS says: " + answerDFS);
            }
            out2.close();
            timeFile2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateTests() {
        Random gen = new Random();
        try {
            for (int i = 1; i < 100; i++) {
                FileWriter file2 = new FileWriter("tests/test" + i + ".txt");
                PrintWriter out = new PrintWriter(file2);
                int V = i * 200;
                out.println(V);
                out.println(V);
                for (int j = 0; j < V; j++) {
                    out.print(gen.nextInt(V) + " " + gen.nextInt(V) + "\n");
                }
                out.close();
                file2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateTestsForBFS(){
        Random gen = new Random();
        try {
            for (int i = 1; i < 100; i++) {
                FileWriter file2 = new FileWriter("testsForBFS/test" + i + ".txt");
                PrintWriter out = new PrintWriter(file2);
                int V = i * 200;
                out.println(V);
                out.println(V);
                for (int j = 0; j < V; j++) {
                    out.print("1" + " " + gen.nextInt(V) + "\n");
                }
                out.close();
                file2.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void readGraphFromConsole() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите количество вершин: ");
        int N = in.nextInt();
        System.out.print("Введите количество ребер: ");
        int M = in.nextInt();
        matrix = new int[N + 1][N + 1];
        System.out.println("Введите номера вершин, инцидентных ребрам: ");
        for (int i = 0; i < M; i++) {
            int row = in.nextInt();
            int column = in.nextInt();
            matrix[row][column] = 1;
            matrix[column][row] = 1;
        }
    }

    private static void readGraphFromFile(int numberOfFile) {
        try {
            FileReader fileIn = new FileReader("testsForBFS/test" + numberOfFile + ".txt");
            Scanner in = new Scanner(fileIn);
            int N = in.nextInt();
            int M = in.nextInt();
            matrix = new int[N + 1][N + 1];
            for (int j = 0; j < M; j++) {
                int row = in.nextInt();
                int column = in.nextInt();
                matrix[row][column] = 1;
                matrix[column][row] = 1;
            }
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int bfs() {
        int componentNumber = 0;
        List<Integer> vertexQueue = new ;
        int currentRoot = 1;
        boolean[] visited = new boolean[matrix.length];

        do {
            vertexQueue.add(currentRoot);
            while (!vertexQueue.isEmpty()) {
                int currentVertex = vertexQueue.poll();
                for (int i = 1; i < visited.length; i++) {
                    if ((matrix[currentVertex][i] == 1) && (visited[i] == false)) {
                        vertexQueue.add(i);
                    }
                }
                visited[currentVertex] = true;
            }
            componentNumber++;
            while (currentRoot < visited.length && visited[currentRoot] == true) {
                currentRoot++;
            }
        } while (currentRoot < visited.length);
        return componentNumber;
    }

    private static int dfs() {
        int componentNumber = 0;
        LinkedList<Integer> vertexStack = new LinkedList();
        int currentRoot = 1;
        boolean[] visited = new boolean[matrix.length];

        do {
            vertexStack.push(currentRoot);
            while (!vertexStack.isEmpty()) {
                int currentVertex = vertexStack.peek();
                int i = 1;
                for (; i < visited.length; i++) {
                    if ((matrix[currentVertex][i] == 1) && (visited[i] == false)) {
                        vertexStack.push(i);
                        break;
                    }
                }
                if (i == matrix.length) vertexStack.pop();
                visited[currentVertex] = true;
            }
            componentNumber++;
            while (currentRoot < visited.length && visited[currentRoot] == true) {
                currentRoot++;
            }
        } while (currentRoot < visited.length);
        return componentNumber;
    }
}
