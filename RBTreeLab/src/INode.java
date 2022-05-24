public interface INode<T extends Comparable<T>, V>
{
    /**
     * set the parent of the current node in the tree
     * @param parent
     */
    void setParent(INode<T, V> parent);
    /**
     * Returns the parent of the current node in the tree
     * @return INode wrapper to the parent of the current node
     */
    INode<T, V> getParent();
    /**
     * set the left child of the current node in the tree
     * @param leftChild
     */
    void setLeftChild(INode<T, V> leftChild);
    /**
     * Returns the left child of the current node in the tree
     * @return INode wrapper to the left child of the current node
     */
    INode<T, V> getLeftChild();
    /**
     * set the right child of the current node in the tree
     * @param rightChild
     */
    void setRightChild(INode<T, V> rightChild);
    /**
     * Returns the right child of the current node in the tree
     * @return INode wrapper to the right child of the current node
     */
    INode<T, V> getRightChild();
    /**
     * Get the value of the current node
     * @return Value of the current node
     */
    V getValue();
    /**
     * Set the value of the current node
     * @param  value
     */
    void setValue(V value);
    /**
     * Get the key of the current node
     * @return key of the current node
     */
    T getKey();
    /**
     * Set the key of the current node
     * @param  key
     */
    void setKey(T key);
    /**
     * Get the color of the current node
     * @return Color of the current node
     */
    Colors getColor();
    /**
     * Set the color of the current node.
     * @param color
     */
    void setColor(Colors color);
    /**
     * Returns the uncle of the current node in the tree.
     * @return INode wrapper to the uncle of the current node
     */
    INode<T, V> getUncle();
    /**
     * Detect if the given node is right child.
     * @return if the given node is right child
     */
    boolean isRightChild(INode<T, V> node);
    /**
     * Detect if the given node is left child.
     * @return if the given node is left child
     */
    boolean isLeftChild(INode<T, V> node);
    /**
     * Returns the sibling of the current node in the tree.
     * @return INode wrapper to the sibling of the current node
     */
    INode<T, V> getSibling();
    /**
     * Detect if the given node is root.
     * @return if the given node is root
     */
    boolean isRoot();
    /**
     * Detect if the color of node is red.
     * @return if the color of node is red
     */
    boolean isRed();
}
