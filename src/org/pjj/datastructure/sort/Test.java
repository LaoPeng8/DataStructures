package org.pjj.datastructure.sort;

import java.util.Arrays;

/**
 * 练习 每天把排序算法写一遍,直到十分熟练
 * @author PengJiaJun
 * @Date 2020/8/4 12:41
 */
public class Test {

    public static void main(String[] args) {

        int[] array0 = {5,4,3,2,1,3,4};
        System.out.println("冒泡排序前: "+Arrays.toString(array0));
        maopaoSort(array0);
        System.out.println("冒泡排序后: "+Arrays.toString(array0));

        /*===========================================================================*/


        int[] array = {5,4,3,2,1};
        System.out.println("\n插入排序前: "+Arrays.toString(array));
        insertSort(array);
        System.out.println("插入排序后: "+Arrays.toString(array));

        /*===========================================================================*/


        int[] array2 = {-5,-9,9,8,7,13,6,5,4,3,2,1,0};
        System.out.println("\n希尔排序前: "+Arrays.toString(array2));
//        shellSort(array2);
//        shellSort3(new int[]{465,48,2,456,2,48,8,7,5,48,752,-10,-5,-3,-1});
//        shellSort4(array2);
//        shellSort5(array2);
//        shellSort6(array2);
//        shellSort7(array2);
//        shellSort8(array2);
//        shellSort9(array2);
//        shellSort10(array2);
//        shellSort11(array2);
//        shellSort12(array2);
        shellSort13(array2);
        System.out.println("希尔排序后: "+Arrays.toString(array2));


        /*===========================================================================*/
        int[] array3 = {9,8,7,6,5,4,3,2,1,0,-1,-1,-8};
        System.out.println("\n快速排序前: "+Arrays.toString(array3));
//        quickSort(array3,0,array3.length-1);
//        quickSort2(array3,0,array3.length-1);
//        quickSort3(array3,0,array3.length-1);
//        quickSort4(array3,0,array3.length-1);
//        quickSort5(array3,0,array3.length-1);
//        quickSort6(array3,0,array3.length-1);
//        quickSort7(array3,0,array3.length-1);
//        quickSort8(array3,0,array3.length-1);
//        quickSort9(array3,0,array3.length-1);
//        quickSort10(array3,0,array3.length-1);
        quickSort11(array3,0,array3.length-1);
        System.out.println("快速排序后: "+Arrays.toString(array3));

        /*===========================================================================*/
        int[] array4 = {546,-3,-9,54,89,3,5,4,87,15,61,101,520,13,14,0,-1,3};
        System.out.println("\n归并排序前: "+Arrays.toString(array4));
//        mergeSort(array4,0,array4.length-1);
//        mergeSort2(array4,0,array4.length-1);
//        mergeSort3(array4,0,array4.length-1);
//        mergeSort4(array4,0,array4.length-1);
//        mergeSort5(array4,0,array4.length-1);
//        mergeSort6(array4,0,array4.length-1);
//        mergeSort7(array4,0,array4.length-1);
        mergeSort8(array4,0,array4.length-1);
        System.out.println("归并排序后: "+Arrays.toString(array4));

        /*===========================================================================*/
        int[] array5 = {4564,874,5,18,75,8,712,7,2,483,7,545,50,2,1,4590,45,48,5465,8789,5};
        System.out.println("\n基数排序前: "+Arrays.toString(array5));
//        radixSort(array5);
//        radixSort2(array5);
//        radixSort3(array5);
//        radixSort4(array5);
//        radixSort5(array5);
//        radixSort6(array5);
//        radixSort7(array5);
        radixSort8(array5);
        System.out.println("基数排序后: "+Arrays.toString(array5));

        /*===========================================================================*/
        int[] array6 = {9,6,8,7,0,1,10,4,2};
        System.out.println("\n堆排序前: "+Arrays.toString(array6));
//        heapSort(array6);
        heapSort2(array6);
        System.out.println("堆排序后: "+Arrays.toString(array6));
    }

