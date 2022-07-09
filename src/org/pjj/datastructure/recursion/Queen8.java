package org.pjj.datastructure.recursion;

/**
 * 八皇后问题
 * @author PengJiaJun
 * @Date 2020/7/29 12:13
 */
public class Queen8 {

    //定义一个max 表示有多少个皇后
    int max = 8;
    //定义一个数组array 保存皇后放置位置的结果, 比如 arr[8]= {0,4, 7,5,2, 6, 1, 3}
    int[] array = new int[max];
    static int count = 0;

    public static void main(String[] args) {
        //测试一波, 八皇后是否正确
        Queen8 queen8 = new Queen8();
        queen8.check(0);
        System.out.println("一共有 "+count+" 种解法...");
    }

    /**
     * 放置皇后
     * 特别注意: check是 每一次递归时, 进入到check中都有 for(int i=0;i<max;i++), 因此会有回溯
     * @param n 放置的第几个皇后
     */
    private void check(int n){
        if(n == max){ // n = 8, 其实八个皇后已经放好了, 因为n表面上是从1开始, 实际从0开始   0~7 八个皇后
            print();
            return;
        }
        
        //依次放入皇后, 并判断是否冲突
        for(int i=0;i<max;i++){
            //先把需要放入的皇后 n, 放入该行的第一列
            array[n] = i;
            //判断当放置第n个皇后到i列时, 是否冲突
            if(judge(n)){
                //如果不冲突, 就接着放n+1个皇后, 即开始递归
                check(n+1);//不冲突就开始递归放下一个皇后, 直到八个皇后都放完 n==max就退出递归了
            }
            //如果冲突,则再次进入循环,i++ 之后 array[n] = i; 如之前i=0;则是将皇后放入该行的第一列 , 而现在i=1后, 则将皇后放入该行的第二列
            //一直循环, 直到不冲突后, 进入if 再次递归调用check(n+1)即摆放下一个皇后.
        }
    }

    /**
     * 查看当我们放置第n个皇后时, 就去检测该皇后是否跟前面已经摆放的皇后 是否冲突
     * 例如: 在摆放第3个皇后时, 则要检测是否跟前面摆放的2个皇后冲突.   在摆放第7个皇后时, 则要检测是否跟前面摆放的6个皇后冲突
     * 摆放第n个皇后, 则要检测是否跟前面摆放的n-1个皇后冲突
     *
     * @param n 代表我们正在放置第几个皇后
     * @return
     */
    private boolean judge(int n){//n实际上最大才7因为 放置皇后时下标是从0开始的 所以 i=0 i<7 则放置第八个皇后时,判断冲突会循环判断7次
        for(int i=0;i<n;i++){//放置第一个皇后时 n=0 进入循环i=0 i<0 则不会进入循环判断是否冲突,因为才一行,随便怎么放都不会冲突的.

            //如果 array[i] == array[n] 则表示 这两个皇后在同一列, 因为array[]中存放的1~8个皇后的 下标, 例如: array[1] = 3, array[5] = 3,
            //意思就是说: 第一个皇后在第三列,第5个皇后也在第3列, 那么就冲突了.
            //如果 Math.abs(n-i) == Math.abs(array[n] - array[i]) 相当于 (y2-y1) == (x2-x1) 斜率K为1, 表示在同一斜线上,如果在同一个斜线上就冲突了
            //这个判断是否在同一斜线 我也看不懂, 好像明白一点了, 数学公式(y2-y1) == (x2-x1) 斜率K为1,则表示两点在同一斜线上,
            //而Math.abs(n-i) == Math.abs(array[n] - array[i]) 就相当于是这个公式, 因为 n与i是代表第几行,相当于y轴 (y2-y1)
            //然后array[n] 与 array[i] 是代表第几列 相当于 x轴 (x2-x1)  所以就是可以使用公式 (y2-y1) == (x2-x1)如为真(1) 则在两点(两个皇后)在同一斜线上,否则不在同一斜线上
            //不用判断是否在同一行, 因为n每次都在递增, 相当于我们放置皇后时, 一行只能放一个皇后
            if(array[i] == array[n] || Math.abs(n-i) == Math.abs(array[n] - array[i]) ){
                return false;
            }
        }
        return true;
    }

    /**
     * 可以将皇后摆放的位置输出
     */
    private void print(){
        count++;
        for(int i=0;i<array.length;i++){
            if(i==0){
                System.out.print("array= ");
            }
            System.out.print(array[i]+"  ");
        }
        System.out.println();
    }

}
