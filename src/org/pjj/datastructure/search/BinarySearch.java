package org.pjj.datastructure.search;

/**
 * 二分查找
 * @author PengJiaJun
 * @Date 2020/8/10 15:48
 */
public class BinarySearch {
    public static void main(String[] args) {
        int array[] = {-1,0,1,8,10,89,1000,1000,1000,1000,1234,1314};
//        int index = binarySearch(array, 1314); //使用 循环迭代的方式
        int index = binarySearch(array,0,array.length-1,1000);//使用递归的方式
        if(index != -1){
            System.out.println("找到了,下标为: "+index);
        }else{
            System.out.println("没找到...");
        }

        int[] ints = binarySearch2(array, 1000);
        if(ints.length > 0){
            System.out.print("\n找到了,下标为: ");
            for(int temp : ints){
                if(temp != -1){
                    System.out.print(temp+"  ");
                }
            }
            System.out.println();
        }else{
            System.out.println("\n没有找到");
        }

        System.out.println();
        binarySearchTest(array,1314);

    }

    /**
     * 二分查找 使用 迭代(循环)
     * 注意:  使用二分查找的前提是 该数组是有序的
     * @param array 在这个数组中查找
     * @param findValue 需要查找的数字
     * @return
     */
    public static int binarySearch(int[] array,int findValue){
        int low = 0;
        int high = array.length-1;
        int mid;

        //注意是 小于等于 我去,找BUG一小时.....   如果是low<high 问题就是: 如果需要查找的数是数组中最大的数(最后一个数),则找不到,最多只能找到倒数第二个数
        //因为 如果只是 low<high的话 虽然还有两个数也会进行一轮比较,但是mid是指向的 "两个数中的第一个数" 如果查找的数是 "两个数中的第二个数(最后一个数)"
        //那么 实际还需要一轮比较 , 但是low<high的话,就不会进行比较了, 因为最后一轮 low,high,mid都指向最后一个数了,low!<high了,也就不会进行循环了
        //如果是 low<=high的话,就是会进行这最后一次循环的.
        while(low <= high){
            mid = (low+high)/2;
            if(findValue == array[mid]){
                return mid;
            }else{
                if(findValue > array[mid]){
                    low = mid+1;
                }else{//findValue <= array[mid]
                    high = mid-1;
                }
            }
        }
        return -1;
    }


    /**
     * 二分查找 使用 递归
     * @param array 在这个数组中查找
     * @param left 查找的范围 左边
     * @param right 查找的范围 右边
     * @param findValue 需要查找的数字
     * @return
     */
    public static int binarySearch(int[] array,int left,int right,int findValue){

        if(left > right){//left>right说明已经递归完整个数组,且没有找到对应的值,则退出递归     意思就是left <= right就可以继续递归
            return -1;
        }

        int mid = (left+right)/2;
        int midVal = array[mid];

        if(findValue > midVal){//向右递归
            //一直递归下去,直到某一层返回那个else中的一个具体,然后就依次返回上一层
            //但是我发现一个问题, 如果 需要查找的数,在array中没有找到, 就会一直递归下去, 然后StackOverflowError
            return binarySearch(array,mid+1,right,findValue);
        }else if(findValue < midVal){//向左递归
            return binarySearch(array,left,mid-1,findValue);
        }else{
            return mid;
        }

    }

    /**
     * 二分查找课后习题
     * 当一个有序列表中,有多个相同的数值时,如何将所有的数值都查询到
     * @param array
     * @param findValue
     * @return
     */
    public static int[] binarySearch2(int[] array,int findValue){
        int low = 0;
        int high = array.length-1;
        int mid;
        int[] result = new int[array.length];
        for(int i=0;i<result.length;i++){//初始化返回值数组默认值为-1
            result[i] = -1;
        }
        int resultIndex = 0;

        while(low <= high){
            mid = (low+high)/2;
            if(findValue == array[mid]){
                int midLeft = mid-1;
                while(midLeft >= 0 && array[midLeft] == findValue){//注意要加 midLeft >= 0;防止数组越界
                    result[resultIndex++] = midLeft;
                    midLeft--;
                }

                //既然二分查找必要要求数组是有序的,那么相同的数据肯定是在一起的,所以当我们找到了,目标值后,左右延伸一下,看有没有相同的值
                result[resultIndex++] = mid;

                int midRight = mid+1;
                while(midRight <= array.length-1 && array[midRight] == findValue){//注意要加 midRight >= array.length-1;防止数组越界
                    result[resultIndex++] = midRight;
                    midRight++;
                }
                break;
            }else{
                if(findValue > array[mid]){
                    low = mid+1;
                }else{//findValue <= array[mid]
                    high = mid-1;
                }
            }
        }
        return result;
    }

    public static void binarySearchTest(int[] array,int value){
        int left = 0;
        int right = array.length-1;
        int mid;

        while(left <= right){
            mid = (left+right)/2;
            if(value == array[mid]){
                System.out.println("需要查找的值"+value+",已经找到了,下标为: "+mid);
                return;
            }else{
                if(value > array[mid]){
                    left = mid+1;
                }else{
                    right = mid-1;
                }
            }
        }
        System.out.println("没有找到");
    }
    
}
