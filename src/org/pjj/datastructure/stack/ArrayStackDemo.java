package org.pjj.datastructure.stack;

import java.util.Scanner;

/**
 * 数组模拟栈
 * 栈: 先进后出
 * @author PengJiaJun
 * @Date 2020/7/22 12:08
 */
public class ArrayStackDemo {
    public static void main(String[] args) {
        //测试一下ArrayStack栈
        ArrayStack stack = new ArrayStack(4);
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

//定义了一个ArrayStack 表示栈
class ArrayStack{
    private int maxSize;    //栈的大小
    private int[] stack;    //数组, 数组模拟栈,数据就放在该数组中
    private int top = -1;   //top表示为栈顶, 没有数据时top = -1

    public ArrayStack(int maxSize){
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    //栈满
    public boolean isFull(){
        //栈是由数组模拟的, 数组的大小是 new int[maxSize], 一个数组 arr[10] 虽然可以存10个数组,但是下标是0~9,最大下标是9, 下标是不可能到10的, 所以要-1
        //也就是说top 最大 不能大于 数据的最大下标 也就是栈的大小maxSize - 1
        return top == maxSize-1 ? true : false;
    }

    //栈空
    public boolean isEmpty(){
        //top == -1 就表示栈为空, 如果有一个数据top都会 是为 0.  因为top是指向栈顶的, 加入一个数据top就会+1
        return top == -1 ? true : false;
    }

    //入栈
    public void push(int value){
        //先判断栈是否满了
        if(isFull()){
            System.out.println("栈满,无法入栈");
            return;
        }
       // top++;//top指向栈顶数据, 当有数据入栈时, 先将top往后移动一格
        stack[++top] = value;//然后再将, 数据赋值到top所在地方, top还是指向栈顶
    }

    //出栈
    public int pop(){
        //先判断栈是否为空
        if(isEmpty()){
            throw new RuntimeException("栈空,无法出栈");
        }
        int value = stack[top--];//top指向栈顶数据, 当有数据出栈时, 先将top所在值, 取出
        //top--;//然后top--, top还是指向栈顶
        return value;
    }

    //显示栈所有的数据(遍历栈), 遍历时, 需要从栈顶开始显示数据
    public void list(){
        //先判断栈是否为空
        if(isEmpty()){
            throw new RuntimeException("栈空,无法出栈");
        }
        for(int i=top;i>=0;i--){
            System.out.println("stack["+stack[i]+"] ");
        }
    }


}
