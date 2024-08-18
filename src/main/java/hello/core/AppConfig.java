package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepositroy;

import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 나중에 기능이 변경시 AppConfig 만 변경하면 된다.
@Configuration
public class AppConfig {

    // 역할 : memberService
    // 의존 : memberRepository
    @Bean   // 스프링 컨테이너 등록 된다.
    public MemberService memberService (){
        return new MemberServiceImpl(memberRepository());     // 생성자 주입
    }

    // 역할 : memberRepository
    @Bean   // 스프링 컨테이너 등록 된다.
    public MemberRepository memberRepository() {
        return new MemoryMemberRepositroy();
    }

    // 역할 : orderService
    // 의존 : memberRepository , discountPolicy
    @Bean   // 스프링 컨테이너 등록 된다.
    public OrderService orderService(){
        return new OrderServiceImpl(memberRepository(), discountPolicy());

    }

    // 역할 : discountPolicy
    @Bean   // 스프링 컨테이너 등록 된다.
    public   DiscountPolicy discountPolicy() {
      //  return new FixDiscountPolicy();
        return new RateDiscountPolicy();    // AppConfig 만 변경 하면 기능을 바꿀 수 있다.
    }
}
