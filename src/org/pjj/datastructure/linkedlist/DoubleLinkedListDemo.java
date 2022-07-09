package org.pjj.datastructure.linkedlist;

/**
 * 双向链表
 * @author PengJiaJun
 * @Date 2020/7/21 8:32
 */
public class DoubleLinkedListDemo {

    public static void main(String[] args) {
        //测试双向链表

        //先创建节点
        HeroNode2 node1 = new HeroNode2(1,"宋江","及时雨");
        HeroNode2 node2 = new HeroNode2(2,"卢俊义","玉麒麟");
        HeroNode2 node3 = new HeroNode2(3,"吴用","智多星");
        HeroNode2 node4 = new HeroNode2(4,"林冲","豹子头");

        //创建一个双向链表对象
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        //加入链表
//        doubleLinkedList.add(node1);
//        doubleLinkedList.add(node3);
//        doubleLinkedList.add(node2);
//        doubleLinkedList.add(node4);

        //加入链表 按照编号的顺序
        doubleLinkedList.addByOrder(node1);
        doubleLinkedList.addByOrder(node3);
        doubleLinkedList.addByOrder(node2);
        doubleLinkedList.addByOrder(node4);

        doubleLinkedList.list();

        //修改测试
        HeroNode2 newHeroNode2 = new HeroNode2(4,"小林冲","小豹子头");
        doubleLinkedList.update(newHeroNode2);
        System.out.println("\n打印修改后的链表");
        doubleLinkedList.list();

        //测试删除
        doubleLinkedList.delete(1);
        doubleLinkedList.delete(2);
        doubleLinkedList.delete(3);
        doubleLinkedList.delete(4);
        System.out.println("\n打印删除后的链表");
        doubleLinkedList.list();

    }

}

//定义一个DoubleLinkedList类 管理我们的英雄(节点)
class DoubleLinkedList{//双向链表的类

    //先初始化一个头节点, 头节点不要动, 存放任何数据
    private HeroNode2 head = new HeroNode2(0,"","");

    public HeroNode2 getHead() {//返回头节点
        return head;
    }

    //遍历双向链表
    public void list(){
        //先判断链表是否为空
        if(head.next == null){
            System.out.println("链表为空");
            return;
        }
        //因为头节点不能动, 因此我们需要一个辅助变量来遍历
        HeroNode2 temp = head.next;//上面判断过了head.next不为空,这说明是head.next是有值的. 这样temp就直接等于第一个数据了
        while(temp != null){//temp不等于null 就一直遍历
            //打印节点信息.
            System.out.println(temp);
            temp = temp.next;//每打印一个节点, 将temp结点的下一个节点 赋值给temp, 此时的temp就是 之前temp的下一个节点了
        }
    }

    //添加一个新节点到链表的最后
    public void add(HeroNode2 heroNode){
        HeroNode2 temp = head;//因为head节点不能动, 因此我们需要一个辅助变量temp
        while(temp.next!=null){//遍历列表, 找到最后的节点
            temp = temp.next;//
        }
        //跳出循环时, temp就已经是最后一个节点了, 因为temp.next == null才会跳出循环, 既然temp.next == null那说明没有下一个节点了, 那么就是最后一个节点
        temp.next = heroNode;//然后将最后一个节点的next指向 新节点
        heroNode.pre = temp;//将新节点的pre指向 之前的最后一个节点(现在新节点才是最后一个节点了)
    }

