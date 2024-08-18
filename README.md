# 1. 스프링 핵심 원리 이해 1 - 예제 만들기

## 1.1 비즈니스 요구사항과 설계

- 회원

  - 회원 가입 및 조회
  - 회원 등급 : 일반과 VIP
  - 회원 데이터는 자체 DB를 구축, 외부 시스템과 연동(미확정)

- 주문과 할인 정책

  - 회원은 상품을 주문할 수 있다.

  - 회원 등급에 따라 할인 정책을 적용할 수 있다.

  - 할인 정책은 모든 VIP는 1000원을 할인해주는 고정 금액 할인을 적용(나중에 변경 될 수 있다.)

  - 할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을 미루고 싶다. 최악의 경우 할인을 적용하지 않을 수 도 있다.(미확정)

    > **이슈 사항 : 할인 정책이 언제든 변경 될 수 있다.**

## 1.2 회원 도메인 설계

- 회원 도메인 협력 관계

  - **기획자도 볼 수 있는 그림**

  - 회원  저장소가 미확정이어서 인터페이스로 분리

    a. 메모리 저장소

    b. db 저장소

    c. 외부 시스템 연동 회원 저장소

  ![스크린샷 2024-08-17 104303](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/2024-08-17-104303.png)

- 회원 클래스 다이어 그램

  - **개발자가 구체화한 그림**
  - 동적으로 구체화된 객체가 정해지므로 클래스 다이어그램 만으로는 판단하기 힘듬

  ![스크린샷 2024-08-17 104642](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/2024-08-17-104642.png)

- 회원 객체 다이어그램
  - **실제 서버가 떴을 때 의존관계 표현**
  - 객체간에 참조 관계 표현(의존관계)![스크린샷 2024-08-17 104815](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/2024-08-17-104815.png)

## 1.3 회원 도메인 개발

### # 동시성 이슈

> 여러 쓰레드가 동시에 같은 자원에 접근할 때 발생하는 문제를 말한다.
>
> - 이슈사항
>
>   1. 쓰레드 간섭
>      - 여러 쓰레드가 동시에 같은 자원을 수정하려고 할 때 발생
>      - ex) 두 쓰레드가 같은 변수의 값을 증가시키려고 하면, 최종 결과가 예상과 다를 수 있다.
>
>   2. 메모리 일관성 오류
>
>      - 한 쓰레드가 변경한 데이터가 다른 쓰레드에 의해 즉시 보이지 않을 때 발생
>
>      - ex) 주로 캐시와 관련된 문제로, 한 쓰레드가 변경한 값을 다른 쓰레드가 즉시 읽지 못하는 상황
>
>   3. 데드락
>
>      - 두개 이상의 쓰레드가 서로의 자원을 기다리며 무한 대기 상태에 빠지는 현상
>
>      - ex) 쓰레드 A가 자원 1을 잠그고 자원 2를 기다리는 동안, 쓰레드 B가 자원 2를 잠그고 자원 1을 기다리는 상황이 발생 할 수 있다.
>
> - 해결 방법
>
>   1. 동기화 (Synchronization) : `synchronization` 키워드를 사용하여 특정 코드 블록이나 메서드를 한 번에 하나의 쓰레드만 접근할 수 있도록 한다.
>   2. 락(Locks) : `ReentrantLock` 과 같은 락을 사용하여 더 세밀하게 동기화를 관리할 수 있다.
>   3. 원자적 연산(Atomic Operations) : `AtomicInteger` 와 같은 클래스를 사용하여 원자적 연산을 수행할 수 있다.
>   4. 스레드 안전한 컬렉션(Thread-safe-Collections): `ConcurrentHashMap` 과 같은 스레드 안전한 컬렉션을 사용하여 동시성 문제를 줄일 수 있다.
>
> - 예시 코드 - AtomicInteger
>
>   - AtomicInteger 를 사용하여 스레드 간섭 없이 안전하게 카운터를 증가시킬 수 있다.
>
>   ```
>   import java.util.concurrent.atomic.AtomicInteger;
>   
>   public class Counter {
>       private AtomicInteger count = new AtomicInteger(0);
>   
>       public void increment() {
>           count.incrementAndGet();
>       }
>   
>       public int getCount() {
>           return count.get();
>       }
>   
>       public static void main(String[] args) {
>           Counter counter = new Counter();
>           // 여러 스레드가 동시에 increment() 메서드를 호출해도 안전합니다.
>       }
>   }
>   ```
>
> - 예시 코드 - ConcurrentHashMap
>
>   ```
>    import java.util.concurrent.ConcurrentHashMap;
>        
>    public class Main {
>        public static void main(String[] args) {
>               ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
>               map.put("A", 1);
>               map.put("B", 2);
>               map.put("C", 3);
>       
>               System.out.println("Map size: " + map.size());
>               int valueA = map.get("A");
>               System.out.println("Value of A: " + valueA);
>       
>               map.remove("B");
>               System.out.println("Map size: " + map.size());
>           }
>   }
>   ```
>

