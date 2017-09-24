package com.nambissians.billing.ui.handlers.taxes;/**
 * Created by SajiV on 20/09/17.
 */

import com.nambissians.billing.model.Tax;
import com.nambissians.billing.model.TaxHead;
import com.nambissians.billing.service.TaxServiceImpl;
import com.nambissians.billing.ui.screen.AbstractTitledPaneChangeListener;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.GridUtils;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Timestamp;

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
public class TaxesPaneViewChangeListener extends AbstractTitledPaneChangeListener {
    private TaxServiceImpl taxService = new TaxServiceImpl();

    protected void populatePane(TitledPane pane) {
        TableView table = new TableView();

        TableColumn id = new TableColumn(InternationalizationUtil.getString(Constants.ID));
        id.setCellValueFactory(new PropertyValueFactory<Tax, Long>(Constants.ID));
        id.prefWidthProperty().bind(table.widthProperty().divide(8));

        TableColumn tag = new TableColumn(InternationalizationUtil.getString(Constants.TAG));
        tag.setCellValueFactory(new PropertyValueFactory<Tax, String>(Constants.TAG));
        tag.prefWidthProperty().bind(table.widthProperty().divide(8));

        TableColumn description = new TableColumn(InternationalizationUtil.getString(Constants.DESCRIPTION));
        description.setCellValueFactory(new PropertyValueFactory<Tax, String>(Constants.DESCRIPTION));
        description.prefWidthProperty().bind(table.widthProperty().divide(8).multiply(3));

        TableColumn percentage = new TableColumn(InternationalizationUtil.getString(Constants.PERCENTAGE));
        percentage.setCellValueFactory(new PropertyValueFactory<Tax, Float>(Constants.PERCENTAGE));
        percentage.prefWidthProperty().bind(table.widthProperty().divide(8));

        TableColumn createDate = new TableColumn(InternationalizationUtil.getString(Constants.CREATE_DATE));
        createDate.setCellValueFactory(new PropertyValueFactory<Tax, Timestamp>(Constants.CREATE_DATE));
        createDate.prefWidthProperty().bind(table.widthProperty().divide(8));

        TableColumn taxHead = new TableColumn(InternationalizationUtil.getString(Constants.TAX_HEAD));
        taxHead.setCellValueFactory(new PropertyValueFactory<Tax, TaxHead>(Constants.TAX_HEAD));
        taxHead.prefWidthProperty().bind(table.widthProperty().divide(8));


        final ObservableList<Tax> data = GridUtils.convertToObservableList(taxService.getTaxes());
        table.setItems(data);
        table.getColumns().addAll(id, tag, description, percentage, taxHead, createDate);
        pane.setContent(table);
        pane.setMinHeight(Constants.TITLED_HEIGHT / 2);
        pane.setMinWidth(Constants.TITLED_WIDTH);
    }

    public TaxesPaneViewChangeListener(TitledPane pane) {
        super(pane);
    }
}