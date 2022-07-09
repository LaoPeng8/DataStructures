package org.pjj.datastructure.tree;

/**
 * 顺序存储二叉树
 * @author PengJiaJun
 * @Date 2020/8/14 17:33
 */
public class ArrayBinaryTreeDemo {
    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,7};
        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(array);
        System.out.println("前序遍历: ");
        arrayBinaryTree.preOrder();//从0开始前序遍历 就相当于从二叉树的根节点开始前序遍历   //1,2,4,5,3,6,7

        System.out.println("\n中序遍历: ");
        arrayBinaryTree.infixOrder();//4,2,5,1,6,3,7

        System.out.println("\n后序遍历: ");
        arrayBinaryTree.postOrder();//4,5,2,6,7,3,1

    }
}

//编写一个ArrayBinaryTree, 实现顺序存储二叉树遍历
class ArrayBinaryTree{
    private int[] array;//存储数据的数组, 该数组中存放二叉树节点

    public ArrayBinaryTree(int[] array) {
        this.array = array;
    }

    //重载下面那个方法, 避免每次调用时都需要传个参数 0 (根节点)
    public void preOrder(){
        this.preOrder(0);
    }

    /**
     * 编写一个方法, 完成顺序存储二叉树的前序遍历
     * 其实就是二叉树是存在数组中的,然后前序遍历这个数组
     * @param index 数组的下标, 二叉树节点的下标
     */
    public void preOrder(int index){
        //如果数组为空, 或者array.length=0
        if(array == null || array.length == 0){
            System.out.println("数组为空, 不能按照二叉树的前序遍历");
            return;//如果这里不return 那么数组为空后 不return就还会继续执行当前这个方法, 下面还输出array[index]数组都没有这么输出,必抛出异常
        }

        //输出当前这个元素
        System.out.println(array[index]);

        //向左递归遍历
        int indexLeft = (2 * index) + 1; // 这个公式算出来的就是 该index的左子节点的下标
        if(indexLeft < array.length){//判断一下 防止越界, 如果没有越界即 递归调用前序遍历 进入左子节点
            preOrder(indexLeft);
        }

        //向右递归遍历
        int indexRight = (2 * index) + 2; // 这个公式算出来的就是 该index的右子节点的下标
        if(indexRight < array.length){//判断一下 防止越界, 如果没有越界即 递归调用前序遍历 进入右子节点
            preOrder(indexRight);
        }
    }


    public void infixOrder(){
        this.infixOrder(0);
    }

    /**
     * 中序遍历 顺序储存二叉树, 其实就是二叉树是存在数组中的,然后中序遍历这个数组
     * @param index
     */
    public void infixOrder(int index){

        if(array == null || array.length == 0){
            System.out.println("数组为空, 不能按照二叉树的中序遍历");
            return;
        }

        int indexLeft = 2 * index + 1;// 这个公式算出来的就是 该index的左子节点的下标
        if(indexLeft < array.length){//只要没越界 就可以继续递归遍历 左子节点
            infixOrder(indexLeft);
        }

        System.out.println(array[index]);

        int indexRight = 2 * index + 2;// 这个公式算出来的就是 该index的右子节点的下标
        if(indexRight < array.length){//只要没越界 就可以继续递归遍历 右子节点
            infixOrder(indexRight);
        }
    }


    public void postOrder(){
        this.postOrder(0);
    }

    /**
     * 后序遍历
     * @param index
     */
    public void postOrder(int index){

        if(array == null || array.length == 0){
            System.out.println("数组为空, 不能按照二叉树的中序遍历");
            return;
        }

        if((2 * index + 1) < array.length){ //这个公式算出来的就是 该index的左子节点的下标, 只要没越界 就可以继续递归遍历 左子节点
            postOrder(2 * index +1);//后序遍历左子节点
        }

        if((2 * index + 2) < array.length){//这个公式算出来的就是 该index的右子节点的下标, 只要没越界 就可以继续递归遍历 右子节点
            postOrder((2 * index + 2));//后序遍历右子节点
        }

        System.out.println(array[index]);

    }



}