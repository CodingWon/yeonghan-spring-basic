package hello.core.member;

/*
 *  회원 도메인 설계의 문제점
 *   - 다른 저장소로 변경할 때 OCP 원칙을 잘 준수 할까?
 *   - DIP를 잘 지키고 있을 까?
 *   - 의존 관계가 인터페이스 뿐만 아니라 구현 까지 모두 의존하는 문제점이 있음
 *
 *   - 현재 추상화에도 의존하고 구체화 에도 의존하고 있다.
 * */

public class MemberServiceImpl implements  MemberService{
    // DIP 를 지키고 있다
    private final MemberRepository memberRepository ;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}
