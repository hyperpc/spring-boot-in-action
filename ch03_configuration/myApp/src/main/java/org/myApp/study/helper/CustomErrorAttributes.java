package org.myApp.study.helper;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
//import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
//import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;


@Component
public class CustomErrorAttributes extends DefaultErrorAttributes  {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest request, ErrorAttributeOptions options){
        Map<String, Object> attrs = super.getErrorAttributes(request, options);
        attrs.put("foo", "bar");
        return attrs;
    }
}
