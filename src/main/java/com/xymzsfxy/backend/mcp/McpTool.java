package com.xymzsfxy.backend.mcp;

import java.lang.annotation.*;

/**
 * MCP工具注解 - 标记可被AI调用的业务方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface McpTool {
    /**
     * 工具名称
     */
    String name();

    /**
     * 工具描述
     */
    String description();

    /**
     * 参数定义
     */
    McpParam[] parameters() default {};
}

/**
 * 参数定义
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
@interface McpParam {
    String name();
    String description();
    String type() default "string";
    boolean required() default true;
}