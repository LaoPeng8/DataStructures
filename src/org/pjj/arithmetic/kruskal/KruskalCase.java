package org.pjj.arithmetic.kruskal;

import java.util.Arrays;

/**
 * 克鲁斯卡尔算法 解决 公交站问题
 * @author PengJiaJun
 * @Date 2020/9/6 22:58
 */
public class KruskalCase {

    public static void main(String[] args) {
        char[] vertexs = {'A','B','C','D','E','F','G'};//顶点
        //邻接矩阵  两个节点能直连 则用两点的距离显示  如果不能直连,则用INF表示  如果是自己与自己则用0表示
        //如下面的 {   0,  12, INF, INF, INF,  16,  14}  0表示 A与A是自己  12表示 A与B可以直连且距离为12  INF表示A与C不能直连
        int matrix[][] = {
                        /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ {   0,  12, INF, INF, INF,  16,  14},
                /*B*/ {  12,   0,  10, INF, INF,   7, INF},
                /*C*/ { INF,  10,   0,   3,   5,   6, INF},
                /*D*/ { INF, INF,   3,   0,   4, INF, INF},
                /*E*/ { INF, INF,   5,   4,   0,   2,   8},
                /*F*/ {  16,   7,   6, INF,   2,   0,   9},
                /*G*/ {  14, INF, INF, INF,   8,   9,   0}};

        KruskalCase kruskal = new KruskalCase(vertexs,matrix);//创建KruskalCase对象
        kruskal.print();//输出构建的邻接矩阵

        kruskal.kruskal();


    }


    private int edgeNum;//边的个数
    private char[] vertexs;//顶点数组
    private int[][] matrix;//邻接矩阵
    private static final int INF = Integer.MAX_VALUE;//使用INF来表示 两个顶点之前 不能直连

    public KruskalCase(char[] vertexs, int[][] matrix){
        //初始化顶点数 和 边的个数
        int vLen = vertexs.length;

        //初始化顶点   为什么不像这样 this.vertexs = vertexs; 而是使用下面这种方式?
        //因为 直接 this.vertexs = vertexs;这样  在程序内会修改 vertexs的值 因为这样赋值 只是将 vertexs的引用赋给 this.vertexs了
        //而 使用下面这种方式 相当于产生了一个将 vertexs的内容 copy给了 this.vertexs 在内存中就有两个 vertexs了  修改this.vertexs不会影响 vertexs
        this.vertexs = new char[vLen];
        for(int i=0; i<vertexs.length; i++){
            this.vertexs[i] = vertexs[i];
        }

        //初始化边
        this.matrix = new int[vLen][vLen];
        for(int i=0; i<vLen; i++){
            for(int j=0; j<vLen; j++){
                this.matrix[i][j] = matrix[i][j];
            }
        }

        //统计边
        for(int i=0; i<vLen; i++){
            for(int j=i+1; j<vLen; j++){
                if(this.matrix[i][j] != INF){ //表示 i 与 j 是可以直连的
                    edgeNum++;
                }
            }
        }

    }

