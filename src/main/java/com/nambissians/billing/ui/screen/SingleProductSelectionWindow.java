package com.nambissians.billing.ui.screen;/**
 * Created by SajiV on 09/09/17.
 */

import com.nambissians.billing.model.Product;
import com.nambissians.billing.ui.handlers.product.ProductViewChangeListener;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
public class SingleProductSelectionWindow {
    private Product selectedProduct;
    private Event event;

    public SingleProductSelectionWindow(Event event) {
        this.event = event;
    }

    public Product selectProduct(List<Product> products) {
        Stage stage = new Stage();
        ScrollPane productPane = new ScrollPane();
        productPane.setMinWidth(Constants.TITLED_WIDTH);
        productPane.setMinHeight(Constants.TITLED_HEIGHT);

        ProductViewChangeListener productViewPopulator = new ProductViewChangeListener(null, products, false, true);
        productViewPopulator.populateScrollPane(productPane);
        stage.setScene(new Scene(productPane));
        stage.setTitle(InternationalizationUtil.getString(Constants.SELECT_PRODUCT));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());
        stage.showAndWait();
        selectedProduct = productViewPopulator.getSelectedProduct();
        return selectedProduct;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }
}
