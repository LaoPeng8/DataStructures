package org.pjj.datastructure.tree.rbtree;

/**
 * 这是昨天那个老师教的, 一堆小问题的代码, 今天给他改好了
 * 这个代码大致逻辑没错, 但是它没讲清楚, 今天从另外一个老师那里听的红黑树, 给昨天这个代码改好了
 *
 * @author PengJiaJun
 * @Date 2022/07/16 12:52
 */
public class RBTree_Fail<K extends Comparable<K>, V> {

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

    /**
     * 红黑树 新增节点
     * @param key
     * @param value
     */
    public V put(K key, V value) {
        RBNode<K, V> node = new RBNode<K, V>(key, value);//先new好 需要插入的节点
        if(root == null) {
            node.parent = null;
            node.color = BLACK;//根节点肯定是黑色的

            root = node;
            return null;
        }
        // 1. 找到插入的位置 (找到新增节点的父节点)
        RBNode temp = root;
        RBNode parent = null;
        int flag = 0;
        while(temp != null) {
            flag = key.compareTo((K) temp.key);
            if(flag > 0) {// 当前 key > 当前遍历节点的key, 则说明当前key应是插入当前遍历节点key的右边, 则继续往右遍历 (左小右大)
                parent = temp;//保存父节点
                temp = temp.right;
            }else if(flag < 0) {// 当前 key < 当前遍历节点的key, 则说明当前key应是插入当前遍历节点key的左边, 则继续往左遍历 (左小右大)
                parent = temp;//保存父节点
                temp = temp.left;
            }else {
                // key 值重复
                // 覆盖value值, 并返回旧的value值
                V oldValue = (V) temp.value;
                temp.value = value;
                return oldValue;
            }
        }
        //退出while到这里说明已经找到当前key, 应该插入的父节点了, 且知道与父节点的关系 flag, 是为左还是为右
        // 2. 将新节点添加到父节点的子节点中
        if(flag > 0) {
            node.parent = parent;
            node.color = RED;
            parent.right = node;
        }else {
            node.parent = parent;
            node.color = RED;
            parent.left = node;
        }

        // 3. 旋转和变色 调整红黑树的平衡
        fixAfterPut(node);

        return null;
    }

    private RBNode parentOf(RBNode node) {
        return node == null ? null : node.parent;
    }

    private RBNode leftOf(RBNode node) {
        return node == null ? null : node.left;
    }

    private RBNode rightOf(RBNode node) {
        return node == null ? null : node.right;
    }

    private boolean colorOf(RBNode node) {
        return node == null ? BLACK : node.color;
    }

    private void setColor(RBNode node, boolean color) {
        if(node != null)
            node.color = color;
        return;
    }

