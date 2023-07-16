# XYZ BANK ATM Program
## Overview
  This is a simple ATM software in JAVA. The data of users are stored in sql, which allows users to check or update their account infomation. Also, user can do three tansactions now, which are Deposit, Withdraw, CheckBalance.
  
## Key Topic
  * How to run the program.
    * Make sure you have following tools updated before running the program.
      * The latest JAVA.(JAVA16)
      * Gradle higher than 7.0.0
    
    * By using 'gradle run' in the terminal. 
  
  * How to do testing.
    * Make testcases by using Junit. The test file should follow the rules of Junit.
    
    * Run testcases
      * Every time running 'gradle build' will make testcases run automatically.
        or
      * Type 'gradle test' in the terminal to run the testcases manually.
      
     * You can find both HTML and csv file of the jacoco code coverage report.
        * PATH: */project-repo/build/reports/jacoco/test/html/index.html (HTML file)
        * PATH  */project-repo/build/reports/jacoco/test/jacocoTestReport.csv (CSV file)
  
  * How to contribute on the codebase.
    * Only write the code in non_master branch.
    
    * Merge master with the branch of devlopment after all test cases.
  
  * How to collaborate.
    * Jenkins already set up.
    
    * All the pushes that are going to happen on master will be validated. 
