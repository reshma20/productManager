package com.nambissians.billing.ui.handlers.sales;/**
 * Created by SajiV on 15/09/17.
 */

import com.nambissians.billing.model.SaleMetaData;
import com.nambissians.billing.service.SaleReportServiceImpl;
import com.nambissians.billing.ui.screen.AbstractTitledPaneChangeListener;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.GridUtils;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
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
public class ListSalesTitledPane extends AbstractTitledPaneChangeListener {
    public ListSalesTitledPane(TitledPane pane) {
        super(pane);
    }

    private VBox mainBox = new VBox();
    private DatePicker fromDatePicker=new DatePicker();
    private DatePicker toDatePicker = new DatePicker();


    private HBox populateSearchBox() {
        HBox searchBox = new HBox();
        searchBox.setSpacing(5);
        searchBox.setPadding(new Insets(10, 10, 10, 10));
        Label lblFromDate = new Label(InternationalizationUtil.getString(Constants.FROM_DATE));
        searchBox.setAlignment(Pos.CENTER);
        Label lblToDate = new Label(InternationalizationUtil.getString(Constants.TO_DATE));
        Button btnSearch = new Button();
        Button export = new Button();
        btnSearch.setText(InternationalizationUtil.getString(Constants.SEARCH));
        mainBox.getChildren().add(searchBox);
        SearchInvoiceButtonHandler handler = new SearchInvoiceButtonHandler(mainBox,fromDatePicker,toDatePicker);
        btnSearch.setOnAction(handler);
        btnSearch.setDefaultButton(true);
        export.setText(InternationalizationUtil.getString(Constants.Export));
        export.setOnAction(new ExportSaleListener(handler));
        searchBox.getChildren().addAll(lblFromDate, fromDatePicker, lblToDate, toDatePicker, btnSearch, export);
        return searchBox;
    }

    @Override
    protected void populatePane(TitledPane pane) {
        ScrollPane scrlPane = new ScrollPane();
        pane.setAlignment(Pos.CENTER);
        scrlPane.setMinHeight(Constants.TITLED_HEIGHT);
        scrlPane.setMinWidth(Constants.TITLED_WIDTH);
        populateSearchBox();
        scrlPane.setContent(mainBox);
        pane.setContent(scrlPane);

    }

}