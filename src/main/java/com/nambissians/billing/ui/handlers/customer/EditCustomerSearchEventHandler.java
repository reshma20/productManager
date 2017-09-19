package com.nambissians.billing.ui.handlers.customer;/**
 * Created by SajiV on 09/09/17.
 */

import com.nambissians.billing.model.Customer;
import com.nambissians.billing.model.CustomerSearchType;
import com.nambissians.billing.service.CustomerServiceImpl;
import com.nambissians.billing.ui.screen.CustomerSearchEventHandler;
import com.nambissians.billing.ui.screen.SingleCustomerSelectionWindow;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import com.nambissians.billing.utils.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
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
public class EditCustomerSearchEventHandler extends CustomerSearchEventHandler {
    private Customer selectedCustomer;
    private CustomerServiceImpl customerService = new CustomerServiceImpl();
    private ComboBox<CustomerSearchType> cmbSearchBy;
    private TextField txtSearchContent;
    private TextField txtCustomerName;
    private TextField txtID;
    private TextField txtTel;
    private TextField txtGSTIN;
    private TextArea txtAddress;
    private TextField txtEmail;

    public EditCustomerSearchEventHandler(ComboBox<CustomerSearchType> cmbSearchBy, TextField txtSearchContent, TextField txtCustomerName,
                                          TextField txtID, TextField txtTel, TextArea txtAddress, TextField txtGSTIN, TextField txtEmail) {
        super(cmbSearchBy, txtSearchContent, txtCustomerName, txtID, txtTel);
        this.cmbSearchBy = cmbSearchBy;
        this.txtSearchContent = txtSearchContent;
        this.txtCustomerName = txtCustomerName;
        this.txtID = txtID;
        this.txtTel = txtTel;
        this.txtAddress = txtAddress;
        this.txtGSTIN = txtGSTIN;
        this.txtEmail = txtEmail;
    }

    @Override
    public void handle(ActionEvent event) {
        List<Customer> matchingCustomers = customerService.searchCustomers(cmbSearchBy.getSelectionModel().getSelectedItem(),
                txtSearchContent.getText());
        if (matchingCustomers.size() > 1) {
            //Multiple customers are matched. Select one.
            SingleCustomerSelectionWindow customerSelectionWindow = new SingleCustomerSelectionWindow(event);
            customerSelectionWindow.selectCustomer(matchingCustomers);
            selectedCustomer = customerSelectionWindow.getCustomer();
            txtCustomerName.setText(selectedCustomer.getCustomerName());
            txtID.setText(selectedCustomer.getId() + Constants.EMPTY_STRING);
            txtTel.setText(selectedCustomer.getTel());
            txtAddress.setText(selectedCustomer.getAddress());
            txtGSTIN.setText(selectedCustomer.getGstin());
            txtEmail.setText(selectedCustomer.getEmail());
        } else if (matchingCustomers.size() == 1) {
            selectedCustomer = matchingCustomers.get(0);
            txtCustomerName.setText(selectedCustomer.getCustomerName());
            txtID.setText(selectedCustomer.getId() + Constants.EMPTY_STRING);
            txtTel.setText(selectedCustomer.getTel());
            txtAddress.setText(selectedCustomer.getAddress());
            txtGSTIN.setText(selectedCustomer.getGstin());
            txtEmail.setText(selectedCustomer.getEmail());
        } else {
            NotificationUtils.showWarn(InternationalizationUtil.getString(Constants.ERR_NO_MATCHING_CUSTOMER));
        }
    }

    public void clearExistingCustomer() {
        txtSearchContent.setText(Constants.EMPTY_STRING);
        txtCustomerName.setText(Constants.EMPTY_STRING);
        txtID.setText(Constants.EMPTY_STRING);
        txtTel.setText(Constants.EMPTY_STRING);
        selectedCustomer = new Customer();
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
}
