package com.us.example.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Retention: 定义注解的保留策略，注释类型的注释要保留
@Retention(RetentionPolicy.RUNTIME)
//@Target说明了Annotation所修饰的对象范围：Annotation可被用于 packages、types（类、接口、枚举、Annotation类型）、类型成员（方法、构造方法、成员变量、枚举值）、方法参数和本地变量（如循环变量、catch参数）。在Annotation类型的声明中使用了target可更加明晰其修饰的目标。
//作用：用于描述注解的使用范围（即：被描述的注解可以用在什么地方）
//取值(ElementType)有：
//CONSTRUCTOR:用于描述构造器
//2.FIELD:用于描述域
//3.LOCAL_VARIABLE:用于描述局部变量
//4.METHOD:用于描述方法
//5.PACKAGE:用于描述包
//6.PARAMETER:用于描述参数
//7.TYPE:用于描述类、接口(包括注解类型) 或enum声明
@Target(ElementType.TYPE)
public @interface MyBatisRepository {

}
