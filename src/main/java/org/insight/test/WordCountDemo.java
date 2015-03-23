package org.insight.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class WordCountDemo implements Serializable {
	private transient SparkConf conf;

	private WordCountDemo(SparkConf conf) {
		this.conf = conf;
	}

	private void run() {
		JavaSparkContext sc = new JavaSparkContext(conf);
		compute(sc);
		sc.stop();
	}

	private void compute(JavaSparkContext sc) {
		File inputDir = new File("wc_input");
		String outputFilePath = "wc_output/wc_result.txt";

		// Load our input data.
		JavaRDD<String> input = sc.textFile("wc_input");

		// Split up into words.
		JavaRDD<String> words = input
				.flatMap(new FlatMapFunction<String, String>() {
					public Iterable<String> call(String x) {
						return Arrays.asList(x.replaceAll("[^a-zA-Z ]", "")
								.toLowerCase().split("\\s+"));
					}
				});

		// Remove empty spaces.
		JavaRDD<String> fileteredWords = words
				.filter(new Function<String, Boolean>() {
					public Boolean call(String x) {
						return x.length() != 0;
					}
				});

		// Transform into pairs, count and sort by words.
		JavaPairRDD<String, Integer> counts = fileteredWords
				.mapToPair(new PairFunction<String, String, Integer>() {
					public Tuple2<String, Integer> call(String x) {
						return new Tuple2(x, 1);
					}
				}).reduceByKey(new Function2<Integer, Integer, Integer>() {
					public Integer call(Integer x, Integer y) {
						return x + y;
					}
				}).sortByKey();

		// Save the word count back out to a text file.
		// counts.saveAsTextFile(outputFilePath);
		List<Tuple2<String, Integer>> results = counts.collect();

		Iterator<Tuple2<String, Integer>> itr = results.iterator();

		PrintWriter writer = null;
		try {
			writer = new PrintWriter("wc_output/wc_result.txt", "UTF-8");
			while (itr.hasNext()) {
				Tuple2<String, Integer> record = itr.next();
				writer.println(record._1 + " = " + record._2);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}

	}

	public static void main(String[] args) {
		SparkConf conf = new SparkConf();
		WordCountDemo app = new WordCountDemo(conf);
		app.run();
	}

}
