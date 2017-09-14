package com.nambissians.billing.model;/**
 * Created by SajiV on 13/09/17.
 */

import com.nambissians.billing.utils.Constants;
import javafx.embed.swing.SwingFXUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

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
public class InvoicePrint implements Printable {
    private SaleRecord salesRecord;
    private OwnerProfile ownerProfile;
    private List<Product> products;

    public InvoicePrint(SaleRecord sr, OwnerProfile ownerProfile, List<Product> products) {
        salesRecord = sr;
        this.ownerProfile = ownerProfile;
        this.products = products;
    }

    private static final String TITLE_LINE = "%70s %s";
    private static final int TITLE_LENGTH = 135;
    private static final int TITLE_STARTING = 355;
    private static final int UNDER_LINE_BUFFER = 5;
    private static final String INVOICE_NUMBER_STRING = "%100s %s%06d";

    private BufferedImage loadImage(byte[] imageBytes) {
        if (imageBytes != null && imageBytes.length != 0) {
            javafx.scene.image.Image img = new javafx.scene.image.Image(new ByteArrayInputStream(imageBytes));
            return SwingFXUtils.fromFXImage(img, null);
        }
        return null;
    }

    private Point printOwnerSection(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        Font titleFont = new Font(Constants.SERIF_FONT, Font.BOLD, Constants.COMPANY_NAME_FONT_SIZE);
        g2.setFont(titleFont);
        g2.drawString(String.format(TITLE_LINE, Constants.EMPTY_STRING, Constants.GST_INVOICE), Constants.TITLE_SPACE_X, Constants.TITLE_SPACE_Y);
        g2.drawLine(Constants.TITLE_SPACE_X + TITLE_STARTING, Constants.TITLE_SPACE_Y + UNDER_LINE_BUFFER, Constants.TITLE_SPACE_X + TITLE_LENGTH + TITLE_STARTING, Constants.TITLE_SPACE_Y + UNDER_LINE_BUFFER);
        Font font = new Font(Constants.SERIF_FONT, Font.BOLD, Constants.COMPANY_NAME_FONT_SIZE);
        g2.setFont(font);
        g2.drawImage(loadImage(ownerProfile.getLogo()), Constants.LEFT_MARGIN, Constants.TOP_MARGIN,
                Constants.LOGO_WIDTH, Constants.LOGO_HEIGHT, null);
        g2.drawString(ownerProfile.getCompanyName(), Constants.COMPANY_NAME_LOWER_X, Constants.COMPANY_NAME_Y);
        font = new Font(Constants.SERIF_FONT, Font.BOLD, Constants.INVOICE_FONT_HEIGHT);
        g2.setFont(font);
        g2.drawString(String.format(INVOICE_NUMBER_STRING, Constants.EMPTY_STRING, Constants.INVOICE_NUMBER, salesRecord.getSaleMetaData().getId()), Constants.TITLE_SPACE_X + TITLE_STARTING, Constants.TITLE_SPACE_Y + UNDER_LINE_BUFFER);
        font = new Font(Constants.SERIF_FONT, Font.PLAIN, Constants.COMPANY_ADDRESS_FONT_SIZE);
        g2.setFont(font);
        g2.drawString(ownerProfile.getAddress(), Constants.COMPANY_NAME_LOWER_X, Constants.COMPANY_ADDRESS_Y);
        font = new Font(Constants.SERIF_FONT, Font.ITALIC, Constants.COMPANY_ADDRESS_FONT_SIZE);
        g2.setFont(font);
        int yLoc = Constants.COMPANY_EMAIL_Y;
        if (ownerProfile.getEmail() != null) {
            g2.drawString(Constants.EMAIL_STR + ownerProfile.getEmail(), Constants.COMPANY_NAME_LOWER_X, Constants.COMPANY_EMAIL_Y);
            yLoc += Constants.INVOICE_FONT_HEIGHT;
        }
        if (ownerProfile.getTel() != null) {
            g2.drawString(Constants.TEL_STR + ownerProfile.getTel(), Constants.COMPANY_NAME_LOWER_X, yLoc);
            yLoc += Constants.INVOICE_FONT_HEIGHT;
        }
        if (ownerProfile.getMobile() != null) {
            g2.drawString(Constants.MOBILE_STR + ownerProfile.getMobile(), Constants.COMPANY_NAME_LOWER_X, yLoc);
            yLoc += Constants.INVOICE_FONT_HEIGHT;
        }
        if (ownerProfile.getGstin() != null) {
            g2.drawString(Constants.GST_IN_STR + ownerProfile.getGstin(), Constants.COMPANY_NAME_LOWER_X, yLoc);
        }
        yLoc = Constants.COMPANY_EMAIL_Y + 4 * Constants.INVOICE_FONT_HEIGHT + Constants.LOGO_BUFFER_SPACE;
        Point cursorCord = new Point(Constants.LEFT_MARGIN, yLoc);
        g2.drawLine(cursorCord.getX(), cursorCord.getY(),
                Constants.INVOICE_WIDTH, yLoc);
        cursorCord.setY(cursorCord.getY() + Constants.INVOICE_FONT_HEIGHT);
        return cursorCord;
    }

