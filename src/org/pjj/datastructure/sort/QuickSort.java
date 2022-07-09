package org.pjj.datastructure.sort;

import java.util.Arrays;

/**
 * 快速排序
 * @author PengJiaJun
 * @Date 2020/8/5 12:17
 */
public class QuickSort {

    public static void main(String[] args) {
//        int array[] = {-9,78,0,23,-567,70};
        int[] array = {101,34,119,1,5,8,666,13,20,896,888,666,666};

        quickSort(array,0,array.length-1);
        System.out.println(Arrays.toString(array));


        //测试一下快速排序的速度  , 给800万个数据, 测试一下
        //创建一个800万个数据的数组, 并加入随机数
        int[] test = new int[8000000];
        for(int i=0;i < 8000000; i++){
            test[i] = (int)(Math.random() * 8000000); //生成一个[0, 8000000)的随机数
        }

        long start = System.currentTimeMillis();//快速排序开始

        quickSort(test,0,test.length-1);

        long end = System.currentTimeMillis();//快速排序结束

        System.out.println("\n8000000个数据, 快速排序使用了"+(end-start)+"毫秒");//800万个数据, 快速排序使用了1141毫秒, 8千万,10171毫秒

    }



    /**
     * 快速排序
     * 颜群老师讲解的  800w个数据 一般是 950毫秒 ~ 1050毫秒
     * 韩顺平老师讲解的快速排序 看的不怎么明白, 太多++ --了, 而且if也很多.  颜群老师讲解的快速排序 就比较好 看得有点明白
     * @param array 需要排序的数组
     * @param start 需要排序的数组从哪里开始排序
     * @param end   需要排序的数组从哪里开始结束排序
     * start ~ end  可以只给数组中 某一段区间 排序
     */
    public static void quickSort(int[] array,int start,int end){

        //递归退出条件, 一直递归排序, 直到传入的参数 start>=end 就不会既进入该if,不会进入该if,就不会再次排序,也不会再次递归调用,只会方法执行完毕,返回上一层.
        //因为  比如: {5,8,3,9,1}pivot=5 第一次排序后为 {1,5,3,9,8} --> 左边部分为{3,1,5}(右边此处就不说了) 然后对{3,1,5}再次递归调用该方法
        //{3,1,5}pivot=3 就变成了 {1,3,5} 左边部分{1,3} 右边部分{5} 然后再次递归调用该方法,此时的右边部分{5}start=0;end=0;此时右边已经遍历完成
        //左边部分{1,3}start=0,end=1 继续递归 {1,3}pivot=1,左边部分{1}start=0,end=0 右边部分{3}start=0,end=0;遍历完成然后一层一层返回,递归调用结束
        if(start<end){

            //取数组中需要排序的部分的第一个为 标准数, 然后进行排序 比此数小的在 此数右边, 比此数大的在此数左边
            int pivot = array[start];//不能直接设为 array[0]; 因为,最开始是整个数组排序确实可以使用0但是,经过经过一轮排序后, 就变成了 左边的 和 右边的, 这两个要分别排序,0就已经不适用了.

            //记录需要排序的下标,我也不知道为什么不能直接使用start与end 需要先赋值给 low与high后再使用
            int low = start;
            int high = end;

            while(low<high){

                //右边的数字 如果 大于等于 标准数pivot 说明 该数字位置是正确的, 不需要换位置, 只需将high--, 逐个遍历右边的数字,
                //(注意此处的 大于等于 非常重要 如果不加 下一个循环array[low] <= pivot会将 等于pivot的数 丢到右边处理)
                //(而右边的循环(该循环)会将 等于pivot的数 丢到右边处理, 这样,岂不没完没了,死循环了)
                //(按道理 只需要左边或者右边 一边处理 等于pivot的值,但不知为什么 如果只有一边处理,即会抛出 栈溢出异常StackOverflowError)
                //(必须要右边array[high] >= pivot不管,同时左边array[low] <= pivot不管 才可以行)
                //只到找到小于标准数pivot的数字,即不会再进入循环, 循环结束后的high即是 小于标准数的 数字的下标
                //该数小于标准数, 正确的位置应该是 在标准数的左边, 而此时却在右边,则要将该数 调换到左边即可.
                while(low<high && array[high] >= pivot){
                    high--;
                }
                array[low] = array[high];

                //左边的数字 如果 小于等于 标准数pivot 说明 该数字位置是正确的, 不需要换位置, 只需要将low++, 逐个遍历左边的数字,
                //(注意此处的 小于等于 非常重要 如果不加 上一个循环array[high] >= pivot会将 等于pivot的数 丢到左边处理)
                //(而左边的循环(该循环)会将 等于pivot的数 丢到右边处理, 这样,岂不没完没了,死循环了)
                //只要找到大于标准数pivot的数字,即不会再进入循环, 循环结束后的low即是 大于标准数的 数字的下标
                //该数大于标准数, 正确的位置应该是 在标准数的右边, 而此时却在左边,则要将该数 调换到右边即可.
                while(low<high && array[low] <= pivot){
                    low++;
                }
                array[high] = array[low];

            }
            //如退出循环 必是 low==high了, 说明low与high重合了,说明本轮排序排完了
            //把标准数赋给low所在的位置,
            array[low] = pivot;

            //此时 标准数pivot左边以是全部小于pivot的数, 右边以是全部大于pivot的数
            //但是 此时数组并不是有序的 因为 所有小于pivot的数,虽然都在pivot左边但是不能保证是有序的 比如 pivot=8;   {5,3,7,2, 8 ,11,15,9,20}
            //同理右边也不能保证是有序的.

            //所以需要 再次处理左边的数字
            quickSort(array,start,low);
            //所以需要 再次处理右边的数字
            quickSort(array,high+1,end);//+1是因为, low与high指向同一个数 已经在左边处理了low,就不需要在右边也处理low了, 要不然最后排序排出来 就有两个low代表的值了.

        }
    }

