package org.pjj.arithmetic.divideandconquer;

/**
 * 使用分治算法 解决 汉诺塔问题
 * @author PengJiaJun
 * @Date 2020/8/26 11:14
 */
public class HanoiTower {

    public static void main(String[] args) {
        hanoiTower(3,'a','b','c');
    }

    /**
     *
     * @param num   一共有多少个盘
     * @param a     塔A
     * @param b     塔B
     * @param c     塔C
     */
    public static void hanoiTower(int num, char a,char b,char c){
        //如果只有一个盘
        if(num == 1){
            System.out.println("第1个盘从 "+ a +"-->"+ c);
        }else{
            //如果 num >= 2 的情况, 我们总是可以看做是两个盘 1.最下面的一个盘  2.上面的所有盘(上面的盘其实又可以看做是两个盘 1.最下面的一个盘 2.上面的盘.....)
            //1. 先把最上面的所有盘 A->B ,  移动过程会使用到 C
            hanoiTower(num - 1,a,c,b);//a b c    a c b      a b c
            //2. 把最下面的盘 A->C
            System.out.println("第"+ num +"个盘从 "+ a +"-->"+ c);
            //3. 把B塔的所有盘 从B->C (因为之前是 1.把A上面的盘都移动到了 B, 2.再把A最下面的盘移动到 C, 现在把B的所有盘移动到 C, 就完成了从 A 移动到 C )
            hanoiTower(num - 1,b,a,c);
        }
    }

}
