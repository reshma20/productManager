package com.nambissians.billing.ui.handlers.sales;/**
 * Created by SajiV on 10/09/17.
 */

import com.nambissians.billing.model.*;
import com.nambissians.billing.service.SaleReportServiceImpl;
import com.nambissians.billing.ui.screen.CustomerSearchEventHandler;
import com.nambissians.billing.utils.UUIDGenerator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleGroup;

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
public class SaveInvoiceButtonHandler implements EventHandler<ActionEvent> {

    private CustomerSearchEventHandler customerSearchEventHandler;
    private NewSalesTitledHandler newSalesTitledHandler;
    private ToggleGroup rbToggleGp;
    private SaleReportServiceImpl saleReportService = new SaleReportServiceImpl();

    private ObservableList<SaleReport> data;

    public SaveInvoiceButtonHandler(ToggleGroup rbToggleGp, CustomerSearchEventHandler customerSearchEventHandler, NewSalesTitledHandler newSalesTitledHandler, ObservableList<SaleReport> data) {
        this.customerSearchEventHandler = customerSearchEventHandler;
        this.newSalesTitledHandler = newSalesTitledHandler;
        this.rbToggleGp = rbToggleGp;
        this.data = data;
    }

    protected void handleInternal(ActionEvent event, String uuid) {
        Customer customer;
        CustomerType custType = (CustomerType) rbToggleGp.getSelectedToggle().getUserData();
        if (CustomerType.NEW_CUSTOMER.equals(custType)) {
            customer = newSalesTitledHandler.getNewCustomer();
        } else {
            customer = customerSearchEventHandler.getSelectedCustomer();
        }
        SaleMetaData saleMetaData = newSalesTitledHandler.getSaleMetaData();
        saleMetaData.setUuid(uuid);
        SaleRecord saleRecord = new SaleRecord();
        saleRecord.setCustomer(customer);
        saleRecord.setSaleMetaData(saleMetaData);
        saleRecord.setBreakUp(data);
        if (saleReportService.persist(saleRecord)) {
            customerSearchEventHandler.clearExistingCustomer();
            newSalesTitledHandler.clearData();
            newSalesTitledHandler.clearNewCustomer();
            newSalesTitledHandler.clearSalesMetaData();
        }
    }

    @Override
    public void handle(ActionEvent event) {
        handleInternal(event, UUIDGenerator.getNextUUID());
    }
}
