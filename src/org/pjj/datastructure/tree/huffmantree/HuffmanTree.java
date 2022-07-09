package org.pjj.datastructure.tree.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 赫夫曼树
 * @author PengJiaJun
 * @Date 2020/8/16 11:44
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int[] array = {13,7,8,3,29,6,1};
        Node huffmanTree = createHuffmanTree(array);
        huffmanTree.preOrder();
    }


    /**
     * 创建赫夫曼树
     * @param array 传入的数组
     * @return 返回根节点
     */
    public static Node createHuffmanTree(int[] array){
        //先使用数组中所有的元素创建若干个二叉树,(只有一个节点的二叉树)
        List<Node> nodes = new ArrayList<>();
        for(int value : array){
            nodes.add(new Node(value));
        }

        //循环处理, 直到nodes中 只有 一个节点
        while(nodes.size() > 1){
            //排序  此时是从小到大
            Collections.sort(nodes);
            //取出权值最小的两个二叉树
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            //创建一个新的二叉树  取出的这两个二叉树作为左右子树  父节点的权值是 左右子节点的权值相加
            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;
            //把取出来的两个二叉树从list中移除
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //将新的二叉树parent,放入原来的二叉树集合中
            nodes.add(parent);//此时 新加入的二叉树parent 在list的最后, 下一轮循环先排序后, 再取出最小的两个二叉树
        }
        return nodes.get(0);
    }

}

//创建节点类
class Node implements Comparable<Node>{
    int value;//节点的权值
    Node left;//指向左子节点
    Node right;//指向右子节点

    public Node(int value){
        this.value = value;
    }

    //前序遍历
    public void preOrder(){
        System.out.println(this);

        if(this.left != null){
            this.left.preOrder();
        }

        if(this.right != null){
            this.right.preOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    /**
     * 想要通过 Collections.sort(nodes); 进行排序 那么 需要排序的 类 就必须实现 Comparable<Node> 接口
     * 这个 Comparable 接口 就会提供一个 compareTo() 方法 让我们实现, 这个方法有三个返回值
     * 返回 1 则 表示 当前节点值 大于 参数节点值
     * 返回 0 则 表示 当前节点值 == 参数节点值
     * 返回 -1 则 表示 当前节点值 小于 参数节点值
     * 总之 想要 通过 Collections.sort(nodes); 来排序 就必须 实现 Comparable接口 中的 compareTo方法
     * compareTo方法 中 根据自己需要比较的属性  来返回 1, 0, -1即可 this.value > o.value 返回 1, this.value==o.value 返回0, this.value<o.value 返回-1
     * @param o
     * @return
     */
    @Override
    public int compareTo(Node o) {
        //表示从小到大排序
        return this.value - o.value;
        // return this.value - o.value;这与  if (this.value > o.value) 返回 1, if (this.value==o.value) 返回 0, if(this.value<o.value) 返回 -1
        //其实是一样的 this.value - o.value 如果this.value 大于 o.value 那么减出来后(this.value - o.value) 实际会返回 0以上的值, 至少是1
        //this.value - o.value 如果this.value 等于 o.value 那么减出来后(this.value - o.value) 实际会返回 0
        //this.value - o.value 如果this.value 小于 0.value 那么减出来后(this.value - o.value) 实际返回 0以下的值, 至少是-1
    }
}