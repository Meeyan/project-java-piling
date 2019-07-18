/*
 * Copyright (c) 1997, 2017, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.util;

import sun.misc.SharedSecrets;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Hash table based implementation of the <tt>Map</tt> interface.  This
 * implementation provides all of the optional map operations, and permits
 * <tt>null</tt> values and the <tt>null</tt> key.  (The <tt>HashMap</tt>
 * class is roughly equivalent to <tt>Hashtable</tt>, except that it is
 * unsynchronized and permits nulls.)  This class makes no guarantees as to
 * the order of the map; in particular, it does not guarantee that the order
 * will remain constant over time.
 *
 * <p>This implementation provides constant-time performance for the basic
 * operations (<tt>get</tt> and <tt>put</tt>), assuming the hash function
 * disperses the elements properly among the buckets.  Iteration over
 * collection views requires time proportional to the "capacity" of the
 * <tt>HashMap</tt> instance (the number of buckets) plus its size (the number
 * of key-value mappings).  Thus, it's very important not to set the initial
 * capacity too high (or the load factor too low) if iteration performance is
 * important.
 *
 * <p>An instance of <tt>HashMap</tt> has two parameters that affect its
 * performance: <i>initial capacity</i> and <i>load factor</i>.  The
 * <i>capacity</i> is the number of buckets in the hash table, and the initial
 * capacity is simply the capacity at the time the hash table is created.  The
 * <i>load factor</i> is a measure of how full the hash table is allowed to
 * get before its capacity is automatically increased.  When the number of
 * entries in the hash table exceeds the product of the load factor and the
 * current capacity, the hash table is <i>rehashed</i> (that is, internal data
 * structures are rebuilt) so that the hash table has approximately twice the
 * number of buckets.
 *
 * <p>As a general rule, the default load factor (.75) offers a good
 * tradeoff between time and space costs.  Higher values decrease the
 * space overhead but increase the lookup cost (reflected in most of
 * the operations of the <tt>HashMap</tt> class, including
 * <tt>get</tt> and <tt>put</tt>).  The expected number of entries in
 * the map and its load factor should be taken into account when
 * setting its initial capacity, so as to minimize the number of
 * rehash operations.  If the initial capacity is greater than the
 * maximum number of entries divided by the load factor, no rehash
 * operations will ever occur.
 *
 * <p>If many mappings are to be stored in a <tt>HashMap</tt>
 * instance, creating it with a sufficiently large capacity will allow
 * the mappings to be stored more efficiently than letting it perform
 * automatic rehashing as needed to grow the table.  Note that using
 * many keys with the same {@code hashCode()} is a sure way to slow
 * down performance of any hash table. To ameliorate impact, when keys
 * are {@link Comparable}, this class may use comparison order among
 * keys to help break ties.
 *
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a hash map concurrently, and at least one of
 * the threads modifies the map structurally, it <i>must</i> be
 * synchronized externally.  (A structural modification is any operation
 * that adds or deletes one or more mappings; merely changing the value
 * associated with a key that an instance already contains is not a
 * structural modification.)  This is typically accomplished by
 * synchronizing on some object that naturally encapsulates the map.
 * <p>
 * If no such object exists, the map should be "wrapped" using the
 * {@link Collections#synchronizedMap Collections.synchronizedMap}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the map:<pre>
 *   Map m = Collections.synchronizedMap(new HashMap(...));</pre>
 *
 * <p>The iterators returned by all of this class's "collection view methods"
 * are <i>fail-fast</i>: if the map is structurally modified at any time after
 * the iterator is created, in any way except through the iterator's own
 * <tt>remove</tt> method, the iterator will throw a
 * {@link ConcurrentModificationException}.  Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than risking
 * arbitrary, non-deterministic behavior at an undetermined time in the
 * future.
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw <tt>ConcurrentModificationException</tt> on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness: <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author Doug Lea
 * @author Josh Bloch
 * @author Arthur van Hoff
 * @author Neal Gafter
 * @see Object#hashCode()
 * @see Collection
 * @see Map
 * @see TreeMap
 * @see Hashtable
 * @since 1.2
 */
