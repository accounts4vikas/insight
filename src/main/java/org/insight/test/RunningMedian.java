package org.insight.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class RunningMedian {

	public Queue<Integer> minHeap;
	public Queue<Integer> maxHeap;
	public int numOfElements;

	public RunningMedian() {
		minHeap = new PriorityQueue<Integer>();
		maxHeap = new PriorityQueue<Integer>(10, new MaxHeapComparator());
		numOfElements = 0;
	}

	public void addLineWordCount(Integer num) {
		maxHeap.add(num);
		if (numOfElements % 2 == 0) {
			if (minHeap.isEmpty()) {
				numOfElements++;
				return;
			} else if (maxHeap.peek() > minHeap.peek()) {
				Integer maxHeapRoot = maxHeap.poll();
				Integer minHeapRoot = minHeap.poll();
				maxHeap.add(minHeapRoot);
				minHeap.add(maxHeapRoot);
			}
		} else {
			minHeap.add(maxHeap.poll());
		}
		numOfElements++;
	}

	public Double getMedian() {
		if (numOfElements % 2 != 0)
			return new Double(maxHeap.peek());
		else
			return (maxHeap.peek() + minHeap.peek()) / 2.0;
	}

	private class MaxHeapComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer o1, Integer o2) {
			return o2 - o1;
		}
	}

}