package org.pjj.datastructure.sort;

import java.util.Arrays;

/**
 * 基数排序(桶排序的扩展)
 * 注意:
 * 基数排序貌似不能处理负数,也不能处理小数
 * <p>
 * 800万数据 只要 439毫秒 比希尔排序,快速排序,归并排序, 都快多了..
 * 8000万数据 直接报 java.lang.OutOfMemoryError: Java heap space 内存溢出 堆空间
 * 80000000 * 11 * 4 / 1024 / 1024 / 1024
 * 8千万个数据  需要11个 存储8千万数据的数组  所以是 8千万 * 11
 * 这些数组存储的是int型的数据 一个int占4个字节  所以是 8千万 * 11 * 4
 * 1024个字节是 1K   1024K是 1M    1024M是 1G  所以 8千万 * 11 * 4需要除 3个1024
 * 80000000 * 11 * 4 / 1024 / 1024 / 1024 = 3.278G
 * 需要 3点几G的内存 怪不得内存溢出....
 * 算法果然是空间换时间, 时间换空间, 基数排序使用空间换时间
 *
 * @author PengJiaJun
 * @Date 2020/8/8 16:46
 */
public class RadixSort {

    public static void main(String[] args) {
        int[] array = {23, 6, 189, 45, 9, 287, 56, 1, 798, 34, 65, 652, 5, 9};
        System.out.println("基数排序前: " + Arrays.toString(array));
//        radixSort(array);     //数组实现的 基数排序
        radixQueueSort(array);  //数组实现的 基数排序
        System.out.println("基数排序后: " + Arrays.toString(array));

        //测试一下基数排序的速度  , 给800万个数据, 测试一下
        //创建一个800万个数据的数组, 并加入随机数
        int[] test = new int[8000000];
        for (int i = 0; i < 8000000; i++) {
            test[i] = (int) (Math.random() * 8000000); //生成一个[0, 8000000)的随机数
        }

        long start = System.currentTimeMillis();//基数排序开始

//        radixSort(test); //数组实现的 基数排序                         //800万数据, 速度差不多
        radixQueueSort(test); //队列(数组实现的队列)实现的 基数排序       //800万数据, 速度差不多

        long end = System.currentTimeMillis();//基数排序结束

        System.out.println("\n8000000个数据, 基数排序使用了" + (end - start) + "毫秒");//800万个数据, 基数排序使用了439毫秒

    }

    /**
     * 基数排序 数组实现
     *
     * @param array
     */
    public static void radixSort(int[] array) {
        int max = Integer.MIN_VALUE;//Integer.MIN_VALUE = -2147483648 最小的整数
        for (int i = 0; i < array.length; i++) {//找出数组中的最大数
            if (array[i] > max) {
                max = array[i];
            }
        }
        //计算最大的数字是几位数的
        int maxLength = Integer.toString(max).length();//其实可以直接 (max+"").length()但是 使用max+""方式将 int转String 效率不高

        //用于临时存储数据的数组(10个桶)
        int[][] temp = new int[10][array.length];//一共有10个桶(10个一维数组), 每个桶长度为 待排序的数组的长度(一维数组的长度) 因为有可能 数组中的数都需要放在一个桶里面.
        //用于记录temp中相应的数组中存放数字的数量(记录每个桶放了几个元素)
        int[] counts = new int[10];


        //根据最大数 的位数 决定比较的次数(最大数 是几位 就循环几次)
        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            //遍历数组,使其按位数值(第一次循环 按个位数的值,第二次按十位数的值, 依次递增) 进入相应的桶
            for (int j = 0; j < array.length; j++) {
                //计算余数,第一次循环 求出该数个位数的值, 第二次求出该数十位数的值, 依次递增
                int ys = array[j] / n % 10;//比如 第一轮循环 求个位数 n=1; 12/1%10=2; 第二轮循环求十位数n=10 12/10%10=1; 第三轮 n=100  223/100%10=2;

                temp[ys][counts[ys]] = array[j];//余数是几就放在第几个桶里面[ys], 放在第temp[ys]桶中的第几个呢? 就顺序的放在该temp[ys]桶中,[counts[ys]]

                counts[ys]++;

            }

            int index = 0;//记录取元素时将元素放回array数组时 放在array哪里的一个下标
            //把存进temp中的数取出来 重新赋给 array
            for (int k = 0; k < counts.length; k++) {//遍历所有的桶

                //counts是记录temp中相应的数组中存放数字的数量(记录每个桶放了几个元素), 如某个桶中数字数量为0则不操作这个桶
                if (counts[k] != 0) {//只有某个桶中数字数量不为0才将该桶中的元素取出来

                    for (int m = 0; m < counts[k]; m++) {//遍历该桶中的每个元素, 并取出来
                        array[index++] = temp[k][m];
                    }
                    counts[k] = 0;//如 该桶中的数据已经取出来了, 则将该桶清空, 以便下一轮排序将数据再次放入

                }

            }

        }
    }


    /**
     * 基数排序  优化 使用队列实现   (其实使用队列,只是方便了,取出数据后不用清空数组,因为队列取出数据,就取出了,队列中就已经没有了
     * ,也不用专门用一个一维数组记录 每个桶有几个元素,因为队列自带了,该队列有几个元素)
     * 我感觉: 使用队列代替数组 来优化基数排序 感觉没什么用  如果队列是用数组实现的, 那么该使用多少内存还是使用多少内存, 和数组应该没有区别
     * 如 队列是用链表实现的 虽然用链表实现 占用内存是变小了,但是遍历链表是很需要时间的(没有数组快),当数据大了之后,虽然占用的内存小了,
     * 但是 速度变慢了, 这就是失去了基数排序的优势速度快, 这样还不如使用 其他排序
     *
     * @param array
     */
    public static void radixQueueSort(int[] array) {
        int max = Integer.MIN_VALUE;//Integer.MIN_VALUE = -2147483648 最小的整数
        for (int i = 0; i < array.length; i++) {//找出数组中的最大数
            if (array[i] > max) {
                max = array[i];
            }
        }
        //计算最大的数字是几位数的
        int maxLength = Integer.toString(max).length();//其实可以直接 (max+"").length()但是 使用max+""方式将 int转String 效率不高

        //用于临时存储数据的队列的数组(该数组内有10个队列,代表10个桶)
        ArrayQueue[] temp = new ArrayQueue[10];//一共有10个桶
        for(int l=0;l<temp.length;l++){//初始化10个队列(10个桶)
            temp[l] = new ArrayQueue(array.length+1); //每个队列(桶)长度为 待排序的数组的长度 因为有可能 数组中的数都需要放在一个桶里面.
        }

        //根据最大数 的位数 决定比较的次数(最大数 是几位 就循环几次)
        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
//            counts = new int[10];
            //遍历数组,使其按位数值(第一次循环 按个位数的值,第二次按十位数的值, 依次递增) 进入相应的桶
            for (int j = 0; j < array.length; j++) {
                //计算余数,第一次循环 求出该数个位数的值, 第二次求出该数十位数的值, 依次递增
                int ys = array[j] / n % 10;//比如 第一轮循环 求个位数 n=1; 12/1%10=2; 第二轮循环求十位数n=10 12/10%10=1; 第三轮 n=100  223/100%10=2;

                temp[ys].addQueue(array[j]);//余数是几就放在第几个桶里面[ys], 放在第temp[ys]桶中的第几个呢? 就顺序的放在该temp[ys]桶中,[counts[ys]]

            }


            int index = 0;//记录取元素时将元素放回array数组时 放在array哪里的一个下标
            //把存进temp中的数取出来 重新赋给 array
            for (int k = 0; k < temp.length; k++) {//遍历所有的桶

                //只有某个桶中数字数量不为空才将该桶中的元素取出来
                while (!temp[k].isEmpty()) {//遍历该桶中的每个元素, 并取出来
                    array[index++] = temp[k].getQueue();
                }

            }

        }
    }

}

