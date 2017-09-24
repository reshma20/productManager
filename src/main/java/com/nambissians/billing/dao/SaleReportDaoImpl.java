package com.nambissians.billing.dao;/**
 * Created by SajiV on 10/09/17.
 */

import com.nambissians.billing.model.*;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

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
public class SaleReportDaoImpl {

    private static final Logger logger = LoggerFactory.getLogger(SaleReportDaoImpl.class);

    private static final String SAVE_INVOICE_RECORD = "INSERT INTO SALES_RECORD (transaction_date, amount, taxes, state, " +
            "vehicle, customer_name, customer_id, customer_address, customer_gst_in, customer_tel, customer_email, " +
            "supply_place, udid, transportation_mode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?, ?)";

    private static final String SAVE_BILL_RECORD = "INSERT INTO SALES_BILL (transaction_date, amount, taxes, state, " +
            "vehicle, customer_name, customer_id, customer_address, customer_gst_in, customer_tel, customer_email, " +
            "supply_place, udid, transportation_mode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?, ?)";

    private static final String SAVE_INVOICE_BREAKUP = "INSERT INTO INVOICE_BREAKUP(udid,product_id,quantity,rate,amount," +
            "rebate,cgst,sgst,final_amount) VALUES(?,?,?,?,?,?,?,?,?)";

    private String GET_SALES_BILL_META_DATA = "SELECT id, transaction_date, amount, taxes, transportation_mode, state, vehicle, " +
            "customer_name, customer_address, customer_gst_in, customer_tel, customer_email, supply_place, udid, customer_id, 1 gst_bill " +
            "FROM SALES_BILL WHERE udid = ?";

    private String GET_SALES_META_DATA = "SELECT id, transaction_date, amount, taxes, transportation_mode, state, vehicle, " +
            "customer_name, customer_address, customer_gst_in, customer_tel, customer_email, supply_place, udid, customer_id, 0 gst_bill " +
            "FROM sales_record WHERE udid = ?";
    private String TRANSACTION_DATE = "transaction_date";
    private String TAXES = "taxes";
    private String AMOUNT = "amount";
    private String VEHICLE = "vehicle";
    private String STATE = "state";
    private String SUPPLY_PLACE = "supply_place";
    private String TRANSPORTATION_MODE = "transportation_mode";
    private String CUSTOMER_EMAIL = "customer_email";
    private String CUSTOMER_TEL = "customer_tel";
    private String CUSTOMER_NAME = "customer_name";
    private String CUSTOMER_ADDRESS = "customer_address";
    private String CUSTOMER_GST_IN = "customer_gst_in";
    private String CUSTOMER_ID = "customer_id";
    private String UDID = "udid";
    private String GET_INVOICE_SPLIT_UP = "SELECT udid, product_id, quantity, rate, amount, rebate,  cgst, sgst, " +
            "final_amount FROM invoice_breakup WHERE udid = ?";
    private String PRICE = "rate";
    private String FINAL_AMOUNT = "final_amount";
    private String CGST = "cgst";
    private String SGST = "sgst";
    private String QUANTITY = "quantity";
    private String REBATE = "rebate";
    private String PRODUCT_ID = "product_id";
    private String GST_BILL = "gst_bill";

    private SaleReport fetchSaleDetail(ResultSet rs, Map<Long, String> products) throws SQLException {
        SaleReport sr = new SaleReport();
        sr.setAmount(rs.getFloat(AMOUNT));
        sr.setPrice(rs.getFloat(PRICE));
        sr.setFinalAmount(rs.getFloat(FINAL_AMOUNT));
        sr.setSgst(rs.getFloat(SGST));
        sr.setCgst(rs.getFloat(CGST));
        sr.setBillUUID(rs.getString(UDID));
        sr.setQuantity(rs.getLong(QUANTITY));
        sr.setRebate(rs.getFloat(REBATE));
        sr.setProductId(rs.getLong(PRODUCT_ID));
        sr.setProductName(products.get(sr.getProductId()));
        float taxableAmount = sr.getAmount() * (1 - sr.getRebate() / 100);
        sr.setTaxableAmount(MathUtils.roundoffToTwoPlaces(taxableAmount));
        return sr;
    }

