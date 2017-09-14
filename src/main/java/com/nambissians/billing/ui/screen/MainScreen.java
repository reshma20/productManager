package com.nambissians.billing.ui.screen;

import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import com.nambissians.billing.utils.NotificationUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
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

public class MainScreen {
    private Stage stage;
    public MainScreen(Stage stage) {
        this.stage = stage;
    }

    public void init() {
        ScrollPane mainScroll = new ScrollPane();
        VBox mainBox = new VBox();
        Accordion root= new Accordion();
        TitledPane salesPane = new SalesPane(stage).generatePanel(root);
        TitledPane customerPane = new CustomerPane(stage).generatePanel(root);
        TitledPane productPane = new ProductPane(stage).generatePanel(root);
        TitledPane taxesPane = new TaxesPane(stage).generatePanel(root);
        TitledPane profilePane = new ProfilePane(stage).generatePanel(root);
        root.getPanes().addAll(salesPane,customerPane,productPane,taxesPane, profilePane);
        root.setMaxHeight(mainBox.getHeight()-20);
        mainBox.getChildren().addAll(NotificationUtils.getInstance().getLblNotificationLabel(),root);
        mainScroll.setContent(mainBox);

        Scene scene = new Scene(mainBox);
        stage.setTitle(InternationalizationUtil.getString(Constants.TITLE));
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