//使用数组模拟队列-编写一个ArrayQueue类
class ArrayQueue {
    private int maxSize;//表示数组的最大容量
    private int front;//指向队列头
    private int rear;//指向队列尾
    private int[] arr;//该数据用于存放数据, 模拟队列

    //创建队列的构造器
    public ArrayQueue(int arrMaxSize) {
        this.maxSize = arrMaxSize;
        this.arr = new int[maxSize];//队列长度
        this.front = -1;//指向队列头, 分析出front是指向队列头的前一个位置.
        this.rear = -1;//指向队列尾, 指向队列尾的一个具体的数据(即就是队列的最后一个数据)
    }

    //判断队列是否满
    public boolean isFull() {
        //比如 我们这是数组实现的队列嘛 然后 输入的队列长度是10 那么 其实对应数组下标就是0~9. 那么10-1==9     rear=9 那么就是满了
        return rear == maxSize - 1;
    }

    //判断队列是否为空
    public boolean isEmpty() {
        //队列头 == 队列尾    这就说明队列为空, 因为但凡添加了一个数据, 队列尾都会往后移一位, 那么就 队列头 != 队列尾 了
        return front == rear;
    }

    //添加数据到队列
    public void addQueue(int n) {
        //判断队列是否满
        if (isFull()) {
            System.out.println("队列满, 不能加入数据~");
            return;
        }
        rear++; //让rear后移一位 , 然后将需要加入队列的值, 赋给rear, 因为rear就是, 指向队列尾的一个具体的数据(即就是队列的最后一个数据)
        arr[rear] = n;
    }

    //获取队列的数据, 出队列
    public int getQueue() {
        //判断队列是否为空
        if (isEmpty()) {
            throw new RuntimeException("队列为空, 不能取数据");
        }
        front++;//front后移   front指向队列头, 分析出front是指向队列头的前一个位置, front++后 就正好指向了第一个数据,然后直接返回使用就行了
        //第二次取值的时候 front虽然有值(指向之前的第一个数据), 但是相当于没有, 因为第二次取值的时候front还是会++ 那么取到的值其实还是第一个
        return arr[front];
    }

    //显示队列的所有数据
    public void showQueue() {
        //遍历
        if (isEmpty()) {
            System.out.println("队列为空, 没有数据~~");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    //显示队列的头数据, 注意不是取出数据
    public int headQueue() {
        //判断
        if (isEmpty()) {
            throw new RuntimeException("队列为空, 没有数据~~");
        }
        return arr[front + 1];//front是指向队列头的前一个位置, 这里需要+1 这样front值没有改变, 下次还是这个数据, 这里只是显示一个front,并没有取出
        //如果front不+1 那么实际显示的 如果之前一个值都没取过 那么会报 数组越界异常 因为front=-1 数组[-1] 显然是没有的
        //如果 之前取过值, 那么front不+1 实际显示的值就是上一次 的front的值, 实际当前front的值是需要front+1的 因为front是指向队列头的前一个位置
    }


}