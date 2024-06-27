package assignment;

import java.util.*;
import java.util.concurrent.*;

// Class to represent a sales record
class SalesRecord {
    String branchID;
    int[] unitsSold;

    SalesRecord(String branchID, int[] unitsSold) {
        this.branchID = branchID;
        this.unitsSold = unitsSold;
    }
}

// Class to process sales data
public class SalesDataProcessor {
    private List<SalesRecord> records; // List of sales records
    private double[] productProfits = {1.10, 1.50, 2.10, 1.60, 1.80, 3.90}; // Profits for each product

    public SalesDataProcessor(List<SalesRecord> records) {
        this.records = records;
    }

    // Method to calculate total units sold for each product using threads
    public int[] calculateTotalUnitsSold() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(6); // 6 threads for 6 products
        List<Future<int[]>> results = new ArrayList<>();

        // Submit tasks to calculate total units sold for each product
        for (int i = 0; i < 6; i++) {
            final int productIndex = i;
            results.add(executor.submit(() -> {
                int total = 0;
                for (SalesRecord record : records) {
                    total += record.unitsSold[productIndex];
                }
                return new int[]{productIndex, total};
            }));
        }

        // Get the results from the threads and store the total units sold for each product
        int[] totalUnitsSold = new int[6];
        for (Future<int[]> result : results) {
            int[] productResult = result.get();
            totalUnitsSold[productResult[0]] = productResult[1];
        }
    
        executor.shutdown(); // Shutdown the executor
        return totalUnitsSold; // Return the total units sold for each product
    }


    // Method to calculate total daily profits worldwide
    public double calculateTotalDailyProfits(int[] totalUnitsSold) {
        double totalProfit = 0; 
        for (int i = 0; i < totalUnitsSold.length; i++) {
            totalProfit += totalUnitsSold[i] * productProfits[i];
        }
        return totalProfit;
    }


    // Method to callcluate the branch wiht the lowest porfit
    public String findBranchWithLowestProfit() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(records.size()); // Thread pool with size equal to number of records
        ConcurrentHashMap<String, Double> branchProfits = new ConcurrentHashMap<>(); // Map to store branch profits

        List<Future<Void>> results = new ArrayList<>();

        // Submit tasks to calculate profit for each branch
        for (SalesRecord record : records) {
            results.add(executor.submit(() -> {
                double profit = 0;
                // Calculate profit for the branch
                for (int i = 0; i < record.unitsSold.length; i++) {
                    profit += record.unitsSold[i] * productProfits[i];
                }
            branchProfits.put(record.branchID, profit); // Store the profit in the map
            return null;
            }));
        }
        // Wait for all threads to finish
        for (Future<Void> result : results) {
            result.get();
        }
        executor.shutdown();
    
        // Check if all branches are present in the map
        if (branchProfits.size() != records.size()) {
            throw new IllegalStateException("Not all branch profits are calculated.");
        }
        
        String branchWithLowestProfit = null;
        double lowestProfit = Double.MAX_VALUE;

        // Find the branch with the lowest profit
        for (Map.Entry<String, Double> entry : branchProfits.entrySet()) {
            if (entry.getValue() < lowestProfit) {
            lowestProfit = entry.getValue();
            branchWithLowestProfit = entry.getKey();
            }
        }

        return branchWithLowestProfit;

    }

    // Method to calculate the lowest profit branch without using thread pools
    public String findBranchWithLowestProfitSequential() {
        String branchWithLowestProfit = null;
        double lowestProfit = Double.MAX_VALUE;

        //iterate through the sales records to find the branch with the lowest profit
        for (SalesRecord record : records) {
            double profit = 0;
            // calculate the profit for the branch
            for (int i = 0; i < record.unitsSold.length; i++) {
                profit += record.unitsSold[i] * productProfits[i];
            }
            // check if the profit is lower than the lowest profit
            if (profit < lowestProfit) {
                lowestProfit = profit;
                branchWithLowestProfit = record.branchID;
            }
        }

        return branchWithLowestProfit;
    }
}
