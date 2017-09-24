package com.nambissians.billing.ui.handlers.sales;/**
 * Created by SajiV on 09/09/17.
 */

import com.nambissians.billing.model.*;
import com.nambissians.billing.service.StateServiceImpl;
import com.nambissians.billing.ui.handlers.product.ProductSearchEventHandler;
import com.nambissians.billing.ui.screen.AbstractTitledPaneChangeListener;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
public class EditSalesTitledHandler extends AbstractTitledPaneChangeListener {

    private static Insets INSETS = new Insets(10, 10, 10, 10);
    private SearchInvoiceClickhandler searchInvoiceClickhandler;
    private TextField txtCustomerName = new TextField();
    private TextArea txtAddress = new TextArea();
    private TextField txtGSTIN = new TextField();
    private TextField txtTel = new TextField();
    private TextField txtEmail = new TextField();
    private Label lblTotal = new Label();
    private TextField txtSupplyPlace = new TextField();
    private TextField txtVehicle = new TextField();
    private TextField txtTransportationMode = new TextField();
    private ComboBox<String> cmbState = new ComboBox<>();
    private StateServiceImpl stateService = new StateServiceImpl();
    private final ObservableList<SaleReport> data = FXCollections.observableArrayList();
    private ProductSearchEventHandler productSearchEventHandler;
    private SaleRecord saleRecord;
    private TableView<SaleReport> saleReportTableView;
    private Label invoiceNumber = new Label();

    public EditSalesTitledHandler(TitledPane pane) {
        super(pane);
        lblTotal.setText(Constants.ZERO);
        lblTotal.setFont(Font.font(40));
        lblTotal.setMaxWidth(Constants.TITLED_WIDTH * 1 / 4);
        lblTotal.setMinWidth(Constants.TITLED_WIDTH * 1 / 4);
        lblTotal.setAlignment(Pos.CENTER_RIGHT);
        lblTotal.setPadding(new Insets(10, 10, 10, 20));
        Font fnt = Font.font("Verdana", FontWeight.EXTRA_BOLD, 10);
        invoiceNumber.setFont(fnt);
    }

    private HBox populateSearchBox() {
        HBox searchBox = new HBox();
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setSpacing(5);
        Label lblInvoiceNumber = new Label();
        lblInvoiceNumber.setText(Constants.INVOICE_NUMBER);
        ComboBox<String> cmbInvoiceType = new ComboBox();
        cmbInvoiceType.getItems().addAll(Constants.GST_INVOICE_INITIAL, Constants.GST_BILL_INITIAL);
        cmbInvoiceType.getSelectionModel().select(Constants.GST_BILL_INITIAL);
        TextField txtInvoiceNumber = new TextField();
        Button searchinvoiceButton = new Button();
        searchinvoiceButton.setText(InternationalizationUtil.getString(Constants.SEARCH));
        searchInvoiceClickhandler = new SearchInvoiceClickhandler(cmbInvoiceType, txtInvoiceNumber, this);
        searchinvoiceButton.setOnAction(searchInvoiceClickhandler);
        searchBox.getChildren().addAll(lblInvoiceNumber, cmbInvoiceType, txtInvoiceNumber, searchinvoiceButton);
        return searchBox;
    }

    private HBox renderSalesMetaData() {
        HBox salesMetaData = new HBox();
        salesMetaData.setPadding(INSETS);
        salesMetaData.setSpacing(5);
        salesMetaData.setAlignment(Pos.CENTER);
        Label lblState = new Label(InternationalizationUtil.getString(Constants.STATE));
        List<State> states = stateService.getStates();
        for (State state : states) {
            cmbState.getItems().add(state.getState());
            if (state.isDefaultState()) {
                cmbState.getSelectionModel().select(state.getState());
            }
        }
        Label lblVehicle = new Label(InternationalizationUtil.getString(Constants.VEHICLE));
        Label lblSupplyPlace = new Label(InternationalizationUtil.getString(Constants.SUPPLY_PLACE));
        Label lblTransportationMode = new Label(InternationalizationUtil.getString(Constants.TRANSPORTATION_MODE_LABEL));
        salesMetaData.getChildren().addAll(lblState, cmbState, lblVehicle, txtVehicle, lblSupplyPlace, txtSupplyPlace, lblTransportationMode, txtTransportationMode);
        return salesMetaData;
    }

