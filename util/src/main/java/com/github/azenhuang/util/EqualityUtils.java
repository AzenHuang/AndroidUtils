package com.github.azenhuang.util;

import java.util.Arrays;

/**
 * Created by huangyongzheng on 6/3/16.
 */
public class EqualityUtils {
    public static EqualStrategy<Object> POINTER_EQUAL_STRATEGY = new PointerEqualStrategy<>();
    public static EqualStrategy<Object> OBJECT_EQUAL_STRATEGY = new ObjectEqualStrategy<>();
    public static EqualStrategy<Object> DEFAULT_EQUAL_STRATEGY = OBJECT_EQUAL_STRATEGY;

    public interface EqualStrategy<E>{
        boolean isEqual(E actual, E expected);
    }

    public static class PointerEqualStrategy<E> implements EqualStrategy<E> {

        @Override
        public boolean isEqual(E actual, E expected) {
            return actual == expected;
        }
    }

    public static class ObjectEqualStrategy<E> implements EqualStrategy<E> {

        @Override
        public boolean isEqual(E actual, E expected) {
            return actual == expected || (actual == null ? expected == null : actual.equals(expected));
        }
    }

    public static <E> boolean equals(E actual, E expected){
        return actual == expected || (actual == null ? expected == null : actual.equals(expected));
    }

    public static <E> boolean pointerEquals(E actual, E expected){
        return actual == expected;
    }

    public static <E> boolean equals(E actual, E expected, EqualStrategy<E> equalStrategy){
        if (equalStrategy == null){
            throw new IllegalArgumentException("equalStrategy should not be null");
        }
        return equalStrategy.isEqual(actual, expected);
    }

    public static <E> boolean deepEquals(E actual, E expected){
        Class<?> cl1, cl2;

        if (actual == expected) {
            return true;
        }

        if (actual == null || expected == null) {
            return false;
        }

        cl1 = actual.getClass().getComponentType();
        cl2 = expected.getClass().getComponentType();

        if (cl1 != cl2) {
            return false;
        }

        if (cl1 == null) {
            return actual.equals(expected);
        }

        /*
         * compare as arrays
         */
        if (actual instanceof Object[]) {
            return deepEquals((Object[]) actual, (Object[]) expected);
        } else if (cl1 == int.class) {
            return Arrays.equals((int[]) actual, (int[]) expected);
        } else if (cl1 == char.class) {
            return Arrays.equals((char[]) actual, (char[]) expected);
        } else if (cl1 == boolean.class) {
            return Arrays.equals((boolean[]) actual, (boolean[]) expected);
        } else if (cl1 == byte.class) {
            return Arrays.equals((byte[]) actual, (byte[]) expected);
        } else if (cl1 == long.class) {
            return Arrays.equals((long[]) actual, (long[]) expected);
        } else if (cl1 == float.class) {
            return Arrays.equals((float[]) actual, (float[]) expected);
        } else if (cl1 == double.class) {
            return Arrays.equals((double[]) actual, (double[]) expected);
        } else {
            return Arrays.equals((short[]) actual, (short[]) expected);
        }
    }

}
