public class RBTree<T extends Comparable<T>, V> implements ITree<T, V>
{
    private INode<T, V> root;
    public RBTree()
    {
        this.root = null;
    }
    @Override
    public INode<T, V> getRoot() {
        return root;
    }
    @Override
    public INode<T, V> search(T key)
    {
        return search(this.root, key);
    }
    //It searches for the node recursively by putting root as child subtree
    private INode<T, V> search(INode<T, V>root,T key)
    {
        if (root == null)
        {
            return null;
        }
        if (root.getKey().compareTo(key) == 0)
        {
            return root;
        }
        else
        {
            return root.getKey().compareTo(key) < 0 ? this.search(root.getRightChild(), key) : this.search(root.getLeftChild(), key);
        }
    }

    @Override
    public boolean contains(T key)
    {
        return !(this.search(this.root, key) == null);
    }

    @Override
    public boolean isEmpty()
    {
        return (this.root == null);
    }

    @Override
    public void clear()
    {
        this.root = null;
    }

    /**\
     * It brings the first node in subtree inorder traversal
     * @param root
     * @return the INode wrapper to first node in subtree inorder traversal
     */
    INode<T, V> subtree_first(INode<T, V> root)
    {
        if(root.getLeftChild() != null)
        {
            return  subtree_first(root.getLeftChild());
        }
        return root;
    }
    /**\
     * It brings the last node in subtree inorder traversal
     * @param root
     * @return the INode wrapper to last node in subtree inorder traversal
     */
    INode<T, V> subtree_last(INode<T, V> root)
    {
        if(root.getRightChild() != null)
        {
            return  subtree_last(root.getRightChild());
        }
        return root;
    }
    @Override
    public void insert(T key, V value)
    {
        Node node = new Node(key, value, Colors.red);
        INode x = this.root;
        INode temp = null;
        //If the tree is empty we make the color of the tree black
        //and put is as root
        if(this.isEmpty())
        {
            node.setColor(Colors.black);
            this.root = node;
            return;
        }
        //otherwise we find the node to be the parent of our new node
        while (x != null)
        {
            temp = x;
            if (x.getKey().compareTo(node.getKey()) > 0)
            {
                x = x.getLeftChild();
            }
            else if(x.getKey().compareTo(node.getKey()) == 0)
            {
                return;
            }
            else
            {
                x = x.getRightChild();
            }
        }
        //We insert our node as child to the parent
        if (temp.getKey().compareTo(node.getKey()) > 0)
        {
            temp.setLeftChild(node);
            node.setParent(temp);
        }
        else
        {
            temp.setRightChild(node);
            node.setParent(temp);
        }
        //Then we have to fix the tree according to our insertion
        fixInsert(node);
    }
    //It makes a color balance and the necessary rotations to keep up with red black tree properties
    private void fixInsert(INode<T, V> node)
    {
        //First case if the parent is black then we exit
        if(!node.getParent().isRed())
        {
            return;
        }
        INode uncle = node.getUncle();
        INode parent = node.getParent();
        //Second case the parent is red and the uncle is red
        if(uncle != null && uncle.isRed())
        {
            uncle.setColor(Colors.black);
            parent.setColor(Colors.black);
            //We then check if the grandparent is not root to recolor it
            if(!parent.getParent().isRoot())
            {
                INode grandparent = parent.getParent();
                grandparent.setColor(Colors.red);
                fixInsert(grandparent);
            }
            return;
        }
        //Third case the parent is red and uncle is black
        //Thus we have to make rotations according to the position of the child and parent
        INode grandparent = parent.getParent();
        boolean isLeft = false;
        if(!grandparent.isRoot())
        {
            isLeft = grandparent.getParent().isLeftChild(grandparent);
        }
        if(parent.isLeftChild(node) && grandparent.isLeftChild(parent))
        {
            grandparent.setColor(Colors.red);
            parent.setColor(Colors.black);
            INode n  = rightRotate(grandparent);
            assignParent(n, isLeft);
            return;
        }
        if(parent.isRightChild(node) && grandparent.isRightChild(parent))
        {
            grandparent.setColor(Colors.red);
            parent.setColor(Colors.black);
            INode n  = leftRotate(grandparent);
            assignParent(n, isLeft);
            return;
        }
        if(parent.isRightChild(node) && grandparent.isLeftChild(parent))
        {
            INode n = leftRotate(parent);
            grandparent.setLeftChild(n);
            grandparent.setColor(Colors.red);
            n.setColor(Colors.black);
            n = rightRotate(grandparent);
            assignParent(n, isLeft);
            return;
        }
        if(parent.isLeftChild(node) && grandparent.isRightChild(parent))
        {
            INode n = rightRotate(parent);
            grandparent.setRightChild(n);
            grandparent.setColor(Colors.red);
            n.setColor(Colors.black);
            n = leftRotate(grandparent);
            assignParent(n, isLeft);
            return;
        }
    }
    /**\
     * Put the node as child to its parent
     * @param node the node to be put as thild
     * @param isLeft detects if the node should be put on left
     * */
    private void assignParent(INode node, boolean isLeft)
    {
        if(!node.isRoot())
        {
            INode parent = node.getParent();
            if(isLeft)
            {
                parent.setLeftChild(node);
                return;
            }
            parent.setRightChild(node);
        }
    }
    @Override
    public boolean delete(T key)
    {
        INode node = search(key);
        if(node != null)
        {
            //first we get the node after putting it as leaf
            INode removedNode = deleteNode(node);
            //if the node is red we remove it directly
            if(removedNode.isRed())
            {
                INode parent = removedNode.getParent();
                removedNode.setParent(null);
                if(parent.isLeftChild(removedNode))
                {
                    parent.setLeftChild(null);
                }
                else
                {
                    parent.setRightChild(null);
                }
            }
            else
            {
                if(removedNode == this.root)
                {
                    this.root = null;
                    return true;
                }
                fixDelete(removedNode);
            }
            return true;
        }
        return false;
    }
    //It removes the double black node by applying cases to keep the red-black tree properties
    private INode<T,V> fixDelete(INode<T, V> node)
    {
        //If the node is root we pass it as we remove the double black color
        if(node.isRoot())
        {
            return null;
        }
        //If the node's sibling is red
        if(node.getSibling() != null && node.getSibling().isRed())
        {
            //We swap the parent and sibling color
            Colors temp = node.getParent().getColor();
            node.getParent().setColor(node.getSibling().getColor());
            node.getSibling().setColor(temp);
            //rotate the parent in the node direction but first we remove node
            boolean isLeft = node.getParent().isLeftChild(node);
            boolean isLeft1 = false;
            INode parent = node.getParent();
            if(!parent.isRoot())
            {
                isLeft1 = parent.getParent().isLeftChild(parent);
            }
            if(isLeft)
            {
                assignParent(leftRotate(parent), isLeft1);
            }
            else
            {
                assignParent(rightRotate(parent), isLeft1);
            }
            return fixDelete(node);
        }
        //The node's sibling is black
        //We see the color of sibling's children if there is children
        //If one child is missing we set its color as black
        INode sibling = node.getSibling();
        INode parent = node.getParent();
        //We distinguish the sibling children according to their nearness to the double black node
        INode nearChild, farChild;
        Colors nearColor = Colors.black, farColor = Colors.black;
        if(parent.isLeftChild(node))
        {
            nearChild = sibling.getLeftChild();
            farChild = sibling.getRightChild();
            if(nearChild != null)
                nearColor = nearChild.getColor();
            if(farChild != null)
                farColor = farChild.getColor();
        }
        else
        {
            nearChild = sibling.getRightChild();
            farChild = sibling.getLeftChild();
            if(nearChild != null)
                nearColor = nearChild.getColor();
            if(farChild != null)
                farColor = farChild.getColor();
        }
        //if the two children are black
        if(nearColor == Colors.black && farColor == Colors.black)
        {
            //make the sibling red
            sibling.setColor(Colors.red);
            //add black to parent if it is black it become double
            boolean flag = false;
            if(parent.isRed())
            {
                parent.setColor(Colors.black);
            }
            else
            {
                flag = true;
            }
            //We remove double black node
            boolean isLeft = parent.isLeftChild(node);
            node.setParent(null);
            if(isLeft)
            {
                parent.setLeftChild(null);
            }
            else
            {
                parent.setRightChild(null);
            }
            if(flag)
            {
                fixDelete(parent);
            }
        }
        //if the sibling near child is red while the other far one is black
        boolean flag = false;
        if(nearColor == Colors.red && farColor == Colors.black)
        {
            //swap the color of the sibling and its near child
            sibling.setColor(Colors.red);
            nearChild.setColor(Colors.black);
            //Rotate the parent in direction opposite to the double black node direction
            if(parent.isLeftChild(node))
            {
                boolean direction =  parent.isLeftChild(sibling);
                assignParent(rightRotate(sibling), direction);
            }
            else
            {
                boolean direction =  parent.isLeftChild(sibling);
                assignParent(leftRotate(sibling), direction);
            }
            //Apply the next case
            flag = true;
        }
        //if the far child is red
        if(flag || (farColor == Colors.red))
        {
            //swap the parent and sibling's colors
            Colors temp = parent.getColor();
            parent.setColor(sibling.getColor());
            sibling.setColor(temp);
            //change the color of the red children
            farChild.setColor(Colors.black);
            if(nearColor == Colors.red)
                nearChild.setColor(Colors.black);
            //rotate the parent in double black node direction
            if(parent.isLeftChild(node))
            {
                boolean isLeft = false;
                if(!parent.isRoot())
                {
                    isLeft = parent.getParent().isLeftChild(parent);
                }
                assignParent(leftRotate(parent), isLeft);
            }
            else
            {
                boolean isLeft = false;
                if(!parent.isRoot())
                {
                    isLeft = parent.getParent().isLeftChild(parent);
                }
                assignParent(rightRotate(parent), isLeft);
            }
            //delete double black node
            boolean h = parent.isLeftChild(node);
            node.setParent(null);
            if(h)
            {
                parent.setLeftChild(null);
            }
            else
            {
                parent.setRightChild(null);
            }
        }
        return node;
    }
    //This function brings the node to be deleted by putting it as leaf
    private INode<T, V> deleteNode(INode node)
    {
        INode temp = null;
        if(node.getRightChild() != null || node.getLeftChild() != null)
        {
            if (node.getLeftChild() != null)
            {
                temp = predecessor(node);
            }
            else
            {
                temp = successor(node);
            }
            V h = (V) temp.getValue();
            T o = (T) temp.getKey();
            temp.setValue(node.getValue());
            temp.setKey(node.getKey());
            node.setKey(o);
            node.setValue(h);
            return deleteNode(temp);
        }
        return node;
    }
    //Rotate the given node in the right direction
    INode<T, V> rightRotate(INode<T, V> x)
    {
        boolean flag = false;
        if(x.isRoot())
        {
            flag = true;
        }
        INode<T, V> xl = x.getLeftChild();
        INode<T, V> xlr = xl.getRightChild();
        INode<T, V> xp = x.getParent();
        xl.setRightChild(x);
        xl.setParent(xp);
        x.setParent(xl);
        x.setLeftChild(xlr);
        if(xlr != null)
            xlr.setParent(x);
        if(flag)
            this.root = xl;
        return xl;
    }
    //Rotate the given node in the left direction
    INode<T, V> leftRotate(INode<T, V> x)
    {
        boolean flag = false;
        if(x.isRoot())
        {
            flag = true;
        }
        INode<T, V> xr = x.getRightChild();
        INode<T, V> xrl = xr.getLeftChild();
        INode<T, V> xp = x.getParent();
        xr.setLeftChild(x);
        xr.setParent(xp);
        x.setParent(xr);
        x.setRightChild(xrl);
        if(xrl != null)
            xrl.setParent(x);
        if(flag)
            this.root = xr;
        return xr;
    }
    //get the given node successor
    private INode<T, V>successor(INode<T, V> x)
    {
        if(x.getRightChild() != null)
        {
            return subtree_first(x.getRightChild());
        }
        while(x.getParent() != null && x.getParent().isRightChild(x))
        {
            x = x.getParent();
        }
        return x.getParent();
    }
    //give the given node predecessor
    private INode<T, V>predecessor(INode<T, V> x)
    {
        if(x.getLeftChild() != null)
        {
            return subtree_last(x.getLeftChild());
        }
        while(x.getParent() != null && x.getParent().isLeftChild(x))
        {
            x = x.getParent();
        }
        return x.getParent();
    }
}
