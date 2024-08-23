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

## 2.5 AppConfig 리팩토링

- 기대하는 그림 - Appconfig 는 역할이 잘 보이지 않는다.

  ![20240818192957](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/20240818192957.png)

- 역할 - 의존 관계를 명확히 볼 수 있다.

  ```java
  package hello.core;
  
  import hello.core.discount.DiscountPolicy;
  import hello.core.discount.FixDiscountPolicy;
  import hello.core.member.MemberRepository;
  import hello.core.member.MemberService;
  import hello.core.member.MemberServiceImpl;
  import hello.core.member.MemoryMemberRepositroy;
  
  import hello.core.order.OrderService;
  import hello.core.order.OrderServiceImpl;
  
  // 나중에 기능이 변경시 AppConfig 만 변경하면 된다.
  public class AppConfig {
  
      // 역할 : memberService
      // 의존 : memberRepository
      public MemberService memberService (){
          return new MemberServiceImpl(memberRepository());     // 생성자 주입
      }
  
      // 역할 : memberRepository
      private static MemberRepository memberRepository() {
          return new MemoryMemberRepositroy();
      }
  
      // 역할 : orderService
      // 의존 : memberRepository , discountPolicy
      public OrderService orderService(){
          return new OrderServiceImpl(memberRepository(), discountPolicy());
  
      }
  
      // 역할 : discountPolicy
      private static DiscountPolicy discountPolicy() {
          return new FixDiscountPolicy();
      //  return new RateDiscountPolicy();    // AppConfig 만 변경 하면 기능을 바꿀 수 있다.
  
      }
  }
  
  ```

## 2.6 새로운 구조와 할인 정책 적용

- 사용 , 구성의 분리
  - AppConfig 만 변경 하여도 할인 정책을 변경할 수 있다.
- ![20240818194124](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/20240818194124.png)

## 2.7 좋은 객체 지향 설계의 5가지 원칙의 적용

### # SRP 단일 책임 원칙

- 구현 객체를 생성하고 연결하는 책임은 AppConfig가 담당
- 클라이언트 객체는 실행하는 책임만 담당

### # DIP 의존관계 역전 원칙

- 프로그래머는 추상화에 의존해야지, 구체화에 의존하면 안된다.
- AppConfig 가 의존성 주입을 해줘서 DIP 원칙을 지킬 수 있다.

### # OCP

- 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다.
- 애플리케이션을 사용영역과 구성영역으로 나눔
- 클라이언트 코드는 변경 하지 않고 새롭게 기능을 확장 할 수 있다.

### # 제어의 역전 IoC(Inversion of Control)

- 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것을 말한다.
- AppConfig 에 의해 흐름이 제어 된다.

> 프레임워크 vs 라이브러리
>
> - 프레임 워크
>   - 내가 작성한 코드를 제어하고, 대신 실행한다.(JUnit)
> - 라이브러리
>   - 내가 작성한 코드가 직접 제어의 흐름을 담당한다.

### # 의존 관계 주입 DI(Dependency Injection)

- `OrderServiceImpl `  는 `DiscountPolicy`인터페이스에 의존한다. 

- 의존관계는 **정적인 클래스 의존 관계와, 실행 시점에 결정되는 동적인 객체(인스턴스) 의존 관계**를 분리해서 생각해야 한다.

- 정적인 클래스 의존 관계

  - 실제 어떤 구현 객체가 사용될지는 모른다.  

    ![20240818201112](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/20240818201112.png)

