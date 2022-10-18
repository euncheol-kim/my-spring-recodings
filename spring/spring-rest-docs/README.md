

<br>

---



#### 목차

| 1. [Spring Rest Docs 장점](#1-spring-rest-docs-장점-20221018)  <sup>2022/10/18</sup> |
| :----------------------------------------------------------- |
| **2. [Spring Rest Docs API 문서 생성 흐름](#2-spring-rest-docs-api-문서-생성-흐름-20221018) <sup>2022/10/18</sup>** |
| **3. [Spring Rest Docs 설정](#3-spring-rest-docs-설정-20221018) <sup>2022/10/18</sup>** |
| **4. [API 문서 생성을 위한 슬라이스 테스트 케이스 작성](#4-api-문서-생성을-위한-슬라이스-테스트-케이스-작성-20221018) <sup>2022/10/18</sup>**<br /> - API문서 생성을 위한 테스트 케이스 기본 구조 |
| **5. [API 문서 생성을 위한 API 스펙 정보 추가 (1/2)](#5-api-문서-생성을-위한-api-스펙-정보-추가-12-20221018) <sup>2022/10/18</sup>**<br />- MemberController의 postMember() 핸들러 메서드에 대한 API 스펙 정보 추가<br />**5.** **[API 문서 생성을 위한 API 스펙 정보 추가 (2/2)](#5-api-문서-생성을-위한-api-스펙-정보-추가-22-20221018) <sup>2022/10/18</sup>** <br />- MemberController의 patchMember() 핸들러 메서드에 대한 API 스펙 정보 추가 |
| **6. [API 문서 템플릿 생성을 위한 디렉토리 및 템플릿 문서 생성](#6-api-문서-템플릿-생성을-위한-디렉토리-및-템플릿-문서-생성-20221018) <sup>2022/10/18</sup>** |
| **7. [템플릿 문서 내용 추가](#7-템플릿-문서-내용-추가-20221018) <sup>2022/10/18</sup>** |
| **8. [템플릿 문서를 HTML 파일로 변환](#8-템플릿-문서를-html-파일로-변환-20221018) <sup>2022/10/18</sup>** |
| **9. [Spring Rest Docs에서의 Asciidoc](#9-spring-rest-docs에서의-asciidoc-20221018) <sup>2022/10/18</sup>**<br /> - [[ 9-1 ] Asciidoc이란?](#-9-1-asciidoc이란-)<br /> - [[ 9-2 ]Asciidoctor란?](#-9-2-asciidoctor란-) |

<br>

>  **문서와 관련된 자료**
>
> - [Spring Docs와 관련한 주소](https://springdoc.org/)
>
> - [정리된 코드와 관련된 github 주소](../sources/spring-rest-docs)

---



### 1. Spring Rest Docs 장점 <sup>2022/10/18</sup>

> [목차로 바로가기](#목차)

- 테스트 케이스에서 전송하는 API 문서 정보와 Controller에서 구현한 Request Body, Response Body, Query Parmeter등의 정보가 하나라도 일치하지 않으면 테스트 케이스의 실행 결과과 failed되면서 API 문서가 정상적으로 생성이 되지 않습니다. 즉, Controller에 정의되어 있는 Request Body나 Response Body 등의 API 스펙 정보와 일치해야 API 문서가 만들어지기 때문에 문서의 정보 불일치로 인해 발생하는 문제를 방지할 수 있습니다.

<BR>

### 2. Spring Rest Docs API 문서 생성 흐름 <sup>2022/10/18</sup>

> [목차로 바로가기](#목차)

<img src="https://user-images.githubusercontent.com/72078208/196195941-817fa549-f598-493e-b1e3-1537eb31ee2f.png" alt="image" style="zoom:67%;" />

1. 테스트 코드 작성
   - 슬라이스 테스트 코드 작성 : 슬라이스 테스트 코드를 작성합니다.
   - API 스펙 정보 코드 작성 : 슬라이스 테스트 코드 작성 후에, Controller에 정의 되어 있는 API 스펙 정보(Request Body, Response Body, Query Parameter 등)를 코드로 작성합니다.
2. test 태스크 실행
   - 작성된 슬라이스 테스트 코드를 실행합니다. 하나의 테스트 클래스를 실행시켜도 되지만 일반적으로 Gradle의 빌드 태스크(task)중 하나인 test task를 실행 시켜서 API 문서 스니핏(snippet)을 일괄 생성합니다.
   - test 태스크가 passed라면 다음 작업을 진행합니다.
3. API 문서 스니핏 생성
   - 테스트 케이스의 테스트 실행 결과가 passed이면 테스트 코드에 포함된 API 스펙 정보 코드를 기반으로 API 문서 스니핏이 `.adoc`확장자를 가진 파일로 생성됩니다.
   - 스니핏이란, 일반적으로는 코드의 일부 조각을 의미합니다. 하지만 여기서는 문서의 일부 조각을 의미합니다. 스니핏은 테스트 케이스 하나 당 하나의 스니핏이 생성되고 여러개의 스니핏을 모아서 하나의 API 문서를 생성할 수 있습니다.
4. API문서 생성
   - 생성된 API 문서 스니핏을 모아서 하나의 API 문서로 생성합니다.
5. API 문서를 HTML로 변환
   - 생성된 API 문서를 HTML 파일로 변환합니다
   - HTML로 변환된 API 문서는 HTML 파일 자체로 공유가 가능하며 또는 URL을 통해 해당 HTML에 접속해서 확인할 수 있습니다.

<BR>

### 3. Spring Rest Docs 설정 <sup>2022/10/18</sup>

> [목차로 바로가기](#목차)

```tex
- build.gradle설정
- API 문서 스니핏을 사용하기 위한 템플릿 API 문서 생성
```

<br>

- **build.gradle설정**

```java
plugins {
	id 'org.springframework.boot' version '2.7.1'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id "org.asciidoctor.jvm.convert" version "3.3.2"    // (1)
	id 'java'
}

group = 'com.codestates'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

repositories {
	mavenCentral()
}

// (2)
ext {
	set('snippetsDir', file("build/generated-snippets"))
}

// (3)
configurations {
	asciidoctorExtensions
}

dependencies {
       // (4)
	testImplementation 'org.springframework.restdocs:spring-restdocs-mockmvc'
  
  // (5) 
	asciidoctorExtensions 'org.springframework.restdocs:spring-restdocs-asciidoctor'

	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'com.h2database:h2'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	implementation 'org.mapstruct:mapstruct:1.5.1.Final'
	annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.1.Final'
	implementation 'org.springframework.boot:spring-boot-starter-mail'

	implementation 'com.google.code.gson:gson'
}

// (6)
tasks.named('test') {
	outputs.dir snippetsDir
	useJUnitPlatform()
}

// (7)
tasks.named('asciidoctor') {
	configurations "asciidoctorExtensions"
	inputs.dir snippetsDir
	dependsOn test
}

// (8)
task copyDocument(type: Copy) {
	dependsOn asciidoctor            // (8-1)
	from file("${asciidoctor.outputDir}")   // (8-2)
	into file("src/main/resources/static/docs")   // (8-3)
}

build {
	dependsOn copyDocument  // (9)
}

// (10)
bootJar {
	dependsOn copyDocument    // (10-1)
	from ("${asciidoctor.outputDir}") {  // (10-2)
		into 'static/docs'     // (10-3)
	}
}
```

1. (1)에서는 `.adoc` 파일 확장자를 가지는 AsciiDoc 문서를 생성해주는 Asciidoctor를 사용하기 위한 플러그인을 추가합니다.
2. (2)에서는 `ext` 변수의 `set()` 메서드를 이용해서 API 문서 스니핏이 생성될 경로를 지정합니다.
3. (3)에서는 AsciiDoctor에서 사용되는 의존 그룹을 지정하고 있습니다. :asciidoctor task가 실행되면 내부적으로 (3)에서 지정한 ‘`asciidoctorExtensions`’라는 그룹을 지정합니다.
4. (4)에서 'org.springframework.restdocs:spring-restdocs-mockmvc'를 추가함으로써 spring-restdocs-core와 spring-restdocs-mockmvc 의존 라이브러리가 추가됩니다.
5. (5)에서 spring-restdocs-asciidoctor 의존 라이브러리를 추가합니다. (3)에서 지정한 asciidoctorExtensions 그룹에 의존 라이브러리가 포함이 됩니다.
6. (6)에서는 :test task 실행 시, API 문서 생성 스니핏 디렉토리 경로를 설정합니다.
7. (7)에서는 :asciidoctor task 실행 시, Asciidoctor 기능을 사용하기 위해 :asciidoctor task에 `asciidoctorExtensions` 을 설정합니다.
8. (8)은 `:build` task 실행 전에 실행되는 task입니다. `:copyDocument` task가 수행되면 index.html 파일이 `src/main/resources/static/docs` 에 copy 되며, copy된 index.html 파일은 API 문서를 파일 형태로 외부에 제공하기 위한 용도로 사용할 수 있습니다.
   1. (8-1)에서는 `:asciidoctor` task가 실행된 후에 task가 실행 되도록 의존성을 설정합니다.
   2. (8-2)에서는 "`build/docs/asciidoc/"` 경로에 생성되는 index.html을 copy한 후,
   3. (8-3)의 "`src/main/resources/static/docs`" 경로로 index.html을 추가해 줍니다.
9. (9)에서는 `:build` task가 실행되기 전에 `:copyDocument` task가 먼저 수행 되도록 합니다.
10. (10)에서는 애플리케이션 실행 파일이 생성하는 `:bootJar` task 설정입니다.
    1. (10-1)에서는 `:bootJar` task 실행 전에 `:copyDocument` task가 실행 되도록 의존성을 설정합니다.
    2. (10-2)에서는 Asciidoctor 실행으로 생성되는 index.html 파일을 jar 파일 안에 추가해 줍니다. jar 파일에 index.html을 추가해 줌으로써 웹 브라우저에서 접속(`http://localhost:8080/docs/index.html`) 후, API 문서를 확인할 수 있습니다.

<br>

- **API 문서 스니핏을 사용하기 위한 템플릿(또는 source 파일) 생성**

1. Gradle 기반 프로젝트에서는 아래 경로에 해당하는 디렉토리를 생성해 주어야 합니다.
   - `src/docs/asciidoc/`

2. 다음으로 `src/docs/asciidoc/` 디렉토리 내에 비어있는 템플릿 문서(`index.adoc`)를 생성해 주면 됩니다.

<br>

### 4. API 문서 생성을 위한 슬라이스 테스트 케이스 작성 <sup>2022/10/18</sup>

> [목차로 바로가기](#목차)

- **API문서 생성을 위한 테스트 케이스 기본 구조**

```java
package com.codestates.restdocs.member;

import com.codestates.member.controller.MemberController;
import com.codestates.member.mapper.MemberMapper;
import com.codestates.member.service.MemberService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@WebMvcTest(MemberController.class)   // (1)
@MockBean(JpaMetamodelMappingContext.class)   // (2)
@AutoConfigureRestDocs    // (3)
public class MemberControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;  // (4)

    @MockBean
	  // (5) 테스트 대상 Controller 클래스가 의존하는 객체를 Mock Bean 객체로 주입 받기

    @Test
    public void postMemberTest() throws Exception {
        // given
        // (6) 테스트 데이터 

        // (7) Mock 객체를 이용한 Stubbing

        // when
        ResultActions actions =
                mockMvc.perform(
                     // (8) request 전송
                );

        // then
        actions
                .andExpect(// (9) response에 대한 기대 값 검증)
                .andDo(document(
                            // (10) API 문서 스펙 정보 추가
                 ));
    }
}
```

- `@WebMvcTest()` : @SpringBootTest대신 사용했습니다. `@WebMvcTest()`는 Controller를 테스트 하기 위한 전용 어노테이션입니다. `@WebMvcTest` 괄호 안에는 테스트 대상 Controller 클래스를 지정합니다.

  > `@SpringBootTest` vs `@WebMvcTest`
  >
  > `@SpringBootTest` 와 `@WebMvcTest` 의 차이점은 먼저 `@SpringBootTest` 애너테이션은 `@AutoConfigureMockMvc` 과 함께 사용되어 Controller를 테스트 할 수 있는데, 프로젝트에서 사용하는 전체 Bean을 ApplicationContext에 등록하여 사용합니다. 한마디로 테스트 환경을 구성하는 것은 편리하긴 한데 실행 속도가 상대적으로 느립니다.
  >
  > <br>
  >
  > `@WebMvcTest` 애너테이션의 경우 Controller 테스트에 필요한 Bean만 ApplicationContext에 등록하기 때문에 실행 속도는 상대적으로 빠릅니다. 다만, Controller에서 의존하고 있는 객체가 있다면 해당 객체에 대해서 Mock 객체를 사용하여 의존성을 일일이 제거해 주어야 합니다.
  >
  > 결과적으로 `@SpringBootTest` 는 데이터베이스까지 요청 프로세스가 이어지는 통합 테스트에 주로 사용되고, `@WebMvcTest` 는 **Controller를 위한 슬라이스 테스트에 주로 사용**합니다.

- `@MockBean` : 여기서 사용된 MockBean은 JPA에서 사용하는 Bean들을 Mock 객체로 주입해주는 설정입니다. Spring Boot 기반의 테스트는 항상 최상위 패키지 경로에 있는 xxxxxxxApplication 클래스를 찾아서 실행합니다.

  > ```java
  > @EnableJpaAuditing
  > @SpringBootApplication
  > public class Section3Week3RestDocsApplication {
  > 
  > 	public static void main(String[] args) {
  > 		SpringApplication.run(Section3Week3RestDocsApplication.class, args);
  > 	}
  > 
  > }
  > ```
  >
  > `@EnableJpaAuditing` 을 xxxxxxApplication 클래스에 추가하게 되면 JPA와 관련된 Bean을 필요로 하기 때문에 `@WebMvcTest` 애너테이션을 사용해서 테스트를 진행 할 경우에는 위의 코드와 같이JpaMetamodelMappingContext를 Mock 객체로 주입해 주어야 합니다.

- `@AutoConfigureRestDocs` : Spring Rest Docs에 대한 자동 구성을 위해 추가합니다.

- `MockMvc` : 서버를 실행하지 않고 Controller를 테스트 할 수 있는 환경을 지원합니다. 일종의 Spring MVC 테스트 프레임워크 입니다. (간단히 MockMvc 객체를 주입 받습니다.)

- `@MockBean` : Controller 클래스가 의존하는 객체(주로 서비스 클래스, Mapper)의 의존성을 제거하기 위해 `@MockBean` 애너테이션을 사용해서 Mock 객체를 주입 받습니다.

- `(6)` :HTTP request에 필요한 request body나 query parmeter, path variable 등의 데이터를 추가합니다.

- `(7)` : (5)에서 주입 받은 Mock 객체가 동작하도록 Mockito에서 지원하는 given() 등의 메서드로 Stubbing 해 줍니다.

- `(8)` : MockMvc의 `perform()` 메서드로 request를 전송합니다. 

- `(9)` : response를 검증합니다. ResultActions의 .andExpect() 역시 슬라이스 테스트에서 사용했던 방법과 동일하게 검증을 진행하면 됩니다.

- `(10)`:  테스트 수행 이 후, API 문서를 자동 생성하기 위한 해당 Controller 핸들러 메서드의 API 스펙 정보를 `document(…)`에 추가해 줍니다. `document(…)` 메서드는 API 문서를 생성 하기 위해 Spring Rest Docs에서 지원하는 메서드입니다. `.andDo(…)` 메서드는 andExpect()처럼 어떤 검증 작업을 하는 것이 아니라 일반적인 동작을 정의하고자 할 때 사용됩니다.

<br>

### 5. API 문서 생성을 위한 API 스펙 정보 추가 (1/2) <sup>2022/10/18</sup>

> [목차로 바로가기](#목차)

- **MemberController의 postMember() 핸들러 메서드에 대한 API 스펙 정보 추가**

```java
package com.codestates.restdocs.member;

import com.codestates.member.controller.MemberController;
import com.codestates.member.dto.MemberDto;
import com.codestates.member.entity.Member;
import com.codestates.member.mapper.MemberMapper;
import com.codestates.member.service.MemberService;
import com.codestates.stamp.Stamp;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.codestates.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;

    // (1)
    @MockBean
    private MemberService memberService;

    // (2)
    @MockBean
    private MemberMapper mapper;

    @Autowired
    private Gson gson;

    @Test
    public void postMemberTest() throws Exception {
        // (3) given
        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com", "홍길동", "010-1234-5678");
        String content = gson.toJson(post);

        // (4)
        MemberDto.response responseDto =
                new MemberDto.response(1L,
                        "hgd@gmail.com",
                        "홍길동",
                        "010-1234-5678",
                        Member.MemberStatus.MEMBER_ACTIVE,
                        new Stamp());

        // (5)
        given(mapper.memberPostToMember(Mockito.any(MemberDto.Post.class))).willReturn(new Member());

        // (6)
        given(memberService.createMember(Mockito.any(Member.class))).willReturn(new Member());

        // (7)
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);

        // (8) when
        ResultActions actions =
                mockMvc.perform(
                        post("/v11/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(post.getEmail()))
                .andExpect(jsonPath("$.data.name").value(post.getName()))
                .andExpect(jsonPath("$.data.phone").value(post.getPhone()))
                .andDo(document(       // (9) 
                        "post-member",     // (9-1)
                        getRequestPreProcessor(),      // (9-2)
                        getResponsePreProcessor(),     // (9-3)
                        requestFields(             // (9-4)
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"), // (9-5)
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("phone").type(JsonFieldType.STRING).description("휴대폰 번호")
                                )
                        ),
                        responseFields(        // (9-6)
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"), // (9-7)
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.phone").type(JsonFieldType.STRING).description("휴대폰 번호"),
                                        fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("회원 상태"),
                                        fieldWithPath("data.stamp").type(JsonFieldType.NUMBER).description("스탬프 갯수")
                                )
                        )
                ));
    }
}
```

[코드 3-214]

코드 3-214는 MemberController 클래스의 postMember() 핸들러 메서드에 대한 API 스펙 정보를 추가하기 위한 테스트 케이스입니다.

<br>

코드 길이가 꽤 길지만 앞에서 살펴본 `‘API 문서 생성을 위한 테스트 케이스의 기본 구조’`에 구체적인 로직들이 조금 추가되었습니다.

<br>

1. MemberController 클래스의 코드를 확인해 보면 `MemberService` 클래스와 `MemberMapper` 를 핸들러 메서드 안에서 사용하고 있습니다.

   즉, 코드 3-214의 테스트 케이스가 MemberController의 `postMember()` 핸들러 메서드에 요청을 전송하면 `MemberMapper` 를 이용해 MemberDto.Post 객체와 Member 객체 간의 실제 매핑 작업을 진행합니다.

   또한 MemberService 객체를 통해 createMember() 메서드를 호출 함으로써 실제 비즈니스 로직을 수행하고 데이터 액세스 계층의 코드까지 호출할 것입니다.

   **우리에게 필요한 핵심 관심사는 MemberController가 요청을 잘 전달 받고, 응답을 잘 전송하며 요청과 응답이 정상적으로 수행되면 API 문서 스펙 정보를 잘 읽어 들여서 적절한 문서를 잘 생성하느냐 하는 것입니다.**

   따라서 MemberController가 MemberService 와 MemberMapper의 메서드를 호출하지 않도록 관계를 단절 시킬 필요가 있습니다.

   MemberController가 의존하는 객체와의 관계를 단절하기 위해 (1)과 (2)에서 MemberService와 MemberMapper의 Mock Bean을 주입 받습니다.

   두 Mock 객체는 테스트 케이스에서 가짜 메서드를 호출하는데 사용됩니다(**Stubbing**).

<br>

2. (3)은 postMember() 핸들러 메서드에 전송하는 **request body**입니다.

<br>

3. (4)는 postMember() 핸들러 메서드가 응답으로 전송하는 **response body**입니다.

<br>

4. (5), (6), (7)은 여러분들이 Mockito 챕터에서 학습했던 내용입니다. MemberController의 postMember()에서 의존하는 객체의 메서드 호출을 (1)과 (2)에서 주입 받은 Mock 객체를 사용해서 Stubbing하고 있습니다.

<br>

5. (8)은 Controller 슬라이스 테스트 챕터에서 학습했던 내용입니다. MockMvc의 perform() 메서드로 POST 요청을 전송하고 있습니다.

<br>

**많이 돌아왔지만 (9)의 document(…) 메서드가 API 문서를 생성하기 위해서 알아야 될 내용입니다. ^^**

6. (9)의 document(…) 메서드는 API 스펙 정보를 전달 받아서 실질적인 문서화 작업을 수행하는 `RestDocumentationResultHandler`클래스에서 가장 핵심 기능을 하는 메서드입니다.

   - `document()` 메서드의 첫 번째 파라미터인 (9-1)은 **API 문서 스니핏의 식별자 역할**을 하며, (9-1)에서 “`post-member`”로 지정했기 때문에 문서 스니핏은 `post-member` 디렉토리 하위에 생성됩니다.

   - (9-2)와 (9-3)은 문서 스니핏을 생성하기 전에 request와 response에 해당하는 문서 영역을 전처리하는 역할을 하는데 [코드 3-215]와 같이 공통화 한 후, 모든 테스트 케이스에서 재사용 할 수 있도록 했습니다.

```java
public interface ApiDocumentUtils {
   static OperationRequestPreprocessor getRequestPreProcessor() {
       return preprocessRequest(prettyPrint());
    }

    static OperationResponsePreprocessor getResponsePreProcessor() {
        return preprocessResponse(prettyPrint());
    }
}
```

[코드 3-215] ApiDocumentUtils 인터페이스

- `preprocessRequest(prettyPrint())` 는 문서에 표시되는 JSON 포맷의 request body를 예쁘게 표현해 줍니다.
- `preprocessResponse(prettyPrint())` 는 문서에 표시되는 JSON 포맷의 response body를 예쁘게 표현해 줍니다.

<br>

c. (9-4)의 `requestFields(…)`는 문서로 표현될 request body를 의미하며, 파라미터로 전달되는 `List<FieldDescriptor>` 의 원소인 `FieldDescriptor` 객체가 request body에 포함된 데이터를 표현합니다.

<br>

d. (9-5)는 request body를 JSON 포맷으로 표현 했을 때, 하나의 프로퍼티를 의미하는 `FieldDescriptor` 입니다. `type(JsonFieldType.STRING)`은 JSON 프로퍼티의 값이 문자열 임을 의미합니다.

<br>

e. (9-6)의 `responseFields(…)`는 문서로 표현될 response body를 의미하며, 파라미터로 전달되는 `List<FieldDescriptor>` 의 원소인 `FieldDescriptor` 객체가 response body에 포함된 데이터를 표현합니다.

- `JsonFieldType.OBJECT` : JSON 포맷으로 표현 된 프로퍼티의 값이 객체임을 의미합니다.
- `JsonFieldType.NUMBER` : JSON 포맷으로 표현 된 프로퍼티의 값이 int나 long 같은 Number 임을 의미합니다.

<br>

f. (9-7)에서 `fieldWithPath("data.memberId")` 의 `data.memberId` 는 data 프로퍼티의 하위 프로퍼티를 의미합니다.

```json
    {
        "data": {
            "memberId": 1,             // data.memberId
            "email": "hgd@gmail.com",
            "name": "홍길동1",
            "phone": "010-1111-1111",
            "memberStatus": "활동중",
            "stamp": 0
        }
    }
```

<br>

드디어 MemberController의 postMember() 핸들러 메서드에 대한 API 스펙 정보가 테스트 케이스에 포함 되었습니다.

<br>

이제 테스트 케이스를 실행하고, 실행 결과가 “`passed`”이면 우리가 작성한 API 스펙 정보를 기반으로 문서 스니핏이 만들어질 것입니다.

![image](https://user-images.githubusercontent.com/72078208/196257815-15df3764-a9b7-4537-a7fc-d756896a5c61.png)

테스트 케이스 실행 후, [그림 3-85]와 같이 문서 스니핏이 생성되었습니다.

![image](https://user-images.githubusercontent.com/72078208/196257890-89ee4df3-aa40-4376-92b2-d082785f0e5e.png)

[그림 3-86]은 생성된 문서 스니핏 중에서 http-request.adoc 파일을 오픈한 모습입니다.

왼쪽은 http-request.adoc에 작성된 내용이며, 오른쪽은 Asciidoc 형태로 작성된 내용이 문서로 렌더링 된 모습입니다.

<br>

> Asciidoc에 대해서는 뒤에서 학습하게 됩니다.



### 5. API 문서 생성을 위한 API 스펙 정보 추가 (2/2) <sup>2022/10/18</sup>

> [목차로 바로가기](#목차)

- **MemberController의 patchMember() 핸들러 메서드에 대한 API 스펙 정보 추가**

- 이번에는 MemberController의 patchMember() 핸들러 메서드에 대한 API 스펙 정보를 테스트 케이스에 추가해 보겠습니다.

```java
package com.codestates.restdocs.member;

import com.codestates.member.controller.MemberController;
import com.codestates.member.dto.MemberDto;
import com.codestates.member.entity.Member;
import com.codestates.member.mapper.MemberMapper;
import com.codestates.member.service.MemberService;
import com.codestates.stamp.Stamp;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;

import static com.codestates.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    @Autowired
    private Gson gson;

    ...
    ...

    @Test
    public void patchMemberTest() throws Exception {
        // given
        long memberId = 1L;
        MemberDto.Patch patch = new MemberDto.Patch(memberId, "홍길동", "010-1111-1111", Member.MemberStatus.MEMBER_ACTIVE);
        String content = gson.toJson(patch);

        MemberDto.response responseDto =
                new MemberDto.response(1L,
                        "hgd@gmail.com",
                        "홍길동",
                        "010-1111-1111",
                        Member.MemberStatus.MEMBER_ACTIVE,
                        new Stamp());

        // willReturn()이 최소한 null은 아니어야 한다.
        given(mapper.memberPatchToMember(Mockito.any(MemberDto.Patch.class))).willReturn(new Member());

        given(memberService.updateMember(Mockito.any(Member.class))).willReturn(new Member());

        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);
        
        // when
        ResultActions actions =
                mockMvc.perform(
                            patch("/v11/members/{member-id}", memberId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(patch.getMemberId()))
                .andExpect(jsonPath("$.data.name").value(patch.getName()))
                .andExpect(jsonPath("$.data.phone").value(patch.getPhone()))
                .andExpect(jsonPath("$.data.memberStatus").value(patch.getMemberStatus().getStatus()))
                .andDo(document("patch-member",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        pathParameters(              // (1)
                                parameterWithName("member-id").description("회원 식별자")
                        ),
                        requestFields(
                                List.of(
                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 식별자").ignored(),    // (2)
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름").optional(),    // (3)
                                        fieldWithPath("phone").type(JsonFieldType.STRING).description("휴대폰 번호").optional(),
                                        fieldWithPath("memberStatus").type(JsonFieldType.STRING).description("회원 상태: MEMBER_ACTIVE / MEMBER_SLEEP / MEMBER_QUIT").optional()
                                )
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.phone").type(JsonFieldType.STRING).description("휴대폰 번호"),
                                        fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("회원 상태: 활동중 / 휴면 상태 / 탈퇴 상태"),
                                        fieldWithPath("data.stamp").type(JsonFieldType.NUMBER).description("스탬프 갯수")
                                )
                        )
                ));
    }
}
```

- [코드 3-216] MemberController 클래스의 patchMember() 핸들러 메서드에 대한 API 스펙 정보 추가

코드 3-216에서는 MemberController 클래스의 patchMember() 핸들러 메서드에 대한 API 스펙 정보를 추가했습니다.

<br>

postMember() 핸들러 메서드에 대한 테스트 케이스와 크게 달라진 건 없지만 몇가지 추가된 부분이 있습니다.

1. (1)에서는 API 스펙 정보 중에서 URL의 **path variable**의 정보를 추가했습니다. MemberController의 patchMember()와 getMember()는 “`/v11/members/{member-id}`”와 같은 요청 URL에 **path variable**이 있는 사실은 우리가 이미 잘 알고 있는 내용입니다.

2. `memberId`의 경우, `path variable` 정보로 memberId를 전달 받기 때문에 MemberDto.Patch DTO 클래스에서 **request body에 매핑되지 않는 정보**입니다.

   따라서 (2)와 같이 `ignored()`를 추가해서 **API 스펙 정보에서 제외**했습니다.

3. **회원 정보는** 모든 정보를 다 수정해야만 하는 것이 아니라 **선택적으로 수정할 수 있어야 합니다.** 즉, 회원 이름, 휴대폰 번호, 회원 상태 중에서 수정하고 싶은 것만 선택적으로 수정할 수 있어야하기 때문에 (3)과 같이 `optional()`을 추가해서 API 스펙 정보에서 필수가 아닌 선택 정보로 설정 합니다.

<br>

### 6. API 문서 템플릿 생성을 위한 디렉토리 및 템플릿 문서 생성 <sup>2022/10/18</sup>

> [목차로 바로가기](#목차)

>  지금까지 Controller의 테스트 케이스에 API 스펙 정보를 추가해서 API 문서 스니핏을 생성해 보았습니다.
>
>  생성한 API 문서 스니핏을 하나로 모아서 실제로 외부에 공개할 수 있는 API 문서를 만들어 보겠습니다.

- 생성한 API 문서 스피닛은 문서 일부에 포함되는 조각 모음입니다. 이 조각 모음을 제대로 된 문서로 만들기 위해서는 스피닛을 포함하는 템플릿 문서가 필요합니다.

![image](https://user-images.githubusercontent.com/72078208/196261500-2208075b-d0eb-4580-989a-b92a3079d208.png)

만약 [Spring Rest Docs란?] 챕터에서 설명한대로 "`index.adoc`"파일을 아직 생성하지 않았다면, [그림 3-87]과 같이 “`src/docs/asciidoc`” 디렉토리를 생성하고 비어 있는 “`index.adoc`” 파일을 생성합니다.

> Gradle 프로젝트의 경우, 템플릿 문서가 위치하는 디폴트 경로가 “`src/docs/asciidoc`”라는 사실을 기억하기 바랍니다.

<br>

이제 index.adoc 템플릿 문서에 API 문서를 위한 기본적인 내용을 추가해 봅시다.

<br>

### 7. 템플릿 문서 내용 추가 <sup>2022/10/18</sup>

> [목차로 바로가기](#목차)

```java
= 커피 주문 애플리케이션    // (1)
:sectnums:
:toc: left                
:toclevels: 4
:toc-title: Table of Contents
:source-highlighter: prettify

Hwang Jung Sik <jungsik.hwang@codestates.com>   // (2)

v1.0.0, 2022.04.08    // (3)

// (4)
***
== MemberController
=== 회원 등록
.curl-request
include::{snippets}/post-member/curl-request.adoc[]     

.http-request
include::{snippets}/post-member/http-request.adoc[]

.request-fields
include::{snippets}/post-member/request-fields.adoc[]

.http-response
include::{snippets}/post-member/http-response.adoc[]

.response-fields
include::{snippets}/post-member/response-fields.adoc[]

=== 회원 정보 수정
.curl-request
include::{snippets}/patch-member/curl-request.adoc[]

.http-request
include::{snippets}/patch-member/http-request.adoc[]

.request-fields
include::{snippets}/patch-member/request-fields.adoc[]

.http-response
include::{snippets}/patch-member/http-response.adoc[]

.response-fields
include::{snippets}/patch-member/response-fields.adoc[]
```

코드 3-217은 Asciidoc 문법으로 작성된 템플릿 문서입니다.

1. (1)은 API 문서의 제목입니다.
2. (2)는 API 문서를 생성한 이의 정보입니다.
3. (1)과 (2) 사이에 있는 항목은 API 문서의 목차와 관련된 내용입니다. 자세한 내용은 Asciidoc에 대한 챕터에서 살펴보겠습니다.
4. (3)은 API 문서의 생성 날짜입니다.
5. (4)부터 우리가 테스트 케이스 실행을 통해 생성한 API 문서 스니핏을 사용하는 부분입니다. 템플릿 문서에서 스니핏을 사용하는 방법은 정해져있습니다. ’`include::{snippets}/스니핏 문서가 위치한 디렉토리/스니핏 문서파일명.adoc[]`’

<br>

템플릿 문서에 포함되지 않은 스니핏이 있는데 필요하다면 추가해서 사용할 수 있습니다.

여러분이 직접 추가한 후, 확인해보세요.

<br>

기본적인 템플릿 문서의 작성은 끝났습니다.

이제 Gradle의 `:build` 또는 `:bootJar` task 명령을 실행해서 `index.adoc` 파일을 `index.html` 파일로 변환하면 됩니다.

<br>

### 8. 템플릿 문서를 HTML 파일로 변환 <sup>2022/10/18</sup>

> [목차로 바로가기](#목차)

![image](https://user-images.githubusercontent.com/72078208/196261885-f27a81ba-ac47-4de7-be49-9aa462ffdead.png)

IntelliJ 우측 상단의 [Gradle] 탭을 클릭한 후, [그림 3-88]과 같이 `:bootJar` 또는 `:build` task 명령을 더블 클릭합니다.

![image](https://user-images.githubusercontent.com/72078208/196261942-d5d9fc01-c8cc-4225-b3d4-2c26caad6c20.png)

`:bootJar` 또는 `:build` task 명령이 실행되고 정상적으로 빌드가 종료되면 [그림 3-89]와 같이 ‘`src/main/resources/static/docs`’ 디렉토리에 index.adoc 파일을 이용해 변환된 index.html 파일이 생성됩니다.

이제 마지막입니다.

IntelliJ에서 애플리케이션을 실행하고 아래 URL을 웹 브라우저에 입력합니다.

- `http://localhost:8080/docs/index.html`

<br>

[그림 3-90]과 같은 API 문서가 화면에 보인다면 여러분은 이제 본격적으로 Spring Rest Docs를 이용해서 API 문서를 생성할 준비가 된 것입니다. ^^

![image](https://user-images.githubusercontent.com/72078208/196262095-da486263-8be2-4779-9e11-447c157d2117.png)

![image](https://user-images.githubusercontent.com/72078208/196262197-6a16fb5a-54d9-4be0-ad2a-798aa77ade89.png)

![image](https://user-images.githubusercontent.com/72078208/196262229-800bb044-5c1c-4b72-962c-f755fbcd3038.png)

![image](https://user-images.githubusercontent.com/72078208/196262324-3d158d74-6837-4bca-bc55-7a42b4e30333.png)

<BR>

### 9. Spring Rest Docs에서의 Asciidoc <sup>2022/10/18</sup>

> [목차로 바로가기](#목차)

#### [ 9-1 Asciidoc이란? ]

**Asciidoc**이란 무엇일까요?

**Asciidoc**은 Spring Rest Docs를 통해 생성되는 텍스트 기반 문서 포맷입니다.

<br>

**Asciidoc** 포맷을 사용해서 메모, 문서, 기사, 서적, E-Book, 웹 페이지, 매뉴얼 페이지, 블로그 게시물 등을 작성할 수 있으며 Asciidoc 포맷으로 작성된 문서는 HTML, PDF, EPUB, 매뉴얼 페이지를 포함한 다양한 형식으로 변환될 수 있습니다.

<br>

또한 **Asciidoc**은 주로 **기술 문서 작성을 위해 설계된 가벼운 마크업 언어**이기도 합니다.

<br>

Spring Rest Docs를 통해 만들어지는 문서 스니핏과 이 문서 스니핏을 사용하는 템플릿 문서는 Asciidoc 포맷의 문서로 이루어져 있기 때문에 우리가 제공하는 API 문서를 사용하는 이들이 직관적으로 API 문서를 이해할 수 있는 수준 정도의 Asciidoc 기본 문법은 알고 있는 것이 좋습니다.

<br>

우리가 생성한 API 문서가 조금 더 세련되고, 가독성 좋은 문서로 만들어질 수 있도록 가벼운 마음으로 Asciidoc의 문법을 간단하게 살펴보겠습니다.

<br>

### ✔ 목차 구성

```
= 커피 주문 애플리케이션     // (1)
:sectnums:                  // (2)
:toc: left                  // (3)
:toclevels: 4               // (4)
:toc-title: Table of Contents   // (5)
:source-highlighter: prettify   // (6)

Hwang Jung Sik <jungsik.hwang@codestates.com>

v1.0.0, 2022.07.10
```

- [adoc-1] 목차구성

[adoc-1]은 Asciidoc 문법으로 목차를 구성하는 방법입니다.



(1) 문서의 제목을 작성하기 위해서는 `=`를 추가하면 됩니다. `====`와 같이 `=`의 개수가 늘어날 수록 글자는 작아집니다.

(2) 목차에서 각 섹션에 넘버링을 해주기 위해서는 `:sectnums:` 를 추가하면 됩니다.

(3) `:toc:` 는 목차를 문서의 어느 위치에 구성할 것인지를 설정합니다. 여기서는 문서의 왼쪽에 목차가 표시되도록 `left`를 지정했습니다.

(4) `:toclevels:` 은 목차에 표시할 제목의 level을 지정합니다. 여기서는 4로 지정했기 때문에 `====` 까지의 제목만 목차에 표시됩니다.

(5) `:toc-title:` 은 목차의 제목을 지정할 수 있습니다.

(6) `:source-highlighter:` 문서에 표시되는 소스 코드 하일라이터를 지정합니다. 여기서는 `prettify`를 지정했습니다.

<br>

[adoc-1]에서 작성된 문서는 아래의 [그림 3-91]과 같이 화면에 표시됩니다.

![image](https://user-images.githubusercontent.com/72078208/196262988-edce5689-10d2-40f7-a715-a4b44946eb27.png)

<br>

### ✔ 박스 문단 사용하기

```
***     // (1)
API 문서 개요
 // (2)
 이 문서는 39기 백엔드 수강생들과 Spring MVC 기반의 REST API 기반 애플리케이션에 대해 직접 학습하며 만들어 가는 샘플 애플리케이션입니다.
 샘플 애플리케이션을 사용해보고자 하는 분들은 이 문서를 통해 API의 구체적인 사용법을 알 수 있습니다.

***
```

- [adoc-2] 박스 문단 구성

[adoc-2]와 같이 API 문서에 박스 문단을 구성해서 API 문서에 대한 설명을 추가할 수 있습니다.

(1) *** 는 단락을 구분 지을 수 있는 수평선을 추가해 줍니다.

(2) 문단의 제목 다음에 한 라인을 띄우고 한 칸 들여쓰기의 문단을 작성하면 박스 문단을 사용할 수 있습니다.

<br>

[adoc-2]에서 작성된 문서는 아래의 [그림 3-92]와 같이 화면에 표시됩니다.

![image](https://user-images.githubusercontent.com/72078208/196263193-892af646-e71f-4a0a-a839-6ecf49fa7b2d.png)

<br>

### ✔ 경고 문구 추가

```
***
API 문서 개요

 이 문서는 39기 백엔드 수강생들과 Spring MVC 기반의 REST API 기반 애플리케이션에 대해 직접 학습하며 만들어 가는 샘플 애플리케이션입니다.
 샘플 애플리케이션을 사용해보고자 하는 분들은 이 문서를 통해 API의 구체적인 사용법을 알 수 있습니다.
// (1)
CAUTION: 이 문서는 학습용으로 일부 기능에 제한이 있습니다. 기능 제한 사항에 대해 알고 싶다면 담당자에게 문의 하세요

***
```

- [adoc-3] 경고 문구 추가 예

(1) `CAUTION:` 을 사용해서 경고 문구를 추가할 수 있습니다. 이 외에 `NOTE:` , `TIP:` , `IMPORTANT:` , `WARNING:` 등을 사용할 수 있습니다.

화면에 어떻게 표시되는지는 여러분이 한번 문서에 추가해 보세요.

<br>

[adoc-3]에서 작성된 경고 문구는 아래의 [그림 3-93]과 같이 화면에 표시됩니다.

![image](https://user-images.githubusercontent.com/72078208/196263429-dce1d75e-cd1e-4041-bcf6-b55c9b2cdc3c.png)

<br>

### ✔ URL Scheme 자동 인식

다음과 같은 URL Scheme는 Asciidoc에서 자동으로 인식하여 링크가 설정됩니다.

- http
- https
- ftp
- irc
- mailto
- hgd@gmail.com

<br>

### ✔ 이미지 추가

API 문서에 이미지를 추가하고 싶다면 아래의 [adoc-4]와 같이 `image::` 를 사용해서 추가할 수 있습니다.

```
image::https://spring.io/images/spring-logo-9146a4d3298760c2e7e49595184e1975.svg[spring]
```

<br>

#### [ 9-2 Asciidoctor란? ]

> [목차로 바로가기](#목차)

Asciidoctor는 AsciiDoc 포맷의 문서를 파싱해서 HTML 5, 매뉴얼 페이지, PDF 및 EPUB 3 등의 문서를 생성하는 툴입니다.

<br>

> Spring Rest Docs에서는 Asciidoc 포맷의 문서를 HTML 파일로 변환하기 위해 내부적으로 Asciidoctor를 사용하고 있습니다.
>
> 우리가 Asciidoctor의 구체적인 사용법을 알 필요는 없지만 Spring Rest Docs를 통해 생성되는 문서 스니핏을 템플릿 문서에 포함해서 하나의 API 문서로 통합하는 방법 정도는 알고 있는게 좋습니다.

<br>

### ✔ 문서 스니핏을 템플릿 문서에 포함 시키기

이 전 챕터에서 테스트 케이스 실행을 통해 생성된 문서 스니핏(snippet)을 템플릿 문서(index.adoc)에 포함(include) 시켰습니다.

이렇게 템플릿 문서에 포함된 스닛핏은 애플리케이션 빌드 타임에 내부적으로 Asciidoctor가 `index.adoc`을 `index.html`로 변환 후, 특정 디렉토리(`src/main/resources/static/docs`)에 생성해 줍니다.

<br>

지금부터 Asciidoctor가 템플릿 문서 변환 작업을 수행하기 이 전 단계인 **‘템플릿 문서에 스니핏을 포함하는 방법’**을 간단히 살펴보겠습니다.

```
***
== MemberController
=== 회원 등록
.curl-request       // (1)
include::{snippets}/post-member/http-request.adoc[]    // (2)

.request-fields
include::{snippets}/post-member/request-fields.adoc[]

.http-response
include::{snippets}/post-member/http-response.adoc[]

.response-fields
include::{snippets}/post-member/response-fields.adoc[]

...
...
```

- [adoc-4] 템플릿(index.adoc) 문서에 스니핏 포함 예

[adoc-4]는 테스트 케이스 실행을 통해 생성된 스니핏을 포함하는 템플릿 문서의 일부입니다.

1. (1)의 `.curl-request` 에서 `.`은 하나의 스니핏 섹션 제목을 표현하기 위해 사용합니다. `curl-request` 은 섹션의 제목이며, 원하는 대로 수정하면 됩니다.

2. (2)에서 `include`는 Asciidoctor에서 사용하는 매크로(macro) 중 하나이며, 스니핏을 템플릿 문서에 포함할 때 사용합니다. `::` 은 매크로를 사용하기 위한 표기법입니다. `{snippets}`는 해당 스니핏이 생성되는 디폴트 경로를 의미하며, 우리가 아래의 [adoc-5] build.gradle 파일에 설정한 `snippetsDir` 변수를 참조하는데 사용할 수 있습니다.

![image](https://user-images.githubusercontent.com/72078208/196263905-750c0e82-c5aa-4a92-a798-8105c69541e2.png)

Asciidoctor에서는 어떤 작업을 처리하기 위한 용어로 **매크로(macro)**라는 용어를 사용합니다.

매크로(macro)는 일반적으로 어떤 반복되는 작업을 자동화한다는 의미를 가지며, 우리가 흔히 알고 있는 매크로에는 엑셀 등의 스프레드시트에서 사용할 수 있는 매크로 기능이 있습니다.