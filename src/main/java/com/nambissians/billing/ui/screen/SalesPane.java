package com.nambissians.billing.ui.screen;


import com.nambissians.billing.ui.handlers.sales.EditSalesHandler;
import com.nambissians.billing.ui.handlers.sales.NewSalesHandler;
import com.nambissians.billing.ui.handlers.sales.NewSalesTitledHandler;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

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

public class SalesPane extends TitlePaneGenerator {

    public SalesPane(Stage stage) {
        setStage(stage);
    }

    @Override
    public TitledPane generatePanel(Accordion root) {
        TitledPane salesPane = new TitledPane();
        salesPane.setText(InternationalizationUtil.getString(Constants.SALES));
        Accordion subRoot = new Accordion();
        ScrollPane scrlPane = new ScrollPane();
        salesPane.setMinHeight(Constants.TITLED_HEIGHT);
        subRoot.setMinWidth(Constants.TITLED_WIDTH);
        scrlPane.setContent(subRoot);
        subRoot.setPadding(new Insets(0, 0, 0, 10));
        TitledPane newPane = new NewSalesHandler(InternationalizationUtil.getString(Constants.NEW)).handle();
        newPane.expandedProperty().addListener(new NewSalesTitledHandler(newPane));

        TitledPane listPane = new EditSalesHandler(InternationalizationUtil.getString(Constants.LIST)).handle();
        listPane.expandedProperty().addListener(new ListSalesTitledPane(listPane));
        subRoot.getPanes().addAll(newPane, listPane);
        salesPane.setContent(scrlPane);
        return salesPane;
    }
}

class ListSalesTitledPane extends AbstractTitledPaneChangeListener {
    public ListSalesTitledPane(TitledPane pane) {
        super(pane);
    }

    @Override
    protected void populatePane(TitledPane pane) {

    }

}