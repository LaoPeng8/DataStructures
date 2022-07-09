package org.pjj.datastructure.stack;

/**
 * 使用栈 完成表达式的计算, 思路啥的, 可以去笔记上看
 * 目前这个代码有两个问题:
 *      1. 只能处理 一位数的 加减乘除 如 1+3+4       10+30+40 就把不行了      已解决
 *          已解决:
 *                 处理多位数分析思路:
 *                 1. 当处理多位数时, 不能发现是一个数就直接入数栈, 因为它可能是多位数
 *                 2. 在处理数时, 需要向expression的表达式的index, 再向后看一位, 如果是数就继续进行扫描, 如果是符号才入栈
 *                 3. 因此我们需要定义一个变量 字符串, 用于拼接多位数
 *      2. 发现只是能处理一部分 表达式. 有些表达式算出来结果是错误的 如"1-2*2+1" 正确答案应为 -2  此处却算出了 -4
 *          因为  确实答案-2 是这样运算的 1-2*2+1 => 1-4+1 => -3+1 => -2
 *          而 该代码算出的是-4 是这样运算的 1-2*2+1 => 1-4+1 => 1-5 => -4
 * @author PengJiaJun
 * @Date 2020/7/22 21:33
 */
public class Calculator {
    public static void main(String[] args) {
        //根据前面老师思路, 完成表达式的运算
        String expression = "700*2*2-5+1-5+3-4"; //可以算出正确结果12   270   2790
//        String expression = "1-2*2+1"; //不可以算出正确结果

        //创建两个栈, 一个数栈(数栈存放运算的数字), 一个符号栈(符号栈存放运行符)
        ArrayStack2 numStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);

        //定义需要的相关变量
        int index = 0;//用于扫描 索引 需要计算的表达式
        char ch =' ';//将每次扫描得到的 字符 保存到ch中
        String keepNum = "";//用于拼接多位数
        int num1 = 0;//进行运算的数1
        int num2 = 0;//进行运算的数2
        int oper = 0;//进行运算的运算符
        int result = 0;//运算得出的结果

        //开始循环的扫描 expression
        while(true){
            //依次得到expression中的每一个字符
            ch = expression.substring(index, index + 1).charAt(0);
            //判断ch是什么, 然后做出相应的处理
            if(operStack.isOper(ch)){//如果是一个运算符

                //判断当前符号栈是否为空
                if(operStack.isEmpty()){
                    //如果为空, 则直接入栈
                    operStack.push(ch);
                }else{
                    //如果不为空, 则判断 当前的ch代表的运算符的优先级 是否 小于或者等于 符号栈,栈顶的运行符的优先级
                    if(operStack.priority(ch) <= operStack.priority(operStack.peek())){
                        //如果 当前的ch代表的运算符的优先级 小于或者等于 符号栈,栈顶的运行符的优先级
                        //则,从数栈中pop出两个数, 再从符号栈中pop出一个符号, 进行运算, 将得到的结果, 入数栈, 然后将ch入符号栈
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();

                        //注意是后取出的这个数 +-*/ 先取出的这个数
                        result = numStack.cal(num1, num2, oper);//运算

                        //将得到的结果入数栈
                        numStack.push(result);

                        //然后将ch入符号栈
                        operStack.push(ch);

                    }else{
                        //如果 当前的ch代表的运算符的优先级 大于 符号栈,栈顶的运行符的优先级
                        //则, 将ch代表的运算符 直接入栈(符号栈)
                        operStack.push(ch);
                    }
                }
            }else{//如果是数, 则直接入数栈
                //numStack.push(ch); 不能这样, 这样是错的, 因为 "1+3"比如扫描这个 扫描到了 '1' 是字符'1' push到数栈中 是 '1' 代表的ASCII码
                //String chInt = new String(new char[]{ch});
                //numStack.push(Integer.parseInt(chInt));// 不晓得可不可以直接强转 比如 (int)ch , 就只好将char -> String -> Integer

                //处理多位数分析思路:
                //1. 当处理多位数时, 不能发现是一个数就直接入数栈, 因为它可能是多位数
                //2. 在处理数时, 需要想expression的表达式的index, 再向后看一位, 如果是数就进行扫描, 如果是符号才入栈
                //3. 因此我们需要定义一个变量 字符串, 用于拼接多位数

                //处理多位数
                keepNum += ch;

                //如果ch已经是expression的最后一位, 就直接入栈
                if(index == expression.length()-1){
                    numStack.push(Integer.parseInt(keepNum));
                }else{//如果不是最后一位
                    //判断下一个字符是不是数字, 如果是数字, 就继续扫描, 如果不是数字, 则入栈
                    if(operStack.isOper(expression.substring(index+1,index+2).charAt(0))){
                        //如果后一位是运算符, 则入栈keepNum = "1" 或者 "123"
                        numStack.push(Integer.parseInt(keepNum));
                        keepNum = "";//将keepNum入栈后 记得将keepNum清空!!! 要不然下次拼接还保留着上次的值.
                    }

                }



            }

            //让 index + 1, 并判断是否扫描到了expression的最后.
            index++;
            if(index >= expression.length()){
                break;
            }

        }

