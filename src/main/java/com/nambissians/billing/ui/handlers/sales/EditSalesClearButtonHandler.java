package com.nambissians.billing.ui.handlers.sales;/**
 * Created by SajiV on 22/09/17.
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

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
public class EditSalesClearButtonHandler implements EventHandler<ActionEvent> {

    private EditSalesTitledHandler editSalesTitledHandler;//AbstractTitledPaneChangeListener

    public EditSalesClearButtonHandler(EditSalesTitledHandler editSalesTitledHandler) {
        this.editSalesTitledHandler = editSalesTitledHandler;
    }

    @Override
    public void handle(ActionEvent event) {
        editSalesTitledHandler.clearNewCustomer();
        editSalesTitledHandler.clearData();
        editSalesTitledHandler.clearSalesMetaData();
        //editSalesTitledHandler.setSaleRecord(null);
    }
}