    private SaleMetaData fetchSaleMetaData(ResultSet rs) throws SQLException {
        SaleMetaData saleMetaData = new SaleMetaData();
        saleMetaData.setTimestamp(rs.getTimestamp(TRANSACTION_DATE));
        saleMetaData.setTotalTaxes(rs.getFloat(TAXES));
        saleMetaData.setTotalAmount(rs.getFloat(AMOUNT));
        saleMetaData.setVehicle(rs.getString(VEHICLE));
        saleMetaData.setState(rs.getLong(STATE));
        saleMetaData.setSupplyPlace(rs.getString(SUPPLY_PLACE));
        saleMetaData.setTransportationMode(rs.getString(TRANSPORTATION_MODE));
        saleMetaData.setUuid(rs.getString(UDID));
        saleMetaData.setId(rs.getLong(Constants.ID));
        saleMetaData.setCustomerName(rs.getString(CUSTOMER_NAME));
        if (rs.getInt(GST_BILL) == 1) {
            saleMetaData.setGstBill(true);
            saleMetaData.setPrintableInvoiceNumber(String.format(Constants.PRINTABLE_INVOICE_FORMAT,
                    Constants.GST_BILL_INITIAL, saleMetaData.getId()));
        } else {
            saleMetaData.setGstBill(false);
            saleMetaData.setPrintableInvoiceNumber(String.format(Constants.PRINTABLE_INVOICE_FORMAT,
                    Constants.GST_INVOICE_INITIAL, saleMetaData.getId()));
        }
        return saleMetaData;
    }

    private Customer fetchCustomerInBill(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setEmail(rs.getString(CUSTOMER_EMAIL));
        customer.setTel(rs.getString(CUSTOMER_TEL));
        customer.setCustomerName(rs.getString(CUSTOMER_NAME));
        customer.setAddress(rs.getString(CUSTOMER_ADDRESS));
        customer.setGstin(rs.getString(CUSTOMER_GST_IN));
        customer.setId(rs.getLong(CUSTOMER_ID));
        return customer;
    }

    private float saveInvoiceBreakup(Connection connection, SaleRecord salesRecord) throws SQLException {
        float totalTaxes = 0;
        PreparedStatement pstmtSr = connection.prepareStatement(SAVE_INVOICE_BREAKUP);
        for (SaleReport sr : salesRecord.getBreakUp()) {
            pstmtSr.setString(1, salesRecord.getSaleMetaData().getUuid());
            pstmtSr.setLong(2, sr.getProductId());
            pstmtSr.setLong(3, sr.getQuantity());
            pstmtSr.setFloat(4, sr.getPrice());
            pstmtSr.setFloat(5, sr.getAmount());
            pstmtSr.setFloat(6, sr.getRebate());
            pstmtSr.setFloat(7, sr.getCgst());
            pstmtSr.setFloat(8, sr.getSgst());
            pstmtSr.setFloat(9, sr.getFinalAmount());
            totalTaxes += sr.getCgst() + sr.getSgst();
            pstmtSr.executeUpdate();
        }
        pstmtSr.close();
        return totalTaxes;
    }

    public SaleRecord getSaleRecord(Connection connection, String udid, boolean gstBill, List<Product> products) throws SQLException {

        PreparedStatement stmt;
        if (gstBill) {
            stmt = connection.prepareStatement(GET_SALES_BILL_META_DATA);
        } else {
            stmt = connection.prepareStatement(GET_SALES_META_DATA);
        }
        stmt.setString(1, udid);
        ResultSet rs = stmt.executeQuery();
        SaleRecord saleRecord = new SaleRecord();
        if (rs.next()) {
            SaleMetaData saleMetaData = fetchSaleMetaData(rs);
            saleRecord.setSaleMetaData(saleMetaData);
            Customer customer = fetchCustomerInBill(rs);
            saleRecord.setCustomer(customer);
        }
        stmt.close();
        stmt = connection.prepareStatement(GET_INVOICE_SPLIT_UP);
        stmt.setString(1, udid);
        rs = stmt.executeQuery();
        List<SaleReport> saleReports = new ArrayList<>();
        saleRecord.setBreakUp(saleReports);
        while (rs.next()) {
            SaleReport sr = fetchSaleDetail(rs, getProductMapping(products));
            saleReports.add(sr);
        }
        stmt.close();
        return saleRecord;
    }

    private String GET_SALE_REPORT = "SELECT * FROM (" +
            "    SELECT id, transaction_date, amount, taxes, transportation_mode, state, vehicle," +
            "              customer_name, customer_address, customer_gst_in, customer_tel, customer_email, supply_place, udid, customer_id, 0 gst_bill" +
            "              FROM sales_record WHERE transaction_date > ? and transaction_date < ? " +
            "    UNION " +
            "    SELECT id, transaction_date, amount, taxes, transportation_mode, state, vehicle," +
            "              customer_name, customer_address, customer_gst_in, customer_tel, customer_email, supply_place, udid, customer_id, 1 gst_bill" +
            "              FROM sales_bill WHERE transaction_date > ? and transaction_date < ? " +
            "              ) temp" +
            " ORDER BY temp.transaction_date desc";