    public GridPane renderCustomer() {
        GridPane customerGrid = new GridPane();
        customerGrid.setAlignment(Pos.CENTER);
        customerGrid.setHgap(10);
        customerGrid.setVgap(10);

        Label lblName = new Label();
        lblName.setText(InternationalizationUtil.getString(Constants.NAME));
        customerGrid.add(lblName, 0, 1);
        customerGrid.add(txtCustomerName, 1, 1);

        Label lblAddress = new Label();
        lblAddress.setText(InternationalizationUtil.getString(Constants.ADDRESS));
        txtAddress.setMaxHeight(75);
        customerGrid.add(lblAddress, 0, 2);
        customerGrid.add(txtAddress, 1, 2);

        Label lblGSTIN = new Label();
        lblGSTIN.setText(InternationalizationUtil.getString(Constants.GSTIN));
        customerGrid.add(lblGSTIN, 0, 3);
        customerGrid.add(txtGSTIN, 1, 3);

        Label lblTel = new Label();
        lblTel.setText(InternationalizationUtil.getString(Constants.TEL));
        customerGrid.add(lblTel, 0, 4);
        customerGrid.add(txtTel, 1, 4);

        Label lblEmail = new Label();
        lblEmail.setText(InternationalizationUtil.getString(Constants.EMAIL));
        customerGrid.add(lblEmail, 0, 5);
        customerGrid.add(txtEmail, 1, 5);

        return customerGrid;
    }

    private HBox renderCustomerAndTotal() {
        HBox customerAndTotal = new HBox();
        customerAndTotal.setSpacing(5);
        customerAndTotal.setAlignment(Pos.CENTER);

        customerAndTotal.getChildren().addAll(renderCustomer(), lblTotal);
        return customerAndTotal;
    }

    private TableView<SaleReport> getSaleReportTable() {
        TableView<SaleReport> saleReportTable = new TableView<>();

        TableColumn id = new TableColumn(InternationalizationUtil.getString(Constants.P_CODE));
        id.setCellValueFactory(new PropertyValueFactory<SaleReport, Long>(Constants.P_CODE));
        id.prefWidthProperty().bind(saleReportTable.widthProperty().divide(16));

        TableColumn tag = new TableColumn(InternationalizationUtil.getString(Constants.P_NAME));
        tag.setCellValueFactory(new PropertyValueFactory<SaleReport, String>(Constants.P_NAME));
        tag.prefWidthProperty().bind(saleReportTable.widthProperty().divide(4));

        TableColumn price = new TableColumn(InternationalizationUtil.getString(Constants.PRICE));
        price.setCellValueFactory(new PropertyValueFactory<SaleReport, Float>(Constants.PRICE));
        price.prefWidthProperty().bind(saleReportTable.widthProperty().divide(16));

        TableColumn quantity = new TableColumn(InternationalizationUtil.getString(Constants.QUANTITY));
        quantity.setCellValueFactory(new PropertyValueFactory<SaleReport, Long>(Constants.QUANTITY));
        quantity.prefWidthProperty().bind(saleReportTable.widthProperty().divide(16).multiply(3));

        TableColumn amount = new TableColumn(InternationalizationUtil.getString(Constants.AMOUNT));
        amount.setCellValueFactory(new PropertyValueFactory<SaleReport, Long>(Constants.AMOUNT));
        amount.prefWidthProperty().bind(saleReportTable.widthProperty().divide(16));

        TableColumn rebate = new TableColumn(InternationalizationUtil.getString(Constants.REBATE));
        rebate.setCellValueFactory(new PropertyValueFactory<SaleReport, Long>(Constants.REBATE));
        rebate.prefWidthProperty().bind(saleReportTable.widthProperty().divide(16));

        TableColumn taxableAmount = new TableColumn(InternationalizationUtil.getString(Constants.TAXABLE_AMOUNT));
        taxableAmount.setCellValueFactory(new PropertyValueFactory<SaleReport, Long>(Constants.TAXABLE_AMOUNT));
        taxableAmount.prefWidthProperty().bind(saleReportTable.widthProperty().divide(8));

        TableColumn cost = new TableColumn(InternationalizationUtil.getString(Constants.COST));
        cost.getColumns().addAll(amount, rebate, taxableAmount);

        TableColumn cgst = new TableColumn(InternationalizationUtil.getString(Constants.CGST));
        cgst.setCellValueFactory(new PropertyValueFactory<SaleReport, Long>(Constants.CGST));
        cgst.prefWidthProperty().bind(saleReportTable.widthProperty().divide(16));

        TableColumn sgst = new TableColumn(InternationalizationUtil.getString(Constants.SGST));
        sgst.setCellValueFactory(new PropertyValueFactory<SaleReport, Long>(Constants.CGST));
        sgst.prefWidthProperty().bind(saleReportTable.widthProperty().divide(16));

        TableColumn taxes = new TableColumn(InternationalizationUtil.getString(Constants.TAXES));
        taxes.getColumns().addAll(cgst, sgst);

        TableColumn finalAmount = new TableColumn(InternationalizationUtil.getString(Constants.FINAL_AMOUNT));
        finalAmount.setCellValueFactory(new PropertyValueFactory<SaleReport, Long>(Constants.FINAL_AMOUNT));
        finalAmount.prefWidthProperty().bind(saleReportTable.widthProperty().divide(16));

        saleReportTable.getColumns().addAll(id, tag, price, quantity, cost, taxes, finalAmount);
        return saleReportTable;
    }

