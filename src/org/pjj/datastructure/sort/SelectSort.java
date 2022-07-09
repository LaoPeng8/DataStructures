package org.pjj.datastructure.sort;

import java.util.Arrays;

/**
 * 选择排序
 * @author PengJiaJun
 * @Date 2020/7/31 22:39
 */
public class SelectSort {

    public static void main(String[] args) {
        selectSortTest();//选择排序过程分析

        int[] array = {101,34,119,1,5,8,666,13,20,896,888,666};
        System.out.println("\n排序前: "+Arrays.toString(array));
        selectSort(array);
        System.out.println("排序后: "+Arrays.toString(array));


        //测试一下选择排序的速度 O(n^2) , 给8万个数据, 测试一下
        //创建一个8万个数据的数组, 并加入随机数
        int[] test = new int[80000];
        for(int i=0;i < 80000; i++){
            test[i] = (int)(Math.random() * 80000); //生成一个[0, 80000)的随机数
        }

        long start = System.currentTimeMillis();//选择排序开始

        selectSort(test);

        long end = System.currentTimeMillis();//选择排序结束

        System.out.println("\n80000个数据, 选择排序使用了"+(end-start)+"毫秒");//2324毫秒, 2.324秒    加了if(minIndex != i) 变成了 1895毫秒
    }

    /**
     * 选择排序的每一轮排序过程 分析
     */
    public static void selectSortTest(){
        int[] array = {101,34,119,1};
        System.out.println("排序前: ");
        System.out.println(Arrays.toString(array));


        //使用逐步推导的方式来讲解选择排序
        //第1轮
        //原始的数组: 101, 34, 119, 1
        //第一轮排序: 1, 34, 119, 101
        //算法 先简单--->后复杂, 就是可以把一个复杂的算法, 拆分成简单的问题-->逐步解决

        int minIndex = 0;//先假定最小值, 是数组下标为0的这个数
        int min = array[0];

        //j=0+1是第因为, 本轮假定最小值下标为0, 要将0与0的下一位开始依次比较(如不+1,岂不还需要自己与自己比较?), 比较出真正的最小值
        for(int j=0+1;j<array.length;j++){
            if(min > array[j]){//进入if, 则说明 我们假定的这个最小值的数, 并不是最小的
                min = array[j];//重置min, 因为之前我们假定min是最小值, 而现在min>array[j] 则, 说明array[j] 比 min更小, 那么代表最小值的min, 则要设为array[j]
                minIndex = j;//重置minIndex, 因为之前我们假定minIndex是最小值的下标, 而现在min>array[j] 则, 说明array[j] 比 min更小, 那么代表最小值下标的minIndex, 则要设为 j
            }
        }

        //如果minIndex不等于我们假定的那个最小值的下标, 才会进行交换, 因为说不定我们假定的那个最小值, 刚好就就是本轮的最小值,
        //for循环都循环完了,minIndex还是我们假定的值, 没有发生交换, 说明我们假定的这个最小值本身就是该轮的最小值
        if(minIndex != 0){
            //将原本的array[0] 放在最小值所在的地方, 将最小值, 放在array[0]处,  即交换, 将最小值与array[0]交换
            array[minIndex] = array[0];//将原本的array[0] 放在最小值所在的地方
            array[0] = min;//将最小值, 放在array[0]处
        }


        System.out.println("第一轮后~~");
        System.out.println(Arrays.toString(array)); //1, 34, 119, 101


        //第2轮
        minIndex = 1;//先假定最小值, 是数组下标为1的这个数
        min = array[1];
        for(int j=0+2;j<array.length;j++){
            if(min > array[j]){//进入if, 则说明 我们假定的这个最小值的数, 并不是最小的
                min = array[j];//重置min, 因为之前我们假定min是最小值, 而现在min>array[j] 则, 说明array[j] 比 min更小, 那么代表最小值的min, 则要设为array[j]
                minIndex = j;//重置minIndex, 因为之前我们假定minIndex是最小值的下标, 而现在min>array[j] 则, 说明array[j] 比 min更小, 那么代表最小值下标的minIndex, 则要设为 j
            }
        }

        //如果minIndex不等于我们假定的那个最小值的下标, 才会进行交换, 因为说不定我们假定的那个最小值, 刚好就就是本轮的最小值,
        //for循环都循环完了,minIndex还是我们假定的值, 没有发生交换, 说明我们假定的这个最小值本身就是该轮的最小值
        if(minIndex != 1){
            //将原本的array[0] 放在最小值所在的地方, 将最小值, 放在array[0]处,  即交换, 将最小值与array[0]交换
            array[minIndex] = array[1];//将原本的array[0] 放在最小值所在的地方
            array[1] = min;//将最小值, 放在array[0]处
        }


        System.out.println("第二轮后~~");
        System.out.println(Arrays.toString(array)); //1, 34, 119, 101

        //第3轮
        minIndex = 2;//先假定最小值, 是数组下标为2的这个数
        min = array[2];
        for(int j=0+3;j<array.length;j++){
            if(min > array[j]){//进入if, 则说明 我们假定的这个最小值的数, 并不是最小的
                min = array[j];//重置min, 因为之前我们假定min是最小值, 而现在min>array[j] 则, 说明array[j] 比 min更小, 那么代表最小值的min, 则要设为array[j]
                minIndex = j;//重置minIndex, 因为之前我们假定minIndex是最小值的下标, 而现在min>array[j] 则, 说明array[j] 比 min更小, 那么代表最小值下标的minIndex, 则要设为 j
            }
        }

        //如果minIndex不等于我们假定的那个最小值的下标, 才会进行交换, 因为说不定我们假定的那个最小值, 刚好就就是本轮的最小值,
        //for循环都循环完了,minIndex还是我们假定的值, 没有发生交换, 说明我们假定的这个最小值本身就是该轮的最小值
        if(minIndex != 2) {
            //将原本的array[0] 放在最小值所在的地方, 将最小值, 放在array[0]处,  即交换, 将最小值与array[0]交换
            array[minIndex] = array[2];//将原本的array[0] 放在最小值所在的地方
            array[2] = min;//将最小值, 放在array[0]处
        }

        System.out.println("第三轮后~~");
        System.out.println(Arrays.toString(array)); //1, 34, 101, 119
    }


