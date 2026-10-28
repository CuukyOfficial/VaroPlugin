package de.varoplugin.varo.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;

/**
 * An {@link ArrayList} that can safely be modified and read by multiple threads (e.g. the different region threads on Folia).
 * Modifications are synchronized and iterating (including streams) operates on a snapshot, so a concurrent modification never
 * causes a {@link java.util.ConcurrentModificationException}. Consequently {@link Iterator#remove()} is not supported.
 */
public class SynchronizedArrayList<E> extends ArrayList<E> {

    private static final long serialVersionUID = 1L;

    private synchronized ArrayList<E> snapshot() {
        return new ArrayList<>(this);
    }

    @Override
    public synchronized boolean add(E e) {
        return super.add(e);
    }

    @Override
    public synchronized void add(int index, E element) {
        super.add(index, element);
    }

    @Override
    public synchronized boolean addAll(Collection<? extends E> c) {
        return super.addAll(c);
    }

    @Override
    public synchronized E remove(int index) {
        return super.remove(index);
    }

    @Override
    public synchronized boolean remove(Object o) {
        return super.remove(o);
    }

    @Override
    public synchronized boolean removeAll(Collection<?> c) {
        return super.removeAll(c);
    }

    @Override
    public synchronized boolean removeIf(java.util.function.Predicate<? super E> filter) {
        return super.removeIf(filter);
    }

    @Override
    public synchronized void clear() {
        super.clear();
    }

    @Override
    public synchronized E get(int index) {
        return super.get(index);
    }

    @Override
    public synchronized int size() {
        return super.size();
    }

    @Override
    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public synchronized boolean contains(Object o) {
        return super.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return readOnly(this.snapshot().iterator());
    }

    @Override
    public Spliterator<E> spliterator() {
        return this.snapshot().spliterator();
    }

    private static <T> Iterator<T> readOnly(Iterator<T> iterator) {
        return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                return iterator.next();
            }
        };
    }
}
