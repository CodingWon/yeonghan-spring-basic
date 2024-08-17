# 1. 스프링 핵심 원리 이해 - 예제 만들기

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

### 동시성 이슈

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
>   

