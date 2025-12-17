package helpers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RawOfStringsUnpacker {
    public static List<String> unpack(String stringRaw){
        List<String> unpackedStrings = Arrays.stream(stringRaw.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        return unpackedStrings;
    }
}
