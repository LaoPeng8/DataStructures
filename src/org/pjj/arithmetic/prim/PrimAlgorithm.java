package org.pjj.arithmetic.prim;

/**
 * 普利姆算法 解决 修路问题  (最小生成树)
 * @author PengJiaJun
 * @Date 2020/9/4 15:40
 */
public class PrimAlgorithm {

    public static void main(String[] args) {
        //测试图是否创建成功
        char[] data = new char[]{'A','B','C','D','E','F','G'};
        int verxs = data.length;
        //邻接矩阵的关系用二维数组表示, 每个数组表示一个 一个顶点0~6表示A~G节点 , 每个数组中的0~6表示当前节点与A-G节点的关系
        //正常的这种 5,7,2表示 当前节点到某个节点的长度(也是这个边的权) 10000表示当前节点与某个节点不能直连
        //如{10000,5,7,10000,10000,10000,2} 该数组表示A节点 数组中的0~6分别表示 A-A不能直连,A-B可以直连且距离为5 等等.....
        int[][] weight = new int[][]{
                {10000,5,7,10000,10000,10000,2},
                {5,10000,10000,9,10000,10000,3},
                {7,10000,10000,10000,8,10000,10000},
                {10000,9,10000,10000,10000,4,10000},
                {10000,10000,8,10000,10000,5,4},
                {10000,10000,10000,4,5,10000,6},
                {2,3,10000,10000,4,6,10000}
        };

        //创建MGraph对象
        MGraph graph = new MGraph(verxs);
        //创建一个MinTree对象
        MinTree minTree = new MinTree();
        minTree.createGraph(graph,verxs,data,weight);
        //打印邻接矩阵
        minTree.showGraph(graph);

        //测试普利姆算法
        minTree.prim(graph,0);

    }

}

//创建最小生成树->村庄的图
class MinTree{

    /**
     * 创建图的邻接矩阵
     * @param graph  图对象
     * @param verxs  图对应的顶点个数
     * @param data   图的各个顶点的值
     * @param weight 图的邻接矩阵
     */
    public void createGraph(MGraph graph,int verxs,char[] data, int[][] weight){
//        老师写的, 不知道为什么不直接像我下面那样写...
//        int i,j;
//        for( i=0; i<verxs; i++){//遍历顶点
//            graph.data[i] = data[i];
//            for( j=0; j<verxs; j++){
//                graph.weight[i][j] = weight[i][j];
//            }
//        }

        graph.data = data;
        graph.weight = weight;
    }

    /**
     * 显示图的邻接矩阵
     * @param graph
     */
    public void showGraph(MGraph graph){
        for(int[] links : graph.weight){//遍历每个节点,
            for(int link : links){//遍历节点的每个边
                System.out.print(link+"\t");
            }
            System.out.println();
        }
    }


    /**
     * 普利姆算法(prim算法), 得到最小生成树
     * @param graph 图(图中必是包含 该图的节点数据  邻接矩阵)
     * @param v 表示从图的第几个顶点开始生成 最小生成树
     */
    public void prim(MGraph graph, int v){
        int[] visited = new int[graph.verxs];//标记顶点是否被访问过, 默认值为0 表示都没有被访问过
        //把当前这个节点标记为已访问
        visited[v] = 1;
        //h1 与 h2 记录两个顶点的下标
        int h1 = -1;
        int h2 = -1;
        int minWeight = 10000;// 表示最短的路    将minWeight初始化成10000, 后面在遍历过程中, 会被替换为 较小的值

        //因为有graph.verxs个顶点, 普利姆算法结束后, 有graph.verxs-1条边 所以k初始化为 1
        //循环几次 表示 生成多少条边   最小生成树 有多少个节点 就有 多少-1条边  所以这个for循环就是  每循环一次 画出一条边
        for(int k=1; k<graph.verxs;k++){

            // 遍历所有节点 i节点表示被访问过的节点(虽然这里遍历时也会遍历到没有访问过的节点,但是会被  if(visited[i] == 1)给过滤掉 剩下的就是 被访问过的节点)
            //用 i 去与 i 可以直连的节点(且没有被访问) 比较 若小于当前最短的路则将最短的路替换为 i~j,且记录i,j的坐标
            for(int i=0; i<graph.verxs; i++){// i顶点(节点) 表示被访问过的节点

                //遍历所有的节点 j节点表示未被访问的节点(虽然这里遍历是也会遍历到已访问的节点, 但是会被 if(visited[j]==0)给过滤掉 剩下的就是 未访问的节点)
                for(int j=0; j<graph.verxs; j++){// j顶点(节点) 表示还未被访问过的节点

                    if(visited[i] == 1 && visited[j] == 0 && graph.weight[i][j] < minWeight){
                        //替换minWeight (寻找已经访问过的节点 和 未访问过的节点间的权值最小的边)
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }

            //找到了一条最短的边
            System.out.println("边<" + graph.data[h1]+","+graph.data[h2] + "> 权值:"+minWeight);
            //将当前这个节点标记为已经访问的节点
            visited[h2] = 1;
            //重置minWeight, 要不然找下一次边的时候, minWeight就是上次的值, 就一直是最小了
            minWeight = 10000;
        }
    }


}

//图
class MGraph{
    int verxs;//表示图的节点个数
    char[] data;//存放节点数据
    int[][] weight;//存放边, 就是我们的邻接矩阵

    public MGraph(int verx){
        this.verxs = verx;
        data = new char[verx];
        weight = new int[verx][verx];
    }

}
