package org.pjj.datastructure.recursion;

/**
 * 递归回顾
 * @author PengJiaJun
 * @Date 2020/7/26 20:08
 */
public class RecursionTest {

    public static void main(String[] args) {
        test(4);//通过打印问题回顾递归的调用机制

//        int factorial = factorial(4);
//        System.out.println(factorial);
    }

    //通过打印问题回顾递归的调用机制
    public static void test(int n){
        if ( n > 2 ){
            test(n-1);
        }
        System.out.println("n="+n);
    }

    //阶乘问题 一个正整数的阶乘（factorial）是所有小于及等于该数的正整数的积，并且0的阶乘为1
    //比如 5的阶乘就是 1x2x3x4x5的值
    public static int factorial(int n){
        if(n==1){
            return 1;
        }else{
            return factorial(n - 1) * n;
        }
    }

}
