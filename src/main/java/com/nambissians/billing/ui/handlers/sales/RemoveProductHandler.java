package com.nambissians.billing.ui.handlers.sales;/**
 * Created by SajiV on 12/09/17.
 */

import com.nambissians.billing.model.SaleReport;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.MathUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class RemoveProductHandler implements EventHandler<ActionEvent> {
    private Label lblTotal;
    private ObservableList<SaleReport> data;
    private TableView<SaleReport> records;
    private static final Logger logger = LoggerFactory.getLogger(RemoveProductHandler.class);

    public RemoveProductHandler(TableView<SaleReport> records, ObservableList<SaleReport> data, Label lblTotal) {
        this.data = data;
        this.lblTotal = lblTotal;
        this.records = records;
    }

    @Override
    public void handle(ActionEvent event) {
        //Remove the selected product from data
        SaleReport sr = records.getSelectionModel().getSelectedItem();
        if (sr != null) {
            data.remove(sr);
            float total = Float.parseFloat(lblTotal.getText());
            total -= sr.getFinalAmount();
            //Update lblTotal content with the new one
            lblTotal.setText(MathUtils.roundoffToTwoPlaces(total) + Constants.EMPTY_STRING);
        } else {
            logger.debug("No records selected. Hence skipping removal");
        }
    }
}
