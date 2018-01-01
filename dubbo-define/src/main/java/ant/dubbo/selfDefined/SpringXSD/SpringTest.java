package ant.dubbo.selfDefined.SpringXSD;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        People people = (People) context.getBean("cutesource");
        System.out.println(people.toString());
    }
}