    private VBox renderProducts() {
        VBox productsSection = new VBox();
        productsSection.setPadding(INSETS);
        productsSection.setSpacing(5);
        ScrollPane saleReportBox = new ScrollPane();
        saleReportTableView = getSaleReportTable();
        saleReportTableView.setItems(data);

        saleReportTableView.setMaxWidth(Constants.TITLED_WIDTH * 3 / 4);
        saleReportTableView.setMinWidth(Constants.TITLED_WIDTH * 3 / 4);
        saleReportTableView.setMaxHeight(Constants.SALE_REPORT_HEIGHT);
        saleReportBox.setMinWidth(Constants.TITLED_WIDTH * 3 / 4);
        saleReportBox.setMaxWidth(Constants.TITLED_WIDTH * 3 / 4);
        saleReportBox.setContent(saleReportTableView);
        saleReportBox.setMaxHeight(200);
        HBox saleBox = new HBox();
        saleBox.setAlignment(Pos.CENTER);
        saleBox.getChildren().addAll(saleReportBox);
        VBox productDetBox = renderProductAddBox();
        productDetBox.setSpacing(5);
        productsSection.getChildren().addAll(productDetBox, saleBox);
        return productsSection;
    }

    private VBox renderProductAddBox() {
        VBox productDetBox = new VBox();
        productDetBox.setSpacing(5);
        productDetBox.setAlignment(Pos.CENTER);
        Label lblProduct = new Label(InternationalizationUtil.getString(Constants.PRODUCT_TAG));
        TextField txtProductName = new TextField();
        txtProductName.setMinWidth(600);
        Label lblQuantity = new Label(InternationalizationUtil.getString(Constants.QUANTITY));
        TextField txtQuantity = new TextField();
        txtQuantity.setText(Constants.ONE);
        Label lblRebate = new Label(InternationalizationUtil.getString(Constants.REBATE));
        TextField txtRebate = new TextField();
        txtRebate.setText(Constants.ZERO);
        Button btnSearchProduct = new Button(InternationalizationUtil.getString(Constants.SEARCH));
        productSearchEventHandler = new ProductSearchEventHandler(txtProductName, txtQuantity, txtRebate);
        btnSearchProduct.setOnAction(productSearchEventHandler);
        Button btnAdd = new Button();
        btnAdd.setDefaultButton(true);
        btnAdd.setText(InternationalizationUtil.getString(Constants.ADD));
        btnAdd.setOnAction(new AddProductHandler(productSearchEventHandler, data, lblTotal));
        Button btnRemove = new Button();
        btnRemove.setText(InternationalizationUtil.getString(Constants.REMOVE));
        //Clear handler
        btnRemove.setOnAction(new RemoveProductHandler(saleReportTableView, data, lblTotal));
        HBox productSearchBox = new HBox();
        productSearchBox.setSpacing(5);
        productSearchBox.setAlignment(Pos.CENTER);
        productSearchBox.getChildren().addAll(lblProduct, txtProductName, btnSearchProduct);
        HBox productControlBox = new HBox();
        productControlBox.setSpacing(5);
        productControlBox.setAlignment(Pos.CENTER);
        productControlBox.getChildren().addAll(lblQuantity, txtQuantity, lblRebate, txtRebate, btnAdd, btnRemove);
        productDetBox.getChildren().addAll(productSearchBox, productControlBox);
        return productDetBox;
    }

