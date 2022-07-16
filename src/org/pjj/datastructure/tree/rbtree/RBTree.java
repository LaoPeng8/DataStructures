package org.pjj.datastructure.tree.rbtree;

/**
 * 红黑树的性质
 *      1. 每个节点要么是黑色, 要么是红色
 *      2. 根节点是黑色
 *      3. 每个叶子节点(NIL)是黑色
 *      4. 每个红色节点的两个子节点一定都是黑色, 不能有两个红色节点相连
 *      5. 任意一节点到每个叶子节点的路径都包含数量相同的黑节点. 俗称: 黑高 (黑节点的高度)
 *      5.1 如果一个节点存在黑子节点, 那么该节点肯定有两个子节点
 *
 * 终于明白了 插入时为什么要判断 是否有叔叔节点了, 其实就是判断被插入的节点是一个 3节点 还是4节点
 * 插入节点为红色节点(这是必须的), 如果插入节点的父节点也是红色, 则需要继续判断, 插入节点的父节点有没有兄弟节点 (兄弟节点 非红即空)
 *      如果有, 则需要将 父节点与兄弟节点颜色翻转(即置为黑色), 然后插入节点的爷爷节点颜色翻转(即置为红色), 然后以爷爷节点为当前节点递归往上处理
 *      如果没有, 则说明爷爷节点(黑色) + 父亲节点(红色) 组合起来成为一个 三节点, 那么当前节点加入则让这个组合成为 四节点, 那么四节点也是可以存在的
 *              即 只需要判断 父亲节点是爷爷节点左子节点还是右子节点, 然后继续判断 插入节点是父亲节点的左子节点还是右子节点, 然后进行左旋右旋即可
 *              如 爷爷节点(黑色)的左子节点为父亲节点(红色), 父亲节点的左子节点为当前插入节点(红色), 则对爷爷节点进行右旋, 同时将 父亲节点爷爷节点颜色翻转
 *              右旋后则成了, 父亲节点(黑色) 的左子节点为当前插入节点, 右子节点为 爷爷节点(红色)
 *
 * @author PengJiaJun
 * @Date 2022/07/16 00:56
 */