    private Point printCustomerSection(Graphics2D g2, Point pt) {
        Customer cust = salesRecord.getCustomer();
        SaleMetaData saleMetaData = salesRecord.getSaleMetaData();
        Font fontBold = new Font(Constants.SERIF_FONT, Font.BOLD, Constants.INVOICE_FONT_HEIGHT);
        g2.setFont(fontBold);
        int baseY = pt.getY();
        g2.drawString(Constants.CUSTOMER_NAME, pt.getX(), pt.getY());
        int locY = pt.getY() + Constants.INVOICE_FONT_HEIGHT;
        g2.drawString(Constants.ADDRESS_STR, pt.getX(), locY);
        String[] address = cust.getAddress().split(Constants.LINE_FEED_CHAR);
        locY += Constants.INVOICE_FONT_HEIGHT * address.length;
        g2.drawString(Constants.EMAIL_STR, pt.getX(), locY);
        locY += Constants.INVOICE_FONT_HEIGHT;
        g2.drawString(Constants.TEL_STR, pt.getX(), locY);
        locY += Constants.INVOICE_FONT_HEIGHT;
        g2.drawString(Constants.GST_IN_STR, pt.getX(), locY);
        locY += Constants.INVOICE_FONT_HEIGHT;
        g2.drawString(Constants.INVOICE_TIME, pt.getX(), locY);
        locY += Constants.INVOICE_FONT_HEIGHT;
        g2.drawLine(pt.getX(), locY, Constants.INVOICE_WIDTH, locY);
        Font fontNormal = new Font(Constants.SERIF_FONT, Font.PLAIN, Constants.INVOICE_FONT_HEIGHT);
        g2.setFont(fontNormal);
        g2.drawString(cust.getCustomerName(), pt.getX() + Constants.INVOICE_CUSTOMER_SPACE, baseY);
        baseY += Constants.INVOICE_FONT_HEIGHT;
        for (int i = 0; i < address.length; i++) {
            g2.drawString(address[i], pt.getX() + Constants.INVOICE_CUSTOMER_SPACE, baseY);
            baseY += Constants.INVOICE_FONT_HEIGHT;
        }
        g2.drawString(cust.getEmail(), pt.getX() + Constants.INVOICE_CUSTOMER_SPACE, baseY);
        baseY += Constants.INVOICE_FONT_HEIGHT;
        g2.drawString(cust.getTel(), pt.getX() + Constants.INVOICE_CUSTOMER_SPACE, baseY);
        baseY += Constants.INVOICE_FONT_HEIGHT;
        g2.drawString(cust.getGstin(), pt.getX() + Constants.INVOICE_CUSTOMER_SPACE, baseY);
        baseY += Constants.INVOICE_FONT_HEIGHT;
        g2.drawString(Constants.SDF.format(saleMetaData.getTimestamp()), pt.getX() + Constants.INVOICE_CUSTOMER_SPACE, baseY);
        pt.setY(locY);
        return pt;
    }