    private HBox populateSaleBox() {
        HBox saleBox = new HBox();
        saleBox.setPadding(INSETS);
        saleBox.setSpacing(5);
        saleBox.setAlignment(Pos.CENTER);

        //SaveAndPrint
        Button btnPrintInvoice = new Button();
        btnPrintInvoice.setText(InternationalizationUtil.getString(Constants.PRINT_AND_SAVE_INVOICE));
        btnPrintInvoice.setOnAction(new EditAndPrintInvoiceButtonHandler(this));
        //Clear Button
        Button btnClear = new Button();
        btnClear.setText(InternationalizationUtil.getString(Constants.CLEAR));
        btnClear.setOnAction(new EditSalesClearButtonHandler(this));

        saleBox.getChildren().addAll(btnPrintInvoice, btnClear);
        saleBox.setAlignment(Pos.CENTER);
        return saleBox;
    }

    @Override
    protected void populatePane(TitledPane pane) {
        ScrollPane editInvoicePane = new ScrollPane();
        VBox editInvoiceVBox = new VBox();
        editInvoiceVBox.setSpacing(5);
        editInvoicePane.setPadding(INSETS);
        editInvoiceVBox.setMinHeight(Constants.TITLED_HEIGHT);
        editInvoiceVBox.setMinWidth(Constants.TITLED_WIDTH);
        editInvoiceVBox.setAlignment(Pos.CENTER);
        editInvoiceVBox.getChildren().addAll(populateSearchBox(), invoiceNumber, renderCustomerAndTotal(), renderSalesMetaData(), renderProducts(), populateSaleBox());
        editInvoicePane.setContent(editInvoiceVBox);
        editInvoicePane.setMinHeight(Constants.TITLED_HEIGHT - 20);
        pane.setContent(editInvoicePane);
    }

    public void clearNewCustomer() {
        txtCustomerName.setText(Constants.EMPTY_STRING);
        txtAddress.setText(Constants.EMPTY_STRING);
        txtGSTIN.setText(Constants.EMPTY_STRING);
        txtTel.setText(Constants.EMPTY_STRING);
        txtEmail.setText(Constants.EMPTY_STRING);
    }

    public void clearSalesMetaData() {
        txtSupplyPlace.setText(Constants.EMPTY_STRING);
        txtVehicle.setText(Constants.EMPTY_STRING);
        lblTotal.setText(Constants.ZERO);
        txtTransportationMode.setText(Constants.EMPTY_STRING);
        invoiceNumber.setText(Constants.EMPTY_STRING);
    }

    public void clearData() {
        data.clear();
    }

    public SaleRecord getSaleRecord() {
        //Update sales record here and then persist
        if (saleRecord == null) {
            return null;
        }
        Customer customer = saleRecord.getCustomer();
        customer.setGstin(txtGSTIN.getText());
        customer.setAddress(txtAddress.getText());
        customer.setCustomerName(txtCustomerName.getText());
        customer.setEmail(txtEmail.getText());
        customer.setTel(txtTel.getText());
        SaleMetaData saleMetaData = saleRecord.getSaleMetaData();
        saleMetaData.setCustomerName(txtCustomerName.getText());
        saleMetaData.setSupplyPlace(txtSupplyPlace.getText());
        saleMetaData.setState(stateService.getStateCode(cmbState.getSelectionModel().getSelectedItem()));
        saleMetaData.setVehicle(txtVehicle.getText());
        saleMetaData.setTotalAmount(Float.valueOf(lblTotal.getText()));
        saleMetaData.setTransportationMode(txtTransportationMode.getText());
        saleRecord.setBreakUp(data);
        return saleRecord;
    }

    public void setSaleRecord(SaleRecord saleRecord) {
        this.saleRecord = saleRecord;
        Customer customer = saleRecord.getCustomer();
        SaleMetaData saleMetaData = saleRecord.getSaleMetaData();
        txtCustomerName.setText(customer.getCustomerName());
        txtAddress.setText(customer.getAddress());
        txtGSTIN.setText(customer.getGstin());
        txtEmail.setText(customer.getEmail());
        txtTel.setText(customer.getTel());
        txtSupplyPlace.setText(saleMetaData.getSupplyPlace());
        txtVehicle.setText(saleMetaData.getVehicle());
        txtTransportationMode.setText(saleMetaData.getTransportationMode());
        cmbState.getSelectionModel().select(stateService.getState(saleMetaData.getState(), Boolean.FALSE));
        invoiceNumber.setText(Constants.INVOICE_NUMBER + saleMetaData.getPrintableInvoiceNumber());
        lblTotal.setText(saleMetaData.getTotalAmount() + Constants.EMPTY_STRING);
        data.clear();
        data.addAll(saleRecord.getBreakUp());
    }
}