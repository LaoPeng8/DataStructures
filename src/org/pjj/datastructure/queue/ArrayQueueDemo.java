package org.pjj.datastructure.queue;

import java.util.Scanner;

/**
 * 队列是一个有序列表, 可以用数组或是链表来实现
 * 遵循先入先出的原则. 即: 先存入队列的数据, 先取出. 后存入的要后取出
 *
 * 数组实现的单向队列
 * @author PengJiaJun
 * @Date 2020/7/17 15:21
 */
public class ArrayQueueDemo {
    public static void main(String[] args) {
        //测试一波, 创建一个队列
        ArrayQueue queue = new ArrayQueue(3);
        char key = ' ';//接收用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        //输出一个菜单
        while(loop){
            System.out.println("s(show): 显示队列");
            System.out.println("e(exit): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0);//接收用户输入的一个字符串, charAt(0)取其中下标为0的字符 赋值给 key
            switch(key) {
                case 's':
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("请输入一个需要加入队列的整数: ");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                case 'g':
                    try{
                        int result = queue.getQueue();
                        System.out.println("取出的数据是: "+result);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 'h':
                    try{
                        int result = queue.headQueue();
                        System.out.println("队列头的数据是: "+result);
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出~~");
    }
}

//使用数组模拟队列-编写一个ArrayQueue类
class ArrayQueue{
    private int maxSize;//表示数组的最大容量
    private int front;//指向队列头
    private int rear;//指向队列尾
    private int[] arr;//该数据用于存放数据, 模拟队列

    //创建队列的构造器
    public ArrayQueue(int arrMaxSize){
        this.maxSize = arrMaxSize;
        this.arr = new int[maxSize];//队列长度
        this.front = -1;//指向队列头, 分析出front是指向队列头的前一个位置.
        this.rear = -1;//指向队列尾, 指向队列尾的一个具体的数据(即就是队列的最后一个数据)
    }

    //判断队列是否满
    public boolean isFull(){
        //比如 我们这是数组实现的队列嘛 然后 输入的队列长度是10 那么 其实对应数组下标就是0~9. 那么10-1==9     rear=9 那么就是满了
        return rear == maxSize - 1;
    }

    //判断队列是否为空
    public boolean isEmpty(){
        //队列头 == 队列尾    这就说明队列为空, 因为但凡添加了一个数据, 队列尾都会往后移一位, 那么就 队列头 != 队列尾 了
        return front == rear;
    }

    //添加数据到队列
    public void addQueue(int n){
        //判断队列是否满
        if(isFull()){
            System.out.println("队列满, 不能加入数据~");
            return;
        }
        rear++; //让rear后移一位 , 然后将需要加入队列的值, 赋给rear, 因为rear就是, 指向队列尾的一个具体的数据(即就是队列的最后一个数据)
        arr[rear] = n;
    }

    //获取队列的数据, 出队列
    public int getQueue(){
        //判断队列是否为空
        if(isEmpty()){
            throw new RuntimeException("队列为空, 不能取数据");
        }
        front++;//front后移   front指向队列头, 分析出front是指向队列头的前一个位置, front++后 就正好指向了第一个数据,然后直接返回使用就行了
        //第二次取值的时候 front虽然有值(指向之前的第一个数据), 但是相当于没有, 因为第二次取值的时候front还是会++ 那么取到的值其实还是第一个
        return arr[front];
    }

    //显示队列的所有数据
    public void showQueue(){
        //遍历
        if(isEmpty()){
            System.out.println("队列为空, 没有数据~~");
            return;
        }
        for(int i=0;i<arr.length;i++){
            System.out.printf("arr[%d]=%d\n",i,arr[i]);
        }
    }

    //显示队列的头数据, 注意不是取出数据
    public int headQueue(){
        //判断
        if(isEmpty()){
            throw new RuntimeException("队列为空, 没有数据~~");
        }
        return arr[front+1];//front是指向队列头的前一个位置, 这里需要+1 这样front值没有改变, 下次还是这个数据, 这里只是显示一个front,并没有取出
        //如果front不+1 那么实际显示的 如果之前一个值都没取过 那么会报 数组越界异常 因为front=-1 数组[-1] 显然是没有的
        //如果 之前取过值, 那么front不+1 实际显示的值就是上一次 的front的值, 实际当前front的值是需要front+1的 因为front是指向队列头的前一个位置
    }


}