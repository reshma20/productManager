package com.nambissians.billing;/**
 * Created by SajiV on 13/09/17.
 */

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.nambissians.billing.model.*;
import com.nambissians.billing.service.ProductServiceImpl;
import com.nambissians.billing.service.ProfileServiceImpl;
import com.nambissians.billing.service.SaleReportServiceImpl;
import com.nambissians.billing.service.StateServiceImpl;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.DBConnectionUtils;
import com.nambissians.billing.utils.InvoiceConstants;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

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
public class PDFWriter {

    private OwnerProfile ownerProfile;
    private SaleRecord saleRecord;
    private SaleReportServiceImpl saleReportService = new SaleReportServiceImpl();
    private ProfileServiceImpl profileService = new ProfileServiceImpl();
    private StateServiceImpl stateService = new StateServiceImpl();
    private static final Chunk chkSpace = new Chunk(String.format("%5s", Constants.EMPTY_STRING));
    private List<Product> products;

    public PDFWriter() throws IOException {
        Properties props = new Properties();
        InputStream input = new FileInputStream("src/main/test/application_test.properties");
        props.load(input);
        props.putAll(System.getProperties());
        System.setProperties(props);
        DBConnectionUtils.initializeDbConnections("manager", "manager");
        ownerProfile = profileService.getOwnerProfile();
        products = new ProductServiceImpl().getAllProductsWithApplicableTaxes();
        saleRecord = saleReportService.getSaleRecord("7df6d5a9-b576-4869-b2c8-d2ce829974a0");
    }

    private void feedOwnerData(Document document, PdfWriter writer) throws IOException, DocumentException {
        Image img = Image.getInstance(ownerProfile.getLogo());
        float logoScaler = (Constants.LOGO_WIDTH / (img.getWidth())) * 100;
        img.scalePercent(logoScaler);
        ColumnText ct = new ColumnText(writer.getDirectContent());
        ct.setSimpleColumn(new Rectangle(InvoiceConstants.COMPANY_BOX_LOC_MIN_X, InvoiceConstants.COMPANY_BOX_LOC_MIN_Y,
                InvoiceConstants.COMPANY_BOX_LOC_MAX_X, InvoiceConstants.COMPANY_BOX_LOC_MAX_Y));

        Phrase gstInvoicePhrase = new Phrase();
        Font customerDetailsFont = FontFactory.getFont(FontFactory.COURIER, InvoiceConstants.CUSTOMER_DETS_FONT_SIZE, BaseColor.BLACK);
        Font gstInvoicePhraseFont = FontFactory.getFont(FontFactory.COURIER_BOLD, InvoiceConstants.GST_INVOICE_FONT_SIZE, Font.UNDERLINE, BaseColor.BLACK);
        Chunk chkcustomerDetailsSpace = new Chunk(String.format("%20s", Constants.EMPTY_STRING), customerDetailsFont);
        Chunk chkGstInvoice = new Chunk(Constants.GST_INVOICE, gstInvoicePhraseFont);
        gstInvoicePhrase.add(chkcustomerDetailsSpace);
        gstInvoicePhrase.add(chkGstInvoice);
        ct.addElement(gstInvoicePhrase);

        Phrase titlePhrase = new Phrase();
        Font companyNameFont = FontFactory.getFont(FontFactory.COURIER, InvoiceConstants.COMPANY_NAME_FONT_SIZE, BaseColor.BLACK);
        titlePhrase.add(new Chunk(img, InvoiceConstants.MIN_X, InvoiceConstants.MIN_Y, true));
        Chunk companyNameChunk = new Chunk(ownerProfile.getCompanyName(), companyNameFont);

        ct.addElement(companyNameChunk);
        Font companyAddressFont = FontFactory.getFont(FontFactory.COURIER, InvoiceConstants.COMPANY_ADDRESS_FONT_SIZE, BaseColor.BLACK);
        StringBuffer companyDets = new StringBuffer(ownerProfile.getAddress());
        if (ownerProfile.getEmail() != null && (ownerProfile.getEmail().isEmpty() == false)) {
            companyDets.append(Constants.LINE_FEED_CHAR).append(Constants.EMAIL_STR).append(ownerProfile.getEmail());
        }
        if (ownerProfile.getTel() != null && (ownerProfile.getTel().isEmpty() == false)) {
            companyDets.append(Constants.LINE_FEED_CHAR).append(Constants.TEL_STR).append(ownerProfile.getTel());
        }
        if (ownerProfile.getMobile() != null && (ownerProfile.getMobile().isEmpty() == false)) {
            companyDets.append(Constants.LINE_FEED_CHAR).append(Constants.MOBILE_STR).append(ownerProfile.getMobile());
        }

        Chunk companyAddressChunk = new Chunk(companyDets.toString(), companyAddressFont);
        ct.addElement(companyAddressChunk);
        ct.go();

        ColumnText ctNumber = new ColumnText(writer.getDirectContent());
        ctNumber.setSimpleColumn(new Rectangle(InvoiceConstants.INVOICE_NUMBER_BOX_MIN_X, InvoiceConstants.INVOICE_NUMBER_BOX_MIN_Y,
                InvoiceConstants.INVOICE_NUMBER_BOX_MAX_X, InvoiceConstants.INVOICE_NUMBER_BOX_MAX_Y));
        Font invoiceNumberFont = FontFactory.getFont(FontFactory.COURIER_BOLD, InvoiceConstants.COMPANY_ADDRESS_FONT_SIZE, BaseColor.RED);
        String invoiceNumber = String.format("%-15s%06d", Constants.INVOICE_NUMBER, saleRecord.getSaleMetaData().getId());
        Chunk invoiceNumberChunk = new Chunk(invoiceNumber, invoiceNumberFont);
        ctNumber.addElement(invoiceNumberChunk);
        if (ownerProfile.getGstin() != null && (ownerProfile.getGstin().isEmpty() == false)) {
            Font companyGSTFont = FontFactory.getFont(FontFactory.COURIER_BOLD, InvoiceConstants.COMPANY_ADDRESS_FONT_SIZE, BaseColor.BLACK);
            String companyGST = String.format("%-15s:%s", Constants.GST_IN_STR, ownerProfile.getGstin());
            Chunk companyGSTChunk = new Chunk(companyGST, companyGSTFont);
            ctNumber.addElement(companyGSTChunk);
        }
        ctNumber.go();
        document.add(titlePhrase);
    }

