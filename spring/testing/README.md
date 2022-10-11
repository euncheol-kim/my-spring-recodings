----

<br>

##### 목차

| **1. [단위테스트 (Unit Test)](#1-단위테스트-unit-test-20221011) <sup>2022/10/11</sup>** |
| ------------------------------------------------------------ |
| **2. [단위 테스트를 위한 F.I.R.S.T 원칙](#2-단위-테스트를-위한-first-원칙-20221011) <sup>2022/10/11</sup>** |
| **3. [hellper 클래스 또는 utility 클래스](#3-hellper-클래스-또는-utility-클래스-20221011) <sup>2022/10/11</sup>** |
| **4. [Given-When-Then 표현 스타일](#4-given-when-then-표현-스타일-20221011) <sup>2022/10/11</sup>** |
| **5. [테스트 프레임워크 JUnit5](#5-테스트-프레임워크-junit5-20221011) <sup>2022/10/11</sup>** |
| **6. [API 계층테스트 : 슬라이스 테스트](#6-api-계층테스트--슬라이스-테스트-20221011) <sup>2022/10/11</sup>** |

<br>

----





###  1. 단위테스트 (Unit Test) <SUP>2022/10/11</SUP>

<img src="https://user-images.githubusercontent.com/72078208/180634838-d19f8591-4c21-455a-9751-2c58d7d81a3b.png" alt="image" style="zoom:70%;" />

- `기능 테스트` : 주로 QA부서에서 테스트하며, 사용자에게 제공하는 기능이 제대로 동작하는지 확인합니다.
- `통합 테스트` : 주로 애플리케이션을 만든 개발자가 테스트를 하며 Presentation / Service / DataAccess Layer를 모두 포함하는 테스트라고 볼 수 있습니다.
- `슬라이스 테스트` :  Presentation / Service / DataAccess Layer를 모두 쪼개서 테스트하는 방식을 말합니다. 슬라이스 테스트를 진행할 때는 주로 Mock객체를 사용해서 계층별로 끊어서 테스트를 진행합니다.
- `단위 테스트` : 메소드 단위로 작성된 코드를 테스트하는 방법을 말합니다. 최대한 독립적이고 작은 단위의 테스트가 진행되는 것이 좋으며, DB를 사용하는 단위테스트인 경우, 테스트 전과 후의 데이터가 동일하게 유지가 되어야합니다.

<br>

### 2. 단위 테스트를 위한 F.I.R.S.T 원칙 <SUP>2022/10/11</SUP>

- `Fast` : 테스트 케이스는 빨라야합니다.
- `Independent` : 테스트 케이스는 독립적이어야 합니다.
- `Repeatable` : 테스트 케이스는 어떤 환경에서도 반복해서 실행 가능해야합니다.
- `Self-validating` : 단위 테스트는 성공 또는 실패의 결과를 보여야합니다.
- `Timely` : 단위 테스트는 테스트 하려는 기능을 구현하기 전에 작성해야 한다는 의미입니다.

<br>

### 3. hellper 클래스 또는 utility 클래스 <SUP>2022/10/11</SUP>

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
  - Assertion이란 '예상하는 결과 값이 참(ture)이길 바라는 것입니다.'

<br>

### 5. 테스트 프레임워크 JUnit5 <SUP>2022/10/11</SUP>

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