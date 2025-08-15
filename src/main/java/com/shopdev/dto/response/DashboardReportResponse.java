package com.shopdev.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardReportResponse {
    List<MonthlyAmount> monthlyRevenue;
    List<YearlyAmount> yearlyRevenue;
    List<MonthlyStock> monthlyStock;
    List<YearlyStock> yearlyStock;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class MonthlyAmount { int month; BigDecimal amount; }
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class YearlyAmount { int year; BigDecimal amount; }
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class MonthlyStock { int month; Integer stock; }
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class YearlyStock { int year; Integer stock; }
}