    private static final String TAG_LINE = "%-3s %-4s %-30s %-10s %-12s %-6s %-6s %-15s %-8s %-6s %-5s %15s";
    private static final String INVOICE_STATEMENTS = "%3d %-4d %-30s %10.2f %8d %10.2f %06.2f %012.2f %10s %6.2f %6.2f %17.2f";
    private static final String TAXES_ONLY = "%84s %15s";

    private void feedSaleData(PdfWriter writer) throws IOException, DocumentException {
        Font customerDetailsLabelFont = FontFactory.getFont(FontFactory.COURIER_BOLD, InvoiceConstants.CUSTOMER_INVOICE_FONT_SIZE, BaseColor.BLACK);
        Font customerDetailsFont = FontFactory.getFont(FontFactory.COURIER, InvoiceConstants.CUSTOMER_INVOICE_FONT_SIZE, BaseColor.BLACK);
        ColumnText ct = new ColumnText(writer.getDirectContent());
        ct.setSimpleColumn(new Rectangle(InvoiceConstants.INVOICE_BOX_MIN_X + InvoiceConstants.MARGIN, InvoiceConstants.SALE_BOX_MIN_Y,
                InvoiceConstants.INVOICE_BOX_MAX_X - InvoiceConstants.MARGIN, InvoiceConstants.SALE_BOX_MAX_Y));
        Phrase saleDetailsPhrase = new Phrase();
        String tag = String.format(TAG_LINE, Constants.SNO, Constants.CODE, Constants.DESCRIPTION_TAG, Constants.PRICE_TAG,
                Constants.QUANTITY_STR, Constants.AMOUNT_STR, Constants.REBATE_STR, Constants.TAXABLE_AMOUNT_STR,
                Constants.TAXES_STR, Constants.CGST_STR, Constants.SGST_STR, Constants.FINAL_STR);
        Chunk chkSaleDetail = new Chunk(tag, customerDetailsLabelFont);
        saleDetailsPhrase.add(chkSaleDetail);
        ct.addElement(saleDetailsPhrase);

        HashMap<Long, Product> productMap = new HashMap<>();
        for (Product product : products) {
            productMap.put(product.getId(), product);
        }
        List<SaleReport> sr = saleRecord.getBreakUp();
        Phrase emptyRec = new Phrase(String.format("%5s", Constants.EMPTY_STRING), customerDetailsFont);
        ct.addElement(emptyRec);
        for (int i = 0; i < sr.size(); i++) {
            SaleReport srep = sr.get(i);
            Product prd = productMap.get(srep.getProductId());
            String[] applicableTaxes = prd.getApplicableTaxes().split(Constants.LINE_FEED_CHAR);
            Phrase chkSaleRec = new Phrase(String.format(INVOICE_STATEMENTS, i + 1, prd.getId(), prd.getTag(), srep.getPrice(),
                    srep.getQuantity(), srep.getAmount(), srep.getRebate(), srep.getTaxableAmount(), applicableTaxes[0],
                    srep.getCgst(), srep.getSgst(), srep.getFinalAmount()), customerDetailsFont);
            ct.addElement(chkSaleRec);
            for (int cnt = 1; cnt < applicableTaxes.length; cnt++) {
                Phrase chkTaxLine = new Phrase(String.format(TAXES_ONLY, Constants.EMPTY_STRING, applicableTaxes[cnt]),
                        customerDetailsFont);
                ct.addElement(chkTaxLine);
            }
        }
        ct.go();
    }

