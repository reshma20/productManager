package com.nambissians.billing.ui.screen;


import com.nambissians.billing.ui.handlers.customer.ViewCustomerTitledChangeListener;
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

public class CustomerPane extends TitlePaneGenerator {

    public CustomerPane(Stage stage) {
        setStage(stage);
    }

    @Override
    public TitledPane generatePanel(Accordion root) {
        TitledPane customerPane = new TitledPane();
        customerPane.setText(InternationalizationUtil.getString(Constants.CUSTOMER));
        Accordion subRoot = new Accordion();
        subRoot.setPadding(new Insets(0, 0, 0, 10));
        TitledPane newPane = new TitledPane();
        newPane.setText(InternationalizationUtil.getString(Constants.NEW));
        newPane.expandedProperty().addListener(new NewCustomerTitledChangeListener(newPane));
        TitledPane listPane = new TitledPane();
        listPane.setText(InternationalizationUtil.getString(Constants.LIST));
        listPane.setMinHeight(700);
        listPane.expandedProperty().addListener(new ViewCustomerTitledChangeListener(listPane, null, false, true));
        subRoot.getPanes().addAll(newPane, listPane);
        customerPane.setContent(subRoot);
        return customerPane;
    }
}

