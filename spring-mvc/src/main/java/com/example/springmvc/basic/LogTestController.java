package com.example.springmvc.basic;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * println -> 무조건 로그가 남게 된다. / 시간이나 스레드 등 부가정보를 하나씩 세팅해야 한다. / 콘솔에만 남긴다. / 성능 저하(내부 버퍼링 등)
 * Slf4j -> 레벨을 달리해서 로깅을 할 수 있다. 보고싶은 레벨을 설정을 하여 볼 수 있다. / 파일이나 네트워크로 전송 가능
 *
 * @Controller -> 반환 값이 String일 경우 뷰 이름으로 인식되어 뷰를 찾고 렌더링하게 됨.
 * @RestController -> 반환 값으로 뷰를 찾는 것이 아닌 HTTP 메시지 바디에 바로 입력 (@ResponseBody)
 */
@Slf4j
@RestController
public class LogTestController {

    // private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "spring";

        System.out.println("name = " + name);

        // + 연산이 늘어난다. -> 쓸모 없는 리소스 사용
        // 계산을 한 뒤 출력을 안하는 꼴
        log.trace(" trace my log = " + name);

        // 파라미터만 넘기기 때문에 연산이 일어나지 않는다.
        // info레벨 기준 trace면 출력안하니 로직이 중지됨.
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info(" info log={}", name);
        log.warn(" warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}
