package org.pjj.arithmetic.kmp;

import java.util.Arrays;

/**
 * KMP算法 解决 字符串匹配问题
 * 看不懂啊...... KMP算法真是牛
 * @author PengJiaJun
 * @Date 2020/8/30 22:13
 */
public class KMPAlgorithm {

    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";

        int[] next = kmpNext(str2); // [0,1,2,0]
        System.out.println("next = "+Arrays.toString(next));

        int index = kmpSearch(str1,str2,next);
        System.out.println("index="+index);
    }


    /**
     * KMP搜索算法  看看 str1 是否包含 str2
     * @param str1
     * @param str2
     * @param next str2的部分匹配表
     * @return str1包含 str2 则返回str2在str1中第一次出现的位置, str1不包含str2则 返回-1
     */
    public static int kmpSearch(String str1,String str2,int[] next){

        //遍历  i指向str1, j指向str2
        for(int i=0,j=0; i<str1.length();i++){

            //需要处理 str1.charAt(i) != str2.charAt(j), 如不等 j需要置为0, i需要移动到下一次比较的位置(暴力匹配是直接将i后移一位)
            //KMP的核心点
            while(j > 0 && str1.charAt(i) != str2.charAt(j)){
                j = next[j-1];
//                i = i + j - next[j-1];
//                j=0;
            }


            if(str1.charAt(i) == str2.charAt(j)){//如果相等 i++,j++继续比较下一个字符(j++是在这里,i++是在for循环上)
                j++;
            }

            if(j == str2.length()){ //找到了
                // 就str1="BBC AB" str2="BBC"来说  i=0,j=0开始找 [i]==[j],j++,i++然后 找到时 j已经++了,i=3, i需要到下一次for循环时才会++,所以i=2
                // i=2,j=3
                return i - j + 1;
            }

        }
        return -1;
    }


    /**
     * 获取到一个字符串(子串) 的部分匹配值 (部分匹配表)
     * 部分匹配值就是 一个字符串 的前缀和后缀的 最长的公共前缀后缀的长度  如: AAAB 前缀就是 A AA AAA 后缀就是 AAB AB B 最长的公共前后缀 没有 就是 0
     * 部分匹配表就是  每个字符串的 部分匹配值 合起来  如:  AAAB  就是 A的部分匹配值为 0  AA的部分匹配值为 1  AAA就是2   AAAB是0
     * 那么对应的部分匹配表就是     A   A   A   B
     *                            0   1   2   0
     * @param dest
     * @return
     */
    public static int[] kmpNext(String dest){
        //创建一个next 数组保存部分匹配表, 因为是字符串dest的部分匹配表, 所以长度应为 dest的长度
        int[] next = new int[dest.length()];
        next[0] = 0; //如果 字符串是 "A" 那么对应的部分匹配表就是 0 因为, "A"并没有前缀和后缀, 也就是说 不管什么字符串 [0]必是0

        //遍历该字符串  i为什么从 1开始? 因为 上面已经设置了 [0]必是 0 就 直接从[1]开始求它的部分匹配值
        for(int i=1,j=0; i<dest.length(); i++){
            //当dest.charAt(i) != dest.charAt(j) 我们需要从next[j-1]获取新的j
            //直到我们发现有 dest.charAt(i) == dest.charAt(j)成立时才退出
            //这是KMP算法的的核心点
            while(j > 0 && dest.charAt(i) != dest.charAt(j)){//不知道为什么这样...
                j = next[j-1];
            }

            //当dest.charAt(i) == dest.charAt(j)满足时, 部分匹配值就是+1
            if(dest.charAt(i) == dest.charAt(j)){//但是我不知道为什么 这样== 之后 就j++  然后j就是部分匹配值了...
                //好像是因为 i=1,j=0 [i]==[j] 就说明 是两个一样的字符 AA 或者 BB 那么 他们的部分匹配值就是 1 所以j++了
                //i=2,j=1 [i]==[j] 就说明 是三个一样的字符 AAA或者BBB 那么他们的部分匹配值就是 2 所以j++了(因为之前j已经是1了所以j++后就是2了)
                j++;
            }
            next[i] = j;//因为是在求 [i]的部分匹配值 所以是赋给[i]
        }
        return next;
    }

}
