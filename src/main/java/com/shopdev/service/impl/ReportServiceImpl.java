package com.shopdev.service.impl;

import com.shopdev.dto.response.DashboardReportResponse;
import com.shopdev.model.OrderEntity;
import com.shopdev.model.ProductEntity;
import com.shopdev.repository.OrderRepository;
import com.shopdev.repository.ProductRepository;
import com.shopdev.service.ReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReportServiceImpl implements ReportService {
    OrderRepository orderRepository;
    ProductRepository productRepository;

    @Override
    public DashboardReportResponse dashboard() {
        List<OrderEntity> orders = orderRepository.findAll();
        Map<Integer, BigDecimal> revenueByMonth = orders.stream().collect(
                Collectors.groupingBy(o -> o.getCreatedAt().atZone(ZoneId.systemDefault()).getMonthValue(),
                        Collectors.mapping(OrderEntity::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)))
        );
        Map<Integer, BigDecimal> revenueByYear = orders.stream().collect(
                Collectors.groupingBy(o -> o.getCreatedAt().atZone(ZoneId.systemDefault()).getYear(),
                        Collectors.mapping(OrderEntity::getAmount,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)))
        );

        List<ProductEntity> products = productRepository.findAll();
        Map<Integer, Integer> stockByMonth = products.stream().collect(
                Collectors.groupingBy(p -> p.getCreatedAt().atZone(ZoneId.systemDefault()).getMonthValue(),
                        Collectors.summingInt(p -> p.getStockQuantity() != null ? p.getStockQuantity() : 0))
        );
        Map<Integer, Integer> stockByYear = products.stream().collect(
                Collectors.groupingBy(p -> p.getCreatedAt().atZone(ZoneId.systemDefault()).getYear(),
                        Collectors.summingInt(p -> p.getStockQuantity() != null ? p.getStockQuantity() : 0))
        );

        List<DashboardReportResponse.MonthlyAmount> monthlyRevenue = new ArrayList<>();
        for (int m = 1; m <= 12; m++) {
            monthlyRevenue.add(DashboardReportResponse.MonthlyAmount.builder()
                    .month(m)
                    .amount(revenueByMonth.getOrDefault(m, BigDecimal.ZERO))
                    .build());
        }

        List<DashboardReportResponse.YearlyAmount> yearlyRevenue = revenueByYear.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> DashboardReportResponse.YearlyAmount.builder().year(e.getKey()).amount(e.getValue()).build())
                .toList();

        List<DashboardReportResponse.MonthlyStock> monthlyStock = new ArrayList<>();
        for (int m = 1; m <= 12; m++) {
            monthlyStock.add(DashboardReportResponse.MonthlyStock.builder()
                    .month(m)
                    .stock(stockByMonth.getOrDefault(m, 0))
                    .build());
        }

        List<DashboardReportResponse.YearlyStock> yearlyStock = stockByYear.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> DashboardReportResponse.YearlyStock.builder().year(e.getKey()).stock(e.getValue()).build())
                .toList();

        return DashboardReportResponse.builder()
                .monthlyRevenue(monthlyRevenue)
                .yearlyRevenue(yearlyRevenue)
                .monthlyStock(monthlyStock)
                .yearlyStock(yearlyStock)
                .build();
    }
}