    private void feedCustomerData(PdfWriter writer) throws IOException, DocumentException {
        Font customerDetailsFont = FontFactory.getFont(FontFactory.COURIER, InvoiceConstants.CUSTOMER_DETS_FONT_SIZE, BaseColor.BLACK);
        Font customerDetailsLabelFont = FontFactory.getFont(FontFactory.COURIER_BOLD, InvoiceConstants.CUSTOMER_DETS_FONT_SIZE, BaseColor.BLACK);
        ColumnText ct = new ColumnText(writer.getDirectContent());
        ct.setSimpleColumn(new Rectangle(InvoiceConstants.INVOICE_BOX_MIN_X + InvoiceConstants.MARGIN, InvoiceConstants.CUSTOMER_BOX_MIN_Y,
                InvoiceConstants.INVOICE_BOX_MAX_X - InvoiceConstants.MARGIN, InvoiceConstants.CUSTOMER_BOX_MAX_Y));
        Phrase customerDetails = new Phrase();
        Font customerDetailsTagFont = FontFactory.getFont(FontFactory.COURIER_BOLD, InvoiceConstants.CUSTOMER_DETS_FONT_SIZE, Font.UNDERLINE, BaseColor.BLACK);
        Chunk chkcustomerDetailsSpace = new Chunk(String.format("%45s", Constants.EMPTY_STRING), customerDetailsFont);
        Chunk chkcustomerDetails = new Chunk(Constants.CUSTOMER_DETAILS, customerDetailsTagFont);
        customerDetails.add(chkcustomerDetailsSpace);
        customerDetails.add(chkcustomerDetails);
        ct.addElement(customerDetails);
        //Name
        Phrase customerNamePhrase = new Phrase();
        Chunk chkLblCustomerName = new Chunk(Constants.CUSTOMER_NAME, customerDetailsLabelFont);
        Chunk cukCustomerName = new Chunk(saleRecord.getCustomer().getCustomerName(), customerDetailsFont);
        customerNamePhrase.add(chkLblCustomerName);
        customerNamePhrase.add(cukCustomerName);
        ct.addElement(customerNamePhrase);
        //Address
        Phrase customerAddressPhrase = new Phrase();
        String[] address = saleRecord.getCustomer().getAddress().split(Constants.LINE_FEED_CHAR);
        StringBuffer sbAddress = new StringBuffer();
        for (int i = 0; i < address.length; i++) {
            sbAddress.append(address[i]).append(Constants.COMMA);
        }
        Chunk chkLblCustomerAddress = new Chunk(Constants.ADDRESS_STR, customerDetailsLabelFont);
        Chunk chkCustomerAddress = new Chunk(sbAddress.substring(0, sbAddress.length() - 1), customerDetailsFont);
        customerAddressPhrase.add(chkLblCustomerAddress);
        customerAddressPhrase.add(chkCustomerAddress);
        ct.addElement(customerAddressPhrase);
        //GSTIN
        Phrase customerDetailPhrase = new Phrase();
        Chunk chkLblGSTIN = new Chunk(Constants.GST_IN_CUSTOMER_STR, customerDetailsLabelFont);
        Chunk chkCustomerGSTIN = new Chunk(saleRecord.getCustomer().getGstin(), customerDetailsFont);
        customerDetailPhrase.add(chkLblGSTIN);
        customerDetailPhrase.add(chkCustomerGSTIN);
        customerDetailPhrase.add(chkSpace);
        //Tel
        Chunk chkLblCustomerTel = new Chunk(Constants.TEL_STR, customerDetailsLabelFont);
        Chunk cukCustomerTel = new Chunk(saleRecord.getCustomer().getTel(), customerDetailsFont);
        customerDetailPhrase.add(chkLblCustomerTel);
        customerDetailPhrase.add(cukCustomerTel);
        customerDetailPhrase.add(chkSpace);
        //Email
        Chunk chkLblCustomerEmail = new Chunk(Constants.EMAIL_STR, customerDetailsLabelFont);
        Chunk cukCustomerEmail = new Chunk(saleRecord.getCustomer().getEmail(), customerDetailsFont);
        customerDetailPhrase.add(chkLblCustomerEmail);
        customerDetailPhrase.add(cukCustomerEmail);
        ct.addElement(customerDetailPhrase);
        customerDetailPhrase.add(chkSpace);
        ct.go();
    }

