public class Node<T extends Comparable<T>, V> implements INode<T, V>
{
    private V value = null;
    private T key = null;
    private INode<T, V> leftChild, rightChild, parent;
    private Colors color = Colors.red;
    public Node(T key, V value, Colors color)
    {
        this.value = value;
        this.key = key;
        this.color = color;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }
    @Override
    public void setParent(INode<T, V> parent)
    {
        this.parent = parent;
    }

    @Override
    public void setLeftChild(INode<T, V> leftChild)
    {
        this.leftChild = leftChild;
    }

    @Override
    public void setColor(Colors color)
    {
        this.color = color;
    }

    @Override
    public void setRightChild(INode<T, V> rightChild)
    {
        this.rightChild = rightChild;
    }

    @Override
    public void setValue(V value)
    {
        this.value = value;
    }

    @Override
    public INode<T, V> getSibling()
    {
        return (this.parent.getLeftChild() == this)? this.parent.getRightChild() : this.parent.getLeftChild();
    }

    @Override
    public INode<T, V> getLeftChild()
    {
        return leftChild;
    }

    @Override
    public INode<T, V> getUncle()
    {
        INode<T, V> uncle;
        INode<T, V> grandParent = this.getParent().getParent();
        if (grandParent.isRightChild(this.getParent()))
        {
            uncle = grandParent.getLeftChild();
        }
        else{
            uncle = grandParent.getRightChild();
        }
        return uncle;
    }

    @Override
    public Colors getColor()
    {
        return color;
    }

    @Override
    public INode<T, V> getParent()
    {
        return parent;
    }

    @Override
    public INode<T, V> getRightChild()
    {
        return rightChild;
    }

    @Override
    public V getValue()
    {
        return value;
    }

    @Override
    public boolean isLeftChild(INode<T, V> node)
    {
        return this.leftChild == node;
    }

    @Override
    public boolean isRightChild(INode<T, V> node)
    {
        return (this.rightChild == node);
    }

    @Override
    public boolean isRoot()
    {
        return (this.parent == null);
    }
    public boolean isRed()
    {
        return (this.color == Colors.red);
    }

    @Override
    public void setKey(T key)
    {
        this.key = key;
    }

    @Override
    public T getKey()
    {
        return key;
    }
}
