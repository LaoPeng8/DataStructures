package org.pjj.datastructure.search;

/**
 * 顺序查找, 也叫线性查找
 *
 * @author PengJiaJun
 * @Date 2020/8/10 14:43
 */
public class SeqSearch {
    public static void main(String[] args) {
        int array[] = {1,9,11,-1,34,-1,89};//无序
        int index = seqSearch(array,-1);
        if(index != -1){
            System.out.println("找到了,下标为: "+index);
        }else{
            System.out.println("没有找到");
        }

        int[] ints = seqSearchList(array, -1);
        if(ints.length > 0){
            System.out.print("找到了,下标为: ");
            for(int temp : ints){
                if(temp != -1){
                    System.out.print(temp+"  ");
                }
            }
        }else{
            System.out.println("没有找到");
        }

    }

    /**
     * 顺序查找
     * 思路: 逐一比对, 发现有相同的值, 就返回下标
     * 这里我们实现的是 顺序查找 是找到一个满足条件的值,就返回
     * @param array
     * @param value
     * @return
     */
    public static int seqSearch(int array[], int value){

        for(int i=0;i<array.length;i++){
            if(value == array[i]){
                return i;
            }
        }
        return -1;
    }

    /**
     * 顺序查找
     * 这里我们实现的是 顺序查找 是找到数组中所有满足条件的值, 就返回
     * @param array
     * @param value
     * @return
     */
    public static int[] seqSearchList(int array[], int value){

        int[] result = new int[array.length];
        for(int r=0;r<result.length;r++){
            result[r] = -1;
        }
        int index = 0;

        for(int i=0;i<array.length;i++){
            if(value == array[i]){
                result[index++] = i;
            }
        }
        return result;
    }
}
