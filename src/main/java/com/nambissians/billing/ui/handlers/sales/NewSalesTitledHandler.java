package com.nambissians.billing.ui.handlers.sales;/**
 * Created by SajiV on 09/09/17.
 */

import com.nambissians.billing.model.*;
import com.nambissians.billing.service.StateServiceImpl;
import com.nambissians.billing.ui.handlers.product.ProductSearchEventHandler;
import com.nambissians.billing.ui.screen.AbstractTitledPaneChangeListener;
import com.nambissians.billing.ui.screen.CustomerSearchEventHandler;
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
public class NewSalesTitledHandler extends AbstractTitledPaneChangeListener {
    private Label lblTotal = new Label();
    private TextField txtSupplyPlace = new TextField();
    private TextField txtVehicle = new TextField();
    private TextField txtTransportationMode = new TextField();
    private ComboBox<String> cmbState = new ComboBox<>();
    private Insets insets = new Insets(0, 0, 0, 10);
    private CustomerSearchEventHandler customerSearchEventHandler;
    private ProductSearchEventHandler productSearchEventHandler;
    private ComboBox<CustomerSearchType> cmbSearchBy;
    private TableView<SaleReport> saleReportTableView;
    private final ObservableList<SaleReport> data = FXCollections.observableArrayList();
    private TextField txtCustomerName = new TextField();
    private TextArea txtAddress = new TextArea();
    private TextField txtGSTIN = new TextField();
    private TextField txtTel = new TextField();
    private TextField txtEmail = new TextField();
    private ToggleGroup customerType = new ToggleGroup();
    private StateServiceImpl stateService = new StateServiceImpl();

