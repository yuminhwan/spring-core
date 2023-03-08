## Containerless

### 서블릿 컨테이너 띄우기

- embed tomcat이 있긴 하지만 (org.apache.catalina.startup.Tomcat) Config 등 챙겨야 할 게 많다.
  이를 쉽게 사용할 수 있도록 부트는 TomcatServletWebServerFactory와 같은 객체를 제공해준다.
    - 기본 설정은 되어 있음!
- ServletWebServerFactory, Webserver 추상화 객체

### 서블릿 등록

- ServletContextInitializer -> servlet 등록을 도와주는 객체
- HttpServlet -> 서블릿 구현 객체 , 어떤 동작을 할 지와 어디에 매핑될 지 명시해야함
- 응답에는 상태 코드, 헤더, 바디가 있어야 한다.

### 프론트 컨트롤러

- 모든 Servlet이 요청을 받고 응답하는 식으로 구현하다 보니 중복 코드가 늘어났다.
- Servlet에 요청, 응답을 직접으로 다룬다는 것이 자연스럽지 않다.
    - 한계가 있음
- 프론트 컨트롤러로 해결!
    - 공통 로직 처리
        - 다국어, 인증, 보안 등
    - 위임하여 처리

## 독립 실행형 스프링 애플리케이션

### 스프링 컨테이너

- 스프링 컨테이너 필요사항
    - BO (Business Objects)
    - Configuration Metadata

- ApplicationContext - GenericApplicationContext
- 서블릿쪽에선 빈이 어떻게 만들어졌는 지 관심 X
    - 그냥 가져다 쓴다.
    - 빈 생성은 컨테이너쪽에서 담당
    - 싱글톤 레지스트리

### Dependency Injection

- 소스 코드에선 직접적으로 의존하지 않지만 런타임시에는 직접 의존
    - 어떤 객체에 의존하는 지? -> Assembler가 주입 시킴
    - 즉, 스프링 컨테이너가 Assembler다.

### DispatcherServlet

- servlet containerless
    - 매핑, 요청 파라미터 추출 등 작업이 서블릿 관련 코드와 혼잡되어 있다.
    - 이를 DispatcherServlet가 처리한다.
    - 하지만 매핑에 어떤 작업을 해야할지 모른다. -> 애노테이션 매핑 정보를 사용한다.
        - 빈을 다 뒤져서 웹 요청을 처리할 수 있는 즉, 매핑정보가 있는 클래스(@Controller)를 찾는다.
        - 요청 정보를 추출 -> 매핑 테이블 생성 -> 요청이 오면 해당 메서드로 요청 진행
        - 메서드단까지 뒤질려면 번거롭다 -> 클래스단에서도 확인 가능
            - 스프링은 클래스 단 -> 메서드 단 확인, 클래스 단 정보를 메서드 단 정보에 추가!
    - 반환값에 DispatcherServlet는 기본적으로 view를 찾는다.

### ComponentScan

- 메타 어노테이션 : 어노테이션에 붙은 어노테이션
- 스프링은 컴포넌트 스캔시 메타 어노테이션까지 확인한다. -> @Controller, @Service, @Repository (스테레오타입)