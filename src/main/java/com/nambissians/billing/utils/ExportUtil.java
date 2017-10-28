package com.nambissians.billing.utils;
/**
 * This is a copyright of the Brahmana food products
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p>
 * <p>
 * Redistribution and use in source and binary forms, without permission
 * from copyright owner is not permited.
 **/

import com.nambissians.billing.model.OwnerProfile;
import com.nambissians.billing.model.Product;
import com.nambissians.billing.model.SaleRecord;
import com.nambissians.billing.model.SaleReport;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ExportUtil
 * <p>
 * Created by svadakkedath on 28/10/17
 **/
public class ExportUtil {
    private static final String BASE_PATH_PROP = "invoicePath";
    private static final String EXPORT_DIRECTORY = "/export/";
    private static final String SALE_REPORT_STR = "Sales_Report_";
    private static final String EMPTY_SPACE = " ";
    private static final String SALES_REPORT_CAPTION = "Sales Report";
    private static final Float ZERO = MathUtils.roundoffToTwoPlaces(Float.valueOf("0.00"));
    private static final Logger logger = LoggerFactory.getLogger(ExportUtil.class);

    private static final String BILL_NO = "Bill No";
    private static final String BILL_DATE = "Bill Date";
    private static final String GST_IN = "GSTIN";
    private static final String PARTIES_NAME = "Parties Name";
    private static final String HSN = "HSN";
    private static final String QTY = "QTY";
    private static final String RATE = "Rate";
    private static final String SALES = "Amount";
    private static final String SGST = "SGST";
    private static final String CGST = "CGST";
    private static final String IGST = "IGST";
    private static final String TOTAL = "Total";

    private static String createExportDirectoryIfRequired(String basepath) {
        String exportPath = String.format("%s%s", basepath, EXPORT_DIRECTORY);
        File directory = new File(exportPath);
        if (!directory.exists()) {
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }
        return exportPath;
    }

    private static String getNonEmptyCellValue(String str) {
        if (str == null) {
            return EMPTY_SPACE;
        }
        return str;
    }

    private static Map<Long, Product> getProductMap(List<Product> products) {
        Map<Long, Product> productMap = new HashMap<>();
        for (Product product : products) {
            productMap.put(product.getId(), product);
        }
        logger.debug("Product map is : {}", productMap);
        return productMap;
    }

    private static boolean createRow(int rowNum, XSSFSheet sheet, SaleReport saleReport,
                                     String billNum, String billDate, String gstIn, String customerName, Map<Long, Product> productMap) {
        Row row = sheet.createRow(rowNum++);
        int colNum = 0;
        Cell billNumCol = row.createCell(colNum++);
        billNumCol.setCellValue(billNum);
        Cell billDateCol = row.createCell(colNum++);
        billDateCol.setCellValue(billDate);
        Cell gstInCol = row.createCell(colNum++);
        gstInCol.setCellValue(gstIn);
        Cell customerNameCol = row.createCell(colNum++);
        customerNameCol.setCellValue(customerName);
        Product prd = productMap.get(saleReport.getProductId());
        Cell hsnCodeCol = row.createCell(colNum++);
        hsnCodeCol.setCellValue(prd.getHsnCode());
        Cell quantityCol = row.createCell(colNum++);
        quantityCol.setCellValue(saleReport.getQuantity());
        Cell rateCol = row.createCell(colNum++);
        rateCol.setCellValue(prd.getPrice());
        Cell salesCol = row.createCell(colNum++);
        salesCol.setCellValue(saleReport.getTaxableAmount());
        Cell sgstCol = row.createCell(colNum++);
        sgstCol.setCellValue(saleReport.getSgst());
        Cell cgstCol = row.createCell(colNum++);
        cgstCol.setCellValue(saleReport.getCgst());
        Cell igstCol = row.createCell(colNum++);
        //TODO add IGST when system is ready
        igstCol.setCellValue(ZERO);
        Cell totalCol = row.createCell(colNum++);
        totalCol.setCellValue(saleReport.getFinalAmount());
        return true;
    }

    private static String createReport(OwnerProfile ownerProfile, List<SaleRecord> saleRecords,
                                       List<Product> products, String basePath, String fileName) throws IOException {
        String filename = String.format("%s%s%s.xlsx", createExportDirectoryIfRequired(basePath),
                SALE_REPORT_STR, fileName);
        Map<Long, Product> productMap = getProductMap(products);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(String.format("%s %s", ownerProfile.getCompanyName(), SALES_REPORT_CAPTION));
        int rowNum = 0;
        logger.debug("Creating excel sheet named {}", filename);
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(BILL_NO);
        row.createCell(1).setCellValue(BILL_DATE);
        row.createCell(2).setCellValue(GST_IN);
        row.createCell(3).setCellValue(PARTIES_NAME);
        row.createCell(4).setCellValue(HSN);
        row.createCell(5).setCellValue(QTY);
        row.createCell(6).setCellValue(RATE);
        row.createCell(7).setCellValue(SALES);
        row.createCell(8).setCellValue(SGST);
        row.createCell(9).setCellValue(CGST);
        row.createCell(10).setCellValue(IGST);
        row.createCell(11).setCellValue(TOTAL);
        Collections.reverse(saleRecords);
        for (SaleRecord saleRecord : saleRecords) {
            String billNum = saleRecord.getSaleMetaData().getPrintableInvoiceNumber();
            String billDate = Constants.SDF_DATE_ONLY.format(saleRecord.getSaleMetaData().getTimestamp());
            String gstIn = getNonEmptyCellValue(saleRecord.getCustomer().getGstin());
            String customerName = getNonEmptyCellValue(saleRecord.getCustomer().getCustomerName());
            for (SaleReport saleReport : saleRecord.getBreakUp()) {
                createRow(rowNum++, sheet, saleReport, billNum, billDate, gstIn, customerName, productMap);
            }

        }
        FileOutputStream outputStream = new FileOutputStream(filename);
        workbook.write(outputStream);
        workbook.close();
        return filename;
    }

    public static String generateExportReport(OwnerProfile ownerProfile, List<SaleRecord> saleRecords, List<Product> products, String fileName)
            throws IOException {
        String basePath = System.getProperty(BASE_PATH_PROP);
        return createReport(ownerProfile, saleRecords, products, basePath, fileName);
    }
}
