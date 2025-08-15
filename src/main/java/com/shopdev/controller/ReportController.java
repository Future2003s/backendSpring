package com.shopdev.controller;

import com.shopdev.dto.response.DashboardReportResponse;
import com.shopdev.dto.response.ResponseData;
import com.shopdev.service.ReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/v1/api/reports")
public class ReportController {
    ReportService reportService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public ResponseData<DashboardReportResponse> dashboard() {
        return new ResponseData<>(HttpStatus.OK, "OK", reportService.dashboard());
    }
}