public class RBTree<K extends Comparable<K>, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private RBNode root;

    /**
     * 获取当前节点父节点
     * @param node
     * @return
     */
    private RBNode parentOf(RBNode node) {
        return node != null ? node.parent : null;
    }

    /**
     * 判断当前节点颜色 是否为红色
     * @param node
     * @return
     */
    private boolean isRed(RBNode node) {
        return node != null && node.color == RED;
    }

    /**
     * 判断当前节点颜色 是否为黑色
     * @param node
     * @return
     */
    private boolean isBlack(RBNode node) {
        return node != null && node.color == BLACK;
    }

    /**
     * 设置节点颜色为红色
     * @param node
     */
    private void setRed(RBNode node) {
        if(node != null)
            node.color = RED;
    }

    /**
     * 设置节点颜色为黑色
     * @param node
     */
    private void setBlack(RBNode node) {
        if(node != null)
            node.color = BLACK;
    }


    /**
     * 中序打印二叉树
     */
    public void inOrderPrint() {
        this.inOrderPrint(root);
    }
    private void inOrderPrint(RBNode node) {
        if(node != null) {
            inOrderPrint(node.left);
            System.out.println("key = " + node.key +", value = " + node.value);
            inOrderPrint(node.right);
        }
    }

    /**
     * 左旋转
     *
     * 假设是讲这样的一颗二叉树进行左旋转, 即图左 变为 图右 的一个过程 (实际上这个树是平衡的, 不需要左旋转, 此处只是举例)
     *    p                    pr
     *   / \                  / \
     *  pl  pr      ==>      p   rr
     *     / \              / \
     *   rl   rr           pl  rl
     *
     * 左旋操作:
     *      p-pl , pr-rr 的关系不用调整
     *      需要调整的节点:
     *      1. pr-rl 调整为 p-rl
     *          将 rl 置为 p 的右子节点
     *          将 rl 的父节点置为 p
     *      2. 判断 p 是否有父节点
     *          有
     *              pr.parent = p.parent
     *              pr作为 p.parent的子节点, 到底是左子节点 还是 右子节点?
     *              if p.parent.left == p
     *                  p.parent.left == pr
     *              else
     *                  p.parent.right == pr
     *          没有
     *              直接将 pr 设置为 root 节点
     *      3. 最后将 p 和 pr交换 (感觉说交换不准确)
     *          p.parent = pr
     *          pr.left = p
     *
     * 这只是普通的平衡二叉树的左旋, 那么红黑树的左旋可能存在变色
     *
     * @param node 需要进行左旋转的节点, 即 p 的左子数高度为 1, 右子树高度为 3, 即右子树 - 左子数 >= 2
     */
    public void leftRotate(RBNode node) {
        //1. 将当前节点的值 指向当前节点的右子节点的左子节点
        RBNode oldRight = node.right;//先将原有的 右子树保存, 因为等会会将 p.right置为其他值, 如不保存, 则原有右子树就丢失了
        node.right = oldRight.left;//当前节点的右子节点  指向旧的右子节点的左子节点
        if(node.right != null) {
            // 如果 pOldRight.left == null, 则将 null 赋值给 p.right 也是没毛病的, 但是如果null.parent 就会报空指针了, 所以这是该if的作用
            node.right.parent = node;
        }

        //2. 将 当前节点的右子节点往上提, 即成为新的根节点 (如果当前节点有父节点, 则让当前节点的父节点指向 当前节点的右子节点)
        if(node.parent == null) {
            // 如果 node节点的父节点为null, 说明node是根节点, 那么此时将 oldRight置为根节点
            root = oldRight;
            oldRight.parent = null;
        }else {
            // 如果 node节点的父节点不为null, 则需要继续判断, node节点是属于它父节点的 左子节点还是右子节点
            if(node.parent.left == node) {
                node.parent.left = oldRight;
                oldRight.parent = node.parent;
            }else {
                node.parent.right = oldRight;
                oldRight.parent = node.parent;
            }
        }

        //3. 新的根节点的左子节点 指向 当前节点 (因为新的根节点的左子节点在第1步中实际是赋值给了 node的右子节点)
        oldRight.left = node;
        node.parent = oldRight;
    }

    public void rightRotate(RBNode node) {
        RBNode oldLeft = node.left;
        node.left = oldLeft.right;
        if(node.left != null) {
            node.left.parent = node;
        }

        if(node.parent == null) {
            root = oldLeft;
            oldLeft.parent = null;
        }else {
            if(node.parent.left == node) {
                node.parent.left = oldLeft;
                oldLeft.parent = node.parent;
            }else {
                node.parent.right = oldLeft;
                oldLeft.parent = node.parent;
            }
        }

        oldLeft.right = node;
        node.parent = oldLeft;
    }

    /**
     * 公开的插入方法
     * @param key
     * @param value
     */
    public void put(K key, V value) {
        RBNode<K, V> node = new RBNode<>(key, value);
        node.color = RED;//新插入节点一定是红色

        insert(node);
    }

    private void insert(RBNode node) {
        if(root == null) {//如果root节点为空, 则插入的节点就赋给root, 且当前节点的父节点为null, 颜色必须为黑
            root = node;
            node.parent = null;
            node.color = BLACK;
            return;
        }
        // 1. 找到当前node节点的父节点, 即找到当前node节点应该插入到哪
        RBNode parent = null;
        RBNode temp = root;
        int cmp = 0;
        while(temp != null) {
            parent = temp;//保存父节点

            cmp = node.key.compareTo(temp.key);//保存与父节点的关系, 是为左还是为右
            if(cmp > 0) {// node节点 > 当前遍历节点, 说明node节点应该存在当前遍历节点的右边, 则继续遍历, 直至temp为空, 跳出while, 此时parent就是node的父节点
                temp = temp.right;
            }else if(cmp < 0) {// node节点 < 当前遍历节点, 说明node节点应该存在当前遍历节点的左边, 则继续遍历, 直至temp为空, 跳出while, 此时parent就是node的父节点
                temp = temp.left;
            }else {// cmp == 0
                temp.value = node.value;//替换值, 然后退出插入方法 (key值重复, 则不用插入了, 只是替换key值重复node的value即可)
                return;
            }
        }

        //退出while之后, 说明node已经找到插入的地方了, parent则是node的父节点 且知道与父节点的关系 cmp, 是为左还是为右
        // 2. 插入节点
        if(cmp > 0) {
            parent.right = node;
            node.parent = parent;
        }else {
            parent.left = node;
            node.parent = parent;
        }

        // 3. 旋转和变色 调整红黑树的平衡
        insertFixup(node);
    }

    /**
     * 插入后修复红黑树平衡的方法
     * |---情况1:红黑树为空树                                                 这种情况, 只需要将插入的节点置为黑色
     * |---情况2:插入节点的key已经存在                                         这种情况, 只需要将key对应的node的value值替换掉即可(insert()方法中已经处理了)
     * |---情况3:插入节点的父节点为黑色                                         这种情况, 插入节点为红节点, 且父节点为黑色, 并没有破坏红黑树黑色节点平衡, 不需要调整
     *
     * 情况4需要咱们去处理
     * |---情况4:插入节点的父节点为红色
     *      |---情况4.1: 叔叔节点存在，并且为红色(父-叔 双红)                    这种情况需要将父-叔-爷节点颜色翻转, 即父黑,叔黑,爷红, 然后爷爷节点是红色, 可能爷爷节点的父节点也是红色, 那么就形成了双红, 所以需要以爷爷节点为当前节点继续递归向上修复平衡
     *      |—--情况4.2:叔叔节点不存在，或者为黑色，父节点为爷爷节点的左子树
     *          |—--情况4.2.1:插入节点为其父节点的左子节点(LL情况)               这种情况需要将父-爷节点颜色翻转, 即父黑,爷红, 然后根据爷爷节点 进行右旋即可
     *          |---情况4.2.2:插入节点为其父节点的右子节点(LR情况)               这种情况需要以父节点为当前节点进行左旋, 这样即得到了 4.2.1的情况 (注意: 旋转后 父子关系实际上是发生了转变 原本是父在上,子在下, 旋转后 子在上,父在下)
     *      |---情况4.3:叔叔节点不存在，或者为黑色，父节点为爷爷节点的右子树        这种情况实际跟4.2是一样的, 只是方向相反
     *          |---情况4.3.1:插入节点为其父节点的右子节点(RR情况)
     *          |—--情况4.3.2:插入节点为其父节点的左子节点(RL情况)
     */
    private void insertFixup(RBNode node) {
        this.root.color = BLACK;//处理 (情况1)

        RBNode parent = parentOf(node);//获取当前node节点的父节点
        RBNode grandParent = parentOf(parent);//获取当前node的爷爷节点

        //当前node节点的父节点 必须是红色才会进行处理  (情况4)
        if(parent != null && isRed(parent)) {
            RBNode uncle = null;//叔叔节点
            if(parent == grandParent.left) {//父节点为左子节点
                uncle = grandParent.right;//如果当前node节点的父节点, 在爷爷节点中属于左子节点, 那么叔叔节点肯定就是右子节点了, 反之亦然

                /**
                 * 情况4.1: 叔叔节点存在，并且为红色(父-叔 双红)
                 *
                 * 这种情况需要, 即父黑,叔黑,爷红, 然后爷爷节点是红色, 可能爷爷节点的父节点也是红色, 那么就形成了双红,
                 * 所以需要以爷爷节点为当前节点继续递归向上修复平衡
                 */
                if(uncle != null && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(grandParent);

                    insertFixup(grandParent);
                    return;
                }else {// uncle == null || isBlack(uncle)
                    /**
                     * 情况4.2:叔叔节点不存在，或者为黑色，父节点为爷爷节点的左子树 (这里已经满足了 父节点为爷爷节点的左子树, 即该方法的第二个if)
                     *
                     * 情况4.2.2:插入节点为其父节点的右子节点(LR情况)               这种情况需要以父节点为当前节点进行左旋, 这样即得到了 4.2.1的情况
                     * 需要注意的是 旋转前: 父在上, 子在下.  旋转后: 子在上, 父在下.
                     */
                    if(node == parent.right) {//如果当前节点为父节点的右子节点, 则需要先根据父节点左旋, 变成4.2.1的情况再进行处理
                        leftRotate(parent);

                        //旋转前: 父在上, 子在下.  旋转后: 子在上, 父在下.
                        //所以需要将 关系互换一下, 方便后面设置颜色; 想像一下如果不互换, 则4.2.1设置节点颜色 父黑,爷红 时 岂不是把子节点设置为黑色, 真正的父节点还是红色
                        RBNode temp = node;
                        node = parent;
                        parent = temp;
                    }

                    /**
                     * 情况4.2.1:插入节点为其父节点的左子节点(LL情况)
                     * 这种情况需要将父-爷节点颜色翻转, 即父黑,爷红, 然后根据爷爷节点 进行右旋即可
                     */
                    setBlack(parent);
                    setRed(grandParent);
                    rightRotate(grandParent);
                }

            }else {//parent == grandParent.right 父节点为右子节点 (判断左右时, 旋转时, 与上面if中内容完全相反即可)
                uncle = grandParent.left;//如果当前node节点的父节点, 在爷爷节点中属于右子节点, 那么叔叔节点肯定就是左子节点了, 反之亦然

                /**
                 * 情况4.1: 叔叔节点存在，并且为红色(父-叔 双红)
                 *
                 * 这种情况需要, 即父黑,叔黑,爷红, 然后爷爷节点是红色, 可能爷爷节点的父节点也是红色, 那么就形成了双红,
                 * 所以需要以爷爷节点为当前节点继续递归向上修复平衡
                 */
                if(uncle != null && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(grandParent);

                    insertFixup(grandParent);
                    return;
                }else {// uncle == null || isBlack(uncle)
                    /**
                     * 情况4.2:叔叔节点不存在，或者为黑色，父节点为爷爷节点的右子树 (这里已经满足了 父节点为爷爷节点的左子树, 即该方法的第二个if)
                     *
                     * 情况4.3.2:插入节点为其父节点的左子节点(RL情况)               这种情况需要以父节点为当前节点进行右旋, 这样即得到了 4.3.1的情况
                     * 需要注意的是 旋转前: 父在上, 子在下.  旋转后: 子在上, 父在下.
                     */
                    if(node == parent.left) {//如果当前节点为父节点的左子节点, 则需要先根据父节点右旋, 变成4.3.1的情况再进行处理
                        rightRotate(parent);

                        //旋转前: 父在上, 子在下.  旋转后: 子在上, 父在下.
                        //所以需要将 关系互换一下, 方便后面设置颜色; 想像一下如果不互换, 则4.3.1设置节点颜色 父黑,爷红 时 岂不是把子节点设置为黑色, 真正的父节点还是红色
                        RBNode temp = node;
                        node = parent;
                        parent = temp;
                    }

                    /**
                     * 情况4.3.1:插入节点为其父节点的左子节点(RR情况)
                     * 这种情况需要将父-爷节点颜色翻转, 即父黑,爷红, 然后根据爷爷节点 进行左旋即可
                     */
                    setBlack(parent);
                    setRed(grandParent);
                    leftRotate(grandParent);
                }
            }
        }


    }






    static class RBNode<K extends Comparable<K>, V> {

        private RBNode parent;//当前节点的父节点

        private RBNode left;//当前节点的左子节点

        private RBNode right;//当前节点的右子节点

        private boolean color;//当前节点的颜色(RED, BLACK)

        private K key;

        private V value;

        public RBNode() {
        }

        public RBNode(K key, V value) {
            this.key = key;
            this.value = value;

            if(this.value == null) {
                this.value = (V) this.key;
            }
        }

        public RBNode(K key, V value, RBNode parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;

            if(this.value == null) {
                this.value = (V) this.key;
            }
        }

        public RBNode getLeft() {
            return left;
        }

        public RBNode getRight() {
            return right;
        }

        public RBNode getParent() {
            return parent;
        }

        public boolean isColor() {
            return color;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    public RBNode getRoot() {
        return root;
    }
}
