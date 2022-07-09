package org.pjj.datastructure.search;

/**
 * 插值查找
 * @author PengJiaJun
 * @Date 2020/8/11 11:29
 */
public class InsertValueSearch {

    public static void main(String[] args) {
        int[] array = new int[100];
        for(int i=0;i<100;i++){//初始化数组为 1~100
            array[i] = i+1;
        }

        int res = insertValueSearch(array, 0, array.length - 1, 100);
        if(res != -1){
            System.out.println("找到了,下标为: "+res);
        }

    }

    /**
     * 插值查找
     * 注意:  使用插值查找的前提是 该数组是有序的
     * @param array 在这个数组中查找
     * @param left 查找的范围 左边
     * @param right 查找的范围 右边
     * @param findValue 需要查找的数字
     * @return 如果找到,返回对应的下标. 如果没有找到返回-1
     */
    public static int insertValueSearch(int[] array, int left, int right, int findValue){

        //注意: findValue < array[0] || findValue > array[array.length-1] 是必须要的, 不然我们得到的mid可能越界
        if(left > right || findValue < array[0] || findValue > array[array.length-1]){
            return -1;
        }

        //求出mid  插值查找 是改进的 二分查找,  与二分查找的区别就在于这个求mid的公式不一样.
        int mid = left + (right - left) * (findValue - array[left]) / (array[right] - array[left]);
        int midVal = array[mid];

        if(findValue > midVal){ //向右递归
            return insertValueSearch(array,mid + 1,right,findValue);
        }else if(findValue < midVal){//向左递归
            return insertValueSearch(array,left,mid - 1,findValue);
        }else{
            return mid;
        }

    }


}
