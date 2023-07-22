import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HippodromeTest {
    @Order(1)
    @DisplayName("Проверяем, что при передаче в конструктор null, будет выброшено IllegalArgumentException;")
    @Test
    public void hippodrome1() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }
    @Order(2)
    @DisplayName("Проверяем, что при передаче в конструктор null, " +
            "выброшенное исключение будет содержать сообщение Horses cannot be null.")
    @Test
    public void hippodrome2() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", e.getMessage());
    }
    @Order(3)
    @DisplayName("Проверяем, что при передаче в конструктор пустого списка, будет выброшено IllegalArgumentException;")
    @Test
    public void hippodrome3() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }
    @Order(4)
    @DisplayName("Проверяем, что при передаче в конструктор пустого списка, " +
            "выброшенное исключение будет содержать сообщение Horses cannot be empty.")
    @Test
    public void hippodrome4() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", e.getMessage());
    }
    @Order(5)
    @DisplayName("Проверяем, что метод возвращает список, который содержит те же объекты и в той же последовательности, " +
            "что и список который был передан в конструктор. " +
            "при создании объекта Hippodrome передали в конструктор список из 30 разных лошадей;")
    @Test
    public void hippodrome5() {
        List<Horse> horses = new ArrayList<>();
        for(int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse",i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Order(6)
    @DisplayName("Проверяем, что метод вызывает метод move у всех лошадей. " +
            "При создании объекта Hippodrome передали в конструктор список из 50 моков лошадей и воспользовались методом verify.")
    @Test
    public void hippodrome() {
        List<Horse> horses = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            horses.add(mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }

    @Order(7)
    @DisplayName("Проверяем, что метод возвращает лошадь с самым большим значением distance.")
    @Test
    public void hippodrome7() {
        Horse horse1 = new Horse("Gui",45.2,200.5);
        Horse horse2 = new Horse("Kui",35.2,100.7);
        Horse horse3 = new Horse("Lui",75.2,50.2);
        Horse horse4 = new Horse("Pui",15.2,40.9);
        Hippodrome hippodrome = new Hippodrome(List.of(horse1,horse2,horse3,horse4));
        assertSame(horse1,hippodrome.getWinner());
    }
}