    //冒泡排序
    public static void maopaoSort(int[] array){
        for(int i=0;i<array.length-1;i++){
            for(int j=0;j<array.length-i-1;j++){
                if(array[j] > array[j+1]){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }
    }
    
    //测试自己写插入排序
    public static void insertSort(int[] array){

        for(int i=1;i<array.length;i++){// 数组下标为0的默认是有序列表, 所以从1开始

            int temp = array[i];//待插入数据
            int index = i-1;

            while(index>=0 && temp < array[index]){
                array[index+1] = array[index];
                index--;
            }
            array[index+1] = temp;

        }

    }

    //测试自己写希尔排序 (希尔排序其实就是 插入排序套一层 步长的for循环)
    public static void shellSort(int[] array){

        for(int gap=array.length/2;gap>0;gap/=2){//步长最开始为 array长度/2 之后 步长 = 步长/2; 步长为1时是最后一趟排序(步长为1时就相当于进行普通的插入排序)

            for(int i=gap;i<array.length;i++){//从 第"1"个数开始排序, 因为第"0"个数 都是各分组的 "有序列表"

                int temp = array[i];//待插入的数
                int index = i-gap;//有序列表的最后一个数

                while(index >= 0 && temp < array[index]){
                    array[index+gap] = array[index];//如果待插入的数 小于 有序列表的最后一个数, 则这最后一个数后移一格
                    index = index - gap;//将index 前移一格 继续判断 有序列表的倒数第二个数, 是否小于 待插入的数
                    //直到 找到 待插入的数 > 有序列表的某个数, 则将待插入的数 插入到 这某个数的后面.
                    //或者 index < 0; 了之后跳出循环, 则说明 待插入的数 是有序列表中最小的数, 则放在 有序列表的第一个位置
                }

                array[index+gap] = temp;

            }

        }

    }

    //希尔排序, 一天写一遍 加深记忆,我就不信会忘记了, 希尔排序真的很牛皮,速度太快了.
    public static void shellSort2(int[] array){

        for(int gap=array.length/2; gap>0;  gap/=2){

            for(int i=gap;i<array.length;i++){//i=gap 从步长开始  步长前面的数就是各分组 的有序列表的最后一个数也是第一个数(因为就一个)

                int value = array[i];//待插入的数
                int index = i-gap;//有序列表的最后一个数

                while(index>=0 && value < array[index]){//判断 待插入的数 是否小于 有序列表的最后一个数 如小于 则将 有序列表的最后一个数后移, 继而继续判断倒数第二个数
                    array[index+gap] =  array[index];//后移
                    index = index - gap;//与 待插入的数比较 的有序列表的数 下标前移, 继续判断
                    //如 待插入的数 不小于(也就是大于) 有序列表的某个数 则将待插入的数 放在该数后面
                    //如 待插入的数 一直小于 有序列表中的数 直到 index<0则说明 待插入的数 是当前有序列表中最小的数, 则插入在第一个位置
                }
                array[index+gap] = value;
            }

        }

    }

    //希尔排序, 一天写一遍 加深记忆,我就不信会忘记了, 希尔排序真的很牛皮,速度太快了.
    public static void shellSort3(int[] array){

        for(int gap=array.length/2; gap>0; gap/=2){//步长
            for(int i=gap;i<array.length;i++){
                int temp = array[i];//待插入的数
                int index = i-gap;//i所在分组 有序列表 的最后一个数,待插入的数的上一个(该分组的上一个)数就是有序列表的最后一个数

                while(index>=0 && temp < array[index]){
                    array[index+gap] = array[index];
                    index-=gap;
                }
                array[index+gap] = temp;

            }

        }

    }

    //希尔排序, 一天写一遍 加深记忆,我就不信会忘记了, 希尔排序真的很牛皮,速度太快了.
    public static void shellSort4(int[] array){

        for(int gap=array.length/2;gap>0;gap/=2){

            for(int i=gap;i<array.length;i++){

                int temp = array[i];//待插入的数
                int index = i-gap;//有序列表的最后一个数, 待插入的数的前一个数

                while(index>=0 && temp < array[index]){//待插入的数 如 小于有序列表的最后一个数, 则将有序列表的最后一个数后移, 同时index往前移一格, 继而与有序列表的倒数第二个数比较...
                    array[index+gap] = array[index];//将有序列表的数后移
                    index-=gap;//index往前移动一格, 继而遍历有序列表
                }
                array[index+gap] = temp;

            }

        }

    }

    //希尔排序, 一天写一遍 加深记忆,我就不信会忘记了, 希尔排序真的很牛皮,速度太快了.
    public static void shellSort5(int[] array){

        for(int gap=array.length/2;gap>0;gap/=2){
            for(int i=gap;i<array.length;i++){
                int temp = array[i];//待插入的数,无序列表的第一个
                int index = i-gap;//待插入的数前一个就是有序列表的最后一个数

                while(index>=0 && temp < array[index]){
                    array[index+gap] = array[index];
                    index-=gap;
                }
                array[index+gap] = temp;
            }
        }

    }

    //希尔排序
    public static void shellSort6(int[] array){

        for(int gap=array.length/2;gap>0;gap/=2){
            for(int i=gap;i<array.length;i++){
                int temp = array[i];//待插入元素(无序列表第一个)
                int index = i-gap;//有序列表最后一个
                while(index >= 0 && temp < array[index]){
                    array[index+gap] = array[index];//后移一格
                    index = index - gap;
                }
                array[index+gap] = temp;
            }
        }

    }

    //希尔排序
    public static void shellSort7(int[] array){
        for(int gap=array.length/2;gap>0;gap/=2){
            for(int i=gap;i<array.length;i++){
                int temp = array[i];//待插入的数据,无序列表第一个
                int index = i-gap;//有序列表最后一个
                while(index>=0 && temp < array[index]){
                    array[index+gap] = array[index];//后移
                    index-=gap;
                }
                array[index+gap] = temp;
            }
        }
    }

    //希尔排序
    public static void shellSort8(int[] array){
        for(int gap=array.length/2;gap>0;gap/=2){
            for(int i=gap;i<array.length;i++){
                int temp = array[i];//待插入的数,无序列表第一个
                int index = i-gap;//有序列表第一个
                while(index >= 0 && temp < array[index]){
                    array[index+gap] = array[index];
                    index-=gap;
                }
                array[index+gap] = temp;
            }
        }
    }

    //希尔排序
    public static void shellSort9(int[] array){
        for(int gap=array.length/2;gap>0;gap/=2){
            for(int i=gap;i<array.length;i++){
                int temp = array[i];
                int index = i-gap;
                while(index >= 0 && temp < array[index]){
                    array[index+gap] = array[index];
                    index-=gap;
                }
                array[index+gap] = temp;
            }
        }
    }

    //希尔排序
    public static void shellSort10(int[] array){
        for(int gap=array.length/2;gap>0;gap/=2){
            for(int i=gap;i<array.length;i++){
                int temp = array[i];//待插入的节点 (无序列表第一个)
                int index = i-gap;//有序列表最后一个
                while(index>=0 && temp < array[index]){
                    array[index+gap] = array[index];
                    index = index - gap;
                }
                array[index+gap] = temp;
            }
        }
    }

    //希尔排序
    public static void shellSort11(int[] array){
        for(int gap=array.length/2;gap>0;gap=gap/2){
            for(int i=gap; i< array.length; i=i+gap){
                int temp = array[i];//需要插入的节点(无序列表第一个)
                int index = i-gap;//需要比较的节点(有序列表最后一个)
                if(i>=0 && temp < array[index]){
                    array[index+gap] = array[index];
                    index = index - gap;
                }
                array[index+gap] = temp;
            }
        }
    }

    //希尔排序
    public static void shellSort12(int[] array){
        for(int gap=array.length/2;gap>0;gap/=2){
            for(int i=gap; i<array.length; i+=gap){
                int temp = array[i]; //待排序的数, 有序数组的第一个
                int index = i-gap;//与待排序的数比较的数, 无序列表最后一个(有序列表的第一个的前一个就是无序列表最后一个)
                while(index>=0 && temp<array[index]){
                    array[index+gap] = array[index];
                    index = index-gap;
                }
                array[index+gap] = temp;
            }
        }
    }

    //希尔排序
    public static void shellSort13(int[] array){

        for(int gap=array.length/2;gap>0;gap=gap/2){

            for(int i=gap;i<array.length;i=i+gap){

                int temp = array[i];
                int index = i-gap;

                while(index>=0 && temp<array[index]){
                    array[index+gap] = array[index];
                    index = index - gap;
                }
                array[index+gap] = temp;

            }

        }

    }


    /*==========================================================================================================*/


    //快速排序, 一天写一遍 加深记忆,我就不信会忘记了, 快速排序真的很牛皮,速度太快了.
    public static void quickSort(int[] array,int start,int end){

        if(start<end){
            int low = start;
            int high = end;
            int pivot = array[start];

            while(low<high){

                while(low<high && array[high] >= pivot){
                    high--;
                }
                array[low] = array[high];

                while(low<high && array[low] <= pivot){
                    low++;
                }
                array[high] = array[low];

            }
            array[low] = pivot;

            quickSort(array,start,low);
            quickSort(array,high+1,end);
        }

    }

    //快速排序, 一天写一遍 加深记忆,我就不信会忘记了, 快速排序真的很牛皮,速度太快了.
    public static void quickSort2(int[] array,int start,int end){

        //退出递归的条件 排序的数组还有两个数时 也会执行 因为start=0,end=1 满足start<end
        if(start<end){//若排序的数组只有一个数时, 就不会再次进入递归调用了, 因为start=0,end=0 不满足start<end

            int low = start;//start 与 end 后面需要用,不能改变它们的值, 所以使用low与high,代替它们, 这样先定义即使用了 start与end 也保存了start与end的值
            int high = end;
            int pivot = array[start];//取标准值, 每次以排序的数据中的 第一个为标志值, 大于标准值的在右边,小于标准值的在左边

            //low与high是会移动的,low相对于从array[0]开始low++, high相当于从array[length-1]开始high--
            while(low<high){//low本身在数组左边, high本身在数组右边, low理应 小于 high 如low不小于high说明他们已经指向同一个值了,则不会继续循环

                while(low<high && array[high] >= pivot){//如array[high] >= pivot,则说明array[high]本身就应该在现在这个位置,无需变化,只需将high--;继续遍历
                    high--;
                }
                //如跳出循环,则说明array[high] < pivot, 小于pivot的数应该在 pivot左边, 现在却在pivot右边,所以将array[high]赋给array[low]则, array[high]的值就到pivot左边去了
                array[low] = array[high];

                while(low<high && array[low] <= pivot){//如array[low] <= pivot,则说明array[low]本身就应该在现在这个位置,无需变化,只需将low++;继续遍历
                    low++;
                }
                //如跳出循环,则说明array[low] > pivot,大于pivot的数应该在 pivot的右边, 现在却在pivot左边,所以将array[low]赋给array[high]则, array[low]的值就去pivot右边去了
                array[high] = array[low];

            }
            //当退出循环时 说明while(low<high)已经不满足了, low与high已经重合了
            array[low] = pivot;//之后将 pivot赋值给 已经重合了的 low或high

            //此时一轮排序就已经完成了, pivot左边以全部是小于pivot的数, pivot右边以全部是大于pivot的数
            //但是并不能保证 pivot左边全部小于pivot的数 是有序的
            //所以需要再次递归调用
            quickSort2(array,start,low);
            quickSort2(array,high+1,end);
        }


    }

    //快速排序, 一天写一遍 加深记忆,我就不信会忘记了, 快速排序真的很牛皮,速度太快了.
    public static void quickSort3(int[] array,int start,int end){

        if(start<end){
            int low = start;
            int high = end;
            int pivot = array[start];

            while(low < high){

                while(low<high && array[high] >= pivot){
                    high--;
                }
                array[low] = array[high];

                while(low<high && array[low] <= pivot) {
                    low++;
                }
                array[high] = array[low];

            }
            //low 与 high 重合
            array[low] = pivot;

            //一轮排序完成后, pivot左边以是全部小于pivot的数了, pivot右边以是全部大于pivot的数了, 但是左边全部小于pivot并不代表他们是有序的, 右边也一样
            quickSort3(array,start,low);//处理左边的数
            quickSort3(array,low+1,end);
        }

    }

    //快速排序
    public static void quickSort4(int[] array,int start,int end){

        if(start < end){//递归退出条件
            int low = start;
            int high = end;
            int pivot = array[start];

            while(low < high){

                while(low < high && array[high] >= pivot){
                    high--;
                }
                array[low] = array[high];

                while(low < high && array[low] <= pivot){
                    low++;
                }
                array[high] = array[low];

            }

            //low 与 high 重合了.
            array[low] = pivot;
            //至此 一轮排序完成 pivot左边是全部是 小于pivot的数(虽然全是小于pivot的数 但是不保证有序), pivot右边全部是 大于pivot的数

            quickSort4(array,start,low);//处理pivot左边的
            quickSort4(array,low+1,end);//处理pivot右边的
        }

    }

    //快速排序
    public static void quickSort5(int[] array,int start,int end){

        if(start < end){//递归退出条件
            int low = start;
            int high = end;
            int pivot = array[start];

            while(low < high){
                while(low < high && array[high] >= pivot){
                    high--;
                }
                array[low] = array[high];

                while(low < high && array[low] <= pivot){
                    low++;
                }
                array[high] = array[low];
            }
            //low与high已经重合了
            array[low] = pivot;

            quickSort5(array,start,low);
            quickSort5(array,low+1,end);
        }

    }

    //快速排序
    public static void quickSort6(int[] array,int start,int end){

        if(start < end){//递归退出条件
            int low = start;
            int high = end;
            int pivot = array[start];

            while(low < high){

                while(low < high && array[high] >= pivot){
                    high--;
                }
                array[low] = array[high];

                while(low < high && array[low] <= pivot){
                    low++;
                }
                array[high] = array[low];

            }
            //low 与 high 重合了
            array[low] = pivot;

            quickSort6(array,start,low);//处理左边
            quickSort6(array,low+1,end);//处理右边
        }


    }

    //快速排序
    public static void quickSort7(int[] array,int start,int end){

        if(start < end){
            int low = start;
            int high = end;
            int pivot = array[start];

            while(low < high){

                while(low < high && array[high] >= pivot){
                    high--;
                }
                array[low] = array[high];

                while(low < high && array[low] <= pivot){
                    low++;
                }
                array[high] = array[low];

            }

            //low与high重合了, 数组遍历完了
            array[low] = pivot;

            quickSort7(array,start,low);//处理左边, 虽然左边的数都小于pivot了但是, 不一定是有序的
            quickSort7(array,low+1,end);//处理右边, 虽然右边的数都大于pivot了,但是不一样是有序的
        }

    }

    //快速排序
    public static void quickSort8(int[] array,int start,int end){

        if(start < end){
            int low = start;
            int high = end;
            int pivot = array[start];

            while(low < high){

                while(low < high && array[high] >= pivot){
                    high--;
                }
                array[low] = array[high];

                while(low < high && array[low] <= pivot){
                    low++;
                }
                array[high] = array[low];

            }

            array[low] = pivot;

            quickSort8(array,start,low);
            quickSort8(array,low+1,end);
        }
    }

    //快速排序
    public static void quickSort9(int[] array,int start,int end){

        if(start<end){
            int low = start;
            int high = end;
            int pivot = array[start];

            while(low < high){

                while(low<high && array[high] >= pivot){
                    high--;
                }
                array[low] = array[high];

                while(low<high && array[low] <= pivot){
                    low++;
                }
                array[high] = array[low];

            }
            array[high] = pivot;

            quickSort9(array,start,low);
            quickSort9(array,low+1,end);
        }

    }

    //快速排序
    public static void quickSort10(int[] array,int start,int end){

        if(start < end){

            int low=start;
            int high=end;
            int pivot=array[start];

            while(low < high){

                while(low < high && array[high] >= pivot){
                    high--;
                }
                array[low] = array[high];

                while(low < high && array[low] <= pivot){
                    low++;
                }
                array[high] = array[low];
            }
            array[low] = pivot;

            quickSort10(array,start,low);
            quickSort10(array,low+1,end);

        }

    }

    //快速排序
    public static void quickSort11(int[] array,int start,int end){

        if(start < end){
            int pivot = array[start];
            int low = start;
            int high = end;

            while(low < high){

                while(low < high && array[high] >= pivot){
                    high--;
                }
                array[low] = array[high];

                while(low < high && array[low] <= pivot){
                    low++;
                }
                array[high] = array[low];
            }
            array[low] = pivot;

            quickSort11(array,start,low);
            quickSort11(array,low+1,end);
        }

    }

    /*==========================================================================================================*/


    //归并排序 之 归并
    public static void merge(int[] array,int low,int mid,int high){//low相当于是array[0]  high相当于是array[length-1] mid并不是真正指向中间 而是第一个数组的最后一个, 那么mid+1就是第二个数组的low

        int[] temp = new int[(high-low)+1];//比较两个数组之后 将这两个数组的数据 按顺序放入temp,完成一轮排序后将temp的值 重新赋给 array 完成一轮排序
        int index = 0;//用于记录 放入 temp数组的下标
        int i = low;//用于遍历第一个数组
        int j = mid+1;//用于遍历第二个数组

        //则两个数组必须都没有遍历完才会继续循环, 如 i 或 j 其中一个已经 超出对应的数组的下标, 则退出循环, 此时只有一个数组遍历完成, 还有一个数组没有遍历完成
        while(i <= mid && j<=high){

            if(array[i] <= array[j]){ //array[i] 比较小, 则将array[i]放入 temp中
                temp[index++] = array[i++];
            }else{ //array[j] 比较小, 则将array[j]放入temp中
                temp[index++] = array[j++];
            }

        }

        //处理多于的数据, 上面那个while循环 只会处理完一个数组, 另一个数组没有处理完,就到这里了.
        while(i<=mid){
            temp[index++] = array[i++];
        }
        while(j<=high){
            temp[index++] = array[j++];
        }

        //将temp的数据(排序后的数据) 重新 赋给 array
        for(int k=0;k<temp.length;k++){
            array[low+k] = temp[k];
        }

    }

    //归并排序
    public static void mergeSort(int[] array,int low,int high){

        if(low<high){ //只要有两个数 都会继续递归, 只有一个数时 low与high重合, 不会执行了,退出,返回上一层
            int mid = (high+low)/2;

            mergeSort(array,low,mid);//处理左边

            mergeSort(array,mid+1,high);//处理右边

            merge(array,low,mid,high);//归并
        }

    }

    //归并排序 之 归并
    public static void merge2(int[] array,int low,int mid,int high){

        int temp[] = new int[(high-low)+1];//临时数组,将排序后的结果放入该数组, 最后将该数组重新赋给array完成一轮排序
        int tempIndex = 0; //记录temp
        int i = low;    //遍历第一个数组
        int j = mid+1;  //遍地第二个数组

        //遍历这两个数组, 如其中一个数组遍历完毕了, 则另一个数组就不会继续遍历了
        while(i <= mid && j <= high){

            if(array[i] <= array[j]){ //array[i] 比较小
                temp[tempIndex++] = array[i++];//array[i]比较小, 则将array[i]的值放入 temp
            }else{ //array[j] 比较小
                temp[tempIndex++] = array[j++];//array[j]比较小, 则将array[j]的值放入 temp
            }

        }

        //处理多于的数据
        while(i <= mid){
            temp[tempIndex++] = array[i++];
        }
        while(j <= high){
            temp[tempIndex++] = array[j++];
        }

        //将temp的数据重新放回array
        for(int k=0;k<temp.length;k++){
            array[low+k] = temp[k];
        }

    }

    //归并排序
    public static void mergeSort2(int[] array,int low,int high) {

        if(low < high){
            int mid = (low+high)/2;

            mergeSort2(array,low,mid);//处理左边(处理第一个数组)
            mergeSort2(array,mid+1,high);//处理右边(处理第二个数组)
            merge2(array,low,mid,high);//归并
        }

    }

    //归并排序 之 归并
    public static void merge3(int[] array,int low,int mid,int high){
        int[] temp = new int[(high-low)+1];
        int tempIndex = 0;
        int i=low;
        int j=mid+1;

        while(i<=mid && j<=high){

            if(array[i] <= array[j]){//array[i]比较小
                temp[tempIndex++] = array[i++];
            }else{//array[j]比较小
                temp[tempIndex++] = array[j++];
            }

        }

        //处理剩余的数据
        while(i<=mid){
            temp[tempIndex++] = array[i++];
        }
        while(j<=high){
            temp[tempIndex++] = array[j++];
        }

        //将temp的值,重新赋给array
        for(int k=0;k<temp.length;k++){
            array[low+k] = temp[k];
        }

    }

    //归并排序
    public static void mergeSort3(int[] array,int low,int high) {

        if(low < high){//递归退出条件
            int mid = (low+high)/2;
            mergeSort3(array,low,mid);
            mergeSort3(array,mid+1,high);
            merge3(array,low,mid,high);
        }

    }

    //归并排序 之 归并
    public static void merge4(int[] array,int low,int mid,int high){
        int i = low;//用于遍历第一个数组的下标
        int j = mid+1;//用于遍历第二个数组的下标
        int[] temp = new int[(high-low)+1];//临时数组,将需要排序的数组内的元素,按照规则一个个放入temp,最后从temp中取出来,重新赋给需要排序的数组,就完成一轮排序
        int tempIndex = 0;//记录temp的下标

        //遍历两个数组,如其中一个遍历完毕,则直接退出, 另外一个还没有遍历完的数组等会处理
        while(i <= mid && j <= high){

            if(array[i] <= array[j]){
                temp[tempIndex++] = array[i++];
            }else{
                temp[tempIndex++] = array[j++];
            }

        }

        //处理多余的数组
        while(i <= mid){//说明在上一个while中,i所在的数组没有遍历完成,在这里就将i所在的数组遍历完
            temp[tempIndex++] = array[i++];
        }
        //说明: 这两个while while(i <= low)||while(j <= high),必只有一个执行, 要不然最开始的那个while(i <= low && j <=high)不会执行完的,也不会到这里
        while(j <= high){//说明在上一个while中,j所在的数组没有遍历完成,在这里就将j所在的数组遍历完
            temp[tempIndex++] = array[j++];
        }

        //将temp中的数据重新赋值给array
        for(int k=0;k<temp.length;k++){
            array[low+k] = temp[k];
        }

    }

    //归并排序
    public static void mergeSort4(int[] array,int low,int high) {
        if(low < high){
            int mid = (low+high)/2;
            mergeSort4(array,low,mid);//处理左边
            mergeSort4(array,mid+1,high);//处理右边
            merge4(array,low,mid,high);
        }
    }

    //归并排序 之 归并
    public static void merge5(int[] array,int low,int mid,int high){
        int[] temp = new int[(high-low)+1];//将array一边排序一边将数据放入temp,最后从temp中取出来,重新赋值给array,就完成了一轮排序
        int tempIndex = 0;//temp下标
        int i = low;//循环第一个数组
        int j=mid+1;//循环第二个数组

        while(i<=mid && j<=high){//遍历两个数组,其中一个数组遍历完成就会退出循环, 另一个数组必没有遍历完(后面处理)
            if(array[i] <= array[j]){
                temp[tempIndex++] = array[i++];
            }else{
                temp[tempIndex++] = array[j++];
            }
        }

        //处理多于的数据(处理没有遍历完的那个数组)
        while(i<=mid){//进入了则说明是 i 代表的数组没有遍历完
            temp[tempIndex++] = array[i++];
        }
        while(j<=high){//进入了则说明是 i 代表的数组没有遍历完
            temp[tempIndex++] = array[j++];
        }

        //array[]全部放入temp[]后,再从temp中取出来,然后重新赋值给array, 这样就完成一轮排序
        for(int k=0;k<temp.length;k++){
            array[low+k] = temp[k];//array[low + k] 是因为, 每次排序的不一定是整是数组, 可能是数组的左边一部分,也可能是右边一部分
            //左边一部分不加low可以反正都是从0开始, 但是右边是从mid开始的, 所以需要+low
        }
    }

    //归并排序
    public static void mergeSort5(int[] array,int low,int high) {
        if(low < high){
            int mid = (high+low)/2;
            mergeSort5(array,low,mid);
            mergeSort5(array,mid+1,high);
            merge5(array,low,mid,high);
        }
    }

    //归并排序 之 归并
    public static void merge6(int[] array,int low,int mid,int high){
        int[] temp = new int[(high-low)+1];//临时数组
        int tempIndex = 0;//temp的下标
        int i=low;
        int j=mid+1;

        while(i <= mid && j<=high){
            if(array[i] <= array[j]){ //array[i]比较小
                temp[tempIndex++] = array[i++];
            }else{ //array[i] > array[j]  array[j]比较小
                temp[tempIndex++] = array[j++];
            }
        }

        while(i<=mid){
            temp[tempIndex++] = array[i++];
        }
        while(j<=high){
            temp[tempIndex++] = array[j++];
        }

        for(int k=0;k<temp.length;k++){
            array[low+k] = temp[k];
        }

    }

    //归并排序
    public static void mergeSort6(int[] array,int low,int high) {
        if(low < high) {
            int mid = (low+high) / 2;
            mergeSort6(array,low,mid);
            mergeSort6(array,mid+1,high);
            merge6(array,low,mid,high);
        }

    }

    //归并排序 之 归并
    public static void merge7(int[] array,int low,int mid,int high){
        int[] temp = new int[(high-low)+1];
        int tempIndex = 0;
        int i = low;
        int j = mid+1;

        while(i <= mid && j <= high){
            if(array[i] < array[j]){
                temp[tempIndex++] = array[i++];
            }else{
                temp[tempIndex++] = array[j++];
            }
        }

        while(i <= mid){
            temp[tempIndex++] = array[i++];
        }
        while(j <= high){
            temp[tempIndex++] = array[j++];
        }

        for(int k=0;k<temp.length;k++){
            array[k+low] = temp[k];
        }
    }

    //归并排序
    public static void mergeSort7(int[] array,int low,int high) {
        if(low < high){
            int mid = (low+high)/2;
            mergeSort7(array,low,mid);
            mergeSort7(array,mid+1,high);
            merge7(array,low,mid,high);
        }
    }

    //归并排序 之 归并
    public static void merge8(int[] array,int low,int mid,int high){
        int[] temp = new int[(high-low)+1];//用于存放一轮排序后的数据的数组
        int index = 0;//temp数组的下标
        int i=low;
        int j=mid+1;

        while(i<=mid && j<=high){

            if(array[i] <= array[j]){
                temp[index++] = array[i++];
            }else{
                temp[index++] = array[j++];
            }

        }

        while(i<=mid){
            temp[index++] = array[i++];
        }

        while(j<=high){
            temp[index++] = array[j++];
        }

        for(int k=0;k<temp.length;k++){
            array[k+low] = temp[k];
        }
    }

    //归并排序
    public static void mergeSort8(int[] array,int low,int high) {
        if(low < high){
            int mid=(low+high)/2;
            mergeSort8(array,low,mid);
            mergeSort8(array,mid+1,high);
            merge8(array,low,mid,high);
        }
    }

    /*==========================================================================================================*/


    //基数排序
    public static void radixSort(int[] array){
        int max = Integer.MIN_VALUE;//先假定一个值是该数组的最大值.
        for(int m=0;m<array.length;m++){//遍历整个数组, 找出最大值.
            if(array[m] > max){
                max = array[m];
            }
        }

        int maxLength = Integer.toString(max).length();//根据最大数, 找出最大数是几位数

        int[][] temp = new int[10][array.length];//共10个桶, 每个桶的长度都与array一样长, 因为万一刚好都是放在一个桶中的呢?
        int[] tempIndex = new int[10];//代表 10个桶 中分别存储了多少个数据 tempIndex[0]=1; 代表第0个桶 存放了1个数据

        int ys;//余数  第一轮是个位数 第二轮是十位数 第三轮是百位数 依此类推
        for(int i=0,n=1;i<maxLength;i++,n*=10){//最大数是几位 就循环多少次(就排序几轮)
            for(int j=0;j<array.length;j++){//遍历数组 完成一轮排序 (将该数组中的元素 全部放入 桶中)
                ys = array[j]/n%10;//求出该数的 余数(就是该数应该放在哪个桶中)
                //按照余数 将该数 放入某个桶中temp[ys] 至于放在该temp[ys]桶中的第几个元素,则看该桶现有多少个元素,依次存放
                temp[ys][tempIndex[ys]] = array[j];//tempIndex[ys]的值就是 代表某个桶中 共有几个元素
                tempIndex[ys]++;
            }

            int index = 0;//重新放回array中,需要的下标
            for(int k=0;k<tempIndex.length;k++){//遍历所有桶

                if(tempIndex[k] != 0){//该tempIndex[k]桶中 存放着 至少一个数据, 则会进入if取出桶中的值,重新赋给array
                    for(int m=0;m<tempIndex[k];m++){
                        array[index++] = temp[k][m];
                    }
                    //该if将tempIndex[k]该桶中的数取出来 重新赋值给 array 后
                    tempIndex[k] = 0;//将该桶清空,以便下一轮 将array中数 放入桶中
                }

            }

        }
    }

    //基数排序
    public static void radixSort2(int[] array){

        int max = Integer.MIN_VALUE;//先假定数组最大值
        for(int m=0;m<array.length;m++){//找出数组中真正的最大值
            if(array[m] > max){
                max = array[m];
            }
        }

        int maxLength = Integer.toString(max).length(); //根据数组最大值 找出该最大值是几位数, 是几位数则遍历几轮

        int[][] temp = new int[10][array.length];//10个数组(10个桶),每个桶的容量都是array.length,因为万一array的每个数都是放在一个桶中的呢?
        int[] tempCounts = new int[10]; //分别记录10个桶 每个桶中装了多少个数据

        for(int i=0,n=1;i<maxLength;i++,n*=10){
            for(int j=0;j<array.length;j++){
                int ys = array[j]/n%10;//余数, 每轮外层for循环,第一次取 数的个位数,第二次取百位,依次递增, 然后比较余数 将其放入 对应的桶中

                temp[ys][tempCounts[ys]] = array[j];
                tempCounts[ys]++;//该桶中加入了一个数据,所以需要自增

            }

            //将array的数据 根据规则 全部分类放入10个桶中了
            //即可按照顺序(从桶0开始取,一直到桶9,每个桶都是从0开始取,一直到最后一个有效数据)从桶中取出来,然后重新赋给array,就完成了一轮排序

            int index = 0;//记录array的下标
            for(int k=0;k<tempCounts.length;k++){//遍历10个桶 0~9个桶

                if(tempCounts[k] != 0){//如该桶存储的数据量 不为0, 只要该桶存了一个数据,那么就是 1
                    for(int m=0;m<tempCounts[k];m++){//该桶中有几个数据就遍历几次
                        array[index++] = temp[k][m];
                    }
                    tempCounts[k] = 0;//清空该桶
                }

            }
        }

    }


    //基数排序
    public static void radixSort3(int[] array){
        //首先要找出数组最大值,然后根据最大值的位数,来决定循环多少次   第一次循环根据数组中的数的个位数,决定放入哪个桶,第二次十位,三次百位
        int max = Integer.MIN_VALUE;//假定最大值
        for(int i=0;i<array.length;i++){//遍历数组,找出真正的最大值
            if(array[i] > max){
                max = array[i];
            }
        }

        int maxLength = Integer.toString(max).length();//找出该最大数是几位数
        int[][] temp = new int[10][array.length];//10个桶,每个桶长度为array.length
        int[] tempCounts = new int[10];//分别代表 10个桶 中 每个桶都存储了多少个数据
        int ys = 0;
        //根据最大数的位数,来决定循环几次
        for(int i=0,n=1;i<maxLength;i++,n*=10){
            for(int j=0;j<array.length;j++){
                ys = array[j]/n%10;//找出该数的 个位数(i=0时找出个位数,i=1时找出十位数,i=2时找出百位数)
                temp[ys][tempCounts[ys]] = array[j];//根据ys决定是在哪个桶中
                tempCounts[ys]++;
            }

            //全部放入桶中后, 依次取出来, 重新赋值给array
            int index = 0;//记录array的下标
            for(int k=0;k<tempCounts.length;k++){//遍历每个桶
                if(tempCounts[k] != 0){//如果该桶有数据
                    for(int m=0;m<tempCounts[k];m++){
                        array[index++] = temp[k][m];
                    }
                    tempCounts[k] = 0;//遍历完一个桶,则将它清空,方便下次遍历
                }
            }

        }

    }

    //基数排序
    public static void radixSort4(int[] array){
        int max = Integer.MIN_VALUE;//假定最大值
        for(int m=0;m<array.length;m++){//找出数组中真正的最大值
            if(array[m] > max){
                max = array[m];
            }
        }

        int maxLength = Integer.toString(max).length();//找出最大数的 是几位数, 是几位数则进行几轮排序
        int[][] temp = new int[10][array.length];//10个桶
        int[] counts = new int[10];//记录10个桶,每个桶存储了多少数据

        int ys;
        for(int i=0,n=1;i<maxLength;i++,n*=10){
            for(int j=0;j<array.length;j++){
               ys = array[j]/n%10; //i=0;时 取个位数, i=1;时 取十位数, i=2;时 取百位数, 依次递增
                temp[ys][counts[ys]] = array[j];
                counts[ys]++;
            }//循环完后,就将该轮的数据全部放入各自的桶中了

            //将temp中的数据依次取出来放入array中, 完成一轮排序
            int index = 0;//记录array的下标
            for(int k=0;k<counts.length;k++){//遍历每个桶

                if(counts[k] != 0){//该桶中 至少有一个数据,则将它们取出来放入array
                    for(int m=0;m<counts[k];m++){//该桶中有几个数据,就循环几次
                        array[index++] = temp[k][m];
                    }
                    counts[k] = 0;//取出k桶中的数据后,将k桶清空
                }

            }

        }

    }

    //基数排序
    public static void radixSort5(int[] array){
        int max = Integer.MIN_VALUE;//假定最大数
        for(int m=0;m<array.length;m++){//在数组中找到真正的最大数
            if(array[m] > max){
                max = array[m];
            }
        }

        int maxLength = Integer.toString(max).length();//根据最大数 找出 最大数是几位数
        int[][] temp = new int[10][array.length];//10个桶
        int[] tempCounts = new int[10];//分别代表 10个桶中每个桶存储了多少数据

        for(int i=0,n=1;i<maxLength;i++,n*=10){//最大数是几位就进行几轮排序, 每轮排序分别比较 数组值的 个位 十位 百位 依次递增到最大的位数
            for(int j=0;j<array.length;j++){
                int ys = array[j]/n%10; //求出需要比较的数 第一轮为数组每个值的 个位, 第二轮为 十位,第三轮为 百位 ......
                temp[ys][tempCounts[ys]] = array[j];//将array[j] 放入 他对应的桶中
                tempCounts[ys]++;//该桶存储了几个数据 + 1
            }

            //全部放入桶中后, 再依次从桶中取出来重新赋值给array, 来完成一轮排序
            int index = 0;
            for(int k=0;k<tempCounts.length;k++){//遍历每个桶
                if(tempCounts[k] != 0){//该桶中有数据才会 取出来, 如没有数据则不操作
                    for(int x=0;x<tempCounts[k];x++){
                        array[index++] = temp[k][x];
                    }
                    tempCounts[k] = 0;//取完后将该桶清空
                }
            }
        }

    }

    //基数排序
    public static void radixSort6(int[] array){
        int max = Integer.MIN_VALUE;//先假定最大值
        for(int i=0;i<array.length;i++){//循环找出数组中最大的值
            if(array[i] > max){
                max = array[i];
            }
        }

        int maxLength = Integer.toString(max).length();//找出最大值 是几位数
        int[][] temp = new int[10][array.length];//10个桶
        int[] count = new int[10];//分别表示10个桶中 每个桶存放了多少数据

        for(int i=0,n=1;i<maxLength;i++,n*=10){
            for(int j=0;j<array.length;j++){
                int ys = array[j]/n%10; //i=1时 求个位数 i=2时 求十位数, 依次递增
                temp[ys][count[ys]++] = array[j];
            }

            //全部的值放入桶中后, 从桶中取出来, 重新赋给array
            int index = 0;//array[]的下标
            for(int k=0;k<count.length;k++){//遍历每个桶
                if(count[k] != 0){//该桶中有数据才会 取出来, 如没有数据则不操作
                    for(int m=0;m<count[k];m++){//遍历每个桶中的数据
                        array[index++] = temp[k][m];
                    }
                    count[k] = 0;//取出该桶中的数据后, 清空该桶的数据
                }
            }
        }

    }

    //基数排序
    public static void radixSort7(int[] array){
        int max = Integer.MIN_VALUE;//假定最大值
        for(int i=0;i<array.length;i++){//找出真正的最大值
            if(array[i] > max){
                max = array[i];
            }
        }

        int maxLength = Integer.toString(max).length();//找出最大值是多少位数, 是几位数则进行几轮遍历
        int[][] temp = new int[10][array.length];//10个桶每个桶可以装 array.length个数据
        int[] tempCount = new int[10];//分别对应10个桶, 分别存储10个桶每个桶存放了多少个数据

        for(int i=0,n=1;i<maxLength;i++,n=n*10){
            for(int j=0;j<array.length;j++){
                int ys = array[j]/n%10; //i=1时 求个位数 i=2时 求十位数, 依次递增
                temp[ys][tempCount[ys]++] = array[j];
            }

            int index = 0;//array的下标
            //从桶中将数据全部取出来, 重新赋值给array
            for(int k=0;k<tempCount.length;k++){
                if(tempCount[k] != 0){
                    for(int m=0;m<tempCount[k];m++){
                        array[index++] = temp[k][m];
                    }
                    tempCount[k] = 0;
                }
            }

        }
    }

    //基数排序
    public static void radixSort8(int[] array){

        int max = Integer.MIN_VALUE;
        for(int i=0; i < array.length; i++){
            if(array[i] > max){
                max = array[i];
            }
        }

        int maxLength = Integer.toString(max).length();
        int[][] temp = new int[10][array.length];//10个桶
        int[] tempCount = new int[10];//分别代表10个桶 每个桶都存放了几个数据

        for(int i=0,n=1; i<maxLength; i++,n*=10){

            for(int j=0; j<array.length; j++){

                int ys = array[j]/n%10; //i=1时 求个位数 i=2时 求十位数, 依次递增
                temp[ys][tempCount[ys]] = array[j];//根据ys是几就将array[j]放入 第几个桶中
                tempCount[ys]++;

            }

            //上面的for执行完毕后就将array全部放入桶中了

            int index = 0;//array的下标
            for(int k=0; k<tempCount.length;k++){//遍历10个桶
                if(tempCount[k] != 0){//该桶中存放了至少一个数据就取出来
                    for(int m=0; m<tempCount[k]; m++){
                        array[index++] = temp[k][m];
                    }
                    tempCount[k] = 0;//取出该桶的数据后, 将表示该桶有几个数据的tempCount[k]至为空
                }
            }

        }

    }

    /*==========================================================================================================*/


    /**
     * 堆排序
     * @param array
     */
    public static void heapSort(int[] array){
        int start = (array.length-1) - 1 / 2; //根据 公式 2n-1 / 2 找到的就是最后一个子节点

        //第一次从 数组 转为 大顶堆 是需要从 最后一个叶子节点开始 往回 依次 转换的
        //如果从根节点开始, 假如根节点 本身就 大于左右子节点, 左右子节点又不大于他们自己的左右子节点.....  全剧终......
        for(int i=start;i>=0;i--){
            maxHeap(array,array.length,i);
        }

        //经过上面这个for循环后, 此时 array 已经成为一个 大顶堆
        //从数组最后一位开始, 往前, 每次将 根节点[0](最大值) 与  [i] 交换值, 将根节点放到最后[i]
        //再次排大顶堆时 长度就是 i, 也就是说 下次排大顶堆时, i就相当于array.length, 不会把 i 也算进去排大顶堆
        //最多 到 i-1;的位置, 这已经是最后一个节点了, 那么根节点[0](最大值) 放入(交换) i后, 就不会动了     (将[0]放入[i] 将[i]放入[0])
        //又排完一次大顶堆后(循环完一次) i--, 然后再次将 新的根据[0](最大值) 放入(交换) i 此时就已经循环两次了, 也将两个最大值 放入了数组最后
        //循环到数组还剩两个元素时 将 跟结点[0] 放入(交换) i(i=1) 就结束了, 再下次i就不大于0了,
        //10个数据的数组 将 9个最大数 放入了 最后, 那么剩余的 1个,就在[0]就好了, 不用排了
        for(int i=array.length-1;i>0;i--){
            //将 根节点(最大值) 放入(交换)  [i](最后)
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            //交换完后, 根节点就改变了, 需要再次排出 新的正确的大顶堆
            maxHeap(array,i,0);
            //i表示 排序的长度 之前已经将 根节点(最大值) 放入 [i] 了, 下次重新排出大顶堆时, 就不要将 i 也算入其中了, 因为 i 就想 array.length一样
            //最多到 array.length - 1; i 也一样, 传入的是 i , 实际上最后一个下标是 i-1
            //
            //0是什么? 之前不是说 要从最后一个非叶子节点开始排吗?
            //只有第一次 需要从 最后一个非叶子节点开始, 因为最开始 的数组是完全不符合 大顶堆的, 所以要从最后一个节点开始,往回将每个叶子节点都遍历一遍
            //但是这里 从 0 开始是因为, 之前已经将一个原始数组排成 大顶堆了, 只是将大顶堆的根节点 改变了(根节点放入数组最后了,最后的值放入根节点了)
            //原本就是 大顶堆 只是改变了 根节点 所以只需对 根节点 进行一轮排为大顶堆  根节点与其左右子节点比较出真正的根节点即可,
            //与根节点交换了值的节点(左右子节点其中一个与根节点交换值了的节点) 由于值已经改变了, 会在maxHeap方法中递归调用 继续与其左右子节点比较
            //直到 重新成为一个 大顶堆, 之后进行下一次循环,让根节点与最后一个节点交换值, 然后再重新排序大顶堆.....
        }
    }

    /**
     * 堆排序 之 将 顺序存储的二叉树 转为 大顶堆
     * @param array     数组 顺序储存的二叉树
     * @param size      需要转换为大顶堆的长度, 因为并不是每次都将 数组元素全部转为 大顶堆
     * @param index     从哪个数开始转为大顶堆
     */
    public static void maxHeap(int[] array,int size,int index){

        //通过公式 获取 index 的左右子节点
        int leftNode = 2 * index + 1;
        int rightNode = 2 * index + 2;

        int max = index; //先假定 最大值是父节点

        //leftNode < size 是为了防止数组越界(因为叶子节点没有左右子节点但是却可以计算出左右子节点的下标, 那么此时这个下标显然越界了)
        //为什么不小于array.length?  因为 并不是每次 都将 数组元素全部转为大顶堆, 如leftNode < array.length 但是 不小于 size那么也是判断越界的
        //因为 虽然你在 array.length 以内, 但是我只需要排序 0 ~ size(就像0~array.length一样) 所以超过了 size 也算越界
        if(leftNode < size && array[leftNode] > array[max]){
            max = leftNode;
        }
        if(rightNode < size && array[rightNode] > array[max]){
            max = rightNode;
        }

        //经过上面两个if后, max此时以指向了 index 与 index左右子节点 中最大的节点
        if(index != max){//如果index != max 则说明 index不是最大的值, max已经指向了最大的节点,则将他们的值交换  注意: 是交换值,并不是交换节点
            int temp = array[index];
            array[index] = array[max];
            array[max] = temp;

            //此时 经过交换节点值后, index 已经成功最大的节点了, max节点就是之前的index的值, 那么max的值显然是改变了, 而且是变小了.
            //max原本是 index的左右子节点中的一个, max现在值变小了,  那么 max 的值可能 不大于 max自己的左右子节点了(虽然之前大于,但是max
            //的值现在改变了,而且是变小了,所以有可能不大于了, 所以需要给 max节点 与其 max节点的左右子节点比较, 则递归调用该方法)
            maxHeap(array,size,max);
        }

        //这个方法 执行完毕后 只是将 传入的index节点 与其 左右子节点  按照大顶堆的特性  父节点大于左右子节点 排好了自index节点为父节点的 左右子树
        //注意: 第一次需要从 最后一个叶子节点开始 往回 依次转为大顶堆, 最后一个叶子节点,倒数第二个叶子节点, 一直往前到根节点
        //      这样经过N轮转为 大顶堆后  一个数组 才真正被转为了 大顶堆
        //      要不然其实只是 将 传入的 index 与 左右子节点 这3个节点的子树 转为了 一个大顶堆

    }


    //堆排序
    public static void heapSort2(int[] array){
        int start = ((array.length-1)-1) / 2; //得到最后一个子节点的值  公式 (n-1)/2   n代表最后一个节点
        for(int i=start;i>=0;i--){
            maxHeap2(array,array.length,i);
        }

        for(int i=array.length-1;i>0;i--){
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;

            maxHeap2(array,i,0);
        }
    }

    //堆排序 之 将 顺序存储的二叉树 转为 大顶堆
    public static void maxHeap2(int[] array,int size,int index){
        int leftNode = 2 * index + 1;//左子节点
        int rightNode = 2 * index + 2;//右子节点

        int max = index;

        if(leftNode < size && array[max] < array[leftNode]){
            max = leftNode;
        }
        if(rightNode < size && array[max] < array[rightNode]){
            max = rightNode;
        }

        if(max != index){
            int temp = array[index];
            array[index] = array[max];
            array[max] = temp;

            maxHeap2(array,size,max);
        }

    }

}
