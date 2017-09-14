package com.nambissians.billing.ui.handlers.product;/**
 * Created by SajiV on 09/09/17.
 */

import com.nambissians.billing.model.Product;
import com.nambissians.billing.service.ProductServiceImpl;
import com.nambissians.billing.ui.screen.SingleProductSelectionWindow;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import com.nambissians.billing.utils.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

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
public class ProductSearchEventHandler implements EventHandler<ActionEvent> {

    private Product selectedProduct;
    private ProductServiceImpl productService = new ProductServiceImpl();
    private TextField txtSearchContent;
    private TextField txtQuantity;
    private TextField txtRebate;

    public ProductSearchEventHandler(TextField txtSearchContent, TextField txtQuantity,TextField txtRebate) {
        this.txtSearchContent = txtSearchContent;
        this.txtQuantity = txtQuantity;
        this.txtRebate = txtRebate;
    }

    @Override
    public void handle(ActionEvent event) {
        List<Product> matchingProducts = productService.searchProductsByTag(txtSearchContent.getText());
        if (matchingProducts.size() > 1) {
            //Multiple products are matched. Select one.
            SingleProductSelectionWindow productSelectionWindow = new SingleProductSelectionWindow(event);
            productSelectionWindow.selectProduct(matchingProducts);
            selectedProduct = productSelectionWindow.getSelectedProduct();
            txtSearchContent.setText(selectedProduct.getTag());
        } else if (matchingProducts.size() == 1) {
            selectedProduct = matchingProducts.get(0);
            txtSearchContent.setText(selectedProduct.getTag());
        } else {
            NotificationUtils.showWarn(InternationalizationUtil.getString(Constants.ERR_NO_MATCHING_PRODUCT));
        }
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public TextField getTxtSearchContent() {
        return txtSearchContent;
    }

    public void setTxtSearchContent(TextField txtSearchContent) {
        this.txtSearchContent = txtSearchContent;
    }

    public TextField getTxtQuantity() {
        return txtQuantity;
    }

    public void setTxtQuantity(TextField txtQuantity) {
        this.txtQuantity = txtQuantity;
    }

    public TextField getTxtRebate() {
        return txtRebate;
    }

    public void setTxtRebate(TextField txtRebate) {
        this.txtRebate = txtRebate;
    }
}