    /**
     * 根据节点编号将节点插入到指定位置(如果已经有这个节点编号了, 则添加失败, 并给出提示)
     * @param newNode
     */
    public void addByOrder(HeroNode2 newNode){

        //我们找的temp是位于 添加位置的前一个节点
        HeroNode2 temp = head;
        boolean flag = false;//标识添加的编号是否存在, 默认为false
        while(true){
            if(temp.next == null){//说明链表为空,或temp已经在链表的最后
                break;
            }

            //如果第一次 temp下一个结点的no > 新结点的no 那么新结点不就应该在temp的下一个,的上一个嘛.
            if(temp.next.no > newNode.no){ //位置找到, 就在temp的后面插入
                break;
            } else if(temp.next.no == newNode.no){//说明希望添加的heroNode的编号已经存在,则不能添加
                flag = true;//说明编号存在
                break;
            }

            temp = temp.next;//后移
        }

        //根据flag的值来判断是否添加
        if(flag){//不能添加, 编号已经存在
            System.out.printf("准备插入的节点的编号 %d 已经存在了, 不能加入\n",newNode.no);
        }else{
            //判断 如果添加的新节点已经到了双向链表的最后端, 如果已经到了最后端则只需将 新节点 加在链表的最后端即可.
            if(temp.next==null){
                newNode.pre = temp;//新节点的 前一个节点 赋为 temp(双向链表的最后一个节点)
                temp.next = newNode;//temp的下一个 则是 新节点(此时新节点就是双向链表的最后一个节点)
            }else{
                //如果添加的新节点,不是在链表的最后端, 则就像, 插入一个新节点一样
                //注意顺序不能乱 至于为什么,说不上来, 自己画个图理解一下
                //因为顺序一乱 链表就断了, 就找不到原来的数据了, 试想一下, 直接temp.next = newNode这样确实把 新节点放在了 temp的后面,
                //但是temp就找不到原来的next节点了, 所以必须要先将 新节点newNode的next指向temp.next 这样链表才不会断, 然后再将temp.next = newNode
                //next是如此, pre反之亦然
                newNode.next = temp.next;
                newNode.pre = temp;

                temp.next.pre = newNode;
                temp.next = newNode;

            }
        }
    }

    //修改节点的信息, 根据编号来修改, 即no编号不能改
    public void update(HeroNode2 newHeroNode){//根据newHeroNode的no来修改
        //判断链表是否为空
        if(head.next==null){
            System.out.println("链表为空");
            return;
        }

        //因为头节点不能动, 因此我们需要一个辅助变量来遍历
        HeroNode2 temp = head.next;//上面判断过了head.next不为空,这说明是head.next是有值的. 这样temp就直接等于第一个数据了

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

    public void delete(int no){

        if(head.next == null){
            System.out.println("链表为空,无法删除");
            return;
        }

        HeroNode2 temp = head.next;//头节点不能动, 所以找个辅助变量
        boolean flag = false;//标识是否已经找到了 待删除的节点
        while(true){
            if(temp == null){
                break;//已经到了链表的最后了
            }

            if(temp.no == no){
                flag = true;//找到了待删除的节点
                break;
            }
            temp = temp.next;
        }
        if(flag){
            //如果已经找到了待删除的节点: 则
            //将待删除节点(temp)的前一个节点(temp.pre)的next属性 跳过待删除节点(temp) 直接指向待删除节点(temp)的next节点
            temp.pre.next = temp.next;

            //如果待删除节点temp是链表的最后一个元素, 则不需要执行这行, 否则会报空指针异常,因为temp已经是最后一个了, 最后一个.next显然是空,那空怎么.pre属性
            if(temp.next != null){
                //然后将待删除节点(temp)的后一个节点(temp.next)的pre属性 跳过待删除节点(temp) 直接指向待删除节点(temp)的pre节点
                temp.next.pre = temp.pre;
            }
        }else{
            System.out.printf("没有找到要删除的 %d 节点\n",no);
        }

    }


}

//定义一个HeroNode, 每个HeroNode对象就是一个节点
class HeroNode2{
    public int no;
    public String name;
    public String nickname;

    public HeroNode2 next; //指向下一个节点, 默认为null
    public HeroNode2 pre;//(previous) 指向上一个节点, 默认为null

    //构造器
    public HeroNode2(int no,String name,String nickname){
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {

        if(pre == null && next==null){
            return "HeroNode2{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", next=" + null +
                    ", pre=" + null +
                    '}';
        }
        if(pre == null){
            return "HeroNode2{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", next=" + next.name +
                    ", pre=" + null +
                    '}';
        }
        if(next == null){
            return "HeroNode2{" +
                    "no=" + no +
                    ", name='" + name + '\'' +
                    ", nickname='" + nickname + '\'' +
                    ", next=" + null +
                    ", pre=" + pre.name +
                    '}';
        }
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", next=" + next.name +
                ", pre=" + pre.name +
                '}';
    }
}
