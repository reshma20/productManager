package com.nambissians.billing.ui.handlers.sales;/**
 * Created by SajiV on 12/09/17.
 */

import com.nambissians.billing.model.CustomerType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

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
public class CustomerTypeChangeListener implements ChangeListener<Toggle> {
    private ToggleGroup toggleGroup;
    private ScrollPane newCustomerScroll;
    private ScrollPane existingCustomerScroll;
    private VBox customerSection;

    public CustomerTypeChangeListener(ToggleGroup toggleGroup, ScrollPane newCustomerScroll, ScrollPane existingCustomerScroll,
                                      VBox customerSection) {
        this.toggleGroup = toggleGroup;
        this.newCustomerScroll = newCustomerScroll;
        this.existingCustomerScroll = existingCustomerScroll;
        this.customerSection = customerSection;
    }

    @Override
    public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
        if (toggleGroup.getSelectedToggle() != null) {
            CustomerType cType = (CustomerType) toggleGroup.getSelectedToggle().getUserData();
            customerSection.getChildren().removeAll(existingCustomerScroll, newCustomerScroll);
            //newSalesBox.getChildren().removeAll(newCustomerScroll, existingCustomerScroll,saleSection);
            switch (cType) {
                case EXISTING_CUSTOMER:
                    customerSection.getChildren().addAll(existingCustomerScroll);
                    newCustomerScroll.setVisible(false);
                    existingCustomerScroll.setVisible(true);
                    break;
                case NEW_CUSTOMER:
                default:
                    customerSection.getChildren().addAll(newCustomerScroll);
                    existingCustomerScroll.setVisible(false);
                    newCustomerScroll.setVisible(true);
                    break;
            }
        }
    }
}
