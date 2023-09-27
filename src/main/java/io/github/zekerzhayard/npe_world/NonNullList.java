package io.github.zekerzhayard.npe_world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import com.google.common.collect.ForwardingList;
import com.google.common.collect.ForwardingListIterator;

public class NonNullList<E> extends ForwardingList<E> {
    public static <T> List<T> create(List<T> delegate) {
        return new NonNullList<>(delegate);
    }

    private final List<E> delegate;

    public NonNullList(List<E> delegate) {
        this.delegate = delegate;
    }

    @Override
    protected List<E> delegate() {
        return this.delegate;
    }

    @Override
    public boolean add(E element) {
        if (element != null) {
            return super.add(element);
        }
        return false;
    }

    @Override
    public void add(int index, E element) {
        if (element != null) {
            super.add(index, element);
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> elements) {
        List<? extends E> nonNullElements = new ArrayList<>(elements);
        nonNullElements.removeAll(Collections.singleton(null));
        return super.addAll(nonNullElements);
    }

    @Override
    public E set(int index, E element) {
        if (element != null) {
            return super.set(index, element);
        }
        return element;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> elements) {
        List<? extends E> nonNullElements = new ArrayList<>(elements);
        nonNullElements.removeAll(Collections.singleton(null));
        return super.addAll(index, nonNullElements);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new NonNullListItr<>(super.listIterator());
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new NonNullListItr<>(super.listIterator(index));
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new NonNullList<>(super.subList(fromIndex, toIndex));
    }

    public static class NonNullListItr<E> extends ForwardingListIterator<E> {
        private final ListIterator<E> delegate;

        public NonNullListItr(ListIterator<E> delegate) {
            this.delegate = delegate;
        }

        @Override
        protected ListIterator<E> delegate() {
            return this.delegate;
        }

        @Override
        public void add(E element) {
            if (element != null) {
                super.add(element);
            }
        }

        @Override
        public void set(E element) {
            if (element != null) {
                super.set(element);
            }
        }
    }
}
