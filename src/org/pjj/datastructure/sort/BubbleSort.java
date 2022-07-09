package org.pjj.datastructure.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * @author PengJiaJun
 * @Date 2020/7/31 10:55
 */
public class BubbleSort {
    public static void main(String[] args) {
        int[] array = {5,7,6,9,3};

        System.out.println("原始数组为: ");
        System.out.println(Arrays.toString(array));

        maoPao(array);

        System.out.println("经过冒泡排序后的数组: ");
        System.out.println(Arrays.toString(array));


        //测试一下冒泡排序的速度 O(n^2), 给8万个数据, 测试一下
        //创建一个8万个数据的数组, 并加入随机数
        int[] test = new int[80000];
        for(int i=0;i < 80000; i++){
            test[i] = (int)(Math.random() * 80000); //生成一个[0, 80000)的随机数
        }

        long start = System.currentTimeMillis();//冒泡排序开始

        maoPao(test);

        long end = System.currentTimeMillis();//冒泡排序结束

        System.out.println("\n80000个数据, 冒牌排序使用了"+(end-start)+"毫秒");//9663毫秒, 9.663秒


    }

    public static void maoPao(int[] array){

        //优化冒泡排序, 因为有可能, 冒泡排序排了一半之后, 整个数组就已经有序了, 但是冒泡排序还是会,将循环执行完毕, 这样就浪费了
        //所以使用此变量, 来表示是否进行过交换, 如某一趟排序, 一次都没有进行两两交换, 则表示已经排好序了, 那么剩下的for循环就可以不用执行了,直接return
        boolean flag = false;//标识变量, 表示是否进行过交换

        //如 数组array有5个数据, 则只需要进行4趟排序(因为进行4趟排序后,就已将4个数据都沉入了后面,只剩一个数据在前面就无需排序了),
        //所以 i < array.length - 1
        for(int i=0;i<array.length-1;i++){

            //j<array.length-1-i 是因为 array.length-1是因为如array有5个数, 则需要进行4次两两比较,则将最大的数沉入最后了
            // -i 是因为, i是表示当前是第几趟排序了, 如i=2则表示, 当前是第3趟排序而且已经完成了2趟排序了, 说明已经将2个最大的数沉入数组最后了
            //则不需要管这两个已经沉入了数组最后的两个数(不需要管这两个已经排好序了的数) 所以需要 -i.
            //i是 完成了几趟排序,一趟排序,排好一个数,几趟排序排好几个数, 下次排序时,不需要管已经排好了的数,所以 -i
            for(int j=0;j<array.length-1-i;j++){

                //判断 array[i] 是否大于 array[i+1] 也就是判断 数组的前一个数是否大于后一个数据
                if(array[j] > array[j+1]){
                    //如果数据前一个数 大于 后一个数, 则将他们进行交换,
                    //这样当内层for执行完后, 该数组中最大的数就被沉入数组的最后了,
                    //下一次进行内层for时, 就不用管已经被沉入数组的最后的数了, 将当前没有被沉入数组的数, 再次比较, 再次将当前最大的数沉入数组最后(并不是真正的最后, 而是排除之前已经沉入的数之后 的最后)
                    //这样进行 array.length-1次排序后, 数组就有序了.
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;

                    flag = true;//进行过两两交换, 那么flag就为true
                }
            }
            //如果数据前一个数 不大于 后一个数, 就不用管他们, 继续进行下一波 两两比较

            if(flag == false){//在一趟排序中, 一次两两交换都没有发生过, 则说明排序以及完成, 就无需循环了, 直接break
                break;
            }else{
                flag = false;//如flag为true, 则说明进行过两两交换, 排序还未完成, 即将flag重置为false继续进行排序.
            }

//            System.out.println("第"+(i+1)+"趟排序后的数组");
//            System.out.println(Arrays.toString(array));

        }
    }

}
