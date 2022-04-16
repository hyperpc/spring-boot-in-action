package org.myApp.study;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
//import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest request, ErrorAttributeOptions options) {
    Map<String, Object> attributes = super.getErrorAttributes(request, options);
    
    attributes.put("foo", "bar");
    
    return attributes;
  }
  
}

