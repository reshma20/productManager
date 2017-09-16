package com.nambissians.billing.ui.handlers.sales;/**
 * Created by SajiV on 15/09/17.
 */

import com.nambissians.billing.model.OwnerProfile;
import com.nambissians.billing.model.Product;
import com.nambissians.billing.model.SaleMetaData;
import com.nambissians.billing.model.SaleRecord;
import com.nambissians.billing.service.ProductServiceImpl;
import com.nambissians.billing.service.ProfileServiceImpl;
import com.nambissians.billing.service.SaleReportServiceImpl;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import com.nambissians.billing.utils.NotificationUtils;
import com.nambissians.billing.utils.PDFWriter;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
public class ExportSaleListener implements EventHandler<ActionEvent> {
    private SearchInvoiceButtonHandler handler;
    private SaleReportServiceImpl saleReportService = new SaleReportServiceImpl();
    private ProfileServiceImpl profileService = new ProfileServiceImpl();
    private ProductServiceImpl productService = new ProductServiceImpl();
    private static final Logger logger = LoggerFactory.getLogger(ExportSaleListener.class);

    public ExportSaleListener(SearchInvoiceButtonHandler handler) {
        this.handler = handler;
    }

    @Override
    public void handle(ActionEvent event) {
        ObservableList<SaleMetaData> data = handler.getData();
        List<SaleRecord> saleRecords = new ArrayList<>();
        for (SaleMetaData smd : data) {
            SaleRecord sr = saleReportService.getSaleRecord(smd.getUuid(), false);
            saleRecords.add(sr);
        }
        OwnerProfile profile = profileService.getOwnerProfile();
        List<Product> products = productService.getAllProductsWithApplicableTaxes();
        Timestamp ts = new Timestamp(Calendar.getInstance().getTimeInMillis());
        try {
            String date = Constants.SDF.format(ts);
            date = date.replace("/", "_");
            date = date.replace(":","_");
            date = date.replace(" ", Constants.EMPTY_STRING);
            String filePath = PDFWriter.createInvoiceReport(saleRecords, date, products, profile);
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File(filePath);
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    // no application registered for PDFs
                    logger.error("Couldn't open the file {}", filePath, ex);
                }
            }
        } catch (Exception exp) {
            logger.error("Couldn't generate the report ", exp);
            NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_COULD_NOT_GENERATE_REPORT));
        }
    }
}
