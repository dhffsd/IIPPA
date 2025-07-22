package com.xymzsfxy.backend.mcp;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;

/**
 * MCP工具注册中心 - 动态发现并注册业务工具
 */
@Component
public class McpToolRegistry {
    private final Map<String, McpToolDefinition> toolMap = new HashMap<>();

    public McpToolRegistry(ApplicationContext context) {
        // 扫描所有带有@Service注解的Bean
        Map<String, Object> serviceBeans = context.getBeansWithAnnotation(org.springframework.stereotype.Service.class);

        for (Object bean : serviceBeans.values()) {
            Class<?> beanClass = bean.getClass();
            for (Method method : beanClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(McpTool.class)) {
                    McpTool toolAnnotation = method.getAnnotation(McpTool.class);
                    McpToolDefinition definition = new McpToolDefinition(
                            toolAnnotation.name(),
                            toolAnnotation.description(),
                            bean,
                            method,
                            Arrays.asList(toolAnnotation.parameters())
                    );
                    toolMap.put(toolAnnotation.name(), definition);
                }
            }
        }
    }

    /**
     * 获取所有注册的工具
     */
    public List<McpToolDefinition> getAllTools() {
        return new ArrayList<>(toolMap.values());
    }

    /**
     * 根据名称获取工具
     */
    public McpToolDefinition getTool(String name) {
        return toolMap.get(name);
    }

    /**
     * 工具定义
     */
    public static class McpToolDefinition {
        private final String name;
        private final String description;
        private final Object targetBean;
        private final Method targetMethod;
        private final List<McpParam> parameters;

        public McpToolDefinition(String name, String description, Object targetBean,
                                 Method targetMethod, List<McpParam> parameters) {
            this.name = name;
            this.description = description;
            this.targetBean = targetBean;
            this.targetMethod = targetMethod;
            this.parameters = parameters;
        }

        // 执行工具方法
        public Object invoke(Map<String, Object> params) throws Exception {
            // 构建参数数组
            Object[] args = new Object[parameters.size()];
            for (int i = 0; i < parameters.size(); i++) {
                McpParam param = parameters.get(i);
                args[i] = params.get(param.name());
            }
            return targetMethod.invoke(targetBean, args);
        }

        // Getters
        public String getName() { return name; }
        public String getDescription() { return description; }
        public List<McpParam> getParameters() { return parameters; }
    }
}