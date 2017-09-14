package com.nambissians.billing.service;

import com.nambissians.billing.dao.ProductDaoImpl;
import com.nambissians.billing.dao.TaxesDaoImpl;
import com.nambissians.billing.model.Product;
import com.nambissians.billing.model.Tax;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.DBConnectionUtils;
import com.nambissians.billing.utils.InternationalizationUtil;
import com.nambissians.billing.utils.NotificationUtils;
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
public class ProductServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private ProductDaoImpl productDao = new ProductDaoImpl();
    private TaxesDaoImpl taxesDao = new TaxesDaoImpl();

    public boolean createProduct(Product prd) {
        try {
            boolean returnVal;
            Connection connection = DBConnectionUtils.getConnection();
            //Case where the profile is created for first time.
            if (productDao.createProfile(connection, prd) == 1) {
                logger.debug("Created product {}", prd);
                returnVal = true;
            } else {
                returnVal = false;
            }

            if (returnVal) {
                return DBConnectionUtils.commitTransaction(connection);
            } else {
                return DBConnectionUtils.rollbackTransaction(connection);
            }
        } catch (SQLException exp) {
            logger.error("Could not save product {}", prd, exp);
        }
        return false;
    }

    public List<Product> getAllProductsWithApplicableTaxes() {
        Connection connection;
        try {
            connection = DBConnectionUtils.getConnection();
            connection.setReadOnly(true);
            List<Tax> taxes = taxesDao.getTaxes(connection);
            List<Product> products = productDao.getAllProducts(connection);
            if (products != null && (products.isEmpty() == false)) {
                for (Product prd : products) {
                    boolean firstProduct = true;
                    StringBuffer applicableTaxes = new StringBuffer(Constants.EMPTY_STRING);
                    long tax = prd.getTaxes();
                    for (Tax taxType : taxes) {
                        if ((taxType.getId() & tax) != 0) {
                            if (firstProduct == false) {
                                applicableTaxes.append(Constants.TAX_SEPARATOR);
                            } else {
                                firstProduct = false;
                            }
                            applicableTaxes.append(taxType.getTag());
                        }
                    }
                    prd.setApplicableTaxes(applicableTaxes.toString());
                }
            }
            DBConnectionUtils.commitTransaction(connection);
            return products;
        } catch (SQLException exp) {
            logger.error("Could not get all products", exp);
            return new ArrayList<>();
        }
    }

    public List<Product> searchProductsByTag(String searchString) {
        List<Product> products;
        try {
            Connection connection = DBConnectionUtils.getConnection();
            connection.setReadOnly(true);
            products = productDao.searchProductsByTag(connection, searchString);
            DBConnectionUtils.commitTransaction(connection);
            if (products.isEmpty()) {
                NotificationUtils.showWarn(InternationalizationUtil.getString(Constants.ERR_NO_MATCHING_PRODUCT));
            }
            return products;
        } catch (Exception exp) {
            logger.error("Couldn't get owner products", exp);
            NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_COULD_NOT_GET_PRODUCT_LIST));
            return new ArrayList<>();
        }
    }
}
