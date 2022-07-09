package org.pjj.datastructure.recursion;

/**
 * 迷宫问题
 *
 * @author PengJiaJun
 * @Date 2020/7/27 14:07
 */
public class MiGong {

    public static void main(String[] args) {
        //先创建一个二维数组, 模拟迷宫
        //地图
        int[][] map = new int[8][7];
        //使用 1 表示墙

        miGongMapInit(map);//初始化迷宫地图,设置墙体

        //输出地图
        System.out.println("地图情况: ");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + "\t");
            }
            System.out.println();
        }

        //使用递归回溯给小球找路
        setWay(map,1,1);//从起点开始 map[1][1]             策略为: 下->右->上->左

//        setWay2(map,1,1);//从起点开始 map[1][1]         策略为: 上->右->下->左

        //输出新的地图, 小球走过, 并标识过的地图
        System.out.println("小球走过, 并标识过的地图 地图情况: ");
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                System.out.print(map[i][j] + "\t");
            }
            System.out.println();
        }

    }

    /**
     * 初始化迷宫地图, 设置墙体
     *
     * @param map 二维数组形式的 迷宫地图
     */
    public static void miGongMapInit(int[][] map) {
        //上下全部置为 1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;//通过循环 将 迷宫地图(二维数组) 的第一行 全部置为 1 表示墙
            map[7][i] = 1;//通过循环 将 迷宫地图(二维数组) 的第八行 全部置为 1 表示墙
        }

        //左右全部置为 1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;//通过循环 将 迷宫地图(二维数组) 的第一列 全部置为 1 表示墙
            map[i][6] = 1;//通过循环 将 迷宫地图(二维数组) 的第七列 全部置为 1 表示墙
        }

        //设置墙
        map[3][1] = 1;
        map[3][2] = 1;
    }

    /**
     * 使用 递归回溯 来给小球找路
     * 说明:
     * 1. map 表示地图
     * 2. i j 表示从地图的哪个位置开始出发(1,1) 迷宫的起始地址为 (1,1)
     * 3. 如果小球能到map[6][5]位置, 则说明通路找到. 迷宫的终点地址为 (6,5)
     * 4. 约定: 当map[i][j] 为 0 表示, 该点没有走过; 为 1 表示, 该点是墙不能走; 为 2 表示, 通路可以走; 为 3 表示, 该点已经走过,但是走不通
     * 5. 在走迷宫时, 需要确定一个策略(方法):  先走下面(如下面走不通再走右面) -> 右(如右面也走不通再走上面) -> 上 -> 左 , 如果该点走不通, 再回溯
     *
     * 虽然看的不太明白, 但是看出个大概: 大概就是 小球是走什么路线到终点, 是根据我们定义的策略来的, 比如 是先走上面,上面走不通再走右面...
     * 就是 小球从起点开始 按照 策略走 先判断是否可以往下走, 如果可以往下走就一直往下走, 如果下面不能走了, 就会判断是否可以往右走, 然后
     * 一直 按照策略 下 右 上 左 开始依次判断依次走, 如果发现下右上左 都走不通了 即将该点标为3意思是死路, 然后由于返回的是false,就
     * 不会再进入if了, 就会依次返回之前调用该方法的方法处(回溯), 如果之前是 往下走 if (setWay(map, i + 1, j)) { 然后走不通,
     * 依次返回的, 那么现在就会返回到 判断是否 可以往下走的if那里 if (setWay(map, i + 1, j)) { 那么该if就为false 就不会进入该if了, 然后
     * 就会再次判断 是否可以往右走 else if (setWay(map, i, j + 1)) { 如果往右走可以走通, 就会再次调用该方法, 判断往右走了之前的位置,
     * 是否可以往上下左右走, 如果可以, 则依次调用, 一直走, 直到走到终点, map[6][5] == 2  就会依次返回,直到退出递归, 如果往右走的之后的位置
     * 不可以往上下左右走, 那么就将该点 map[i][j] = 3; 置为3 表示死路, 然后返回到之前判断是否可以往右走的时候的方法处, 然后判断是否可以往上走
     * 如此一直递归, 直到 map[6][5] == 2 然后判断到 map[6][5] == 2的那个方法就会返回true, 然后之前调用该方法的方法也会返回true, 然后
     * 然后之前调用该方法的方法的方法也会返回true, 然后疯狂返回, 直到最开始那层, 然后地图上的路线也标出来了.
     *
     * 注意:
     *  在递归中 虽然每个局部变量 如果 该方法中的 int i 与 int j 是相互独立的, 在每个方法中 i,j都是独立 是不一样的
     *  但是 方法中使用的是引用类型变量(比如数组), 就会共享该引用类型的数据 如此处的 方法参数 int[][] map 在每次递归中, 都是这个 map, 因为
     *      每次递归时 传入的引用类型的参数 都是之前最开始调用时传入的那个对象  对于引用对象来说, 它们无论是几个变量名, 都指向同一个对象.
     *
     * @param map 二维数组形式的 迷宫地图
     * @param i   从哪个位置开始找
     * @param j   从哪个位置开始找
     * @return 如果找到通路, 就返回true , 否则返回false
     */
    public static boolean setWay(int[][] map, int i, int j) {
        if (map[6][5] == 2) { //通路(终点)已经找到
            return true;
        } else {//终点还没有找到

            if (map[i][j] == 0) {//如果当前这个点还没有走过
                //按照策略(方法)走 下->右->上->左
                map[i][j] = 2; //假定该点是可以走通.
                if (setWay(map, i + 1, j)) {//先向下走
                    return true;
                } else if (setWay(map, i, j + 1)) {//上面判断了向下走不行, 这里就判断是否可以向右走
                    return true;
                } else if (setWay(map, i - 1, j)) {//判断是否可以向上走
                    return true;
                } else if (setWay(map, i, j - 1)) {//判断是否可以向左
                    return true;
                } else {//如果上下左右都走不通
                    //说明该点是走不通的, 是死路
                    map[i][j] = 3;
                }

            } else { //如果map[i][j] != 0 , 那么可能是 1, 2, 3
                //如果map[i][j] == 1, 则说明 该点是墙 不能走, 返回false
                //如果map[i][j] == 2, 则说明 该点已经走过了, 可以走, 就不要反复走了, 返回false
                //如果map[i][j] == 3, 则说明 该点已经走过了, 但走不通, 返回false
                return false;
            }
        }


        return false;
    }

    //修改策略为 上->右->下->左
    public static boolean setWay2(int[][] map, int i, int j) {
        if (map[6][5] == 2) { //通路(终点)已经找到
            return true;
        } else {//终点还没有找到

            if (map[i][j] == 0) {//如果当前这个点还没有走过
                //按照策略(方法)走 上->右->下->左
                map[i][j] = 2; //假定该点是可以走通.
                if (setWay2(map, i - 1, j)) {//先向上走
                    return true;
                } else if (setWay2(map, i, j + 1)) {//上面判断了向上走不行, 这里就判断是否可以向右走
                    return true;
                } else if (setWay2(map, i + 1, j)) {//判断是否可以向下走
                    return true;
                } else if (setWay2(map, i, j - 1)) {//判断是否可以向左
                    return true;
                } else {//如果上下左右都走不通
                    //说明该点是走不通的, 是死路
                    map[i][j] = 3;
                }

            } else { //如果map[i][j] != 0 , 那么可能是 1, 2, 3
                //如果map[i][j] == 1, 则说明 该点是墙 不能走, 返回false
                //如果map[i][j] == 2, 则说明 该点已经走过了, 可以走, 就不要反复走了, 返回false
                //如果map[i][j] == 3, 则说明 该点已经走过了, 但走不通, 返回false
                return false;
            }
        }


        return false;
    }

}
