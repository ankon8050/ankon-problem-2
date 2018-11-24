1. Clone the public repository

2. Import in your IDE as a MAVEN project (it was developed in IntelliJ Idea)

3. Open "Credentials.rtf" file and add the following:
        i. GitHub username + carriage return
        ii. GitHub password + carriage return
        iii. Name of input file, select from the following:
                a. InputLocal.rtf : A couple of files in the project
                b. Input_2.rtf: 2 public GitLab .java file URLs and 2 GitHub .java file URLs
                c. Input_10.rtf: 10 public GitHub .java file URLs
                d. Input_100.rtf: 40 public GitHub .java file URLs
        iv. Name of output file, must have a .csv extension, e.g. "Output.csv"
        
4. Build the project

5. Run as Java Application

6. The console will show the sequential stage of compilation and run time

7. Once you get "Done" in the console, you will find the output file in project directory with the name provided

8. A few output files are provided with the repo, as follows:
        i.  test_2.csv: Contains output of Input_2.rtf file
        ii. test_10.csv: Contains output of Input_10.rtf file
        iii. test_100.csv: Contains output of Input_100.rtf file
        iv. testLocal.csv: Contains output of InputLocal.rtf file
        
9. In case you need to check any other java file apart from the ones listed, navigate to the raw java code page of the 
desired file, copy the link and add it in any of the existing input files or create a new one. At least 2 URLs need to
be present to provide an actual analytics.