    /**
     * 快速排序
     * 韩顺平老师讲解的    800w个数据 一般是 1000毫秒 ~ 1200毫秒
     * 韩顺平老师讲解的快速排序 看的不怎么明白, 太多++ --了, 而且if也很多. 颜群老师讲解的快速排序 就比较好 看得有点明白
     * @param array 需要排序的数组
     * @param left 数组的左下标 (最左边)
     * @param right 数组的右下标 (最右边)
     */
    public static void quickSort2(int[] array, int left, int right){
        int l = left;//左下标
        int r = right;//右下标
        int pivot = array[(left+right)/2];//pivot 中轴值  即数组中心点
        int temp;

        //while循环的目的是让 比pivot值 小的数 放在pivot值的左边, 比pivot值 大的数 放在pivot值的右边
        while( l < r){

            //在pivot的左边一直找, 找到大于等于pivot的值, 才退出(因为pivot的左边应是小于pivot的值,如pivot的左边有大于等于pivot的值,则需将该值, 调到右边)
            while(array[l] < pivot){
                l++;
            }
            //在pivot的右边一直找, 找到小于等于pivot的值, 才退出(因为pivot的右边应是大于pivot的值,如pivot的右边有小于等于pivot的值,则需将该值, 调到左边)
            while(array[r] > pivot){
                r--;
            }

            //如果 l >= r 说明pivot 的左右两边的值, 已经按照左边全部是小于等于pivot的值, 右边全部是大于等于pivot的值
            if(l >= r){
                break;
            }

            //交换
            temp = array[l];
            array[l] = array[r];
            array[r] = temp;

            //如果交换完后, 发现这个array[l] == pivot值  相等
            if(array[l] == pivot){
                r--;
            }
            //如果交换完后, 发现这个array[r] == pivot值 相等
            if(array[r] == pivot){
                l++;
            }

        }

        //如果 l == r, 必须l++, r--, 否则会出现栈溢出
        if(l==r){
            l++;
            r--;
        }

        //向左递归
        if(left < r){
            quickSort2(array,left,r);
        }
        //向右递归
        if(right > l){
            quickSort2(array,l,right);
        }


    }

}
