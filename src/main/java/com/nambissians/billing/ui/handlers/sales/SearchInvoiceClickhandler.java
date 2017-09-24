package com.nambissians.billing.ui.handlers.sales;/**
 * Created by SajiV on 21/09/17.
 */

import com.nambissians.billing.model.SaleRecord;
import com.nambissians.billing.service.SaleReportServiceImpl;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import com.nambissians.billing.utils.NotificationUtils;
import com.nambissians.billing.utils.StringUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
public class SearchInvoiceClickhandler implements EventHandler<ActionEvent> {
    private ComboBox<String> cmbInvoiceType;
    private TextField txtInvoiceNumber;
    private EditSalesTitledHandler editSalesTitledHandler;
    private SaleReportServiceImpl saleReportService = new SaleReportServiceImpl();


    public SearchInvoiceClickhandler(ComboBox<String> cmbInvoiceType, TextField txtInvoiceNumber, EditSalesTitledHandler editSalesTitledHandler) {
        this.cmbInvoiceType = cmbInvoiceType;
        this.txtInvoiceNumber = txtInvoiceNumber;
        this.editSalesTitledHandler = editSalesTitledHandler;
    }

    @Override
    public void handle(ActionEvent event) {
        String initial = cmbInvoiceType.getSelectionModel().getSelectedItem();
        if (StringUtils.isLongVal(txtInvoiceNumber.getText())) {
            Long invoiceNumber = Long.valueOf(txtInvoiceNumber.getText());
            SaleRecord selectedSaleRecord = saleReportService.getSaleRecord(initial, invoiceNumber);
            editSalesTitledHandler.setSaleRecord(selectedSaleRecord);
        } else {
            NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_NOT_VALID_INVOICE_NUM));
        }
    }
}