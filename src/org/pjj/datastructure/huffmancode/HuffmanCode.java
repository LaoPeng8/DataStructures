package org.pjj.datastructure.huffmancode;

import java.io.*;
import java.util.*;

/**
 * 赫夫曼编码
 * 生成的赫夫曼编码表为: {32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010, 107=1111, 108=000, 111=0011}
 *
 * @author PengJiaJun
 * @Date 2020/8/17 10:39
 */
public class HuffmanCode {
    public static void main(String[] args) {
/*
        String content = "i like like like java do you like a java";
        byte[] contentBytes = content.getBytes();
        System.out.println("contentBytes="+Arrays.toString(contentBytes)); //length = 40

        List<Node> nodes = getNodes(contentBytes);
        System.out.println("nodes="+nodes);

        Node huffmanTree = createHuffmanTree(nodes);
        huffmanTree.preOrder();

        Map<Byte, String> huffmanCodes = getCodes(huffmanTree);
        System.out.println("生成的赫夫曼编码表为: "+huffmanCodes);

        byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodes);
        System.out.println("huffmanCodeBytes="+Arrays.toString(huffmanCodeBytes)); //length = 17
*/
/*
        String content = "i like like like java do you like a java";
        byte[] huffmanZip = huffmanZip(content.getBytes());
        System.out.println(Arrays.toString(huffmanZip));

        byte[] decode = decode(huffmanZip, huffmanCodes);
        System.out.println(new String(decode, 0, decode.length));
 */

        //测试压缩文件
//        zipFile("D:\\oneTwoThree.jpg","D:/oneTwoThree.zip");
//        zipFile("D:\\x.txt","D:/x.zip");
//        zipFile("D:\\huffmanCode\\英雄时刻.avi","D:\\huffmanCode\\英雄时刻.zip");
//        zipFile("D:\\huffmanCode\\music.mp3","D:\\huffmanCode\\music.zip");
//        zipFile("D:\\huffmanCode\\sun.bmp","D:\\huffmanCode\\sun.zip");

        //测试解压文件
        unZipFile("D:\\huffmanCode\\music.zip","D:\\huffmanCode\\music22222.mp3");


    }

    /**
     * 压缩文件
     *
     * @param srcFile 需要压缩的文件 绝对路径
     * @param dstFile 压缩后的文件, 存储在哪里的路径 (绝对路径)
     */
    public static void zipFile(String srcFile, String dstFile) {
        //创建输出 输出流
        InputStream bis = null;
        ObjectOutput oos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(srcFile));
            oos = new ObjectOutputStream(new FileOutputStream(dstFile));

            //创建一个和源文件一样大小的的byte[]
            byte[] byt = new byte[bis.available()];//最后输出到文件的数组

            byte[] temp = new byte[1024 * 1024];//缓存数组 1M 1兆的读取
            int len = -1;
            int i = 0;//记录byt的下标
            while ((len = bis.read(temp)) != -1) {
                for (int j = 0; j < len; j++) {//将读到temp中的数据 放入byt
                    byt[i++] = temp[j];
                }
            }
            //while结束后 就将原文件 读取完毕了 全部存储到 byt[] 字节数组中了

            byte[] huffmanBytes = huffmanZip(byt);//将 源文件的byte数组 进行 赫夫曼压缩 直接返回压缩后的数组

            //把 赫夫曼编码后的的字节数组写入压缩文件 (这里是用的object流, 直接将 这两个对象 序列化, 读取时直接反序列化即可)
            //(我不知道用其他流行不行, 用其他流,写入方便, 读取就不知道什么读取了, 因为要先读取 赫夫曼编码表,再根据表 解压 文件)
            oos.writeObject(huffmanBytes);//先将 赫夫曼编码后的字节数组 写入 压缩文件
            oos.writeObject(huffmanCodes);//再将 对应的赫夫曼编码表 写入 压缩文件
            //注意读取时(反序列化时) 要按照写入时的顺序 读取
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param zipFile 准备解压的文件 (绝对路径)
     * @param dstFile 将文件解压到那个路径 (绝对路径)
     */
    public static void unZipFile(String zipFile,String dstFile){
        //创建文件输入流 输出流
        ObjectInputStream ois = null;
        BufferedOutputStream bos = null;
        byte[] huffmanBytes = null;
        Map<Byte,String> huffmanCodes = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(zipFile));
            bos = new BufferedOutputStream(new FileOutputStream(dstFile));

