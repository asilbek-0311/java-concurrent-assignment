package assignment;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Main {
    //provide ncessary comments for the code
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        // Read sales records from Excel file
        List<SalesRecord> records = readSalesRecords("D:\\UNI\\ConProg\\salesdata\\src\\main\\resources\\sales_records.xlsx");
        
        // Create a SalesDataProcessor object and pass the records to its constructor 
        SalesDataProcessor processor = new SalesDataProcessor(records);

        // Calculate total units sold for each product
        int[] totalUnitsSold = processor.calculateTotalUnitsSold();
        System.out.println("Product A sold: " + totalUnitsSold[0]);
        System.out.println("Product B sold: " + totalUnitsSold[1]);
        System.out.println("Product C sold: " + totalUnitsSold[2]);
        System.out.println("Product D sold: " + totalUnitsSold[3]);
        System.out.println("Product E sold: " + totalUnitsSold[4]);
        System.out.println("Product F sold: " + totalUnitsSold[5]);

        // Calculate total daily profits worldwide
        double totalDailyProfits = processor.calculateTotalDailyProfits(totalUnitsSold);
        System.out.printf("Total profit in a day worldwide: %.1f\n", totalDailyProfits);

        // Find the branch with the lowest profit using threads and display its ID
        // String branchWithLowestProfit = processor.findBranchWithLowestProfit();
        // System.out.println("The lowest profited branch: " + branchWithLowestProfit);

        // Find the branch with the lowest profit sequentially and display its ID
        String branchWithLowestProfitSeq = processor.findBranchWithLowestProfitSequential();
        System.out.println("The lowest profited branch Seq: " + branchWithLowestProfitSeq);
    }

    //Method to read the sales records from the excel file
    private static List<SalesRecord> readSalesRecords(String fileName) throws IOException {
        //create a list to hold hte sales records
        List<SalesRecord> records = new ArrayList<>();

        //reading the excel file
        FileInputStream file = new FileInputStream(fileName);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);

        //iterating through the rows of the excel file
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next(); // Skip header row

        // loop through the each rows of the excel file to get the sales records
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            String branchID = row.getCell(0).getStringCellValue();
            int[] unitsSold = new int[6];
            for (int i = 0; i < 6; i++) {
                unitsSold[i] = (int) row.getCell(i + 1).getNumericCellValue();
            }
            //add the sales records to the list
            records.add(new SalesRecord(branchID, unitsSold));
        }
        //close the workbook and file after reading the records
        workbook.close();
        file.close();

        //return the list of sales records
        return records;
    }
}
