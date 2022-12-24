package com.report.service;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.report.binding.CitizenPlan;
import com.report.binding.SearchRequest;
import com.report.repo.CitizenPlanRepository;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private CitizenPlanRepository repo;

    @Override
    public List<String> getPlanName() {
        return repo.getByPlanNames();
    }

    @Override
    public List<String> getPlanStatus() {
        return repo.getByPlanStatues();
    }

    @Override
    public List<CitizenPlan> getCitizenPlan(SearchRequest request) {
//		if (request.getPlanName() != null && request.getPlanStatus() != null) {
//			return repo.getByPlanNameAndStatus(request.getPlanName(), request.getPlanStatus());
//		}
//		if (request.getPlanName() == null && request.getPlanStatus() != null) {
//			return repo.getByPlanStatus(request.getPlanStatus());
//		}
//		if (request.getPlanName() != null && request.getPlanStatus() == null) {
//			return repo.getByPlanName(request.getPlanName());
//		}
//		if (request.getPlanName() == null && request.getPlanStatus() == null) {
//			return repo.findAll();
//
//		} else {
//			return null;
//		}


        CitizenPlan plan = new CitizenPlan();
        if (request.getPlanName() != null && !request.getPlanName().equals("")) {
            plan.setPlanName(request.getPlanName());
        }
        if (request.getPlanStatus() != null && !request.getPlanStatus().equals("")) {
            plan.setPlanStatus(request.getPlanStatus());
        }
        if (request.getGender() != null && !request.getGender().equals("")) {
            plan.setGender(request.getGender());
        }
        Example<CitizenPlan> filter = Example.of(plan);
        return repo.findAll(filter);

    }

    @Override
    public void exportExcel(HttpServletResponse response) throws IOException {

        List<CitizenPlan> allPlan = repo.findAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Citizen plan");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Name");
        row.createCell(2).setCellValue("Email");
        row.createCell(3).setCellValue("Gender");
        row.createCell(4).setCellValue("Phone Number");
        row.createCell(5).setCellValue("SSN Number");
        row.createCell(6).setCellValue("Plan Name");
        row.createCell(7).setCellValue("Plan Status");
        int dataRowIndex = 1;

        for (CitizenPlan plan : allPlan) {
            HSSFRow row1 = sheet.createRow(dataRowIndex);
            row1.createCell(0).setCellValue(plan.getCid());
            row1.createCell(1).setCellValue(plan.getCname());
            row1.createCell(2).setCellValue(plan.getCemail());
            row1.createCell(3).setCellValue(plan.getGender());
            row1.createCell(4).setCellValue(plan.getPhno());
            row1.createCell(5).setCellValue(plan.getSsn());
            row1.createCell(6).setCellValue(plan.getPlanName());
            row1.createCell(7).setCellValue(plan.getPlanStatus());
            dataRowIndex++;
        }
        ServletOutputStream ops = response.getOutputStream();
        workbook.write(ops);
        workbook.close();
        ops.close();
    }

    @Override
    public void exportPdf(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        titleFont.setSize(20);
        titleFont.setColor(Color.red);

        Paragraph paragraph = new Paragraph("Citizen Plan Info", titleFont);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(8);

        table.setWidthPercentage(100);
        table.setWidths(new int[]{3, 6, 10, 4, 7, 5, 5, 5});
        table.setSpacingBefore(5);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.blue);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(Color.white);
        font.setSize(10);
        font.isBold();

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Email", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Gender", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Phone Number", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("SSN Number", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Plan Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Plan Status", font));
        table.addCell(cell);

        List<CitizenPlan> plans = repo.findAll();

        for (CitizenPlan plan : plans) {
            table.addCell(String.valueOf(plan.getCid()));
            table.addCell(plan.getCname());
            table.addCell(plan.getCemail());
            table.addCell(plan.getGender());
            table.addCell(String.valueOf(plan.getPhno()));
            table.addCell(String.valueOf(plan.getSsn()));
            table.addCell(plan.getPlanName());
            table.addCell(plan.getPlanStatus());
        }
        document.add(table);
        document.close();
    }

}
