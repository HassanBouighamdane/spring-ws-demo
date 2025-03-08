package com.exemple.wswithspring.ws;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import static com.exemple.wswithspring.ws.BanqueSoapEndpoint.NAMESPACE_URI;

@Configuration
public class SpringWSConfig {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> servletRegistrationBean(ApplicationContext applicationContext){
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean
    public XsdSchema xsdSchema(){
        SimpleXsdSchema simpleXsdSchema = new SimpleXsdSchema();
        simpleXsdSchema.setXsd(new ClassPathResource("schema/contract.xsd"));
        return simpleXsdSchema;
    }

    @Bean("banqueWS")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema xsdSchema){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setSchema(xsdSchema);
        definition.setLocationUri("/ws");
        definition.setPortTypeName("BankPort");
        definition.setTargetNamespace(NAMESPACE_URI);
        return definition;
    }
}
