package org.pjj.datastructure.tree.binarysorttree;

/**
 * 二叉排序树  BST = Binary Sort(Search) Tree
 *
 * @author PengJiaJun
 * @Date 2020/8/19 15:26
 */
public class BinarySortTreeDemo {

    public static void main(String[] args) {
        int[] array = {5,2,7};
        BinarySortTree bst = new BinarySortTree();

        for (int i = 0; i < array.length; i++) { //循环的添加节点到二叉排序树
            bst.add(new Node(array[i]));
        }

//        bst.add(new Node(2));
        bst.infixOrder(); // 中序遍历二叉排序树   1,3,5,7,9,10,12

        //查找
//        Node search = bst.search(5);
//        System.out.println("查找节点值为5的节点: " + search);

        //查找父节点
//        Node parent = bst.searchParent(12);
//        System.out.println("查找节点值为12的节点的父节点: "+parent);

//        bst.delete(2);
//        bst.delete(5);
//        bst.delete(9);
//        bst.delete(12);
//        bst.delete(7);
//        bst.delete(3);
//        bst.delete(10);
//        bst.delete(1);

        bst.delete(7);
        bst.delete(2);
        bst.delete(5);
        System.out.println("\n删除了值为7的节点后: ");
        bst.infixOrder();// 中序遍历二叉排序树

    }

}

//二叉排序树
class BinarySortTree {
    private Node root;

    /**
     * 添加节点的方法
     *
     * @param node
     */
    public void add(Node node) {
        if (root == null) { //如果根节点为空, 则添加接点 就不用比较什么左右子树了, 直接赋给根节点
            root = node;
        } else {
            root.add(node);
        }
    }

    /**
     * 中序遍历  二叉排序树使用中序遍历 遍历出来就是 从小到大的顺序
     */
    public void infixOrder() {
        if (root != null) {
            root.infixOrder();
        } else {
            System.out.println("二叉排序树为空, 不能遍历");
        }
    }

    /**
     * 查找节点
     * 根据节点值 查找到该节点
     * @param value
     * @return
     */
    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    /**
     * 查找父节点
     * 根据节点值 查找到该节点 的父节点
     * @param value
     * @return
     */
    public Node searchParent(int value){
        if(root == null){
            return null;
        }else{
            return root.searchParent(value);
        }
    }

    /**
     * 删除节点
     * @param value
     */
    public void delete(int value){
        if(root==null){
            return;
        }else{
            //找到这个节点 (待删除的节点)
            Node target = this.search(value);
            if(target == null){//没有找到待删除的节点
                return;
            }
            //找到这个节点的父节点
            Node targetParent = this.searchParent(value);

            //如果要删除的节点是叶子节点(没有左右子树)
            if(target.left == null && target.right == null){//当前节点的左右子节点都是空, 则当前节点就是一个叶子节点
                if(targetParent != null){
                    //知道要删除的节点是target后, 而且还是叶子节点, 则直接让target的父节点 将原本指向 target的指针指向空即可.
                    //但是还需要判断一下, target的父节点   是 左子节点是 target 还是右子节点是target 然后才可以 删除
                    if(targetParent.left == target){ //target的父节点   是左子节点 指向 target的, 所以将target的父节点 的左子节点 置为空即可
                        targetParent.left = null;
                    } else if(targetParent .right == target){ //target的父节点 是右子节点 指向 target的, 所有将target的父节点 的右子节点 置为空即可
                        targetParent.right = null;
                    }
                }else{ //targetParent == null 则说明即是叶子节点 也是 根节点  (只有一个节点的根节点)
                    root = null;
                }

            //如果要删除的节点是非叶子节点 而且是 只有一颗子树的 非叶子节点 (左右子树 只有一颗)
            }else if((target.left != null && target.right == null) || (target.right != null && target.left == null)){
                if(targetParent != null){ //
                    //知道要是删除的节点 只有一颗子树 (左右子树 只有一颗) 则直接让 target的父节点 将原本指向target的指针 指向 target的子节点(因为左右子树点只有一颗, 则可以让该子树代替target的位置,则就删除了target)
                    //还需要先判断一下 target的父节点, 是target的父节点的左子节点 还是右子节点 是target, 然后才可以删除
                    if(targetParent.left == target){ //target的父节点   是左子节点 指向 target的
                        //知道是 target父节点的左子节点指向 target的之后 就可以 将target父节点的左子节点指向其他 就相当于删除了 target
                        //知道要是删除的节点 只有一颗子树 (左右子树 只有一颗) 但是还不知道 是只有左子树 还是只有 右子树
                        if(target.left != null){ //是左子树, 则将targetParent.left 指向 target.left 这样 target就被 跳过了(删除了)
                            targetParent.left = target.left;
                        }else{ //target.left == null, 那么意思就是 target.right != null  //是右子树
                            targetParent.left = target.right;
                        }
                    } else if(targetParent .right == target){ //target的父节点   是右子节点 指向 target的
                        if(target.left != null){
                            targetParent.right = target.left;
                        }else{ //target.left == null, 那么意思就是 target.right != null
                            targetParent.right = target.right;
                        }
                    }
                }else{ //如果父节点为空, 则说明删除的节点是root节点 而且是只有一颗子树的 root根节点
                    if(target.left != null){
                        root = target.left;
                    }else{ //target.right != null
                        root = target.right;
                    }
                }

            //如果要删除的节点是非叶子节点 而且是 有两颗子树的 非叶子节点 (左右子树 两颗数都在)
            }else if(target.left != null && target.right != null){
                //思路:  首先找到 待删除节点 的前驱节点 或者 后继节点 然后将其删除 将值 赋给待删除节点 这样待删除节点就删除了.
                //如 [1,3,7,9,12] 需要删除 7 则将 3 或者 9 删除 然后就变成了[1,3,7,12] 然后将删除的值(9) 赋给待删除节点 7 就变成了 [1,3,9,12] 删除成功
                //在二叉排序树中 7(待删除节点)的 前驱节点 就是 该节点的左子树中 最大的值(节点), 后继节点就是 该节点的右子数中 最小的值(节点)
                //至于 为什么 该节点的 前驱后继节点是左子树最大节点右子树最小节点, 我也说不清楚     画个图就明白了.

                //删除右子树中值最小的节点, 先获取到到节点的值
                int min = deleteMin(target.right);//如何 删除右子树中值最小的节点, 先获取到到节点的值  看这个方法的实现
                //替换目标节点中的值 这样target就相当于被删除了, target右子树中值最小的代替了 target
                target.value = min;

            }

        }
    }

