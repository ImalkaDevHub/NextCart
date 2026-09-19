package com.ecommerce.app.service.impl;

import com.ecommerce.app.entity.Product;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.service.ReportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    private final ProductRepository productRepository;

    public ReportServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ByteArrayInputStream exportProductsToExcel() {
        String[] HEADERs = {"ID", "Product Name", "Brand", "Category", "Price ($)", "Stock", "Featured"};
        String SHEET = "Products";

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet(SHEET);

            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
                cell.setCellStyle(headerStyle);
            }

            List<Product> products = productRepository.findAll();
            int rowIdx = 1;
            for (Product p : products) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(p.getId());
                row.createCell(1).setCellValue(p.getProductName());
                row.createCell(2).setCellValue(p.getBrand() != null ? p.getBrand() : "N/A");
                row.createCell(3).setCellValue(p.getCategory() != null ? p.getCategory().getCategoryName() : "N/A");
                row.createCell(4).setCellValue(p.getPrice().doubleValue());
                row.createCell(5).setCellValue(p.getStock());
                row.createCell(6).setCellValue(Boolean.TRUE.equals(p.getIsFeatured()) ? "Yes" : "No");
            }

            workbook.write(out);
            log.info("Successfully generated Excel report for {} products.", products.size());
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            log.error("Failed to export products to Excel: ", e);
            throw new RuntimeException("Failed to generate Excel report: " + e.getMessage());
        }
    }
}
