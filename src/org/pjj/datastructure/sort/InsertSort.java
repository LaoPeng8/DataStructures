package org.pjj.datastructure.sort;

import java.util.Arrays;

/**
 * 插入排序
 * @author PengJiaJun
 * @Date 2020/8/1 17:28
 */
public class InsertSort {

    public static void main(String[] args) {
        insertSortTest();

        int[] array = {9,5,3,11,8,2,7,135,873,239};
        System.out.println("排序前: ");
        System.out.println(Arrays.toString(array));

        insertSort(array);

        System.out.println("排序后: ");
        System.out.println(Arrays.toString(array));


        //测试一下插入排序的速度  , 给8万个数据, 测试一下
        //创建一个8万个数据的数组, 并加入随机数
        int[] test = new int[80000];
        for(int i=0;i < 80000; i++){
            test[i] = (int)(Math.random() * 80000); //生成一个[0, 80000)的随机数
        }

        long start = System.currentTimeMillis();//插入排序开始

        insertSort(test);

        long end = System.currentTimeMillis();//插入排序结束

        System.out.println("\n80000个数据, 插入排序使用了"+(end-start)+"毫秒");//508毫秒, 0.58秒
    }

    /**
     * 插入排序的每一轮排序过程 分析
     */
    public static void insertSortTest(){
        int[] array = {101,34,119,1};
        System.out.println("原始数组: ");
        System.out.println(Arrays.toString(array));
        //使用逐步推导的方式来讲解, 便于理解
        //第1轮 {101,34,119,1} => {34,101,119,1}

        //定义待插入的的数
        int insertVal = array[1];//待插入的数(无序列表的第一个数)(该趟排序的数)
        int insertIndex = 1-1;//有序列表的最后一个数的下标(无序列表的的第一个数的前一个数不就是有序列表的最后一个数吗? 所以 无序列表的第一个数(下标) -1 即是有序列表的诸侯一个数(下标))

        //给insertVal找到插入的位置
        //1. insertIndex >= 0 保证在给insertVal找插入位置时, 不越界.
        //2. insertVal<array[insertIndex] 说明待插入的数, 还没有找到插入位置
        //3. 就需要将 array[insertIndex] 后移
        while(insertIndex >= 0 && insertVal<array[insertIndex]){
            array[insertIndex+1] = array[insertIndex];//将array[insertIndex]后移一位   比如: 101 34 119 1  ==>  101 101 119 1
            insertIndex--;
        }
        //当退出while时, 说明插入的位置已经找到, insertIndex+1
        array[insertIndex+1] = insertVal;//为什么要+1? 因为需要插入的数是第一次大于insertIndex的(如需要插入的数小于insertIndex,是会一直在while循环中的,
        //如退出循环不是数组已经-1了 就是需要插入的数找到了大于的有序列表的某个数, 既然大于有序列表的某个数,则就要在这某个数的后面跟着 ),自然要在insertVal的后面一位

        System.out.println("第1轮排序后: ");
        System.out.println(Arrays.toString(array));


        //第2轮排序
        //定义待插入的的数
        insertVal = array[2];//待插入的数(无序列表的第一个数)(该趟排序的数)
        insertIndex = 2-1;//有序列表的最后一个数的下标(无序列表的的第一个数的前一个数不就是有序列表的最后一个数吗? 所以 无序列表的第一个数(下标) -1 即是有序列表的诸侯一个数(下标))

        //给insertVal找到插入的位置
        //1. insertIndex >= 0 保证在给insertVal找插入位置时, 不越界.
        //2. insertVal<array[insertIndex] 说明待插入的数, 还没有找到插入位置
        //3. 就需要将 array[insertIndex] 后移
        while(insertIndex >= 0 && insertVal<array[insertIndex]){
            array[insertIndex+1] = array[insertIndex];//将array[insertIndex]后移一位   比如: 34 101 119 1  ==>  34 101 119 1
            insertIndex--;
        }
        //当退出while时, 说明插入的位置已经找到, insertIndex+1
        array[insertIndex+1] = insertVal;//为什么要+1? 因为需要插入的数是第一次大于insertIndex的(如需要插入的数小于insertIndex,是会一直在while循环中的,
        //如退出循环不是数组已经-1了 就是需要插入的数找到了大于的有序列表的某个数, 既然大于有序列表的某个数,则就要在这某个数的后面跟着 ),自然要在insertVal的后面一位

        System.out.println("第2轮排序后: ");
        System.out.println(Arrays.toString(array));


        //第3轮排序
        //定义待插入的的数
        insertVal = array[3];//待插入的数(无序列表的第一个数)(该趟排序的数)
        insertIndex = 3-1;//有序列表的最后一个数的下标(无序列表的的第一个数的前一个数不就是有序列表的最后一个数吗? 所以 无序列表的第一个数(下标) -1 即是有序列表的诸侯一个数(下标))

        //给insertVal找到插入的位置
        //1. insertIndex >= 0 保证在给insertVal找插入位置时, 不越界.
        //2. insertVal<array[insertIndex] 说明待插入的数, 还没有找到插入位置
        //3. 就需要将 array[insertIndex] 后移
        while(insertIndex >= 0 && insertVal<array[insertIndex]){
            array[insertIndex+1] = array[insertIndex];//将array[insertIndex]后移一位   比如: 34 101 119 1  ==>  34 101 119 1  ==> 34 101 119 119  ==>  34 101 101 119  ==>  34 34 101 119  ==> 1 34 101 119
            insertIndex--;
        }
        //当退出while时, 说明插入的位置已经找到, insertIndex+1
        array[insertIndex+1] = insertVal;//为什么要+1? 因为需要插入的数是第一次大于insertIndex的(如需要插入的数小于insertIndex,是会一直在while循环中的,
        //如退出循环不是数组已经-1了 就是需要插入的数找到了大于的有序列表的某个数, 既然大于有序列表的某个数,则就要在这某个数的后面跟着 ),自然要在insertVal的后面一位

        System.out.println("第3轮排序后: ");
        System.out.println(Arrays.toString(array));

    }

