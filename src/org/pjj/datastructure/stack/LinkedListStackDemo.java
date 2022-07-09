package org.pjj.datastructure.stack;

import java.util.Scanner;

/**
 * 单向链表实现栈
 * @author PengJiaJun
 * @Date 2020/7/22 13:13
 */
public class LinkedListStackDemo {
    public static void main(String[] args) {
        //测试一下ArrayStack栈
        LinkedListStack stack = new LinkedListStack(4);
        String key = "";
        boolean loop = true; //控制是否退出菜单
        Scanner scanner = new Scanner(System.in);
        while(loop){
            System.out.println("show: 表示显示栈");
            System.out.println("exit: 退出程序");
            System.out.println("push: 表示入栈");
            System.out.println("pop: 表示出栈");
            System.out.print("请输入你的选择: ");
            key = scanner.next();
            switch (key){
                case "show" :
                    try{
                        stack.list();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                case "exit" :
                    loop = false;
                    scanner.close();
                    break;
                case "push" :
                    System.out.print("请输入需要入栈的数据: ");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "pop" :
                    try{
                        int pop = stack.pop();
                        System.out.println("出栈的数据为"+pop);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出...");

    }
}

class LinkedListStack{
    private int maxSize;    //栈的大小
    private Node top;   //top表示为栈顶, 没有数据时top = null

    public LinkedListStack(int maxSize) {
        this.top = null;
        this.maxSize = maxSize;
    }

    //栈满
    public boolean isFull(){
//        return top ==
        return listSize() >= maxSize;
    }

    //栈空
    public boolean isEmpty(){
        //top是栈顶, 如果top==null, 则栈中没有数据, 但凡有一个数据,top都不为null
        return top == null ? true : false;
    }

    //入栈
    public void push(int value){
        //先判断栈是否满了
        if(isFull()){
            System.out.println("栈满,无法入栈");
            return;
        }
        Node node = new Node(value);//需要入栈的数据

        node.next = top;//旧top赋值给 新入栈的数据的next,保证数据不会丢失
        top = node;//这个新入栈的数据 成为新的 top
    }

    //出栈
    public int pop(){
        //先判断栈是否为空
        if(isEmpty()){
            throw new RuntimeException("栈空,无法出栈");
        }
        int value = top.data;//出栈, 当前栈顶元素出栈
        top = top.next;//新栈顶,等于之前栈顶的下一个元素
        return value;
    }

    //返回当前链表有多少个 数据,
    public int listSize(){
        int size = 0;
        Node temp = top;
        while(temp!=null){
            size++;
            temp = temp.next;
        }
        return size;
    }

    //打印当前栈
    public void list(){
        if(isEmpty()){
            throw new RuntimeException("栈为空");
        }
        Node temp = top;
        while(temp!=null){
            System.out.println("stack="+temp.data);
            temp = temp.next;
        }
    }
}

class Node{
    public int data;
    public Node next;

    public Node() {
    }
    public Node(int data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                '}';
    }
}