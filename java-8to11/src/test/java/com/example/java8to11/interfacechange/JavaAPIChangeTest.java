package com.example.java8to11.interfacechange;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * A 인터페이스가 있고 그걸 구현한 abstract B 클래스가 있을 때 상속받는 클래스는 원하는 메서드만 오버라이딩 해서 사용할 수 있다.
 * -> 인터페이스의 default 메서드가 나오면서 바로 인터페이스에 구현을 할 수 있는 모양으로 바뀌어졌다.
 * -> 상속 강제 X
 *  ex) WebMvcConfigurer - WebMvcConfigurerAdapter(Deprecated)
 */
class JavaAPIChangeTest {

    private List<String> names;

    @BeforeEach
    void setUp() {
        names = new ArrayList<>(List.of("hwan", "yu", "min", "test"));
    }

    @DisplayName("Iterable: forEach")
    @Test
    void forEach() {
        names.forEach(System.out::println);
    }

    @DisplayName("Iterable: spliterator")
    @Test
    void spliterator() {
        Spliterator<String> spliterator = names.spliterator();
        Spliterator<String> stringSpliterator = spliterator.trySplit();

        assertThat(spliterator.estimateSize()).isEqualTo(2);
        assertThat(stringSpliterator.estimateSize()).isEqualTo(2);
    }

    @DisplayName("Collection: stream")
    @Test
    void stream() {
        long count = names.stream()
            .map(String::toUpperCase)
            .filter(s -> s.startsWith("H"))
            .count();

        assertThat(count).isEqualTo(1);
    }

    @DisplayName("Collection: removeIf")
    @Test
    void removeIf() {
        names = new ArrayList<>();
        names.add("hwan");

        names.removeIf(s -> s.startsWith("h"));

        assertThat(names).doesNotContain("hwan");
    }

    @DisplayName("Comparator: reversed")
    @Test
    void reversed() {
        Comparator<String> compareToIgnoreCase = String::compareToIgnoreCase;

        names.sort(compareToIgnoreCase.reversed()
            .thenComparing(Comparator.naturalOrder()));
    }
}
