package com.nambissians.billing.ui.handlers.customer;

import com.nambissians.billing.model.Customer;
import com.nambissians.billing.model.Tax;
import com.nambissians.billing.service.CustomerServiceImpl;
import com.nambissians.billing.service.TaxServiceImpl;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

public class SaveCustomerButtonHandler implements EventHandler<ActionEvent> {
    private static final Logger logger = LoggerFactory.getLogger(SaveCustomerButtonHandler.class);
    private TextField txtCustomerName;
    private TextArea txtAddress;
    private TextField txtGSTIN;
    private TextField txtTel;
    private TextField txtEmail;
    private CustomerServiceImpl customerService = new CustomerServiceImpl();

    public SaveCustomerButtonHandler(TextField txtCustomerName, TextArea txtAddress, TextField txtGSTIN, TextField txtTel, TextField txtEmail) {
        this.txtCustomerName = txtCustomerName;
        this.txtAddress = txtAddress;
        this.txtGSTIN = txtGSTIN;
        this.txtTel=txtTel;
        this.txtEmail=txtEmail;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            Customer cust = new Customer();
            cust.setEmail(txtEmail.getText());
            cust.setCustomerName(txtCustomerName.getText());
            cust.setTel(txtTel.getText());
            cust.setAddress(txtAddress.getText());
            cust.setGstin(txtGSTIN.getText());
            customerService.persist(cust);
        } catch (Exception exp) {
            logger.error("Couldn't save tax", exp);
        }
    }
}
