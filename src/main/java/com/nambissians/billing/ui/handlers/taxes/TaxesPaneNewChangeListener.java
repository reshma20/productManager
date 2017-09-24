package com.nambissians.billing.ui.handlers.taxes;/**
 * Created by SajiV on 20/09/17.
 */

import com.nambissians.billing.model.TaxHead;
import com.nambissians.billing.ui.handlers.CancelButtonTitledHandler;
import com.nambissians.billing.ui.screen.AbstractTitledPaneChangeListener;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

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
public class TaxesPaneNewChangeListener extends AbstractTitledPaneChangeListener {

    public TaxesPaneNewChangeListener(TitledPane pane) {
        super(pane);
    }

    protected void populatePane(TitledPane pane) {
        GridPane gridPane = new GridPane();
        pane.setContent(gridPane);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label lblTag = new Label();
        lblTag.setText(InternationalizationUtil.getString(Constants.TAG));
        TextField txtTag = new TextField();
        txtTag.setText(Constants.EMPTY_STRING);
        gridPane.add(lblTag, 0, 1);
        gridPane.add(txtTag, 1, 1);

        Label lblDesc = new Label();
        lblDesc.setText(InternationalizationUtil.getString(Constants.DESCRIPTION));
        TextArea txtDescription = new TextArea();
        txtDescription.setText(Constants.EMPTY_STRING);
        gridPane.add(lblDesc, 0, 2);
        gridPane.add(txtDescription, 1, 2);

        Label lblPercentage = new Label();
        lblPercentage.setText(InternationalizationUtil.getString(Constants.PERCENTAGE));
        TextField txtPercentage = new TextField();
        txtPercentage.setText(Constants.EMPTY_STRING);
        gridPane.add(lblPercentage, 0, 3);
        gridPane.add(txtPercentage, 1, 3);

        Label lblTaxHead = new Label();
        lblTaxHead.setText(InternationalizationUtil.getString(Constants.TAX_HEAD));
        ComboBox<TaxHead> cmbTaxHead = new ComboBox<>();
        cmbTaxHead.getItems().addAll(TaxHead.CGST, TaxHead.SGST);
        cmbTaxHead.getSelectionModel().select(TaxHead.CGST);
        gridPane.add(lblTaxHead, 0, 4);
        gridPane.add(cmbTaxHead, 1, 4);

        Button btnSave = new Button(InternationalizationUtil.getString(Constants.SAVE));
        btnSave.setOnAction(new SaveTaxesButtonHandler(txtTag, txtDescription, txtPercentage, cmbTaxHead));
        gridPane.add(btnSave, 0, 5);
        btnSave.setDefaultButton(true);

        Button btnCancel = new Button(InternationalizationUtil.getString(Constants.CANCEL));
        btnCancel.setOnAction(new CancelButtonTitledHandler(pane));
        btnCancel.setCancelButton(true);
        gridPane.add(btnCancel, 1, 5);
    }
}
