Java console application that implements Counting Code Lines functionality described here 

    http://codekata.com/kata/kata13-counting-code-lines/

## Requirements

##### 1. It should be simple java application without Spring or any other frameworks.
##### 2. The application should have CLI which allows user to provide file/folder name
##### 3. The result should be printed to console
##### 4. The result should be printed in pretty format.

If single file is provided as input result should be in form of

    "<filename> : <number of lines>". 
E.g. 

    "App.java : 42"

If directory name is provider as an input result should include aggregated values as well, e.g.

    root : 331
        subfolder1 : 140
            Class1.java : 65
            Class2.java : 75
        subfolder2 : 161
            Class3.java : 102
            Class4.java : 59

##### 5. Unit tests are expected. You can use framework of your choice.