package ${PACKAGE_NAME}

<#list packages as package>
    import ${package};
</#list>

public class ${CLASS_NAME} {

<#list attrs as attr>
    private ${attr.type} ${attr.name};
</#list>

<#list attrs as attr>
    public void set${attr.name?cap_first}(${attr.type} ${attr.name}){
        this.${attr.name} = ${attr.name};
    }

    public ${attr.type} get${attr.name?cap_first}(){
        return this.${attr.name};
    }
</#list>

}