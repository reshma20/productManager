package com.nambissians.billing.ui.screen;


import com.nambissians.billing.model.Product;
import com.nambissians.billing.model.Tax;
import com.nambissians.billing.service.ProductServiceImpl;
import com.nambissians.billing.service.TaxServiceImpl;
import com.nambissians.billing.ui.handlers.CancelButtonTitledHandler;
import com.nambissians.billing.ui.handlers.product.EditProductHandler;
import com.nambissians.billing.ui.handlers.product.NewProductHandler;
import com.nambissians.billing.ui.handlers.product.ProductViewChangeListener;
import com.nambissians.billing.ui.handlers.product.SaveProductButtonHandler;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.GridUtils;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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

public class ProductPane extends TitlePaneGenerator {

    public ProductPane(Stage stage) {
        setStage(stage);
    }

    @Override
    public TitledPane generatePanel(Accordion root) {
        TitledPane productName = new TitledPane();
        productName.setText(InternationalizationUtil.getString(Constants.PRODUCT));
        Accordion subRoot = new Accordion();

        subRoot.setPadding(new Insets(0, 0, 0, 10));
        TitledPane newPane = new NewProductHandler(InternationalizationUtil.getString(Constants.NEW)).handle();
        newPane.expandedProperty().addListener(new NewProductTitledPaneChangeListener(newPane));
        TitledPane listPane = new EditProductHandler(InternationalizationUtil.getString(Constants.LIST)).handle();
        listPane.expandedProperty().addListener(new ProductViewChangeListener(listPane, null, true, false));
        subRoot.getPanes().addAll(newPane, listPane);
        productName.setContent(subRoot);
        return productName;
    }
}

class NewProductTitledPaneChangeListener extends AbstractTitledPaneChangeListener {
    private TaxServiceImpl taxService = new TaxServiceImpl();

    public NewProductTitledPaneChangeListener(TitledPane pane) {
        super(pane);
    }

    @Override
    protected void populatePane(TitledPane pane) {
        pane.setMinWidth(Constants.TITLED_WIDTH);
        pane.setMinHeight(Constants.TITLED_HEIGHT);
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
        txtDescription.setMaxHeight(75);
        gridPane.add(lblDesc, 0, 2);
        gridPane.add(txtDescription, 1, 2);

        Label lblHSNCode = new Label();
        lblHSNCode.setText(InternationalizationUtil.getString(Constants.HSN_CODE));
        TextField txtHSNCode = new TextField();
        txtHSNCode.setText(Constants.EMPTY_STRING);
        gridPane.add(lblHSNCode, 0, 3);
        gridPane.add(txtHSNCode, 1, 3);

        Label lblTaxes = new Label();
        lblTaxes.setText(InternationalizationUtil.getString(Constants.TAXES));
        List<Tax> taxes = taxService.getTaxes();
        Map<String, Long> taxMap = new HashMap<>();
        ListView lstTaxes = new ListView();
        lstTaxes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        lstTaxes.setMinHeight(100);
        for (Tax tax : taxes) {
            taxMap.put(tax.getTag(), tax.getId());
            lstTaxes.getItems().add(tax.getTag());
        }
        gridPane.add(lblTaxes, 0, 4);
        gridPane.add(lstTaxes, 1, 4);

        Label lblPrice = new Label();
        lblPrice.setText(InternationalizationUtil.getString(Constants.PRICE));
        TextField txtPrice = new TextField();
        txtPrice.setText(Constants.EMPTY_STRING);
        gridPane.add(lblPrice, 0, 5);
        gridPane.add(txtPrice, 1, 5);

        Button btnSave = new Button(InternationalizationUtil.getString(Constants.SAVE));
        btnSave.setOnAction(new SaveProductButtonHandler(txtTag, txtDescription, txtHSNCode, lstTaxes, txtPrice, taxMap));
        gridPane.add(btnSave, 0, 6);
        btnSave.setDefaultButton(true);

        Button btnCancel = new Button(InternationalizationUtil.getString(Constants.CANCEL));
        btnCancel.setOnAction(new CancelButtonTitledHandler(pane));
        btnCancel.setCancelButton(true);
        gridPane.add(btnCancel, 1, 6);
    }
}


