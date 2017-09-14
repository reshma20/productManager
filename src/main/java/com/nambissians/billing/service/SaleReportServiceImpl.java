package com.nambissians.billing.service;/**
 * Created by SajiV on 10/09/17.
 */

import com.nambissians.billing.dao.SaleReportDaoImpl;
import com.nambissians.billing.model.SaleRecord;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.DBConnectionUtils;
import com.nambissians.billing.utils.InternationalizationUtil;
import com.nambissians.billing.utils.NotificationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

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
public class SaleReportServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(SaleReportServiceImpl.class);
    private SaleReportDaoImpl saleReportDao = new SaleReportDaoImpl();

    public boolean persist(SaleRecord saleRecord) {
        try {
            //Assumption : There is only one profile at a time.
            if (saleRecord.getBreakUp() != null && (saleRecord.getBreakUp().isEmpty() == false)) {
                Connection connection = DBConnectionUtils.getConnection();
                boolean success = saleReportDao.saveSaleInvoice(connection, saleRecord);

                if (success) {
                    NotificationUtils.showMessage(InternationalizationUtil.getString(Constants.MSG_SAVED_INVOICE));
                    return DBConnectionUtils.commitTransaction(connection);
                } else {
                    NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_COULD_NOT_SAVE_INVOICE));
                    return DBConnectionUtils.rollbackTransaction(connection);
                }
            } else {
                NotificationUtils.showWarn(InternationalizationUtil.getString(Constants.ERR_NO_PRODUCT_SELECTED_FOR_INVOICE));
            }
        } catch (SQLException exp) {
            logger.error("Could not save Sale Report {}", saleRecord, exp);
            NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_COULD_NOT_SAVE_INVOICE));
        }
        return false;
    }

    public SaleRecord getSaleRecord(String udid) {
        try {
            Connection connection = DBConnectionUtils.getConnection();
            SaleRecord sr = saleReportDao.getSaleRecord(connection, udid);
            DBConnectionUtils.commitTransaction(connection);
            return sr;
        } catch (Exception exp) {
            logger.error("Error while generating record. Please try again later", exp);
            NotificationUtils.showError(InternationalizationUtil.getString(Constants.ERR_COULD_NOT_GENERATE_INVOICE));
        }
        return null;
    }
}