    /**
     * 删除一颗树中最小的节点
     * @param node 需要删除哪棵树
     * @return 返回被删除节点的value值
     */
    private int deleteMin(Node node) {

        Node target = node;//先假定 node 就是  这个树中最小的节点
        //向左子字节 循环 直到找到 最后的左子节点 也是 最小的节点 因为 二叉排序树的 规则 左小右大, 左子节点 必小于 父节点
        //所以找到最左的 左子节点 就 找到了  这课树中最小的节点
        while(target.left != null){
            target = target.left;
        }
        //while退出后 此时 target 就是 最小的节点了

        //不能 直接 用 将 target 置为 null, 因为他可能有 右子节点
        //如果这个最小的节点是target 有右子节点,  (只能是 只有右子节点, 不可能有左子节点的,因为如果有左子节点 那么这个左子节点岂不比target更小了, 那么target应该是这个左子节点了)
        //则 直接让 target的父节点 跳过target 直接指向 target的右子节点  这样 就相当于删除了 最小节点 target
        //也有可能这个最小的节点target没有右子节点, 则直接 让target的父节点将原本指向target的指针 指向 null
        //这些情况 delete()方法中都考虑到了, 所以可以直接调调用 delete来删除 target节点
        //有人可能会是 delete方法中 会调用 本方法, 本方法又会调用delete这岂不凉了.....
        //注意: delete中删除节点时 只有 待删除的节点 有左右子节点时才会调用本方法, 而本方法中调用delete方法删除target时,
        //target只可能是 1.没有左右子节点   2.只有右子节点    不可能会出现同时有左右字节的情况的
        delete(target.value);

        return target.value;
    }

}


//节点
class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }


    /**
     * 添加节点的方法
     * 递归的形式添加节点, 注意需要满足二叉排序树的要求
     *
     * @param node
     */
    public void add(Node node) {
        if (node == null) {
            return;
        }

        //判断传入的节点的值, 和当前子树的的根节点的值关系
        if (node.value < this.value) { //如果插入值 小于 该节点的值 则往左边遍历
            //如果当前节点左子节点为null , 则将新节点放到这里
            if (this.left == null) {
                this.left = node;
            } else {
                //递归的向左子树添加
                this.left.add(node);
            }
        } else {// node.value >= this.value  如果插入值 大于 该节点的值 则往右边遍历
            if (this.right == null) {
                this.right = node;
            } else {
                //递归的向右子树添加
                this.right.add(node);
            }
        }

    }

    /**
     * 中序遍历
     */
    public void infixOrder() {

        if (this.left != null) {
            this.left.infixOrder();
        }

        System.out.println(this);

        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    /**
     * 查找节点
     *
     * @param value
     */
    public Node search(int value) {

        if (this.value == value) {
            return this;
        } else if (value < this.value) {
            if (this.left == null) {
                return null;
            } else {
                return this.left.search(value);
            }
        } else { //value >= this.value
            if (this.right == null) {
                return null;
            } else {
                return this.right.search(value);
            }
        }
    }

    /**
     * 查找父节点
     * 根据节点值 查找到该节点 的父节点
     * @param value
     * @return
     */
    public Node searchParent(int value) {

         if((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)){
             return this;
         }else{
             if(value < this.value){
                 if(this.left != null){
                    return this.left.searchParent(value);
                 }else{
                     return null;
                 }
             }else{
                 if(this.right != null){
                    return this.right.searchParent(value);
                 }else{
                     return null;
                 }
             }
         }

    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

}
