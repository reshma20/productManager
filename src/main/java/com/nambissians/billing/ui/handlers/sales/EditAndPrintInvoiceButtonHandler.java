package com.nambissians.billing.ui.handlers.sales;/**
 * Created by SajiV on 10/09/17.
 */

import com.nambissians.billing.model.OwnerProfile;
import com.nambissians.billing.model.Product;
import com.nambissians.billing.model.SaleRecord;
import com.nambissians.billing.service.ProductServiceImpl;
import com.nambissians.billing.service.ProfileServiceImpl;
import com.nambissians.billing.service.SaleReportServiceImpl;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import com.nambissians.billing.utils.NotificationUtils;
import com.nambissians.billing.utils.PDFWriter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
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
public class EditAndPrintInvoiceButtonHandler implements EventHandler<ActionEvent> {

    private static final Logger logger = LoggerFactory.getLogger(EditAndPrintInvoiceButtonHandler.class);
    private EditSalesTitledHandler editSalesTitledHandler;
    private SaleReportServiceImpl saleReportService = new SaleReportServiceImpl();
    private ProfileServiceImpl profileService = new ProfileServiceImpl();
    private ProductServiceImpl productService = new ProductServiceImpl();


    public EditAndPrintInvoiceButtonHandler(EditSalesTitledHandler editSalesTitledHandler) {
        this.editSalesTitledHandler = editSalesTitledHandler;
    }

    @Override
    public void handle(ActionEvent event) {
        SaleRecord saleRecord = editSalesTitledHandler.getSaleRecord();
        saleReportService.editSalesReport(saleRecord);
        //PrintUtils.printInvoice(saleRecord,profile, products);
        try {
            OwnerProfile profile = profileService.getOwnerProfile();
            List<Product> products = productService.getAllProductsWithApplicableTaxes();
            saleRecord = saleReportService.getSaleRecord(saleRecord.getSaleMetaData().getUuid(), saleRecord.getSaleMetaData().isGstBill());
            String filePath = PDFWriter.generatePdfInvoice(profile, saleRecord, products);
            editSalesTitledHandler.clearNewCustomer();
            editSalesTitledHandler.clearData();
            editSalesTitledHandler.clearSalesMetaData();
            //editSalesTitledHandler.setSaleRecord(null);
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File(filePath);
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    // no application registered for PDFs
                    logger.error("Couldn't open the file {}", filePath, ex);
                    NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_NO_APP_TO_OPEN));
                }
            }

        } catch (Exception exp) {
            logger.error("Couldn't save the invoice.", exp);
            NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_GEN_INVOICE));
        }
    }
}
