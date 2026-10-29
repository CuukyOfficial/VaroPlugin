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
    public synchronized E set(int index, E element) {
        return super.set(index, element);
    }

    @Override
    public synchronized boolean addAll(int index, Collection<? extends E> c) {
        return super.addAll(index, c);
    }

    @Override
    public synchronized boolean retainAll(Collection<?> c) {
        return super.retainAll(c);
    }

    @Override
    public synchronized int indexOf(Object o) {
        return super.indexOf(o);
    }

    @Override
    public synchronized int lastIndexOf(Object o) {
        return super.lastIndexOf(o);
    }

    @Override
    public synchronized Object[] toArray() {
        return super.toArray();
    }

    @Override
    public synchronized <T> T[] toArray(T[] a) {
        return super.toArray(a);
    }

    @Override
    public synchronized void sort(java.util.Comparator<? super E> c) {
        super.sort(c);
    }

    @Override
    public synchronized void replaceAll(java.util.function.UnaryOperator<E> operator) {
        super.replaceAll(operator);
    }

    @Override
    public synchronized boolean containsAll(Collection<?> c) {
        return super.containsAll(c);
    }

    @Override
    public synchronized void ensureCapacity(int minCapacity) {
        super.ensureCapacity(minCapacity);
    }

    @Override
    public synchronized void trimToSize() {
        super.trimToSize();
    }

    @Override
    public synchronized Object clone() {
        return super.clone();
    }

    @Override
    public synchronized boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public synchronized int hashCode() {
        return super.hashCode();
    }

    @Override
    public synchronized String toString() {
        return super.toString();
    }

    /**
     * @return A copy of the range, changes are not reflected in this list
     */
    @Override
    public synchronized java.util.List<E> subList(int fromIndex, int toIndex) {
        return new ArrayList<>(super.subList(fromIndex, toIndex));
    }

    @Override
    public java.util.ListIterator<E> listIterator() {
        return readOnly(this.snapshot().listIterator());
    }

    @Override
    public java.util.ListIterator<E> listIterator(int index) {
        return readOnly(this.snapshot().listIterator(index));
    }

    private static <T> java.util.ListIterator<T> readOnly(java.util.ListIterator<T> iterator) {
        return new java.util.ListIterator<T>() {
            @Override public boolean hasNext() { return iterator.hasNext(); }
            @Override public T next() { return iterator.next(); }
            @Override public boolean hasPrevious() { return iterator.hasPrevious(); }
            @Override public T previous() { return iterator.previous(); }
            @Override public int nextIndex() { return iterator.nextIndex(); }
            @Override public int previousIndex() { return iterator.previousIndex(); }
            @Override public void remove() { throw new UnsupportedOperationException(); }
            @Override public void set(T t) { throw new UnsupportedOperationException(); }
            @Override public void add(T t) { throw new UnsupportedOperationException(); }
        };
    }

    @Override
    public void forEach(java.util.function.Consumer<? super E> action) {
        this.snapshot().forEach(action);
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
