package org.pjj.arithmetic.dynamic;

/**
 * 使用动态规划算法 解决 背包问题(01背包)
 * @author PengJiaJun
 * @Date 2020/8/27 15:48
 */
public class KnapsackProblem {

    public static void main(String[] args) {
        int[] w = {1,4,3};//物品的重量, 依次为 吉他(重量为 1), 音箱(4), 电脑(3)
        int[] val = {1500,3000,2000};//物品的价值, 依次为 吉他(价值为 1500), 音箱(3000), 电脑(2000)
        int m = 4;//背包的容量
        int n = val.length;//物品的个数

        //创建二维数组
        int[][] v = new int[n+1][m+1];
        //为了记录放入商品的情况, 我们定义一个二维数组
        int[][] path = new int[n+1][m+1];

        //初始化第一行和第一列为0, 这里说一下就行了, int[][]二维数组 默认就是0,  所以这里可以不处理
        for(int i=0;i<v.length;i++){//初始化第一列为 0
            v[i][0] = 0;
        }
        for(int i=0;i<v[0].length;i++){//初始化第一行位 0
            v[0][i] = 0;
        }

        //根据前面得到的公式来动态规划处理
        //外层for每循环一次 新增一种商品, 下次外层for就是在上次外层for处理的结果的基础上处理的
        //内存for每循环一次 就是在考虑 背包容量为1时可以 放什么商品, 背包容量为2时可以 放什么商品 直到背包真正的容量, 也就是说背包容量为5, 那么也会记录背包容量为1~4时可以放入什么商品
        for(int i=1; i<v.length; i++){ //i从1开始 表示不处理第一行, 因为第一行代表 没有物品, 空有背包咋放?
            for(int j=1; j<v[1].length; j++){ //j从1开始 表示不处理 第一列, 因为第一列代表 背包容量为0 啥也放放不了
                //公式
                if(w[i-1] > j){ // 因为 i是从1开始的 但是 w[]存储物品重量的数组是从0开始的  所以需要 w[i-1] 才能对应起来
                    //如果 w[i-1]表示的 新增物品重量  大于  j表示的背包的容量  则v[i][j]该位置的的值 等于 上次 新增物品时的 值v[i-1][j]
                    v[i][j] = v[i-1][j];
                } else { //w[i-1] <= j  //如果 w[i-1]表示的 新增物品重量  小于等于  j表示的背包的容量, 则
                    //公式原本是 Math.max(v[i-1][j],val[i]+v[i-1][j-w[i]]); 是什么意思呢?
                    //v[i-1][j-w[i]]  v[i-1]就是上一次新增物品时的那一行, [j-w[i]]就是 j表示当前背包容量 - w[i]表示新增物品的重量 = 背包剩余容量
                    //然后 v[i-1][j-w[i]]  就是 背包剩余容量    把这个背包剩余容量当做就是背包的正常容量 在上一次新增物品时 可以 装些什么物品 价值是多少.
                    //val[i]新增物品价值 + (加了val[i]后)背包剩余容量在上一次可以装下的物品的价值
                    //然后用 v[i-1][j] 与 val[i]+v[i-1][j-w[i]] 比较  看看谁的价值高, 则将谁放入本次新增物品 的对应背包容量大小的 地方  存放物品价值

                    //但是由于 i是从1开始的  val[]与w[] 是从0开始的 使用 所以涉及到val[i] 与 w[i]的地方需要 -1 变成 val[i-1] 与 w[i-1]
//                    v[i][j] = Math.max(v[i-1][j],val[i-1]+v[i-1][j-w[i-1]]);
                    //上面的使用的Math.max方法, 如果不想使用Math.max方法, 也可以如下面这样写
                    if(v[i-1][j] < val[i-1]+v[i-1][j-w[i-1]]){
                        v[i][j] = val[i-1]+v[i-1][j-w[i-1]];

                        path[i][j] = 1;//将当前情况记录到path 方便打印时观看
                    }else{// v[i-1][j] >= val[i-1]+v[i-1][j-w[i-1]]
                        v[i][j] = v[i-1][j];
                    }

                }
            }
        }

        //输出 v[][]
        for(int i=0;i<v.length;i++){
            for(int j=0;j<v[0].length;j++){
                System.out.print(v[i][j]+"\t");
            }
            System.out.println();
        }

        System.out.println("======================================");
        //输出 最后我们是放入的那些商品, 这样看起来还是不好, 于是有了下面那种
//        for(int i=0;i<path.length;i++){
//            for(int j=0;j<path[0].length;j++){
//                if(path[i][j] == 1){
//                    System.out.printf("第%d个商品放入背包\n",i);
//                }
//            }
//        }

        int i = path.length-1;
        int j = path[0].length-1;
        while(i > 0 && j > 0){
            if(path[i][j] > 0){
                System.out.printf("第%d个商品放入背包\n",i);
                j -= w[i-1];
            }
            i--;
        }


    }

}
