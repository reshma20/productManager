package com.nambissians.billing.service;/**
 * Created by SajiV on 07/09/17.
 */

import com.nambissians.billing.dao.CustomerDaoImpl;
import com.nambissians.billing.model.Customer;
import com.nambissians.billing.model.CustomerSearchType;
import com.nambissians.billing.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class CustomerServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    private CustomerDaoImpl customerDao = new CustomerDaoImpl();

    public boolean persist(Customer cust) {
        try {
            boolean returnVal = true;
            //Assumption : There is only one profile at a time.
            Connection connection = DBConnectionUtils.getConnection();
            int count = customerDao.createCustomer(connection, cust);

            if (count == 1) {
                NotificationUtils.showMessage(InternationalizationUtil.getString(Constants.MSG_SAVED_CUSTOMER));
                return DBConnectionUtils.commitTransaction(connection);
            } else {
                return DBConnectionUtils.rollbackTransaction(connection);
            }
        } catch (SQLException exp) {
            logger.error("Could not save Customer {}", cust, exp);
            NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_COULD_NOT_SAVE_CUSTOMER));
        }
        return false;
    }

    public List<Customer> getCustomers() {
        try {
            Connection connection = DBConnectionUtils.getConnection();
            connection.setReadOnly(true);
            List<Customer> taxes = customerDao.getCustomers(connection);
            DBConnectionUtils.commitTransaction(connection);
            return taxes;
        } catch (Exception exp) {
            logger.error("Couldn't get owner profile", exp);
            return new ArrayList<>();
        }
    }

    public List<Customer> searchCustomers(CustomerSearchType searchType, String searchString) {
        try {
            List<Customer> customers = new ArrayList<>();
            boolean notified = false;
            Connection connection = DBConnectionUtils.getConnection();
            connection.setReadOnly(true);
            switch (searchType) {
                case ID:
                    if(StringUtils.isLongVal(searchString)) {
                        customers = customerDao.getCustomerByID(connection, Long.valueOf(searchString));
                    } else {
                        notified=true;
                        NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_NON_NUMERIC_ID));
                    }
                    break;
                case NAME:
                    customers = customerDao.getCustomerByName(connection, searchString);
                    break;
                case TEL:
                    customers = customerDao.getCustomerByTel(connection, searchString);
                    break;
                default:
                    notified = true;
                    NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_SEACH_MECHANISM_NOT_IMPLEMENTED));
            }
            DBConnectionUtils.commitTransaction(connection);
            if(customers.isEmpty() && (notified == false)) {
                NotificationUtils.showWarn(InternationalizationUtil.getString(Constants.ERR_NO_MATCHING_CUSTOMER));
            }
            return customers;
        } catch (Exception exp) {
            logger.error("Couldn't get owner profile", exp);
            NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_COULD_NOT_GET_CUSTOMER_LIST));
            return new ArrayList<>();
        }
    }
}
