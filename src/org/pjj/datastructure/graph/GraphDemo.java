package org.pjj.datastructure.graph;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 图
 * @author PengJiaJun
 * @Date 2020/8/23 17:56
 */
public class GraphDemo {

    public static void main(String[] args) {
        //测试一把图是否创建ok
        int n = 5;
//        String vertexValues[] = {"A","B","C","D","E"};
        String vertexValues[] = {"1","2","3","4","5","6","7","8"};
        //创建图对象
        Graph graph = new Graph(8);
        //循环的添加顶点
        for(String vertexValue : vertexValues){
            graph.insertVertex(vertexValue);
        }
        //添加边  A-B  A-C  B-C  B-D  B-E
//        graph.insertEdge(0,1,1); // A-B
//        graph.insertEdge(0,2,1); // A-C
//        graph.insertEdge(1,2,1); // B-C
//        graph.insertEdge(1,3,1); // B-D
//        graph.insertEdge(1,4,1); // B-E
        graph.insertEdge(0, 1, 1);// 1-2
        graph.insertEdge(0, 2, 1);// 1-3
        graph.insertEdge(1, 3, 1);// 2-4
        graph.insertEdge(1, 4, 1);// 2-5
        graph.insertEdge(3, 7, 1);// 4-8
        graph.insertEdge(4, 7, 1);// 5-8
        graph.insertEdge(2, 5, 1);// 3-6
        graph.insertEdge(2, 6, 1);// 3-7
        graph.insertEdge(5, 6, 1);// 6-7


        graph.showGraph();//打印图

        //测试 深度优先算法, dfs
        System.out.println("深度遍历");
        graph.dfs();//什么事情都要我高啊，妈的，我裂开，29号去学校，被古没了，我就没得地方睡觉了

        //测试 广度优先算法, bfs
        System.out.println("\n广度遍历");
        graph.bfs();
    }

}

class Graph{

    private ArrayList<String> vertexList;// 存储顶点的集合
    private int[][] edges; //存储图对应的邻结矩阵
    private int numOfEdges;// 表示边的数目
    private boolean[] isVisited;// 定义一个boolean[]数组, 记录某个节点是否被访问

    //构造器
    public Graph(int n){// n = 有多少个顶点
        //初始化矩阵 和 vertexList
        this.vertexList = new ArrayList<>(n);//有多少个顶点, 就创建多大的list来存放这些顶点
        this.edges = new int[n][n];//有多少个顶点 矩阵大小就是 多少 * 多少
        this.numOfEdges = 0;//有几条边 初始化为0
        isVisited = new boolean[n];// 有几个顶点, 就new一个 多大的boolean[] , 记录某个节点是否被访问
    }

    /**
     * 得到第一个邻接节点的下标
     * @param index 输入顶点的下标, 返回这个顶点第一个邻接节点的下标
     * @return 如果存在就返回对应的下标, 如果不存在就返回-1
     * 直接看代码可能不太明白, 配合图(邻接矩阵) 一看就看出来了.
     */
    public int getFirstNeighbor(int index){
        for(int j=0;j<vertexList.size();j++){
            if(edges[index][j] > 0){
                return j;
            }
        }
        return -1;
    }

    /**
     * 根据前一个邻接节点的 下标 , 来获取下一个邻接节点
     * @param v1 相当于 邻接节点的 第几行  array[v1][]
     * @param v2 相当于 邻接节点的 第几列  array[][v2]
     * @return
     */
    public int getNextNeighbor(int v1,int v2){
        //j = v2+1;是必须+1的因为, 如果不+1 遍历时就会比较输入的这个邻接节点的下标,看看当前下标是否 是邻接节点,
        //输入的是邻接节点的下标, 那么当前必是 邻接节点, +1是为了从当前邻接节点的下一个位置开始找, 不然每次都是返回输入的这个邻接节点了...
        for(int j=v2+1;j<vertexList.size();j++){
            if(edges[v1][j] > 0){
                return j;
            }
        }
        return -1;
    }

    /**
     * 深度优先遍历算法
     * i 第一次就是 0
     * @param isVisited 该数组中存了 某个节点是否被访问过了
     * @param i 节点下标
     */
    private void dfs(boolean[] isVisited, int i){
        //首先我们访问该节点, 输出
        System.out.print(getValueByIndex(i) + "-->");
        //将刚才已经访问过的节点置为已访问
        isVisited[i] = true;

        int w = getFirstNeighbor(i);//根据 i(顶点的下标) 返回第一个 邻接节点
        while(w != -1){ //找到了 i(顶点的下标) 的第一个 邻接节点
            if(isVisited[w] != true){// 该邻接节点 w 未被访问
                dfs(isVisited,w);
            }
            //如果 w节点已经被访问过了
            w = getNextNeighbor(i,w);
        }

    }