            Object huffmanBytesObj = ois.readObject();//之前经过赫夫曼编码 压缩后的 原始文件
            Object huffmanCodesObj = ois.readObject(); //之前存入文件的 赫夫曼编码表

            if(huffmanBytesObj instanceof byte[]){
                huffmanBytes = (byte[]) huffmanBytesObj;
            }
            if(huffmanCodesObj instanceof Map){
                huffmanCodes = (Map<Byte,String>)huffmanCodesObj;
            }

            byte[] OriginalFile = decode(huffmanBytes, huffmanCodes);//解压后的原文件

            bos.write(OriginalFile,0,OriginalFile.length);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    /**
     * 将一个字符数组, 按每个字节已经字节在字节数组中 出现的次数, 生成N个节点, 节点的权值为 该字节出现次数
     * 接收一个字节数组, 返回一个List, List中存的是 每个字节已经字节在字节数组中 出现的次数
     *
     * @param bytes
     * @return
     */
    private static List<Node> getNodes(byte[] bytes) {
        ArrayList<Node> nodes = new ArrayList<>();

        //遍历 bytes , 统计每个byte出现的次数  --> map[key,value]
        HashMap<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);//从map中获取 b
            if (count == null) { //如果没有获取 b 对应的 value, 则说明b没有 存储在map中
                counts.put(b, 1);
            } else {//如果获取到了 b 对应的value, 则说明 b 已经存在 map中了, 然后再次以b为key,value+1为value存放进map, 覆盖上次的 b
                counts.put(b, count + 1);
            }
        }

        //把每个 kv对 转为一个Node对象 并加入到nodes集合
        Set<Byte> keys = counts.keySet();
        for (Byte key : keys) {
            Integer value = counts.get(key);
            nodes.add(new Node(key, value));
        }
        return nodes;
    }

    //可以通过List 创建对应的赫夫曼树
    private static Node createHuffmanTree(List<Node> list) {

        while (list.size() > 1) {

            //排序 从小到大 (根据权值)
            Collections.sort(list);
            //取出权值最小的两个节点
            Node leftNode = list.get(0);
            Node rightNode = list.get(1);
            //创建新的二叉树
            Node parent = new Node(null, leftNode.weight + rightNode.weight);
            parent.leftNode = leftNode;
            parent.rightNode = rightNode;
            //删除之前取的出两节点
            list.remove(leftNode);
            list.remove(rightNode);
            //将新的二叉树加入list
            list.add(parent);
        }
        return list.get(0);
    }

    //根据赫夫曼树生成对应的赫夫曼编码
    //思路:
    //1. 将赫夫曼编码表放在Map<Byte,String>中, 形式 32->01, 97->100(a->100)
    private static Map<Byte, String> huffmanCodes = new HashMap<>();

    private static Map<Byte, String> getCodes(Node root) {
        if (root == null) {
            return null;
        } else {
            getCodes(root, "", new StringBuilder());
            return huffmanCodes;
        }
    }

    /**
     * 将 传入的node节点的 所有叶子节点的赫夫曼编码得到, 并放入到 huffmanCodes
     *
     * @param node          赫夫曼树的根节点
     * @param code          路径: 左子节点是0, 右子节点是1
     * @param stringBuilder 用于拼接路径
     */
    private static void getCodes(Node node, String code, StringBuilder stringBuilder) {
        //这里用一个新的StringBuilder对象是为了, 某个节点遍历完毕后, 不能继续向下递归了, 要返回上一层时, stringBuilder2都是
        //代表本层节点的路径的,  不知道为什么 直接中参数中的stringBuilder 往下递归 值变换后, 返回上一层 值还是修改之后的值, 并不是当前节点的路径
        //(知道了, 因为StringBuilder是个对象类型, 无论在递归第几层处理后,返回上一层,都是被处理后的值,因为 相对于处理的都是最开始传入方法中
        //的那个StringBuilder 所以就算是递归返回来后, 这个StringBuilder并不会 还原到上一次的值)
        //使用stringBuilder2即 每次递归都会创建一个 stringBuilder2, 代表当前节点的路径, 往下递归时, 可以通过参数继承上一个节点的路径
        //上一个节点的路径 + 上一个节点递归来到这里时 是左(0)还是右(1) 的路径, 即可得到该节点的路径, 该节点不能继续向下递归时, 返回上一次递归
        //stringBuilder2 就是 上一次递归创建的, 代表上一次递归时节点的路径, 总的来说 stringBuilder2 就是代表当前递归节点的 路径
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        stringBuilder2.append(code);

        if (node != null) {
            if (node.data == null) {//叶子节点

                getCodes(node.leftNode, "0", stringBuilder2);//向左递归
                getCodes(node.rightNode, "1", stringBuilder2);//向右递归

            } else {//非叶子节点
                huffmanCodes.put(node.data, stringBuilder2.toString());
//                sb.delete(0,sb.length());//清空
            }
        }

    }

    /**
     * 将传过来 bytes 赫夫曼编码处理(压缩)后  然后返回
     *
     * @param bytes        原始的字节数组(没有压缩)
     * @param huffmanCodes 赫夫曼编码表
     * @return 返回经过赫夫曼编码处理(压缩)后的byte[]数组
     * 举例: String content = "i like like like java do you like a java"; ==> byte[] contentBytes = content.getBytes();
     * 将 contentBytes 经过 赫夫曼编码表 处理后 就变成了  字符串 "1010100010111111110010000101111111100100....."
     * 将 字符串 "101010001..." 转为对应的 ==> byte[] huffmanCodeBytes , 即8位对应一个byte, 放入到huffmanCodeBytes
     * huffmanCodeBytes[0] = 10101000(补码) => byte  [推导 101010000 => 10101000 - 1 => 10100111(反码) => 11011000(原码) = -88]
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        //1. 利用huffmanCodes 将 bytes 转成 赫夫曼编码对应的字符串
        StringBuilder stringBuilder = new StringBuilder();
        //2. 遍历bytes数组  将 bytes数组 的每个 byte 作为key 从 赫夫曼编码表中 取出对应的 赫夫曼编码 然后拼接成字符串
        for (byte b : bytes) {
            stringBuilder.append(huffmanCodes.get(b));//根据 bytes数组 的每个 byte 作为key 从 赫夫曼编码表中 取出对应的 赫夫曼编码 然后拼接成字符串
        }

        //将 stringBuilder=>"1010100010111..." 转为 byte[]
        //统计返回 byte[] huffmanCodeBytes 长度
        int len;//记录 将"1010100010111..." 转为多长的 byte[]  8位等于1个字节
        if (stringBuilder.length() % 8 == 0) {// stringBuilder % 8 ==0 说明 stringBuilder刚好等于 多少个字节
            len = stringBuilder.length() / 8;
        } else {//// stringBuilder % 8 !=0 说明 stringBuilder 不是刚好等于多少个字符 可能是13.7个字符(java就算13个字符) 则 + 1 直接算 14个字符
            len = stringBuilder.length() / 8 + 1;
        }

        //存储压缩后的byte数组
        byte[] huffmanCodeBytes = new byte[len];
        int index = 0;//记录huffmanCodeBytes的下标
        for (int i = 0; i < stringBuilder.length(); i += 8) { //i+=8是因为  是 8bit 转为 1byte
            String strByte;
            //最后 一次 i还小于stringBuilder.length 但是 i+8大于stringBuilder.length, 就是说 stringBuilder内还有数据但是不够 8 位了
            if (i + 8 > stringBuilder.length()) {
                strByte = stringBuilder.substring(i);//从 i 截取到 字符串最后
            } else { // i + 8 还是 小于 stringBuilder.length说明 还没有到最后 还可以继续 8 位,8 位的截取, 不用担心越界
                strByte = stringBuilder.substring(i, i + 8);//每次截取 8位 第一次 0~8, 第二次 8 ~ 16, 第三次 16~24
            }
            //看源码可以得知 第二个参数 parseInt 是表示将 第一个参数 转成什么类型的整数 不写默认就是 10 就是转为十进制整数, 此时是2则转为二进制
            huffmanCodeBytes[index++] = (byte) Integer.parseInt(strByte, 2);
        }

        return huffmanCodeBytes;
    }

    /**
     * 使用一个方法将 前面的方法封装起来, 便于调用.
     * 输入一个字节数组 返回一个 使用赫夫曼编码 压缩后的 字节数组
     *
     * @param bytes 原始的字节数组
     * @return 返回 经过赫夫曼编码 处理后的 字节数组
     */
    public static byte[] huffmanZip(byte[] bytes) {
        List<Node> nodes = getNodes(bytes);//将bytes转为 N个Node 每个Node 包含一个对应的 字节, 以及一个 根据该字节出现的次数 的权值

        Node huffmanTree = createHuffmanTree(nodes);//将N个Node 根据Node的权值 创建一颗赫夫曼树

        Map<Byte, String> huffmanCodes = getCodes(huffmanTree);//根据赫夫曼树 生成对应的 赫夫曼编码表

        byte[] huffmanCodeBytes = zip(bytes, huffmanCodes);//将 输入的原始的bytes 根据 赫夫曼编码表  进行 压缩处理

        return huffmanCodeBytes;//返回经过 赫夫曼编码 处理后的 字节数组
    }


    /**
     * 将一个 byte 转成一个 二进制的字符串
     *
     * @param flag 标识是否需要补高位, 如果是true 表示需要补高位, 如果是false表示不补, 如果是最后一个字节,无需补高位
     * @param b    传入的 byte
     * @return 返回 b 对应的 二进制的字符串 (注意返回的补码)
     * 看不懂就对了, 我也看不懂.... 裂开...
     */
    private static String byteToBitString(boolean flag, byte b) {
        int temp = b;
        if (flag) {
            temp = temp | 256;
        }
        String str = Integer.toBinaryString(temp);
        if (flag || temp < 0) {
            return str.substring(str.length() - 8);
        } else {
            return str;
        }
    }

    /**
     * 输入一个 被赫夫曼编码压缩后的 字节数组, 将该 字节数组 解压, 返回一个压缩前的原始字节数组
     *
     * @param huffmanBytes 被赫夫曼编码压缩后的 字节数组
     * @param huffmanCodes 赫夫曼编码表
     * @return 返回 解压后的 字节数组
     */
    private static byte[] decode(byte[] huffmanBytes, Map<Byte, String> huffmanCodes) {

        //先得到 huffmanBytes 对应的 二进制的的字符串 "1010100010111..."
        StringBuilder stringBuilder = new StringBuilder();
        //将byte数组 转为 二进制字符串
        for (int i = 0; i < huffmanBytes.length; i++) {
            String str;
            if (i == huffmanBytes.length - 1) { //最后一位
                str = byteToBitString(false, huffmanBytes[i]);//最后一位 不需要补位
            } else {
                str = byteToBitString(true, huffmanBytes[i]);//不是最后一位 需要补位
            }
            stringBuilder.append(str);
        }

        //把二进制的 按照指定的霍夫曼编码进行解码
        //把赫夫曼编码表进行调换, 因为反向查询 a->100   100->a    就是将 key,value 从 a(key),100(value)  变成  100(key),a(value)
        Map<String, Byte> map = new HashMap<>();
        Set<Byte> bytes = huffmanCodes.keySet();
        for (byte byt : bytes) {
            String key = huffmanCodes.get(byt);
            map.put(key, byt);
        }

        //创建一个集合, 存放byte
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length(); ) {
            int count = 1;
            boolean flag = true;
            Byte b = null;

            while (flag) {
                String key = stringBuilder.substring(i, i + count);// i 不动, 让count移动, 指定匹配到这个字符
                b = map.get(key);
                if (b == null) {//说明没有匹配到
                    count++;
                } else {
                    //匹配到
                    flag = false;
                }
            }
            list.add(b);
            i += count; //i直接移动到count
        }
        //当for循环结束后, 我们list中就存放了所有的字符 "i like like like java do you like a java"
        //把list 中的数据 放入到byte[] 并返回
        byte[] byt = new byte[list.size()];
        for (int i = 0; i < byt.length; i++) {
            byt[i] = list.get(i);
        }

        return byt;
    }


}

//创建Node, 带数据 和 权值
class Node implements Comparable<Node> {
    Byte data; //存放数据本身 比如 'a' => 97
    int weight; //权值, 表示字符出现的次数
    Node leftNode;//左子节点
    Node rightNode;//右子节点

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node o) {
        //从小到大排序
        return this.weight - o.weight;
    }

    //前序遍历
    public void preOrder() {
        System.out.println(this);

        if (this.leftNode != null) {
            this.leftNode.preOrder();
        }

        if (this.rightNode != null) {
            this.rightNode.preOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }
}