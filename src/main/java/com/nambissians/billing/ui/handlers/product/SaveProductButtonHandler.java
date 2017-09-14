package com.nambissians.billing.ui.handlers.product;

import com.nambissians.billing.model.Product;
import com.nambissians.billing.service.ProductServiceImpl;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import com.nambissians.billing.utils.NotificationUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

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

public class SaveProductButtonHandler implements EventHandler<ActionEvent> {
    private static final Logger logger = LoggerFactory.getLogger(SaveProductButtonHandler.class);

    private ProductServiceImpl productService = new ProductServiceImpl();
    private TextField txtTag;
    private TextArea txtDescription;
    private TextField txtHSNCode;
    private ListView lstTaxes;
    private TextField txtPrice;
    private Map<String, Long> taxMap;

    public SaveProductButtonHandler(TextField txtTag, TextArea txtDescription, TextField txtHSNCode,
                                    ListView lstTaxes, TextField txtPrice, Map<String, Long> taxMap) {
        this.txtTag = txtTag;
        this.txtDescription = txtDescription;
        this.txtHSNCode = txtHSNCode;
        this.lstTaxes = lstTaxes;
        this.txtPrice = txtPrice;
        this.taxMap = taxMap;
    }

    private void clear() {
        txtTag.setText(Constants.EMPTY_STRING);
        txtPrice.setText(Constants.EMPTY_STRING);
        txtHSNCode.setText(Constants.EMPTY_STRING);
        txtDescription.setText(Constants.EMPTY_STRING);
        txtPrice.setText(Constants.EMPTY_STRING);
        lstTaxes.getSelectionModel().clearSelection();
        NotificationUtils.showMessage(InternationalizationUtil.getString(Constants.MSG_SAVED_PRODUCT_SUCCESSFULLY));
    }
    @Override
    public void handle(ActionEvent event) {
        try {
            Product prd = new Product();
            prd.setPrice(Float.valueOf(txtPrice.getText()));
            prd.setHsnCode(txtHSNCode.getText());
            prd.setDescription(txtDescription.getText());
            prd.setTag(txtTag.getText());
            long taxes = 0l;
            ObservableList<String> selectedTaxes = lstTaxes.getSelectionModel().getSelectedItems();
            logger.debug("Selected taxes : {}", selectedTaxes);
            for(String tax : selectedTaxes) {
                taxes |= taxMap.get(tax);
            }
            logger.debug("Computed tax is {}", taxes);
            prd.setTaxes(taxes);
            productService.createProduct(prd);
            clear();
        } catch (Exception exp) {
            NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_COULD_NOT_SAVE_PRODUCT_DETAIL));
            logger.error("Couldn't save product", exp);
        }
    }
}
