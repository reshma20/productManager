package com.nambissians.billing.dao;/**
 * Created by SajiV on 12/09/17.
 */

import com.nambissians.billing.model.State;
import com.nambissians.billing.utils.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
public class StateDaoImpl {

    private static final String GET_STATES = "SELECT id, state, default_state FROM STATE order by id";
    public List<State> getStates(Connection connection) throws SQLException {
        PreparedStatement pstmt = connection.prepareStatement(GET_STATES);
        ResultSet rs = pstmt.executeQuery();
        List<State> states = new ArrayList<>();
        while (rs.next()) {
            State state = new State();
            state.setId(rs.getLong(Constants.ID));
            state.setState(rs.getString(Constants.STATE));
            state.setDefaultState(rs.getInt(Constants.DEFAULT_STATE) == 1);
            states.add(state);
        }
        pstmt.close();
        return states;
    }
}
