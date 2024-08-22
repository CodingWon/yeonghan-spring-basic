package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findAllBean(){
        String[] beanDefinitionNames =  ac.getBeanDefinitionNames();
        for (String benaDefintionName : beanDefinitionNames){
            Object bean =  ac.getBean(benaDefintionName);
            System.out.println("name = " + benaDefintionName + " object : " + bean);
        }
    }


    /*
-- 스프링 자체 빈
name = org.springframework.context.annotation.internalConfigurationAnnotationProcessor object : org.springframework.context.annotation.ConfigurationClassPostProcessor@4c1f22f3
name = org.springframework.context.annotation.internalAutowiredAnnotationProcessor object : org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor@598bd2ba
name = org.springframework.context.annotation.internalCommonAnnotationProcessor object : org.springframework.context.annotation.CommonAnnotationBeanPostProcessor@5a755cc1
name = org.springframework.context.event.internalEventListenerProcessor object : org.springframework.context.event.EventListenerMethodProcessor@7ae42ce3
name = org.springframework.context.event.internalEventListenerFactory object : org.springframework.context.event.DefaultEventListenerFactory@4f5991f6
--
-- 직접 등록한 빈
name = appConfig object : hello.core.AppConfig$$SpringCGLIB$$0@484094a5
name = memberService object : hello.core.member.MemberServiceImpl@38234a38
name = memberRepository object : hello.core.member.MemoryMemberRepositroy@63fbfaeb
name = orderService object : hello.core.order.OrderServiceImpl@602e0143
name = discountPolicy object : hello.core.discount.RateDiscountPolicy@2c07545f
* */
    @Test
    @DisplayName("모든 빈 출력하기")
    void findApplicationBean(){
        String[] beanDefinitionNames =  ac.getBeanDefinitionNames();
        for (String benaDefintionName : beanDefinitionNames){
            BeanDefinition beanDefinition = ac. getBeanDefinition(benaDefintionName);

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION){
                Object bean =  ac.getBean(benaDefintionName);
                System.out.println("name = " + benaDefintionName + " object : " + bean);
            }
        }
    }
}

