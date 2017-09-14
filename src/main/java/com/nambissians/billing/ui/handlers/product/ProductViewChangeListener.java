package com.nambissians.billing.ui.handlers.product;/**
 * Created by SajiV on 09/09/17.
 */

import com.nambissians.billing.model.Product;
import com.nambissians.billing.service.ProductServiceImpl;
import com.nambissians.billing.ui.screen.AbstractTitledPaneChangeListener;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.GridUtils;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.Timestamp;
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
public class ProductViewChangeListener extends AbstractTitledPaneChangeListener {

    private ProductServiceImpl productService = new ProductServiceImpl();
    private boolean forceRefresh;
    private List<Product> products;
    private boolean enableRowClick;
    private Product selectedProduct;

    public ProductViewChangeListener(TitledPane pane, List<Product> products, boolean loadFromDB, boolean enableRowClick) {
        super(pane);
        this.forceRefresh = loadFromDB;
        this.products = products;
        this.enableRowClick = enableRowClick;
    }

    public TableView populateTable() {
        TableView table = new TableView();
        table.setMinHeight(Constants.TITLED_HEIGHT);
        table.setMinWidth(Constants.TITLED_WIDTH);
        ObservableList<Product> data;
        if (forceRefresh) {
            data = GridUtils.convertToObservableList(productService.getAllProductsWithApplicableTaxes());
        } else {
            data = GridUtils.convertToObservableList(products);
        }

        TableColumn id = new TableColumn(InternationalizationUtil.getString(Constants.ID));
        id.setCellValueFactory(new PropertyValueFactory<Product, Long>(Constants.ID));
        id.prefWidthProperty().bind(table.widthProperty().divide(20));
        TableColumn tag = new TableColumn(InternationalizationUtil.getString(Constants.TAG));
        tag.setCellValueFactory(new PropertyValueFactory<Product, String>(Constants.TAG));
        tag.prefWidthProperty().bind(table.widthProperty().divide(8));
        TableColumn description = new TableColumn(InternationalizationUtil.getString(Constants.DESCRIPTION));
        description.setCellValueFactory(new PropertyValueFactory<Product, String>(Constants.DESCRIPTION));
        description.prefWidthProperty().bind(table.widthProperty().divide(10).multiply(3));
        TableColumn hsnCode = new TableColumn(InternationalizationUtil.getString(Constants.HSN_CODE));
        hsnCode.setCellValueFactory(new PropertyValueFactory<Product, String>(Constants.HSN_CODE_LABEL));
        hsnCode.prefWidthProperty().bind(table.widthProperty().divide(20));
        TableColumn price = new TableColumn(InternationalizationUtil.getString(Constants.PRICE));
        price.setCellValueFactory(new PropertyValueFactory<Product, String>(Constants.PRICE));
        price.prefWidthProperty().bind(table.widthProperty().divide(8));
        TableColumn applicableTaxes = new TableColumn(InternationalizationUtil.getString(Constants.TAXES));
        applicableTaxes.setCellValueFactory(new PropertyValueFactory<Product, String>(Constants.APPLICABLE_TAXES));
        applicableTaxes.prefWidthProperty().bind(table.widthProperty().divide(8));
        TableColumn createDate = new TableColumn(InternationalizationUtil.getString(Constants.CREATE_DATE));
        createDate.setCellValueFactory(new PropertyValueFactory<Product, Timestamp>(Constants.CREATE_DATE));
        createDate.prefWidthProperty().bind(table.widthProperty().divide(40).multiply(9));
        table.setItems(data);
        table.getColumns().addAll(id, tag, description, hsnCode, price, applicableTaxes, createDate);
        if (enableRowClick) {
            table.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                        Node node = ((Node) event.getTarget()).getParent();
                        if (node instanceof TableRow ) {
                            TableRow row = (TableRow) node;
                            selectedProduct = (Product) row.getItem();
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.close();
                        }
                    }
                }

            });
        }
        return table;
    }

    public void populateScrollPane(ScrollPane scrollPane) {
        scrollPane.setContent(populateTable());
    }

    @Override
    protected void populatePane(TitledPane pane) {
        pane.setContent(populateTable());
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }
}
