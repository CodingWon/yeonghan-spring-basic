package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemoryMemberRepositroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    // DIP 를 지키고 있다
    private final MemberRepository memberRepository ;
    private final DiscountPolicy discountPolicy ;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) { //1. 주문 생성

        Member member = memberRepository.findById(memberId);                  //2. 회원 조회

        //  DiscountPolicy 에게 역할을 위임(단일 책임 원칙)
        int discountPrice = discountPolicy.discount(member, itemPrice);       //3. 할인 적용


        return new Order(memberId, itemName, itemPrice, discountPrice );      //4. 주문 결과 반환
    }

    // 테스트용
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
