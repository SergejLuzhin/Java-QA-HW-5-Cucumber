package helpers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RawOfStringsUnpacker {
    /**
     * Преобразует строку, содержащую значения, разделённые запятыми, в список строк.
     * <p>
     * Алгоритм:
     * разбивает строку по запятым, удаляет пробелы по краям каждого элемента,
     * отбрасывает пустые значения и возвращает результат в виде списка.
     *
     * @param stringRaw строка с элементами, разделёнными запятыми
     * @return список непустых строковых значений без пробелов по краям
     *
     * @author Сергей Лужин
     */
    public static List<String> unpack(String stringRaw){
        return Arrays.stream(stringRaw.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
