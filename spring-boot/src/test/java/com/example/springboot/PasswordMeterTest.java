package com.example.springboot;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PasswordMeterTest {

    private final PasswordMeter passwordMeter = new PasswordMeter();

    @Test
    void null_입력이면_예외가_발생한다() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> passwordMeter.meter(null));
    }

    @Test
    void 빈_값_입력이면_예외가_발생한다() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> passwordMeter.meter(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcABC123", "123abcABC"})
    void 모든_조건을_충족하면_강함(String pw) {
        assertPasswordStrength(pw, PasswordStrength.STRONG);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcC123", "123abcC", "Cab12"})
    void 길이가_8미만_다른_조건_충족(String password) {
        PasswordStrength expected = PasswordStrength.NORMAL;

        assertPasswordStrength(password, expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcd1234", "1234abcdwefw"})
    void 대문자_없음_다른_조건_충족(String password) {
        assertPasswordStrength(password, PasswordStrength.NORMAL);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ABCDabcde", "abcdQWEXA"})
    void 숫자_없음_다른_조건_충족(String password) {
        assertPasswordStrength(password, PasswordStrength.NORMAL);
    }

    @Test
    void 길이만_충족() {
        assertPasswordStrength("abcdehiohw", PasswordStrength.WEAK);
    }

    @Test
    void 대문자만_충족() {
        assertPasswordStrength("abcABC", PasswordStrength.WEAK);
    }

    @Test
    void 숫자만_충족() {
        assertPasswordStrength("123abc", PasswordStrength.WEAK);
    }

    @Test
    void 아무것도_충족하지_않음() {
        assertPasswordStrength("awef", PasswordStrength.WEAK);
    }

    private void assertPasswordStrength(final String password, final PasswordStrength expected) {
        PasswordStrength result = passwordMeter.meter(password);
        assertThat(result).isEqualTo(expected);
    }
}
