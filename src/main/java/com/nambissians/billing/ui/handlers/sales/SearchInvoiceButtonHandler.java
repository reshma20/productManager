package com.nambissians.billing.ui.handlers.sales;/**
 * Created by SajiV on 15/09/17.
 */

import com.nambissians.billing.model.SaleMetaData;
import com.nambissians.billing.service.SaleReportServiceImpl;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.GridUtils;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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
public class SearchInvoiceButtonHandler implements EventHandler<ActionEvent> {

    private ObservableList<SaleMetaData> data;
    private LocalDate fromDate;
    private LocalDate toDate;
    private ScrollPane scrollPane = new ScrollPane();
    private SaleReportServiceImpl saleReportService = new SaleReportServiceImpl();

    public SearchInvoiceButtonHandler(VBox mainBox, DatePicker fromDatePicker, DatePicker toDatePicker) {
        scrollPane.setMinHeight(Constants.TITLED_HEIGHT / 2);
        scrollPane.setMinWidth(Constants.TITLED_WIDTH);
        mainBox.getChildren().add(scrollPane);
        fromDatePicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                fromDate = fromDatePicker.getValue();
            }
        });
        toDatePicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                toDate = toDatePicker.getValue();
            }
        });
    }

    private TableView populateSaleMetaData() {
        TableView table = new TableView();
        table.setMinHeight(Constants.TITLED_HEIGHT);
        table.setMinWidth(Constants.TITLED_WIDTH);

        TableColumn id = new TableColumn(InternationalizationUtil.getString(Constants.ID));
        id.setCellValueFactory(new PropertyValueFactory<SaleMetaData, Long>(Constants.ID));
        id.prefWidthProperty().bind(table.widthProperty().divide(20));
        TableColumn transactionDate = new TableColumn(InternationalizationUtil.getString(Constants.TRANSACTION_DATE));
        transactionDate.setCellValueFactory(new PropertyValueFactory<SaleMetaData, String>(Constants.TIMESTAMP));
        transactionDate.prefWidthProperty().bind(table.widthProperty().divide(8));
        TableColumn customerName = new TableColumn(InternationalizationUtil.getString(Constants.CUSTOMER));
        customerName.setCellValueFactory(new PropertyValueFactory<SaleMetaData, String>(Constants.NAME));
        customerName.prefWidthProperty().bind(table.widthProperty().divide(10).multiply(3));
        TableColumn totalTaxes = new TableColumn(InternationalizationUtil.getString(Constants.TOTAL_TAX));
        totalTaxes.setCellValueFactory(new PropertyValueFactory<SaleMetaData, String>(Constants.TOTAL_TAX));
        totalTaxes.prefWidthProperty().bind(table.widthProperty().divide(20));
        TableColumn totalAmount = new TableColumn(InternationalizationUtil.getString(Constants.TOTAL_AMOUNT));
        totalAmount.setCellValueFactory(new PropertyValueFactory<SaleMetaData, String>(Constants.TOTAL_AMOUNT));
        totalAmount.prefWidthProperty().bind(table.widthProperty().divide(20));
        table.setItems(data);
        table.getColumns().addAll(id, transactionDate, customerName, totalTaxes, totalAmount);
//            if (enableRowClick) {
//                table.setOnMousePressed(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
//                            Node node = ((Node) event.getTarget()).getParent();
//                            if (node instanceof TableRow ) {
//                                TableRow row = (TableRow) node;
//                                selectedProduct = (Product) row.getItem();
//                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                                stage.close();
//                            }
//                        }
//                    }
//
//                });
//            }
        return table;
    }

    @Override
    public void handle(ActionEvent event) {
        ZonedDateTime fromDateZDT = fromDate.atStartOfDay(ZoneId.of("Asia/Kolkata"));
        ZonedDateTime toDateZDT = toDate.atStartOfDay(ZoneId.of("Asia/Kolkata"));
        data = GridUtils.convertToObservableList(
                saleReportService.getSales(new Timestamp(fromDateZDT.toInstant().toEpochMilli()),
                        new Timestamp(toDateZDT.toInstant().toEpochMilli())));
        scrollPane.setContent(populateSaleMetaData());
    }

    public ObservableList<SaleMetaData> getData() {
        return data;
    }

    public void setData(ObservableList<SaleMetaData> data) {
        this.data = data;
    }
}