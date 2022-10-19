package com.example.springdb.exception.basic;

import static org.assertj.core.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class UnCheckedAppTest {

    /**
     * Controller, Service쪽에서 기술에 종속적인 예외에 대해
     * 몰라도 된다. 또한, 복구 불가능 예외를 신경쓰지 않아도 된다.
     * 다만, 복구 불가능 예외는 일관성 있게 공통으로 처리 해야함
     * -> 필터, 인터셉터, @ControllerAdvice
     */
    @DisplayName("언체크 예외로 해결")
    @Test
    void unchecked() {
        Controller controller = new Controller();
        assertThatThrownBy(() -> controller.request())
            .isInstanceOf(Exception.class);
    }

    @DisplayName("스택 트레이스 테스트")
    @Test
    void printEx() {
        Controller controller = new Controller();

        try {
            controller.request();
        } catch (Exception e) {
            log.info("ex", e);
        }
    }

    static class Controller {
        Service service = new Service();

        public void request() {
            service.logic();
        }
    }

    static class Service {
        Repository repository = new Repository();
        NetworkClient networkClient = new NetworkClient();

        public void logic() {
            repository.call();
            networkClient.call();
        }
    }

    static class NetworkClient {
        public void call() {
            throw new RuntimeConnectException("연결 실패");
        }
    }

    static class Repository {

        /**
         * 예외를 포함하지 않음으로써 SQLException과 스택 트레이스를 확인할 수 없음.
         * RuntimeException만 확인 가능하기 때문 뭐땜에 예외가 발생한 지 모르게 됨.
         */
        public void call() {
            try {
                runSQL();
            } catch (SQLException e) {
                throw new RuntimeSQLException();
            }
        }

        public void runSQL() throws SQLException {
            throw new SQLException("ex");
        }
    }

    static class RuntimeConnectException extends RuntimeException {
        public RuntimeConnectException(String message) {
            super(message);
        }
    }

    static class RuntimeSQLException extends RuntimeException {

        public RuntimeSQLException() {
        }

        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }

}