### # 의존성 이슈

```
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

    private final MemberRepository memberRepository = new MemoryMemberRepositroy();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

```

## 1.4 주문과 할인 도메인 설계

- 주문 도메인 협력, 역할, 책임

  1. 주문생성 : **클라이언트**는 **주문 서비스**에 주문 생성을 요청
  2. 회원 조회 : 할인을 위해서는 회원 등급이 필요하다. 그래서 **주문 서비서**는 **회원 저장소**에서 회원을 조회
  3. 할인 적용 : **주문 서비스**는 회원 등급에 따른 할인 여부를 **할인 정책**에 위임
  4. 주문 결과 반환 : **주문 서비스**는 할인 결과를 포함한 주문 결과를 반환

  ![20240817123058](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/20240817123058.png)

- 주문 도메인 전체

  - 역할을 분리해서 자유롭게 구현 객체를 조립할 수 있게 설계
  - 회원 저장소, 할인 정책을 유연하게 변경할 수 있다.

  ![20240817133600](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/20240817133600.png)

- 주문 도메인 클래스 다이어그램

  ![20240817134040](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/20240817134040.png)

- 주문 도메인 객체 다이어그램 -1 ![20240817133908](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/20240817133908.png)

- 주문 도메인 객체 다이어그램 -2

  ![20240817134223](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/20240817134223.png)

```
✅ 협력 관계를 그대로 유지하고 기능만 변경할 수 있다.
```



# 2. 스프링 핵심 원리 이해 2 - 객체지향 원리 적용

## 2.1 새로운 할인 정책을 개발

- RateDiscountPolicy 추가![20240818181546](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/20240818181546.png)

  

## 2.2 새로운 할인 정책 적용과 문제점

- 문제점

  ```java
  public class OrderServiceImpl implements OrderService {
  
      private final MemberRepository memberRepository = new MemoryMemberRepositroy();
      //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
       private final DiscountPolicy discountPolicy = new RateDiscountPolicy(); // 할인 정책 변경
  	...
  }
  ```

  - DIP 위반

    - 추상(인터페이스) 뿐만 아니라 **구체(구현) 클래스에도 의존**하고 있다

      ![20240818183121](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/20240818183121.png)

  - OCP 위반

    - 코드는 기능을 확장해서 변경하면, 클라이언트 코드에 영향을 준다

      ![20240818183232](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/20240818183232.png)

## 2.3 해결방법

- 해결방법
  - 누군가가 클라이언트인 `OrderServiceImpl` 에 `DiscountPolicy` 의 구현 객체를 대
    신 생성하고 주입해주어야 한다.  

## 2.4 관심사 분리

- 관심사 분리

  - 공연의 예
    - 배우는 배우 역할에 충실해야한다.
    - 배역은 공연기획자가 정한다.

- AppConfig 등장

  - AppConfig 가 애플리케이션의 실제 동작에 필요한 구현 객체를 생성 한다.
  - 생성한 객체를 인스턴스의 참조(레퍼런스)를 생성자를 통해서 주입(연결) 해준다.

  - 각 구현객체는 실행에만 집중할 수 있다.

  - 클래스 다이어 그램

    - DIP 를 만족시킨다.
    - 관심사 분리 : 객체를 생성하고 연결하는 역할과 실행하는 역할이 분리되었다.

    ![20240818190136](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/20240818190136.png)
