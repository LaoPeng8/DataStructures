package org.pjj.arithmetic.greedy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 贪心算法 解决 集合覆盖问题
 *
 * @author PengJiaJun
 * @Date 2020/9/1 22:03
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        //创建广播电台, 放入Map中
        HashMap<String, HashSet<String>> broadcasts = new HashMap<>();
        //将各个电台放入broadcasts
        HashSet<String> hashSet1 = new HashSet<>();//电台 k1 覆盖的城市
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");

        HashSet<String> hashSet2 = new HashSet<>();//电台 k2 覆盖的城市
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");

        HashSet<String> hashSet3 = new HashSet<>();//电台 k3 覆盖的城市
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");

        HashSet<String> hashSet4 = new HashSet<>();//电台 k4 覆盖的城市
        hashSet4.add("上海");
        hashSet4.add("天津");

        HashSet<String> hashSet5 = new HashSet<>();//电台 k5 覆盖的城市
        hashSet5.add("杭州");
        hashSet5.add("大连");

        //加入到map  将每个电台覆盖的城市一一对应
        broadcasts.put("k1", hashSet1);
        broadcasts.put("k2", hashSet2);
        broadcasts.put("k3", hashSet3);
        broadcasts.put("k4", hashSet4);
        broadcasts.put("k5", hashSet5);

        //保存着 所有还未被电台覆盖的城市
        HashSet<String> allAreas = new HashSet<>();

        //将所有城市加入 allAreas 因为刚开始 所有的城市都还未被电台覆盖
        Set<String> keys = broadcasts.keySet();
        for (String key : keys) {
            HashSet<String> strings = broadcasts.get(key);
            for (String str : strings) {
                allAreas.add(str);
            }
        }

        //创建一个ArrayList, 存放选择的电台集合
        ArrayList<String> selects = new ArrayList<>();

        //定义一个临时的集合, 保存在遍历过程中, 存放遍历过程中电台覆盖的地区 和 当前还未覆盖的地区的一个交集
        //也就是 排除那些已经被覆盖的地方  每个电台 还可以覆盖多少个地区
        HashSet<String> tempSet = new HashSet<>();

        //定义一个maxKey , 保存在一次遍历过程中 能够覆盖最多 "未覆盖地区" 对应的电台的key
        //如果maxKey 不为null, 则会加入到 selects
        String maxKey = null;
        //如果allAreas不为0则说明 还有未覆盖的地区, 则继续寻找哪个电台 能够覆盖最多 "未覆盖地区"
        //然后将选择的电台加入selects,然后将加入的电台覆盖的地区从allAreas中移除, 因为allAreas是保存未覆盖地区的
        while(allAreas.size() != 0){
            //遍历 broadcasts (遍历所有电台)
            for (String key : broadcasts.keySet()) {

                tempSet.clear();//清空tempSet, 因为下一次循环tempSet要保存 当前key(电台) 可以覆盖的地区 而如果不清空 则 tempSet会保存 从开始循环一直到循环结束 中间所有电台可以覆盖的地区....

                //当前key(电台) 可以覆盖的地区
                HashSet<String> areas = broadcasts.get(key);
                tempSet.addAll(areas);
                tempSet.retainAll(allAreas);//当前key(电台) 可以覆盖的地区  与  还未覆盖的地区 的交集(也就是当前电台可以覆盖多少个地区, 然后循环遍历找出本轮可以覆盖最多未覆盖地区的电台)
                //retainAll方法讲解:  A.retainAll(B); 取A容器和容器B中都包含的元素(交集), 移除非交集元素

                /**  tempSet.size()表示当前电台可以覆盖的 未覆盖地区的 个数
                 *   tempSet.size() > broadcasts.get(maxKey).size() 就是 当前电台可以覆盖未覆盖地区的 个数 大于 maxKey对应电台可以覆盖的地区
                 *
                 *   如果当前电台可以覆盖1及以上个未覆盖地区 同时 (maxKey == null(没有能够覆盖最多 "未覆盖地区"的电台) 则 maxKey = key
                 *   或者 tempSet.size()的长度 大于 maxKey电台可以覆盖的长度 则 maxKey = key )
                 *
                 *   tempSet.size() > broadcasts.get(maxKey).size() 就体现出贪心算法的特点, 每次都选择最优的.
                 *
                 *   感觉tempSet.size() > broadcasts.get(maxKey).size()这个地方是有点问题的
                 *   因为tempSet.size()是表示当前电台可以覆盖的 未覆盖地区的 个数  而
                 *   broadcasts.get(maxKey).size()中的 maxKey是表示 在一次遍历过程中 能够覆盖最多 "未覆盖地区" 对应的电台的key
                 *   但是这样 broadcasts.get(maxKey).size() 只是取出了这个key 可以覆盖多少个地区, 而不是 可以覆盖多少个 未覆盖地区
                 *
                 */
                if (tempSet.size() > 0 && (maxKey == null || tempSet.size() > broadcasts.get(maxKey).size())) {
                    maxKey = key;
                }
            }
            // maxKey != null , 则将maxKey 加入 selects
            if (maxKey != null){
                selects.add(maxKey);
                //将maxKey指向的广播电台覆盖的地区从 allAreas中去掉, 因为allAreas中存放的是未覆盖的地区, 而这些地区现在已经由刚刚加入的maxKey电台覆盖了
                allAreas.removeAll(broadcasts.get(maxKey));
                //removeAll方法讲解: A.removeAll(B); 移除A容器与B容器中都包含的元素

                maxKey = null;//清除maxKey 放置下次比较maxKey maxKey其实是上一次的值 从而出现问题
            }
        }
        System.out.println("得到的选择结果是" + selects);
    }


}
