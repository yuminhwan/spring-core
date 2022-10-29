package com.example.java8to11.optional;

import static org.assertj.core.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 1. null을 확인하기 위해 if(progress != null)로 하게 되는 데 깜빡하게 되면 문제가 발생한다.
 * 2. null 자체를 반환하는 게 잘못된 것이다.
 * -> 예외를 던진다. -> checked는 예외 처리를 강제하게 된다. 스택 트레이스를 채우는 것 자체가 리소스 낭비이다.
 * -> null을 던진다 -> 클라이언트 쪽에서 확인해야 한다.
 *
 * 자바 8부터 Optional를 통해 해결한다.
 *
 * 1. 리턴 타입으로만 사용한다. -> 메서드 파라미터로 쓴다고 가정하면 Optional 자체가 null이 될 수 있고 안의 데이터가 null인지도 확인해야 한다.
 *                              -> 즉, 메소드 매개변수 타입, 맵의 키 타입, 인스턴스 필드 타입으로 쓰지 말자.
 * 2. Optional을 리턴하는 메소드에서 null을 리턴하지 말자.
 * 3. 프리미티브 타입용 Optional을 따로 있다. OptionalInt, OptionalLong,...-> 프리미티브 타입은 박싱, 언박싱이 벌어지게 된다.
 * 4. Collection, Map, Stream Array, Optional은 Opiontal로 감싸지 말 것. -> 한번 더 warp하는 것이다. 그 자체로 비어있는 지 확인 가능한 컨테이너 성격이다.
 *                                                                       -> emptyColletion 등을 반환하자.
 */
class OptionalTest {

    @DisplayName("진행정도는 설정해주지 않으면 null이다. - 클라이언트쪽에서 확인해야한다.")
    @Test
    void progressIsNull() {
        // given
        OnlineClassCanNull springBoot = new OnlineClassCanNull(1, "spring boot", true);

        // when
        Duration studyDuration = springBoot.getProgress().getStudyDuration();

        // then
        assertThat(studyDuration).isNull();
    }
}
