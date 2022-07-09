package org.pjj.datastructure.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 逆波兰计算器 使用 后缀表达式(逆波兰表达式)
 * @author PengJiaJun
 * @Date 2020/7/23 21:09
 */
public class PolandNotation {
    public static void main(String[] args) {

        //完成将一个中缀表达式转后缀表达式(逆波兰表达式)
        //1. 1 + ( ( 2 + 3 ) x 4 ) - 5   ==>   1 2 3 + 4 x + 5 -   ==>    16
        //2. 因为直接对String操作, 不方便, 因此 先将 "1+((2+3)x4)-5"  转为一个  中缀表达式对应的List
        //   即"1+((2+3)x4)-5" 转为ArrayList [1, +, (, (, 2, +, 3, ), *, 4, ), -, 5]
        //3. 将得到的中缀表达式对应的List => 后缀表达式对应的List
        String infixExpression = "1+((2+3)*4)-5";
        List<String> infixExpressionList = toInfixExpressionList(infixExpression);
        System.out.println("中缀表达式 "+infixExpression);
        System.out.println("中缀表达式对应的List: "+infixExpressionList); //ArrayList [1, +, (, (, 2, +, 3, ), *, 4, ), -, 5]
        List<String> suffixExpressionList = parseSuffixExpressionList(infixExpressionList);
        System.out.println("后缀表达式对应的List: "+suffixExpressionList); //ArrayList [1, 2, 3, +, 4, *, +, 5, -]
        System.out.println("后缀表达式 "+suffixListToSuffixExpression(suffixExpressionList)+"的值为: "+calculate(suffixExpressionList));//16



        //先定义一个 逆波兰表达式
        // (3+4)x5-6  对应的逆波兰表达式 => 3 4 + 5 x 6 -     ==> 29
        // (30+4)x5-6  对应的逆波兰表达式 => 30 4 + 5 x 6 -     ==> 164
        // 4*5-8+60+8/2  对应的逆波兰表达式 => 4 5 * 8 - 60 + 8 2 / +    ==> 76
        //为了方便, 逆波兰表达式的 数字 和 符号 使用空格隔开
//        String suffixExpression = "30 4 + 5 * 6 -";
//        String suffixExpression = "4 5 * 8 - 60 + 8 2 / +";

        //思路:
        //1. 先将 "3 4 + 5 * 6 -"  ==> 放入一个ArrayList中
        //2. 将 ArrayList 传递给一个方法 这个方法 遍历ArrayList, 配合栈 完成计算
//        List<String> list = getListString(suffixExpression);
//        System.out.println(list);
//
//        int result = calculate(list);
//        System.out.println("逆波兰表达式"+suffixExpression+"计算出的值为: "+result);
    }

    /**
     * 将一个中缀表达式, 依次将数据和运算符放入到一个ArrayList中
     * (其实和下面那个getListString()方法有点重复了...)
     * @param infixExpression 前缀表达式
     * @return 返回中缀表达式对应的 List
     */
    public static List<String> toInfixExpressionList(String infixExpression){
        //定义一个 List , 存放中缀表达式对应的内容
        List<String> list = new ArrayList<>();
        int i = 0;//索引, 用于遍历中缀表达式字符串
        String str;//对多位数进行拼接
        char c;//每遍历一个字符, 就放入c中
        do {
//            c = infixExpression.charAt(i);//charAt()是返回当前索引的字符 "my name is LaoPeng".charAt(5) 则返回 'm'  .charAt(14) 则返回 'P'
            //如果c是一个非数字, 就直接加入list中
            //48~57   是数字0~9的ASCII码, char 与 int 比较, 比较的就是ASCII码, 如果c不在48~57之间, 那么c就是个非数字
            if((c=infixExpression.charAt(i))<48 || (c=infixExpression.charAt(i))>57){
                list.add(new String(new char[]{c}));//将c转为String加入list中 (其实将char转为String 可以直接 c+""就转为String了,但是听说这样是效率最低的...)
                i++;//索引后移, 以此来遍历, 字符串
            }else{//如果是一个数字, 需要考虑多位数
                str = "";//清空str, 以免上次的结果, 影响到本次

                //如果i索引还在 该字符串内, 并且 i 是一个数字, 则将c赋值给str,同时i++, 如果i++后还满足条件则说明 上次循环的数字,与这次讯的数字,一起的
                //他们是一个多位数, 二位数或以上, 如果i++后不满足条件, 则就说明上次循环的数字 是一个单位数,就跳出循环了.
                while(i < infixExpression.length() && ((c=infixExpression.charAt(i)) >= 48 && (c=infixExpression.charAt(i)) <= 57)){
                    str += c;//拼接多位数
                    i++;
                }
                list.add(str);
            }
        } while (i<infixExpression.length());//第一次用 do while ......

        return list;
    }

