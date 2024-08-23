package com.projectharpseal.APIcall;

import com.projectharpseal.APIcall.Dto.Date_Return;

import java.time.LocalDate;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {

    // 오늘 이전의 날짜에서 가장 먼저 만나는 달의 첫번째 금요일을 계산하는 메서드
    public static String calculateLastFridayBeforeToday() {
        LocalDate today = LocalDate.now();
        LocalDate FFM = today
                .with(TemporalAdjusters.firstDayOfMonth())  // 이번 달의 첫 번째 날로 이동
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));  // 이번 달 첫 번째 금요일로 이동

        // 만약 첫 번째 금요일이 오늘 이후라면, 전달의 첫 번째 금요일로 이동
        if (FFM.isAfter(today)) {
            FFM = today.minusMonths(1)
                    .with(TemporalAdjusters.firstDayOfMonth())  // 이전 달의 첫 번째 날로 이동
                    .with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));  // 이전 달의 첫 번째 금요일로 이동
        }

        return FFM.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
    public static Date_Return calculateLastYearMonth(){

        LocalDate today = LocalDate.now();
        LocalDate LY = today.minusYears(1);
        String Year = LY.format(DateTimeFormatter.ofPattern("yyyy"));
        String Month = LY.format(DateTimeFormatter.ofPattern("MM"));
        String Day = "Null";
        return new Date_Return(Year, Month, Day);
    }
}
