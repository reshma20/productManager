package com.nambissians.billing.ui.screen;/**
 * Created by SajiV on 08/09/17.
 */

import com.nambissians.billing.ui.handlers.CancelButtonTitledHandler;
import com.nambissians.billing.ui.handlers.customer.SaveCustomerButtonHandler;
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
public class NewCustomerTitledChangeListener extends AbstractTitledPaneChangeListener {

    public NewCustomerTitledChangeListener(TitledPane pane) {
        super(pane);
    }

    protected GridPane populateCustomerDetailsSection() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label lblName = new Label();
        lblName.setText(InternationalizationUtil.getString(Constants.NAME));
        TextField txtCustomerName = new TextField();
        txtCustomerName.setText(Constants.EMPTY_STRING);
        gridPane.add(lblName, 0, 1);
        gridPane.add(txtCustomerName, 1, 1);

        Label lblAddress = new Label();
        lblAddress.setText(InternationalizationUtil.getString(Constants.ADDRESS));
        TextArea txtAddress = new TextArea();
        txtAddress.setText(Constants.EMPTY_STRING);
        txtAddress.setMaxHeight(75);
        gridPane.add(lblAddress, 0, 2);
        gridPane.add(txtAddress, 1, 2);

        Label lblGSTIN = new Label();
        lblGSTIN.setText(InternationalizationUtil.getString(Constants.GSTIN));
        TextField txtGSTIN = new TextField();
        txtGSTIN.setText(Constants.EMPTY_STRING);
        gridPane.add(lblGSTIN, 0, 3);
        gridPane.add(txtGSTIN, 1, 3);

        Label lblTel = new Label();
        lblTel.setText(InternationalizationUtil.getString(Constants.TEL));
        TextField txtTel = new TextField();
        txtTel.setText(Constants.EMPTY_STRING);
        gridPane.add(lblTel, 0, 4);
        gridPane.add(txtTel, 1, 4);

        Label lblEmail = new Label();
        lblEmail.setText(InternationalizationUtil.getString(Constants.EMAIL));
        TextField txtEmail = new TextField();
        txtEmail.setText(Constants.EMPTY_STRING);
        gridPane.add(lblEmail, 0, 5);
        gridPane.add(txtEmail, 1, 5);
        return gridPane;
    }

    protected GridPane populateGridPane(TitledPane pane) {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label lblName = new Label();
        lblName.setText(InternationalizationUtil.getString(Constants.NAME));
        TextField txtCustomerName = new TextField();
        txtCustomerName.setText(Constants.EMPTY_STRING);
        gridPane.add(lblName, 0, 1);
        gridPane.add(txtCustomerName, 1, 1);

        Label lblAddress = new Label();
        lblAddress.setText(InternationalizationUtil.getString(Constants.ADDRESS));
        TextArea txtAddress = new TextArea();
        txtAddress.setText(Constants.EMPTY_STRING);
        txtAddress.setMaxHeight(75);
        gridPane.add(lblAddress, 0, 2);
        gridPane.add(txtAddress, 1, 2);

        Label lblGSTIN = new Label();
        lblGSTIN.setText(InternationalizationUtil.getString(Constants.GSTIN));
        TextField txtGSTIN = new TextField();
        txtGSTIN.setText(Constants.EMPTY_STRING);
        gridPane.add(lblGSTIN, 0, 3);
        gridPane.add(txtGSTIN, 1, 3);

        Label lblTel = new Label();
        lblTel.setText(InternationalizationUtil.getString(Constants.TEL));
        TextField txtTel = new TextField();
        txtTel.setText(Constants.EMPTY_STRING);
        gridPane.add(lblTel, 0, 4);
        gridPane.add(txtTel, 1, 4);

        Label lblEmail = new Label();
        lblEmail.setText(InternationalizationUtil.getString(Constants.EMAIL));
        TextField txtEmail = new TextField();
        txtEmail.setText(Constants.EMPTY_STRING);
        gridPane.add(lblEmail, 0, 5);
        gridPane.add(txtEmail, 1, 5);

        Button btnSave = new Button(InternationalizationUtil.getString(Constants.SAVE));
        btnSave.setOnAction(new SaveCustomerButtonHandler(txtCustomerName, txtAddress, txtGSTIN, txtTel, txtEmail));
        gridPane.add(btnSave, 0, 6);
        btnSave.setDefaultButton(true);

        Button btnCancel = new Button(InternationalizationUtil.getString(Constants.CANCEL));
        btnCancel.setOnAction(new CancelButtonTitledHandler(pane));
        btnCancel.setCancelButton(true);
        gridPane.add(btnCancel, 1, 6);
        return gridPane;
    }

    @Override
    protected void populatePane(TitledPane pane) {
        pane.setContent(populateGridPane(pane));
    }

}
