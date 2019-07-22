import csp.Driver;
import generic_algorithm.GA;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args)
    {
        final int CSP_CODE = 1;
        final int GA_CODE = 2;
        final int GA_MAX_RUN = 1;
        final int GA_MAX_EPOCH = 1000;
        final double GA_MUTATION_RATE = 0.001;

        Scanner reader = new Scanner(System.in);  // Reading from System.in

        // Algorithm chooses
        System.out.println("Choose algorithm: ");
        System.out.println("1 - CSP");
        System.out.println("2 - GA");

        int algorithm = reader.nextInt();

        // Algorithm chooses
        System.out.println("Enter board size: ");
        int boardSize = reader.nextInt();

        // once finished
        reader.close();

        long startTime = System.nanoTime();

        // Used memory
        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long afterUsedMem = 0;

        switch (algorithm)
        {
            case CSP_CODE:
                Driver d = new Driver(boardSize);
                d.placeMoves();
                String cspRes = d.getBoard().print();
                System.out.println(cspRes);
                writeToFile("CSP-N" + boardSize, cspRes);
                afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                break;
            case GA_CODE:
                GA ga = new GA(GA_MAX_RUN);
                ga.run(boardSize, GA_MUTATION_RATE, GA_MAX_EPOCH);
                afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                break;
        }

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        long actualMemUsed = afterUsedMem - beforeUsedMem;


//
//        long startTime = System.nanoTime();
//
//        // Used memory
//        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//        long afterUsedMem = 0;
//
//        GA ga = new GA(GA_MAX_RUN);
//        ga.run(11, GA_MUTATION_RATE, GA_MAX_EPOCH);
//        afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
//
//        long endTime = System.nanoTime();
//        long totalTime = endTime - startTime;
//        long actualMemUsed = afterUsedMem - beforeUsedMem;

        // Summary
        System.out.println("\n Actual memory used: " + actualMemUsed + " bytes");
        System.out.println(" Total runtime: " + totalTime + " nanoseconds" +
                " (" + TimeUnit.SECONDS.convert(totalTime, TimeUnit.NANOSECONDS) + " seconds)");
    }

    // timestamp
    //    System.currentTimeMillis()

    private static void writeToFile(String fileName, String input) {
        List<String> lines = Collections.singletonList(input);
        Path file = Paths.get(fileName + "_" + System.currentTimeMillis() + ".txt");
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//        // Get the Java runtime
//        Runtime runtime = Runtime.getRuntime();
//
//        // Run the garbage collector
//        runtime.gc();
//
//        // Calculate the used memory
//        long memory = runtime.totalMemory() - runtime.freeMemory();