    /**
     * 插入排序
     * @param array
     */
    public static void insertSort(int[] array){

        int insertVal;//待插入的数(无序列表的第一个数)(该趟排序的数)
        int insertIndex;//有序列表的最后一个数的下标(无序列表的的第一个数的前一个数不就是有序列表的最后一个数吗?)
        //i从1开始 因为 一个数组 3 5 2 9  一开始 3 看做有序列表, 因为一个数 3 无论从小到大还是反之, 都是有序的.
        //5 2 9 看做无序列表, 即从无序列表第一个 5 开始排序 插入有序列表, 所以i从1开始 因为下标为0的从一趟排序开始就是有序的, 不用排
        for(int i=1;i<array.length;i++){
            //定义待插入的的数
            insertVal = array[i];//待插入的数(无序列表的第一个数)(该趟排序的数)
            insertIndex = i-1;//有序列表的最后一个数的下标(无序列表的的第一个数的前一个数不就是有序列表的最后一个数吗? 所以 无序列表的第一个数(下标) -1 即是有序列表的诸侯一个数(下标))

            //给insertVal找到插入的位置
            //1. insertIndex >= 0 保证在给insertVal找插入位置时, 不越界.
            //2. insertVal<array[insertIndex] 说明待插入的数, 还没有找到插入位置
            //3. 就需要将 array[insertIndex] 后移
            while(insertIndex >= 0 && insertVal<array[insertIndex]){
                array[insertIndex+1] = array[insertIndex];//将array[insertIndex]后移一位   比如: 101 34 119 1  ==>  101 101 119 1
                insertIndex--;
            }
            //当退出while时, 说明插入的位置已经找到, insertIndex+1

            //加这个if可以优化一下速度, 因为 如果insertIndex + 1 == i 则说明 无序数组的第一个(本趟待排序的数) 本来就在当前这个位置, 就无序赋值
            if(insertIndex + 1 != i){//所以这里才判断一下, 只有insertIndex + 1 != i 才会进行赋值操作
                array[insertIndex+1] = insertVal;//为什么要+1? 因为需要插入的数是第一次大于insertIndex的(如需要插入的数小于insertIndex,是会一直在while循环中的,
                //如退出循环不是数组已经-1了 就是需要插入的数找到了大于的有序列表的某个数, 既然大于有序列表的某个数,则就要在这某个数的后面跟着 ),自然要在insertVal的后面一位
            }

        }

    }

}
