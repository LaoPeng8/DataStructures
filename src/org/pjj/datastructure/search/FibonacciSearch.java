package org.pjj.datastructure.search;

import java.util.Arrays;

/**
 * 斐波拉契查找  又称 (黄金分割查找)
 * 没搞明白...
 * @author PengJiaJun
 * @Date 2020/8/11 19:30
 */
public class FibonacciSearch {

    public static int maxSize = 20;

    public static void main(String[] args) {
        int[] array = {1,8,10,89,1000,1234};

        System.out.println("index="+fibonacciSearch(array,1234));

    }


    /**
     * 因为后面 mid=low+F(k-1)-1 , 需要使用到斐波拉契数列, 因此我们需要先获取到一个斐波拉契数列
     * 使用 非递归的方式 得到一个斐波拉契数列
     * @return
     */
    public static int[] fibonacci(){

        int[] f = new int[maxSize];
        f[0] = 1;//斐波拉契数列 前两个数 是固定的 1, 之后从第三数开始 第三个数 = 第一个数 + 第二个数; 第四个数 = 第三个数 + 第二个数;
        f[1] = 1;
        for(int i=2;i<maxSize;i++){
            f[i] = f[i-1]+f[i-2]; //斐波拉契数列 某个数 = 某个数的前一个数 + 某个数的前二个数;
        }

        return f;
    }

    /**
     * 编写斐波拉契查找算法
     * 使用 非递归 的方式编写斐波拉契查找算法
     * @param a     数组
     * @param key   需要查找的关键码(需要查找的值)
     * @return      返回查找到的值的下标, 没有找到返回-1
     */
    public static int fibonacciSearch(int[] a, int key){
        int low = 0;
        int high = a.length-1;
        int k = 0;//表示斐波拉契分割数值的下标
        int mid = 0;//存放mid
        int f[] = fibonacci();//获取到斐波拉契数列

        //获取到斐波拉契分割数值的下标
        while(high > f[k]-1){
            k++;
        }
        //因为 f[k] 值, 可能大于a的长度, 因此我们需要使用Arrays类, 构造一个新的数组并指向a
        int[] temp = Arrays.copyOf(a,f[k]);
        //实际上需要使用a数组最后的数填充temp
        for(int i=high+1;i<temp.length;i++){
            temp[i] = a[high];
        }

        while(low <= high){
            mid = low+f[k-1]-1;
            if(key < temp[mid]){ //我们应该继续向数组的前面查找(左边)
                high = mid - 1;
                //为什么是k--
                //因为: 1.全部元素 = 前面的元素 + 后面元素
                //2. f[k] = f[k-1] + f[k-2]
                //因为 前面有 f[k-1]个元素,所以可以继续拆分 f[k-1] = f[k-2] = f[k-3]
                //即在 f[k-1] 的前面继续查找 k--
                //即下次循环 mid = f[k-1-1]-1
                k--;
            }else if(key > temp[mid]){ //我们应该继续向数组的前面查找(左边)
                low = mid + 1;
                //为什么是 k = k-2
                //因为: 1.全部元素 = 前面的元素 + 后面元素
                //2. f[k] = f[k-1] + f[k-2]
                //3. 因为后面我们有f[k-2], 所以可以继续拆分 f[k-1] = f[k-3] + f[k-4]
                //4. 即在f[k-2] 的前面进行查找 k=k-2
                //5. 即下次循环 mid = f[k-1-2]-1
                k = k-2;
            }else{//key == temp[mid]  找到了
                //需要确定, 返回的是哪个下标
                if(mid <= high){
                    return mid;
                }else{
                    return high;
                }

            }
        }

        return -1;
    }

}
