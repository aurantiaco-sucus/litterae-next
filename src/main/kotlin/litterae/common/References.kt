package litterae.common

object References {
    /**
     * Acquire a weak immutable reference to object at a specified index.
     *
     * Note that this reference have similar behaviours as pointers in C/C++ that it neither checks the correctness
     * of index nor ensures the continuous availability of target object.
     * @param index the index of object in the list
     * @return an immutable reference to object of specified index
     */
    fun <T> List<T>.weakReferenceOf(index: Int) = object : Reference<T> {
        override val value: T get() = this@weakReferenceOf[index]
    }
}