    /**
     * 将中缀表达式对应的List 转为 后缀表达式对应的List
     * 具体思路可以去看我记的笔记
     * @param infixExpressionList 中缀表达式对应的List
     * @return
     */
    public static List<String> parseSuffixExpressionList(List<String> infixExpressionList){
        //定义两个栈
        Stack<String> s1 = new Stack();//符号栈
        //说明: 因为s2这个栈, 在整个转换的过程中, 没有pop的操作, 而且后面我们还需要逆序输出
        //因此比较麻烦, 这里我们就不使用Stack<String>栈了, 直接使用List<String> s2 来替代.
        //Stack<String> s2 = new Stack<>();//存储中间结果的栈
        List<String> s2 = new ArrayList<String>();//存储中间结果的List

        //遍历infixExpressionList
        for(String item : infixExpressionList){
            //如果是一个数, 则入中间结果栈, 当然我们这里用 List<String> s2 来代替这个栈, 因为这个栈并不需要取出数据, 而且最后取出来还需要逆序, 使用集合代替方便一些
            if(item.matches("\\d+")){//匹配多位数
                //如果是一个数, 则直接入中间结果栈(加入集合)
                s2.add(item);
            }else if(item.equals("(")){
                //如果 它是一个左括号 "(" 则直接入符号栈        如果是左括号“(”，则直接压入s1符号栈
                s1.push(item);
            }else if(item.equals(")")){
                //如果 它是一个右括号 ")"  如果是右括号 ")"， 则依次弹出s1符号栈 栈顶的运算符，并压入s2中间结果栈(此处为List<String> s2 代替这个中间结果栈),
                //直到遇到左括号为止，此时将这一对括号丢弃
                while(true){
                    String pop = s1.pop();//弹出s1符号栈 栈顶
                    if(pop.equals("(")){
                        //如果弹出的是 "(" 则, 弹出完毕, 跳出循环
                        break;
                    }else{
                        //如果弹出的不是 "(" 则, 将弹出的s1符号栈 栈顶元素 压入s2中间结果栈(此处为List<String> s2 代替这个中间结果栈)
                        s2.add(pop);
                    }
                }
            } else {
                //如果 即不是数字, 也不是 左括号 "(", 也不是 右括号 ")", 那么我们就当它是个 运算符 + - * /
                //遇到运算符时，比较其与s1栈项运算符的优先级:
                while(true){
                    //4.1 如果s1符号栈为空，或s1符号栈 栈顶运算符为左括号  “(”， 则直接将此运算符入栈;
                    if(s1.isEmpty() || s1.peek().equals("(")){
                        s1.push(item);
                        break;
                    }else if(Operation.getValue(item) > Operation.getValue(s1.peek())){//4.2 否则，若优先级比 s1符号栈 栈顶运算符的高，也将运算符压入s1;
                        s1.push(item);
                        break;
                    }else{//4.3 否则，将s1栈顶的运算符弹出并压入到s2中，再次转到(4.1)与s1中新的栈顶运算符相比较;
                        s2.add(s1.pop());
                    }
                }

            }
        }

        //将s1中剩佘的运算符依次弹出并加入s2
        while(s1.size() != 0){
            s2.add(s1.pop());
        }

        return s2; //注意: 因为我们用 List<String> s2 来代替这个栈(中间结果栈 Stack<String> s2) 所以 我们输出的时候 不需要逆序输出
    }

    /**
     * 将后缀表达式对应的List 转为 后缀表达式
     * @param suffixExpressionList 后缀表达式对应的List
     * @return 返回一个后缀表达式 (后缀表达式 每个元素之间 用空格隔开 方便操作)
     */
    public static String suffixListToSuffixExpression(List<String> suffixExpressionList){
        StringBuilder sb = new StringBuilder();
        for(String temp : suffixExpressionList){
            sb.append(temp+" ");
        }
        return sb.toString();
    }

    /**
     * 将一个逆波兰表达式, 依次将数据和运算符放入到一个ArrayList中
     * @param suffixExpression 逆波兰表达式
     * @return 将逆波兰表达式分割后 装入ArrayList中返回
     */
    public static List<String> getListString(String suffixExpression){
        //将 suffixExpression 分割
        String[] split = suffixExpression.split(" ");
        ArrayList<String> list = new ArrayList<>();

        //将由suffixExpression分割成的数组 加入到list中
        for(int i=0; i<split.length;i++){
            list.add(split[i]);
        }

        return list;
    }


    /**
     * 完成对逆波兰表达式的运算
     * 思路:   逆波兰表达式 3 4 + 5 * 6 -
     * 1. 从左至右扫描,将3和4压入堆栈;
     * 2. 遇到+运算符,因此弹出4和3 (4为栈项元素, 3为次顶元素) ,计算出3+4的值,得7,再将7入栈;
     * 3. 将5入栈;
     * 4. 接下来是x运算符,因此弹出5和7,计算出7x5=35,将35入栈;
     * 5. 将6入栈;
     * 6. 最后是-运算符，计算出35-6的值，即29，由此得出最终结果
     */

    public static int calculate(List<String> ls){
        //创建一个栈 , 只需要一个栈即可
        Stack<String> stack = new Stack<>();
        //遍历 ls
        for(String item : ls){
            //使用正则表达式来取出数
            if (item.matches("\\d+")){//该正则表达式 \\d+ 匹配多位数
                //入栈
                stack.push(item);
            }else{ //如果不是一个数, 那就是个运算符
                //pop出两个数, 并运算, 再入栈
                int num2 = Integer.parseInt(stack.pop());
                int num1= Integer.parseInt(stack.pop());
                int result = 0;//保存运算结果
                if(item.equals("+")){
                    result = num1+num2;
                }else if(item.equals("-")){
                    result = num1 - num2;
                }else if(item.equals("*")){
                    result = num1 * num2;
                }else if(item.equals("/")){
                    result = num1 / num2;
                } else {
                    throw new RuntimeException("逆波兰表达式的运算符有误");
                }
                //把运算结果result入栈
                stack.push(result+"");//stack中泛型是String 所以将运算结果result转为String 然后入栈
            }
        }

        //循环计算完 逆波兰表达式后 最后留着栈中的数据 就是运算结果
        return Integer.parseInt(stack.pop());//stack中泛型是String 所以出栈后 先转为Integer然后返回
    }
}

//编写一个类 Operation 可以返回一个运算符 对应的优先级
class Operation {
    private static final int ADD=1;
    private static final int SUB=1;
    private static final int MUL=2;
    private static final int DIV=2;

    public static int getValue(String operation){
        int result = 0;
        switch (operation){
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                throw new RuntimeException("不存在该运算符: "+operation);
        }
        return result;
    }


}

