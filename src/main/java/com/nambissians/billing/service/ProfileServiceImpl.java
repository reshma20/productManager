package com.nambissians.billing.service;


import com.nambissians.billing.dao.ProfileDaoImpl;
import com.nambissians.billing.model.OwnerProfile;
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
public class ProfileServiceImpl {
    private static Logger logger = LoggerFactory.getLogger(ProfileServiceImpl.class);
    private ProfileDaoImpl profileDao = new ProfileDaoImpl();

    public boolean saveOrUpdateProfile(OwnerProfile profile) {
        try {
            boolean returnVal = false;
            //Assumption : There is only one profile at a time.
            Connection connection = DBConnectionUtils.getConnection();
            int count = profileDao.updateProfile(connection, profile);
            if(count == 0) {
                //Case where the profile is created for first time.
                if(profileDao.createProfile(connection, profile)==1) {
                    logger.debug("Created profile {}", profile);
                    returnVal= true;
                } else {
                    returnVal = false;
                }
            } else {
                returnVal = true;
            }
            if(returnVal) {
                NotificationUtils.showMessage(InternationalizationUtil.getString(Constants.MSG_PROFILE_SAVED));
                return DBConnectionUtils.commitTransaction(connection);
            } else {
                return DBConnectionUtils.rollbackTransaction(connection);
            }
        } catch (SQLException exp) {
            logger.error("Could not save profile {}", profile, exp);
            NotificationUtils.showMessage(InternationalizationUtil.getString(Constants.ERR_PROFILE_SAVED));
        }
        return false;
    }

    public OwnerProfile getOwnerProfile() {
        try {
            Connection connection = DBConnectionUtils.getConnection();
            OwnerProfile profile = profileDao.getOwnerProfile(connection);
            DBConnectionUtils.commitTransaction(connection);
            return profile;
        } catch (Exception exp) {
            logger.error("Couldn't get owner profile", exp);
            return new OwnerProfile();
        }
    }
}
