package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepositroy;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepositroy();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) { //1. 주문 생성

        Member member = memberRepository.findById(memberId);                  //2. 회원 조회

        //  DiscountPolicy 에게 역할을 위임(단일 책임 원칙)
        int discountPrice = discountPolicy.discount(member, itemPrice);       //3. 할인 적용


        return new Order(memberId, itemName, itemPrice, discountPrice );      //4. 주문 결과 반환
    }
}
