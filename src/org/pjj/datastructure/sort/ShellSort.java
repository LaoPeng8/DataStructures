package org.pjj.datastructure.sort;

import java.util.Arrays;

/**
 * 希尔排序
 * @author PengJiaJun
 * @Date 2020/8/3 9:21
 */
public class ShellSort {
    public static void main(String[] args) {
//        shellSortTest();

        int[] array = {9,5,7,8,3,6,2,0,1,10,-1};//{8,9,1,7,2,3,5,4,6,0,13,26,27,26,-3,-8}
        System.out.println("\n原始数组为: ");
        System.out.println(Arrays.toString(array));

//        shellSort(array);//使用交换式的希尔排序
        shellSort2(array);//使用插入式的希尔排序

        System.out.println("希尔排序后: ");
        System.out.println(Arrays.toString(array));


        //测试一下希尔排序的速度  , 给8万个数据, 测试一下
        //创建一个8万个数据的数组, 并加入随机数
        int[] test = new int[8000000];
        for(int i=0;i < 8000000; i++){
            test[i] = (int)(Math.random() * 8000000); //生成一个[0, 80000)的随机数
        }

        long start = System.currentTimeMillis();//希尔排序开始

        //shellSort(test);//使用交换法的希尔排序
        shellSort2(test);//使用插入法的希尔排序

        long end = System.currentTimeMillis();//希尔排序结束

        System.out.println("\n80000个数据, 希尔排序使用了"+(end-start)+"毫秒");
        //使用交换法的希尔排序: 7415毫秒, 7.415秒        //使用插入法的希尔排序: 13毫秒, 0.013秒
        //之前使用选择排序80w数据使用了4分多, 使用插入排序用了50多秒, 使用插入法的希尔排序用了193毫秒, 0.193秒, 惊了,这就是算法的魅力吗?
    }