    public NewSalesTitledHandler(TitledPane pane) {
        super(pane);
        lblTotal.setText(Constants.ZERO);
        lblTotal.setFont(Font.font(40));
        lblTotal.setMaxWidth(Constants.TITLED_WIDTH * 1 / 4);
        lblTotal.setMinWidth(Constants.TITLED_WIDTH * 1 / 4);
        lblTotal.setAlignment(Pos.CENTER_RIGHT);
        lblTotal.setPadding(new Insets(10, 10, 10, 20));
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

    private VBox populateProductDetBox() {
        VBox productDetBox = new VBox();
        productDetBox.setSpacing(5);
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

    private ScrollPane populateExistingCustomer() {
        TextField txtSearchContent;
        TextField txtTel;
        TextField txtID;
        TextField txtCustomerName;
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
        cmbSearchBy = new ComboBox<>();
        cmbSearchBy.getItems().addAll(CustomerSearchType.NAME, CustomerSearchType.TEL, CustomerSearchType.ID);
        cmbSearchBy.getSelectionModel().select(CustomerSearchType.NAME);
        txtSearchContent = new TextField();
        Button btnSearch = new Button(InternationalizationUtil.getString(Constants.SEARCH));
        searchBox.getChildren().addAll(lblSearchBy, cmbSearchBy, txtSearchContent, btnSearch);
        GridPane selectedCustomerDet = new GridPane();
        selectedCustomerDet.setVgap(10);
        selectedCustomerDet.setHgap(10);
        Label lblSelectedCustomer = new Label(InternationalizationUtil.getString(Constants.SELECTED_CUSTOMER));
        selectedCustomerDet.add(lblSelectedCustomer, 0, 0);
        Label lblName = new Label();
        lblName.setText(InternationalizationUtil.getString(Constants.NAME));
        txtCustomerName = new TextField();
        txtCustomerName.setText(Constants.EMPTY_STRING);
        txtCustomerName.setEditable(false);
        selectedCustomerDet.add(lblName, 0, 1);
        selectedCustomerDet.add(txtCustomerName, 1, 1);
        Label lblId = new Label();
        lblId.setText(InternationalizationUtil.getString(Constants.ID));
        txtID = new TextField();
        txtID.setText(Constants.EMPTY_STRING);
        txtID.setEditable(false);
        selectedCustomerDet.add(lblId, 0, 2);
        selectedCustomerDet.add(txtID, 1, 2);
        Label lblTel = new Label();
        lblTel.setText(InternationalizationUtil.getString(Constants.TEL));
        txtTel = new TextField();
        txtTel.setText(Constants.EMPTY_STRING);
        selectedCustomerDet.add(lblTel, 0, 3);
        selectedCustomerDet.add(txtTel, 1, 3);
        txtTel.setEditable(false);
        customerSearchEventHandler = new CustomerSearchEventHandler(cmbSearchBy, txtSearchContent, txtCustomerName, txtID, txtTel);
        btnSearch.setOnAction(customerSearchEventHandler);
        mainSearchBox.getChildren().addAll(searchBox, selectedCustomerDet);

        existingCustomerScroll.setContent(mainSearchBox);
        existingCustomerScroll.setVisible(false);
        return existingCustomerScroll;
    }

    private ScrollPane populateNewCustomer() {
        ScrollPane newCustomerScroll = new ScrollPane();
        newCustomerScroll.setPadding(insets);
        GridPane newCustomerGrid = new GridPane();
        newCustomerGrid.setHgap(10);
        newCustomerGrid.setVgap(10);
        newCustomerScroll.setContent(newCustomerGrid);
        populateGridPane(newCustomerGrid);
        return newCustomerScroll;

    }

    private VBox populatedProducts() {
        VBox productsSection = new VBox();
        productsSection.setPadding(insets);
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
        VBox productDetBox = populateProductDetBox();
        productDetBox.setSpacing(5);
        productsSection.getChildren().addAll(productDetBox, saleReportBox);
        return productsSection;
    }

    private HBox populateSaleBox() {
        HBox saleBox = new HBox();
        saleBox.setPadding(insets);
        saleBox.setSpacing(5);
        //SaveAndPrint
        Button btnPrintInvoice = new Button();
        btnPrintInvoice.setText(InternationalizationUtil.getString(Constants.PRINT_AND_SAVE_INVOICE));
        btnPrintInvoice.setOnAction(new SaveAndPrintInvoiceButtonHandler(customerType, customerSearchEventHandler, this, data));
        //Clear Button
        Button btnClear = new Button();
        btnClear.setText(InternationalizationUtil.getString(Constants.CLEAR));
        btnClear.setOnAction(new ClearButtonHandler(customerSearchEventHandler, this));

        saleBox.getChildren().addAll(btnPrintInvoice, btnClear);
        saleBox.setAlignment(Pos.CENTER);
        return saleBox;
    }

    private HBox populateSalesMetaData() {
        HBox salesMetaData = new HBox();
        salesMetaData.setPadding(insets);
        salesMetaData.setSpacing(5);
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

    protected VBox populateCustomerPane() {
        HBox customerTypeBox = new HBox();
        VBox customerSection = new VBox();
        customerSection.setPadding(insets);
        customerSection.setSpacing(5);
        customerTypeBox.setSpacing(10);
        customerTypeBox.setMinHeight(20);
        customerTypeBox.setAlignment(Pos.CENTER_LEFT);
        RadioButton rbExistingCustomer = new RadioButton(InternationalizationUtil.getString(Constants.EXISTING_CUSTOMER));
        rbExistingCustomer.setToggleGroup(customerType);
        rbExistingCustomer.setUserData(CustomerType.EXISTING_CUSTOMER);
        RadioButton rbNewCustomer = new RadioButton(InternationalizationUtil.getString(Constants.NEW_CUSTOMER));
        rbNewCustomer.setToggleGroup(customerType);
        rbNewCustomer.setUserData(CustomerType.NEW_CUSTOMER);
        rbNewCustomer.setSelected(true);
        customerTypeBox.getChildren().addAll(rbNewCustomer, rbExistingCustomer);
        ScrollPane newCustomerScroll = populateNewCustomer();
        ScrollPane existingCustomerScroll = populateExistingCustomer();
        customerSection.getChildren().addAll(customerTypeBox, newCustomerScroll);
        customerType.selectedToggleProperty().addListener(new CustomerTypeChangeListener(customerType, newCustomerScroll,
                existingCustomerScroll, customerSection));
        newCustomerScroll.setVisible(true);
        return customerSection;
    }

    protected void populateGridPane(GridPane gridPane) {
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label lblName = new Label();
        lblName.setText(InternationalizationUtil.getString(Constants.NAME));
        gridPane.add(lblName, 0, 1);
        gridPane.add(txtCustomerName, 1, 1);

        Label lblAddress = new Label();
        lblAddress.setText(InternationalizationUtil.getString(Constants.ADDRESS));
        txtAddress.setMaxHeight(75);
        gridPane.add(lblAddress, 0, 2);
        gridPane.add(txtAddress, 1, 2);

        Label lblGSTIN = new Label();
        lblGSTIN.setText(InternationalizationUtil.getString(Constants.GSTIN));
        gridPane.add(lblGSTIN, 0, 3);
        gridPane.add(txtGSTIN, 1, 3);

        Label lblTel = new Label();
        lblTel.setText(InternationalizationUtil.getString(Constants.TEL));
        gridPane.add(lblTel, 0, 4);
        gridPane.add(txtTel, 1, 4);

        Label lblEmail = new Label();
        lblEmail.setText(InternationalizationUtil.getString(Constants.EMAIL));
        gridPane.add(lblEmail, 0, 5);
        gridPane.add(txtEmail, 1, 5);

        clearNewCustomer();
        clearSalesMetaData();
        clearData();
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
    }

    public Customer getNewCustomer() {
        Customer customer = new Customer();
        customer.setGstin(txtGSTIN.getText());
        customer.setAddress(txtAddress.getText());
        customer.setTel(txtTel.getText());
        customer.setCustomerName(txtCustomerName.getText());
        customer.setEmail(txtEmail.getText());
        return customer;
    }

    public SaleMetaData getSaleMetaData() {
        SaleMetaData saleMetaData = new SaleMetaData();
        saleMetaData.setSupplyPlace(txtSupplyPlace.getText());
        saleMetaData.setVehicle(txtVehicle.getText());
        String state = cmbState.getSelectionModel().getSelectedItem();
        saleMetaData.setState(stateService.getStateCode(state));
        saleMetaData.setTotalAmount(Float.valueOf(lblTotal.getText()));
        saleMetaData.setTransportationMode(txtTransportationMode.getText());
        return saleMetaData;
    }

    public void clearData() {
        data.clear();
    }

    @Override
    protected void populatePane(TitledPane pane) {
        VBox newSalesBox = new VBox();
        newSalesBox.setSpacing(5);
        newSalesBox.setPadding(insets);
        VBox productsSection = populatedProducts();
        HBox totalWrapper = new HBox();
        HBox salesMetaDataSection = populateSalesMetaData();
        VBox customerSection = populateCustomerPane();
        HBox saleSection = populateSaleBox();
        customerSection.setMinWidth(Constants.TITLED_WIDTH / 2 + 20);
        customerSection.setMaxWidth(Constants.TITLED_WIDTH / 2 + 20);
        customerSection.setMinHeight(Constants.TITLED_HEIGHT / 8 * 3 + 20);
        customerSection.setMaxHeight(Constants.TITLED_HEIGHT / 8 * 3);
        totalWrapper.getChildren().addAll(customerSection, lblTotal);
        //Existing customer panel would be loaded only when the user selects the radio buttons. By default is it always new customer
        newSalesBox.getChildren().addAll(totalWrapper, salesMetaDataSection, productsSection, saleSection);
        newSalesBox.setPadding(insets);
        pane.setPadding(insets);
        ScrollPane scrlPane = new ScrollPane();
        scrlPane.setPadding(insets);
        scrlPane.setContent(newSalesBox);
        newSalesBox.setMinHeight(Constants.TITLED_HEIGHT);
        scrlPane.setMinHeight(Constants.TITLED_HEIGHT);
        //scrlPane.setMinWidth(Constants.TITLED_WIDTH);
        pane.setContent(scrlPane);
        pane.setMinHeight(Constants.TITLED_HEIGHT + 100);
    }
}