    private void feedCustomerMetaData(PdfWriter writer) throws IOException, DocumentException {
        Font customerDetailsFont = FontFactory.getFont(FontFactory.COURIER, InvoiceConstants.CUSTOMER_DETS_FONT_SIZE, BaseColor.BLACK);
        Font customerDetailsLabelFont = FontFactory.getFont(FontFactory.COURIER_BOLD, InvoiceConstants.CUSTOMER_DETS_FONT_SIZE, BaseColor.BLACK);
        ColumnText ct = new ColumnText(writer.getDirectContent());
        ct.setSimpleColumn(new Rectangle(InvoiceConstants.INVOICE_BOX_MIN_X + InvoiceConstants.MARGIN, InvoiceConstants.CUSTOMER_BOX_META_DATA_MIN_Y,
                InvoiceConstants.INVOICE_BOX_MAX_X - InvoiceConstants.MARGIN, InvoiceConstants.CUSTOMER_BOX_META_DATA_MAX_Y));
        SaleMetaData saleMetaData = saleRecord.getSaleMetaData();
        //Timestamp
        Phrase transactionPhrase = new Phrase();
        Chunk chkLblDate = new Chunk(Constants.DATE_STR, customerDetailsLabelFont);
        Chunk cukDate = new Chunk(Constants.SDF.format(saleMetaData.getTimestamp()) + Constants.HRS, customerDetailsFont);
        transactionPhrase.add(chkLblDate);
        transactionPhrase.add(cukDate);
        transactionPhrase.add(chkSpace);
        Chunk chkLblState = new Chunk(Constants.STATE_STR, customerDetailsLabelFont);
        Chunk cukState = new Chunk(stateService.getState(saleMetaData.getState()), customerDetailsFont);
        transactionPhrase.add(chkLblState);
        transactionPhrase.add(cukState);
        ct.addElement(transactionPhrase);

        //Vehicle
        Phrase transportPhrase = new Phrase();
        Chunk chkLblVehicle = new Chunk(Constants.VEHICLE_STR, customerDetailsLabelFont);
        Chunk cukVehicle = new Chunk(saleMetaData.getVehicle(), customerDetailsFont);
        transportPhrase.add(chkLblVehicle);
        transportPhrase.add(cukVehicle);
        transportPhrase.add(chkSpace);

        Chunk chkLblTransportationMode = new Chunk(Constants.TRANSPORTATION_MODE, customerDetailsLabelFont);
        if (saleMetaData.getTransportationMode() == null) {
            saleMetaData.setTransportationMode(String.format("%5s", Constants.EMPTY_STRING));
        }
        Chunk cukTransportationMode = new Chunk(saleMetaData.getTransportationMode(), customerDetailsFont);
        transportPhrase.add(chkLblTransportationMode);
        transportPhrase.add(cukTransportationMode);
        transportPhrase.add(chkSpace);

        Chunk chkLblSupplyPlace = new Chunk(Constants.SUPPLY_PLACE_STR, customerDetailsLabelFont);
        Chunk cukSupplyPlace = new Chunk(saleMetaData.getSupplyPlace(), customerDetailsFont);
        transportPhrase.add(chkLblSupplyPlace);
        transportPhrase.add(cukSupplyPlace);
        transportPhrase.add(chkSpace);
        ct.addElement(transportPhrase);
        ct.go();
    }

