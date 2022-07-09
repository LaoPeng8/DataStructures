package org.pjj.datastructure.hashtab;

import java.util.Scanner;

/**
 * 哈希表
 * @author PengJiaJun
 * @Date 2020/8/12 10:34
 */
public class HashTabDemo {

    public static void main(String[] args) {
        //创建啊哈希表
        HashTab hashTab = new HashTab(7);

        //写一个简单的菜单
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("add: 添加雇员");
            System.out.println("delete: 删除雇员");
            System.out.println("update: 修改员工");
            System.out.println("find: 查找雇员");
            System.out.println("list: 显示雇员");
            System.out.println("exit: 退出系统");

            key = scanner.next();
            switch (key){
                case "add" :
                    System.out.println("输入id: ");
                    int id = scanner.nextInt();
                    System.out.println("输入名字: ");
                    String name = scanner.next();
                    System.out.println("输入年龄: ");
                    int age = scanner.nextInt();
                    //创建雇员
                    Emp emp = new Emp(id,name,age);
                    hashTab.add(emp);
                    break;
                case "delete":
                    System.out.println("输入需要删除的雇员id");
                    id = scanner.nextInt();
                    hashTab.deleteEmpById(id);
                    break;
                case "update":
                    System.out.println("输入需要修改的雇员id");
                    id = scanner.nextInt();
                    System.out.println("输入需要修改id为"+id+"的雇员的name为");
                    name = scanner.next();
                    System.out.println("输入需要修改id为"+id+"的雇员的age为");
                    age = scanner.nextInt();
                    hashTab.updateEmpById(new Emp(id,name,age));
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("输入需要查找的雇员id");
                    id = scanner.nextInt();
                    hashTab.findEmpById(id);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }

    }

}

//创建HashTab 管理多条链表
class HashTab{
    private EmpLinkedList[] empLinkedListArray;
    private int size;//表示共有多少条链表

    //构造器
    public HashTab(int size){
        this.size = size;
        empLinkedListArray = new EmpLinkedList[this.size];
        for(int i=0;i<empLinkedListArray.length;i++){//初始化这个数组,先给每个数组元素赋一个链表(虽然链表此时还为空,但是之后给链表添加节点时就可以直接添加了)
            empLinkedListArray[i] = new EmpLinkedList();
        }
    }


    /**
     * 添加雇员
     * @param emp
     */
    public void add(Emp emp){
        //根据员工的id,得到该员工应该添加到哪条链表
        int empLinkedListNO = hashFun(emp.id);
        //将emp添加到对应的链表中
        empLinkedListArray[empLinkedListNO].add(emp);
    }

    /**
     * 根据id删除雇员
     * @param id
     */
    public void deleteEmpById(int id){
        int empLinkedListNO = hashFun(id);//找到该id对应的应该放在哪条链表中
        Emp emp = empLinkedListArray[empLinkedListNO].findEmpById(id);
        if(emp != null){
            int flag = empLinkedListArray[empLinkedListNO].deleteEmpById(id);
            if(flag == 1){
                System.out.println("在第"+(empLinkedListNO+1)+"条链表中删除了该雇员: "+emp);
            }else{
                System.out.println("在哈希表中, 没有找到该雇员...");
            }
        }else{
            System.out.println("在哈希表中, 没有找到该雇员...");
        }

    }

    /**
     * 根据id修改雇员
     * 其实不用写这个方法,add方法 如果id重复就会覆盖,相当于修改了, 但是为了好看 还是把增删改查都写了吧
     * @param emp
     * @return
     */
    public void updateEmpById(Emp emp){
        int empLinkedListNo = hashFun(emp.id);//找到该id对应的应该放在哪条链表中
        //不会吧,这里我明明是 先查找到的 原始值, 然后才update的,为什么打印
        //在第2条链表中将Emp{id=8, name='王五五', age=55}修改为了Emp{id=8, name='王五五', age=55}
        Emp empById = empLinkedListArray[empLinkedListNo].findEmpById(emp.id);
        int flag = empLinkedListArray[empLinkedListNo].updateEmpById(emp);
        if(flag == 1){
            System.out.println("在第"+(empLinkedListNo+1)+"条链表中将"+empById+"修改为了"+emp);
        }else{
            System.out.println("需要修改的id为"+emp.id+"的雇员不存在,无法修改");
        }
    }

    /**
     * 根据id查找雇员
     * @param id
     * @return 找到 就返回对应的Emp, 找不到就返回null
     */
    public void findEmpById(int id){
        int empLinkedListNo = hashFun(id);//找到该id对应的应该放在哪条链表中
        Emp emp = empLinkedListArray[empLinkedListNo].findEmpById(id);
        if(emp != null){
            System.out.println("在第"+(empLinkedListNo+1)+"条链表中找到了该雇员: "+emp);
        }else{
            System.out.println("在哈希表中, 没有找到该雇员...");
        }
    }

    /**
     * 遍历所有链表, 遍历哈希表
     */
    public void list(){
        for(int i=0;i<empLinkedListArray.length;i++){
            empLinkedListArray[i].list(i);
        }
    }


