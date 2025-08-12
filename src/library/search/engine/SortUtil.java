package library.search.engine;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortUtil {

    public static <T> void bubbleSort(List<T> list, Comparator<T> comparator) {
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    Collections.swap(list, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public static <T> void insertionSort(List<T> list, Comparator<T> comparator) {
        for (int i = 1; i < list.size(); i++) {
            T key = list.get(i);
            int j = i - 1;
            while (j >= 0 && comparator.compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    public static <T> void quickSort(List<T> list, Comparator<T> comparator, int low, int high) {
        if (low < high) {
            int pi = partition(list, comparator, low, high);
            quickSort(list, comparator, low, pi - 1);
            quickSort(list, comparator, pi + 1, high);
        }
    }

    private static <T> int partition(List<T> list, Comparator<T> comparator, int low, int high) {
        T pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                Collections.swap(list, ++i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }
}
