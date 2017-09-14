package com.nambissians.billing.dao;


import com.nambissians.billing.model.Tax;
import com.nambissians.billing.model.TaxHead;
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

public class TaxesDaoImpl {

    private static final String INSERT_TAX = "INSERT INTO taxes (tag, description, percentage, create_date, id, head) VALUES (?, ?, ?, ?, ?, ?)";

    public int createProfile(Connection connection, Tax tax) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(INSERT_TAX);
        pstmt.setString(1, tax.getTag());
        pstmt.setString(2, tax.getDescription());
        pstmt.setFloat(3, tax.getPercentage());
        Calendar cal = Calendar.getInstance();
        Timestamp currentTimeStamp = new Timestamp(cal.getTimeInMillis());
        pstmt.setTimestamp(4, currentTimeStamp);
        pstmt.setLong(5, tax.getId());
        pstmt.setString(6, tax.getTaxHead().toString());
        int res = pstmt.executeUpdate();
        pstmt.close();
        return res;
    }

    private static final String GET_TAXES = "SELECT id, tag, description, percentage, create_date, head FROM taxes order by create_date desc";
    private static final String ID = "id";
    private static final String PERCENTAGE = "percentage";
    private static final String TAG = "tag";
    private static final String CREATE_DATE = "create_date";
    private static final String TAX_HEAD = "head";

    public List<Tax> getTaxes(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(GET_TAXES);
        ResultSet rs = pstmt.executeQuery();
        List<Tax> taxes = new ArrayList<>();
        while (rs.next()) {
            Tax tax = new Tax();
            tax.setPercentage(rs.getFloat(PERCENTAGE));
            tax.setId(rs.getLong(ID));
            tax.setTag(rs.getString(TAG));
            tax.setDescription(rs.getString(Constants.DESCRIPTION));
            tax.setCreateDate(rs.getTimestamp(CREATE_DATE));
            tax.setTaxHead(TaxHead.getTaxHead(rs.getString(TAX_HEAD)));
            taxes.add(tax);
        }
        pstmt.close();
        return taxes;
    }

    private static final String GET_NEXT_TAX_ID = "SELECT (CASE WHEN t.nextId IS NULL THEN 2 ELSE t.nextId * 2 END ) nextId FROM (SELECT max(id) nextId FROM taxes)t;";
    private static final String NEXT_ID = "nextId";

    public long getNextTaxId(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(GET_NEXT_TAX_ID);
        ResultSet rs = pstmt.executeQuery();
        long nextId = 0l;
        if (rs.next()) {
            nextId = rs.getLong(NEXT_ID);
        }
        pstmt.close();
        return nextId;
    }
}