    /**
     * 希尔排序的每一轮排序过程 分析
     * 使用逐步推导的方式来编写希尔排序
     */
    public static void shellSortTest(){
        int[] array = {8,9,1,7,2,3,5,4,6,0,-1};
        System.out.println("原始数组为: ");
        System.out.println(Arrays.toString(array));

        int temp=0;
        //希尔排序第1轮
        //因为第1轮排序, 是将10个数据分成了5组
        for(int i=5;i<array.length;i++){
            //遍历各组中所有的元素(共5组, 每组有2个元素), 步长5
            for(int j=i-5;j>=0;j-=5){
                //如果当前元素大于加上步长后的那个元素, 说明交换
                if(array[j] > array[j+5]){
                    temp = array[j];
                    array[j] = array[j+5];
                    array[j+5] = temp;
                }
            }
        }
        System.out.println("第1轮希尔排序后: ");
        System.out.println(Arrays.toString(array));


        //希尔排序第2轮
        //因为第2轮排序, 是将10个数据分成了5/2   两组
        for(int i=2;i<array.length;i++){
            //遍历各组中所有的元素(共2组, 每组有5个元素), 步长2
            for(int j=i-2;j>=0;j-=2){
                //如果当前元素大于加上步长后的那个元素, 说明交换
                if(array[j] > array[j+2]){
                    temp = array[j];
                    array[j] = array[j+2];
                    array[j+2] = temp;
                }
            }
        }
        System.out.println("第2轮希尔排序后: ");
        System.out.println(Arrays.toString(array));


        //希尔排序第3轮
        //因为第3轮排序, 是将10个数据分成了2/2   一组
        for(int i=1;i<array.length;i++){
            //遍历各组中所有的元素(共1组, 每组有10个元素), 步长1
            for(int j=i-1;j>=0;j-=1){
                //如果当前元素大于加上步长后的那个元素, 说明交换
                if(array[j] > array[j+1]){
                    temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
        System.out.println("第3轮希尔排序后: ");
        System.out.println(Arrays.toString(array));

    }


    /**
     * 希尔排序(此处使用的交换法)
     * 使用交换法的希尔排序效率并不高
     * 使用插入法的希尔排序效率比较高
     * @param array
     */
    public static void shellSort(int[] array){

        int temp;
        //步长第一次为 数组长度 / 2 = 步长;  之后就是 老步长 / 2 = 新步长;
        //每次循环步长都需除2, 步长为1时是最后一次排序, 步长为0了就不会进入循环了. 如 数组长度为11的数组  11/2=5; 5/2=2; 2/2=1; 1/2=0
        //每轮循环都会按照步长分组 然后对每组 分别进行插入排序
        for(int gap = array.length/2; gap > 0; gap/=2){//步长初始为 数组长度 / 2 = 步长; 步长>0; 老步长 / 2 = 新步长;

            //遍历所有元素, i=gap(步长) 相当于 如 步长为 gap = 5, [9,8,7,6,5,4,3,2,1] 则会从5开始遍历, 但是这样不行啊, 5前面的不能不遍历啊
            //所以 该 for循环内 又嵌套了一个 for(int j=i-gap;j>=0;j-=gap) int j=i-gap; 那么j就是从 0 开始遍历的了
            for(int i=gap; i<array.length; i++){

                //先 往后遍历一个数 因为比如 j=3 进入if(array[j=3] > array[j+gap=5]) 如j后面一个位数 大于 j 则让他们交换位置
                //之后 j  = j - 步长 如步长为2 则 j = 3-2; j=1; 则继续判断 if(array[j=1] > array[j+gap=3]) 此时的 j=1;
                //则判断array[1]>array[3] 而 array[3]也是之前刚刚经过if(array[j=3] > array[j+gap=5])判断了的 将 j=3与j=5之间 较小的数 赋给了j=3
                //此时判断 array[j=1]>array[j+gap=3] 再次将较小的数 赋值给 j=1, 再之后 j = j-步长 == j=1-2=0 退出循环了.
                //每次执行该for循环 就会 将当前 j 所在的分组 往后遍历一个数,并排序, 并且 j 在该分组中 不是第一个数 j还会像前比较,直到0
                for(int j=i-gap;j>=0;j-=gap){

                    //如果当前元素大于加上步长后的那个元素, 说明交换
                    //也就是该组前面的元素大于后面的元素,则将他们交换位置, 达到排序的效果
                    if(array[j] > array[j+gap]){
                        temp = array[j];
                        array[j] = array[j+gap];
                        array[j+gap] = temp;
                    }

                }

            }

        }

    }

    /**
     * 使用交换式的希尔排序效率并不高, 所以我们可以使用插入式的希尔排序
     * 希尔排序(此处使用的插入法)
     * 使用交换法的希尔排序效率并不高
     * 使用插入法的希尔排序效率比较高
     * @param array
     */
    public static void shellSort2(int[] array){

        //增量首先为 数组长度/2; 之后 增量 = 增量/2; 增量为1时(就相当于变成了直接插入排序) 是最后一趟循环排序, 增量为0就不会再进入循环了.
        for(int gap=array.length/2;gap>0;gap/=2){

            //从第gap个元素, 逐个对其所在的组进行直接插入排序,
            //因为从第gap个元素开始, 就像之前的直接插入排序从1开始一样, 从gap开始, 前面的数据相当于是每组的 "有序列表"
            //因为 一个数组 3 5 2 9  一开始 3 看做有序列表, 因为一个数 3 无论从小到大还是反之, 都是有序的.
            //虽然从 gap开始 前面会有多个数据 如 array={9,8,7,5,2,1} 第一次gap=3; array[3]==5, 虽然5前面有多数, 表面上看 不是"有序列表"
            //但是 实际上 5前面 都是 每组 的第一个元素  在该次遍历中, 他们相当于互不影响, 只是在同一个数组中而已,
            //然后按照 直接插入排序的思想 将 无序列表的数依次 插入有序列表即可.
            for(int i=gap; i<array.length; i++){
                int j = i;
                int temp = array[j];//待插入的数  array[j-gap]就相当于 有序列表的最后一个数 (无序列表的的第一个数的前一个数(不是表面上的前一个数(表面上的前一个数是-1),而是该数分组内的前一个数 所以是-gap)不就是有序列表的最后一个数吗?)

                //如果当前的数array[j](待插入的数,无序列表的第一个数) 小于 array[j - gap](有序列表的最后一个数, -gap是为了 让j是与j所在的分组 内的数进行比较)
                if(array[j] < array[j - gap]){
                    // j - gap >=0是为了防止数组越界
                    //temp(待插入的数,无序列表的第一个数) 如果 小于 array[j-gap](有序列表的最后一个数),则将,array[j-gap]往后移 array[j] = array[j-gap]
                    //array[j-gap]往后移之后 j需要往前一格继续判断 temp是否小于 有序列表的倒数第二数 由于是在该分组内判断 所以是 j = j-gap; 而不是j--
                    //一直判断 直到 temp 不小于 array[j-gap](退出循环) 则 temp的插入位置找到了,就在array[j-gap]的后面 所以 array[j] = temp;
                    //或者一直判断 temp一直小于 array[j-gap] 则说明 temp是最小的数, 则直到 j-gap 不 大于等于 0了之后 退出循环
                    //此时 j-gap 不大于等于0了, 说明 j-gap<0, 说明 j的上一个数 已经没有了(因为数组下标最小是0)
                    //既然 j的上一个数已经没有了, 那么j就是该组有序列表的第一个数, 而temp一直小于array[j-gap] 是 j<=0了才退出循环的
                    //temp就是该分组最小的数 则将 temp赋值给 array[j] = temp; 将temp赋值给该分组的有序列表的第一个数
                    while(j - gap >= 0 && temp<array[j-gap]){
                        //移动
                        array[j] = array[j-gap];
                        j-=gap;
                    }
                    //当退出while时, 就给temp找到了插入的位置
                    array[j] = temp;
                }
            }
        }
    }


}
