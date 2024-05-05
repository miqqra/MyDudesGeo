package mydudesgeo.common;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DateUtils {

    private final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private static final Map<String, String> monthMap = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("января", "01"),
            new AbstractMap.SimpleEntry<>("февраля", "02"),
            new AbstractMap.SimpleEntry<>("марта", "03"),
            new AbstractMap.SimpleEntry<>("апреля", "04"),
            new AbstractMap.SimpleEntry<>("мая", "05"),
            new AbstractMap.SimpleEntry<>("июня", "06"),
            new AbstractMap.SimpleEntry<>("июля", "07"),
            new AbstractMap.SimpleEntry<>("августа", "08"),
            new AbstractMap.SimpleEntry<>("сентября", "09"),
            new AbstractMap.SimpleEntry<>("октября", "10"),
            new AbstractMap.SimpleEntry<>("ноября", "11"),
            new AbstractMap.SimpleEntry<>("декабря", "12")
    );

    public static ZonedDateTime toDateFromRussian(String date, String time) {
        // date format: dd ruMonth yyyy
        // time format: hh:mm
        return Optional.of(date)
                .map(v -> v.split(" "))
                .map(v -> String.join("-", v[0], monthMap.get(v[1]), v[2]))
                .map(v -> String.join(" ", v, time))
                .map(v -> LocalDateTime.parse(v, dateTimeFormatter))
                .map(v -> ZonedDateTime.of(v, ZoneId.systemDefault()))
                .orElse(null);
    }

    public static ZonedDateTime[] parseTimestampFromRussian(String timeStamp) {
        //format: dd ruMonth yyyy - dd ruMonth yyyy, hh:mm - hh:mm
        String[] datesAndTimes = timeStamp.split(", ");
        String[] dates = datesAndTimes[0].split(" – ");
        String[] times = datesAndTimes[1].split(" - ");
        return new ZonedDateTime[]{
                toDateFromRussian(dates[0], times[0]),
                toDateFromRussian(dates[1], times[1])
        };
    }
}
