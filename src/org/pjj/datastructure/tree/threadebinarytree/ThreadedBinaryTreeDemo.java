package org.pjj.datastructure.tree.threadebinarytree;

/**
 * 线索二叉树
 * @author PengJiaJun
 * @Date 2020/8/15 8:00
 */
public class ThreadedBinaryTreeDemo {

    public static void main(String[] args) {
        HeroNode2 root = new HeroNode2(1,"tom");
        HeroNode2 node2 = new HeroNode2(3,"jack");
        HeroNode2 node3 = new HeroNode2(6,"smith");
        HeroNode2 node4 = new HeroNode2(8,"mary");
        HeroNode2 node5 = new HeroNode2(10,"king");
        HeroNode2 node6 = new HeroNode2(14,"dim");

        //二叉树, 后面会讲递归创建, 现在先简单使用手动创建
        root.setLeft(node2);
        root.setRight(node3);
        node2.setLeft(node4);
        node2.setRight(node5);
        node3.setLeft(node6);

        //测试 中序线索化二叉树
        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree();
        threadedBinaryTree.setRoot(root);
        threadedBinaryTree.threadedNodes(root);

        //测试: 以10号节点测试
        HeroNode2 leftNode = node5.getLeft();
        HeroNode2 rightNode = node5.getRight();
        System.out.println("10号节点的前驱节点是=" + leftNode);
        System.out.println("10号节点的后继节点是=" + rightNode);

        //测试 中序遍历 线索二叉树
        System.out.println("\n中序遍历 中序线索化二叉树:(自己写的)");
        threadedBinaryTree.threadedPreOrder(root);

        System.out.println("\n使用线索化方式遍历 线索化二叉树:(老师写的)");
        threadedBinaryTree.threadedList();
    }

}

class ThreadedBinaryTree {
    private HeroNode2 root; //根节点

    //为了实现线索化, 需要创建要给当前节点的前驱节点的指针
    //在递归进行线索化时, pre 总是保留前一个节点
    private HeroNode2 pre = null;

    public void setRoot(HeroNode2 root) {
        this.root = root;
    }


    /**
     * 编写对二叉树进行中序线索化的方法
     * @param node 就是当前需要线索化的节点
     */
    public void threadedNodes(HeroNode2 node){
        //如果node == null, 不能线索化
        if(node == null){
            return;
        }

        //1.线索化左子树
        threadedNodes(node.getLeft());

        //2.线索化当前节点
        if(node.getLeft() == null){ //如当前节点的左指针指向空
            //让当前节点的左指针指向前驱节点
            node.setLeft(pre);
            node.setLeftType(1);//修改当前节点的左指针的类型
        }

        //处理后继节点
        if(pre != null && pre.getRight() == null){
            //让前驱节点右指针指向当前节点
            pre.setRight(node);
            pre.setRightType(1);//修改前驱节点的右指针的类型
        }

        // !!! 处理每个节点后, 让当前节点是下一个节点的前驱节点
        pre = node;

        //3.线索化右子树
        threadedNodes(node.getRight());

    }

    /**
     * 当二叉树被线索化后变成了 线索二叉树后, 就不能使用 原来遍历二叉树的方法来遍历 线索二叉树了..
     * 中序遍历线索化二叉树 (自己写的)
     */
    public void threadedPreOrder(HeroNode2 node2){

        if(node2 == null){
            System.out.println("索化二叉树为空,不能中序遍历...");
            return;
        }

        if(node2.getLeft() != null && node2.getLeftType() == 0){
            threadedPreOrder(node2.getLeft());
        }

        System.out.println(node2);

        if(node2.getRight() != null && node2.getRightType() == 0){
            threadedPreOrder(node2.getRight());
        }

    }

    /**
     * 当二叉树被线索化后变成了 线索二叉树后, 就不能使用 原来遍历二叉树的方法来遍历 线索二叉树了..
     * 遍历线索二叉树的方法 (老师写的)
     */
    public void threadedList(){
        //定义一个变量, 储存当前遍历的节点, 从root开始
        HeroNode2 node = root;
        while(node != null){
            //循环的找到leftType == 1的节点, 第一个找到的节点就是 8
            //后面node随着遍历而变化, 因为当leftType == 1时, 说明该节点是按照线索化处理后的有效节点
            while(node.getLeftType() == 0){
                node = node.getLeft();
            }

            //打印当前节点
            System.out.println(node);

            //如果当前节点的右指针指向的是后继节点, 就一直输出
            while(node.getRightType() == 1){
                //获取到当前节点的后继节点
                node = node.getRight();
                System.out.println(node);
            }
            //替换这个遍历的节点
            node = node.getRight();

        }
    }





