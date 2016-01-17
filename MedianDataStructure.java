/*
 * Copyright 2016 Bhaskar.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Bhaskar
 * @param <T>
 */

// This data structure gets the median of a all entries of a generic data type

public class MedianDataStructure <T extends Comparable<T>>{
    class NaturalComparator implements Comparator<T>{
        @Override
        public int compare(T o1, T o2) {
            return o1.compareTo(o2);
        }
    }
    class ReverseComparator implements Comparator<T>{
        @Override
        public int compare(T o1, T o2) {
            return -o1.compareTo(o2);
        }
    }
    private final Heap<T> maxOfSmaller;
    private final Heap<T> minOfGreater;
    private T oddMedian;
    private final Average<T> avg;

    public MedianDataStructure(Average<T> avg) {
        this.minOfGreater = new Heap(new NaturalComparator());
        this.oddMedian = null;
        this.maxOfSmaller = new Heap(new ReverseComparator());
        this.avg = avg;
    }
    
    public void addNum(T num){
        if (oddMedian == null){
            // current size is even
            if (maxOfSmaller.size() == 0 && minOfGreater.size() == 0){
                oddMedian = num;
            } else {
                T left = maxOfSmaller.viewTop();
                T right = minOfGreater.viewTop();
                if (num.compareTo(left) <= 0){
                    maxOfSmaller.add(num);
                    oddMedian = maxOfSmaller.deleteTop();
                } else if (num.compareTo(left) > 0 && num.compareTo(right) < 0){
                    oddMedian = num;
                } else {
                    // num.compareTo(right) >= 0
                    minOfGreater.add(num);
                    oddMedian = minOfGreater.deleteTop();
                }
            }
        } else {
            // current size is odd
            if (num.compareTo(oddMedian) <= 0){
                maxOfSmaller.add(num);
                minOfGreater.add(oddMedian);
                oddMedian = null;
            } else {
                maxOfSmaller.add(oddMedian);
                minOfGreater.add(num);
                oddMedian = null;
            }
        }
    }
    public T findMedian(){
        if (oddMedian != null){
            return oddMedian;
        } else {
            if (maxOfSmaller.size() == 0 && minOfGreater.size() == 0){
                return null;
            }
            return avg.average(maxOfSmaller.viewTop(), minOfGreater.viewTop());
        }
    }

// Example of how to use this class

//    private static class DoubleAvg implements Average<Double>{
//        @Override
//        public Double average(Double o1, Double o2) {
//            return (o1 + o2) / 2.0;
//        }
//    }
//    public static void main(String[] args){
//        MedianDataStructure<Double> data = new MedianDataStructure(new DoubleAvg());
//        int[] input = new int[]{155,66,114,0,60,73,109,26,154,0,107,75,9,57,53,6,85,151,12,110,64,103,42,103,126,3,88,142,79,88,147,47,134,27,82,95,26,124,71,79,130,91,131,67,64,16,60,156,9,65,21,66,49,108,80,17,159,24,90,79,31,79,113,39,54,156,139,8,90,19,10,50,89,77,83,13,3,71,52,21,50,120,159,45,22,69,144,158,19,109,52,50,51,62,20,22,71,95,47,12,21,32,17,130,109,8,61,13,48,107,14,122,62,54,70,96,11,141,129,157,136,41,40,78,141,16,137,127,19,70,15,16,65,96,157,111,87,95,52,42,12,60,17,20,63,56,37,129,67,129,106,107,133,80,8,56,72,81,143,90,0};
//        ArrayList<Double> output = new ArrayList();
//        for (int i : input){
//            data.addNum(i + 0.0);
//            Double result = data.findMedian();
//            output.add(result);
//        }
//        StringBuilder sb = new StringBuilder();
//        sb.append("[");
//        for(int i = 0 ; i < output.size(); i++){
//            sb.append(String.format("%5.05f", output.get(i)));
//            if (i < output.size() - 1){
//                sb.append(",");
//            }
//        }
//        sb.append("]");
//        int i = 0;
//    }
}