    /**
     * 对dfs 进行一个重载, 遍历我们所有的节点, 并进行dfs
     */
    public void dfs(){
        //遍历所有的节点, 进行dfs[回溯]
        for(int i=0;i<getNumOfVertex();i++){
            if(isVisited[i] != true){
                dfs(isVisited,i);
            }
        }

        //深度优先遍历完后, 将代表 节点是否被访问的数组 isVisited[] 置为 全部节点未访问, 方便下一次 遍历
        for(int i=0;i<isVisited.length;i++){
            isVisited[i] = false;
        }
    }

    /**
     * 广度优先遍历算法
     * @param isVisited
     * @param i
     */
    private void bfs(boolean[] isVisited,int i){
        int u;//表示队列头节点对应的下标
        int w;//表示 节点u的第一个邻接节点
        //队列, 记录节点访问的顺序
        LinkedList<Integer> queue = new LinkedList<>();//模拟队列
        //访问节点, 输出节点信息
        System.out.print(getValueByIndex(i)+"-->");
        //将刚才已经访问过的节点置为已访问
        isVisited[i] = true;
        //将节点加入队列 算法 data structure  arithmetic
        queue.addLast(i);//相当于入队列
        while( !queue.isEmpty()){
            //取出队列的头节点 下标
            u = queue.removeFirst();//相当于出队列
            w = getFirstNeighbor(u);//取出节点 u 的第一个邻接节点
            while(w != -1){//找到了 u(顶点的下标) 的第一个 邻接节点
                if(isVisited[w] != true){//结点w尚未被访问 才会进入该 if
                    System.out.print(getValueByIndex(w)+"-->");
                    isVisited[w] = true;//若结点w尚未被访问，则访问结点w并标记为已访问。
                    queue.addLast(w);//结点w入队列
                }
                w = getNextNeighbor(u, w);//查找结点u的继w邻接结点后的下一个邻接结点w
            }
        }

    }

    //遍历所有的节点, 都进行广度优先搜索
    public void bfs(){
        //遍历所有的节点, 进行bfs
        for(int i=0;i<getNumOfVertex();i++){
            if(isVisited[i] != true){
                bfs(isVisited,i);
            }
        }

        //广度优先遍历完后, 将代表 节点是否被访问的数组 isVisited[] 置为 全部节点未访问, 方便下一次 遍历
        for(int i=0;i<isVisited.length;i++){
            isVisited[i] = false;
        }
    }

    /**
     * 插入节点(顶点)
     * @param vertex
     */
    public void insertVertex(String vertex){
        vertexList.add(vertex);
    }

    /**
     * 添加边
     * @param v1        表示顶点的下标  (哪个顶点和哪个顶点之间添加边)   "A"-"B" "A"->0 "B"->1 需要连接A与B 则是 v1=0,v2=1
     * @param v2        表示顶点的下标  (哪个顶点和哪个顶点之间添加边)
     * @param weight    权值(v1~v2之间的距离)
     */
    public void insertEdge(int v1,int v2, int weight){
        edges[v1][v2] = weight; //建立 v1 ~ v2 的边     (建立之后表示 v1 可以去 v2)
        edges[v2][v1] = weight; //由于是 无向图 所以  也需建立 v2 ~ v1 的边       (建立之后表示 v2 可以去 v1)
        numOfEdges++;//新加了一条边, 则表示边的数据的 numOfEdges需要+1
    }

    //图中常用的方法↓↓↓↓↓↓↓

    /**
     * 返回顶点的个数
     * @return
     */
    public int getNumOfVertex(){
        return vertexList.size();
    }

    /**
     * 返回边的个数
     * @return
     */
    public int getNumOfEdges(){
        return numOfEdges;
    }

    /**
     * 返回顶点i(下标) 对应的数据 0->"A"  1->"B"  2->"C"
     * 根据 顶点下标 i 返回该顶点
     * @param i
     * @return
     */
    public String getValueByIndex(int i){
        return vertexList.get(i);
    }

    /**
     * 返回 v1 和 v2 的权值   权值: (v1 与 v2 之间边的 长度)
     * @param v1
     * @param v2
     * @return
     */
    public int getWeight(int v1,int v2){
        return edges[v1][v2];
    }

    /**
     * 显示图对应的矩阵
     */
    public void showGraph(){
        for(int i=0;i<edges.length;i++){
            for(int j=0;j<edges[i].length;j++){
                System.out.print(edges[i][j]+"\t");
            }
            System.out.println("\n");
        }
    }


}
