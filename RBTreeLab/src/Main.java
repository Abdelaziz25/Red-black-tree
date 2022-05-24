import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        RBTree<Integer, Integer> tree = new RBTree<Integer, Integer>();
        Scanner input = new Scanner(System.in);
        //We build the tree by getting keys and values
        System.out.println("Enter the keys to build tree");
        String in = input.nextLine();
        String[] tempArr = in.split(" ");
        int[] keys = new int[tempArr.length];
        ArrayList<Integer> keyList = new ArrayList<Integer>();
        for(int i = 0; i < tempArr.length; i++)
        {
            keys[i] = Integer.parseInt(tempArr[i]);
        }
        for(int i = 0; i < keys.length; i++)
        {
            keyList.add(keys[i]);
        }
        System.out.println("Enter the values corresponding to the given keys");
        in = input.nextLine();
        int[] values = new int[tempArr.length];
        for(int i = 0; i < tempArr.length; i++)
        {
            values[i] = Integer.parseInt(tempArr[i]);
        }
        //We check if the number keys and values sre the same
        if(values.length != keys.length)
        {
            System.out.println("The number of values entered must equal the number of keys entered");
            return;
        }
        int batchInsertionTime = (int) System.currentTimeMillis();
        for(int i = 0; i < keys.length; i++)
        {
            tree.insert(keys[i], values[i]);
        }
        System.out.println("Time to insert all nodes: " + ((int)System.currentTimeMillis() - batchInsertionTime));
        //We print the tree after building by displaying each node's children and parent
        System.out.println("*************************");
        for(int i = 0; i < keys.length; i++)
        {
            System.out.println(keys[i] + "   ");
            if(tree.search(keys[i]).getParent() != null)
                System.out.println("parent: " + tree.search(keys[i]).getParent().getColor() + " "+ tree.search(keys[i]).getParent().getKey());
            if(tree.search(keys[i]).getLeftChild() != null)
                System.out.println("leftChild: " + tree.search(keys[i]).getLeftChild().getColor() + " "+ tree.search(keys[i]).getLeftChild().getKey());
            if(tree.search(keys[i]).getRightChild() != null)
                System.out.println("rightChild: " + tree.search(keys[i]).getRightChild().getColor() + " "+ tree.search(keys[i]).getRightChild().getKey());
            System.out.println("*************************");
        }
        String end="yes";
        while (end.equals("yes"))
        {
            System.out.println("Select the number of the method you want to test:");
            System.out.println("1. Insert");
            System.out.println("2. Search");
            System.out.println("3. Delete");
            System.out.println("4. Contain");
            System.out.println("5. getRoot");
            int number = input.nextInt();
            switch (number)
            {
                case 1:
                    System.out.println("Enter the key");
                    int key = input.nextInt();
                    System.out.println("Enter the corresponding value");
                    int value = input.nextInt();
                    if(!tree.contains(key))
                        keyList.add(key);
                    int insertTime= (int) System.currentTimeMillis();
                    tree.insert(key, value);
                    System.out.println("Time Taken: " + ((int)System.currentTimeMillis() - insertTime));
                    break;
                case 2:
                    System.out.println("Enter the key to search for");
                    int temp = input.nextInt();
                    int time;
                    if(tree.search(temp) != null)
                    {
                        time= (int) System.currentTimeMillis();
                        System.out.println(tree.search(temp).getValue());
                        System.out.println("Time Taken: " + ((int)System.currentTimeMillis() - time));
                    }
                    else
                    {
                        System.out.println("Not found");
                    }
                    break;
                case 3:
                    System.out.println("Enter the key");
                    int keyDelete = input.nextInt();
                    if(tree.contains(keyDelete))
                    {
                        keyList.remove(keyList.indexOf(keyDelete));
                    }
                    int deleteTime= (int) System.currentTimeMillis();
                    System.out.println(tree.delete(keyDelete));
                    System.out.println("Time Taken: " + ((int)System.currentTimeMillis() - deleteTime));
                    break;
                case 4:
                    System.out.println("Enter the key");
                    int keyContain = input.nextInt();
                    int containTime= (int) System.currentTimeMillis();
                    System.out.println(tree.contains(keyContain));
                    System.out.println("Time Taken: " + ((int)System.currentTimeMillis() - containTime));
                    break;
                case 5:
                    if(!tree.isEmpty())
                        System.out.println(tree.getRoot().getKey());
            }
            System.out.println();
            System.out.println("if you want to complete operations write yes ");
            end = input.next();
        }
        //remove all nodes wanted
        System.out.println("Enter the keys to delete from tree");
        String in2 = input.nextLine();
        String[] tempArr2 = in2.split(" ");
        int[] keys2 = new int[tempArr2.length];
        for(int i = 0; i < tempArr2.length; i++)
        {
            keys2[i] = Integer.parseInt(tempArr2[i]);
        }
    }
}

