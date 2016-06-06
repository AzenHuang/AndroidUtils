package com.github.azenhuang.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * CollectionUtils
 * 
 */
public class CollectionUtils {

    /** default join separator **/
    public static final CharSequence DEFAULT_JOIN_SEPARATOR = ",";

    private CollectionUtils() {
        throw new AssertionError();
    }


    /**
     * join collection to string, separator is {@link #DEFAULT_JOIN_SEPARATOR}
     * 
     * <pre>
     * join(null)      =   "";
     * join({})        =   "";
     * join({a,b})     =   "a,b";
     * </pre>
     * 
     * @param collection
     * @return join collection to string, separator is {@link #DEFAULT_JOIN_SEPARATOR}. if collection is empty, return
     *         ""
     */
    public static String join(Iterable collection) {
        return collection == null ? "" : TextUtils.join(DEFAULT_JOIN_SEPARATOR, collection);
    }



    /**
     * get size of list
     *
     * <pre>
     * getSize(null)   =   -1;
     * getSize({})     =   0;
     * getSize({1})    =   1;
     * </pre>
     *
     * @param <V>
     * @param collection
     * @return -1 if list is null ,0 if empty, else return {@link Collection#size()}.
     */
    public static <V> int getSize(Collection<V> collection) {
        return collection == null ? -1 : collection.size();
    }

    /**
     * is null or its size is 0
     *
     * <pre>
     * isEmpty(null)   =   true;
     * isEmpty({})     =   true;
     * isEmpty({1})    =   false;
     * </pre>
     *
     * @param <V>
     * @param collection
     * @return if list is null or its size is 0, return true, else return false.
     */
    public static <V> boolean isEmpty(Collection<V> collection) {
        return (collection == null || collection.size() == 0);
    }

    /**
     * compare two list
     *
     * <pre>
     * isEqual(null, null) = true;
     * isEqual(new ArrayList&lt;String&gt;(), null) = false;
     * isEqual(null, new ArrayList&lt;String&gt;()) = false;
     * isEqual(new ArrayList&lt;String&gt;(), new ArrayList&lt;String&gt;()) = true;
     * </pre>
     *
     * @param <V>
     * @param actual
     * @param expected
     * @return
     */
    public static <V> boolean isEqual(Collection<V> actual, Collection<V> expected) {
        if (actual == null) {
            return expected == null;
        }
        if (expected == null) {
            return false;
        }
        if (actual.size() != expected.size()) {
            return false;
        }

        Iterator<V> actualIterator = actual.iterator();
        Iterator<V> expectedIterator = expected.iterator();
        while (actualIterator.hasNext()){
            if(!EqualityUtils.equals(actualIterator.next(), expectedIterator.next())){
                return false;
            }
        }
        return true;
    }

    /**
     *  @deprecated Please use {@link TextUtils}.
     * join list to string, separator is ","
     *
     * <pre>
     * join(null)      =   "";
     * join({})        =   "";
     * join({a,b})     =   "a,b";
     * </pre>
     *
     * @param list
     * @return join list to string, separator is ",". if list is empty, return ""
     */
    @Deprecated
    public static String join(List<String> list) {
        return join(list, DEFAULT_JOIN_SEPARATOR);
    }

    /**
     * @deprecated Please use {@link TextUtils}.
     * join list to string
     *
     * <pre>
     * join(null, '#')     =   "";
     * join({}, '#')       =   "";
     * join({a,b,c}, ' ')  =   "abc";
     * join({a,b,c}, '#')  =   "a#b#c";
     * </pre>
     *
     * @param list
     * @param separator
     * @return join list to string. if list is empty, return ""
     */
    @Deprecated
    public static String join(List<String> list, char separator) {
        return join(list, new String(new char[] {separator}));
    }

    /**
     * @deprecated Please use {@link TextUtils}.
     * join list to string. if separator is null, use {@link #DEFAULT_JOIN_SEPARATOR}
     *
     * <pre>
     * join(null, "#")     =   "";
     * join({}, "#$")      =   "";
     * join({a,b,c}, null) =   "a,b,c";
     * join({a,b,c}, "")   =   "abc";
     * join({a,b,c}, "#")  =   "a#b#c";
     * join({a,b,c}, "#$") =   "a#$b#$c";
     * </pre>
     *
     * @param list
     * @param separator
     * @return join list to string with separator. if list is empty, return ""
     */
    @Deprecated
    public static String join(List<String> list, CharSequence separator) {
        return list == null ? "" : TextUtils.join(separator, list);
    }

    /**
     * @deprecated Please use {@link Collections#addAll(Collection, Object[])}
     * add distinct entry to list
     *
     * @param <V>
     * @param destination
     * @param source
     * @return if entry already exist in sourceList, return false, else add it and return true.
     */
    @Deprecated
    public static <V> boolean addDistinctEntry(Collection<V> destination, V source) {
        return (destination != null && !destination.contains(source)) ? destination.add(source) : false;
    }

    /**
     * add all distinct entry to destination from source
     *
     * @param <V>
     * @param destination
     * @param source
     * @return the count of entries be added
     */
    public static <V> int addAll(Collection<V> destination, Collection<V> source) {
        if (destination == null || isEmpty(source)) {
            return 0;
        }

        int sourceCount = destination.size();
        for (V entry : source) {
            if (!destination.contains(entry)) {
                destination.add(entry);
            }
        }
        return destination.size() - sourceCount;
    }

