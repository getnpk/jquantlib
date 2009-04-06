/*
 Copyright (C) 2008 Praneet Tiwari

 This source code is release under the BSD License.

 This file is part of JQuantLib, a free-software/open-source library
 for financial quantitative analysts and developers - http://jquantlib.org/

 JQuantLib is free software: you can redistribute it and/or modify it
 under the terms of the JQuantLib license.  You should have received a
 copy of the license along with this program; if not, please email
 <jquant-devel@lists.sourceforge.net>. The license is also available online at
 <http://www.jquantlib.org/index.php/LICENSE.TXT>.

 This program is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE.  See the license for more details.

 JQuantLib is based on QuantLib. http://quantlib.org/
 When applicable, the original copyright notice follows this notice.
 */
package org.jquantlib.math.statistics;

//~--- JDK imports ------------------------------------------------------------
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Praneet Tiwari
 *  Sorts an ArrayList of Value weight pairs
 */
public class PairSortingAlgorithms<F extends Number, S extends Number> {
    
    public PairSortingAlgorithms(){
        if (System.getProperty("EXPERIMENTAL") == null) {
            throw new UnsupportedOperationException("Work in progress");
        }
    }

    public ArrayList<Pair<Number, Number>> insertionSort(ArrayList<Pair<F, S>> unsortedArrayList) {
        Number t, u;    // =  Number.;
        int size = unsortedArrayList.size();
        ArrayList<Pair<Number, Number>> sortedArrayList = new ArrayList<Pair<Number, Number>>(size);
        Iterator<Pair<F, S>> lIterator = unsortedArrayList.iterator();
        Pair<F, S> fs = new Pair<F, S>();

        // there are no arrays of generics, just yet :-<
        Number[] f = new Number[size];
        Number[] g = new Number[size];
        int i = 0;

        while (lIterator.hasNext()) {
            fs = lIterator.next();
            f[i] = fs.getFirst();
            g[i++] = fs.getSecond();
        }

        for (int j = 1; j < size; j++) {
            int index = j;

            t = f[j];
            u = g[j];

            while ((index > 0) && (f[index - 1].doubleValue() > t.doubleValue())) {
                f[index] = f[index - 1];
                g[index] = g[index - 1];
                index--;
            }

            // now place the selected element
            f[index] = t;
            g[index] = u;
        }

        // convert the array into array list of Pair
        for (int k = 0; k < size; k++) {
            Pair<Number, Number> tmp = new Pair<Number, Number>(f[k], g[k]);

            // System.out.println("This is the object --->" + tmp.getFirst()+tmp.getSecond());
            sortedArrayList.add(tmp);
        }

        return sortedArrayList;
    }

    // Quicksort
    public ArrayList<Pair<Number, Number>> quickSort(ArrayList<Pair<F, S>> unsortedArrayList) {
        Number t, u;    // =  Number.;
        int size = unsortedArrayList.size();
        ArrayList<Pair<Number, Number>> sortedArrayList = new ArrayList<Pair<Number, Number>>(size);
        Iterator<Pair<F, S>> lIterator = unsortedArrayList.iterator();
        Pair<F, S> fs = new Pair<F, S>();

        // there are no arrays of generics, just yet :-<
        Number[] f = new Number[size];
        Number[] g = new Number[size];
        int i = 0;

        while (lIterator.hasNext()) {
            fs = lIterator.next();
            f[i] = fs.getFirst();
            g[i++] = fs.getSecond();
        }

        quicksort(f, g, 0, f.length - 1);

        for (int k = 0; k < size; k++) {
            Pair<Number, Number> tmp = new Pair<Number, Number>(f[k], g[k]);

            // System.out.println("This is the object --->" + tmp.getFirst()+tmp.getSecond());
            sortedArrayList.add(tmp);
        }

        return sortedArrayList;
    }

    private void quicksort(Number[] values, Number[] weights, int left0, int right0) {
        int left, right;
        Number pivot, temp;
        Number pivotW, tempW;

        left = left0;
        right = right0 + 1;
        pivot = values[left0];
        left++;
        right--;

        do {
            while ((left <= right0) && (values[left].doubleValue() < (pivot).doubleValue())) {
                left++;
            }

            while (values[right].doubleValue() > (pivot).doubleValue()) {
                right--;
            }

            if (left < right) {
                temp = values[left];
                tempW = weights[left];
                values[left] = values[right];
                weights[left] = weights[right];
                values[right] = temp;
                weights[right] = tempW;
            }
        } while (left <= right);

        temp = values[left0];
        tempW = weights[left0];
        values[left0] = values[right];
        weights[left0] = weights[right];
        values[right] = temp;
        weights[right] = tempW;

        if (left0 < right) {
            quicksort(values, weights, left0, right);
        }

        if (left < right0) {
            quicksort(values, weights, left, right0);
        }
    }

