package org.pjj.datastructure.sort;

import java.util.Arrays;

/**
 * 堆排序
 * @author PengJiaJun
 * @Date 2020/8/15 18:47
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] array = {9,6,8,7,0,1,10,4,2};
        System.out.println("堆排序前: "+Arrays.toString(array));
        heapSort(array);
        System.out.println("堆排序后: "+Arrays.toString(array));


        //测试一下排序的速度  , 给8万个数据, 测试一下
        //创建一个8万个数据的数组, 并加入随机数
        int[] test = new int[80000];
        for(int i=0;i < 80000; i++){
            test[i] = (int)(Math.random() * 80000); //生成一个[0, 80000)的随机数
        }

        long start = System.currentTimeMillis();//堆排序开始

        heapSort(test);

        long end = System.currentTimeMillis();//堆排序结束

        System.out.println("\n80000个数据, 堆排序使用了"+(end-start)+"毫秒");//8w,13毫秒. 80w,141毫秒. 800w,2272毫秒. 8000w,37555毫秒

    }

    public static void heapSort(int[] array){
        //开始位置是最后一个非叶子节点, 即最后一个节点的父节点
        int start = ((array.length -1) - 1) / 2; // (n-1)/2 是求 n 的父节点的公式, 最后一个节点下标应为array.length-1

        //这个for循环就是先把 array转成一个 大顶堆
        for(int i=start;i>=0;i--){
            maxHeap(array,array.length,i);
        }
        //此时 array已经是一个大顶堆了,先把数据中的第0个和堆中的第0个和堆中的最后一个数交换位置,再把前面的数据处理为大顶堆
        //假设有9个数据 则要进行8次排序, 因为每次将大顶堆的根节点放入数组末尾, 还有两个数据时将根节点放入数组末尾,还有一个数时就不用放了.
        //i从数组最后往前遍历, 每一轮i--, 因为每处理一轮就会将该轮最大的数 放入数组末尾,然后i--,下次就不处理已经放入数组末尾的数了
        for(int i=array.length-1; i>0; i--){
            //大顶堆的根节点必是最大的值, 用根节点与 数组末尾 交换, 最大的值就跑到数组最后了,
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            maxHeap(array,i,0);//将最大值放入[i]后不用担心 这里调用 maxHeap(array,i,0);第二个参数 i 会造成将已经排好序的 array[i]
            //再次混入 大顶堆中, 因为 判断 一个节点的左右子节点时 需要leftNode 与 rightNode < size(也就是这里的 i ) 如果==i是不会将该节点加入大顶堆的.

            //maxHeap(array,i,0);
            //刚开始我也觉得 从 0 开始好像不太对, 但是通过Debug发现,0也是可以的
            //因为已经进过一轮的转换 将 array已经转为了大顶堆
            //此时无需从最后一个节点开始转成大顶堆, 因为之前已经转为大顶堆了, 现在只是改变了根节点[0]的值 导致根节点不符合大顶堆的特性
            //而其他节点都还符合大顶堆的特性, 所以只需要将根节点与其 左右子节点 比较 出真正的 根节点, 然后交换值, 使根节点真正成为根节点
            //与根节点交换了值的节点(左右子节点其中一个与根节点交换值了的节点) 由于值已经改变了, 会在maxHeap方法中递归调用 继续与其左右子节点比较
            //最后使其 重新成为一个 大顶堆, 之后进行下一次循环,让根节点与最后一个节点交换值, 然后再重新排序大顶堆.....

        }
    }

    /**
     * 将一个顺序存储的二叉树 转为 大顶堆
     * @param array     顺序存储的二叉树
     * @param size      将顺序存储的二叉树中从 0 ~ size个数据 转为 大顶堆, 因为第一次是将整个数组都转为大顶堆
     *                  然后将根节点(最大值)与数组尾部的值交换,之后再次转为大顶堆(排除之前已经排好的放入数组尾部的最大值),
     *                  所以需要这个变量指定长度, 因为除了第一次,之后都不是将整个数组 转为 大顶堆
     *
     * @param index     从哪个节点开始将 二叉树 转为 大顶堆, 从顺序存储二叉树的数组 最后一位所在的节点 的 父节点
     *                  也就是最后一个非叶子节点, 那么只需找到最后一个叶子节点的父节点也就是最后一个非叶子节点
     *                  通过数组索引可以找到 二叉树的最后一个节点, 然后就可以找到这个最后节点的父节点(也就是最后一个非叶子节点)
     *                  index就代表这个最后一个非叶子节点
     */
    public static void maxHeap(int[] array,int size,int index){
        //左子节点
        int leftNode = 2 * index + 1;
        //右子节点
        int rightNode = 2 * index + 2;

        int max = index;//先假定 传进来的 这个 index(最后一个非叶子节点) 最大
        //和两个子节点分别对比, 找出最大的节点
        //由于会递归调用该方法, 叶子节点也可能会进来,可以计算出该叶子节点的左右子节点下标
        //但是叶子节点又没有左右子节点, 所以这下标会越界, 需要加判断 leftNode < size; 右子节点也一样 rightNode < size
        //然后叶子节点 就不会 与 不存在的左右子节点比较  max还是等于index 也不会继续向下递归, 相当于叶子节点进来该方法啥也没干就退出了.
        if(leftNode < size && array[leftNode] > array[max]){
            max = leftNode;
        }
        if(rightNode < size && array[rightNode] > array[max]){
            max = rightNode;
        }

        //找到参数传进来的非叶子节点后,让其该节点与该节点的左子节点 和 右子节点比较值
        //最大的值后, 需要交换两个节点值, 将最大值赋给刚才的叶子节点, 然后将叶子节点值赋给 与它交换值的 子节点
        if(max != index){//如果max 与 index不相等了,说明有子节点比index更大了(比index大的值已经赋给max了), 则将他们交换位置
            int temp = array[index];
            array[index] = array[max];
            array[max] = temp;
            //注意 只是值交换了, 下标并没有变,index还是指向index, max还是指向max,
            //如果max != index 则说明 max与index交换值了, 此时index的值最大了, max等于之前index的值,
            //max就相当于改变了, 这样可能会破坏,之前排好的 max 与其自己的左右子节点,因为之前已经排好了 max 大于 它的两个子节点
            //但是此时max改变了, 所以 有可能已经不大于 它自己的左右子节点了
            //所以需要 递归调用 maxHeap(array,size,max); 来对max及左右子节点,进行比较, 找出3者中的最大值,赋值给max所在的节点

            //交换位置后, 可能会破坏之前排好的堆, 所以, 之前排好的堆需要重新调整
            maxHeap(array,size,max);
        }

    }

}