    /**
     * WARNNING：time complexity O(N*N)
     * remove duplicate entries in list
     *
     * @param <V>
     * @param sourceList
     * @return the count of entries be removed
     */
    //TODO Reduce the time complexity
    public static <V> int distinctList(List<V> sourceList) {
        if (isEmpty(sourceList)) {
            return 0;
        }

        int sourceCount = sourceList.size();
        int sourceListSize = sourceList.size();
        for (int i = 0; i < sourceListSize; i++) {
            for (int j = (i + 1); j < sourceListSize; j++) {
                if (sourceList.get(i).equals(sourceList.get(j))) {
                    sourceList.remove(j);
                    sourceListSize = sourceList.size();
                    j--;
                }
            }
        }
        return sourceCount - sourceList.size();
    }

    /**
     * add not null entry to list
     *
     * @param sourceList
     * @param value
     * @return <ul>
     *         <li>if sourceList is null, return false</li>
     *         <li>if value is null, return false</li>
     *         <li>return {@link List#add(Object)}</li>
     *         </ul>
     */
    public static <V> boolean addListNotNullValue(List<V> sourceList, V value) {
        return (sourceList != null && value != null) ? sourceList.add(value) : false;
    }

    /**
     * Get the first index of the element which equals to expectedElement in sourceList.
     * The search begins at the start index and moves towards the end-1 index of sourceList.
     *
     * @param sourceList
     * @param expectedElement
     * @param <V>
     * @return the index of the element which equals to expectedElement , or -1 if no such element.
     */
    public static <V> int getIndex(List<V> sourceList, V expectedElement) {
        return getIndex(sourceList, expectedElement, new EqualityUtils.ObjectEqualStrategy<V>());
    }

    /**
     * Get the first index of the element which equals to expectedElement in sourceList.
     * The search begins at the start index and moves towards the end-1 index of sourceList.
     *
     * @param sourceList
     * @param expectedElement
     * @param <V>
     * @return the index of the element which equals to expectedElement , or -1 if no such element.
     */
    public static <V> int getIndex(List<V> sourceList, V expectedElement, EqualityUtils.EqualStrategy<V> equalStrategy) {
        return getIndex(sourceList, expectedElement, equalStrategy, 0);
    }

    /**
     * Get the first index of the element which equals to expectedElement in sourceList.
     * The search begins at the start index and moves towards the end-1 index of sourceList.
     *
     * @param sourceList
     * @param expectedElement
     * @param start       the start index, inclusive
     * @param <V>
     * @return the index of the element which equals to expectedElement , or -1 if no such element.
     */
    public static <V> int getIndex(List<V> sourceList, V expectedElement, EqualityUtils.EqualStrategy<V> equalStrategy, int start) {
        return getIndex(sourceList, expectedElement, equalStrategy, start, sourceList.size());
    }

    /**
     * Get the first index of the element which equals to expectedElement in sourceList.
     * The search begins at the start index and moves towards the end-1 index of sourceList.
     *
     * @param sourceList
     * @param expectedElement
     * @param start       the start index, inclusive
     * @param end         the end index, exclusive
     * @param <V>
     * @return the index of the element which equals to expectedElement , or -1 if no such element.
     */
    public static <V> int getIndex(List<V> sourceList, V expectedElement, EqualityUtils.EqualStrategy<V> equalStrategy, int start, int end) {
        if (sourceList == null) {
            return -1;
//            throw new NullPointerException(sourceList+" is null");
        }
        if (start > end) {
            return -1;
//            throw new IllegalArgumentException("the start index is bigger than end index");
        }
        int sourceSize = sourceList.size();
        if (start < 0 || start >= sourceSize) {
            return -1;
//            throw new ArrayIndexOutOfBoundsException("the start index is out of bound");
        }
        int actualEnd = Math.min(end, sourceSize);
        for (int i = start; i < actualEnd; i++) {
            if (equalStrategy.isEqual(expectedElement, sourceList.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 返回两个列表的交集
     *
     * @param list1
     * @param list2
     * @return null if list1 or list2 is null
     */
    public static <T> List<T> intersect(List<T> list1, List<T> list2) {
        if (list1==null || list2 ==null) {
            return null;
        }
        List<T> list = new ArrayList<>(list1.size());
        Collections.copy(list, list1);
        list.retainAll(list2);
        return list;
    }

    public static <T> List<T> asList(T... arr) {
        if (arr == null) {
            return null;
        }
        return new ArrayList<T>(Arrays.asList(arr));
    }

    /**
     * 返回两个列表的并集
     *
     * @param list1
     * @param list2
     * @return
     */
    public static <T> List<T> union(List<T> list1, List<T> list2) {
        @SuppressWarnings("unchecked")
        List<T> list = new ArrayList<T>(
                (Collection<? extends T>) Arrays.asList(new Object[list1.size()]));

        Collections.copy(list, list1);
        list.removeAll(list2);
        list.addAll(list2);
        return list;
    }

    /**
     * 返回两个列表的差集
     *
     * @param list1
     * @param list2
     * @return
     * @author mashengchao 2012-2-27 下午3:53:33
     */
    public static <T> List<T> diff(List<T> list1, List<T> list2) {
        List<T> unionList = union(list1, list2);
        List<T> intersectList = intersect(list1, list2);

        unionList.removeAll(intersectList);

        return unionList;
    }


}