/*
 C:\Users\kjw\.jdks\corretto-17.0.9\bin\java.exe -ea -Didea.test.cyclic.buffer.size=1048576 "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2023.3.2\lib\idea_rt.jar=52596:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2023.3.2\bin" -Dfile.encoding=UTF-8 -classpath "C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2023.3.2\lib\idea_rt.jar;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2023.3.2\plugins\junit\lib\junit5-rt.jar;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2023.3.2\plugins\junit\lib\junit-rt.jar;D:\kjw\programing\SpringFrameWork\0_workspace\kimyounghan\1_basic\core\out\test\classes;D:\kjw\programing\SpringFrameWork\0_workspace\kimyounghan\1_basic\core\out\production\classes;D:\kjw\programing\SpringFrameWork\0_workspace\kimyounghan\1_basic\core\out\production\resources;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-test\3.3.2\7b4eb23bcd42b088b8a03088ff8e54c194f34240\spring-boot-starter-test-3.3.2.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter\3.3.2\f9377a223dab5c4afe7aead05bf6ac567cf565ab\spring-boot-starter-3.3.2.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-test-autoconfigure\3.3.2\73b7d34fbb427ba9d40ded122e55fd721e4a5bef\spring-boot-test-autoconfigure-3.3.2.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-test\3.3.2\65d63a6516482fcd70e65a3c0fa1e6ca0f3a29a3\spring-boot-test-3.3.2.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.springframework\spring-test\6.1.11\413005921c335352caa6eb2e650bc078819d814e\spring-test-6.1.11.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.springframework\spring-core\6.1.11\90fce4311852c68c1e87c65adf60db9add1a6dcb\spring-core-6.1.11.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\com.jayway.jsonpath\json-path\2.9.0\37fe2217f577b0b68b18e62c4d17a8858ecf9b69\json-path-2.9.0.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\jakarta.xml.bind\jakarta.xml.bind-api\4.0.2\6cd5a999b834b63238005b7144136379dc36cad2\jakarta.xml.bind-api-4.0.2.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\net.minidev\json-smart\2.5.1\4c11d2808d009132dfbbf947ebf37de6bf266c8e\json-smart-2.5.1.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.assertj\assertj-core\3.25.3\792b270e73aa1cfc28fa135be0b95e69ea451432\assertj-core-3.25.3.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.awaitility\awaitility\4.2.1\e56b600e0b184182ba5b2baccd2bab593a98a624\awaitility-4.2.1.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.hamcrest\hamcrest\2.2\1820c0968dba3a11a1b30669bb1f01978a91dedc\hamcrest-2.2.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.junit.jupiter\junit-jupiter\5.10.3\6686d8fbf251f9bf8ecba413cab57b9e00f9134d\junit-jupiter-5.10.3.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.mockito\mockito-junit-jupiter\5.11.0\8e658dd339f40305ed4293db25545b5df98b171b\mockito-junit-jupiter-5.11.0.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.mockito\mockito-core\5.11.0\e4069fa4f4ff2c94322cfec5f2e45341c6c70aff\mockito-core-5.11.0.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.skyscreamer\jsonassert\1.5.3\aaa43e0823d2a0e106e8754d6a9c4ab24e05e9bc\jsonassert-1.5.3.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.xmlunit\xmlunit-core\2.9.1\e5833662d9a1279a37da3ef6f62a1da29fcd68c4\xmlunit-core-2.9.1.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-autoconfigure\3.3.2\6a9ab910b00f0b504a5903e3680ac10018da6247\spring-boot-autoconfigure-3.3.2.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot\3.3.2\72a257d5518b2b8b9949a26543cdb3a4e67de5f8\spring-boot-3.3.2.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.springframework.boot\spring-boot-starter-logging\3.3.2\b685c97f3c24dc30c53ae18151a798f701f2a640\spring-boot-starter-logging-3.3.2.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\jakarta.annotation\jakarta.annotation-api\2.1.1\48b9bda22b091b1f48b13af03fe36db3be6e1ae3\jakarta.annotation-api-2.1.1.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.yaml\snakeyaml\2.2\3af797a25458550a16bf89acc8e4ab2b7f2bfce0\snakeyaml-2.2.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.springframework\spring-jcl\6.1.11\97b34c6b72c8084759e71cd09a7dad16bfb8f1da\spring-jcl-6.1.11.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\jakarta.activation\jakarta.activation-api\2.1.3\fa165bd70cda600368eee31555222776a46b881f\jakarta.activation-api-2.1.3.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\net.minidev\accessors-smart\2.5.1\19b820261eb2e7de7d5bde11d1c06e4501dd7e5f\accessors-smart-2.5.1.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\net.bytebuddy\byte-buddy\1.14.18\81e9b9a20944626e6757b5950676af901c2485\byte-buddy-1.14.18.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.junit.jupiter\junit-jupiter-params\5.10.3\4852f4e4af9074d9214213b199751f99efeab8b9\junit-jupiter-params-5.10.3.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.junit.jupiter\junit-jupiter-api\5.10.3\a22aa91d1d6c69b2020a9aeb6d095ea81132bfa5\junit-jupiter-api-5.10.3.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\net.bytebuddy\byte-buddy-agent\1.14.18\417558ea01fe9f0e8a94af28b9469d281c4e3984\byte-buddy-agent-1.14.18.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\com.vaadin.external.google\android-json\0.0.20131108.vaadin1\fa26d351fe62a6a17f5cda1287c1c6110dec413f\android-json-0.0.20131108.vaadin1.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.springframework\spring-context\6.1.11\8fc69f776333713aa26be8821af8c6355ccad8d8\spring-context-6.1.11.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\ch.qos.logback\logback-classic\1.5.6\afc75d260d838a3bddfb8f207c2805ed7d1b34f9\logback-classic-1.5.6.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.apache.logging.log4j\log4j-to-slf4j\2.23.1\425ad1eb8a39904d2830e907a324e956fb456520\log4j-to-slf4j-2.23.1.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.slf4j\jul-to-slf4j\2.0.13\a3bcd9d9dd50c71ce69f06b1fd05e40fdeff6ba5\jul-to-slf4j-2.0.13.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.ow2.asm\asm\9.6\aa205cf0a06dbd8e04ece91c0b37c3f5d567546a\asm-9.6.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.apiguardian\apiguardian-api\1.1.2\a231e0d844d2721b0fa1b238006d15c6ded6842a\apiguardian-api-1.1.2.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.junit.platform\junit-platform-commons\1.10.3\a353d42a2f13343a7cb80c5228ae66ff64495481\junit-platform-commons-1.10.3.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.opentest4j\opentest4j\1.3.0\152ea56b3a72f655d4fd677fc0ef2596c3dd5e6e\opentest4j-1.3.0.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.springframework\spring-aop\6.1.11\3503ef99b759b81f8b0b3d5260bf4842e393a581\spring-aop-6.1.11.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.springframework\spring-beans\6.1.11\266176d132ab2bbf8e69e4aa6d7f47ec746ddc9a\spring-beans-6.1.11.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.springframework\spring-expression\6.1.11\7c12cf1cd39289cfab7c5933eda9e4704a7f2799\spring-expression-6.1.11.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\io.micrometer\micrometer-observation\1.13.2\a498ee0871596f41a61679a29bc8390defba4245\micrometer-observation-1.13.2.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\ch.qos.logback\logback-core\1.5.6\41cbe874701200c5624c19e0ab50d1b88dfcc77d\logback-core-1.5.6.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.slf4j\slf4j-api\2.0.13\80229737f704b121a318bba5d5deacbcf395bc77\slf4j-api-2.0.13.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.apache.logging.log4j\log4j-api\2.23.1\9c15c29c526d9c6783049c0a77722693c66706e1\log4j-api-2.23.1.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\io.micrometer\micrometer-commons\1.13.2\b2b69f7887f9bf6f5ae42e86a67a62367056aa3c\micrometer-commons-1.13.2.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.junit.platform\junit-platform-launcher\1.10.3\2e07e6389624f3e93fb2e87aec2fdc30cc84b069\junit-platform-launcher-1.10.3.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.junit.platform\junit-platform-engine\1.10.3\365a320c3cfd47f3346625e541e424e35dc75c42\junit-platform-engine-1.10.3.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.junit.jupiter\junit-jupiter-engine\5.10.3\48c14e866bb1a87ca35d24ff068463bb202ada24\junit-jupiter-engine-5.10.3.jar;C:\Users\kjw\.gradle\caches\modules-2\files-2.1\org.objenesis\objenesis\3.3\1049c09f1de4331e8193e579448d0916d75b7631\objenesis-3.3.jar" com.intellij.rt.junit.JUnitStarter -ideVersion5 -junit5 hello.core.beanfind.ApplicationContextInfoTest,findApplicationBean
name = appConfig object : hello.core.AppConfig$$SpringCGLIB$$0@4c1f22f3
name = memberService object : hello.core.member.MemberServiceImpl@598bd2ba
name = memberRepository object : hello.core.member.MemoryMemberRepositroy@5a755cc1
name = orderService object : hello.core.order.OrderServiceImpl@7ae42ce3
name = discountPolicy object : hello.core.discount.RateDiscountPolicy@4f5991f6
* */