    private static final String TAG_LINE = "%3s %4s %40s %20s %3s %5s %6s %8s %20s %6s %5s %25s";

    public Point printInvoiceHeadLine(Graphics2D g2, Point pt) {
        pt.incrementY();
        Font fontBold = new Font(Constants.SERIF_FONT, Font.BOLD, Constants.INVOICE_FONT_HEIGHT);
        g2.setFont(fontBold);
        String headLine = String.format(TAG_LINE, Constants.SNO, Constants.CODE, Constants.DESCRIPTION_TAG, Constants.PRICE_TAG, "Quantity", "Amount", "Rebate", "Taxable Amount", "Taxes", "CGST", "SGST", "Final");
        g2.drawString(headLine, pt.getX(), pt.getY());
        pt.incrementY();
        g2.drawLine(pt.getX(), pt.getY(), Constants.INVOICE_WIDTH, pt.getY());
        return pt;
    }

    private static final String INVOICE_STATEMENTS = "%3d %4d %50s %10.2f %12d %10.2f %6.2f %27.2f %20s %6.2f %6.2f %27.2f";
    private static final String TAXES_ONLY = "%164s %15s";

    public Point printInvoiceContent(Graphics2D g2, Point pt) {
        pt.incrementY();
        Font fontNormal = new Font(Constants.SERIF_FONT, Font.PLAIN, Constants.INVOICE_BREAKUP_HEIGHT);
        g2.setFont(fontNormal);
        HashMap<Long, Product> productMap = new HashMap<>();
        for (Product product : products) {
            productMap.put(product.getId(), product);
        }
        for (int i = 0; i < salesRecord.getBreakUp().size(); i++) {
            SaleReport srep = salesRecord.getBreakUp().get(i);
            Product prd = productMap.get(srep.getProductId());
            String[] applicableTaxes = prd.getApplicableTaxes().split(Constants.LINE_FEED_CHAR);
            g2.drawString(String.format(INVOICE_STATEMENTS, i + 1, prd.getId(), prd.getTag(), srep.getPrice(), srep.getQuantity(), srep.getAmount(), srep.getRebate()
                    , srep.getTaxableAmount(), applicableTaxes[0], srep.getCgst(), srep.getSgst(), srep.getFinalAmount()), pt.getX(), pt.getY());
            for (int cnt = 1; cnt < applicableTaxes.length; cnt++) {
                pt.incrementY();
                g2.drawString(String.format(TAXES_ONLY, Constants.EMPTY_STRING, applicableTaxes[cnt]), pt.getX(), pt.getY());
            }
            pt.incrementY();
        }
        return pt;
    }

    private static final String TOTAL_TAXES = "Total Taxes %140s %27.2f";
    private static final String AMOUNT_PAYABLE = "Amount Payable %131s %27.2f";
    ;

    public Point printLastLine(Graphics2D g2, Point pt) {
        g2.drawLine(pt.getX(), pt.getY(), Constants.INVOICE_WIDTH, pt.getY());
        pt.incrementY();
        pt.incrementY();
        Font fontBold = new Font(Constants.SERIF_FONT, Font.BOLD, Constants.AMOUNT_HEIGHT);
        g2.setFont(fontBold);
        g2.drawString(String.format(TOTAL_TAXES, Constants.EMPTY_STRING, salesRecord.getSaleMetaData().getTotalTaxes()), pt.getX(), pt.getY());
        pt.incrementY();
        pt.incrementY();
        g2.drawString(String.format(AMOUNT_PAYABLE, Constants.EMPTY_STRING, salesRecord.getSaleMetaData().getTotalAmount()), pt.getX(), pt.getY());
        return pt;
    }

    private void printInvoice(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Point pt = printOwnerSection(g2);
        pt = printCustomerSection(g2, pt);
        pt = printInvoiceHeadLine(g2, pt);
        pt = printInvoiceContent(g2, pt);
        printLastLine(g2, pt);
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D) graphics;
        pageFormat.setOrientation(PageFormat.LANDSCAPE);
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        printInvoice(g2d);


        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
}

class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void incrementY() {
        y += Constants.INVOICE_FONT_HEIGHT;
    }
}