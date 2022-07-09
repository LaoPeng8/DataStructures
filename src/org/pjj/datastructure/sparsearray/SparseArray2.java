package org.pjj.datastructure.sparsearray;

import java.io.*;

/**
 * 写入文件版:
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
 * @Date 2020/7/16 11:36
 */
public class SparseArray2 {
	public static void main(String[] args) throws IOException {
		//创建一个原始的二维数组   11 * 11     相当于一个11行11列的棋盘
        //0: 表示没有棋子     1: 表示黑子     2: 表示白子
        int chessArray[][] = new int[11][11];
        chessArray[1][2] = 1;//该坐标有个1 也就是有个黑子
        chessArray[2][3] = 2;//该坐标有个2 也就是有个白子

        String filepath = "E:/IDEA/ideaProject/DataStructures/data/数据结构与算法/files/sparseArray.txt";
        twoArrayToSparseArrayToFile(chessArray,filepath);           //将二维数组转为稀疏数组存入磁盘
        int[][] chessArray2 = sparseArrayFileToTwoArray(filepath);  //将磁盘中的稀疏数组读取出来转为二维数组

        //打印从文件恢复的二维数组
        for(int[] row : chessArray2){
            for(int data : row){
                System.out.printf("%d\t",data);
            }
            System.out.println();
        }

    }

	//二维数组 转 稀疏数组 然后再存入文件
	public static void twoArrayToSparseArrayToFile(int[][] chessArray, String filepath) throws IOException {
        int sum = 0;
        for(int i=0;i<chessArray.length;i++){
            for(int j=0;j<chessArray[i].length;j++){
                if(chessArray[i][j]!=0){
                    sum++;
                }
            }
        }

        //2. 创建对应的稀疏数组, 我们之前遍历得出了二维数组有多少个有效数据sum, 那么就可以得出稀疏数组的行,因为稀疏数组一行存储一个元素的x,y坐标 + 元素值,
        //二维数组有几个有效数据, 对应的稀疏数组就有多少行+1, 为什么要+1, 因为稀疏数组第一行需要存储 二维数组的 行和列 以及 有多少个有效数据.
        //而稀疏数组的列是固定的 有 3列 分别存储一个元素的 x,y坐标 + 元素值, 那么知道了稀疏数组的 行和列 就可以创建稀疏数组了.
        int sparseArray[][] = new int[sum+1][3];

        //稀疏数组第一行需要存储 二维数组的 行和列 以及 有多少个有效数据.
        sparseArray[0][0] = chessArray.length;//行
        sparseArray[0][1] = chessArray[0].length;//列
        sparseArray[0][2] = sum;//多少个有效数据

        // 遍历二维数组, 将非0的值存放到 稀疏数组sparseArray中
        int count = 0;//用于记录是第几个非0数据(有效数据)
        for(int i=0;i<chessArray.length;i++){
            for(int j=0;j<chessArray[i].length;j++){
                if(chessArray[i][j]!=0){
                    count++;
                    //稀疏数组的列是固定的 有 3列 分别存储一个元素的 x,y坐标 + 元素值
                    sparseArray[count][0] = i;                  //行
                    sparseArray[count][1] = j;                  //列
                    sparseArray[count][2] = chessArray[i][j];   //元素值
                }
            }
        }

        FileOutputStream fos = new FileOutputStream(filepath);//输出流
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<sparseArray.length;i++){//遍历稀疏数组
            sb.append(sparseArray[i][0]+"\t");
            sb.append(sparseArray[i][1]+"\t");
            sb.append(sparseArray[i][2]+"\n");
        }
        fos.write(sb.toString().getBytes(),0,sb.toString().length());
        fos.flush();
        fos.close();
    }

    //从文件读取 稀疏数组 转为 二维数组
    public static int[][] sparseArrayFileToTwoArray(String filepath) throws IOException {
	    int[][] chessArray = null;
        BufferedReader br = new BufferedReader(new FileReader(filepath));

        int one = 1;
        boolean flag = true;
        while(flag){
            String sparseArray = "";
            String[] split = null;
            if(one == 1){
                //第一次读取的是 二维数组的 行和列以及有效数据个数, 然后生成对应大小的二维数组
                sparseArray = br.readLine();
                split = sparseArray.split("\t");    //读取一行数据 并根据 \t分割   如   11\t 11\t 2\n
                chessArray = new int[Integer.parseInt(split[0])][Integer.parseInt(split[1])];
                one++;//只有第一次既然循环才会执行该if内的语句 之后 one++ 变成2了 就不会进入该if了
            }else{
                sparseArray = br.readLine();//第0行存储的是二维数组的行和列, 所以这里从第1行开始读取
                if(sparseArray==null){//读取为null 则说明文件中的稀疏数组没有值了,那么flag=false 跳出循环
                    flag = false;
                }else{//不为null 则说明文件中的稀疏数组还有数据, 那么就继续赋值给二维数组
                    split = sparseArray.split("\t");    //读取一行数据 并根据 \t分割   如   11\t 11\t 2\n
                    chessArray[Integer.parseInt(split[0])][Integer.parseInt(split[1])] = Integer.parseInt(split[2]);
                }
            }

        }
        br.close();
	    return chessArray;
    }
}
