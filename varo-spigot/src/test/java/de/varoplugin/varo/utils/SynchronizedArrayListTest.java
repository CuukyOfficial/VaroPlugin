package de.varoplugin.varo.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

class SynchronizedArrayListTest {

    @Test
    void modifyingWhileIteratingDoesNotThrow() {
        List<Integer> list = new SynchronizedArrayList<>();
        list.addAll(Arrays.asList(1, 2, 3));

        for (Integer i : list)
            list.add(i + 10);
        assertEquals(6, list.size());

        list.forEach(i -> list.remove(i));
        assertEquals(0, list.size());
    }

    @Test
    void iteratorsAreReadOnly() {
        List<Integer> list = new SynchronizedArrayList<>();
        list.add(1);

        assertThrows(UnsupportedOperationException.class, () -> {
            java.util.Iterator<Integer> it = list.iterator();
            it.next();
            it.remove();
        });
        ListIterator<Integer> it = list.listIterator();
        it.next();
        assertThrows(UnsupportedOperationException.class, () -> it.set(2));
    }

    @Test
    void subListIsACopy() {
        List<Integer> list = new SynchronizedArrayList<>();
        list.addAll(Arrays.asList(1, 2, 3));

        List<Integer> sub = list.subList(0, 2);
        list.clear();

        assertEquals(new ArrayList<>(Arrays.asList(1, 2)), sub);
    }

    @Test
    void concurrentModification() throws InterruptedException {
        List<Integer> list = new SynchronizedArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (int t = 0; t < 4; t++)
            executor.execute(() -> {
                for (int i = 0; i < 5000; i++) {
                    list.add(i);
                    list.forEach(x -> { });
                    list.removeIf(x -> x % 2 == 0);
                }
            });
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);

        assertEquals(true, list.stream().allMatch(x -> x % 2 == 1));
    }
}
