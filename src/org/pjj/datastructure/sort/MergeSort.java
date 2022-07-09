package org.pjj.datastructure.sort;

import java.util.Arrays;

/**
 * 归并排序
 * @author PengJiaJun
 * @Date 2020/8/7 17:30
 */
public class MergeSort {

    public static void main(String[] args) {
        int[] array = {9,5,-3,18,30,-2,0,52,6};//{0,34,-9,45,2,4,7,2,4,8,3,-1,-5,888,108,72,36,666};
        System.out.println("排序前: "+Arrays.toString(array));
        mergeSort(array,0,array.length-1);
        System.out.println("排序后: "+Arrays.toString(array));

        //测试一下归并排序的速度  , 给800万个数据, 测试一下
        //创建一个800万个数据的数组, 并加入随机数
        int[] test = new int[8000000];
        for(int i=0;i < 8000000; i++){
            test[i] = (int)(Math.random() * 8000000); //生成一个[0, 8000000)的随机数
        }

        long start = System.currentTimeMillis();//归并排序开始

        mergeSort(test,0,test.length-1);

        long end = System.currentTimeMillis();//归并排序结束

        System.out.println("\n8000000个数据, 归并排序使用了"+(end-start)+"毫秒");//800万个数据, 归并排序使用了1564毫秒, 8千万, 15724毫秒
    }

    /**
     * 归并排序
     * @param array
     * @param low
     * @param high
     */
    public static void mergeSort(int[] array,int low,int high){
        int mid = (high+low)/2;//得到中间索引 中间索引不可能真的指向中间,还是得指向一个下标的, 当前就定义指向 第一个有序序列的最后一个元素

        //如果只有两个数字了,则不会进入递归了 因为 还有两个数据时 {8,5} low=0,mid=0,high=1, 则不会再次进入递归, 返回上一次调用的时候
        //一个数组内只有两个数据了, 那么实际上调用merge时, 会把这一个数据当成, 两个各有一个数据的有序数组,然后进行排序,然后返回上一次调用的时候
        if(low<high){
            //处理左边
            mergeSort(array,low,mid);
            //处理右边
            mergeSort(array,mid+1,high);
            //归并
            merge(array,low,mid,high);
        }
    }

    /**
     * 归并排序  合并
     * @param array         待排序的数组
     * @param low           第一个有序序列的初始索引 类似 array[0]
     * @param mid           中间索引 中间索引不可能真的指向中间,还是得指向一个下标的, 当前就定义指向 第一个有序序列的最后一个元素
     * @param high          第二个索引有序列表的结束索引 类似 length-1
     */
    public static void merge(int[] array,int low,int mid,int high){
        //用于储存归并后的临时数组
        int[] temp = new int[high-low+1];//长度等于 排序的数据的最后一个数的下标 - 第一个数的下标 + 1;
        //用于记录在临时数组中存放的下标
        int index = 0;
        //记录左边数组中需要遍历的下标
        int i=low;
        //记录右边数组中需要遍历的下标
        int j=mid+1;//+1是因为 mid并不是真正指向中间的(比如指向5,6中间这怎么指,所以指向5,+1便为6) 而是指向第一个数组的最后一个数,+1就相当于是第二个数组的第一个数了.

        //遍历两个数组取出小的数字, 放入零时数组中, while(i<=mid && j<=high) 只要这两个数组没有遍历完,则一直继续遍历,
        //但凡有一个数组遍历完成了, 则条件不满足了就会跳出循环, 但是这样会导致另外一个数组还没有遍历完成(后面处理)
        while(i<=mid && j<=high){

            //如果 array[i] <= array[j] 则说明 array[i] 比较小 将array[i]放入 临时数组,然后i++,继续与array[j]比较,然后放入零时数组
            if(array[i] <= array[j]){
                temp[index] = array[i];
                i++;
            }else{//如果 array[i] 不小于等于 array[j], 则是array[i] > array[j], 则array[j]比较小, 然后将array[j]放入临时数组,然后j++,继续与array[i]比较
                temp[index] = array[j];
                j++;
            }
            index++;//每放入一个数到临时数组, index都需要自增,要不然循环将array中的数据放入temp临时数组放了半天,结果temp才一个值....
        }

        //处理多于的数据
        //经过上面的while已将 array中的 逻辑上的两个数组遍历 加入到temp中, 但是由于while(i<=mid && j<=high),会导致只要有一个数组遍历完毕了
        //就会跳出循环,导致另一个数组的数据还没有加入到temp中,所以这里处理多于的数据,使其加入temp中
        while(j<=high){//如果 j<=high则说明第二个数组还没有遍历完毕, 则将第二个数组的数据 加入到 temp中,由于第一个数组与第二个数组都是有序的
                       //如 第一个数组全部加入temp中了, 第二个数组还有一部分没有加入temp中,说明这部分 是在最后的, 要不然比较的时候就将它们加入temp了
                       //所以 只需要将 剩下的数据, 依次加入到temp中即可.
            temp[index] = array[j];
            index++;
            j++;
        }
        while(i<=mid){//如果 i<=mid则说明第一个数组还没有遍历完毕, 则将入第一个数组的数据 依次加入到 temp中.
            temp[index] = array[i];
            index++;
            i++;
        }

        //把临时数组中的数据重新存入原数组
        for(int k=0;k<temp.length;k++){
            array[low+k] = temp[k]; //为什么array[low+k] 而不是直接array[k] 因为 处理第一个数组还好说 low一直是0
                                    //但是处理 第二个数组的时候 low是有值的, 处理的是最开始需要排序的数组的 某一部分数据
                                    //处理完毕后 将这些数据 重新赋给array, 如果不加low则返回到[0][1]...,但是实际处理的
                                    //并不是这一部分,所以就不行, array[low+k]就可以 处理的哪一段,最后则将数据重新赋值给某一段.
        }

    }

}
