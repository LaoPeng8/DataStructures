package org.pjj.datastructure.linkedlist;

import java.util.Stack;

/**
 * 单向链表
 * @author PengJiaJun
 * @Date 2020/7/18 12:55
 */
public class SingleLinkedListDemo {

    public static void main(String[] args) {
        //测试单向链表
        //先创建节点
        HeroNode node1 = new HeroNode(1,"宋江","及时雨");
        HeroNode node2 = new HeroNode(2,"卢俊义","玉麒麟");
        HeroNode node3 = new HeroNode(3,"吴用","智多星");
        HeroNode node4 = new HeroNode(4,"林冲","豹子头");

        //创建单向链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();

        //加入
//        singleLinkedList.add(node1);
//        singleLinkedList.add(node4);
//        singleLinkedList.add(node2);
//        singleLinkedList.add(node3);

        //加入 按照编号的顺序
        singleLinkedList.addByOrder(node1);
        singleLinkedList.addByOrder(node3);
        singleLinkedList.addByOrder(node2);
//        singleLinkedList.addByOrder(node2);
        singleLinkedList.addByOrder(node4);

        //显示链表全部
        System.out.println("修改前的链表");
        singleLinkedList.list();

        //测试修改节点的方法
        singleLinkedList.update(new HeroNode(2,"小卢","小麒麟"));//将2号节点修改为 当前创建的这个节点
        System.out.println("\n被singleLinkedList.update(new HeroNode(2,小卢,小麒麟));修改后的链表");
        singleLinkedList.list();

        //查到一个节点
        System.out.println("\n被singleLinkedList.get(1)(2)(3)(4);查找到的节点");
        singleLinkedList.get(1);
        singleLinkedList.get(2);
        singleLinkedList.get(3);
        singleLinkedList.get(4);

        //删除一个节点
//        singleLinkedList.delete(1);
//        singleLinkedList.delete(4);
//        singleLinkedList.delete(2);
//        singleLinkedList.delete(3);
//        System.out.println("\n被singleLinkedList.delete(1)(2)(3)(4);删除后的链表");
//        singleLinkedList.list();


        //测试一下, 获取到单链表的节点的个数(如果是带头节点的链表, 需要不统计头节点)
//        int length = singleLinkedList.getLength(singleLinkedList.getHead());
//        System.out.println("\n该链表有效的节点个数为: "+length);


        //测试一下, 查找单链表中的倒数第K个节点【新浪面试题】
//        HeroNode lastIndexNode = singleLinkedList.findLastIndexNode(singleLinkedList.getHead(), 4);
//        System.out.println("\nlastIndexNode="+lastIndexNode);


        //测试一下, 将单链表反转 【腾讯面试题】
//        singleLinkedList.reverseList(singleLinkedList.getHead());
//        System.out.println("\n反转后的链表为:");
//        singleLinkedList.list();


        //测试一下, 将单列表逆序打印, 不改变链表的的结构
//        System.out.println("\n将原链表,逆序打印");
//        singleLinkedList.reversePrint(singleLinkedList.getHead());


        //测试一下, 合并两个有序的单链表, 合并之后的链表依然有序
        System.out.println("\n合并两个有序的单链表, 合并之后的链表依然有序");
        HeroNode head1 = new HeroNode(0, "", "");
        HeroNode head1Node1 = new HeroNode(1, "张三", "张小三");
        HeroNode head1Node4 = new HeroNode(4,"赵六","赵小六");
        HeroNode head1Node9 = new HeroNode(9,"x9","x小9");
        head1.next = head1Node1;
        head1Node1.next = head1Node4;
        head1Node4.next = head1Node9;
        head1Node9.next = null;

        HeroNode head2 = new HeroNode(0, "", "");
        HeroNode head2Node1 = new HeroNode(2, "李四", "李小四");
        HeroNode head2Node4 = new HeroNode(3,"王五","王小五");
        HeroNode head1Node4x = new HeroNode(4,"赵六Plus","赵小六Plus");
        HeroNode head2Node8 = new HeroNode(8,"钱八","钱小八");
        head2.next = head2Node1;
        head2Node1.next = head2Node4;
        head2Node4.next = head1Node4x;
        head1Node4x.next = head2Node8;
        head2Node8.next = null;

        HeroNode head3 = singleLinkedList.mergeTwoLinkedList(head1, head2);
        //打印合并后的链表
        HeroNode temp = head3;
        while(temp != null){//temp不等于null 就一直遍历
            //打印节点信息.
            System.out.println(temp);
            temp = temp.next;//每打印一个节点, 将temp结点的下一个节点 赋值给temp, 此时的temp就是 之前temp的下一个节点了
        }


    }

}

