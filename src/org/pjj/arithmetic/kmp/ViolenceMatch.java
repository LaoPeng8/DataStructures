package org.pjj.arithmetic.kmp;

/**
 * 暴力匹配算法 解决 字符串匹配问题
 * @author PengJiaJun
 * @Date 2020/8/28 15:51
 */
public class ViolenceMatch {
 
    public static void main(String[] args) {
        String str1 = "硅硅谷 尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
        String str2 = "尚硅谷你尚硅你";
        int i = violenceMatch(str1, str2);//自己写的
        System.out.println(str2+"-->第一次出现的位置为: "+i);

        i = violenceMatch2(str1,str2);//照老师的
        System.out.println(str2+"-->第一次出现的位置为: "+i);


    }

    /**
     * 暴力匹配算法  实现匹配字符串问题  看str1 是否包含 str2
     *
     * 如果用暴力匹配的思路，并假设现在str1匹配到 i 位置，子串str2匹配到 j 位置，则有:
     * 如果当前字符匹配成功（即str1[i] == str2[j]），则i++，j++，继续匹配下一个字符
     * 如果失配（即str1[i]! = str2[j]），令i = i - (j - 1)，j = 0。相当于每次匹配失败时，i 回溯，j 被置为0。  这里我是根据自己的想法 每轮用ii与j比较  匹配失败后 ii=i++; j=0; 也相当于 i回溯
     * 用暴力方法解决的话就会有大量的回溯，每次只移动一位，若是不匹配，移动到下一位接着判断，浪费了大量的时间。(不可行!)
     *
     * 没看老师写的代码 自己根据思路 写出来的
     * @param str1
     * @param str2
     * @return
     */
    public static int violenceMatch(String str1,String str2){

        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        for(int i=0;i<s1.length;i++){//遍历 s1
            int ii = i; //每轮 ii都会作为 s1的下标 来与 s2比较 ii++,j++ 然后 如果 跳出while之后 就说明匹配失败了 则for i++; ii=i; 从下一个字符开始再次比较
            int j = 0; //表示 s2的下标, 每轮都从0开始, 如跳出while之后 就说明匹配失败了 则进行下一轮比较 从i++后的位置 开始与 j 开始比较
            while(true){
                if(ii >= s1.length && j < s2.length){//s1 都匹配完了, s2还没有匹配完,显然s1是不可能包含s2的, 则返回 -1
                    return -1;
                }

                //只有 s1 没有匹配完, s2 也没有匹配完 才会继续匹配
                if(ii < s1.length && j < s2.length){ //尽管上面的 if 已经判断越界了 但是感觉不太对劲, 还是写这个 if 保险一下 防止越界
                    if(j == s2.length-1){
                        if(s1[ii] == s2[j]){//如果 s2匹配到了最后一个字符 还是和 s1 是匹配的, 则说明 s1 是 包含 s2的, 则返回 i的位置 这个位置相当于是 s2第一次出现的地方
                            return i;
                        }
                    }
                    if(s1[ii] == s2[j]){// 让s1与s2 逐个比较字符 如果一致 则继续比较下一个字符
                        ii++;
                        j++;
                    }else{//如果不一致, 则跳出当前循环, i从下一个字符 开始 再与s2 逐个比较
                        break;
                    }
                }
            }
        }
        return -1;
    }

    /**
     * 暴力匹配算法  实现匹配字符串问题  看str1 是否包含 str2
     * 思路还是一样的
     *
     * 照老师写的
     * @param str1
     * @param str2
     * @return
     */
    public static int violenceMatch2(String str1,String str2){
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();

        int s1Len = s1.length;
        int s2Len = s2.length;

        int i=0;//i索引指向s1
        int j=0;//j索引指向s2

        while(i < s1Len && j < s2Len){ //保证匹配时, 不越界

            if(s1[i] == s2[j]){//匹配ok
                i++;
                j++;
            }else{ //没有匹配成功
                //如果失配（即str1[i]! = str2[j]），令i = i - (j - 1)，j = 0。相当于每次匹配失败时，i 回溯，j 被置为0。
                i = i - (j - 1);
                j = 0;
            }
        }

        //判断是否匹配成功
        if(j == s2Len){
            return i - j; //i - j是因为 此时 i 也是指向 与 j匹配时 s1中包含的 s2的最后一个字符
            //要求是返回第一次 出现的地方 所以 i - j; 那么 i 就是指向 s1中包含的 s2的第一个字符
        }else{
            return -1;
        }

    }

}
