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
