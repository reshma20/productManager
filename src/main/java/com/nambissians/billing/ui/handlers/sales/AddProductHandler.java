package com.nambissians.billing.ui.handlers.sales;/**
 * Created by SajiV on 12/09/17.
 */

import com.nambissians.billing.model.Product;
import com.nambissians.billing.model.SaleReport;
import com.nambissians.billing.model.Tax;
import com.nambissians.billing.service.TaxServiceImpl;
import com.nambissians.billing.ui.handlers.product.ProductSearchEventHandler;
import com.nambissians.billing.utils.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

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
public class AddProductHandler implements EventHandler<ActionEvent> {

    private ProductSearchEventHandler productSearchEventHandler;
    private TaxServiceImpl taxService = new TaxServiceImpl();
    private Label lblTotal;
    private ObservableList<SaleReport> data;

    public AddProductHandler(ProductSearchEventHandler productSearchEventHandler, ObservableList<SaleReport> data, Label lblTotal) {
        this.productSearchEventHandler = productSearchEventHandler;
        this.data = data;
        this.lblTotal = lblTotal;
    }

    @Override
    public void handle(ActionEvent event) {
        Product prd = productSearchEventHandler.getSelectedProduct();
        SaleReport sr = new SaleReport();
        sr.setProductId(prd.getId());
        sr.setPrice(prd.getPrice());
        sr.setProductName(prd.getTag());
        if (StringUtils.isLongVal(productSearchEventHandler.getTxtQuantity().getText()) == false) {
            NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_QUANTITY_NOT_VALID));
            return;
        }
        sr.setQuantity(Long.valueOf(productSearchEventHandler.getTxtQuantity().getText()));
        if (StringUtils.isFloatVal(productSearchEventHandler.getTxtRebate().getText()) == false) {
            NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_REBATE_NOT_VALID));
            return;
        }
        sr.setRebate(Float.valueOf(productSearchEventHandler.getTxtRebate().getText()));
        productSearchEventHandler.getTxtRebate().setText(Constants.EMPTY_STRING);
        productSearchEventHandler.getTxtQuantity().setText(Constants.EMPTY_STRING);
        productSearchEventHandler.getTxtSearchContent().setText(Constants.EMPTY_STRING);
        List<Tax> taxes = taxService.getTaxes();
        float cgst = 0, sgst = 0, amount, taxableAmount = 0;
        amount = sr.getPrice() * sr.getQuantity();
        taxableAmount = MathUtils.roundoffToTwoPlaces(amount * (1 - sr.getRebate() / 100));
        sr.setAmount(amount);
        sr.setTaxableAmount(taxableAmount);
        for (Tax tax : taxes) {
            if ((tax.getId() & prd.getTaxes()) != 0) {
                switch (tax.getTaxHead()) {
                    case CGST:
                        cgst += MathUtils.roundoffToTwoPlaces(taxableAmount * tax.getPercentage() / 100);
                        break;
                    case SGST:
                        sgst += MathUtils.roundoffToTwoPlaces(taxableAmount * tax.getPercentage() / 100);
                        break;
                }
            }
        }
        sr.setCgst(cgst);
        sr.setSgst(sgst);
        sr.setFinalAmount(MathUtils.roundoffToTwoPlaces(taxableAmount + cgst + sgst));
        float total = Float.parseFloat(lblTotal.getText());
        total += sr.getFinalAmount();
        lblTotal.setText(MathUtils.roundoffToTwoPlaces(total) + Constants.EMPTY_STRING);
        data.add(sr);
        productSearchEventHandler.getTxtQuantity().setText(Constants.ONE);
        productSearchEventHandler.getTxtRebate().setText(Constants.ZERO);
    }
}
