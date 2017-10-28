package com.nambissians.billing.ui.handlers.customer;/**
 * Created by SajiV on 09/09/17.
 */

import com.nambissians.billing.model.Customer;
import com.nambissians.billing.service.CustomerServiceImpl;
import com.nambissians.billing.ui.screen.AbstractTitledPaneChangeListener;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.GridUtils;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.security.Timestamp;
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
public class ViewCustomerTitledChangeListener extends AbstractTitledPaneChangeListener {
    private List<Customer> customers;
    private Customer selectedCustomer;
    private boolean selectCustomer;
    private boolean alwaysRefresh;
    private boolean loadContextMenu;
    private CustomerServiceImpl customerService = new CustomerServiceImpl();


    public ViewCustomerTitledChangeListener(TitledPane pane, List<Customer> customers, boolean selectCustomer, boolean loadFromDB, boolean loadContextMenu) {
        super(pane);
        this.customers = customers;
        this.selectCustomer = selectCustomer;
        this.alwaysRefresh = loadFromDB;
        this.loadContextMenu = loadContextMenu;
    }

    public TableView populateTableView() {
        if (alwaysRefresh) {
            customers = customerService.getCustomers();
        }
        TableView table = new TableView();
        table.setMinHeight(Constants.TITLED_HEIGHT);
        table.setMinWidth(Constants.TITLED_WIDTH);

        TableColumn id = new TableColumn(InternationalizationUtil.getString(Constants.ID));
        id.setCellValueFactory(new PropertyValueFactory<Customer, Long>(Constants.ID));
        id.prefWidthProperty().bind(table.widthProperty().divide(25));

        TableColumn customerName = new TableColumn(InternationalizationUtil.getString(Constants.NAME));
        customerName.setCellValueFactory(new PropertyValueFactory<Customer, String>(Constants.NAME));
        customerName.prefWidthProperty().bind(table.widthProperty().divide(25).multiply(3));

        TableColumn address = new TableColumn(InternationalizationUtil.getString(Constants.ADDRESS));
        address.setCellValueFactory(new PropertyValueFactory<Customer, String>(Constants.ADDRESS));
        address.prefWidthProperty().bind(table.widthProperty().divide(10).multiply(3));

        TableColumn gstIN = new TableColumn(InternationalizationUtil.getString(Constants.GSTIN));
        gstIN.setCellValueFactory(new PropertyValueFactory<Customer, String>(Constants.GSTIN));
        gstIN.prefWidthProperty().bind(table.widthProperty().divide(5));

        TableColumn email = new TableColumn(InternationalizationUtil.getString(Constants.EMAIL));
        email.setCellValueFactory(new PropertyValueFactory<Customer, String>(Constants.EMAIL));
        email.prefWidthProperty().bind(table.widthProperty().divide(25).multiply(4));

        TableColumn createDate = new TableColumn(InternationalizationUtil.getString(Constants.CREATE_DATE));
        createDate.setCellValueFactory(new PropertyValueFactory<Customer, Timestamp>(Constants.CREATE_DATE));
        createDate.prefWidthProperty().bind(table.widthProperty().divide(50).multiply(9));


        final ObservableList<Customer> data = GridUtils.convertToObservableList(customers);
        table.setItems(data);
        table.getColumns().addAll(id, customerName, address, gstIN, email, createDate);

        if (selectCustomer) {
            table.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                        Node node = ((Node) event.getTarget()).getParent();
                        if (node instanceof TableRow) {
                            TableRow row = (TableRow) node;
                            selectedCustomer = (Customer) row.getItem();
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.close();
                        }
                    }
                }

            });
        }
        if (loadContextMenu) {
            table.setRowFactory(new Callback<TableView<Customer>, TableRow<Customer>>() {
                @Override
                public TableRow<Customer> call(TableView<Customer> param) {
                    final TableRow<Customer> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem removeItem = new MenuItem(InternationalizationUtil.getString(Constants.DELETE));
                    removeItem.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle(InternationalizationUtil.getString(Constants.DELETE_CUSTOMER));
                            alert.setHeaderText(InternationalizationUtil.getString(Constants.DELETE_CUSTOMER));
                            alert.initModality(Modality.APPLICATION_MODAL);
                            alert.setContentText(InternationalizationUtil.getString(Constants.DELETE_CUSTOMER_CAPTION));
                            alert.showAndWait();
                            if (alert.getResult().equals(ButtonType.OK)) {
                                Customer customer = row.getItem();
                                if (customerService.deleteCustomer(customer.getId())) {
                                    table.getItems().remove(row.getItem());
                                }
                            }
                        }
                    });
                    rowMenu.getItems().add(removeItem);
                    row.contextMenuProperty().bind(
                            Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                    .then(rowMenu)
                                    .otherwise((ContextMenu) null));
                    return row;
                }
            });
        }

        return table;
    }

    @Override
    protected void populatePane(TitledPane pane) {
        ScrollPane scrlPane = new ScrollPane();
        scrlPane.setPadding(new Insets(10, 10, 10, 10));
        scrlPane.setContent(populateTableView());
        scrlPane.setMinHeight(Constants.TITLED_HEIGHT);
        pane.setContent(scrlPane);
    }

    public void populateScrollPane(ScrollPane pane) {
        pane.setContent(populateTableView());
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
}