        //当表达式扫描完毕(入栈完毕), 就顺序的从 数栈和符号栈中pop出相应的数和符号, 并运算
        while(true){
            //如果符号栈为空, 则计算到最后的结果, 数栈中只有一个数字了
            if(operStack.isEmpty()){
                break;
            }

            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            result = numStack.cal(num1, num2, oper);//运算
            numStack.push(result);
        }

        System.out.println("表达式"+expression+"的计算结果为"+numStack.pop());
    }
}

//创建一个栈, 直接使用之前创建好的 用数组实现的栈, 需要扩展一些功能
class ArrayStack2{
    private int maxSize;    //栈的大小
    private int[] stack;    //数组, 数组模拟栈,数据就放在该数组中
    private int top = -1;   //top表示为栈顶, 没有数据时top = -1

    public ArrayStack2(int maxSize){
        this.maxSize = maxSize;
        stack = new int[this.maxSize];
    }

    //栈满
    public boolean isFull(){
        //栈是由数组模拟的, 数组的大小是 new int[maxSize], 一个数组 arr[10] 虽然可以存10个数组,但是下标是0~9,最大下标是9, 下标是不可能到10的, 所以要-1
        //也就是说top 最大 不能大于 数据的最大下标 也就是栈的大小maxSize - 1
        return top == maxSize-1 ? true : false;
    }

    //栈空
    public boolean isEmpty(){
        //top == -1 就表示栈为空, 如果有一个数据top都会 是为 0.  因为top是指向栈顶的, 加入一个数据top就会+1
        return top == -1 ? true : false;
    }

    //入栈
    public void push(int value){
        //先判断栈是否满了
        if(isFull()){
            System.out.println("栈满,无法入栈");
            return;
        }
        // top++;//top指向栈顶数据, 当有数据入栈时, 先将top往后移动一格
        stack[++top] = value;//然后再将, 数据赋值到top所在地方, top还是指向栈顶
    }

    //出栈
    public int pop(){
        //先判断栈是否为空
        if(isEmpty()){
            throw new RuntimeException("栈空,无法出栈");
        }
        int value = stack[top--];//top指向栈顶数据, 当有数据出栈时, 先将top所在值, 取出
        //top--;//然后top--, top还是指向栈顶
        return value;
    }

    //可以返回栈顶, 但是,不是出栈, 只是看一眼
    public int peek(){
        //先判断栈是否为空
        if(isEmpty()){
            throw new RuntimeException("栈空,无法出栈");
        }
        return stack[top];//不是出栈, 所以top不用--, 只是返回栈顶看一眼
    }

    //显示栈所有的数据(遍历栈), 遍历时, 需要从栈顶开始显示数据
    public void list(){
        //先判断栈是否为空
        if(isEmpty()){
            throw new RuntimeException("栈空,无法出栈");
        }
        for(int i=top;i>=0;i--){
            System.out.println("stack["+stack[i]+"] ");
        }
    }

    /**
     * 返回运算符的优先级, 优先级是程序员来确定, 优先级使用数字表示, 数字越大, 则优先级越高
     * @param oper
     * @return
     */
    public int priority(int oper){
        // int 与 char 比较的 ASCII吗        比如 我记得 小写a 好像是97
        if(oper == '*' || oper == '/'){
            return 1;
        } else if(oper == '+' || oper == '-'){
            return 0;
        } else {
            return -1; //假定目前的表达式只有 + - * / 四种运算符, 如果此处判断运算符优先级时不是则四种运算符, 那么返回-1, 表示不认识
        }
    }

    /**
     * 判断是不是一个运算符
     * @param val
     * @return
     */
    public boolean isOper(char val){
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    /**
     * 计算方法  将 param1 与 param2 按照 指定的运算符 进行运算   比如 param1(5) + param2(7) = return 12
     * @param param1    参数1
     * @param param2    参数2
     * @param oper      运算符
     * @return 返回计算出的值
     */
    public int cal(int param1, int param2 ,int oper){
        int res = -1;//res 用与存放计算的结果

        switch (oper){
            case '+' :
                res = param1 + param2;//加法不在乎顺序 无论是 1+2 还是 2+1 都是等于3
                break;
            case '-':
                res = param2 - param1;//注意顺序, 是后弹出的数 - 先弹出的数 所以是 param2 - param1
                break;
            case '*':
                res = param1 * param2;//乘法不在乎顺序 无论是 2*3 还是 3*2 都是等于6
                break;
            case '/':
                res = param2 / param1;//注意顺序, 是后弹出的数 / 先弹出的数 所以是 param2 / param1
                break;
            default:
                break;
        }
        return res;
    }

}