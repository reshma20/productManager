package com.nambissians.billing.ui.handlers.customer;/**
 * Created by SajiV on 08/09/17.
 */

import com.nambissians.billing.model.CustomerSearchType;
import com.nambissians.billing.ui.handlers.CancelButtonTitledHandler;
import com.nambissians.billing.ui.screen.AbstractTitledPaneChangeListener;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
public class EditCustomerTitledChangeListener extends AbstractTitledPaneChangeListener {

    private ComboBox<CustomerSearchType> cmbSearchBy = new ComboBox<>();
    private TextField txtSearchContent;
    private TextField txtCustomerName = new TextField();
    private TextArea txtAddress = new TextArea();
    private TextField txtTel = new TextField();
    private TextField txtGSTIN = new TextField();
    private TextField txtEmail = new TextField();
    private TextField txtId = new TextField();

    private EditCustomerSearchEventHandler customerSearchEventHandler;
    private static final Insets insets = new Insets(10, 10, 10, 10);

    public EditCustomerTitledChangeListener(TitledPane pane) {
        super(pane);
        txtSearchContent = new TextField();
        customerSearchEventHandler = new EditCustomerSearchEventHandler(cmbSearchBy, txtSearchContent, txtCustomerName, txtId, txtTel, txtAddress, txtGSTIN, txtEmail);
    }

    protected GridPane populateGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label lblName = new Label();
        lblName.setText(InternationalizationUtil.getString(Constants.NAME));

        txtCustomerName.setText(Constants.EMPTY_STRING);
        gridPane.add(lblName, 0, 1);
        gridPane.add(txtCustomerName, 1, 1);

        Label lblAddress = new Label();
        lblAddress.setText(InternationalizationUtil.getString(Constants.ADDRESS));
        txtAddress.setText(Constants.EMPTY_STRING);
        txtAddress.setMaxHeight(75);
        gridPane.add(lblAddress, 0, 2);
        gridPane.add(txtAddress, 1, 2);

        Label lblGSTIN = new Label();
        lblGSTIN.setText(InternationalizationUtil.getString(Constants.GSTIN));
        txtGSTIN.setText(Constants.EMPTY_STRING);
        gridPane.add(lblGSTIN, 0, 3);
        gridPane.add(txtGSTIN, 1, 3);

        Label lblTel = new Label();
        lblTel.setText(InternationalizationUtil.getString(Constants.TEL));
        txtTel.setText(Constants.EMPTY_STRING);
        gridPane.add(lblTel, 0, 4);
        gridPane.add(txtTel, 1, 4);

        Label lblEmail = new Label();
        lblEmail.setText(InternationalizationUtil.getString(Constants.EMAIL));
        txtEmail.setText(Constants.EMPTY_STRING);
        gridPane.add(lblEmail, 0, 5);
        gridPane.add(txtEmail, 1, 5);
        return gridPane;
    }

    protected HBox populateSearchBox() {
        ScrollPane existingCustomerScroll = new ScrollPane();
        existingCustomerScroll.setPadding(insets);
        VBox mainSearchBox = new VBox();
        mainSearchBox.setSpacing(10);
        HBox searchBox = new HBox();
        searchBox.setMinHeight(30);
        searchBox.setSpacing(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setPadding(insets);
        Label lblSearchBy = new Label(InternationalizationUtil.getString(Constants.SEARCH_BY));
        cmbSearchBy.getItems().addAll(CustomerSearchType.NAME, CustomerSearchType.TEL, CustomerSearchType.ID);
        cmbSearchBy.getSelectionModel().select(CustomerSearchType.NAME);
        Button btnSearch = new Button(InternationalizationUtil.getString(Constants.SEARCH));
        btnSearch.setOnAction(customerSearchEventHandler);
        searchBox.getChildren().addAll(lblSearchBy, cmbSearchBy, txtSearchContent, btnSearch);
        searchBox.setAlignment(Pos.CENTER);
        return searchBox;
    }

    private HBox populateCommands(TitledPane pane) {
        Button btnSave = new Button();
        btnSave.setText(InternationalizationUtil.getString(Constants.SAVE));
        btnSave.setOnAction(new EditCustomerButtonHandler(txtCustomerName, txtAddress, txtGSTIN, txtTel, txtEmail, customerSearchEventHandler));
        Button btnCancel = new Button();
        btnCancel.setText(InternationalizationUtil.getString(Constants.CANCEL));
        btnCancel.setOnAction(new CancelButtonTitledHandler(pane));
        HBox commandBox = new HBox();
        commandBox.setAlignment(Pos.CENTER);
        commandBox.getChildren().addAll(btnSave, btnCancel);
        commandBox.setPadding(insets);
        commandBox.setSpacing(5);
        return commandBox;
    }

    @Override
    protected void populatePane(TitledPane pane) {
        VBox editCustomerBox = new VBox();
        editCustomerBox.getChildren().addAll(populateSearchBox(), populateGridPane(), populateCommands(pane));
        pane.setContent(editCustomerBox);
    }

}
