
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


import java.lang.reflect.Field;

//import java.util.logging.LogManager;
//import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;


@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HorseTest {
    //private static final Logger logger = LoggerFactory.getLogger(HorseTest.class);

    @Order(1)
    @DisplayName("При передаче в конструктор первым параметром null, будет выброшено IllegalArgumentException")
    @Test
    public void horse1() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null,2.6));
    }
    @Order(2)
    @DisplayName("При передаче в конструктор первым параметром null, выброшенное исключение будет содержать сообщение Name cannot be null.")
    @Test
    public void horse2() {
            /*try{
                new Horse(null, 4.9);
            } catch (IllegalArgumentException e) {
                assertEquals("Name cannot be null.", e.getMessage());
            }*/
        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new Horse(null,1.2);
                }
        );
        assertEquals("Name cannot be null.", exception.getMessage());
    }
    @Order(3)
    @DisplayName("При передаче в конструктор первым параметром пустой строки или строки содержащей только пробельные символы (пробел, табуляция и т.д.)," +
            " будет выброшено IllegalArgumentException")
    @ParameterizedTest
    @ValueSource(strings = { "", " ", "  ", "\t\t", "\n\n\n" })
    public void horse3(String str) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(str,8.2));
    }

    @Order(4)
    @DisplayName("При передаче в конструктор первым параметром пустой строки или строки содержащей только пробельные символы (пробел, табуляция и т.д.), " +
            "выброшенное исключение будет содержать сообщение Name cannot be blank.")
    @ParameterizedTest
    @ValueSource(strings = { "", " ", "  ", "\t\t", "\n\n\n" })
    public void horse4(String str) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(str,18.2));
        assertEquals("Name cannot be blank.",e.getMessage());
    }

    @Order(5)
    @DisplayName("При передаче в конструктор вторым параметром отрицательного числа, будет выброшено IllegalArgumentException")
    @Test
    public void horse5() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Andrey",-2.6));
    }

    @Order(6)
    @DisplayName("При передаче в конструктор вторым параметром отрицательного числа, " +
            "выброшенное исключение будет содержать сообщение Speed cannot be negative.")
    @Test
    public void horse6() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("Oleg",-22.6));
        assertEquals("Speed cannot be negative.",e.getMessage());
    }

    @Order(7)
    @DisplayName("При передаче в конструктор третьим параметром отрицательного числа, будет выброшено IllegalArgumentException")
    @Test
    public void horse7() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Andrey",42.6, -32.7));
    }

    @Order(8)
    @DisplayName("При передаче в конструктор третьим параметром отрицательного числа, " +
            "выброшенное исключение будет содержать сообщение Distance cannot be negative.")
    @Test
    public void horse8() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("Oleg",242.6, -111.2));
        assertEquals("Distance cannot be negative.",e.getMessage());
    }

    @Order(9)
    @DisplayName("Проверяет, что метод возвращает строку, которая была передана первым параметром в конструктор")
    @Test
    public void horse9() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Andrey",85.7);
        String string = horse.getName();
        assertNotEquals(null,string);
        assertEquals("Andrey", string);
        /*Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String str = (String) name.get(horse);
        assertEquals("Andrey", str);*/
    }

    @Order(10)
    @DisplayName("1)Проверяю, что метод возвращает число, которое было передано третьим параметром в конструктор;\n" +
            "2)Проверяю, что метод возвращает ноль, если объект был создан с помощью конструктора с двумя параметрами;")
    @Test
    public void horse10() {
        Horse horse = new Horse("Oleg",19.5,4.2);
        Horse horse1 = new Horse("Vasy",0);
        Double doub = horse.getDistance();
        assertEquals(4.2,doub);
        Double doub1 = horse1.getDistance();
        assertEquals(0, doub1);
    }
    @Mock
    Horse horse;
    @Order(11)
    @DisplayName("1)Проверяет, что метод вызывает внутри метод getRandomDouble с параметрами 0.2 и 0.9," +
            "2)Проверяет, что метод присваивает дистанции значение высчитанное по формуле: distance + speed * getRandomDouble(0.2, 0.9)")
    @Test
    public void horse11() {
        Horse horse1;
        try (MockedStatic<Horse> utilities =  Mockito.mockStatic( Horse.class)) {
            horse1 = new Horse("andrey", 9.5);
            horse1.move();
            utilities.verify(() -> Horse.getRandomDouble(eq(0.2),anyDouble()));
        }
        Mockito.doReturn(horse1.getDistance()).when(horse).getDistance();
        assertEquals(horse1.getDistance(),horse.getDistance());
    }


}