    /**
     * 前序遍历
     */
    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空,无法进行前序遍历");
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if (this.root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("二叉树为空,无法进行中序遍历");
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        } else {
            System.out.println("二叉树为空,无法进行后序遍历");
        }
    }

    /**
     * 前序遍历查找
     * @param no
     * @return
     */
    public HeroNode2 preOrderSearch(int no){
        if(root != null){
            return root.preOrderSearch(no);
        }else{
            return null;//根节点都是空,这....二叉树都没有 还找个鸡儿,直接返回null 没找到
        }
    }

    /**
     * 中序遍历查找
     * @param no
     * @return
     */
    public HeroNode2 infixOrderSearch(int no){
        if(root != null){
            return root.infixOrderSearch(no);
        }else{
            return null;//根节点都是空,这....二叉树都没有 还找个鸡儿,直接返回null 没找到
        }
    }

    /**
     * 后序遍历查找
     * @param no
     * @return
     */
    public HeroNode2 postOrderSearch(int no){
        if(root != null){
            return root.postOrderSearch(no);
        }else{
            return null;//根节点都是空,这....二叉树都没有 还找个鸡儿,直接返回null 没找到
        }
    }

    /**
     * 删除节点
     * @param no
     */
    public void deleteNode(int no){
        //先判断root是否为空, 如为空, 则说明这个二叉树是个空数, 还删个鸡儿节点...
        if(root != null){
            //先判断root是否是需要删除的节点, 如果不是再调用root.deleteNode(no);
            //因为root.deleteNode(no)不会判断root是否是需要删除的节点,只会从root的子节点开始判断是否是需要删除的节点
            if(root.getNo() == no){
                root = null;
                System.out.println("以将编号为 "+no+" 的节点删除");
            }else{
                int flag = root.deleteNode(no);
//                int flag = root.deleteNodePlus(no);
                if(flag == 1){
                    System.out.println("编号为 "+no+" 的节点以被删除");
                }else{
                    System.out.println("没有找到编号为 "+no+" 的节点");
                }
            }

        }else{
            System.out.println("二叉树为空树,无法删除 "+no+" 节点");
        }
    }
}

//先创建HeroNode节点
class HeroNode2 {
    private int no;             //节点编号
    private String name;        //节点姓名
    private HeroNode2 left;      //该节点的左子节点, 默认为null
    private HeroNode2 right;     //该节点的右子节点, 默认为null

    //说明: 线索二叉树  就是在 二叉树 上的叶子节点上 空余的左右指针上 指向该节点的前驱节点(左)和后继结点(右)
    //那么就等于 左指针有可能正常指向左子树, 也有可能指向前驱节点, 右指针有可能指向右子树, 也有可能指向后继节点
    //所以定义两个变量来区分, 究竟是指向左子树,还是指向前驱节点
    //1.如果leftType == 0; 表示指向左子树, 如果leftType == 1; 表示指向前驱节点
    //2.如果rightType == 0; 表示指向右子树, 如果rightType == 1; 表示指向后继节点
    private int leftType;//默认为 0; 也就是说默认指向左子树
    private int rightType;

