package com.project.ecommerce.ultil;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.nimbusds.jose.util.Resource;
import com.project.ecommerce.domain.response.RestResponse;
import com.project.ecommerce.ultil.annotation.APIMessage;

import jakarta.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FormatRestResponse implements ResponseBodyAdvice<Object>{

    @Override
    @Nullable
    public Object beforeBodyWrite(
    @Nullable Object body,
    MethodParameter returnType,
    MediaType selectedContentType,
    Class selectedConverterType,
    ServerHttpRequest request,
    ServerHttpResponse response) {

       HttpServletResponse servletResponse= ((ServletServerHttpResponse) response).getServletResponse();
        int status =servletResponse.getStatus();
        RestResponse<Object> res=new RestResponse<Object>();
        res.setStatusCode(status);
        //swwager
        // String path = request.getURI().getPath();
        //     if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
        //         return body;
        //     }
        //storage
        if (body instanceof String || body instanceof Resource) {
            return body;

        }
        if (status >=400) {
            //case err
            return body;
        }else{
            // case success
            res.setData(body);
            APIMessage message = returnType.getMethodAnnotation(APIMessage.class);
            res.setMessage(message != null ? message : "CALL API SUCCESS");
        }
                return res;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // TODO Auto-generated method stub
       return true;
    }
}
