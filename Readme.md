
The purpose of this project is to process and analyze sales data from an Excel file to answer the following questions:

    Total Units Sold: How many units of each product were sold across all branches?  

    Total Daily Profit: What is the total daily profit generated worldwide from the sales of all products?

    Branch with the Lowest Profit: Which branch has the lowest profit, and what is the branch identifier?

__Project Structure__  
The project consists of three main classes:

    Main: Reads the Excel file and initiates the calculations.  

    SalesRecord: Represents a sales record for a branch.  

    SalesDataProcessor: Contains methods to perform the required calculations.


**Dependencies**  
The project uses the Apache POI library to read Excel files.
```xml
<dependencies>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>5.2.3</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>5.2.3</version>
        </dependency>
    </dependencies>

```

**Usage**  
1. Clone the Repository: Clone the repository to your local machine.
2. Add Dependency: Ensure that the Apache POI dependency is added to your pom.xml file.
3. Update File Path: Update the file path for the Excel file in the Main class.
4. Run the Program: Execute the Main class to run the program.  


  
**Output**  
After running the program, you should see the following output:
``` shell
Product A sold: 601355
Product B sold: 598974
Product C sold: 602230
Product D sold: 602822
Product E sold: 601668
Product F sold: 601993
Total profit in a day worldwide: 7219924.8
The lowest profited branch Seq: 058589
```