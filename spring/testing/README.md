----

<br>

#### 목차

| **1. [단위테스트 (Unit Test)](#1-단위테스트-unit-test-20221011) <sup>2022/10/11</sup>** |
| :----------------------------------------------------------- |
| **2. [단위 테스트를 위한 F.I.R.S.T 원칙](#2-단위-테스트를-위한-first-원칙-20221011) <sup>2022/10/11</sup>** |
| **3. [hellper 클래스 또는 utility 클래스](#3-hellper-클래스-또는-utility-클래스-20221011) <sup>2022/10/11</sup>** |
| **4. [Given-When-Then 표현 스타일](#4-given-when-then-표현-스타일-20221011) <sup>2022/10/11</sup>** |
| **5. [테스트 프레임워크 JUnit5](#5-테스트-프레임워크-junit5-20221011) <sup>2022/10/11</sup>** |
| **6. [API 계층테스트 : 슬라이스 테스트](#6-api-계층테스트--슬라이스-테스트-20221011) <sup>2022/10/11</sup>** |
| **7.** **[Mock](#7-Mock-20221017)** <SUP>2022/10/17</SUP>    |

<br>

----





###  1. 단위테스트 (Unit Test) <SUP>2022/10/11</SUP>

> [목차로 돌아가기](#목차)

<img src="https://user-images.githubusercontent.com/72078208/180634838-d19f8591-4c21-455a-9751-2c58d7d81a3b.png" alt="image" style="zoom:70%;" />

- `기능 테스트` : 주로 QA부서에서 테스트하며, 사용자에게 제공하는 기능이 제대로 동작하는지 확인합니다.
- `통합 테스트` : 주로 애플리케이션을 만든 개발자가 테스트를 하며 Presentation / Service / DataAccess Layer를 모두 포함하는 테스트라고 볼 수 있습니다.
- `슬라이스 테스트` :  Presentation / Service / DataAccess Layer를 모두 쪼개서 테스트하는 방식을 말합니다. 슬라이스 테스트를 진행할 때는 주로 Mock객체를 사용해서 계층별로 끊어서 테스트를 진행합니다.
- `단위 테스트` : 메소드 단위로 작성된 코드를 테스트하는 방법을 말합니다. 최대한 독립적이고 작은 단위의 테스트가 진행되는 것이 좋으며, DB를 사용하는 단위테스트인 경우, 테스트 전과 후의 데이터가 동일하게 유지가 되어야합니다.

<br>

### 2. 단위 테스트를 위한 F.I.R.S.T 원칙 <SUP>2022/10/11</SUP>

> [목차로 돌아가기](#목차)

- `Fast` : 테스트 케이스는 빨라야합니다.
- `Independent` : 테스트 케이스는 독립적이어야 합니다.
- `Repeatable` : 테스트 케이스는 어떤 환경에서도 반복해서 실행 가능해야합니다.
- `Self-validating` : 단위 테스트는 성공 또는 실패의 결과를 보여야합니다.
- `Timely` : 단위 테스트는 테스트 하려는 기능을 구현하기 전에 작성해야 한다는 의미입니다.

<br>

### 3. hellper 클래스 또는 utility 클래스 <SUP>2022/10/11</SUP>

> [목차로 돌아가기](#목차)

1. 의미 

   - 유틸리티 클래스의 메소드들은 일반적으로 클래스의 객체로 인스턴스화 될 필요가 없기 때문에 정적인 메소드(static method)로 구성됩니다.

2. 예시

   - ```java
     public class StampCalculatorHellper {
         public static int StampCount(int nowcnt, int earned) {
             return nowcnt + earned
         }
     }
     ```

<br>

### 4. Given-When-Then 표현 스타일 <SUP>2022/10/11</SUP>

> [목차로 돌아가기](#목차)

- `Given` 
  - 테스트를 위한 준비 과정을 명시합니다.
  - 테스트에 필요한 전제 조건들이 포함되어 있습니다.
  - 테스트 대상에 전달되는 입력 값(테스트 데이터) 역시 Given에 포함됩니다.
- `When`
  - 테스트 할 동작(대상)을 지정합니다.
  - 단위 테스트에서는 일반적으로 메소드 호출을 통해 테스트를 진행하기에 한 두줄 정도의 작성으로 끝납니다.
- `Then`
  - 테스트의 결과를 검증하는 영역입니다.
  - 일반적으로 예상하는 값(`expected`)과 테스트 대상 메서드의 동작 수행 결과(`actual`) 값을 비교해서 기대한대로 동작을 수행하는지 검증(**Assertion**)하는 코드들이 포함됩니다.
  - Assertion이란 '예상하는 결과 값이 참(ture)이길 바라는 것' 입니다.

<br>

### 5. 테스트 프레임워크 JUnit5 <SUP>2022/10/11</SUP>

> [목차로 돌아가기](#목차)

1. JUnit이란?

   - Spring 테스트 프레임워크의 default가 JUnit인 만큼 사실상 표준으로 자로 잡은 테스트 프레임워크

   

2. 간단한 사용방법

   ```java
   public class JUnitTest {
       @Test
       @DisplayName("JUnit display name set")
       public void assertionTest() {
           String expected = "값은 10입니다.";
           String actual = "값은 10입니다.";
           
           assertEquals(expected, actual); //True
       }
   }
   ```

   해당 테스트 케이스는 예측값과 실제값이 일치하기 때문에 테스트가 통과됩니다.

   - `@Test` : 테스트 대상의 타켓 메소드에 작성합니다.
   - `@DisplayName` : 테스트 코드 실행시의 이름을 명해줍니다.

   

3. 여러가지 assert메소드

   - `assertNotNull(testTarget, testFaildMessage)` : testTarget이 null인지 확인합니다. 만약, testTarget이  null이라면 testFaildMessage를 출력합니다. null이 아니라면 테스트는 통과되어 pass되었다라고 볼 수 있습니다. (passed)
   - `assertThrows(expectedExceptionClass, () -> testTarget)` : 첫 번째 파라메터에는 예상되는 ExceptionClass를 입력하고 두 번째 파라메터에는 람다 표현식으로 테스트 대상을 호출하면 됩니다. 만약 예측하는 Exception이 발생한다면 해당 테스트는 pass가 될 것이고 예상되는 Exception이 발생하지 않는다면 fail이 될 것이므로 테스트케이스는 통과되지 않습니다. (failed) 여기서 주의깊게 살펴볼 수 있는 것은 두 번째 파라메터인데 두 번째 파라메터는 `Executable` 함수형 인터페이스 입니다. Java에서 지웒나는 함수형 인터페이스 중에서 리턴값이 없는 `Consumer`에 해당된다고 보면 됩니다.

   

4. 테스트 케이스 실행전, 전처리

   - `@BeforeEach` : 테스트 케이스를 실행하기 전에 어떤 객체나 값에 대한 초기화 작업 등의 전처리 과정에서 사용됩니다. (테스트 케이스 실행 직전에 먼저 실행되어 초기화 작업 등을 진행하며, 클래스 레벨에서 실행시에 각 메소드가 호출될 때마다 초기화가 진행됩니다.)
   - `@BeforeAll` : 테스트 케이스를 실행하고 난 후에 어떤 객체나 값에 대한 초기화 작업 등의 전처리 과정에서 사용됩니다. (테스트 케이스 실행 직전에 먼저 실행되어 초기화 작업 등을 진행하며, 클래스 레벨에서 실행시에 메소드를 호출하기전에 단 한 번만 실행됩니다. )

   ```java
   public class BeforeEachTest() {
       @BeforeEach
       public void init() {
           System.out.println("테스트 실행전 초기화");
       }
       
       @Test
       public void FirstMethod() {}
       
       @Test
       public void SecondMethod() {}
       
       /* ---- 클래스 레벨에서의 테스트 출력결과 ----
       	테스트 실행전 초기화
       	테스트 실행전 초기화
       */ -------------------------------------
   }
   ```

   - `@AfterEach` : @BeforeEach와 반대 의미를 가지는 어노테이션입니다.
   - `@AfterAll` : @BeforeAll과 반대 의미를 가지는 어노테이션입니다.

<br>

### 6. API 계층테스트 : 슬라이스 테스트 <SUP>2022/10/11</SUP>

> [목차로 돌아가기](#목차)

#### [1] Controller 테스트

1. Controller 테스트의 기본 구조

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest       
@AutoConfigureMockMvc 
public class ControllerTestDefaultStructure {
    @Autowired
    private MockMvc mockMvc;
     
    @Test
    public void postMemberTest() {
        // given : 테스트용 request body 생성
        
        // when : MockMvc 객체로 테스트 대상 Controller 호출
        
        // then : Controller 핸들러 메서드에서 응답으로 수신한 HTTP Status 및 response body 검증 
    }
}
```

- `@SpringBootTest` :  Application Context를 생성합니다. Application Context에는 애플리케이션에 필요한 Bean 객체들이 등록되어 있습니다. 기존의 `@WebMvcTest`를 사용한다면 Controller에 의존하는 컴포넌트들을 모두 일일이 설정해주어야 하는 불편함이 있기 때문에 @SpringBootTest를 이용하도록 합니다.
- `@AutoConfigureMockMvc` : Controller 테스트를 위한 애플리케이션의 자동 구성 작업을 해줍니다. `MockMvc`같은 기능을 사용하기 위해서는 반드시 작성해주어야합니다.
- `MockMvc` : 서버를 실행하지 않고 Controller를 테스트 할 수 있는 환경을 지원합니다. 일종의 Spring MVC 테스트 프레임워크 입니다. 



2. Controller 테스트 예시

```java
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void postMemberTest() throws Exception {
        // given
        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com",
                                                        "홍길동",
                                                    "010-1234-5678");
        String content = gson.toJson(post);

        // when
        ResultActions actions =
                mockMvc.perform(                       
	                                post("/v11/members")  
                                        .accept(MediaType.APPLICATION_JSON) 
                                        .contentType(MediaType.APPLICATION_JSON) 
                                        .content(content)
                                );

        // then
        MvcResult result = actions
                                .andExpect(status().isCreated())
                                .andReturn();

        // System.out.println(result.getResponse().getContentAsString());
    }
}
```

- `// given` : 테스트할  데이터를 준비합니다.

- `gson.toJson()` : GSON은 구글에서 지원하는 라이브러리로 자바 오브젝트를 쉽게 JSON형태로 변경해줍니다.

- `ResultActions` : ResultActions는 인터페이스입니다. MockMvc로 테스트 대상 Controller의 핸들러 메소드에 요청을 보내기 위해서는 `perform()`메소드를 호출하여 사용합니다.

- `perform() 내부`  : perform()메소드의 내부에는 Controller 호출을 위한 세부적인 정보들이 포함됩니다. 예시에서의 `post()`, `accept()`, `contentType()`, `content()`는 HTTP Request에 대한 정보입니다. 세부 내용은 아래와 같습니다.

  - `post()` : HTTP POST METHOD와 request URL을 설정합니다.
  - `accept()` : 클라이언트가 response 받을 때 이해 가능한 컨텐츠 타입이 무엇인지를 알려줍니다.
  - `contentType()` : 클라이언트가 request로 데이터를 실어 보내는 데이터의 타입정보를 표현합니다.
  - `content()` : request body 데이터를 설정합니다.  

- `MvcResult` : ResultActions 객체를 이용해서 우리가 전송한 request에 대한 검증을 수행합니다.

  - `andExpect()` : 응답에 대한 상태값이 파라메터로 들어가는 값과 일치하는지 여부를 확인합니다.
  - `andReturn()` : reponse 데이터를 확인할 수 있으며 디버깅 용도로 response로 전달되는 응답 데이터를 출력할 때 사용할 수 있습니다.

- `예제의 주석` : response body 응답 데이터에 포함된 한글이 깨지는 경우라면 주석을 해제하고 application.yml파일에 아래와 같은 내용을 기입합니다.

  - ```properties
    server:
      servlet:
        encoding:
          force-response: true
    ```



3. 위의 Controller Test의 문제점
   - Controller테스트임에도 불구하고 Service계층과 DataAccess계층까지 모두 실행이 됩니다.
   - 따라서 해당 테스트는 슬라이스 테스트라고 보기힘듭니다.
   - 해당 문제를 해결하기 위해서는 Mock(가짜)객체를 사용해 계층 간의 연결을 끊어줌으로써 해결이 가능합니다. 해당 내용은 Mockito를 사용하여 해결이 가능합니다. Mockito는 아래에서 설명하겠습니다.

<br>

#### [2] DataAccess 계층 테스트 <SUP>2022/10/11</SUP>

1. 데이터 액세스 계층을 테스트 하기 위한 규칙

   - DB의 상태를 테스트 케이스 실행 이전으로 되돌려서 깨끗하게 만들어야 합니다.

   

2. DataAccess 테스트 예시

```java
@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveMemberTest() {
        // given
        Member member = new Member();
        member.setEmail("hgd@gmail.com");
        member.setName("홍길동");
        member.setPhone("010-1111-2222");

        // when
        Member savedMember = memberRepository.save(member);

        // then
        assertNotNull(savedMember); // (5-1)
        assertTrue(member.getEmail().equals(savedMember.getEmail()));
        assertTrue(member.getName().equals(savedMember.getName()));
        assertTrue(member.getPhone().equals(savedMember.getPhone()));
    }
}
```

- `@DataJpaTest` : 데이터 액세스 계층을 테스트 하기 위한 가장 핵심입니다. @DataJpaTest가 클래스에 추가됨으로써 Repository의 기능을 정상적으로 사용하기 위한 Configuration을 Spring이 자동으로 해주게 됩니다. 예제 코드에서는 MemberRepository의 기능을 정상적으로 사용하기 위한 Configuration을 Spring이 자동으로 해줍니다. 또한, @DataJpaTest는 `@Transactional`을 포함하고 있기 때문에 하나의 테스트 케이스 실행이 종료되는 시점에서 데이터 베이스에 저장된 데이터는 rollback처리 됩니다. 이로써 데이터 베이스의 상태는 유지할 수 있습니다.

<br>

### 7. Mock <SUP>2022/10/17</SUP>

1. Mock이란?

   - 테스트 세계에서의 Mock은 가짜 객체를 의미합니다.

2. Mock을 사용하는 이유

   <img width="607" alt="12143" src="https://user-images.githubusercontent.com/72078208/180656677-da0201e7-6135-4a28-ae1b-e6b17066aba5.png" style="zoom: 67%">

   - [API 계층테스트 : 슬라이스 테스트](./spring/testing)의 [Controller 테스트](#1-controller-테스트) 소목록 3번에서 언급했듯이 애플리케이션에서 Controller만 테스트한다고 가정할 때 Service계층과 DataAccess계층을 모두 거치는 것은 슬라이스 테스트라고 볼 수 없습니다. 따라서 성능적으로나 슬라이스 테스트에 부합하지 않습니다. (위의 사진은 MemberController 슬라이스 테스트를 위해 모든 계층을 거치고 있는 것을 나타낸 그림입니다. )

     <br>

   <img width="583" alt="353653" src="https://user-images.githubusercontent.com/72078208/180657341-396a4f5c-588a-4fee-8799-8aa434c81203.png" style="zoom:67%;" >

   - Mock객체를 사용하면 불필요한 계층의 과정은 거치지 않고 특정 계층의 테스트에 집중할 수 있습니다.

3. Mock을 지원해주는 라이브러리
   - Mock객체를 생성하고 진짜처럼 동작해주는 Mocking할 수 있게 해주는 대표 라이브러리는 Mockito입니다. Mockito는 계층의 분리를 시켜줌으로써 특정 계층의 테스트에 집중할 수 있도록 돕습니다.
   
4. **Mockito 적용한 예시 (1/2)**

   - MemberController의 postMember()테스트에 Mockito 적용
   - [참고자료](./sources/testing) (출처 : codestates)

```java
@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private MemberService memberService;

    @Autowired
    private MemberMapper mapper;

    @Test
    void postMemberTest() throws Exception {
        // given
        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com",
                                                        "홍길동",
                                                    "010-1234-5678");
				
        Member member = mapper.memberPostToMember(post);
        member.setStamp(new Stamp());

        
        given(memberService.createMember(Mockito.any(Member.class)))
                .willReturn(member);

        String content = gson.toJson(post);

        
        ResultActions actions =
                mockMvc.perform(
                                    post("/v11/members")
                                        .accept(MediaType.APPLICATION_JSON)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(content)
                                );

        
        MvcResult result = actions
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.data.email").value(post.getEmail()))
                                .andExpect(jsonPath("$.data.name").value(post.getName()))
                                .andExpect(jsonPath("$.data.phone").value(post.getPhone()))
                                .andReturn();

//        System.out.println(result.getResponse().getContentAsString());
    }
}
```

- `@MockBean` : Application Context에 등록되어 있는 Bean에 대한 Mockito Mock 객체를 생성하고 주입하는 역할을 합니다. (Mock객체를 생성하고 주입한다는 것은 실제 코드의 의존성을 끊어주어 테스트 속도를 높인다고 이해해도 될 것 같습니다.)
- `given(memberService.createMember(Mockito.any(Member.class))).willReturn(member)` : Mockito에서 지원하는 Stubbing 메소드입니다.
  - `given(memberService.createMember(Mockito.any(Member.class)))` : given()은 Mock 객체가 특정 값을 리턴하는 동작을 지정하는데 사용하며, Mockito에서 지원하는 when()과 동일한 기능을 합니다. 해당 예제에서는 Mock객체인 memberService 객체로 createMember()메소드를 호출하도록 정의합니다. createMember()의 파라미터인 Mockito.any(Member.class)는 Mockito에서 지원하는 변수 타입 중 하나입니다. MockMemeberService(가칭)가 아닌 실제 MemberService 클래스에서 createMember()의 파라미터 타입은 Member임을 밝힙니다. 따라서 Mockito.any()에 Member.class를 타입으로 지정합니다.
  - `willReturn(member)` : MockMemberService(가칭)의 createMember() 메소드가 리턴 할 Stub 데이터입니다.

5. **Mockito 적용한 예시 (2/2)**

   - MemberService의 createMember()테스트에 Mockito 적용

   - ```
     MemberService 클래스의 createMember()메소드는 비즈니스 로직입니다.
     일반적으로 비즈니스 로직은 데이터 액세스 계층과는 무관하게 서비스 계층에 구현된 비즈니스 로직 자체를 Spring Framework의 도움을 받지 않고도 빠르게 진행할 수 있어야합니다.
     ```

   - [참고자료](./sources/testing) (출처 : codestates)

   -  아래 코드는 MemberService 클래스 코드의 일부입니다.

```java
@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;

    public MemberService(MemberRepository memberRepository,
                         ApplicationEventPublisher publisher) {
        this.memberRepository = memberRepository;
        this.publisher = publisher;

    }

    public Member createMember(Member member) {
        verifyExistsEmail(member.getEmail());     // (1)
        Member savedMember = memberRepository.save(member);

        publisher.publishEvent(new MemberRegistrationApplicationEvent(this, savedMember));
        return savedMember;
    }

    ...
		...

    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);  // (2)

        // (3)
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }
}
```

- 테스트의 목적은 DB에 존재하는 이메일인지 여부를 검증하는 `verifyExistsEmail()`메소드가 정상적으로 동작을 수행하는지를 테스트하는 것입니다.
  - `verifyExistsEmail()` 메소드의 내부를 확인하면 (2)와 같이 `memberRepository.findByEamil(email)`로 DB내부에서 파라메터 email을 조건으로 회원 정보가 있는지 조회하고 있습니다. 하지만 우리는 `verifyExistsEmail()`메소드가 DB에서 Member 객체를 잘 조회하는지 여부를 테스트 하려는게 아니라, 어디서 조회하던 상관없이 조회된 Member객체가 null이면 `BusinessLogicException`을 잘 던지는지 여부만 테스트하면 됩니다.
  - (3)과 같이 Member객체 null여부를 판단하는 것 역시 비즈니스 로직이라고 볼 수 있습니다.
  - 따라서 DB에서 회원 정보를 조회하는 `memberRepository.findByEamil(email)`은 Mocking의 대상이 됩니다. 중요한 것은 Member객체를 어디에서 얻어오던지 상관없습니다. 즉, DB에서 회원 정보를 조회하지 않고 Mocking을 통해서 Member 객체를 제공할 수 있다는 것입니다.

<br>

- Mockito를 통한 Mocking구현

```java
package com.codestates.slice.mock;

import com.codestates.exception.BusinessLogicException;
import com.codestates.member.entity.Member;
import com.codestates.member.repository.MemberRepository;
import com.codestates.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

// (1)
@ExtendWith(MockitoExtension.class)
public class MemberServiceMockTest {
    @Mock   // (2)
    private MemberRepository memberRepository;

    @InjectMocks    // (3)
    private MemberService memberService;

    @Test
    public void createMemberTest() {
        // given
        Member member = new Member("hgd@gmail.com", "홍길동", "010-1111-1111");

        // (4)
        given(memberRepository.findByEmail(member.getEmail()))
                .willReturn(Optional.of(member)); // (5)

				// when / then (6)
        assertThrows(BusinessLogicException.class, () -> memberService.createMember(member));
    }
}
```

- `@ExtendWith(MockitoExtension.class)` : Spring을 사용하지 않고 JUnit에서 Mockito의 기능을 사용하기 위해서는 넣어줘야합니다.
- `@Mock` : 필드의 객체를 Mock 객체로 생성합니다.
- `@InjectMocks` : 이 어노테이션을 추가한 필드에 (2)에서 생성한 Mock객체를 주입합니다. **따라서 (3)의 memberService 객체는 주입 받은 memberRepository Mock 객체를 포함하고 있습니다.**
- `given(memberRepository.findByEmail(member.getEmail())).willReturn(Optional.of(member));` : 여기서는 (2)에서 생성한 memberRepository Mock객체로 Stubbing을 하고 있습니다. `memberRepository.findByEmail(Mockito.anyString())`의 리턴 값으로 (5)와 같이 `Optional.of(member)`를 지정했기 때문에 테스트 케이스를 실행하면 결과는 “`passed`”입니다.
- `Optional.of(member)` 의 member 객체에 포함된 이메일 주소가 `memberService.createMember(member)` 에서 파라미터로 전달한 member 객체에 포함된 이메일 주소와 동일하기 때문에 검증 결과가 “`passed`”라는 사실을 잘 이해해야합니다.