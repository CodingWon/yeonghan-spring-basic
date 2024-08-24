package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(     // @Component 애너테이션이 붙은 클래스를 스캔에서 스프링 빈으로 등록한다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // @Configuration 들어간 클래스는 제외(기존 코드를 유지하기 위해서)
)
public class AutoAppConfig {

}
