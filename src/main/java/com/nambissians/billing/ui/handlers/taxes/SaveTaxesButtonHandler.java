package com.nambissians.billing.ui.handlers.taxes;

import com.nambissians.billing.model.Tax;
import com.nambissians.billing.model.TaxHead;
import com.nambissians.billing.service.TaxServiceImpl;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import com.nambissians.billing.utils.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class SaveTaxesButtonHandler implements EventHandler<ActionEvent> {
    private static final Logger logger = LoggerFactory.getLogger(SaveTaxesButtonHandler.class);
    private TextField txtTag;
    private TextArea txtDescription;
    private TextField txtPercentage;
    private ComboBox<TaxHead> cmbTaxHead;
    private TaxServiceImpl taxService = new TaxServiceImpl();

    public SaveTaxesButtonHandler(TextField txtTag, TextArea txtDescription, TextField txtPercentage, ComboBox<TaxHead> cmbTaxHead) {
        this.txtTag = txtTag;
        this.txtDescription = txtDescription;
        this.txtPercentage = txtPercentage;
        this.cmbTaxHead = cmbTaxHead;
    }

    private void clear() {
        NotificationUtils.showMessage(InternationalizationUtil.getString(Constants.MSG_SAVED_TAX_SUCCESSFULLY));
        txtDescription.setText(Constants.EMPTY_STRING);
        txtTag.setText(Constants.EMPTY_STRING);
        txtPercentage.setText(Constants.EMPTY_STRING);
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            Tax tax = new Tax();
            tax.setDescription(txtDescription.getText());
            tax.setTag(txtTag.getText());
            tax.setPercentage(Float.valueOf(txtPercentage.getText()));
            tax.setTaxHead(cmbTaxHead.getSelectionModel().getSelectedItem());
            taxService.persist(tax);
            clear();
        } catch (Exception exp) {
            NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_COULD_NOT_SAVE_TAX_DETAIL));
            logger.error("Couldn't save tax", exp);
        }
    }
}