    /**
     * 选择排序
     * @param array 需要排序的数组
     */
    public static void selectSort(int[] array){
        int minIndex;
        int min;
        //i<array.length-1 是因为 如数组有5个数据, 则只需要进行4趟排序, 因为进行4趟排序后, 已经将前4个最小的数依次排好序了, 只剩最后一个最大了, 也就不用排序了..
        for(int i=0;i<array.length-1;i++){
            minIndex = i;//先假定最小值, 是数组下标为0的这个数, 之后经过一轮排序, i就+1  第几趟就假定最小值是几, i之前的都是已经排好序了的, 就不用管了.
            min = array[i];

            //j=i+1   j=i是因为, i是代表第几趟排序, i=0则说明是第一趟排序, 那么真正排好序的一个都没有, 则i=0,j=i
            //                   i=1则说明是第二趟排序, 既然是第二趟排序, 那么就已经有一个排好序了数据了,就不用管那个已经排好序的数了 所以i=1,j=i,
            //如i=0则是第一趟排序 第一趟排序则用本轮的假定最小值 与 最小值后面一位开始依次比较, 筛选出本轮真正的最小值
            //如不+1, 岂不每轮都要自己与自己比较一番?  如数据量小还好说, 数据量大了, 就浪费时间了
            for(int j=i+1;j<array.length;j++){

                if(min > array[j]){//进入if, 则说明 我们假定的这个最小值的数, 并不是最小的
                    min = array[j];//重置min, 因为之前我们假定min是最小值, 而现在min>array[j] 则, 说明array[j] 比 min更小, 那么代表最小值的min, 则要设为array[j]
                    minIndex = j;//重置minIndex, 因为之前我们假定minIndex是最小值的下标, 而现在min>array[j] 则, 说明array[j] 比 min更小, 那么代表最小值下标的minIndex, 则要设为 j
                }

            }
            //如果minIndex不等于我们假定的那个最小值的下标, 才会进行交换, 因为说不定我们假定的那个最小值, 刚好就就是本轮的最小值,
            //for循环都循环完了,minIndex还是我们假定的值, 没有发生交换, 说明我们假定的这个最小值本身就是该轮的最小值
            if(minIndex != i){
                //将原本的array[0] 放在最小值所在的地方, 将最小值, 放在array[0]处,  即交换, 将最小值与array[0]交换
                array[minIndex] = array[i];//将原本的array[0] 放在最小值所在的地方
                array[i] = min;//将最小值, 放在array[0]处
            }

        }

    }

}
