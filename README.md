# Insight Programming Problem Solutions

This project contains the solution for Word Count and Running Median 
problems as part of Insight application.   

# Dependencies
1. This project needs java 1.6 or above and maven to compile and run the code
2. This project also need apache spark 1.3 to run the word count solution
 - download spark-1.3.0-bin-hadoop2.4.tgz file from apache spark download page - https://spark.apache.org/downloads.html
 - copy that tar file inside the root directory of this project
 - untar that file, it should create a directory named spark-1.3.0-bin-hadoop2.4


# Steps to run the solutions

1. Clone the project and download on your machine
```
git clone git@github.com:accounts4vikas/insight.git
```

2. Compile the code using maven:

```
% mvn package
```

3. run.sh scrpt will first run Word Count and then running Median and put the results in output directory

```
sh run.sh 
```