//定义一个SingleLinkedList 管理我们的英雄(节点)
class SingleLinkedList{
    //初始化一个头节点, 头节点不存放具体的数据, 头节点不能动
    private HeroNode head = new HeroNode(0,"","");

    public HeroNode getHead() {
        return head;
    }

    //添加节点到到单向链表
    //思路: 当不考虑节点编号顺序时, 只需要找到当前链表的最后节点, 然后将最后节点的next指向新节点, 即可完成添加节点到单向链表
    public void add(HeroNode heroNode){
        HeroNode temp = head;//因为head节点不能动, 因此我们需要一个辅助变量temp
        while(temp.next!=null){//遍历列表, 找到最后的节点
            temp = temp.next;//
        }
        //跳出循环时, temp就已经是最后一个节点了, 因为temp.next == null才会跳出循环, 既然temp.next == null那说明没有下一个节点了, 那么就是最后一个节点
        temp.next = heroNode;//然后将最后一个节点的next指向 新节点
    }

    //第二种添加节点到单向链表的方式    要求: 根据节点编号将节点插入到指定位置(如果已经有这个节点编号了, 则添加失败, 并给出提示)
    public void addByOrder(HeroNode heroNode){
        //因为头节点不能动, 因此我们需要一个辅助变量temp, 来帮忙找到添加的位置
        //因为是单链表, 因此我们找的temp是位于 添加位置的前一个节点, 否则插入不了
        HeroNode temp = head;
        boolean flag = false;//标识添加的编号是否存在, 默认为false
        while(true){
            if(temp.next == null){//说明链表为空,或temp已经在链表的最后
                break;
            }

            //如果第一次 temp下一个结点的no > 新结点的no 那么新结点不就应该在temp的下一个,的上一个嘛.
            if(temp.next.no > heroNode.no){ //位置找到, 就在temp的后面插入
                break;
            } else if(temp.next.no == heroNode.no){//说明希望添加的heroNode的编号已经存在,则不能添加
                flag = true;//说明编号存在
                break;
            }

            temp = temp.next;//后移
        }

        //根据flag的值来判断是否添加
        if(flag){//不能添加, 编号已经存在
            System.out.printf("准备插入的节点的编号 %d 已经存在了, 不能加入\n",heroNode.no);
        }else{
            //插入到链表中, temp的后面
            heroNode.next = temp.next;  //先让heroNode.next 指向 temp.next 这样链表还是完整的
            temp.next = heroNode;       //再将temp.next 指向 heroNode,  这样才可以将heroNode正确的插入到temp 与 temp.next之前
        }

    }

    //修改节点的信息, 根据编号来修改, 即no编号不能改
    public void update(HeroNode newHeroNode){//根据newHeroNode的no来修改
        //判断链表是否为空
        if(head.next==null){
            System.out.println("链表为空");
            return;
        }

        //因为头节点不能动, 因此我们需要一个辅助变量来遍历
        HeroNode temp = head.next;//上面判断过了head.next不为空,这说明是head.next是有值的. 这样temp就直接等于第一个数据了

        //找到需要修改的节点
        boolean flag = false;//表示是否找到了需要修改的节点
        while(temp != null){
            if(temp.no == newHeroNode.no){//找到了
                flag = true;
                break;
            }
            temp = temp.next;
        }

        //根据flag判断是否找到了需要修改的节点, 如找到了flag为true, 如链表遍历完了还没有找到则为false
        if (flag){//找到了
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        }else {//没有找到
            System.out.printf("没有找到编号为 %d 的节点, 不能修改\n",newHeroNode.no);
        }

    }

