package org.pjj.datastructure.queue;

import java.util.Scanner;

/**
 * 数组实现的环形队列
 * @author PengJiaJun
 * @Date 2020/7/17 21:51
 */
public class CircleArrayQueueDemo {
    public static void main(String[] args) {
        //测试一波,创建一个环形队列
        CircleArrayQueue queue = new CircleArrayQueue(5);//虽然设置的环形队列的大小为5, 但是实际上环形队列会空出一个空间, 所以实际大小为4
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

class CircleArrayQueue{
    private int maxSize;//表示数组的最大容量
    private int front;//指向队列头, front指向队列的第一个数据,是一个具体的数据(之前的单向队列,队列头是指向队列的第一个数据的前一个位置)
    private int rear;//指向队列尾, rear指向队列的最后一个元素的后一个位置,因为希望空出一个空间作为约定
    private int[] arr;//该数据用于存放数据, 模拟队列

    public CircleArrayQueue(int arrMaxSize){
        this.maxSize = arrMaxSize;
        arr = new int[maxSize];
        front = 0;//队列头, 初始化指向队列第一个数据,虽然还没有
        rear = 0;//队列尾
    }

    //判断队列是否满
    public boolean isFull(){
        //因为rear是指向最后一个元素的后一个格子的,所以如果rear+1==front就是满了(相当于rear绕了一圈到front的屁股后面了)
        //为什么要rear要+1,因为,rear是要留一个空位的,他永远指向空,如果加1==front,那就相当于满了,加不了数据了
        //如果rear 不+1, 那么岂不是队列一个数据没加的时候 rear = 0,front =0,rear == front队列就满了?
        //所以我们浪费一个空间作为约定,只要rear + 1 == front 那么队列就满了, rear + 1 == front 就相当rear没加一的时候已经到了front屁股后面了,那么队列就满了
        //%maxSize是因为maxSize-1是数组的最大下标了,rear本身是指向最后一个数据的后一格,
        //如果rear+1 == maxSize的话,rear+1%maxSize 就 == 0了,
        //比如 % n，小于n的数字，都没影响不是吗？那到n的时候，一取模，就又回到0的位置了，所以数组的第一位是0，其实还有这么一个好处。
        return (rear+1) % maxSize == front;
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
        //直接将数据加入
        arr[rear] = n;
        //将rear后移, 不能只是单纯的rear++,因为是环形队列,如果: front把数据都取了也就!=0了, 那么rear是可以转到数组下标为0的地方的
        //所以 需要%maxSize (数组最大下标是maxSize-1) 如果rear+1 == maxSize 那说明已经超过数组最大下标了,所以直接 % maxSize 使其,rear变为0,
        //从数组开头继续添加元素到队列.
        rear = (rear+1) % maxSize;//这里必须考虑取模
    }

    //获取队列的数据, 出队列
    public int getQueue(){
        //判断队列是否为空
        if(isEmpty()){
            throw new RuntimeException("队列为空, 不能取数据");
        }

        //这里需要分析, front是指向队列的第一个元素
        int value = arr[front];         //1. 先把 front 对应的值保存到一个临时变量
        front = (front + 1) % maxSize;    //2. 将 front 后移, 考虑取模
        return value;                   //3. 将临时保存的变量返回
    }

    //显示队列的所有数据
    public void showQueue(){
        //遍历
        if(isEmpty()){
            System.out.println("队列为空, 没有数据~~");
            return;
        }
        //思路: 从front开始遍历, 遍历多少个元素, 有多少个有效元素就遍历多少遍
        for(int i=front;i<front+size();i++){
            //%maxSize的原因是 front有可能已经是 一个大小为 int[4] 的数组 front可能已经 是 front = 3了, 下一个数据在front = 0处,所以需要%maxSize
            System.out.printf("arr[%d]=%d\n",i%maxSize,arr[i%maxSize]);
        }
    }

    //求出当前队列有效数据的个数
    public int size(){
        return (rear + maxSize - front) % maxSize;
    }

    //显示队列的头数据, 注意不是取出数据
    public int headQueue(){
        //判断
        if(isEmpty()){
            throw new RuntimeException("队列为空, 没有数据~~");
        }
        return arr[front];//front是指向队列头的一个具体的元素, 使用直接返回就行了
    }

}
