package com.github.azenhuang.util;


/**
 * {@code ArrayUtils} contains static methods which operate on arrays.Used as an extension of {@link java.util.Arrays}
 */
public class ArrayUtils {



    private ArrayUtils() {
        /* empty */
    }

    /**
     * is null or its length is 0
     *
     * @param <V>
     * @param sourceArray
     * @return
     */
    public static <V> boolean isEmpty(V[] sourceArray) {
        return (sourceArray == null || sourceArray.length == 0);
    }

    /**
     * is null or its length is 0
     *
     * @param sourceArray
     * @return
     */
    public static boolean isEmpty(int[] sourceArray) {
        return (sourceArray == null || sourceArray.length == 0);
    }

    /**
     * is null or its length is 0
     *
     * @param sourceArray
     * @return
     */
    public static boolean isEmpty(long[] sourceArray) {
        return (sourceArray == null || sourceArray.length == 0);
    }

    /**
     * is null or its length is 0
     *
     * @param sourceArray
     * @return
     */
    public static boolean isEmpty(float[] sourceArray) {
        return (sourceArray == null || sourceArray.length == 0);
    }

    /**
     * is null or its length is 0
     *
     * @param sourceArray
     * @return
     */
    public static boolean isEmpty(double[] sourceArray) {
        return (sourceArray == null || sourceArray.length == 0);
    }

    /**
     * is null or its length is 0
     *
     * @param sourceArray
     * @return
     */
    public static boolean isEmpty(boolean[] sourceArray) {
        return (sourceArray == null || sourceArray.length == 0);
    }

    /**
     * This exception is thrown by the ArrayUtils APIs when targetValue
     * can not be found in Array.
     */
    @Deprecated
    public static class NotFoundException extends RuntimeException {
        public NotFoundException() {
        }

        public NotFoundException(String message) {
            super(message);
        }
    }

    /**
     * Get the first index of the element which equals to expectedElement(actualElement.equals(expectedElement)) ,
     * or -1 if no such element.
     * The search begins at the index 0 and moves towards the end of sourceArray.
     *
     * @param sourceArray
     * @param expectedElement
     * @param <V>
     * @return the index of the element which absolutely equals to expectedElement , or -1 if no such element.
     */
    public static <V> int getIndex(V[] sourceArray, V expectedElement) {
        return getIndex(sourceArray, expectedElement, new EqualityUtils.ObjectEqualStrategy<V>());
    }

    /**
     * Get the first index of the element which equals to expectedElement(use equalStrategy) ,
     * or -1 if no such element.
     * The search begins at the index 0 and moves towards the end of sourceArray.
     *
     * @param sourceArray
     * @param expectedElement
     * @param equalStrategy
     * @param <V>
     * @return the index of the element which absolutely equals to expectedElement , or -1 if no such element.
     */
    public static <V> int getIndex(V[] sourceArray, V expectedElement, EqualityUtils.EqualStrategy<V> equalStrategy) {
        return getIndex(sourceArray, expectedElement, equalStrategy, 0);
    }

    /**
     * Get the first index of the element which equals to expectedElement , or -1 if no such element.
     * The search begins at the start index and moves towards the end of sourceArray.
     *
     * @param sourceArray
     * @param expectedElement
     * @param start       the start index, inclusive
     * @param <V>
     * @return the index of the element which equals to expectedElement , or -1 if no such element.
     */
    public static <V> int getIndex(V[] sourceArray, V expectedElement, EqualityUtils.EqualStrategy<V> equalStrategy, int start) {
        return getIndex(sourceArray, expectedElement, equalStrategy, start, sourceArray.length);
    }