    /**
     * 插入节点到 二节点 是不需要调整的, 二节点为黑色, 插入节点为红色
     * 插入节点到 三节点 一共有6种情况, 两种情况是不需要调整的, 即 一个黑节点,左右各一个红节点(即插入的节点为 左节点 与 插入节点为右节点 两种情况)
     *                另外4种情况需要调整, 即 一个黑节点, 左子节点为红节点, 左子节点的左子节点也是红节点(即插入的节点), 这种情况是需要调整的
     *                                     一个黑节点, 左子节点为红节点, 左子节点的右子节点也是红节点(即插入的节点), 这种情况是需要调整的
     *                                     一个黑节点, 右子节点为红节点, 右子节点的右子节点也是红节点(即插入的节点), 这种情况是需要调整的
     *                                     一个黑节点, 右子节点为红节点, 右子节点的左子节点也是红节点(即插入的节点), 这种情况是需要调整的
     *                实际相当于只有两种情况, 另外两种不过是方向相反, 继续调整时, 左旋右旋的方法也相反
     * 插入节点到 四节点 一共有4种情况, 都需要调整, 即 一个黑节点, 左子节点为红节点, 左子节点的左子节点也是红节点(即插入的节点), 右子节点为红节点
     *                                           一个黑节点, 左子节点为红节点, 左子节点的右子节点也是红节点(即插入的节点), 右子节点为红节点
     *                                           一个黑节点, 右子节点为红节点, 右子节点的右子节点也是红节点(即插入的节点), 左子节点为红节点
     *                                           一个黑节点, 右子节点为红节点, 右子节点的左子节点也是红节点(即插入的节点), 左子节点为红节点
     *                可以发现 四节点 插入的 4 种情况 与 三节点 插入的四种情况基本一样, 只是黑节点多了另外一个子节点(但是不影响, 与3节点调整 基本一致)
     *
     * 可以发现一共 8 种情况需要调整, 除去方向相反的, 实际只有4种情况
     *
     * @param x 插入的节点
     */
    private void fixAfterPut(RBNode<K, V> x) {
        //插入的节点 肯定是红色
        x.color = RED;

        //插入的节点不能是根节点, 插入的节点的父节点颜色必须是红色才需要调整 (根节点就是黑色, 父节点是黑色那么子节点是红色很正常也不用管)
        while(x != null && x != root && x.parent.color == RED) {

            /**
             * 判断当前节点的父节点, 在它的父节点中, 它是属于左子节点 还是 右子节点  (它 指当前节点的父节点)
             * 知道了当前节点的父节点, 是左子节点 还是 右子节点后, 就可以区分出 8 种情况中的 4 种情况 与 另外4种方向相反的情况
             * if处理的情况:
             *      一个黑节点, 左子节点为红节点, 左子节点的左子节点也是红节点(即插入的节点), 这种情况是需要调整的
             *      一个黑节点, 左子节点为红节点, 左子节点的右子节点也是红节点(即插入的节点), 这种情况是需要调整的
             *      一个黑节点, 左子节点为红节点, 左子节点的左子节点也是红节点(即插入的节点), 右子节点为红节点
             *      一个黑节点, 左子节点为红节点, 左子节点的右子节点也是红节点(即插入的节点), 右子节点为红节点
             * else处理的情况:
             *      一个黑节点, 右子节点为红节点, 右子节点的右子节点也是红节点(即插入的节点), 这种情况是需要调整的
             *      一个黑节点, 右子节点为红节点, 右子节点的左子节点也是红节点(即插入的节点), 这种情况是需要调整的
             *      一个黑节点, 右子节点为红节点, 右子节点的右子节点也是红节点(即插入的节点), 左子节点为红节点
             *      一个黑节点, 右子节点为红节点, 右子节点的左子节点也是红节点(即插入的节点), 左子节点为红节点
             */
            if(x.parent.parent != null && x.parent == x.parent.parent.left) {
                RBNode parentBrother = x.parent.parent.right;//获取叔叔节点
                /**
                 * 区分出 4 种情况中, 节点插入的是三节点还是四节点, 即有没有叔叔节点
                 * if处理的情况:
                 *      一个黑节点, 左子节点为红节点, 左子节点的左子节点也是红节点(即插入的节点), 右子节点为红节点
                 *      一个黑节点, 左子节点为红节点, 左子节点的右子节点也是红节点(即插入的节点), 右子节点为红节点
                 * else处理的情况:
                 *      一个黑节点, 左子节点为红节点, 左子节点的左子节点也是红节点(即插入的节点), 这种情况是需要调整的
                 *      一个黑节点, 左子节点为红节点, 左子节点的右子节点也是红节点(即插入的节点), 这种情况是需要调整的
                 */
                if(colorOf(parentBrother) == RED) {//四节点(有叔叔节点)
                    //变色 + 递归
                    //父亲 和 叔叔变为黑色, 爷爷变为红色
                    x.parent.color = BLACK;
                    parentBrother.color = BLACK;
                    x.parent.parent.color = RED;

                    //递归处理
                    x = x.parent.parent;
                }else {//三节点(无叔叔节点)
                    if(x == x.parent.right) {
                        x = x.parent;
                        leftRotate(x);//对 x 的父节点继续 左旋
                    }
                    //该if处理后 else的两种情况就变为一种了, 即 根节点 -> 左子节点 -> 右子节点 变为了 根节点 -> 左子节点 -> 左子节点

                    //父亲节点变为黑色, 爷爷节点变为红色
                    x.parent.color = BLACK;
                    x.parent.parent.color = RED;
                    //根据 爷爷节点继续右旋
                    rightRotate(x.parent.parent);
                }
            }else {
                RBNode parentBrother = x.parent.parent.left;//获取叔叔节点
                if(colorOf(parentBrother) == RED) {//四节点(有叔叔节点)
                    //变色 + 递归
                    //父亲 和 叔叔变为黑色, 爷爷变为红色
                    x.parent.color = BLACK;
                    parentBrother.color = BLACK;
                    x.parent.parent.color = RED;

                    //递归处理
                    x = x.parent.parent;
                }else {//三节点(无叔叔节点)
                    if(x == x.parent.left) {
                        x = x.parent;
                        rightRotate(x);//对 x 的父节点继续 右旋
                    }

                    //父亲节点变为黑色, 爷爷节点变为红色
                    x.parent.color = BLACK;
                    x.parent.parent.color = RED;
                    //根据 爷爷节点继续右旋
                    x = x.parent.parent;
                    leftRotate(x);
                }
            }
        }

        //root 节点肯定为黑色
        root.color = BLACK;
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

        public RBNode(K key, V value, RBNode parent, RBNode left, RBNode right, boolean color) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
        }
        public RBNode(K key, V value, RBNode parent, boolean color) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left = null;
            this.right = null;
            this.color = color;
        }

        public RBNode(K key, V value, RBNode parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;

            if(this.value == null) {
                this.value = (V) this.key;
            }
        }
        public RBNode(K key, V value) {
            this.key = key;
            this.value = value;

            if(this.value == null) {
                this.value = (V) this.key;
            }
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

    public RBNode getRoot() {
        return root;
    }
}
