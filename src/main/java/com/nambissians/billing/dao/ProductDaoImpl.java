package com.nambissians.billing.dao;/**
 * Created by SajiV on 07/09/17.
 */

import com.nambissians.billing.model.Product;
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
public class ProductDaoImpl {

    private static final String INSERT_PROFILE = "INSERT INTO product (tag, description, hsn_code,taxes,price, createdate) VALUES (?, ?, ?, ?, ?, ?)";

    private Product mapProduct(ResultSet rs) throws SQLException {
        Product prd = new Product();
        prd.setId(rs.getLong(Constants.ID));
        prd.setTag(rs.getString(Constants.TAG));
        prd.setDescription(rs.getString(Constants.DESCRIPTION));
        prd.setHsnCode(rs.getString(Constants.HSN_CODE));
        prd.setTaxes(rs.getLong(Constants.TAXES));
        prd.setPrice(rs.getFloat(Constants.PRICE));
        prd.setCreateDate(rs.getTimestamp(Constants.CREATE_DATE));
        return prd;
    }
    public int createProfile(Connection connection, Product product) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(INSERT_PROFILE);
        pstmt.setString(1, product.getTag());
        pstmt.setString(2, product.getDescription());
        pstmt.setString(3, product.getHsnCode());
        pstmt.setLong(4, product.getTaxes());
        pstmt.setFloat(5, product.getPrice());
        Calendar cal = Calendar.getInstance();
        Timestamp currentTimeStamp = new Timestamp(cal.getTimeInMillis());
        pstmt.setTimestamp(6, currentTimeStamp);
        int res=  pstmt.executeUpdate();
        pstmt.close();
        return res;
    }

    private static final String GET_ALL_PRODUCTS = "SELECT id,tag, description, hsn_code,taxes,price, createdate FROM product ORDER BY createdate desc";

    public List<Product> getAllProducts(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(GET_ALL_PRODUCTS);
        ResultSet rs = pstmt.executeQuery();
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            Product prd = mapProduct(rs);
            products.add(prd);
        }
        pstmt.close();
        return products;
    }

    private static final String SEARCH_PRODUCTS_BY_TAG = "SELECT id,tag, description, hsn_code,taxes,price, createdate FROM product WHERE tag like UPPER(?) ORDER BY createdate desc";
    public List<Product> searchProductsByTag(Connection connection, String pattern) throws  SQLException{
        String searchPattern = Constants.WILD_CARD_FOR_ANY+pattern+Constants.WILD_CARD_FOR_ANY;
        PreparedStatement pstmt = connection.prepareStatement(SEARCH_PRODUCTS_BY_TAG);
        pstmt.setString(1,searchPattern);
        ResultSet rs = pstmt.executeQuery();
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            Product prd = mapProduct(rs);
            products.add(prd);
        }
        pstmt.close();
        return products;
    }
}