    public void feedTotalBox(PdfWriter writer) throws IOException, DocumentException{
        Font customerDetailsFont = FontFactory.getFont(FontFactory.COURIER_BOLD, InvoiceConstants.GST_INVOICE_FONT_SIZE, BaseColor.BLACK);
        ColumnText ct = new ColumnText(writer.getDirectContent());
        ct.setSimpleColumn(new Rectangle(InvoiceConstants.TOTAL_BOX_MIN_X, InvoiceConstants.TOTAL_BOX_MIN_Y,
                InvoiceConstants.TOTAL_BOX_MAX_X, InvoiceConstants.TOTAL_BOX_MAX_Y));
        Phrase totalTaxPhrase = new Phrase(String.format(Constants.TOTAL_TAX_COLLECTED, Constants.TOTAL_TAX,saleRecord.getSaleMetaData().getTotalTaxes()), customerDetailsFont);
        Phrase totalBillPhrase = new Phrase(String.format(Constants.TOTAL_BILL_AMOUNT, Constants.TOTAL_BILL,saleRecord.getSaleMetaData().getTotalAmount()), customerDetailsFont);
        ct.addElement(totalTaxPhrase);
        ct.addElement(totalBillPhrase);
        Phrase emptyRec = new Phrase(String.format("%5s", Constants.EMPTY_STRING), customerDetailsFont);
        ct.addElement(emptyRec);
        ct.addElement(emptyRec);
        ct.addElement(emptyRec);
        Phrase authorisedSignatory = new Phrase(String.format("%5s%s",Constants.EMPTY_STRING, Constants.AUTHORISED_SIGNATORY), customerDetailsFont);
        ct.addElement(authorisedSignatory);
        ct.go();

    }

    public void createInvoice() throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));
        document.open();
        drawInvoiceBox(writer);
        Rectangle margin = new Rectangle(InvoiceConstants.MIN_X, InvoiceConstants.MIN_Y,
                InvoiceConstants.MAX_X, InvoiceConstants.MAX_Y);
        document.setPageSize(margin);
        document.setMargins(InvoiceConstants.MARGIN, InvoiceConstants.MARGIN, InvoiceConstants.MARGIN,
                InvoiceConstants.MARGIN);
        feedOwnerData(document, writer);
        feedCustomerData(writer);
        feedCustomerMetaData(writer);
        feedSaleData(writer);
        feedTotalBox(writer);
        document.close();
    }

    public void drawInvoiceBox(PdfWriter writer) {
        PdfContentByte canvas = writer.getDirectContent();
        CMYKColor magentaColor = new CMYKColor(0.f, 0.f, 0.f, 1.f);
        canvas.setColorStroke(magentaColor);
        canvas.moveTo(InvoiceConstants.INVOICE_BOX_MIN_X, InvoiceConstants.INVOICE_BOX_MIN_Y);
        canvas.lineTo(InvoiceConstants.INVOICE_BOX_MIN_X, InvoiceConstants.INVOICE_BOX_MAX_Y);
        canvas.lineTo(InvoiceConstants.INVOICE_BOX_MAX_X, InvoiceConstants.INVOICE_BOX_MAX_Y);
        canvas.lineTo(InvoiceConstants.INVOICE_BOX_MAX_X, InvoiceConstants.INVOICE_BOX_MIN_Y);
        canvas.closePathStroke();

        canvas.moveTo(InvoiceConstants.INVOICE_BOX_MIN_X, InvoiceConstants.OWNER_PROFILE_Y);
        canvas.lineTo(InvoiceConstants.INVOICE_BOX_MAX_X, InvoiceConstants.OWNER_PROFILE_Y);
        canvas.closePathStroke();

        canvas.moveTo(InvoiceConstants.INVOICE_BOX_MIN_X, InvoiceConstants.SALES_BOX_Y);
        canvas.lineTo(InvoiceConstants.INVOICE_BOX_MAX_X, InvoiceConstants.SALES_BOX_Y);
        canvas.closePathStroke();

        canvas.moveTo(InvoiceConstants.INVOICE_BOX_MIN_X, InvoiceConstants.SALE_BOX_HEADER_Y);
        canvas.lineTo(InvoiceConstants.INVOICE_BOX_MAX_X, InvoiceConstants.SALE_BOX_HEADER_Y);
        canvas.closePathStroke();

        canvas.moveTo(InvoiceConstants.INVOICE_BOX_MIN_X, InvoiceConstants.TOTAL_BOX_HEADER_Y);
        canvas.lineTo(InvoiceConstants.INVOICE_BOX_MAX_X, InvoiceConstants.TOTAL_BOX_HEADER_Y);
        canvas.closePathStroke();
    }

    public static void main(String args[]) throws IOException, DocumentException {
        PDFWriter pdfWriter = new PDFWriter();
        pdfWriter.createInvoice();
    }
}