    // selection sort now
    public ArrayList<Pair<Number, Number>> selectionSort(ArrayList<Pair<F, S>> unsortedArrayList) {
        Number t, u;    // =  Number.;
        int size = unsortedArrayList.size();
        ArrayList<Pair<Number, Number>> sortedArrayList = new ArrayList<Pair<Number, Number>>(size);
        Iterator<Pair<F, S>> lIterator = unsortedArrayList.iterator();
        Pair<F, S> fs = new Pair<F, S>();

        // there are no arrays of generics, just yet :-<
        Number[] f = new Number[size];
        Number[] g = new Number[size];
        int j = 0;

        while (lIterator.hasNext()) {
            fs = lIterator.next();
            f[j] = fs.getFirst();
            g[j++] = fs.getSecond();
        }

        int indexSmallest;

        for (int i = 0; i < f.length - 1; i++) {
            indexSmallest = i;

            for (int index = i + 1; index < f.length; index++) {
                if (f[index].doubleValue() < f[indexSmallest].doubleValue()) {
                    indexSmallest = index;
                }
            }

            swap(f, g, i, indexSmallest);
        }

        for (int k = 0; k < size; k++) {
            Pair<Number, Number> tmp = new Pair<Number, Number>(f[k], g[k]);

            // System.out.println("This is the object --->" + tmp.getFirst()+tmp.getSecond());
            sortedArrayList.add(tmp);
        }

        return sortedArrayList;
    }

    private void swap(Number[] f, Number[] g, int i1, int i2) {    // swap items in array
        Number tmp = f[i1];

        f[i1] = f[i2];
        f[i2] = tmp;

        Number tmpW = g[i1];

        g[i1] = g[i2];
        g[i2] = tmpW;
    }

    // heap sort now
    public ArrayList<Pair<Number, Number>> heapSort(ArrayList<Pair<F, S>> unsortedArrayList) {
        Number t, u;    // =  Number.;
        int size = unsortedArrayList.size();
        ArrayList<Pair<Number, Number>> sortedArrayList = new ArrayList<Pair<Number, Number>>(size);
        Iterator<Pair<F, S>> lIterator = unsortedArrayList.iterator();
        Pair<F, S> fs = new Pair<F, S>();

        // there are no arrays of generics, just yet :-<
        Number[] f = new Number[size];
        Number[] g = new Number[size];
        int i = 0;

        while (lIterator.hasNext()) {
            fs = lIterator.next();
            f[i] = fs.getFirst();
            g[i++] = fs.getSecond();
        }

        heapify(f, g);

        for (int end = f.length - 1; end > 0; end--) {
            swap(f, g, 0, end);
            heapSortWork(f, g, 0, end - 1);
        }

        for (int k = 0; k < size; k++) {
            Pair<Number, Number> tmp = new Pair<Number, Number>(f[k], g[k]);

            // System.out.println("This is the object --->" + tmp.getFirst()+tmp.getSecond());
            sortedArrayList.add(tmp);
        }

        return sortedArrayList;
    }

    private void heapify(Number[] f, Number[] g) {
        for (int start = f.length / 2 - 1; start >= 0; start--) {
            heapSortWork(f, g, start, f.length - 1);
        }
    }

    private void heapSortWork(Number[] f, Number[] g, int root, int end) {
        while (root * 2 + 1 <= end) {
            int child = root * 2 + 1;

            if ((child < end) && (f[child].doubleValue() < (f[child + 1].doubleValue()))) {
                child += 1;
            }

            if (f[root].doubleValue() < (f[child]).doubleValue()) {
                swap(f, g, root, child);
                root = child;
            } else {
                break;
            }
        }
    }

    public static void main(String args[]) {
        if (System.getProperty("EXPERIMENTAL") == null) {
            throw new UnsupportedOperationException("Work in progress");
        }
        System.out.println("************************************ ");

        ArrayList<Pair<Number, Number>> al = new ArrayList<Pair<Number, Number>>();
        ArrayList<Pair<Number, Number>> resArrayList = new ArrayList<Pair<Number, Number>>();

//      Math.random();
        for (int i = 0; i < 14; i++) {

            // Pair<Number, Number> p = new Pair<Number, Number>(100-i, i*10);
            Pair<Number, Number> p = new Pair<Number, Number>(Math.random(), Math.random());

            al.add(p);
        }

        for (int j = 0; j < al.size(); j++) {
            System.out.println("Now Showing values before sorting at index " + j + " " + al.get(j).getFirst() + " " + al.get(j).getSecond());
        }

        System.out.println("************************************ ");
        resArrayList = new PairSortingAlgorithms().insertionSort(al);

        for (int j = 0; j < resArrayList.size(); j++) {
            System.out.println("Now Showing values after insertion sort at index " + j + " " + resArrayList.get(j).getFirst() + " " + resArrayList.get(j).getSecond());
        }

        System.out.println("************************************ ");

        // check quicksort now
        resArrayList = new PairSortingAlgorithms().quickSort(al);

        for (int j = 0; j < resArrayList.size(); j++) {
            System.out.println("Now Showing " + "values after quick sort at index " + j + " " + resArrayList.get(j).getFirst() + " " + resArrayList.get(j).getSecond());
        }

        System.out.println("************************************ ");

        // check selection sort now
        resArrayList = new PairSortingAlgorithms().selectionSort(al);

        for (int j = 0; j < resArrayList.size(); j++) {
            System.out.println("Now Showing values after selection sort at index " + j + " " + resArrayList.get(j).getFirst() + " " + resArrayList.get(j).getSecond());
        }

        System.out.println("************************************ ");

        // check heap sort now
        resArrayList = new PairSortingAlgorithms().heapSort(al);

        for (int j = 0; j < resArrayList.size(); j++) {
            System.out.println("Now Showing values after heap sort at index " + j + " " + resArrayList.get(j).getFirst() + " " + resArrayList.get(j).getSecond());
        }
    }
}    // class
 
