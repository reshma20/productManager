package com.nambissians.billing.dao;


import com.nambissians.billing.model.OwnerProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class ProfileDaoImpl {
    private static final String UPDATE_PROFILE = "UPDATE owner SET tel = ?, mob = ?, email = ?, address = ?, logo = ?, company = ? , gstin = ?";

    public int updateProfile(Connection connection, OwnerProfile profile) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(UPDATE_PROFILE);
        pstmt.setString(1, profile.getTel());
        pstmt.setString(2, profile.getMobile());
        pstmt.setString(3, profile.getEmail());
        pstmt.setString(4, profile.getAddress());
        pstmt.setBytes(5, profile.getLogo());
        pstmt.setString(6, profile.getCompanyName());
        pstmt.setString(7, profile.getGstin());
        return pstmt.executeUpdate();
    }

    private static final String INSERT_PROFILE = "INSERT INTO owner (tel, mob, email,address,logo,company, gstin) VALUES (?, ?, ?,?, ?,?,?)";

    public int createProfile(Connection connection, OwnerProfile profile) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(INSERT_PROFILE);
        pstmt.setString(1, profile.getTel());
        pstmt.setString(2, profile.getMobile());
        pstmt.setString(3, profile.getEmail());
        pstmt.setString(4, profile.getAddress());
        pstmt.setBytes(5, profile.getLogo());
        pstmt.setString(6, profile.getCompanyName());
        pstmt.setString(7, profile.getGstin());
        return pstmt.executeUpdate();
    }

    private static final String GET_OWNER_PROFILE = "SELECT id, tel, mob, email, address, logo, company, gstin FROM owner";
    private static final String ID = "id";
    private static final String TEL="tel";
    private static final String MOB="mob";
    private static final String EMAIL = "email";
    private static final String ADDRESS = "address";
    private static final String LOGO = "logo";
    private static final String COMPANY="company";
    private static final String GSTIN = "gstin";
    public OwnerProfile getOwnerProfile(Connection connection) throws SQLException{
        PreparedStatement pstmt = connection.prepareStatement(GET_OWNER_PROFILE);
        OwnerProfile profile = new OwnerProfile();
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()) {
            profile.setId(rs.getLong(ID));
            profile.setTel(rs.getString(TEL));
            profile.setMobile(rs.getString(MOB));
            profile.setEmail(rs.getString(EMAIL));
            profile.setAddress(rs.getString(ADDRESS));
            profile.setCompanyName(rs.getString(COMPANY));
            profile.setGstin(rs.getString(GSTIN));
            profile.setLogo(rs.getBytes(LOGO));
        }
        pstmt.close();
        return profile;
    }
}
