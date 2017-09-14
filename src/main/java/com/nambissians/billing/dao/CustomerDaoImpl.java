package com.nambissians.billing.dao;

import com.nambissians.billing.model.Customer;
import com.nambissians.billing.utils.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
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
public class CustomerDaoImpl {

    private Customer populateCustomer(ResultSet rs) throws SQLException{
        Customer cust = new Customer();
        cust.setId(rs.getLong(Constants.ID));
        cust.setTel(rs.getString(Constants.TEL));
        cust.setAddress(rs.getString(Constants.ADDRESS));
        cust.setCustomerName(rs.getString(Constants.NAME_FIELD));
        cust.setEmail(rs.getString(Constants.EMAIL));
        cust.setCreateDate(rs.getTimestamp(Constants.CREATE_DATE));
        cust.setGstin(rs.getString(Constants.GSTIN));
        return cust;
    }

    private static final String INSERT_CUSTOMER = "INSERT INTO customer (name, address, gstin, tel, email, createDate) VALUES (UPPER(?),UPPER(?),UPPER(?),UPPER(?),LOWER(?),?)";

    public int createCustomer(Connection connection, Customer cust) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(INSERT_CUSTOMER);
        pstmt.setString(1, cust.getCustomerName());
        pstmt.setString(2, cust.getAddress());
        pstmt.setString(3, cust.getGstin());
        pstmt.setString(4, cust.getTel());
        pstmt.setString(5, cust.getEmail());
        Calendar cal = Calendar.getInstance();
        Timestamp currentTimeStamp = new Timestamp(cal.getTimeInMillis());
        pstmt.setTimestamp(6,currentTimeStamp);
        int result = pstmt.executeUpdate();
        pstmt.close();
        return result;
    }

    private static final String GET_CUSTOMERS = "SELECT id, name, address, gstin, tel, email, createDate FROM customer";

    public List<Customer> getCustomers(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(GET_CUSTOMERS);
        ResultSet rs = pstmt.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            Customer cust = populateCustomer(rs);
            customers.add(cust);
        }
        pstmt.close();
        return customers;
    }

    private static final String GET_CUSTOMERS_BY_TEL = "SELECT id, name, address, gstin, tel, email, createDate FROM customer WHERE tel LIKE UPPER(?)";
    public List<Customer> getCustomerByTel(Connection connection, String tel) throws SQLException{
        String tempTel = Constants.WILD_CARD_FOR_ANY+tel+Constants.WILD_CARD_FOR_ANY;
        PreparedStatement pstmt = connection.prepareStatement(GET_CUSTOMERS_BY_TEL);
        pstmt.setString(1,tempTel);
        ResultSet rs = pstmt.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            Customer cust = populateCustomer(rs);
            customers.add(cust);
        }
        pstmt.close();
        return customers;
    }

    private static final String GET_CUSTOMERS_BY_NAME = "SELECT id, name, address, gstin, tel, email, createDate FROM customer WHERE name LIKE UPPER(?)";
    public List<Customer> getCustomerByName(Connection connection, String name) throws SQLException{
        String tempName = Constants.WILD_CARD_FOR_ANY+name+Constants.WILD_CARD_FOR_ANY;
        PreparedStatement pstmt = connection.prepareStatement(GET_CUSTOMERS_BY_NAME);
        pstmt.setString(1,tempName);
        ResultSet rs = pstmt.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            Customer cust = populateCustomer(rs);
            customers.add(cust);
        }
        pstmt.close();
        return customers;
    }

    private static final String GET_CUSTOMERS_BY_ID = "SELECT id, name, address, gstin, tel, email, createDate FROM customer WHERE id =?";
    public List<Customer> getCustomerByID(Connection connection, Long id) throws SQLException{
        PreparedStatement pstmt = connection.prepareStatement(GET_CUSTOMERS_BY_ID);
        pstmt.setLong(1,id);
        ResultSet rs = pstmt.executeQuery();
        List<Customer> customers = new ArrayList<>();
        while (rs.next()) {
            Customer cust = populateCustomer(rs);
            customers.add(cust);
        }
        pstmt.close();
        return customers;
    }
}
