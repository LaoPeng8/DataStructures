package org.pjj.arithmetic.binarysearch;

/**
 * 二分查找
 * @author PengJiaJun
 * @Date 2020/8/25 15:36
 */
public class BinarySearch {

    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,20,25,30,35,40,45,50};
        int index = binarySearchNoRecursion(array,50); //非递归版
        if(index != -1){
            System.out.println("非递归版二分查找==>找到了,下标为: "+index);
        }else{
            System.out.println("没有找到....");
        }

        int indexTwo = binarySearchRecursion(array,0,array.length-1,50); //递归版
        if(indexTwo != -1){
            System.out.println("递归版二分查找==>找到了,下标为: "+indexTwo);
        }else{
            System.out.println("没有找到....");
        }
    }

    /**
     * 二分查找 非递归版 (数组是升序, 如果是降序, 方法实现需要改一下)
     * 其实也可以加个形参, 告诉我们是升序还是降序, 然后写两套代码, 或者在方法内部判断一下是升序还是降序
     *
     * 注意: 二分查找是需要 数组有序的
     * @param array     在哪个数组中查找
     * @param target    需要查找的目标
     * @return          找到了返回该数的下标, 没找到返回-1
     */
    public static int binarySearchNoRecursion(int[] array,int target){
        //本来这两个参数 我写在形参中 后来发现没什么必要, 又不是递归调用, 这个方法只需要调一次, 我就把这两个参数摞到下面了
        int low = 0;                //@param low       从哪来开始
        int high = array.length-1;  //@param high      从哪里结束
        int mid;
        while(low <= high){
            mid =  (low+high) / 2;
            if(target == array[mid]){
                return mid;
            }else if(target > array[mid]){
                low = mid+1;
            }else if(target < array[mid]){
                high = mid-1;
            }
        }
        return -1;
    }

    /**
     * 二分查找 递归版 (数组是升序, 如果是降序, 方法实现需要改一下)
     * 注意: 二分查找是需要 数组有序的
     * @param array
     * @param low
     * @param high
     * @param target
     * @return
     */
    public static int binarySearchRecursion(int[] array,int low,int high,int target){
        int mid =  (low+high) / 2;
        while(low <= high){
            if(target == array[mid]){
                return mid;
            }else if(target > array[mid]){
                return binarySearchRecursion(array,mid+1,high,target);
            }else if(target < array[mid]){
                return binarySearchRecursion(array,low,mid-1,target);
            }
        }
        return -1;
    }

}
