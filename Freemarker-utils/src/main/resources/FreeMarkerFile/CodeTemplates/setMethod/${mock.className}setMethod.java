public static ${mock.className}buildRequest(Request request){
        ${mock.className}${mock.className?uncap_first}=new ${mock.className}();
<#list mock.methodsName as method>
        ${mock.className?uncap_first}.method("");
</#list>
        return ${mock.className?uncap_first};
        }