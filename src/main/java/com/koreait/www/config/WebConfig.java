package com.koreait.www.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {RootConfig.class, SecurityConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {ServletConfigration.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    // filter 설정
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter encoding =
                new CharacterEncodingFilter("UTF-8", true);
        return new Filter[] {encoding};
    }

    // 기타 사용자 설정 -> customizeRegistration
    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        // 파일 업로드 경로 설정, 익셉션 처리 설정
        String uploadLocation = "/Users/suminhong/web_0826_hsm/_myProject/_java/_fileUpload";
        int maxFileSize = 1024 * 1024 * 10; // 10MB
        int maxReqSize = maxFileSize*3;
        int fileSizeThreshold = maxReqSize;

        MultipartConfigElement multipartConfigElement =
                new MultipartConfigElement(uploadLocation, maxFileSize, maxReqSize, fileSizeThreshold);
        registration.setMultipartConfig(multipartConfigElement);

        // 404 page
        registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }
}