    /**
     * 散列函数, 使用一个简单的取模法
     * 用 emp的 id   % size 得到对应的哈希值,然后就可以知道放入到哪个链表中了.
     *
     * 散列函数要求要 计算简单 分布均匀
     * 有几种简单的散列函数介绍下:
     * 直接定址法: 根据数据直接 定位 比如 Emp{5,"张三"} 就直接 放入 下标为5的地方, 但是Emp{368363,"李四"}这样就不太好了,所以不行
     * 数字分析法: 比如电话号码 Emp{17628374499,"张三"} 就可以根据 最后四位 放入下标4499的地方, 或者身份证号Emp{429xxxxxx1234} 根据最后四位 放入1234的地方
     * 平方取中法: 比如Emp{13,"张三"} 就根据 13*13=169 就可以将Emp放入下标为6的地方, Emp{14,"李四"} 15*15=225 就放入 下标为2的地方
     * 取余法:    比如Emp{13,"张三"} 就根据13%10=3 就将Emp放入下标为3的地方, Emp{14,"李四"} 14%10=4就放入 下标为4的地方, Emp{25,"王五"} 25%10=5就放入下标为5的地方
     * 随机数法:   random();  ......
     * @param id
     * @return
     */
    public int hashFun(int id){
        return id % size;
    }

}

//表示一个雇员
class Emp{
    public int id;
    public String name;
    public int age;
    public Emp next;//默认为空

    public Emp() {
    }
    public Emp(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

//创建一个EmpLinkedList, 表示一条链表
class EmpLinkedList{
    //头指针, 指向第一个Emp, 因此我们这个链表的head是直接指向第一个节点了,
    private Emp head;//默认为空

    /**
     * 添加雇员到链表
     * 默认 添加雇员时, id是自增长的,即id的分配总是从小到大, 因此我们将雇员直接加入到本链表的最后即可
     * @param emp
     */
    public void add(Emp emp){
        //如果是添加第一个雇员
        if(head == null){
            head = emp;
            return;
        }else{//不是第一个, 则使用一个temp遍历到链表尾部,然后添加
            Emp empById = findEmpById(emp.id);//查询一下,用户需要添加的emp是否已经存在
            if(empById == null){//如不存在,即在链表尾部添加用户输入的emp
                Emp temp = head;
                while(temp.next != null){//temp的next不为空就一直遍历,直到temp的next为空,说明已经temp已经是链表最后一个元素了,则退出循环
                    temp = temp.next;
                }
                //上面循环完后,temp已经是链表最后一个节点, 将emp赋给 temp.next, 就将emp加入到链表的最后了.
                temp.next = emp;
            }else{//如果已经存在,则修改 该id的name与age 为 用户输入的
                Emp temp = head;
                while(temp != null){
                    if(temp.id == emp.id){
                        temp.name = emp.name;
                        temp.age = emp.age;
                    }
                    temp = temp.next;
                }
            }
        }
    }

    /**
     * 根据id删除雇员
     * @param id
     */
    public int deleteEmpById(int id){
        //判断链表是否为空
        if(head == null){
            System.out.println("链表为空");
            return -1;
        }else{
            Emp temp = head;
            if(head.id == id){ //如第一个就是需要删除的节点
                head = head.next;
                return 1;
            }

            while(temp.next != null){//这是从temp.next.id开始判断 是否是需要删除的节点,没有判断temp
                if(temp.next.id == id){
                    temp.next = temp.next.next;
                    return 1;
                }
                temp = temp.next;
            }
            return -1;
        }
    }

    /**
     * 根据id修改雇员
     * 其实不用写这个方法,add方法 如果id重复就会覆盖,相当于修改了, 但是为了好看 还是把增删改查都写了吧
     * @param emp
     * @return
     */
    public int updateEmpById(Emp emp){
        Emp empById = findEmpById(emp.id);//根据id查找, 看看需要修改的Emp是否存在
        if(empById != null){
            Emp temp = head;
            while(temp != null){
                if(temp.id == emp.id){
                    temp.name = emp.name;
                    temp.age = emp.age;
                    return 1;
                }
                temp = temp.next;
            }
            return -1;
        }else{
            return -1;
        }
    }

    /**
     * 根据id查找雇员
     * @param id
     * @return 找到 就返回对应的Emp, 找不到就返回null
     */
    public Emp findEmpById(int id){
        //判断链表是否为空
        if(head == null){
            System.out.println("链表为空");
            return null;
        }else{
            Emp temp = head;
            while(temp != null){
                if(temp.id == id){
                    return temp;
                }
                temp = temp.next;
            }
            return null;
        }
    }

    /**
     * 遍历链表
     */
    public void list(int no){
        if(head == null){
            System.out.println("第 "+(no+1)+" 条链表为空");
        }else{
            System.out.print("第 "+(no+1)+" 条链表信息为: ");
            Emp temp = head;
            while(temp != null){
                System.out.printf("=> id=%d name=%s age=%d",temp.id,temp.name,temp.age);
                temp = temp.next;
            }
            System.out.println();
        }
    }
}