    /**
     * Get the first index of the element which equals to expectedElement in sourceArray.
     * The search begins at the start index and moves towards the end-1 index of sourceArray.
     *
     * @param sourceArray
     * @param expectedElement
     * @param start       the start index, inclusive
     * @param end         the end index, exclusive
     * @param <V>
     * @return the index of the element which equals to expectedElement , or -1 if no such element.
     */
    public static <V> int getIndex(V[] sourceArray, V expectedElement, EqualityUtils.EqualStrategy<V> equalStrategy, int start, int end) {
        if (sourceArray == null) {
            return -1;
//            throw new NullPointerException(sourceArray+" is null");
        }
        if (start > end) {
            return -1;
//            throw new IllegalArgumentException("the start index is bigger than end index");
        }
        int sourceLength = sourceArray.length;
        if (start < 0 || start >= sourceLength) {
            return -1;
//            throw new ArrayIndexOutOfBoundsException("the start index is out of bound");
        }
        int actualEnd = Math.min(end, sourceLength);
        for (int i = start; i < actualEnd; i++) {
            if (equalStrategy.isEqual(expectedElement, sourceArray[i])) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Get the first index of the element which equals to expectedElement , or -1 if no such element.
     * The search begins at the index 0 and moves towards the end of sourceArray.
     *
     * @param sourceArray
     * @param expectedElement
     * @return the index of the element which equals to expectedElement , or -1 if no such element.
     */
    public static int getIndex(int[] sourceArray, int expectedElement) {
        return getIndex(sourceArray, expectedElement, 0);
    }

    /**
     * Get the first index of the element which equals to expectedElement , or -1 if no such element.
     * The search begins at the start index and moves towards the end of sourceArray.
     *
     * @param sourceArray
     * @param expectedElement
     * @param start       the start index, inclusive
     * @return the index of the element which equals to expectedElement , or -1 if no such element.
     */
    public static int getIndex(int[] sourceArray, int expectedElement, int start) {
        return getIndex(sourceArray, expectedElement, start, sourceArray.length);
    }

    /**
     * Get the first index of the element which equals to expectedElement in sourceArray.
     * The search begins at the start index and moves towards the end-1 index of sourceArray.
     *
     * @param sourceArray
     * @param expectedElement
     * @param start       the start index, inclusive
     * @param end         the end index, exclusive
     * @return the index of the element which equals to expectedElement , or -1 if no such element.
     */
    public static int getIndex(int[] sourceArray, int expectedElement, int start, int end) {
        if (sourceArray == null) {
            return -1;
//            throw new NullPointerException(sourceArray+" is null");
        }
        if (start > end) {
            return -1;
//            throw new IllegalArgumentException("the start index is bigger than end index");
        }
        int sourceLength = sourceArray.length;
        if (start < 0 || start >= sourceLength) {
            return -1;
//            throw new ArrayIndexOutOfBoundsException("the start index is out of bound");
        }
        int actualEnd = Math.min(end, sourceLength);
        for (int i = start; i < actualEnd; i++) {
            if (expectedElement == sourceArray[i]) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Get the first index of the element which equals to expectedElement , or -1 if no such element.
     * The search begins at the index 0 and moves towards the end of sourceArray.
     *
     * @param sourceArray
     * @param expectedElement
     * @return the index of the element which equals to expectedElement , or -1 if no such element.
     */
    public static int getIndex(long[] sourceArray, int expectedElement) {
        return getIndex(sourceArray, expectedElement, 0);
    }

    /**
     * Get the first index of the element which equals to expectedElement , or -1 if no such element.
     * The search begins at the start index and moves towards the end of sourceArray.
     *
     * @param sourceArray
     * @param expectedElement
     * @param start       the start index, inclusive
     * @return the index of the element which equals to expectedElement , or -1 if no such element.
     */
    public static int getIndex(long[] sourceArray, int expectedElement, int start) {
        return getIndex(sourceArray, expectedElement, start, sourceArray.length);
    }

    /**
     * Get the first index of the element which equals to expectedElement in sourceArray.
     * The search begins at the start index and moves towards the end-1 index of sourceArray.
     *
     * @param sourceArray
     * @param expectedElement
     * @param start       the start index, inclusive
     * @param end         the end index, exclusive
     * @return the index of the element which equals to expectedElement , or -1 if no such element.
     */
    public static int getIndex(long[] sourceArray, int expectedElement, int start, int end) {
        if (sourceArray == null) {
            return -1;
//            throw new NullPointerException(sourceArray+" is null");
        }
        if (start > end) {
            return -1;
//            throw new IllegalArgumentException("the start index is bigger than end index");
        }
        int sourceLength = sourceArray.length;
        if (start < 0 || start >= sourceLength) {
            return -1;
//            throw new ArrayIndexOutOfBoundsException("the start index is out of bound");
        }
        int actualEnd = Math.min(end, sourceLength);
        for (int i = start; i < actualEnd; i++) {
            if (expectedElement == sourceArray[i]) {
                return i;
            }
        }

        return -1;
    }




}