    public List<SaleMetaData> getSaleReport(Connection connection, Timestamp fromDate, Timestamp toDate) throws SQLException {
        List<SaleMetaData> sales = new ArrayList<>();
        PreparedStatement pstmt = connection.prepareStatement(GET_SALE_REPORT);
        pstmt.setTimestamp(1, fromDate);
        pstmt.setTimestamp(2, toDate);
        pstmt.setTimestamp(3, fromDate);
        pstmt.setTimestamp(4, toDate);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            SaleMetaData smd = fetchSaleMetaData(rs);
            sales.add(smd);
        }
        return sales;
    }

    public static final String GET_SALE_BILL_METADATA = "SELECT id, transaction_date, amount, taxes, transportation_mode, " +
            " state, vehicle, customer_name, customer_address, customer_gst_in, customer_tel, customer_email, supply_place, " +
            " udid, customer_id, 1 gst_bill FROM sales_bill WHERE id = ?";
    public static final String GET_SALE_INVOICE_METADATA = "SELECT id, transaction_date, amount, taxes, transportation_mode, " +
            " state, vehicle, customer_name, customer_address, customer_gst_in, customer_tel, customer_email, supply_place, " +
            " udid, customer_id, 0 gst_bill FROM sales_record WHERE id = ?";

    private Map<Long, String> getProductMapping(List<Product> products) {
        Map<Long, String> productNameMapping = new HashMap<>();
        for (Product prd : products) {
            productNameMapping.put(prd.getId(), prd.getTag());
        }
        return productNameMapping;
    }

    public SaleRecord getSaleRecord(Connection connection, String initial, Long id, List<Product> products) throws SQLException {
        SaleRecord saleRecord = new SaleRecord();
        PreparedStatement stmt;
        if (Constants.GST_BILL_INITIAL.equals(initial)) {
            stmt = connection.prepareStatement(GET_SALE_BILL_METADATA);
        } else {
            stmt = connection.prepareStatement(GET_SALE_INVOICE_METADATA);
        }
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            SaleMetaData smd = fetchSaleMetaData(rs);
            saleRecord.setSaleMetaData(smd);
            Customer customer = fetchCustomerInBill(rs);
            saleRecord.setCustomer(customer);
            stmt.close();
            stmt = connection.prepareStatement(GET_INVOICE_SPLIT_UP);
            stmt.setString(1, smd.getUuid());
            rs = stmt.executeQuery();
            List<SaleReport> saleReports = new ArrayList<>();
            saleRecord.setBreakUp(saleReports);

            while (rs.next()) {
                SaleReport sr = fetchSaleDetail(rs, getProductMapping(products));
                saleReports.add(sr);
            }
        }
        stmt.close();
        return saleRecord;
    }

    public static final String UPDATE_SALES_RECORD = "UPDATE sales_record SET amount = ?, taxes = ?, transportation_mode = ?, " +
            " state = ?, vehicle = ?, customer_name = ?, customer_address = ?, customer_gst_in = ?, customer_tel = ?, " +
            " customer_email = ?, supply_place=?, customer_id=? WHERE id = ?";
    public static final String UPDATE_SALES_BILL = "UPDATE sales_bill SET amount = ?, taxes = ?, transportation_mode = ?, " +
            " state = ?, vehicle = ?, customer_name = ?, customer_address = ?, customer_gst_in = ?, customer_tel = ?, " +
            " customer_email = ?, supply_place=?, customer_id=? WHERE id = ?";
    public static final String DELETE_DATA_FROM_INVOICE_BREAKUP = "DELETE FROM invoice_breakup WHERE udid = ?";

    public boolean editSalesReport(Connection connection, SaleRecord saleRecord) throws SQLException {
        PreparedStatement pstmt;
        //Delete all breakups for this salereacord udid
        pstmt = connection.prepareStatement(DELETE_DATA_FROM_INVOICE_BREAKUP);
        pstmt.setString(1, saleRecord.getSaleMetaData().getUuid());
        int res = pstmt.executeUpdate();
        logger.debug("Deleted {} records for sale record {}", res, saleRecord.getSaleMetaData());
        //Persist individual breakups
        float totalTaxes = saveInvoiceBreakup(connection, saleRecord);
        pstmt.close();
        if (saleRecord.getSaleMetaData().isGstBill()) {
            pstmt = connection.prepareStatement(UPDATE_SALES_BILL);
        } else {
            pstmt = connection.prepareStatement(UPDATE_SALES_RECORD);
        }
        pstmt.setFloat(1, saleRecord.getSaleMetaData().getTotalAmount());
        pstmt.setFloat(2, totalTaxes);
        pstmt.setString(3, saleRecord.getSaleMetaData().getTransportationMode());
        pstmt.setLong(4, saleRecord.getSaleMetaData().getState());
        pstmt.setString(5, saleRecord.getSaleMetaData().getVehicle());
        pstmt.setString(6, saleRecord.getCustomer().getCustomerName());
        pstmt.setString(7, saleRecord.getCustomer().getAddress());
        pstmt.setString(8, saleRecord.getCustomer().getGstin());
        pstmt.setString(9, saleRecord.getCustomer().getTel());
        pstmt.setString(10, saleRecord.getCustomer().getEmail());
        pstmt.setString(11, saleRecord.getSaleMetaData().getSupplyPlace());
        pstmt.setLong(12, saleRecord.getCustomer().getId());
        pstmt.setLong(13, saleRecord.getSaleMetaData().getId());
        res = pstmt.executeUpdate();
        pstmt.close();
        if (res == 1) {
            return true;
        } else {
            logger.error("The record is not persisted. Please check {}", saleRecord);
            return false;
        }
    }

    public boolean saveSaleBill(Connection connection, SaleRecord salesRecord) throws SQLException {
        //Persist individual breakups
        float totalTaxes = saveInvoiceBreakup(connection, salesRecord);
        salesRecord.getSaleMetaData().setTotalTaxes(MathUtils.roundoffToTwoPlaces(totalTaxes));
        PreparedStatement pstmt = connection.prepareStatement(SAVE_BILL_RECORD);
        Calendar cal = Calendar.getInstance();
        Timestamp currentTime = new Timestamp(cal.getTimeInMillis());
        pstmt.setTimestamp(1, currentTime);
        pstmt.setFloat(2, salesRecord.getSaleMetaData().getTotalAmount());
        pstmt.setFloat(3, salesRecord.getSaleMetaData().getTotalTaxes());
        pstmt.setLong(4, salesRecord.getSaleMetaData().getState());
        pstmt.setString(5, salesRecord.getSaleMetaData().getVehicle());
        pstmt.setString(6, salesRecord.getCustomer().getCustomerName());
        pstmt.setLong(7, salesRecord.getCustomer().getId());
        pstmt.setString(8, salesRecord.getCustomer().getAddress());
        pstmt.setString(9, salesRecord.getCustomer().getGstin());
        pstmt.setString(10, salesRecord.getCustomer().getTel());
        pstmt.setString(11, salesRecord.getCustomer().getEmail());
        pstmt.setString(12, salesRecord.getSaleMetaData().getSupplyPlace());
        pstmt.setString(13, salesRecord.getSaleMetaData().getUuid());
        pstmt.setString(14, salesRecord.getSaleMetaData().getTransportationMode());
        int numRecs = pstmt.executeUpdate();
        pstmt.close();
        logger.debug("Number of records created is {} : {}", numRecs, salesRecord);
        if (numRecs == 1) {
            return true;
        } else {
            return false;

        }
    }

    public boolean saveSaleInvoice(Connection connection, SaleRecord salesRecord) throws SQLException {
        if (salesRecord.getCustomer().getGstin().isEmpty()) {
            saveSaleBill(connection, salesRecord);
            return true;
        } else {
            float totalTaxes = saveInvoiceBreakup(connection, salesRecord);
            salesRecord.getSaleMetaData().setTotalTaxes(MathUtils.roundoffToTwoPlaces(totalTaxes));
            PreparedStatement pstmt = connection.prepareStatement(SAVE_INVOICE_RECORD);
            Calendar cal = Calendar.getInstance();
            Timestamp currentTime = new Timestamp(cal.getTimeInMillis());
            pstmt.setTimestamp(1, currentTime);
            pstmt.setFloat(2, salesRecord.getSaleMetaData().getTotalAmount());
            pstmt.setFloat(3, salesRecord.getSaleMetaData().getTotalTaxes());
            pstmt.setLong(4, salesRecord.getSaleMetaData().getState());
            pstmt.setString(5, salesRecord.getSaleMetaData().getVehicle());
            pstmt.setString(6, salesRecord.getCustomer().getCustomerName());
            pstmt.setLong(7, salesRecord.getCustomer().getId());
            pstmt.setString(8, salesRecord.getCustomer().getAddress());
            pstmt.setString(9, salesRecord.getCustomer().getGstin());
            pstmt.setString(10, salesRecord.getCustomer().getTel());
            pstmt.setString(11, salesRecord.getCustomer().getEmail());
            pstmt.setString(12, salesRecord.getSaleMetaData().getSupplyPlace());
            pstmt.setString(13, salesRecord.getSaleMetaData().getUuid());
            pstmt.setString(14, salesRecord.getSaleMetaData().getTransportationMode());
            int numRecs = pstmt.executeUpdate();
            pstmt.close();
            logger.debug("Number of records created is {} : {}", numRecs, salesRecord);
            if (numRecs == 1) {
                return false;
            }
        }
        return false;
    }
}
