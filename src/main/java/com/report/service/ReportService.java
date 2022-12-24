package com.report.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.report.binding.CitizenPlan;
import com.report.binding.SearchRequest;

public interface ReportService {

    List<String> getPlanName();

    List<String> getPlanStatus();

    List<CitizenPlan> getCitizenPlan(SearchRequest request);

    void exportExcel(HttpServletResponse response) throws IOException;

    void exportPdf(HttpServletResponse response) throws IOException;
}
