package org.pjj.datastructure.tree.rbtree;

/**
 * 红黑树
 * @author PengJiaJun
 * @Date 2022/07/14 13:10
 */
public class BRTree {

    private static final boolean RED = false;
    private static final boolean BLACK = true;

    //红黑树的根节点
    private RBNode root;

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
     * @param p 需要进行左旋转的节点, 即 p 的左子数高度为 1, 右子树高度为 3, 即右子树 - 左子数 >= 2
     */
    private void leftRotate(RBNode p) {
        if(p != null) {
            /**
             * pr-rl 调整为 p-rl
             */
            RBNode pOldRight = p.right;//先将p原有的 右子树保存, 因为等会会将 p.right置为其他值, 如不保存, 则原有右子树就丢失了
            p.right = pOldRight.left;//将 p 的右子树 置为 p原有右子树的左子树 (即 pr-rl 调整为 p-rl)这一步操作
            if(p.right != null) {
                // 如果 pOldRight.left == null, 则将 null 赋值给 p.right 也是没毛病的, 但是如果null.parent 就会报空指针了, 所以这是该if的作用
                p.right.parent = p;
            }

            /**
             * 2. 判断 p 是否有父节点
             */
            if(p.parent != null) {
                if(p.parent.left == p) {
                    p.parent.left = pOldRight;
                }else{
                    p.parent.right = pOldRight;
                }
            }else {
                root = pOldRight;
            }
            //不管 p 是否存在父节点, 都设置p的父节点为 pR的父节点
            // 两种情况: 1. p.parent为null, 那么pR.parent = null (既然p.parent=null说明p是根节点, 那么pR就是新的根节点, 根节点的parent指向null有什么问题嘛)
            //         2. p.parent!=null, 那么pR.parent = p.parent(既然p.parent!=null 那么pR都准备替代p了, 那么p的父节点就是pr的父节点)
            pOldRight.parent = p.parent;

            /**
             * 最后将 p 和 pr交换 (感觉说交换不准确)
             */
            pOldRight.left = p;
            p.parent = pOldRight;
        }


    }

    /**
     * 右旋转, 与左旋转一样, 可以参考左旋转
     * 其实可谓是和左旋转一模一样, 区别就是 方向相反, left => right, right => left
     *
     * @param p 需要进行右旋转的节点, 即 p 的左子数高度为 3, 右子树高度为 1, 即左子树 - 右子数 >= 2
     */
    public void rightRotate(RBNode p) {
        if(p != null) {
            RBNode pOldLeft = p.left;
            p.left = pOldLeft.right;
            if(p.left != null) {
                p.left.parent = p;
            }

            if(p.parent != null) {
                if(p.parent.left == p) {
                    p.parent.left = pOldLeft;
                }else {
                    p.parent.right = pOldLeft;
                }
            }else {
                root = pOldLeft;
            }
            pOldLeft.parent = p.parent;

            pOldLeft.right = p;
            p.parent = pOldLeft;
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

        public RBNode(RBNode parent, RBNode left, RBNode right, boolean color, K key, V value) {
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
            this.key = key;
            this.value = value;
        }

        public RBNode(K key, V value, RBNode parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public RBNode getParent() {
            return parent;
        }

        public void setParent(RBNode parent) {
            this.parent = parent;
        }

        public RBNode getLeft() {
            return left;
        }

        public void setLeft(RBNode left) {
            this.left = left;
        }

        public RBNode getRight() {
            return right;
        }

        public void setRight(RBNode right) {
            this.right = right;
        }

        public boolean isColor() {
            return color;
        }

        public void setColor(boolean color) {
            this.color = color;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

}