- 동적인 객체 인스턴스 의존 관계

  - 런타임 시

    - 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달
    - 클라이언트와 서버의 실제 의존 관계가 연결되는 것을 의존성 주입이라고 한다.

  - 정적인 클래스 의존 관계를 손대지 않고, 동적인 객체 인스턴스 의존관계를 쉽게 변경할 수 있다.

    ![20240818201545](https://raw.githubusercontent.com/CodingWon/yeonghan-spring-basic/master/imgs/20240818201545.png)

### # Ioc 컨테이너, DI 컨테이너

- AppConfig
  - 객체를 생성, 관리, 의존관계를 연결 해주는 것
  - 의존관계 주입에 초점을 맞추어 DI 컨테이너라고 부른다.
  - 어샘블러(조립) , 오브젝트 팩토리 .... 등등 으로 불림

## 2.8 스프링으로 전환하기  

### # 스프링 컨테이너

- `ApplicationContext` 를 스프링 컨테이너라 한다  
- `@Configuration` 이 붙은 `AppConfig` 를 설정(구성) 정보로 사용한다.
- `@Bean` 이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록  
- `@Bean` 이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다 .
- 스프링 빈은 `applicationContext.getBean()` 메서드를 사용 해서 찾을 수 있다.  
- 스프링 컨테이너를 사용했을 때 장점 ?

# 4. 스프링 컨테이너와 스프링 빈

## 4.1 스프링 컨테이너 생성

- `ApplicationContext` 를 스프링 컨테이너라 한다  
- `AnnotationConfigApplicationContext(AppConfig.class);`  는 구현체
- `ApplicationContext` 는 인터페이스

### # 스프링 컨테이너 생성 과정

1. 스프링 컨테이너 생성
   - `AppConfig.class`구성 정보 지정

![20240822195559](https://raw.githubusercontent.com/CodingWon/images/master/imgs/20240822195559.png)

2. 스프링 빈 등록

   - `@Bean` 을 확인해서 호출 
   - 메서드 이름을 기준으로 객체를 저장
   - `@Bean(name="memberService2")` 직접 지정 가능

   ![20240822195747](https://raw.githubusercontent.com/CodingWon/images/master/imgs/20240822195747.png)

3. 스프링 빈 의존관계 설정 준비

![20240822200114](https://raw.githubusercontent.com/CodingWon/images/master/imgs/20240822200114.png)

4. 스프링 빈 의존관계 설정 - 완료
   - 동적인 의존 관계를 스프링이 연결시켜준다.

![20240822200203](https://raw.githubusercontent.com/CodingWon/images/master/imgs/20240822200203.png)

## 4.2 컨테이너에 등록된 모든 빈 조회

## 4.3 스프링 빈 조회 -기본

## 4.4 스프링 빈 조회 - 동일한 타입이 둘이상

1. 타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다.
2. 타입으로 조회시 같은 타입이 둘 이상 있으면, 빈 이름을 지정하면 된다.
3. 특정 타입을 모두 조회하기

## 4.5 스프링 빈 조회 - 상속 관계

```
"부모 타입으로 조회하면 , 자식 타입도 함께 조회한다."
```

![20240823153437](https://raw.githubusercontent.com/CodingWon/images/master/imgs/20240823153437.png)

1. 부모 타입으로 조회시, 자식이 둘 이상 있으면, 중복 오류가 발생한다.
2. 부모 타입으로 조회시, 자식이 둘 이상 있으면, 빈 이름으로 지정하면 된다.
3. 특정 하위 타입으로 조회
4. 부모 타입으로 모두 조회하기
5. 부모 타입으로 모두 조회하기 - Object

## 4.6 BeanFactory와 ApplicationContext  

![20240823155532](https://raw.githubusercontent.com/CodingWon/images/master/imgs/20240823155532.png)

- BeanFactory

  - 스프링 컨테이너의 최상의 인터페이스
  - 스프링 빈을 관리하고 조회하는 역할 담당

- ApplicationContext

  - BeanFactory 기능을 모두 상속 받아서 제공
  - 빈 관리 기능 이외의 부가기능을 물려 받았다.
    - 메세지 소스를 활용한 국제화 기능
      - 한국권 -> 한국어 / 영어권 -> 영어로 출력
    - 환경변수
      - 로컬, 개발, 운영등을 구분해서 처리
    - 애플리케이션 이벤트
      - 이벤트를 발행하고 구독하는 모델을 편리하게 지원
    - 편리한 리소스 조회
      - 파일, 클래스, 외부 등에서 외부 리소스를 편리하게 조회

  ![20240823155816](https://raw.githubusercontent.com/CodingWon/images/master/imgs/20240823155816.png)

## 4.7 다양한 설정 형식 지원 - 자바 코드, XML

![20240823160322](https://raw.githubusercontent.com/CodingWon/images/master/imgs/20240823160322.png)

1. 애노테이션 기반 자바 코드 설정 사용

2. XML 설정 사용
   - XML 을 사용하면 컴파일 없이 빈 설정 정보를 변경할 수 있는 장점이 있다.

- xml 방식은 최근에 잘 사용하지 않는다.

## 4.8 스프링 빈 설정 메타 정보 - BeanDefinition

> 스프링이 다양한 설정 형식을 지원할 수 있는 이유는 ?

- `BeanDefinition` 이라는 추상화가 존재
- 설정 메타정보라고 한다.
- @Bean 당 각각 하나씩 메타 정보가 생성된다.

![20240823161957](https://raw.githubusercontent.com/CodingWon/images/master/imgs/20240823161957.png)

- 좀 더 깊이...

  ![20240823162212](https://raw.githubusercontent.com/CodingWon/images/master/imgs/20240823162212.png)

- BeanDefinition 정보
  - BeanClassName: 생성할 빈의 클래스 명(자바 설정 처럼 팩토리 역할의 빈을 사용하면 없음)
  - factoryBeanName: 팩토리 역할의 빈을 사용할 경우 이름, 예) appConfig
  - factoryMethodName: 빈을 생성할 팩토리 메서드 지정, 예) memberService
    Scope: 싱글톤(기본값)
  - lazyInit: 스프링 컨테이너를 생성할 때 빈을 생성하는 것이 아니라, 실제 빈을 사용할 때 까지 최대한 생성을 지연 처리 하는지 여부
  - InitMethodName: 빈을 생성하고, 의존관계를 적용한 뒤에 호출되는 초기화 메서드 명
  - DestroyMethodName: 빈의 생명주기가 끝나서 제거하기 직전에 호출되는 메서드 명
  - Constructor arguments, Properties: 의존관계 주입에서 사용한다. (자바 설정 처럼 팩토리 역할의 빈을 사용하면 없음)  

# 5. 싱글톤 컨테이너

## 5.1 웹 애플리케이션과 싱글톤  

- 웹 애플리케이션은 보통 고객이 동시에 요청을 한다.

  ![20240823174659](https://raw.githubusercontent.com/CodingWon/images/master/imgs/20240823174659.png)

- AppConfig 는 요청할 때마다 객체를 새로 생성한다. 
- 메모리 낭비가 심하다
- 싱글톤으로 만들고 공유해서 사용하면 된다.

## 5.2 싱글톤 패턴  

- 클래스 인스턴스가 1개만 생성되는 것을 보장하는 디자인 패턴
- 스프링 컨테이너가 싱글톤으로 객체를 관리해준다.
- 싱글톤 패턴 문제점
  - 구현하는 코드 자체가 많이 들어간다.
  - 의존 관계상 클라이언트가 구체클래스에 의존해야한다. ->DIP 위만
  - 구체클래스에 의존해서 OCP 원칙을 위반
  - 테스트하기 어렵다.
  - 내부 속성을 변경하거나 초기화 하기 어렵다.
  - private 생성자로 자식 클래스를 만들기 어렵다.
  - 안티패턴으로 불리기도 한다.

## 5.3 싱글톤 컨테이너

- 싱글톤 패턴을 적용하지 않아도 객체 인스턴스를 싱글톤으로 관리한다.
- 싱글톤 패턴을 위한 지저분한 코드 불필요
- DIP, OCP, 테스트, private 생성자로 부터 자유롭게 싱글톤을 사용할 수 있다.
- 싱글톤 컨테이너 적용 후
  - 이미 만들어진 객체를 효율적으로 사용 할 수 있다.

![20240823181218](https://raw.githubusercontent.com/CodingWon/images/master/imgs/20240823181218.png)

> 참고 : 스프링 기본은 싱글톤이지만, 요청할 때 마다 새로운 객체를 생성해서 반환하는 기능도 제공

## 5.4  싱글톤 방식의 주의점

```
여러 클라이언트가 하나의 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지하게 설계하면 안된다.
```

- 무상태(stateless)
  - 특정 클라이언트에 의존적인 필드가 있으면 안된다.
  - 가급적이면 읽기만 가능하게
  - 필드 대신에 자바에서 공유되지 않는, 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다.
- 스프링 빈의 필드에 공유 값을 설정하면 정말 큰 장애가 발생할 수 있다.
- 상태를 유지할 경우 발생하는 문제점 예시
  - 필드를 공유할경유 A 고객의 값을 B의 고객이 바꿀 수 있다.
- 스프링 빈은 항상 무상태(stateless)로 설계하자 !!

## 5.5 @Configuration과 싱글톤  

- 각각 다른 2개의 `MemoryMemberRepository` 가 생성되면서 싱글톤이 깨지는 것 처럼 보인다. 스프링
  컨테이너는 이 문제를 어떻게 해결할까?  

- 총 세번 호출 해야 하는데 테스트 결과 => **모두 1번만 호출된다.**  

  1. 스프링 컨테이너가 스프링 빈에 등록하기 위해 @Bean이 붙어있는 `memberRepository()` 호출
  2. memberService() 로직에서 `memberRepository()` 호출
  3. orderService() 로직에서 `memberRepository()` 호출  

  
