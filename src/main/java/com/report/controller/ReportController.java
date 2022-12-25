package com.report.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.hibernate.type.LocalDateTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.report.binding.CitizenPlan;
import com.report.binding.SearchRequest;
import com.report.service.ReportService;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ReportController {
    private static LocalTime t=LocalTime.now();
    private static LocalTime time=LocalTime.of(t.getHour(), t.getMinute(),t.getSecond());
    private static LocalDate date = LocalDate.now();
    @Autowired
    private ReportService service;

    @GetMapping("/planName")
    public List<String> getPlanName() {
        return service.getPlanName();
    }

    @GetMapping("/planStatus")
    public List<String> getPlanStatus() {
        return service.getPlanStatus();
    }

    @PostMapping("/getPlan")
    public List<CitizenPlan> getByPlanNameAndStatus(@RequestBody SearchRequest search) {
        List<CitizenPlan> citizenPlan = service.getCitizenPlan(search);
        return citizenPlan;
    }

    @GetMapping("/excel")
    public void generateExcelReport(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=citizenPlan_"+date+" t "+time+".xls";
        response.setHeader(headerKey, headerValue);
        service.exportExcel(response);
    }

    @GetMapping("/pdf")
    public void generatePdfDocument(HttpServletResponse response) throws IOException {

        response.setContentType("application/octet-stream");
        String headerKey = "content-disposition";
        String headerValue = "attachment;filename=ctitzenPlan_"+date+" t "+time+".pdf";
        response.setHeader(headerKey, headerValue);
        service.exportPdf(response);
    }


}
