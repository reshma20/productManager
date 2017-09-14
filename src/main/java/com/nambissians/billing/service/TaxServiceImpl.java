package com.nambissians.billing.service;


import com.nambissians.billing.dao.TaxesDaoImpl;
import com.nambissians.billing.model.Tax;
import com.nambissians.billing.utils.DBConnectionUtils;
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
public class TaxServiceImpl {
    private static Logger logger = LoggerFactory.getLogger(TaxServiceImpl.class);
    private TaxesDaoImpl taxesDao = new TaxesDaoImpl();

    public boolean persist(Tax tax) {
        try {
            boolean returnVal = true;
            //Assumption : There is only one profile at a time.
            Connection connection = DBConnectionUtils.getConnection();
            tax.setId(taxesDao.getNextTaxId(connection));
            int count = taxesDao.createProfile(connection, tax);

            if (count == 1) {
                return DBConnectionUtils.commitTransaction(connection);
            } else {
                return DBConnectionUtils.rollbackTransaction(connection);
            }
        } catch (SQLException exp) {
            logger.error("Could not save profile {}", tax, exp);
        }
        return false;
    }

    public List<Tax> getTaxes() {
        try {
            Connection connection = DBConnectionUtils.getConnection();
            connection.setReadOnly(true);
            List<Tax> taxes = taxesDao.getTaxes(connection);
            DBConnectionUtils.commitTransaction(connection);
            return taxes;
        } catch (Exception exp) {
            logger.error("Couldn't get owner profile", exp);
            return new ArrayList<>();
        }
    }
}
