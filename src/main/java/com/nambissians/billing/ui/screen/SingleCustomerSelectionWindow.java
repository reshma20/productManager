package com.nambissians.billing.ui.screen;/**
 * Created by SajiV on 09/09/17.
 */

import com.nambissians.billing.model.Customer;
import com.nambissians.billing.ui.handlers.customer.ViewCustomerTitledChangeListener;
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
public class SingleCustomerSelectionWindow {
    private Customer customer;
    private Event event;

    public SingleCustomerSelectionWindow(Event event) {
        this.event = event;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer selectCustomer(List<Customer> customers) {
        Stage stage = new Stage();
        ScrollPane customerPane = new ScrollPane();
        customerPane.setMinWidth(Constants.TITLED_WIDTH);
        customerPane.setMinHeight(Constants.TITLED_HEIGHT);

        ViewCustomerTitledChangeListener customerListPopulater = new ViewCustomerTitledChangeListener(null, customers, true, false);
        customerListPopulater.populateScrollPane(customerPane);
        stage.setScene(new Scene(customerPane));
        stage.setTitle(InternationalizationUtil.getString(Constants.SELECT_CUSTOMER));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(
                ((Node) event.getSource()).getScene().getWindow());
        stage.showAndWait();
        customer = customerListPopulater.getSelectedCustomer();
        return  customerListPopulater.getSelectedCustomer();
    }
}
