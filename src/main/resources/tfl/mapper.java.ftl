package ${PACKAGE_NAME};

<#list packages as package>
    import ${package};
</#list>

public interface ${CLASS_NAME} {

<#-- 生成接口方法 -->
<#list methods as method>
    ${method.type} ${method.name}(
    <#list method.params as param>
        ${param.type} ${param.name},
    </#list>
    );
</#list>

}