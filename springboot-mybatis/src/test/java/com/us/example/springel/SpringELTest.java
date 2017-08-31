package com.us.example.springel;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.List;

/**
 * @Author:Dave
 * @Description:
 * @Date: 2017/8/31 9:52
 */
public class SpringELTest {

    public static void main(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();

        //literal expressions
//        Expression exp = parser.parseExpression("'Hello world'");
//        String msg1 = exp.getValue(String.class);
//        System.out.println(msg1);
//
//        //method invocation
//        Expression exp2 = parser.parseExpression("'Hello wold'.length()");
//        int msg2 = (Integer) exp2.getValue();
//        System.out.println(msg2);
//
//        //mathematical operators
//        Expression exp3 = parser.parseExpression("100 * 2");
//        int msg3 = (Integer) exp3.getValue();
//        System.out.println(msg3);
//
//        //create a item object
//        Item item = new Item("yibai",100);
//        //test El with item object
//        StandardEvaluationContext itemContext = new StandardEvaluationContext(item);
//
//        //display the value of item.name property
//        String a = "name";
//        Expression exp4 = parser.parseExpression(a);
//        String msg4 = exp4.getValue(itemContext,String.class);
//        System.out.println(msg4);
//
//        //test if item.name == 'yibai'
//        Expression exp5 = parser.parseExpression("name == 'yibai'");
//        boolean msg5 = exp5.getValue(itemContext,Boolean.class);
//        System.out.println(msg5);
//
//        Expression exp6 = parser.parseExpression("name");
//        String name = (String)exp6.getValue(item);
//        System.out.println(name);
//
//        SpelParserConfiguration config = new SpelParserConfiguration(true,true);
//        ExpressionParser parser1 = new SpelExpressionParser(config);
//        Expression expression = parser1.parseExpression("list[3]");
//
//        Demo demo = new Demo();
//        Object o = expression.getValue(demo);
//        System.out.println(o);
        double avogadrosNumber = (Double) parser.parseExpression("6.0221415E+23").getValue();
        System.out.println(avogadrosNumber);

        int maxValue = (Integer) parser.parseExpression("0x7FFFFFFF").getValue();
        System.out.println(maxValue);

        boolean trueValue = (Boolean) parser.parseExpression("true").getValue();
        System.out.println(trueValue);

        Object nullValue = parser.parseExpression("null").getValue();
        System.out.println(nullValue);
    }
}
class Demo{
    public List<String> list;
}