    public HeroNode2(int no, String name) {
        this.no = no;
        this.name = name;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        System.out.println(this);//先输出父节点(当前节点)
        //递归向左子树遍历
        if (this.left != null) {
            this.left.preOrder();
        }
        //递归向右子树遍历
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        //递归向左子树中序遍历
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);//输出当前节点
        //递归向右子树中序遍历
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        //递归向左子树后序遍历
        if (this.left != null) {
            this.left.postOrder();
        }
        //递归向右子树后序遍历
        if (this.right != null) {
            this.right.postOrder();
        }
        System.out.println(this);//输出当前节点
    }

    /**
     * 前序遍历查找
     *
     * @param no 需要查找的节点编号
     * @return 找到了返回该节点, 找不到返回null
     */
    public HeroNode2 preOrderSearch(int no) {
        HeroNode2 resNode = null;

        System.out.println("前序遍历查找一次");
        //判断当前节点编号 是否等于 用户需要查找的 节点编号, //如等于 则将该当前节点返回, //如不等于 继续前序遍历二叉树
        if (this.no == no) {
            return this;
        }

        //递归调用前序遍历  继续遍历当前节点的左子树节点
        if (this.left != null) {//左子节点不为空就遍历左子节点
            resNode = this.left.preOrderSearch(no);
        }
        if (resNode != null) { //说明左子树找到了 用户需要查找的节点
            return resNode;
        }

        //递归调用前序遍历 继续遍历当前节点右子节点
        if (this.right != null) {
            resNode = this.right.preOrderSearch(no);
        }
        //不管右子树找没找到 都要返回  找到了就返回找到的节点resNode, 找不到就返回resNode也是null
        return resNode;
    }

    //不看老师写的代码, 自己写的前序遍历查找方法
    public HeroNode2 preOrderSearch(int no,String my) {
        HeroNode2 heroNode = null;

        if(this.no == no){
            return this;
        }

        if(this.left != null){
            heroNode = this.left.preOrderSearch(no, "LaoPeng");
            if(heroNode != null){
                return heroNode;
            }
        }

        if(this.right != null){
            heroNode = this.right.preOrderSearch(no,"LaoPeng");
            if(heroNode != null){
                return  heroNode;
            }
        }
        return heroNode;//到这里返回实际上已经就是返回null了;
    }

    /**
     * 中序遍历查找
     *
     * @param no
     * @return
     */
    public HeroNode2 infixOrderSearch(int no) {
        HeroNode2 resNode = null;

        //判断当前节点的左子节点是否为空, 如果不为空, 则递归中序查找
        if (this.left != null) {
            resNode = this.left.infixOrderSearch(no);
        }
        if(resNode != null){
            return resNode;
        }

        System.out.println("中序遍历查找一次");
        //如果当前节点编号等于 用户需要查找的编号, 则直接将当前节点返回
        if (this.no == no) {
            return this;
        }

        //判断当前节点的右子节点是否为空, 如果不为空, 则递归中序查找
        if(this.right != null){
            resNode = this.right.infixOrderSearch(no);
        }
        return resNode;//不管右子树找没找到 都要返回  找到了就返回找到的节点resNode, 找不到就返回resNode也是null
    }

    /**
     * 后序遍历查找
     * @param no
     * @return
     */
    public HeroNode2 postOrderSearch(int no) {
        HeroNode2 resNode = null;

        //判断当前节点的左子节点是否为空, 如果不为空, 则递归后序查找
        if(this.left != null){
            resNode = this.left.postOrderSearch(no);
        }
        if(resNode != null){
            return resNode;
        }

        //判断当前节点的右子节点是否为空, 如果不为空, 则递归后序查找
        if(this.right != null){
            resNode = this.right.postOrderSearch(no);
        }
        if(resNode != null){
            return resNode;
        }

        System.out.println("后序遍历查找一次");
        //如果当前节点编号等于 用户需要查找的编号, 则直接将当前节点返回
        if(this.no == no){
            return this;
        }

        return resNode;//如果左右子树 和 根节点都没有找到 就返回resNode  (实际上到这里才返回resNode,那么这个resNode就已经是null了)
    }

    /**
     * 删除节点
     * 1.如果删除的是叶子节点, 则直接删除
     * 2.如果删除的是非叶子节点, 则也直接删除(虽然这样算是删除了一个子树,但是此处为了简单就是这样,之后再讲如果真正删除叶子节点)
     * 思路:
     * 1.因为我们的二叉树是单向的，使用我们判断的是当前节点的子节点是否是需要删除的节点，就像单链表删除节点一样，因为如果直
     * 接指向了需要删除的节点我们实际上是没有办法将它删除的，只有指向待删除节点的父节点，父节点将待删除的节点置为null.
     * 2.如果当前节点的左子节点不为空，并且左子节点就是要删除的节点，将将this.left= null;然后返回(结束递归)
     * 3.如果当前节点的右子节点不为空，并且右子节点就是要删除的节点，将将this.right= null;然后返回(结束递归)
     * 4.如果第2和第3步没有删除节点，那么我们就要向左子树进行递归删除
     * 5.如果第4步也没有删除节点，则应当向右子树进行递归删除
     * @param no
     */
    public int deleteNode(int no){

        //2.如果当前节点的左子节点不为空，并且左子节点就是要删除的节点，将将this.left= null;然后返回(结束递归)
        if(this.left != null && this.left.no == no){
            this.left = null;
            return 1;
        }

        //3.如果当前节点的右子节点不为空，并且右子节点就是要删除的节点，将将this.right= null;然后返回(结束递归)
        if(this.right != null && this.right.no == no){
            this.right = null;
            return 1;
        }

        //4.如果第2和第3步没有删除节点，那么我们就要向左子树进行递归删除
        if(this.left != null){
            int flag = this.left.deleteNode(no);
            if(flag == 1){
                return 1;
            }
        }

        //5.如果第4步也没有删除节点，则应当向右子树进行递归删除
        if(this.right != null){
            int flag = this.right.deleteNode(no);
            if(flag == 1){
                return 1;
            }
        }
        return -1;
    }

    /**
     * 删除节点
     * 如果是叶子节点则直接删除
     * 如果是非叶子节点 则将该节点删除,然后用该节点的左子节点代替刚才被删除的节点, 如果没有左子节点就用右子节点
     *
     * 错误: 是可以将待删除节点的左子节点提上来代替待删除节点(作用就相当于删除了待删除节点)
     * 但是 待删除节点的左子节点提上来代替了待删除节点  那么如果待删除节点有右子节点,那么该右子节点就丢失了....
     * 尝试过 先将待删除节点的右子节点 先赋值给 准备提上来代替待删除节点的节点的右子节点 这样 替换调待删除节点后,原本的已经删除的了右子节点就
     * 还是在的, 但是没有成功, 而且就算可以, 那么准备提上来代替待删除节点的节点的右子节点岂不丢失了?
     * 所以还是等 讲 二叉排序树时,再听老师详细讲解...
     * @param no
     * @return
     */
    public int deleteNodePlus(int no){

        if(this.left != null && this.left.no == no){//找到了需要删除的节点 this.left
            if(this.left.left == null && this.left.right == null){//判断this的左子节点,是否还有左子节点和右子节点
                //如果 this.left 没有左子节点也没有右子节点,那么this.left就是个 叶子节点
                this.left = null;//直接删除
                return 1;
            }else{
                //如果this.left 有 左子节点 或 有右子节点 或 两个都有 则this.left是个非叶子节点,该this.left有子节点
                if(this.left.left != null){//如果需要删除的节点this.left有左子节点,则让左子节点代替this.left的位置,就相当于删除了this.left
                    //this.left.left.right = this.left.right;//先将原本指向this.left的this.left.right 指向 即将代替this.left的this.left.left
                    //不知道为什么 这句不起作用...
                    this.left = this.left.left;
                    return 1;
                }else{//如果this.left没有左子节点, 那么到这里就一定有右子节点, 要不然不会进入外层的else的
                    //既然this.left没有左子节点, 就让this.left的右子节点代替this.left的位置,就相当删除了this.left
                    this.left = this.left.right;
                    return 1;
                }
            }
        }

        if(this.right != null && this.right.no == no){
            if(this.right.left == null && this.right.right == null){//判断this的右子节点,是否还有左子节点和右子节点
                //如果 this.right 没有左子节点也没有右子节点,那么this.right就是个 叶子节点
                this.right = null;//直接删除
                return 1;
            }else{
                //如果this.right 有 左子节点 或 有右子节点 或 两个都有 则this.right是个非叶子节点,该this.right有子节点
                if(this.right.left != null){//如果需要删除的节点this.right有左子节点,则让左子节点代替this.right的位置,就相当于删除了this.right
                    this.right = this.right.left;
                    return 1;
                }else{//如果this.right没有左子节点, 那么到这里就一定有右子节点, 要不然不会进入外层的else的
                    //既然this.right没有左子节点, 就让this.right的右子节点代替this.right的位置,就相当删除了this.right
                    this.right = this.right.right;
                    return 1;
                }
            }
        }

        if(this.left != null){
            int flag = this.left.deleteNode(no);
            if(flag == 1){
                return 1;
            }
        }

        if(this.right != null){
            int flag = this.right.deleteNode(no);
            if(flag == 1){
                return 1;
            }
        }
        return -1;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode2 getLeft() {
        return left;
    }

    public void setLeft(HeroNode2 left) {
        this.left = left;
    }

    public HeroNode2 getRight() {
        return right;
    }

    public void setRight(HeroNode2 right) {
        this.right = right;
    }

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}
