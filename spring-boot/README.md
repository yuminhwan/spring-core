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
