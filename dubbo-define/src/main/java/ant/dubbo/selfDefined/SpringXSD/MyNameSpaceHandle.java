package ant.dubbo.selfDefined.SpringXSD;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MyNameSpaceHandle extends NamespaceHandlerSupport {
    public void init() {
        registerBeanDefinitionParser("people",new PeopleBeanDefinitionParser());
    }
}
