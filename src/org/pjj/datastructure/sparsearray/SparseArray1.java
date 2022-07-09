package org.pjj.datastructure.sparsearray;

/**
 * 没有写入文件版:
 *
 * 稀疏数组: 可以把二维数组转为稀疏数组, 稀疏数组的第一行存储着二位数组的 行 列 元素个数, 第二行开始就存储元素的 行坐标 列坐标 元素值
 *
 * 稀疏数组应用场景:
 * 		当二维数组中有很多值是默认值0,因此记录了很多没有意义的数据的可以可以转为 ->稀疏数组保存
 * 			比如 五子棋棋盘 存盘退出和续上盘功能
 *
 * 二维数组转稀疏数组:
 * 	1.遍历 原始的二维数组,得到有效数据的个数sum
 * 	2.根据sum的值就可以创建稀疏数组sparseArr[sum+1][2]
 * 	3.将二维数组的有效数据存到稀疏数组
 * 稀疏数组转二维数组:
 * 	1.先读取稀疏数组的第一行(0),根据第一行的数据,创建二维数组
 * 	2.在读取稀疏数组之后几行的数据(从第二行开始(1)),并赋值为原始的二维数组
 * @author PengJiaJun
 * @Date 2020/7/16 20:36
 */
public class SparseArray1 {
	public static void main(String[] args) {
		//创建一个原始的二维数组   11 * 11     相当于一个11行11列的棋盘
        //0: 表示没有棋子     1: 表示黑子     2: 表示白子
        int chessArr1[][] = new int[11][11];
        chessArr1[1][2] = 1;//该坐标有个1 也就是有个黑子
        chessArr1[2][3] = 2;//该坐标有个2 也就是有个白子
        //输出原始的二维数组
        System.out.println("原始的二维数组: ");
        for(int[] row : chessArr1){
            for(int data : row){
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }


        //将二维数组 转 稀疏数组 的思路
        //1. 先遍历二维数组 得到非0数据的个数
        int sum = 0;
        for(int i=0;i<chessArr1.length;i++){
            for(int j=0;j<chessArr1[i].length;j++){
                if(chessArr1[i][j]!=0){
                    sum++;
                }
            }
        }
        System.out.println("sum="+sum);

        //2. 创建对应的稀疏数组, 我们之前遍历得出了二维数组有多少个有效数据sum, 那么就可以得出稀疏数组的行,因为稀疏数组一行存储一个元素的x,y坐标 + 元素值,
        //二维数组有几个有效数据, 对应的稀疏数组就有多少行+1, 为什么要+1, 因为稀疏数组第一行需要存储 二维数组的 行和列 以及 有多少个有效数据.
        //而稀疏数组的列是固定的 有 3列 分别存储一个元素的 x,y坐标 + 元素值, 那么知道了稀疏数组的 行和列 就可以创建稀疏数组了.
        int sparseArray[][] = new int[sum+1][3];

        //稀疏数组第一行需要存储 二维数组的 行和列 以及 有多少个有效数据.
        sparseArray[0][0] = chessArr1.length;//行
        sparseArray[0][1] = chessArr1[0].length;//列
        sparseArray[0][2] = sum;//多少个有效数据

        // 遍历二维数组, 将非0的值存放到 稀疏数组sparseArray中
        int count = 0;//用于记录是第几个非0数据(有效数据)
        for(int i=0;i<chessArr1.length;i++){
            for(int j=0;j<chessArr1[i].length;j++){
                if(chessArr1[i][j]!=0){
                    count++;
                    //稀疏数组的列是固定的 有 3列 分别存储一个元素的 x,y坐标 + 元素值
                    sparseArray[count][0] = i;                  //行
                    sparseArray[count][1] = j;                  //列
                    sparseArray[count][2] = chessArr1[i][j];    //元素值
                }
            }
        }

        // 输出稀疏数组
        System.out.println("由二维数组转成的稀疏数组: ");
        for(int i=0;i<sparseArray.length;i++){
            System.out.printf("%d\t%d\t%d\n ",sparseArray[i][0],sparseArray[i][1],sparseArray[i][2]);
        }


        //稀疏数组 转 二维数组 的思路
        //1. 先读取稀疏数组的第一行(0),根据第一行的数据,创建二维数组
        int chessArr2[][] = new int[sparseArray[0][0]][sparseArray[0][1]];

        //2. 读取稀疏数组 后几行的数据(从第二行开始(1)),并赋值为原始的二维数组
        for(int i=1;i<sparseArray.length;i++){
            chessArr2[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }

        //输出由稀疏数组转为的二维数组
        System.out.println("由稀疏数组转成的二维数组: ");
        for(int[] row : chessArr1){
            for(int data : row){
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }

	}
}