    /**
     * 打印邻接矩阵
     */
    public void print(){
        System.out.println("邻接矩阵为: \n");
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
//                System.out.printf("%13d",this.matrix[i][j]);
                System.out.print((this.matrix[i][j]==INF?"NO":this.matrix[i][j])+"\t\t\t");
            }
            System.out.println();
        }
    }

    /**
     * 对边进行排序 , 这里使用希尔排序
     * @param edges
     */
    private void sortEdges(EData[] edges){
        for(int gap=edges.length/2; gap>0; gap=gap/2){
            for(int i=gap; i<edges.length;i=i+gap){
                EData temp = edges[i];//需要排序的节点, 也就是无序列表的第一个
                int index = i-gap;//有序列表最后一个
                while(index>=0 && temp.weight < edges[index].weight){
                    edges[index+gap] = edges[index];
                    index = index - gap;
                }
                edges[index+gap] = temp;
            }
        }
    }

    /**
     * 根据顶点的值 返回 该顶点的下标
     * @param ch 顶点的值, 例如 'A','B'
     * @return 返回 ch顶点 对应的下标, 如果找不到该顶点对应的下标, 返回-1
     */
    private int getPosition(char ch){
        for(int i=0; i<vertexs.length;i++){
            if(vertexs[i] == ch){
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取图中边, 放到EData[]数组中 , 后面我们需要遍历该数组
     * 通过matrix 邻接矩阵来获取
     * EData[] 形式 [['A','B',12],['B','F',7], ......]
     * @return
     */
    private EData[] getEdges(){
        int index = 0;
        EData[] edges = new EData[edgeNum];
        for(int i=0; i<vertexs.length; i++){
            for(int j=i+1; j<vertexs.length; j++){
                if(matrix[i][j] != INF){
                    edges[index++] = new EData(vertexs[i],vertexs[j],matrix[i][j]);
                }
            }
        }

        return edges;
    }

    /**
     * 获取下标为 i 的顶点的终点, 用于后面判断两个顶点的终点是否相同
     * 终点: 顶点的终点是 "最小生成树中与它连通的最大顶点(最大顶点的意思是,例如'A','B','C' 互相连接 则与A连接的最大顶点为C, 所以A的终点也是C)"
     * 添加一条边到最小生成树时, 需要判断该边的两个顶点的终点是否重合, 若重合则会构成回路
     * @param ends 记录了 各个顶点对应的终点是哪个, ends 数组是在遍历过程中, 逐步形成的
     * @param i 表示传入的顶点对应的下标
     * @return 返回的就是 下标为i的顶点对应的终点的下标
     */
    private int getEnd(int[] ends, int i){
        while(ends[i] != 0){
            i = ends[i];
        }
        return i;
    }

    /**
     * 克鲁斯卡尔算法
     */
    public void kruskal(){
        int index = 0;//表示最后结果数组的索引
        int[] ends = new int[edgeNum]; //用于保存 "已有最小生成树" 中的每个顶点在最小生成树中的终点

        //创建结果数组, 保存最后的最小生成树
        EData[] rets = new EData[edgeNum];

        //获取图中所有的边的集合, 一共有12条边
        EData[] edges = getEdges();
        System.out.println("图的边的集合=" + Arrays.toString(edges)+" 共"+edges.length);

        //按照边的权值大小进行排序(从小到大)
        sortEdges(edges);

        //遍历edges数组, 将边添加到最下生成树中, 判断是准备加入的边是否形成了回路, 如果没有, 就加入 rets, 否则不能加入
        for(int i=0;i<edgeNum; i++){
            //获取到第 i 条边的 一个点(起点)     哎,一条边(线), 不是需要两个点吗, 然后连接两个点, 就是一条线, 这里就是获取 i 条边的 一个点
            int p1 = getPosition(edges[i].start);
            int p2 = getPosition(edges[i].end); // 获取 第 i 条边的 另一个点

            //获取 p1 这个顶点在已有最小生成树中的终点
            int m = getEnd(ends,p1);
            //获取 p2 这个顶点在已有最小生成树中的终点
            int n = getEnd(ends,p2);

            //判断p1 与 p2 是否是同一个终点(是否构成回路)
            if(m != n){ //如果 m != n 也就是 p1 与 p2 的终点不是同一个终点, 即可
                ends[m] = n; //设置 m 在 "以有最小生成树" 中的终点
                rets[index++] = edges[i]; //有一条边加入rets数组
            }
        }

        //统计并打印 "最小生成树" , 输出 rets
        for(int i=0; i<rets.length;i++){
            System.out.println(rets[i]);
        }

    }

}

//创建一个边类EData, 它的对象实例就表示一条边
class EData{
    char start;//边的起点 (边的一个点)
    char end;//边的终点 (边的另一个点)
    int weight;//边的权值 表示连接这两个点的边的距离

    //构造器
    public EData(char start, char end, int weight){
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "EData{" +
                "<" + start +
                ", " + end +
                ">= " + weight +
                '}';
    }
}
