#!/usr/bin/env bash

# run command for calculating the running median
./spark-1.3.0-bin-hadoop2.4/bin/spark-submit  --files ./log4j.properties \
              --class org.insight.test.WordCountDemo \
              --master local[4] \
              --name "InsightWordCountSparkDemo" \
             ./target/insight-0.0.1-SNAPSHOT.jar \
             $@
             
# run command for calculating the running median
java -jar ./target/insight-0.0.1-SNAPSHOT.jar