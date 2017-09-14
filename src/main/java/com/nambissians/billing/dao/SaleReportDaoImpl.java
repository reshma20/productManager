package com.nambissians.billing.dao;/**
 * Created by SajiV on 10/09/17.
 */

import com.nambissians.billing.model.Customer;
import com.nambissians.billing.model.SaleMetaData;
import com.nambissians.billing.model.SaleRecord;
import com.nambissians.billing.model.SaleReport;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class SaleReportDaoImpl {

    private static final Logger logger = LoggerFactory.getLogger(SaleReportDaoImpl.class);

    private static final String SAVE_INVOICE_RECORD = "INSERT INTO SALES_RECORD (transaction_date, amount, taxes, state, " +
            "vehicle, customer_name, customer_id, customer_address, customer_gst_in, customer_tel, customer_email, " +
            "supply_place, udid, transportation_mode) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?, ?);";
    private static final String SAVE_INVOICE_BREAKUP = "INSERT INTO INVOICE_BREAKUP(udid,product_id,quantity,rate,amount," +
            "rebate,cgst,sgst,final_amount) VALUES(?,?,?,?,?,?,?,?,?)";

    public boolean saveSaleInvoice(Connection connection, SaleRecord salesRecord) throws SQLException {
        PreparedStatement pstmtSr = connection.prepareStatement(SAVE_INVOICE_BREAKUP);
        float totalTaxes = 0;
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
            return true;
        } else {
            return false;

        }
    }

    private String GET_SALES_META_DATA = "SELECT id, transaction_date, amount, taxes, transportation_mode, state, vehicle, " +
            "customer_name, customer_address, customer_gst_in, customer_tel, customer_email, supply_place, udid, customer_id " +
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

    public SaleRecord getSaleRecord(Connection connection, String udid) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(GET_SALES_META_DATA);
        stmt.setString(1, udid);
        ResultSet rs = stmt.executeQuery();
        SaleRecord saleRecord = new SaleRecord();
        if (rs.next()) {
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
            saleRecord.setSaleMetaData(saleMetaData);
            Customer customer = new Customer();
            customer.setEmail(rs.getString(CUSTOMER_EMAIL));
            customer.setTel(rs.getString(CUSTOMER_TEL));
            customer.setCustomerName(rs.getString(CUSTOMER_NAME));
            customer.setAddress(rs.getString(CUSTOMER_ADDRESS));
            customer.setGstin(rs.getString(CUSTOMER_GST_IN));
            customer.setId(rs.getLong(CUSTOMER_ID));
            saleRecord.setCustomer(customer);
        }
        stmt.close();
        stmt = connection.prepareStatement(GET_INVOICE_SPLIT_UP);
        stmt.setString(1, udid);
        rs = stmt.executeQuery();
        List<SaleReport> saleReports = new ArrayList<>();
        saleRecord.setBreakUp(saleReports);
        while (rs.next()) {
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
            float taxableAmount = sr.getAmount() * (1-sr.getRebate()/100);
            sr.setTaxableAmount(MathUtils.roundoffToTwoPlaces(taxableAmount));
            saleReports.add(sr);
        }
        stmt.close();
        return saleRecord;
    }
}
