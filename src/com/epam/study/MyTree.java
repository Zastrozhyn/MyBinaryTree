package com.epam.study;

import java.util.Comparator;

public class MyTree<T> {
    private MyNode firstNode;
    private int size;
    private Comparator<T> comparator;

    public MyTree(){
        size = 0;
    }

    public MyTree (Comparator<T> comparator){
        if(comparator != null)
        this.comparator = comparator;
    }

    public void add (T t){
        if (!isComparable(t)){
            throw new IllegalArgumentException("Object doesn't implements Comparable ");
        }
        if (size == 0){
            firstNode =new MyNode(t);
            size++;
            return;
        }
        addNode(firstNode, t);
    }

    public boolean remove(T t){
        MyNode node = findNode(firstNode, t);
        if(node == null){
            return false;
        }

        // If there are no leaf in deleted root
        if(node.leftLeaf == null && node.rightLeaf == null){
            if(node.root.leftLeaf.t.equals(t)){
                node.root.leftLeaf = null;
            }
            else {
                node.root.rightLeaf = null;
            }
            return true;
        }

        //if node has both left and right child
        if(node.leftLeaf != null && node.rightLeaf != null){
            T minNodeInRightLeaf = (T) minNode((T) node.rightLeaf.t).t;
            remove(minNodeInRightLeaf);
            node.t = minNodeInRightLeaf;
        }

        //if node has only right child
        if(node.leftLeaf == null && node.rightLeaf != null){
            if(compare(t, (T) node.root.t) < 0){
                node.root.leftLeaf = node.rightLeaf;
            }
            else {
                node.root.rightLeaf = node.rightLeaf;
            }
            node.rightLeaf.root = node.root;
            node = node.rightLeaf;
            return true;
        }

        //if node has only left child
        if(node.leftLeaf != null && node.rightLeaf == null){
            if(compare(t, (T) node.root.t) > 0){
                node.root.leftLeaf = node.leftLeaf;
            }
            else {
                node.root.rightLeaf = node.leftLeaf;
            }
            node.leftLeaf.root = node.root;
            node = node.leftLeaf;
            return true;
        }

        return true;
    }

    public boolean contains(T t){
        MyNode node = findNode(firstNode, t);
        if(node == null){
            return false;
        }
        return t.equals(node.t);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MyTree{");
        sb.append("firstNode=").append(firstNode);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }

    public MyNode findNode(MyNode node, T t){
        if (node != null && (compare((T) node.t, t) > 0)){
            node =findNode(node.rightLeaf, t);
        }
        if (node != null &&(compare((T) node.t, t) < 0)){
            node = findNode(node.leftLeaf, t);
        }
        return node;
    }

    public MyNode minNode (T t){
        MyNode node =findNode(firstNode, t);
        while(node.leftLeaf != null){
            node = node.leftLeaf;
        }
        return node;
    }

    static class MyNode<T>{
        T t;
        MyNode root;
        MyNode leftLeaf;
        MyNode rightLeaf;

        MyNode() {
        }

        MyNode(T t, MyNode root) {
            this.t = t;
            this.root = root;
        }

        MyNode(T t) {
            root = new MyNode();
            this.t = t;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("MyNode{");
            sb.append("t=").append(t);
            sb.append(", root=").append(root.t);
            sb.append(", left=").append(leftLeaf);
            sb.append(", right=").append(rightLeaf);
            sb.append('}');
            return sb.toString();
        }
    }
    private MyNode addNode(MyNode node, T t){
        if ((compare((T) node.t, t) > 0)){
            if(node.rightLeaf == null){
                node.rightLeaf = new MyNode(t, node);
                size++;
                return node.rightLeaf;
            }
            node = addNode(node.rightLeaf, t);
        }
        if ((compare((T) node.t, t) < 0)){
            if(node.leftLeaf == null){
                node.leftLeaf = new MyNode<>(t, node);
                size++;
                return node.leftLeaf;
            }
            node = addNode(node.leftLeaf, t);
        }
        return node;
    }
    private boolean isComparable(T t){
        if(comparator != null){
            return true;
        }
        return t instanceof Comparable;
    }

    private int compare(T t1, T t2){
        if (comparator != null){
            return comparator.compare(t2, t1);
        }
        else{
            Comparable o1 =(Comparable) t1;
            Comparable o2 =(Comparable) t2;
            return o2.compareTo(o1);
        }
    }

}