    //删除节点
    //思路: 1.head不能动, 因此我们需要一个temp辅助节点找到待删除节点的前一个节点, 让待删除节点的前一个节点,跳过待删除节点, 直接指向待删除节点的下一个节点
    //2. 说明 我们在比较时, 是temp.next.no 和 需要删除节点的no 进行比较
    public void delete(int no){
        HeroNode temp = head;
        boolean flag = false; //标志是否找到待删除节点
        while(true){
            if(temp.next == null){//链表的最后,或者链表为空
                break;
            }
            if(temp.next.no == no){
                //找到了待删除节点的前一个节点 temp , 如果temp.next的no == no 则说明 temp.next就是需要删除的节点, 那么temp不就是待删除节点的前一个节点嘛
                //然后用待删除的节点的前一个节点 temp 跳过待删除节点temp.next 直接指向 temp.next的next 这样待删除的节点temp.next就从链表中断开了, 之后由于没有引用就会被Java的垃圾回收机制,回收
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //判断flag
        if(flag){//找到了
            temp.next = temp.next.next;
        }else{//没有找到
            System.out.printf("没有找到要删除的 %d 节点\n",no);
        }
    }

    //查找链表中的某个元素 根据 节点编号
    public void get(int no){

        HeroNode temp = head.next;
        boolean flag = false;//标志是否找到对应节点
        while(temp!=null){//遍历整个链表
            if(temp.no == no){
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if(flag){
            System.out.println("找到了 节点编号为"+no+"的节点: "+temp);
        }else{
            System.out.println("没有找到节点编号为"+no+"节点");
        }
    }

    //显示链表[遍历]
    public void list(){
        //先判断链表是否为空
        if(head.next == null){
            System.out.println("链表为空");
            return;
        }
        //因为头节点不能动, 因此我们需要一个辅助变量来遍历
        HeroNode temp = head.next;//上面判断过了head.next不为空,这说明是head.next是有值的. 这样temp就直接等于第一个数据了
        while(temp != null){//temp不等于null 就一直遍历
            //打印节点信息.
            System.out.println(temp);
            temp = temp.next;//每打印一个节点, 将temp结点的下一个节点 赋值给temp, 此时的temp就是 之前temp的下一个节点了
        }
    }


    /**
     * 获取到单链表的节点的个数(如果是带头节点的链表, 需要不统计头节点)
     * @param head 链表的头节点
     * @return 返回该链表的有效节点个数
     */
    public int getLength(HeroNode head){
        if(head.next == null){//空链表
            return 0;
        }

        int length = 0;
        //定义一个辅助的变量
        HeroNode cur = head.next;//cur 直接从 head.next开始, 没有计算head头节点
        while(cur!=null){
            length++;
            cur = cur.next;
        }
        return length;
    }


    /**
     * 查找单链表中的倒数第K个节点【新浪面试题】
     * 思路: 1. 编写一个方法, 接收head节点, 同时接收一个index(index表示的是倒数第index个节点)
     * 2. 先把链表从头到屋遍历, 得到链表的总的长度 getLength(), 得到链表的长度后, 我们从链表的第一个开始遍历 (length-index)个, 就可以得到倒数第index个节点了
     * @param head 头节点
     * @param index index表示的是倒数第index个节点
     * @return 找到了倒数第index个节点就返回该节点, 没有找到就返回null
     */
    public HeroNode findLastIndexNode(HeroNode head,int index){
        //判断如果链表为空,返回null
        if(head.next == null){
            return null;
        }

        //第一次遍历,得到链表的长度(节点个数)
        int length = getLength(head);

        //第二次遍历 length-index 位置, 就是我们的倒数的第K个节点
        //校验index
        if(index <= 0 || index > length){
            //数据不规范 index是倒数第几个节点, 没有倒数第0个节点, 最低也是倒数第1个节点.
            //也不能超过链表的长度, 链表一共就五个数据, 你可以找倒数第5个数据, 但是不能找倒数第6个节点
            return null;
        }
        //定义一个辅助变量
        HeroNode current = head.next;
        for(int i = 0;i<length-index;i++){
            //比如链表长度为 5 ; 要找倒数第2个节点; i=0;i<5-2;i++    0~3 循环遍历了 3次
            //由于current本来就指向第一个节点, 循环3次后移之后 就变成了 1->2  2->3 3->4   那么就找到了, 4这个节点, 长度为5的链表,4不就是倒数第二个嘛
            current = current.next;
        }
        return current;
    }

    /**
     * 将单链表反转 【腾讯面试题】
     * 思路: 1.先定义一个节点reverseHead = new HeroNode()
     * 2. 从头到尾遍历原来的链表, 每遍历一个节点, 就将其取出, 并放在新链表的reverseHead的最前端
     * 3. 原来的链表的head.next = reverseHead.next
     * @param head 需要反转的单链表的头节点
     */
    public void reverseList(HeroNode head){
        //如果当前链表为空, 或者, 只有一个节点, 则不做任何操作
        if(head.next == null || head.next.next == null){
            return;
        }

        //定义一个赋值的变量(指针), 帮助我们遍历原来的链表
        HeroNode current = head.next;
        HeroNode next = null;//指向当前节点(current)的下一个节点
        HeroNode reverseHead = new HeroNode(0,"","");
        //遍历原来的链表, 每遍历一个节点, 就将其取出, 并放在新的链表reverseHead的最前端
        while(current != null){
            //先暂时保存原链表当前结点的下一个节点, 因为当前结点的下一个节点,等下要指向反转后的链表的 "第一个节点", 如果不保存,原链表的数据就丢失了
            next = current.next;

            //将原链表的节点的下一个节点,指向 反转后的链表的第一个节点,这样就将原链表的一个节点指向了反转后的链表的第一个节点
            //而反转后的链表的第一个节点就指向了, 刚才原链表插入过来的节点的后一个节点,就相当于原链表一直循环往反转后的链表插入节点
            //每次都插入在第一个节点处, 而原本的第一个节点则指向刚才插入节点的后一个结点(原本第一个节点就变为了第二个节点)
            current.next = reverseHead.next;
            reverseHead.next = current;//反转后的链表的节点数据都跟着了current节点的后面,此时将reverseHead.next指向current,这样反转后的链表就连起来了.

            //之前原链表的一个节点插入到反转后的链表的第一个节点处了. 此处就是将current 变为 之前保存的原链表的current的下一个节点.
            //从而继续遍历原链表
            current = next;
        }
        //将head.next 指向 reverseHead.next , 这样就将原来链表的头节点的next指向 反转后的单链表, 相当于head链表就反转了
        head.next = reverseHead.next;
    }

    /**
     * 从尾到头打印单链表(这里使用的是方式二,使用栈实现单链表逆序打印)
     * 思路: 有两种方式,1 是可以先将单列表反转然后再打印单列表, 但是这样会破坏原有单列表的结构, 所以不推荐
     * 2 使用栈, 遍历单列表, 将单列表的各个节点压入到栈中, 然后利用栈先进后出的特点, 就实现了逆序打印的效果.
     * @param head 需要逆序打印的单链表的头节点
     */
    public void reversePrint(HeroNode head){
        if(head.next == null){
            return;//空链表,不用打印
        }

        //创建一个栈, 将各个节点压入栈中
        Stack<HeroNode> stack = new Stack<>();
        HeroNode temp = head.next;
        while(temp!=null){//遍历链表, 并将数据依次压入栈中
            stack.add(temp);//存入栈中
            temp = temp.next;//后移
        }

        //将栈中的节点进行打印
        while(stack.size() > 0){
            //栈的特点是 先进后出, 也就是说之前我们将链表的第一个节点存入栈中,实际上出栈时是最后一个出栈
            System.out.println(stack.pop());
        }
    }

    /**
     * 合并两个有序的单链表, 合并之后的链表依然有序
     * 思路: 遍历head2链表, 然后遍历时head1链表的元素比较, 如果head2链表的元素第一次大于head1链表的某个节点,
     *      则将head2链表的元素插入到head1链表的某个节点后面,如此循环, 等head2链表的节点全部插入到head1链表中, 那么就合并完成了
     *
     * 唉, 看上去很简单, 我Debug了两三个小时, 终于弄好了. 心累啊... 2020/7/20 20:57
     * @param head1 需要合并的两个单列表之一
     * @param head2 需要合并的两个单列表之一
     * @return 返回合并后的单链表
     */
    public HeroNode mergeTwoLinkedList(HeroNode head1,HeroNode head2){

        HeroNode head1Current = head1.next;
        HeroNode head2Current = head2.next;

        boolean isNext = false;//head2链表是否已经指向下一个节点了
        while(head2Current != null){//遍历head2链表, 使其中的每个节点都插入到head1链表中

            while(true){
                if(head1Current.next == null){//说明已经到了链表链表的最后一个元素了,还是没有找到新节点可以插入到原链表的哪个地方
                    head1Current.next = head2Current;//那么就直接插入到最后吧
                    break;
                }
                //如果 原链表的"某个节点"的下一个节点第一次大于, 需要插入的节点
                //那么说明这个需要加入的节点就应该在 "某个节点"的下一个节点 的 前面, 也就是 "某个节点"的后面
                if (head1Current.next.no >= head2Current.no){
                    //运行发现死循环了, 通过Debug发现, 是因为直接head2Current.next = head1Current.next;导致head2链表直接断开, 导致一直死循环
                    HeroNode next = head2Current.next;//此处先保存head2链表当前节点的下一个值

                    head2Current.next = head1Current.next;
                    head1Current.next = head2Current;

                    head2Current = next;//将之前保存的head2链表的当前节点的下一个值 重新赋值给 代表head2链表当前节点的变量
                    isNext = true;//发现如果此处已经将 head2Current 赋值为了 headCurrent的next那么外层while就不需要再次head2Current = head2Current.next;
                    break;
                }
                head1Current = head1Current.next;
            }

            //判断 head2Current 在内层while中 是否已经指向了 head2Current.next
            //如果已经指向了,则外层while中就不用再次head2Current = head2Current.next; 只需重置判断head2Current是否已经指向了head2Current.next的变量为false
            //如果没有指向 head2Current.next;   那么则需要在外层循环中 head2Current = head2Current.next;
            if (isNext){
                //将isNext重置为false, 以免下次在内层while中, 并没有将 head2Current 赋值为 headCurrent的next,
                //然后到了外层while中也没有将head2Current = head2Current.next; 然后就死循环了
                isNext = false;
            }else{
                head2Current = head2Current.next;
            }
        }
        return head1;
    }



}

//定义一个HeroNode, 每个HeroNode对象就是一个节点
class HeroNode{
    public int no;
    public String name;
    public String nickname;
    public HeroNode next; //指向下一个节点

    //构造器
    public HeroNode(int no,String name,String nickname){
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    //为了显示方便,重写了toString()
    @Override
    public String toString() {

        if(next==null){
            return "HeroNode{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", next=" + "null" +
                    '}';
        }else{
            return "HeroNode{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", next=" + next.name +
                    '}';
        }
    }
}