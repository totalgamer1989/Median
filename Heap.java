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
 * A heap data structure with a custom comparator and generic datatype
 * @author Bhaskar
 * @param <T>
 */
public class Heap <T> {
    public Heap(Comparator<T> comp){
        this.comp = comp;
    }
    
    private final Comparator<T> comp;
    private final ArrayList<T> data = new ArrayList();
    private int parent(int index){
        return ((index+1) / 2) - 1;
    }
    private int leftChild(int index){
        return (2*index) + 1;
    }
    private int rightChild(int index){
        return (2*index) + 2;
    }
    public void add(T t){
        int insertLocation = data.size();
        data.add(t);
        int curr = insertLocation;
        while(curr > 0){
            // sift up
            int parentLoc = parent(curr);
            T par = data.get(parentLoc);
            T c = data.get(curr);
            if (comp.compare(c, par) < 0){
                // if c is less than parent, it goes up
                data.set(curr, par);
                data.set(parentLoc, c);
                curr = parentLoc;
            } else {
                break;
            }
        }
    }
    public T viewTop(){
        return data.get(0);
    }
    public T deleteTop(){
        T retval = data.get(0);
        int curr = 0;
        int size = data.size();
        T replacement = data.get(size - 1);
        data.remove(size - 1);
        data.set(curr, replacement);
        // sift down
        
        while(curr < size - 1){
            int lcIdx = leftChild(curr);
            int rcIdx = rightChild(curr);
            T c = data.get(curr);
            if (rcIdx < size - 1){
                // if right child exists, left child must exist
                T left = data.get(lcIdx);
                T right = data.get(rcIdx);
                // pick the smaller one to replace the parent
                if (comp.compare(left, right) < 0){
                    // if left is smaller than right 
                    if (comp.compare(c, left) > 0){
                        // c is greater than child, it should go down
                        data.set(lcIdx, c);
                        data.set(curr, left);
                        curr = lcIdx;
                    } else {
                        // smallest child isn't smaller that the parent
                        break;
                    }
                } else {
                    // right <= left
                    if (comp.compare(c, right) > 0){
                        //c is greater than child, it should go down
                        data.set(rcIdx, c);
                        data.set(curr, right);
                        curr = rcIdx;
                    } else {
                        // smallest child isn't smaller that the biggest
                        break;
                    }
                }
            } else if (lcIdx == size - 2 ){
                //only left child exists we are at the bottom
                T left = data.get(lcIdx);
                if (comp.compare(c, left) > 0){
                    // c is greater than child, it should go down
                    data.set(lcIdx, c);
                    data.set(curr, left);
                    break;
                } else {
                    break;
                }
            } else {
                // the current value has no children
                break;
            }
        }
        return retval;
    }
    
    private boolean heapConsistent(){
        int size = data.size();
        for (int i = 0; i< size; i++){
            int l = leftChild(i);
            int r = rightChild(i);
            if (l < size){
                T curr = data.get(i);
                T temp = data.get(l);
                if (comp.compare(curr, temp) > 0){
                    return false;
                }
            }
            if (r < size){
                T curr = data.get(i);
                T temp = data.get(r);
                if (comp.compare(curr, temp) > 0){
                    return false;
                }
            }
        }
        return true;
    }
    public int size(){
        return data.size();
    }
    
//    Example of how to use the heap class

//    private static class Comp implements Comparator<Double>{
//
//        @Override
//        public int compare(Double o1, Double o2) {
//            return o1.compareTo(o2);
//        }
//        
//    }
//    public static void main(String[] args){
//        Heap<Double> h = new Heap(new Comp());
//        int[] input = new int[]{155,66,114,0,60,73,109,26,154,0,107,75,9,57,53,6,85,151,12,110,64,103,42,103,126,3,88,142,79,88,147,47,134,27,82,95,26,124,71,79,130,91,131,67,64,16,60,156,9,65,21,66,49,108,80,17,159,24,90,79,31,79,113,39,54,156,139,8,90,19,10,50,89,77,83,13,3,71,52,21,50,120,159,45,22,69,144,158,19,109,52,50,51,62,20,22,71,95,47,12,21,32,17,130,109,8,61,13,48,107,14,122,62,54,70,96,11,141,129,157,136,41,40,78,141,16,137,127,19,70,15,16,65,96,157,111,87,95,52,42,12,60,17,20,63,56,37,129,67,129,106,107,133,80,8,56,72,81,143,90,0};
//        for (int i : input){
//            h.add(i+ 0.0);
//        }
//    }
}

