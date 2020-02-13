package com.simon.wang.soap;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/CNEFundamental/*");
    }

    @Bean(name = "mapper")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema fixtureDataSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("GPort_1");
        wsdl11Definition.setLocationUri("/CNEFundamental");
        wsdl11Definition.setTargetNamespace("http://www.simon.wang.com/probation/soap");
        wsdl11Definition.setSchema(fixtureDataSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema fixtureDataSchema() {
        //return new SimpleXsdSchema(new ClassPathResource("xsd/CNEFundamental_1_FixtureData_1.xsd"));
        return new SimpleXsdSchema(new ClassPathResource("xsd/test.xsd"));
    }
}
