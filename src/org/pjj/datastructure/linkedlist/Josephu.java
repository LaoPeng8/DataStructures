package org.pjj.datastructure.linkedlist;

/**
 * 约瑟夫(约瑟夫环,约瑟夫问题):
 * 设编号为1,2,....n的n个人围坐一圈,约定编号为k(1<=k<=n)的人从1开始报数,数到m的那个人出列,
 * 它的下一位又从1开始报数,数到m的那个人又出列,依此类推,直到所有人出列为止,由此产生一个出队编号的序列.
 * @author PengJiaJun
 * @Date 2020/7/21 15:19
 */
public class Josephu {

    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoys(5);
        circleSingleLinkedList.showBoy();

        //测试 小孩出圈是否正确
        System.out.println();
        circleSingleLinkedList.countBoy(1,2,5);
    }
}

//创建一个环形的单向链表
class CircleSingleLinkedList{
    //创建一个first节点, 当前没有编号
    private Boy first = null;

    //添加节点, 构成一个环形的链表
    public void addBoys(int nums){
        //nums 做一个数据校验
        if (nums < 1){
            System.out.println("nums的值不正确");
            return;
        }

        Boy curBoy = null;//辅助变量, 帮助构建环形链表, 每次添加新节点了都会后移, 相当于尾指针, 总是在链表最后
        //使用for来创建我们的环形链表
        for(int i=1 ; i<=nums ; i++){
            //根据编号创建节点
            Boy boy = new Boy(i);

            if(i == 1){//如果是第一个小孩, 这代表链表第一个节点的first = boy,且first.next = first自己的下一个还是自己,构成一个 只有一个节点的单向环形链表
                first = boy;//这代表链表第一个节点的first 指向 boy (等于说这个新节点就是链表的第一个节点)
                first.setNext(first);//新节点的 next 指向链表的第一个节点, 形成环形, 虽然这里只有一个节点 但是也是可以构成环形的
                curBoy = first; //让curBoy指向当前的最后一个节点,(当前只有一个节点 所以 first即是第一个,也是最后一个)
            }else{
                curBoy.setNext(boy);//让curBoy(链表当前的最后一个节点)的next 指向 新节点
                boy.setNext(first);//新节点的 next 指向链表的第一个节点, 形成环形
                curBoy = boy;//让curBoy指向当前的最后一个节点
            }
        }
    }

    //遍历当前的单向环形链表
    public void showBoy(){
        //判断链表是否为空
        if(first == null){
            System.out.println("链表为空,没有任何节点~");
            return;
        }

        //因为first不能动, 因此我们仍然使用一个辅助指针完成遍历
        Boy temp = first;
        while(true){
            System.out.printf("小孩的编号 %d \n",temp.getNo());
            if(temp.getNext() == first){
                //说明 单向环形链表 已经遍历到了最后一个节点
                break;
            }
            temp = temp.getNext();
        }

    }

