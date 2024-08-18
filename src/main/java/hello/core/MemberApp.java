package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
        
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        // 스프링 컨테이너 AppConfig 를 등록
        
        MemberService memberService = applicationContext.getBean("memberService",MemberService.class);
        // method 이름 , 타입 인수로 전달 => 스프링 컨테이너가 찾아줌

        Member member = new Member(1L,"memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("member : " + member.getName());
        System.out.println("find member : " + findMember.getName());

        System.out.println("member : " + member.toString());
        System.out.println("find member : " + findMember.toString());

    }
}