@SuppressWarnings("all")
public class HashMap<K, V> extends AbstractMap<K, V>
        implements Map<K, V>, Cloneable, Serializable {

    private static final long serialVersionUID = 362498820763181265L;

    /*
     * Implementation notes.
     *
     * This map usually acts as a binned (bucketed) hash table, but
     * when bins get too large, they are transformed into bins of
     * TreeNodes, each structured similarly to those in
     * java.util.TreeMap. Most methods try to use normal bins, but
     * relay to TreeNode methods when applicable (simply by checking
     * instanceof a node).  Bins of TreeNodes may be traversed and
     * used like any others, but additionally support faster lookup
     * when overpopulated. However, since the vast majority of bins in
     * normal use are not overpopulated, checking for existence of
     * tree bins may be delayed in the course of table methods.
     *
     * Tree bins (i.e., bins whose elements are all TreeNodes) are
     * ordered primarily by hashCode, but in the case of ties, if two
     * elements are of the same "class C implements Comparable<C>",
     * type then their compareTo method is used for ordering. (We
     * conservatively check generic types via reflection to validate
     * this -- see method comparableClassFor).  The added complexity
     * of tree bins is worthwhile in providing worst-case O(log n)
     * operations when keys either have distinct hashes or are
     * orderable, Thus, performance degrades gracefully under
     * accidental or malicious usages in which hashCode() methods
     * return values that are poorly distributed, as well as those in
     * which many keys share a hashCode, so long as they are also
     * Comparable. (If neither of these apply, we may waste about a
     * factor of two in time and space compared to taking no
     * precautions. But the only known cases stem from poor user
     * programming practices that are already so slow that this makes
     * little difference.)
     *
     * Because TreeNodes are about twice the size of regular nodes, we
     * use them only when bins contain enough nodes to warrant use
     * (see TREEIFY_THRESHOLD). And when they become too small (due to
     * removal or resizing) they are converted back to plain bins.  In
     * usages with well-distributed user hashCodes, tree bins are
     * rarely used.  Ideally, under random hashCodes, the frequency of
     * nodes in bins follows a Poisson distribution
     * (http://en.wikipedia.org/wiki/Poisson_distribution) with a
     * parameter of about 0.5 on average for the default resizing
     * threshold of 0.75, although with a large variance because of
     * resizing granularity. Ignoring variance, the expected
     * occurrences of list size k are (exp(-0.5) * pow(0.5, k) /
     * factorial(k)). The first values are:
     *
     * 0:    0.60653066
     * 1:    0.30326533
     * 2:    0.07581633
     * 3:    0.01263606
     * 4:    0.00157952
     * 5:    0.00015795
     * 6:    0.00001316
     * 7:    0.00000094
     * 8:    0.00000006
     * more: less than 1 in ten million
     *
     * The root of a tree bin is normally its first node.  However,
     * sometimes (currently only upon Iterator.remove), the root might
     * be elsewhere, but can be recovered following parent links
     * (method TreeNode.root()).
     *
     * All applicable internal methods accept a hash code as an
     * argument (as normally supplied from a public method), allowing
     * them to call each other without recomputing user hashCodes.
     * Most internal methods also accept a "tab" argument, that is
     * normally the current table, but may be a new or old one when
     * resizing or converting.
     *
     * When bin lists are treeified, split, or untreeified, we keep
     * them in the same relative access/traversal order (i.e., field
     * Node.next) to better preserve locality, and to slightly
     * simplify handling of splits and traversals that invoke
     * iterator.remove. When using comparators on insertion, to keep a
     * total ordering (or as close as is required here) across
     * rebalancings, we compare classes and identityHashCodes as
     * tie-breakers.
     *
     * The use and transitions among plain vs tree modes is
     * complicated by the existence of subclass LinkedHashMap. See
     * below for hook methods defined to be invoked upon insertion,
     * removal and access that allow LinkedHashMap internals to
     * otherwise remain independent of these mechanics. (This also
     * requires that a map instance be passed to some utility methods
     * that may create new nodes.)
     *
     * The concurrent-programming-like SSA-based coding style helps
     * avoid aliasing errors amid all of the twisty pointer operations.
     */

    /**
     * 默认初始化容量：16
     * The default initial capacity - MUST be a power of two.
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16

    /**
     * 最大容量：1073741824
     * <p>
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two <= 1<<30.
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 负载因子：每达到 size * 0.75 时，要扩容，比较耗性能（最好初始时就有一个预估容量）
     * <p>
     * The load factor used when none specified in constructor.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * threshold:临界值，入口，门槛
     * <p>
     * 容器的数量临界值：决定是否采用树的形式而不是使用链表的形式存储数据
     * 用途：当达到该临界值，对应的容器将会由链表转换为树
     * <p>
     * The bin count threshold for using a tree rather than list for a
     * bin.  Bins are converted to trees when adding an element to a
     * bin with at least this many nodes. The value must be greater
     * than 2 and should be at least 8 to mesh with assumptions in
     * tree removal about conversion back to plain bins upon
     * shrinkage.
     */
    static final int TREEIFY_THRESHOLD = 8;

    /**
     * 反树化操作，当树的节点数量小于该值时，将会退化成链表
     * <p>
     * The bin count threshold for untreeifying a (split) bin during a
     * resize operation. Should be less than TREEIFY_THRESHOLD, and at
     * most 6 to mesh with shrinkage detection under removal.
     */
    static final int UNTREEIFY_THRESHOLD = 6;

    /**
     * treeified：树化的
     * <p>
     * 表能树化的最小容量。
     * <p>
     * The smallest table capacity for which bins may be treeified.
     * (Otherwise the table is resized if too many nodes in a bin.)
     * Should be at least 4 * TREEIFY_THRESHOLD to avoid conflicts
     * between resizing and treeification thresholds.
     */
    static final int MIN_TREEIFY_CAPACITY = 64;

    /**
     * Basic hash bin node, used for most entries.  (See below for
     * TreeNode subclass, and in LinkedHashMap for its Entry subclass.)
     */
    static class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;

        /**
         * 链表结构
         */
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public final K getKey() {
            return key;
        }

        public final V getValue() {
            return value;
        }

        public final String toString() {
            return key + "=" + value;
        }

        public final int hashCode() {
            return Objects.hashCode(key) ^ Objects.hashCode(value);
        }

        public final V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }

        public final boolean equals(Object o) {
            if (o == this)
                return true;
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                if (Objects.equals(key, e.getKey()) &&
                        Objects.equals(value, e.getValue()))
                    return true;
            }
            return false;
        }
    }

    /* ---------------- Static utilities -------------- */

    /**
     * 计算hash值，把高位传播到低位
     * collide : 冲突，碰撞
     * <p>
     * Computes key.hashCode() and spreads (XORs) higher bits of hash
     * to lower.  Because the table uses power-of-two masking, sets of
     * hashes that vary only in bits above the current mask will
     * always collide. (Among known examples are sets of Float keys
     * holding consecutive whole numbers in small tables.)  So we
     * apply a transform that spreads the impact of higher bits
     * downward. There is a tradeoff between speed, utility, and
     * quality of bit-spreading. Because many common sets of hashes
     * are already reasonably distributed (so don't benefit from
     * spreading), and because we use trees to handle large sets of
     * collisions in bins, we just XOR some shifted bits in the
     * cheapest possible way to reduce systematic lossage, as well as
     * to incorporate impact of the highest bits that would otherwise
     * never be used in index calculations because of table bounds.
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * Returns x's Class if it is of the form "class C implements
     * Comparable<C>", else null.
     */
    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c;
            Type[] ts, as;
            Type t;
            ParameterizedType p;
            if ((c = x.getClass()) == String.class) // bypass checks
                return c;
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType)
                            && ((p = (ParameterizedType) t).getRawType() == Comparable.class)
                            && (as = p.getActualTypeArguments()) != null
                            && as.length == 1
                            && as[0] == c) // type arg is c
                        return c;
                }
            }
        }
        return null;
    }

    /**
     * Returns k.compareTo(x) if x matches kc (k's screened comparable
     * class), else 0.
     */
    @SuppressWarnings({"rawtypes", "unchecked"}) // for cast to Comparable
    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc ? 0 : ((Comparable) k).compareTo(x));
    }

    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    /* ---------------- Fields -------------- */

    /**
     * 数据表，第一次使用时初始化，必要时扩容。
     * 长度总是2的次方
     * <p>
     * The table, initialized on first use, and resized as
     * necessary. When allocated, length is always a power of two.
     * (We also tolerate length zero in some operations to allow
     * bootstrapping mechanics that are currently not needed.)
     */
    transient Node<K, V>[] table;

    /**
     * Holds cached entrySet(). Note that AbstractMap fields are used
     * for keySet() and values().
     */
    transient Set<Map.Entry<K, V>> entrySet;

    /**
     * The number of key-value mappings contained in this map.
     */
    transient int size;

    /**
     * The number of times this HashMap has been structurally modified
     * Structural modifications are those that change the number of mappings in
     * the HashMap or otherwise modify its internal structure (e.g.,
     * rehash).  This field is used to make iterators on Collection-views of
     * the HashMap fail-fast.  (See ConcurrentModificationException).
     */
    transient int modCount;

    /**
     * The next size value at which to resize (capacity * load factor).
     *
     * @serial
     */
    // (The javadoc description is true upon serialization.
    // Additionally, if the table array has not been allocated, this
    // field holds the initial array capacity, or zero signifying
    // DEFAULT_INITIAL_CAPACITY.)
    int threshold;

    /**
     * The load factor for the hash table.
     *
     * @serial
     */
    final float loadFactor;

    /* ---------------- Public operations -------------- */

    /**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and load factor.
     *
     * @param initialCapacity the initial capacity
     * @param loadFactor      the load factor
     * @throws IllegalArgumentException if the initial capacity is negative
     *                                  or the load factor is nonpositive
     */
    public HashMap(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
                    initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                    loadFactor);
        this.loadFactor = loadFactor;
        this.threshold = tableSizeFor(initialCapacity);
    }

    /**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and the default load factor (0.75).
     *
     * @param initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative.
     */
    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * Constructs an empty <tt>HashMap</tt> with the default initial capacity
     * (16) and the default load factor (0.75).
     */
    public HashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; // all other fields defaulted
    }

    /**
     * Constructs a new <tt>HashMap</tt> with the same mappings as the
     * specified <tt>Map</tt>.  The <tt>HashMap</tt> is created with
     * default load factor (0.75) and an initial capacity sufficient to
     * hold the mappings in the specified <tt>Map</tt>.
     *
     * @param m the map whose mappings are to be placed in this map
     * @throws NullPointerException if the specified map is null
     */
    public HashMap(Map<? extends K, ? extends V> m) {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        putMapEntries(m, false);
    }

    /**
     * Implements Map.putAll and Map constructor.
     *
     * @param m     the map
     * @param evict false when initially constructing this map, else
     *              true (relayed to method afterNodeInsertion).
     */
    final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
        int s = m.size();
        if (s > 0) {
            if (table == null) { // pre-size
                float ft = ((float) s / loadFactor) + 1.0F;
                int t = ((ft < (float) MAXIMUM_CAPACITY) ?
                        (int) ft : MAXIMUM_CAPACITY);
                if (t > threshold)
                    threshold = tableSizeFor(t);
            } else if (s > threshold)
                resize();
            for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
                K key = e.getKey();
                V value = e.getValue();
                putVal(hash(key), key, value, false, evict);
            }
        }
    }

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    public int size() {
        return size;
    }

    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * @return <tt>true</tt> if this map contains no key-value mappings
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
     * key.equals(k))}, then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     *
     * <p>A return value of {@code null} does not <i>necessarily</i>
     * indicate that the map contains no mapping for the key; it's also
     * possible that the map explicitly maps the key to {@code null}.
     * The {@link #containsKey containsKey} operation may be used to
     * distinguish these two cases.
     *
     * @see #put(Object, Object)
     */
    public V get(Object key) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    /**
     * Implements Map.get and related methods.
     *
     * @param hash hash for key
     * @param key  the key
     * @return the node, or null if none
     */
    final Node<K, V> getNode(int hash, Object key) {
        Node<K, V>[] tab;
        Node<K, V> first, e;
        int n;
        K k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && // always check first node
                    ((k = first.key) == key || (key != null && key.equals(k))))
                return first;
            if ((e = first.next) != null) {
                if (first instanceof TreeNode)
                    return ((TreeNode<K, V>) first).getTreeNode(hash, key);
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        return e;
                } while ((e = e.next) != null);
            }
        }
        return null;
    }

    /**
     * Returns <tt>true</tt> if this map contains a mapping for the
     * specified key.
     *
     * @param key The key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified
     * key.
     */
    public boolean containsKey(Object key) {
        return getNode(hash(key), key) != null;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key, the old
     * value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * (A <tt>null</tt> return can also indicate that the map
     * previously associated <tt>null</tt> with <tt>key</tt>.)
     */
    public V put(K key, V value) {
        // 取key的hash值
        return putVal(hash(key), key, value, false, true);
    }

    /**
     * Implements Map.put and related methods.
     *
     * @param hash         hash for key
     * @param key          the key
     * @param value        the value to put
     * @param onlyIfAbsent if true, don't change existing value
     * @param evict        if false, the table is in creation mode.
     * @return previous value, or null if none
     */
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        // 方法为final，禁止覆盖
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, i;

        /**
         * 1. 初始化或者扩容tab，如果是扩容的话，内部包含节点的重新定位、移动操作，resize方法是核心方法，必须懂
         * 2. 定位元素在表中的位置，然后存入
         */

        // tab为空先初始化：resize
        if ((tab = table) == null || (n = tab.length) == 0) {
            n = (tab = resize()).length;
        }

        /**
         *
         * 2种情况：
         *   1. 计算出的新的位置上没有元素，直接新建node，存入。
         *   2. 位置上有元素存在，则hash冲突，但hash值不一定相同
         *    hash值相同，且key值相同
         *     2.1 若旧元素的hash和新的key的hash相同，且key值相同，则认为找到了节点e（覆盖操作）
         *    hash值相同，key值不同或者 hash值不同
         *     2.2 否则，若节点是treeNode，在树中找到节点e（或者 新建节点，e为null）
         *     2.3 否则(链表存储方式或者链表转换为树)，指定位置上的节点存在，hash相同，key值不同，或hash不同，这个节点不是treeNode（hash必定会相同）
         *          （思考，hash不一致的情况下，会落到同一个位置么？会，高位不同，低位相同，则会落到同一个位置，但是此时hash并不相同）
         */

        // (长度 - 1) & hash = 元素在数据表中的位置
        // 节点p：为在新数据表中的元素
        if ((p = tab[i = (n - 1) & hash]) == null) {
            // 新的位置上没有元素，直接添加进去
            tab[i] = newNode(hash, key, value, null);
        } else {
            // 有元素，表示hash冲突

            Node<K, V> e;
            K k;

            /*
             * hash相同且key值相同
             *
             * p.hash == hash: 哈希相同
             * ((k = p.key) == key || (key != null && key.equals(k))) : 将原有节点的key赋值给k，同时判断原有的k和新的key是否相同
             */
            if (p.hash == hash && ((k = p.key) == key || (key != null && key.equals(k)))) {
                e = p;
            } else if (p instanceof TreeNode) {
                // tree节点
                e = ((TreeNode<K, V>) p).putTreeVal(this, tab, hash, key, value);
            } else {

                /*
                 * 2.3
                 * 循环的目的：
                 *   1. 如果不存在一个节点，其hash和key的hash相同，且其key值和 新的key值相同，那么新建一个节点，加入链表。
                 *   2. 如果存在一个hash冲突的节点，定位到该节点，交给外层处理。
                 */
                for (int binCount = 0; ; ++binCount) {


                    // node的next节点为null，那么新建节点，e指向该节点，在外层将value赋值给e.value，即便转换成树，value也没有作赋值操作
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);

                        // 是否需要转成树
                        if (binCount >= TREEIFY_THRESHOLD - 1) {
                            // -1 for 1st
                            treeifyBin(tab, hash);
                        }
                        break;
                    }

                    // 节点不为null，hash相同，且key值相同，不做操作，直接中断，视为找到了节点e，在外层作覆盖值操作
                    if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
                        break;
                    }

                    // 准备下次循环
                    p = e;
                }
            }

            // 存在hash冲突的节点
            if (e != null) {
                // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null) {
                    e.value = value;
                }

                // 方法为空，不用看
                afterNodeAccess(e);
                return oldValue;
            }
        }

        ++modCount;
        if (++size > threshold) {
            resize();
        }

        // 方法为空，不用看
        afterNodeInsertion(evict);
        return null;
    }

    /**
     * Initializes or doubles table size.  If null, allocates in
     * accord with initial capacity target held in field threshold.
     * Otherwise, because we are using power-of-two expansion, the
     * elements from each bin must either stay at same index, or move
     * with a power of two offset in the new table.
     *
     * @return the table
     */
    final Node<K, V>[] resize() {

        /**
         * 目标：初始化或者扩容tab
         *
         * 总体流程：
         *
         * 1. 计算新tab的容量
         *   1.1 如果存在已有数据表（老的容量），则扩充一倍
         *   1.2 如果老容量不存在，但是老的临界值存在，则新表长度使用老的临界值（显示指定map初始大小时存在这种情况）
         *   1.3 老容量不存在，且临界值不存在，使用默认参数初始化新表空间
         *
         * 2. 老的数据表存在，要执行扩容后的移动操作
         *   2.1 节点的next节点不存在，表示该位置在老的数据表中不存在hash冲突，则直接计算在新表中的位置，存放节点
         *   2.2 节点的next节点存在，同时该节点是TreeNode，需要将树中的元素处理一遍，将hash不冲突的元素移到新表中的其他位置，split方法是核心方法，必须懂
         *   2.3 节点的next节点存在，但节点不是TreeNode，此时是链表，hash冲突并不严重，则遍历链表，计算每个元素在新空间中的位置
         */

        Node<K, V>[] oldTab = table;
        // 旧的容量
        int oldCap = (oldTab == null) ? 0 : oldTab.length;

        int oldThr = threshold;
        int newCap, newThr = 0;
        /*
         * 老的容量大于0，需要扩容
         * Integer.MAX_VALUE=2147483647
         */
        if (oldCap > 0) {
            if (oldCap >= MAXIMUM_CAPACITY) {
                threshold = Integer.MAX_VALUE;
                return oldTab;
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY && oldCap >= DEFAULT_INITIAL_CAPACITY) {
                /*
                 * (newCap = oldCap << 1)：新的容量为老的临界值一倍（左移一位）
                 * newThr = oldThr << 1 表示 新的临界值为老的临界值左移一位（一倍）
                 */
                // double threshold，
                newThr = oldThr << 1;
            }
        } else if (oldThr > 0) {
            // initial capacity was placed in threshold
            newCap = oldThr;
        } else {
            /*
             * 老的容量为空或者=0 并且 老的临界值也为空或者=0，则执行初始化操作
             */
            // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;

            // 新的临界值：12
            newThr = (int) (DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }

        if (newThr == 0) {
            float ft = (float) newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float) MAXIMUM_CAPACITY ? (int) ft : Integer.MAX_VALUE);
        }

        threshold = newThr;

        /*
         * 创建数据表
         */
        @SuppressWarnings({"rawtypes", "unchecked"})
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
        table = newTab;

        // 存在老的数据表，要执行复制操作
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K, V> e;

                // 取出元素
                if ((e = oldTab[j]) != null) {
                    // 释放旧有位置，gc
                    oldTab[j] = null;
                    if (e.next == null) {
                        // 没有下一个元素，那么把node存入新的数据表黄总
                        newTab[e.hash & (newCap - 1)] = e;
                    } else if (e instanceof TreeNode) {
                        // 如果node是树，则需要计算树中节点新的分布情况
                        ((TreeNode<K, V>) e).split(this, newTab, j, oldCap);
                    } else {
                        // preserve order
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        Node<K, V> next;
                        do {
                            // 参考split方法内部的注释
                            // 如原空间16 ->  0000 1111  (cap-1)  hash & (cap-1) = 元素在表中的位置
                            //   新空间32 ->  0001 1111  (cap-1)
                            // 那么   16 ->  0001 0000   (cap)   hash & cap = 节点是否有高位hash，有就需要变动位置，否则不变

                            // 记住：hash & cap          得到的是节点是否需要变动位置
                            //      hash & (cap - 1)    得到的是节点在表中的位置

                            // 注意：不是(e.hash & (oldCap-1));而是(e.hash & oldCap)

                            // (e.hash & oldCap) 得到的 是 『元素的在数组中的位置是否需要移动』,示例如下
                            // 示例1：
                            // e.hash = 10 0000 1010
                            // oldCap = 16 0001 0000
                            //	 &    = 0  0000 0000   比较高位的第一位 0
                            // 结论：元素位置在扩容后数组中的位置没有发生改变

                            // 示例2：
                            // e.hash = 17 0001 0001
                            // oldCap = 16 0001 0000
                            //	 &    = 1  0001 0000      比较高位的第一位   1
                            // 结论：元素位置在扩容后数组中的位置发生了改变，新的下标位置是原下标位置+原数组长度


                            // (e.hash & (oldCap-1)) 得到的是『下标位置』,示例如下
                            // 示例1：
                            //   e.hash = 10 0000 1010
                            // oldCap-1 = 15 0000 1111
                            //      &   = 10 0000 1010

                            // 示例2：
                            //   e.hash = 17 0001 0001
                            // oldCap-1 = 15 0000 1111
                            //      &   = 1  0000 0001

                            // 新下标位置
                            //   e.hash = 17 0001 0001
                            // newCap-1 = 31 0001 1111    newCap = 32
                            //      &   = 17 0001 0001    1 + oldCap = 1+16 = 17

                            // 跟新next
                            next = e.next;

                            if ((e.hash & oldCap) == 0) {
                                // 位置不需要变动的情况
                                // 加入存在一组元素： a -> b -> c -> d
                                // 第一次：
                                //  loHead = a;  loHead 只在第一次赋值
                                //  loTail = a

                                // 第二次
                                //   a.next = b
                                //   loTail = b

                                // 第三次
                                //   b.next = c
                                //   loTail = c

                                // 第四次
                                //   c.next = d
                                //   loTail = d

                                // 结论：loTail总是指向下一个节点，方便下次遍历，将新的节点加入到上一个节点的尾部
                                if (loTail == null) {
                                    loHead = e;
                                } else {
                                    loTail.next = e;
                                }

                                // 指向最新的元素
                                loTail = e;

                            } else {
                                // 位置需要变动的情况
                                // 加入存在一组元素： a -> b -> c -> d
                                // first time
                                //   hiHead = a;  hiHead 只在第一次赋值
                                //   hiTail = a;

                                // second time
                                //   a.next = b;
                                //   hiTail = b;

                                // third time
                                //   b.next = c;
                                //   hiTail = c;

                                // forth time
                                //   c.next = d;
                                //   hiTail = d;


                                if (hiTail == null) {
                                    hiHead = e;
                                } else {
                                    hiTail.next = e;
                                }
                                hiTail = e;
                            }
                        } while ((e = next) != null);

                        // 位置不变
                        if (loTail != null) {
                            //是否为了帮助gc？
                            loTail.next = null;
                            newTab[j] = loHead;
                        }

                        // 位置发生变化，新的为位置：原下标位置 + 原数组长度
                        if (hiTail != null) {
                            //是否为了帮助gc？
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

    /**
     * Replaces all linked nodes in bin at index for given hash unless
     * table is too small, in which case resizes instead.
     */
    final void treeifyBin(Node<K, V>[] tab, int hash) {
        int n, index;
        Node<K, V> e;
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
            resize();
        else if ((e = tab[index = (n - 1) & hash]) != null) {
            TreeNode<K, V> hd = null, tl = null;
            do {
                TreeNode<K, V> p = replacementTreeNode(e, null);
                if (tl == null)
                    hd = p;
                else {
                    p.prev = tl;
                    tl.next = p;
                }
                tl = p;
            } while ((e = e.next) != null);
            if ((tab[index] = hd) != null)
                hd.treeify(tab);
        }
    }

    /**
     * Copies all of the mappings from the specified map to this map.
     * These mappings will replace any mappings that this map had for
     * any of the keys currently in the specified map.
     *
     * @param m mappings to be stored in this map
     * @throws NullPointerException if the specified map is null
     */
    public void putAll(Map<? extends K, ? extends V> m) {
        putMapEntries(m, true);
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     *
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with <tt>key</tt>, or
     * <tt>null</tt> if there was no mapping for <tt>key</tt>.
     * (A <tt>null</tt> return can also indicate that the map
     * previously associated <tt>null</tt> with <tt>key</tt>.)
     */
    public V remove(Object key) {
        Node<K, V> e;
        return (e = removeNode(hash(key), key, null, false, true)) == null ?
                null : e.value;
    }

    /**
     * Implements Map.remove and related methods.
     *
     * @param hash       hash for key
     * @param key        the key
     * @param value      the value to match if matchValue, else ignored
     * @param matchValue if true only remove if value is equal true：匹配value值，false：不匹配value值
     * @param movable    if false do not move other nodes while removing
     * @return the node, or null if none
     */
    final Node<K, V> removeNode(int hash, Object key, Object value,
                                boolean matchValue, boolean movable) {
        Node<K, V>[] tab;
        Node<K, V> p;
        int n, index;

        // 表存在，找到了元素
        // 1. hash相同，key值相同，视为找到了节点
        // 2. hash相同key值不同或者hash不同，但是存在next节点，
        //    2.1 p是TreeNode，遍历找到node节点
        //    2.2 p是链表，遍历找到node
        if ((tab = table) != null
                && (n = tab.length) > 0
                && (p = tab[index = (n - 1) & hash]) != null) {
            Node<K, V> node = null, e;
            K k;
            V v;
            if (p.hash == hash
                    && ((k = p.key) == key || (key != null && key.equals(k)))) {
                node = p;
            } else if ((e = p.next) != null) {
                if (p instanceof TreeNode)
                    node = ((TreeNode<K, V>) p).getTreeNode(hash, key);
                else {
                    do {
                        if (e.hash == hash &&
                                ((k = e.key) == key ||
                                        (key != null && key.equals(k)))) {
                            node = e;
                            break;
                        }
                        p = e;
                    } while ((e = e.next) != null);
                }
            }

            // node存在 并且 （ 不匹配value 或者 value和node.value相同 或者 value和node.value的值相同）
            // 1. node是treenode，从树中删除节点，移除tab中的节点
            // 2. node不是TreeNode，但是和p相同，则直接将原表中的位置上的p节点指向node的next节点，node被删除。
            // 3. node不是TreeNode，且和p不同（p和node hash冲突），则p.next指向node.next，删除node节点。
            if (node != null && (!matchValue || (v = node.value) == value ||
                    (value != null && value.equals(v)))) {

                if (node instanceof TreeNode) {
                    ((TreeNode<K, V>) node).removeTreeNode(this, tab, movable);
                } else if (node == p) {
                    tab[index] = node.next;
                } else {
                    p.next = node.next;
                }
                ++modCount;
                --size;

                // 空方法
                afterNodeRemoval(node);
                return node;
            }
        }
        return null;
    }

    /**
     * Removes all of the mappings from this map.
     * The map will be empty after this call returns.
     */
    public void clear() {
        Node<K, V>[] tab;
        modCount++;
        if ((tab = table) != null && size > 0) {
            size = 0;
            for (int i = 0; i < tab.length; ++i)
                tab[i] = null;
        }
    }

    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.
     *
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     * specified value
     */
    public boolean containsValue(Object value) {
        Node<K, V>[] tab;
        V v;
        if ((tab = table) != null && size > 0) {
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K, V> e = tab[i]; e != null; e = e.next) {
                    if ((v = e.value) == value ||
                            (value != null && value.equals(v)))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations.  It does not support the <tt>add</tt> or <tt>addAll</tt>
     * operations.
     *
     * @return a set view of the keys contained in this map
     */
    public Set<K> keySet() {
        Set<K> ks = keySet;
        if (ks == null) {
            ks = new KeySet();
            keySet = ks;
        }
        return ks;
    }

    final class KeySet extends AbstractSet<K> {
        public final int size() {
            return size;
        }

        public final void clear() {
            HashMap.this.clear();
        }

        public final Iterator<K> iterator() {
            return new KeyIterator();
        }

        public final boolean contains(Object o) {
            return containsKey(o);
        }

        public final boolean remove(Object key) {
            return removeNode(hash(key), key, null, false, true) != null;
        }

        public final Spliterator<K> spliterator() {
            return new KeySpliterator<>(HashMap.this, 0, -1, 0, 0);
        }

        public final void forEach(Consumer<? super K> action) {
            Node<K, V>[] tab;
            if (action == null)
                throw new NullPointerException();
            if (size > 0 && (tab = table) != null) {
                int mc = modCount;
                for (int i = 0; i < tab.length; ++i) {
                    for (Node<K, V> e = tab[i]; e != null; e = e.next)
                        action.accept(e.key);
                }
                if (modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own <tt>remove</tt> operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Collection.remove</tt>, <tt>removeAll</tt>,
     * <tt>retainAll</tt> and <tt>clear</tt> operations.  It does not
     * support the <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * @return a view of the values contained in this map
     */
    public Collection<V> values() {
        Collection<V> vs = values;
        if (vs == null) {
            vs = new Values();
            values = vs;
        }
        return vs;
    }

    final class Values extends AbstractCollection<V> {
        public final int size() {
            return size;
        }

        public final void clear() {
            HashMap.this.clear();
        }

        public final Iterator<V> iterator() {
            return new ValueIterator();
        }

        public final boolean contains(Object o) {
            return containsValue(o);
        }

        public final Spliterator<V> spliterator() {
            return new ValueSpliterator<>(HashMap.this, 0, -1, 0, 0);
        }

        public final void forEach(Consumer<? super V> action) {
            Node<K, V>[] tab;
            if (action == null)
                throw new NullPointerException();
            if (size > 0 && (tab = table) != null) {
                int mc = modCount;
                for (int i = 0; i < tab.length; ++i) {
                    for (Node<K, V> e = tab[i]; e != null; e = e.next)
                        action.accept(e.value);
                }
                if (modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation, or through the
     * <tt>setValue</tt> operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
     * <tt>clear</tt> operations.  It does not support the
     * <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * @return a set view of the mappings contained in this map
     */
    public Set<Map.Entry<K, V>> entrySet() {
        Set<Map.Entry<K, V>> es;
        return (es = entrySet) == null ? (entrySet = new EntrySet()) : es;
    }

    final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
        public final int size() {
            return size;
        }

        public final void clear() {
            HashMap.this.clear();
        }

        public final Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        public final boolean contains(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            Object key = e.getKey();
            Node<K, V> candidate = getNode(hash(key), key);
            return candidate != null && candidate.equals(e);
        }

        public final boolean remove(Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
                Object key = e.getKey();
                Object value = e.getValue();
                return removeNode(hash(key), key, value, true, true) != null;
            }
            return false;
        }

        public final Spliterator<Map.Entry<K, V>> spliterator() {
            return new EntrySpliterator<>(HashMap.this, 0, -1, 0, 0);
        }

        public final void forEach(Consumer<? super Map.Entry<K, V>> action) {
            Node<K, V>[] tab;
            if (action == null)
                throw new NullPointerException();
            if (size > 0 && (tab = table) != null) {
                int mc = modCount;
                for (int i = 0; i < tab.length; ++i) {
                    for (Node<K, V> e = tab[i]; e != null; e = e.next)
                        action.accept(e);
                }
                if (modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }
    }

    // Overrides of JDK8 Map extension methods

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? defaultValue : e.value;
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return putVal(hash(key), key, value, true, true);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return removeNode(hash(key), key, value, true, true) != null;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        Node<K, V> e;
        V v;
        if ((e = getNode(hash(key), key)) != null &&
                ((v = e.value) == oldValue || (v != null && v.equals(oldValue)))) {
            e.value = newValue;
            afterNodeAccess(e);
            return true;
        }
        return false;
    }

    @Override
    public V replace(K key, V value) {
        Node<K, V> e;
        if ((e = getNode(hash(key), key)) != null) {
            V oldValue = e.value;
            e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
        return null;
    }

    @Override
    public V computeIfAbsent(K key,
                             Function<? super K, ? extends V> mappingFunction) {
        if (mappingFunction == null)
            throw new NullPointerException();
        int hash = hash(key);
        Node<K, V>[] tab;
        Node<K, V> first;
        int n, i;
        int binCount = 0;
        TreeNode<K, V> t = null;
        Node<K, V> old = null;
        if (size > threshold || (tab = table) == null ||
                (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((first = tab[i = (n - 1) & hash]) != null) {
            if (first instanceof TreeNode)
                old = (t = (TreeNode<K, V>) first).getTreeNode(hash, key);
            else {
                Node<K, V> e = first;
                K k;
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k)))) {
                        old = e;
                        break;
                    }
                    ++binCount;
                } while ((e = e.next) != null);
            }
            V oldValue;
            if (old != null && (oldValue = old.value) != null) {
                afterNodeAccess(old);
                return oldValue;
            }
        }
        V v = mappingFunction.apply(key);
        if (v == null) {
            return null;
        } else if (old != null) {
            old.value = v;
            afterNodeAccess(old);
            return v;
        } else if (t != null)
            t.putTreeVal(this, tab, hash, key, v);
        else {
            tab[i] = newNode(hash, key, v, first);
            if (binCount >= TREEIFY_THRESHOLD - 1)
                treeifyBin(tab, hash);
        }
        ++modCount;
        ++size;
        afterNodeInsertion(true);
        return v;
    }

    public V computeIfPresent(K key,
                              BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (remappingFunction == null)
            throw new NullPointerException();
        Node<K, V> e;
        V oldValue;
        int hash = hash(key);
        if ((e = getNode(hash, key)) != null &&
                (oldValue = e.value) != null) {
            V v = remappingFunction.apply(key, oldValue);
            if (v != null) {
                e.value = v;
                afterNodeAccess(e);
                return v;
            } else
                removeNode(hash, key, null, false, true);
        }
        return null;
    }

    @Override
    public V compute(K key,
                     BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        if (remappingFunction == null)
            throw new NullPointerException();
        int hash = hash(key);
        Node<K, V>[] tab;
        Node<K, V> first;
        int n, i;
        int binCount = 0;
        TreeNode<K, V> t = null;
        Node<K, V> old = null;
        if (size > threshold || (tab = table) == null ||
                (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((first = tab[i = (n - 1) & hash]) != null) {
            if (first instanceof TreeNode)
                old = (t = (TreeNode<K, V>) first).getTreeNode(hash, key);
            else {
                Node<K, V> e = first;
                K k;
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k)))) {
                        old = e;
                        break;
                    }
                    ++binCount;
                } while ((e = e.next) != null);
            }
        }
        V oldValue = (old == null) ? null : old.value;
        V v = remappingFunction.apply(key, oldValue);
        if (old != null) {
            if (v != null) {
                old.value = v;
                afterNodeAccess(old);
            } else
                removeNode(hash, key, null, false, true);
        } else if (v != null) {
            if (t != null)
                t.putTreeVal(this, tab, hash, key, v);
            else {
                tab[i] = newNode(hash, key, v, first);
                if (binCount >= TREEIFY_THRESHOLD - 1)
                    treeifyBin(tab, hash);
            }
            ++modCount;
            ++size;
            afterNodeInsertion(true);
        }
        return v;
    }

    @Override
    public V merge(K key, V value,
                   BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        if (value == null)
            throw new NullPointerException();
        if (remappingFunction == null)
            throw new NullPointerException();
        int hash = hash(key);
        Node<K, V>[] tab;
        Node<K, V> first;
        int n, i;
        int binCount = 0;
        TreeNode<K, V> t = null;
        Node<K, V> old = null;
        if (size > threshold || (tab = table) == null ||
                (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((first = tab[i = (n - 1) & hash]) != null) {
            if (first instanceof TreeNode)
                old = (t = (TreeNode<K, V>) first).getTreeNode(hash, key);
            else {
                Node<K, V> e = first;
                K k;
                do {
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k)))) {
                        old = e;
                        break;
                    }
                    ++binCount;
                } while ((e = e.next) != null);
            }
        }
        if (old != null) {
            V v;
            if (old.value != null)
                v = remappingFunction.apply(old.value, value);
            else
                v = value;
            if (v != null) {
                old.value = v;
                afterNodeAccess(old);
            } else
                removeNode(hash, key, null, false, true);
            return v;
        }
        if (value != null) {
            if (t != null)
                t.putTreeVal(this, tab, hash, key, value);
            else {
                tab[i] = newNode(hash, key, value, first);
                if (binCount >= TREEIFY_THRESHOLD - 1)
                    treeifyBin(tab, hash);
            }
            ++modCount;
            ++size;
            afterNodeInsertion(true);
        }
        return value;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        Node<K, V>[] tab;
        if (action == null)
            throw new NullPointerException();
        if (size > 0 && (tab = table) != null) {
            int mc = modCount;
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K, V> e = tab[i]; e != null; e = e.next)
                    action.accept(e.key, e.value);
            }
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Node<K, V>[] tab;
        if (function == null)
            throw new NullPointerException();
        if (size > 0 && (tab = table) != null) {
            int mc = modCount;
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K, V> e = tab[i]; e != null; e = e.next) {
                    e.value = function.apply(e.key, e.value);
                }
            }
            if (modCount != mc)
                throw new ConcurrentModificationException();
        }
    }

    /* ------------------------------------------------------------ */
    // Cloning and serialization

    /**
     * Returns a shallow copy of this <tt>HashMap</tt> instance: the keys and
     * values themselves are not cloned.
     *
     * @return a shallow copy of this map
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object clone() {
        HashMap<K, V> result;
        try {
            result = (HashMap<K, V>) super.clone();
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
        result.reinitialize();
        result.putMapEntries(this, false);
        return result;
    }

    // These methods are also used when serializing HashSets
    final float loadFactor() {
        return loadFactor;
    }

    final int capacity() {
        return (table != null) ? table.length :
                (threshold > 0) ? threshold :
                        DEFAULT_INITIAL_CAPACITY;
    }

    /**
     * Save the state of the <tt>HashMap</tt> instance to a stream (i.e.,
     * serialize it).
     *
     * @serialData The <i>capacity</i> of the HashMap (the length of the
     * bucket array) is emitted (int), followed by the
     * <i>size</i> (an int, the number of key-value
     * mappings), followed by the key (Object) and value (Object)
     * for each key-value mapping.  The key-value mappings are
     * emitted in no particular order.
     */
    private void writeObject(java.io.ObjectOutputStream s)
            throws IOException {
        int buckets = capacity();
        // Write out the threshold, loadfactor, and any hidden stuff
        s.defaultWriteObject();
        s.writeInt(buckets);
        s.writeInt(size);
        internalWriteEntries(s);
    }

    /**
     * Reconstitutes this map from a stream (that is, deserializes it).
     *
     * @param s the stream
     * @throws ClassNotFoundException if the class of a serialized object
     *                                could not be found
     * @throws IOException            if an I/O error occurs
     */
    private void readObject(java.io.ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        // Read in the threshold (ignored), loadfactor, and any hidden stuff
        s.defaultReadObject();
        reinitialize();
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new InvalidObjectException("Illegal load factor: " +
                    loadFactor);
        s.readInt();                // Read and ignore number of buckets
        int mappings = s.readInt(); // Read number of mappings (size)
        if (mappings < 0)
            throw new InvalidObjectException("Illegal mappings count: " +
                    mappings);
        else if (mappings > 0) { // (if zero, use defaults)
            // Size the table using given load factor only if within
            // range of 0.25...4.0
            float lf = Math.min(Math.max(0.25f, loadFactor), 4.0f);
            float fc = (float) mappings / lf + 1.0f;
            int cap = ((fc < DEFAULT_INITIAL_CAPACITY) ?
                    DEFAULT_INITIAL_CAPACITY :
                    (fc >= MAXIMUM_CAPACITY) ?
                            MAXIMUM_CAPACITY :
                            tableSizeFor((int) fc));
            float ft = (float) cap * lf;
            threshold = ((cap < MAXIMUM_CAPACITY && ft < MAXIMUM_CAPACITY) ?
                    (int) ft : Integer.MAX_VALUE);

            // Check Map.Entry[].class since it's the nearest public type to
            // what we're actually creating.
            SharedSecrets.getJavaOISAccess().checkArray(s, Map.Entry[].class, cap);
            @SuppressWarnings({"rawtypes", "unchecked"})
            Node<K, V>[] tab = (Node<K, V>[]) new Node[cap];
            table = tab;

            // Read the keys and values, and put the mappings in the HashMap
            for (int i = 0; i < mappings; i++) {
                @SuppressWarnings("unchecked")
                K key = (K) s.readObject();
                @SuppressWarnings("unchecked")
                V value = (V) s.readObject();
                putVal(hash(key), key, value, false, false);
            }
        }
    }

    /* ------------------------------------------------------------ */
    // iterators

    abstract class HashIterator {
        Node<K, V> next;        // next entry to return
        Node<K, V> current;     // current entry
        int expectedModCount;  // for fast-fail
        int index;             // current slot

        HashIterator() {
            expectedModCount = modCount;
            Node<K, V>[] t = table;
            current = next = null;
            index = 0;
            if (t != null && size > 0) { // advance to first entry
                do {
                } while (index < t.length && (next = t[index++]) == null);
            }
        }

        public final boolean hasNext() {
            return next != null;
        }

        final Node<K, V> nextNode() {
            Node<K, V>[] t;
            Node<K, V> e = next;
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            if (e == null)
                throw new NoSuchElementException();
            if ((next = (current = e).next) == null && (t = table) != null) {
                do {
                } while (index < t.length && (next = t[index++]) == null);
            }
            return e;
        }

        public final void remove() {
            Node<K, V> p = current;
            if (p == null)
                throw new IllegalStateException();
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
            current = null;
            K key = p.key;
            removeNode(hash(key), key, null, false, false);
            expectedModCount = modCount;
        }
    }

    final class KeyIterator extends HashIterator
            implements Iterator<K> {
        public final K next() {
            return nextNode().key;
        }
    }

    final class ValueIterator extends HashIterator
            implements Iterator<V> {
        public final V next() {
            return nextNode().value;
        }
    }

    final class EntryIterator extends HashIterator
            implements Iterator<Map.Entry<K, V>> {
        public final Map.Entry<K, V> next() {
            return nextNode();
        }
    }

    /* ------------------------------------------------------------ */
    // spliterators

    static class HashMapSpliterator<K, V> {
        final HashMap<K, V> map;
        Node<K, V> current;          // current node
        int index;                  // current index, modified on advance/split
        int fence;                  // one past last index
        int est;                    // size estimate
        int expectedModCount;       // for comodification checks

        HashMapSpliterator(HashMap<K, V> m, int origin,
                           int fence, int est,
                           int expectedModCount) {
            this.map = m;
            this.index = origin;
            this.fence = fence;
            this.est = est;
            this.expectedModCount = expectedModCount;
        }

        final int getFence() { // initialize fence and size on first use
            int hi;
            if ((hi = fence) < 0) {
                HashMap<K, V> m = map;
                est = m.size;
                expectedModCount = m.modCount;
                Node<K, V>[] tab = m.table;
                hi = fence = (tab == null) ? 0 : tab.length;
            }
            return hi;
        }

        public final long estimateSize() {
            getFence(); // force init
            return (long) est;
        }
    }

    static final class KeySpliterator<K, V>
            extends HashMapSpliterator<K, V>
            implements Spliterator<K> {
        KeySpliterator(HashMap<K, V> m, int origin, int fence, int est,
                       int expectedModCount) {
            super(m, origin, fence, est, expectedModCount);
        }

        public KeySpliterator<K, V> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid || current != null) ? null :
                    new KeySpliterator<>(map, lo, index = mid, est >>>= 1,
                            expectedModCount);
        }

        public void forEachRemaining(Consumer<? super K> action) {
            int i, hi, mc;
            if (action == null)
                throw new NullPointerException();
            HashMap<K, V> m = map;
            Node<K, V>[] tab = m.table;
            if ((hi = fence) < 0) {
                mc = expectedModCount = m.modCount;
                hi = fence = (tab == null) ? 0 : tab.length;
            } else
                mc = expectedModCount;
            if (tab != null && tab.length >= hi &&
                    (i = index) >= 0 && (i < (index = hi) || current != null)) {
                Node<K, V> p = current;
                current = null;
                do {
                    if (p == null)
                        p = tab[i++];
                    else {
                        action.accept(p.key);
                        p = p.next;
                    }
                } while (p != null || i < hi);
                if (m.modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }

        public boolean tryAdvance(Consumer<? super K> action) {
            int hi;
            if (action == null)
                throw new NullPointerException();
            Node<K, V>[] tab = map.table;
            if (tab != null && tab.length >= (hi = getFence()) && index >= 0) {
                while (current != null || index < hi) {
                    if (current == null)
                        current = tab[index++];
                    else {
                        K k = current.key;
                        current = current.next;
                        action.accept(k);
                        if (map.modCount != expectedModCount)
                            throw new ConcurrentModificationException();
                        return true;
                    }
                }
            }
            return false;
        }

        public int characteristics() {
            return (fence < 0 || est == map.size ? Spliterator.SIZED : 0) |
                    Spliterator.DISTINCT;
        }
    }

    static final class ValueSpliterator<K, V>
            extends HashMapSpliterator<K, V>
            implements Spliterator<V> {
        ValueSpliterator(HashMap<K, V> m, int origin, int fence, int est,
                         int expectedModCount) {
            super(m, origin, fence, est, expectedModCount);
        }

        public ValueSpliterator<K, V> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid || current != null) ? null :
                    new ValueSpliterator<>(map, lo, index = mid, est >>>= 1,
                            expectedModCount);
        }

        public void forEachRemaining(Consumer<? super V> action) {
            int i, hi, mc;
            if (action == null)
                throw new NullPointerException();
            HashMap<K, V> m = map;
            Node<K, V>[] tab = m.table;
            if ((hi = fence) < 0) {
                mc = expectedModCount = m.modCount;
                hi = fence = (tab == null) ? 0 : tab.length;
            } else
                mc = expectedModCount;
            if (tab != null && tab.length >= hi &&
                    (i = index) >= 0 && (i < (index = hi) || current != null)) {
                Node<K, V> p = current;
                current = null;
                do {
                    if (p == null)
                        p = tab[i++];
                    else {
                        action.accept(p.value);
                        p = p.next;
                    }
                } while (p != null || i < hi);
                if (m.modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }

        public boolean tryAdvance(Consumer<? super V> action) {
            int hi;
            if (action == null)
                throw new NullPointerException();
            Node<K, V>[] tab = map.table;
            if (tab != null && tab.length >= (hi = getFence()) && index >= 0) {
                while (current != null || index < hi) {
                    if (current == null)
                        current = tab[index++];
                    else {
                        V v = current.value;
                        current = current.next;
                        action.accept(v);
                        if (map.modCount != expectedModCount)
                            throw new ConcurrentModificationException();
                        return true;
                    }
                }
            }
            return false;
        }

        public int characteristics() {
            return (fence < 0 || est == map.size ? Spliterator.SIZED : 0);
        }
    }

    static final class EntrySpliterator<K, V>
            extends HashMapSpliterator<K, V>
            implements Spliterator<Map.Entry<K, V>> {
        EntrySpliterator(HashMap<K, V> m, int origin, int fence, int est,
                         int expectedModCount) {
            super(m, origin, fence, est, expectedModCount);
        }

        public EntrySpliterator<K, V> trySplit() {
            int hi = getFence(), lo = index, mid = (lo + hi) >>> 1;
            return (lo >= mid || current != null) ? null :
                    new EntrySpliterator<>(map, lo, index = mid, est >>>= 1,
                            expectedModCount);
        }

        public void forEachRemaining(Consumer<? super Map.Entry<K, V>> action) {
            int i, hi, mc;
            if (action == null)
                throw new NullPointerException();
            HashMap<K, V> m = map;
            Node<K, V>[] tab = m.table;
            if ((hi = fence) < 0) {
                mc = expectedModCount = m.modCount;
                hi = fence = (tab == null) ? 0 : tab.length;
            } else
                mc = expectedModCount;
            if (tab != null && tab.length >= hi &&
                    (i = index) >= 0 && (i < (index = hi) || current != null)) {
                Node<K, V> p = current;
                current = null;
                do {
                    if (p == null)
                        p = tab[i++];
                    else {
                        action.accept(p);
                        p = p.next;
                    }
                } while (p != null || i < hi);
                if (m.modCount != mc)
                    throw new ConcurrentModificationException();
            }
        }

        public boolean tryAdvance(Consumer<? super Map.Entry<K, V>> action) {
            int hi;
            if (action == null)
                throw new NullPointerException();
            Node<K, V>[] tab = map.table;
            if (tab != null && tab.length >= (hi = getFence()) && index >= 0) {
                while (current != null || index < hi) {
                    if (current == null)
                        current = tab[index++];
                    else {
                        Node<K, V> e = current;
                        current = current.next;
                        action.accept(e);
                        if (map.modCount != expectedModCount)
                            throw new ConcurrentModificationException();
                        return true;
                    }
                }
            }
            return false;
        }

        public int characteristics() {
            return (fence < 0 || est == map.size ? Spliterator.SIZED : 0) |
                    Spliterator.DISTINCT;
        }
    }

    /* ------------------------------------------------------------ */
    // LinkedHashMap support


    /*
     * The following package-protected methods are designed to be
     * overridden by LinkedHashMap, but not by any other subclass.
     * Nearly all other internal methods are also package-protected
     * but are declared final, so can be used by LinkedHashMap, view
     * classes, and HashSet.
     */

    // Create a regular (non-tree) node
    Node<K, V> newNode(int hash, K key, V value, Node<K, V> next) {
        return new Node<>(hash, key, value, next);
    }

    // For conversion from TreeNodes to plain nodes
    Node<K, V> replacementNode(Node<K, V> p, Node<K, V> next) {
        return new Node<>(p.hash, p.key, p.value, next);
    }

    // Create a tree bin node
    TreeNode<K, V> newTreeNode(int hash, K key, V value, Node<K, V> next) {
        return new TreeNode<>(hash, key, value, next);
    }

    // For treeifyBin
    TreeNode<K, V> replacementTreeNode(Node<K, V> p, Node<K, V> next) {
        return new TreeNode<>(p.hash, p.key, p.value, next);
    }

    /**
     * Reset to initial default state.  Called by clone and readObject.
     */
    void reinitialize() {
        table = null;
        entrySet = null;
        keySet = null;
        values = null;
        modCount = 0;
        threshold = 0;
        size = 0;
    }

    // Callbacks to allow LinkedHashMap post-actions
    void afterNodeAccess(Node<K, V> p) {
    }

    void afterNodeInsertion(boolean evict) {
    }

    void afterNodeRemoval(Node<K, V> p) {
    }

    // Called only from writeObject, to ensure compatible ordering.
    void internalWriteEntries(java.io.ObjectOutputStream s) throws IOException {
        Node<K, V>[] tab;
        if (size > 0 && (tab = table) != null) {
            for (int i = 0; i < tab.length; ++i) {
                for (Node<K, V> e = tab[i]; e != null; e = e.next) {
                    s.writeObject(e.key);
                    s.writeObject(e.value);
                }
            }
        }
    }

    /* ------------------------------------------------------------ */
    // Tree bins

    /**
     * Entry for Tree bins. Extends LinkedHashMap.Entry (which in turn
     * extends Node) so can be used as extension of either regular or
     * linked node.
     */
    static final class TreeNode<K, V> extends LinkedHashMap.Entry<K, V> {
        TreeNode<K, V> parent;  // red-black tree links
        TreeNode<K, V> left;
        TreeNode<K, V> right;
        TreeNode<K, V> prev;    // needed to unlink next upon deletion
        boolean red;

        TreeNode(int hash, K key, V val, Node<K, V> next) {
            super(hash, key, val, next);
        }

        /**
         * Returns root of tree containing this node.
         */
        final TreeNode<K, V> root() {
            for (TreeNode<K, V> r = this, p; ; ) {
                if ((p = r.parent) == null)
                    return r;
                r = p;
            }
        }

        /**
         * 把建好树的root节点，移动到tab中分支的头节点（这些hash值都是相同的）
         * <p>
         * Ensures that the given root is the first node of its bin.
         */
        static <K, V> void moveRootToFront(Node<K, V>[] tab, TreeNode<K, V> root) {

            /**
             * 假设root所在的链表中在tab中3处位置（root本身是一个红黑树，已经包含了a,b,c三个节点）
             * tab[3] = a -> b -> root -> c
             * 那么，需要调整成
             * tab[3] = root -> a ->b -> c
             * 需要将b和c建立链式关系，root和a建立链式关系
             *
             * 以下操作，主要实现该目的
             */

            int n;
            if (root != null && tab != null && (n = tab.length) > 0) {

                // 确定整棵树在tab中的位置
                int index = (n - 1) & root.hash;

                // 取出节点
                TreeNode<K, V> first = (TreeNode<K, V>) tab[index];

                // 树的root节点和tab中树的第一个节点不同
                if (root != first) {
                    Node<K, V> rn;
                    tab[index] = root;

                    TreeNode<K, V> rp = root.prev;

                    /**
                     * root从链表中脱离出来，则需要把root.prev和root.next建立联系
                     */

                    // root存在next节点，把rn赋给rp节点上
                    if ((rn = root.next) != null) {
                        ((TreeNode<K, V>) rn).prev = rp;
                    }

                    // 如果rp节点存在，那么让rp.next 指向rn，root从链表中脱离
                    if (rp != null) {
                        rp.next = rn;
                    }

                    if (first != null) {
                        first.prev = root;
                    }

                    // root成为头结点
                    root.next = first;
                    root.prev = null;

                }
                assert checkInvariants(root);
            }
        }

        /**
         * 这个方法是TreeNode类的一个实例方法，调用该方法的也就是一个TreeNode对象，
         * 该对象就是树上的某个节点，以该节点作为根节点，查找其所有子孙节点，
         * 看看哪个节点能够匹配上给定的键对象
         * h k的hash值
         * k 要查找的对象
         * kc k的Class对象，该Class应该是实现了Comparable<K>的，否则应该是null，参见：
         *
         * https://blog.csdn.net/weixin_42340670/article/details/80753826
         */
        /**
         * Finds the node starting at root p with the given hash and key.
         * The kc argument caches comparableClassFor(key) upon first use
         * comparing keys.
         *
         * @param h  新key的hash值
         * @param k  新key的值
         * @param kc
         */
        final TreeNode<K, V> find(int h, Object k, Class<?> kc) {
            // 当前node节点赋值给p
            TreeNode<K, V> p = this;
            do {
                // ph: 当前节点的hash值
                // dir: 用来决定分支
                int ph, dir;
                K pk;

                // q : 定义找到的对象
                TreeNode<K, V> pl = p.left, pr = p.right, q;

                //  如果当前节点的hash值大于k得hash值h，那么后续就应该让k和左孩子节点进行下一轮比较
                if ((ph = p.hash) > h) {
                    // 指向左孩子节点
                    p = pl;
                }
                // 如果当前节点的hash值小于k得hash值h，那么后续就应该让k和右孩子节点进行下一轮比较
                else if (ph < h) {
                    p = pr;
                }

                // 如果h和当前节点的hash值相同，并且当前节点的键对象pk和k相等（地址相同或者equals相同）
                else if ((pk = p.key) == k || (k != null && k.equals(pk))) {
                    // 返回节点，意味着找到了节点
                    return p;
                }
                // 如果左孩子为空（hash值相同，key值不同）
                else if (pl == null) {
                    // p指向右孩子，紧接着就是下一轮循环了
                    p = pr;
                } else if (pr == null) {
                    // p指向左孩子，紧接着就是下一轮循环了
                    p = pl;
                }

                // 如果左右孩子都不为空，那么需要再进行一轮对比来确定到底该往哪个方向去深入对比
                // 这一轮的对比主要是想通过comparable方法来比较pk和k的大小（hash相同，key值不同，左右孩子节点都不为空）
                else if ((kc != null || (kc = comparableClassFor(k)) != null)
                        && (dir = compareComparables(kc, k, pk)) != 0) {
                    // dir小于0，p指向左孩子，否则指向右孩子。紧接着就是下一轮循环了
                    p = (dir < 0) ? pl : pr;
                }

                // 执行到这里说明无法通过comparable比较  或者 比较之后还是相等
                // 从右孩子节点递归循环查找，如果找到了匹配的则返回
                else if ((q = pr.find(h, k, kc)) != null) {
                    // 递归调用
                    return q;
                } else {
                    // 如果从右孩子节点递归查找后仍未找到，那么从左孩子节点进行下一轮循环
                    p = pl;
                }
            } while (p != null);

            // 未找到匹配的节点返回null
            return null;
        }

        /**
         * Calls find for root node.
         */
        final TreeNode<K, V> getTreeNode(int h, Object k) {
            // 父节点不为空，就找root节点 否则 以自身作为root查找
            return ((parent != null) ? root() : this).find(h, k, null);
        }

        /**
         * Tie-breaking utility for ordering insertions when equal
         * hashCodes and non-comparable. We don't require a total
         * order, just a consistent insertion rule to maintain
         * equivalence across rebalancings. Tie-breaking further than
         * necessary simplifies testing a bit.
         *
         * @param a 新key
         * @param b 搜索到的节点key
         */
        static int tieBreakOrder(Object a, Object b) {
            int d;
            if (a == null
                    || b == null
                    || (d = a.getClass().getName().compareTo(b.getClass().getName())) == 0) {

                d = (System.identityHashCode(a) <= System.identityHashCode(b) ? -1 : 1);

            }
            return d;
        }

        /**
         * 链表-->树
         * <p>
         * Forms tree of the nodes linked from this node.
         */
        final void treeify(Node<K, V>[] tab) {
            TreeNode<K, V> root = null;

            for (TreeNode<K, V> x = this, next; x != null; x = next) {
                next = (TreeNode<K, V>) x.next;

                x.left = x.right = null;

                // 初始化root节点
                if (root == null) {
                    x.parent = null;
                    x.red = false;
                    root = x;
                } else {

                    K k = x.key;
                    int h = x.hash;
                    Class<?> kc = null;

                    // 遍历，确定以后的节点在tree中的位置
                    for (TreeNode<K, V> p = root; ; ) {
                        /**
                         * dir > 0 左子
                         * dir < 0 右子
                         * hash相同时，使用比较器比较key值
                         */
                        int dir, ph;
                        K pk = p.key;
                        if ((ph = p.hash) > h)
                            dir = -1;
                        else if (ph < h)
                            dir = 1;
                        else if ((kc == null &&
                                (kc = comparableClassFor(k)) == null) ||
                                (dir = compareComparables(kc, k, pk)) == 0)
                            dir = tieBreakOrder(k, pk);

                        TreeNode<K, V> xp = p;

                        // 子树上没有节点，则直接放进去，完成建树，然后平衡树
                        if ((p = (dir <= 0) ? p.left : p.right) == null) {
                            x.parent = xp;
                            if (dir <= 0)
                                xp.left = x;
                            else
                                xp.right = x;
                            root = balanceInsertion(root, x);
                            break;
                        }
                    }
                }
            }

            moveRootToFront(tab, root);
        }

        /**
         * 反树化，即树变链表操作
         * <p>
         * Returns a list of non-TreeNodes replacing those linked from
         * this node.
         */
        final Node<K, V> untreeify(HashMap<K, V> map) {

            /**
             * hd ： 意义类似于链表头结点
             * tl ： 临时变量，总是指向下一个节点
             */
            Node<K, V> hd = null, tl = null;

            /**
             * 循环完成后，treeNode将变成一个链表
             */
            for (Node<K, V> q = this; q != null; q = q.next) {

                // replacementNode 是实例函数，必须传入map引用
                // 该方法就是创建一个node节点，其next引用为null
                Node<K, V> p = map.replacementNode(q, null);
                if (tl == null) {
                    hd = p;
                } else {
                    tl.next = p;
                }
                tl = p;
            }
            return hd;
        }

        /**
         * Tree version of putVal.
         *
         * @param map
         * @param tab
         * @param h   新key的hash
         * @param k   新key
         * @param v   新key的value
         * @return TreeNode
         */
        final TreeNode<K, V> putTreeVal(HashMap<K, V> map, Node<K, V>[] tab, int h, K k, V v) {
            Class<?> kc = null;
            boolean searched = false;

            // 找到根节点
            TreeNode<K, V> root = (parent != null) ? root() : this;


            for (TreeNode<K, V> p = root; ; ) {
                int dir, ph;
                K pk;

                /**
                 * 四种情况：
                 *   1. 旧节点的hash > 新节点hash，带有赋值ph操作 左孩子节点
                 *   2. 旧节点的hash < 新节点hash，右孩子结点
                 *   3. 两者hash相同，且key值相同，直接返回，在外围进行值覆盖操作
                 *   4. hash相同，但是key值不同，则比较key值
                 */
                if ((ph = p.hash) > h) {
                    dir = -1;
                } else if (ph < h) {
                    dir = 1;
                } else if ((pk = p.key) == k || (k != null && k.equals(pk))) {
                    // 原有节点的hash和新节点hash相同，且值相同，则直接返回，在外层方法对key值更新
                    return p;
                } else if ((kc == null && (kc = comparableClassFor(k)) == null)
                        || (dir = compareComparables(kc, k, pk)) == 0) {
                    // 走到这里说明：指定key没有实现comparable接口   或者   实现了comparable接口并且和当前
                    // 节点的键对象比较之后相等（仅限第一次循环）

                    if (!searched) {
                        TreeNode<K, V> q, ch;
                        // 下次便不会走这个分支，在当前节点上搜索一次，如果没有搜索到
                        searched = true;
                        if (((ch = p.left) != null && (q = ch.find(h, k, kc)) != null)
                                || ((ch = p.right) != null && (q = ch.find(h, k, kc)) != null)) {
                            return q;
                        }
                    }


                    dir = tieBreakOrder(k, pk);
                }

                TreeNode<K, V> xp = p;

                // <=0，在左孩子节点
                // >0，在右孩子节点
                // 如果p的左孩子节点或者右孩子节点 为null，可以把node插入树中
                if ((p = (dir <= 0) ? p.left : p.right) == null) {
                    Node<K, V> xpn = xp.next;

                    // 构造一个新的节点
                    TreeNode<K, V> x = map.newTreeNode(h, k, v, xpn);

                    if (dir <= 0) {
                        xp.left = x;
                    } else {
                        xp.right = x;
                    }

                    xp.next = x;

                    // x的父节点和前置节点都是xp
                    x.parent = x.prev = xp;

                    // 原来存在next节点，则把next节点和新节点做链表
                    if (xpn != null) {
                        ((TreeNode<K, V>) xpn).prev = x;
                    }

                    moveRootToFront(tab, balanceInsertion(root, x));

                    return null;
                }
            }

        }

        /**
         * Removes the given node, that must be present before this call.
         * This is messier than typical red-black deletion code because we
         * cannot swap the contents of an interior node with a leaf
         * successor that is pinned by "next" pointers that are accessible
         * independently during traversal. So instead we swap the tree
         * linkages. If the current tree appears to have too few nodes,
         * the bin is converted back to a plain bin. (The test triggers
         * somewhere between 2 and 6 nodes, depending on tree structure).
         */
        final void removeTreeNode(HashMap<K, V> map, Node<K, V>[] tab, boolean movable) {
            int n;
            if (tab == null || (n = tab.length) == 0)
                return;
            int index = (n - 1) & hash;
            TreeNode<K, V> first = (TreeNode<K, V>) tab[index], root = first, rl;
            TreeNode<K, V> succ = (TreeNode<K, V>) next, pred = prev;
            if (pred == null)
                tab[index] = first = succ;
            else
                pred.next = succ;
            if (succ != null)
                succ.prev = pred;
            if (first == null)
                return;
            if (root.parent != null)
                root = root.root();
            if (root == null
                    || (movable
                    && (root.right == null
                    || (rl = root.left) == null
                    || rl.left == null))) {
                tab[index] = first.untreeify(map);  // too small
                return;
            }
            TreeNode<K, V> p = this, pl = left, pr = right, replacement;
            if (pl != null && pr != null) {
                TreeNode<K, V> s = pr, sl;
                while ((sl = s.left) != null) // find successor
                    s = sl;
                boolean c = s.red;
                s.red = p.red;
                p.red = c; // swap colors
                TreeNode<K, V> sr = s.right;
                TreeNode<K, V> pp = p.parent;
                if (s == pr) { // p was s's direct parent
                    p.parent = s;
                    s.right = p;
                } else {
                    TreeNode<K, V> sp = s.parent;
                    if ((p.parent = sp) != null) {
                        if (s == sp.left)
                            sp.left = p;
                        else
                            sp.right = p;
                    }
                    if ((s.right = pr) != null)
                        pr.parent = s;
                }
                p.left = null;
                if ((p.right = sr) != null)
                    sr.parent = p;
                if ((s.left = pl) != null)
                    pl.parent = s;
                if ((s.parent = pp) == null)
                    root = s;
                else if (p == pp.left)
                    pp.left = s;
                else
                    pp.right = s;
                if (sr != null)
                    replacement = sr;
                else
                    replacement = p;
            } else if (pl != null)
                replacement = pl;
            else if (pr != null)
                replacement = pr;
            else
                replacement = p;
            if (replacement != p) {
                TreeNode<K, V> pp = replacement.parent = p.parent;
                if (pp == null)
                    root = replacement;
                else if (p == pp.left)
                    pp.left = replacement;
                else
                    pp.right = replacement;
                p.left = p.right = p.parent = null;
            }

            TreeNode<K, V> r = p.red ? root : balanceDeletion(root, replacement);

            if (replacement == p) {  // detach
                TreeNode<K, V> pp = p.parent;
                p.parent = null;
                if (pp != null) {
                    if (p == pp.left)
                        pp.left = null;
                    else if (p == pp.right)
                        pp.right = null;
                }
            }
            if (movable)
                moveRootToFront(tab, r);
        }

        /**
         * Splits nodes in a tree bin into lower and upper tree bins,
         * or untreeifies if now too small. Called only from resize;
         * see above discussion about split bits and indices.
         *
         * @param map   the map 主要作为引用使用，内部有些方法是实例方法
         * @param tab   the table for recording bin heads 新数据表
         * @param index the index of the table being split 当前元素在旧表中的位置
         * @param bit   the bit of hash to split on 老的数据表的长度
         */
        final void split(HashMap<K, V> map, Node<K, V>[] tab, int index, int bit) {
            // this : treeNode，当前节点是TreeNode时，在该节点上调用split方法指代当前节点对象
            TreeNode<K, V> b = this;

            // Relink into lo and hi lists, preserving order
            TreeNode<K, V> loHead = null, loTail = null;
            TreeNode<K, V> hiHead = null, hiTail = null;
            int lc = 0, hc = 0;

            /**
             * 遍历当前树节点，将hash不冲突的节点放到数据表中，hash冲突的，继续留在树内（不从树内删除么？）
             *  e.hash和原有长度进行与操作（表扩充后）
             *   1. 如果结果为0，那么该节点没有高位，全在低位，则位置不变
             *   2. 如果结果不为0，那么该节点有高位，则其在新的表中的位置为index+bit
             */

            // 以下讲解，在resize方法内部也有说明
            // 原表长度：16，即bit
            // case 1，一个节点的hash值为0000 1100，其在原表中的位置
            //    0000 1100   hash
            //  & 0000 1111   bit-1
            //  = 0000 1100  -> 12

            // tab扩充一倍后，长度成为32，其在新表中的位置还是12
            //    0000 1100
            //  & 0001 1111
            //  = 0000 1100  -> 12

            //    0000 1100  hash
            //  & 0001 0000  bit
            //  = 0000 0000  -> 0  等于0，不需要变化位置

            // case 2, 一个节点的hash值为0001 1100，其在原表中的位置
            //    0001 1100   hash
            //  & 0000 1111   bit-1
            //  = 0000 1100  -> 12  （出现了hash冲突）

            // tab扩充一倍后，长度成为32，其在新表中的位置：28
            //    0001 1100
            //  & 0001 1111
            //  = 0001 1100  -> 28  （该节点需要重新定位）

            //    0001 1100  hash
            //  & 0001 0000  bit
            //  = 0001 0000  -> 16  大于0，需要重新定位

            // case 3, 一个节点的hash值为0011 1100，其在原表中的位置
            //    0011 1100   hash
            //  & 0000 1111   bit-1
            //  = 0000 1100  -> 12  （出现了hash冲突，同时和case2，case1情况冲突）

            // tab扩充一倍后，长度成为32，其在新表中的位置：28
            //    0011 1100
            //  & 0001 1111
            //  = 0001 1100  -> 28  （该节点需要重新定位，由12变成28，但是在新表中和情况2依旧发生冲突，需要组成同一课树）

            //    0011 1100  hash
            //  & 0001 0000  bit
            //  = 0001 0000  -> 16  大于0，需要重新定位


            for (TreeNode<K, V> e = b, next; e != null; e = next) {
                next = (TreeNode<K, V>) e.next;

                // 释放原有元素的next引用
                e.next = null;


                if ((e.hash & bit) == 0) {
                    // 存在一组元素：a -> b -> c -> d,其中a是一个TreeNode对象
                    // 1. e = a;
                    //   a.prev = null;
                    //   loHead = a;  (初始节点)
                    //   loTail = a;  ++lc;

                    // 2. e = b;
                    //   b.prev = a;
                    //   a.next = b;
                    //   loTail = b;

                    // 3. e = c;
                    //   c.prev = b;
                    //   b.next = c;
                    //   loTail = c;

                    // 4. e = d;
                    //   d.prev = c;
                    //   c.next = d;
                    //   loTail = d;


                    if ((e.prev = loTail) == null) {
                        loHead = e;
                    } else {
                        loTail.next = e;
                    }

                    // loTail指向上个元素
                    loTail = e;
                    ++lc;

                } else {
                    // 存在一组元素：a -> b -> c -> d,其中a是一个TreeNode对象
                    // 1. e = a;
                    //   a.prev = null;
                    //   hiHead = a;  (初始节点)
                    //   hiTail = a;  ++lc;

                    // 2. e = b;
                    //   b.prev = a;
                    //   a.next = b;
                    //   hiTail = b;

                    // 3. e = c;
                    //   c.prev = b;
                    //   b.next = c;
                    //   hiTail = c;

                    // 4. e = d;
                    //   d.prev = c;
                    //   c.next = d;
                    //   hiTail = d;

                    if ((e.prev = hiTail) == null) {
                        hiHead = e;
                    } else {
                        hiTail.next = e;
                    }
                    hiTail = e;
                    ++hc;
                }
            }

            if (loHead != null) {

                // 未超过6个，使用链表存储
                if (lc <= UNTREEIFY_THRESHOLD) {
                    // 反树化，转为一个链表存储
                    tab[index] = loHead.untreeify(map);
                } else {
                    tab[index] = loHead;
                    if (hiHead != null) {
                        // (else is already treeified)
                        loHead.treeify(tab);
                    }
                }
            }

            if (hiHead != null) {
                if (hc <= UNTREEIFY_THRESHOLD) {
                    tab[index + bit] = hiHead.untreeify(map);
                } else {
                    tab[index + bit] = hiHead;
                    if (loHead != null) {
                        hiHead.treeify(tab);
                    }
                }
            }
        }

        /* ------------------------------------------------------------ */
        // Red-black tree methods, all adapted from CLR

        /**
         * 左旋
         *
         * @param root
         * @param p
         * @param <K>
         * @param <V>
         * @return
         */
        static <K, V> TreeNode<K, V> rotateLeft(TreeNode<K, V> root, TreeNode<K, V> p) {
            /**
             * 几个大点要注意：以p为中心，进行左旋
             *
             *  1. p的右孩子的左孩子，左旋后，要成为p的右孩子
             *  2. p的右孩子(p.right)成为新的子根，p成为原来p.right的左孩子
             *  3. 左旋后，p.right的父节点，指向原p.parent
             */

            /*
             * r : p.right
             * pp: p.parent
             * rl: p.right.left
             */
            TreeNode<K, V> r, pp, rl;
            // 节点存在 且 右孩子存在（进行左旋的必要条件），同时，进行r的初始化（r=p.right),即左旋后的新的子根
            if (p != null && (r = p.right) != null) {

                // 将p.right 指向 p.right.left，同时定义节点rl（后面左父节点关联使用）
                if ((rl = p.right = r.left) != null) {
                    rl.parent = p;
                }

                /*
                 * 1. 如果p的父节点不存在，那么r.parent将会时null，则r成为黑色，否则，r的父节点指向原p的父节点
                 * 2. 如果p是其父节点的左孩子，那么pp的左孩子更新成r
                 * 3. 如果p是其父节点的右孩子，那么pp的右孩子更新成r
                 */
                if ((pp = r.parent = p.parent) == null) {
                    (root = r).red = false;
                } else if (pp.left == p) {
                    pp.left = r;
                } else {
                    pp.right = r;
                }
                // r成为了新的子根，p成为其左孩子
                r.left = p;
                p.parent = r;
            }
            return root;
        }

        /**
         * 右旋操作
         *
         * @param root
         * @param p
         * @param <K>
         * @param <V>
         * @return
         */
        static <K, V> TreeNode<K, V> rotateRight(TreeNode<K, V> root, TreeNode<K, V> p) {
            /**
             * l : p.left
             * pp: p.parent
             * lr: p.left.right
             *
             * 1. p存在，且p的左孩子存在
             *   1.1 定义lr节点，和p.left同时指向原p.left.right节点，如果节点存在
             *      lr节点，成为p的孩子
             *   1.2 定义pp节点，和l.parent同时指向原p.parent
             *      如果节点不存在，则l的颜色改成黑色，直接返回，右旋完成
             *      如果存在，且p是父节点的右孩子，则pp的右孩子更新成l
             *      如果存在，且p是父节点的左孩子，则pp的左孩子更新成l
             *
             *   l.right指向p，成为新的子根
             *   p.parent指向l
             */
            TreeNode<K, V> l, pp, lr;
            if (p != null && (l = p.left) != null) {
                if ((lr = p.left = l.right) != null)
                    lr.parent = p;

                if ((pp = l.parent = p.parent) == null) {
                    (root = l).red = false;
                } else if (pp.right == p) {
                    pp.right = l;
                } else {
                    pp.left = l;
                }

                l.right = p;
                p.parent = l;
            }
            return root;
        }

        @SuppressWarnings("all")
        static <K, V> TreeNode<K, V> balanceInsertion(TreeNode<K, V> root, TreeNode<K, V> x) {

            x.red = true;

            /**
             * xp：x的parent节点
             * xpp：x的祖父节点
             * xppl:x的祖父节点的左孩子
             * xppr:x的祖父节点的右孩子
             */
            for (TreeNode<K, V> xp, xpp, xppl, xppr; ; ) {

                /**
                 * 1. 如果x的父节点不存在，则x就是树的根，直接返回节点x
                 * 2. 如果x的父节点不是红色（即黑色），或者x的祖父节点不存在（x的父节点红色），则
                 * 无需调整（x红色，父亲黑色），直接返回root树
                 */
                if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                } else if (!xp.red || (xpp = xp.parent) == null) {
                    return root;
                }

                /**
                 * 1. 父节点是祖父节点的左孩子
                 *    1.1 存在叔叔节点（祖父节点的右孩子），且节点颜色是红色
                 *        叔叔节点改成黑色
                 *        父节点改成黑色
                 *        祖父节点改成红色
                 *    1.2 不存在叔叔节点，或者叔叔节点颜色是黑色
                 *        x是父节点的右孩子
                 *           以xp为点执行左旋，同时：x指向xp，即原x的父节点
                 *           此时，xp指向新的x的父节点（主要是新的x），xpp指向xp的父节点（这些节点都必须存在）
                 *        如果存在xp节点（新的）: 如果x,xp,xpp都存在，则三者处于同一侧，且是左子关系，以xpp右旋
                 *           则xp节点颜色成为黑色
                 *           如果xpp节点存在，则xpp节点颜色改成红色，以xpp节点进行右旋
                 *
                 * 2. 父节点是祖父节点的右孩子
                 *    2.1 如果祖父节点的左孩子存在（xppl）且xppl的颜色是红色
                 *        xppl改成黑色
                 *        xp（父节点）改成黑色
                 *        xpp（祖父节点）改成红色
                 *        x指向xpp，递归下一次算法
                 *    2.2 如果xppl不存在或者xppl存在，但是颜色是黑色
                 *        如果x节点是父节点的左孩子
                 *           那么x指向xp，做一次右旋
                 *           xp更新成新的x的父节点（原xpp），xpp更新成xp的父节点（原xppp，假设说法）
                 *        如果xp存在：如果x,xp,xpp同时存在，则三者处于同一侧，且是右子关系，以xpp执行左旋
                 *           xp的颜色改成黑色
                 *           如果xpp存在，xpp的颜色改成红色，以xpp节点执行左旋
                 */
                if (xp == (xppl = xpp.left)) {
                    // 存在叔叔节点，且颜色是红色
                    if ((xppr = xpp.right) != null && xppr.red) {
                        xppr.red = false;   // 叔叔节点改成黑色
                        xp.red = false;     // 父节点改成黑色
                        xpp.red = true;     // 祖父节点改成红色
                        x = xpp;            // x指向祖父节点，从新算法计算
                    } else {
                        /**
                         * 叔叔节点不存在，或者叔叔节点不是红色
                         */

                        // x是父节点的you右孩子
                        if (x == xp.right) {
                            // 以父节点进行左旋
                            // 左旋后，旧的xp成为旧的x的左子，旧的x成为旧的xpp的左子，下一次遍历时需要以xpp右旋
                            root = rotateLeft(root, x = xp);

                            // 上一步，x已经指向xp，那么，xp要随机更新（即，指向原x的xpp）
                            // 这一步的要点：将xp和xpp同步向上一级，因为x已经更新成xp
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }

                        // 若同时存在x,xp,xpp三个节点，则都是左子，
                        // 此时，新的x已经成为新的xp的左子，执行右旋，
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateRight(root, xpp);
                            }
                        }
                    }
                } else {
                    // 父节点是祖父节点的右孩子

                    if (xppl != null && xppl.red) {
                        xppl.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    } else {

                        if (x == xp.left) {
                            root = rotateRight(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }

                        // 此时，新的x成为新的p的右子，执行左旋
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateLeft(root, xpp);
                            }
                        }
                    }
                }
            }
        }

        static <K, V> TreeNode<K, V> balanceDeletion(TreeNode<K, V> root,
                                                     TreeNode<K, V> x) {
            for (TreeNode<K, V> xp, xpl, xpr; ; ) {
                if (x == null || x == root)
                    return root;
                else if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                } else if (x.red) {
                    x.red = false;
                    return root;
                } else if ((xpl = xp.left) == x) {
                    if ((xpr = xp.right) != null && xpr.red) {
                        xpr.red = false;
                        xp.red = true;
                        root = rotateLeft(root, xp);
                        xpr = (xp = x.parent) == null ? null : xp.right;
                    }
                    if (xpr == null)
                        x = xp;
                    else {
                        TreeNode<K, V> sl = xpr.left, sr = xpr.right;
                        if ((sr == null || !sr.red) &&
                                (sl == null || !sl.red)) {
                            xpr.red = true;
                            x = xp;
                        } else {
                            if (sr == null || !sr.red) {
                                if (sl != null)
                                    sl.red = false;
                                xpr.red = true;
                                root = rotateRight(root, xpr);
                                xpr = (xp = x.parent) == null ?
                                        null : xp.right;
                            }
                            if (xpr != null) {
                                xpr.red = (xp == null) ? false : xp.red;
                                if ((sr = xpr.right) != null)
                                    sr.red = false;
                            }
                            if (xp != null) {
                                xp.red = false;
                                root = rotateLeft(root, xp);
                            }
                            x = root;
                        }
                    }
                } else { // symmetric
                    if (xpl != null && xpl.red) {
                        xpl.red = false;
                        xp.red = true;
                        root = rotateRight(root, xp);
                        xpl = (xp = x.parent) == null ? null : xp.left;
                    }
                    if (xpl == null)
                        x = xp;
                    else {
                        TreeNode<K, V> sl = xpl.left, sr = xpl.right;
                        if ((sl == null || !sl.red) &&
                                (sr == null || !sr.red)) {
                            xpl.red = true;
                            x = xp;
                        } else {
                            if (sl == null || !sl.red) {
                                if (sr != null)
                                    sr.red = false;
                                xpl.red = true;
                                root = rotateLeft(root, xpl);
                                xpl = (xp = x.parent) == null ?
                                        null : xp.left;
                            }
                            if (xpl != null) {
                                xpl.red = (xp == null) ? false : xp.red;
                                if ((sl = xpl.left) != null)
                                    sl.red = false;
                            }
                            if (xp != null) {
                                xp.red = false;
                                root = rotateRight(root, xp);
                            }
                            x = root;
                        }
                    }
                }
            }
        }

        /**
         * Recursive invariant check
         */
        static <K, V> boolean checkInvariants(TreeNode<K, V> t) {
            TreeNode<K, V> tp = t.parent, tl = t.left, tr = t.right,
                    tb = t.prev, tn = (TreeNode<K, V>) t.next;
            if (tb != null && tb.next != t)
                return false;
            if (tn != null && tn.prev != t)
                return false;
            if (tp != null && t != tp.left && t != tp.right)
                return false;
            if (tl != null && (tl.parent != t || tl.hash > t.hash))
                return false;
            if (tr != null && (tr.parent != t || tr.hash < t.hash))
                return false;
            if (t.red && tl != null && tl.red && tr != null && tr.red)
                return false;
            if (tl != null && !checkInvariants(tl))
                return false;
            if (tr != null && !checkInvariants(tr))
                return false;
            return true;
        }
    }

}
