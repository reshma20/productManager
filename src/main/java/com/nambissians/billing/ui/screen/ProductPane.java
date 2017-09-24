package com.nambissians.billing.ui.screen;


import com.nambissians.billing.ui.handlers.product.EditProductHandler;
import com.nambissians.billing.ui.handlers.product.NewProductHandler;
import com.nambissians.billing.ui.handlers.product.NewProductTitledPaneChangeListener;
import com.nambissians.billing.ui.handlers.product.ProductViewChangeListener;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
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