    /**
     * 根据用户的输入, 计算出 小孩出圈的顺序
     * 思路: 1.需要先创建一个辅助变量(指针)helper, 事先应该指向环形链表的最后一个节点, 也就是这个最后一个节点 继续.next后就到了第一个节点first
     *
     * 2. 小孩报数前, 先要让first 和 helper 移动 startNo-1次, 因为这个first本来是指向第一个小孩的, 但是并不是每次都是从第一个小孩开始报数
     *    而是由startNo变量 来决定的, 所以 如果startNo=4 则是从第4个小孩还是报数, 而first指向第1个小孩,
     *    所以需要移动startNo - 1次, 也就是4-1= 3 移动3次 即可从 第一个节点 移动到 第四个节点 如果不-1就移动过了.
     *    而helper也需要移动startNo-1次是因为, helper本来就是一个辅助指针,方便我们将小孩(节点)出圈, helper的任务就是跟着first的屁股后面
     *    而helper本来就在第一步时定义在了first的后面, 所以只需每次移动first时,也同时移动helper, 这样helper就会一直在first的后面
     *
     * 3. 当小孩报数时, 让first 和 helper移动 countNum - 1 次, 然后first就指向要出圈的小孩, 因为小孩报数,比如报两下 countNum = 2
     *    报两下, first当前这个节点,也是一个小孩, 那么自己报1, 自己的下一个报2, 那么实际上first只用移动一次, 也是就countNum - 1
     *    而helper也需要移动startNo-1次是因为, helper本来就是一个辅助指针,方便我们将小孩(节点)出圈, helper的任务就是跟着first的屁股后面
     *    而helper本来就在第一步时定义在了first的后面, 所以只需每次移动first时,也同时移动helper, 这样helper就会一直在first的后面
     *
     * 4. 这时就可以将first指向的小孩出圈 first = first.next; first指向下一个小孩开始报数,(这时就体验出了之前定义helper的目的)
     *    helper.next = first; 将helper的下一个指向新的first, 这样之前的first就出圈了(因为helper.next之前指向的是之前还没有出圈的first)
     *    最后只有两个节点时first = first.next, 就只有一个节点了, 而helper.next = first, 其实也就都指向了最后一个节点
     *    之后 helper == first 也就循环结束 一个环形链表也都相继出圈了,只剩下一个节点了
     * @param startNo   表示从第几个小孩开始数数
     * @param countNum  表示数几下
     * @param nums      表示最初有多少个小孩在圈中
     */
    public void countBoy(int startNo,int countNum,int nums){
        //先对数据进行校验
        if(first == null || startNo<1 || startNo > nums){
            //如果first == null则说明, 单向循环列表为空, 既然为空,那还算个什么顺序,直接return
            //如果startNo<1 则说明, startNo表示从第几个小孩还是报数, 最少也是1吧, 不可能从-1开始报数吧,直接return
            //如果startNo > nums, startNo表示从第几个小孩还是报数, nums表示有多少个小孩, 一共就nums个, 你startNo还大于nums个 怎么数? 直接return
            System.out.println("参数输入有误, 请重新输入");
            return;
        }
        //创建一个辅助指针, 帮助完成小孩出圈
        Boy helper = first;
        while(true){
            if(helper.getNext() == first){//说明helper指向了链表中最后的小孩节点
                break;
            }
            helper = helper.getNext();
        }
        //2. 小孩报数前, 先要让first 和 helper 移动 startNo-1次, 因为这个first本来是指向第一个小孩的, 但是并不是每次都是从第一个小孩开始报数
        //而是由startNo变量 来决定的, 所以 如果startNo=4 则是从第4个小孩还是报数, 而first指向第1个小孩,所以需要移动startNo - 1次, 也就是4-1= 3 移动3次 即可从 第一个节点 移动到 第四个节点 如果不-1就移动过了.
        for(int j=0;j<startNo -1 ;j++){
            first = first.getNext();
            helper = helper.getNext();
        }//循环完之后, first就指向了 第一个开始报数的那个节点, 而helper也是跟着移动, helper.next就是first

        //3. 当小孩报数时, 让first 和 helper移动 countNum - 1 次, 然后first就指向要出圈的小孩
        //这里是个循环操作, 直到圈中只有一个节点
        while(true){
            if(helper == first){//说明圈中只有一个节点
                break;
            }
            //让first 和 helper移动 countNum - 1 次, 然后first就指向要出圈的小孩 然后在出圈
            //为什么countNum需要-1 因为比如 first指向第一个节点 现在需要出圈的节点是3 first是不是只需要移动两次就已经指向第3个节点了
            for(int j=0;j<countNum - 1;j++){
                first = first.getNext();
                helper = helper.getNext();
            }//循环完之后, first就指向了 要出圈的那个节点, 而helper也是跟着移动, helper.next就是first

            System.out.printf("小孩 %d 出圈\n",first.getNo());
            //打印出圈小孩信息后, 将first指向的小孩出圈,
            first = first.getNext();
            helper.setNext(first);//将helper的下一个指向新的first, 这样之前的first就出圈了(因为helper.next之前指向的是之前还没有出圈的first)
        }
        System.out.printf("最后留着圈中的小孩编号 %d \n",first.getNo());

    }


}

//创建一个Boy类, 表示一个节点
class Boy{
    private int no;//编号
    private Boy next;//指向下一个节点,默认为null
    
    public Boy(int no){
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}
