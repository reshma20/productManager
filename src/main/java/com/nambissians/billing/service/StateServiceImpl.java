package com.nambissians.billing.service;/**
 * Created by SajiV on 12/09/17.
 */

import com.nambissians.billing.dao.StateDaoImpl;
import com.nambissians.billing.model.State;
import com.nambissians.billing.utils.DBConnectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class StateServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(StateServiceImpl.class);
    private static List<State> states = null;
    private static Map<String, Long> stateMap = new HashMap<>();
    private static Map<Long, String> reverseStateMap = new HashMap<>();
    private StateDaoImpl stateDao = new StateDaoImpl();

    public List<State> getStates() {
        if(states == null) {
            synchronized (StateServiceImpl.class) {
                if(states == null) {
                    try {
                        Connection connection = DBConnectionUtils.getConnection();
                        states = stateDao.getStates(connection);
                        for(State state: states) {
                            stateMap.put(state.getState(), state.getId());
                            reverseStateMap.put(state.getId(), state.getState());
                        }
                        DBConnectionUtils.commitTransaction(connection);
                    } catch (SQLException exp) {
                        logger.error("Could not fetch list of states", exp);
                    }
                }
            }
        }
        return states;
    }

    public String getState(Long num) {
        if(states == null) {
            getStates();
        }
        return String.format("%s (%2d)",reverseStateMap.get(num), num);
    }

    public String getState(Long num, Boolean format) {
        if(format) {
            return getState(num);
        } else {
            if(states == null) {
                getStates();
            }
            return reverseStateMap.get(num);
        }
    }

    public Long getStateCode(String str) {
        if(states == null) {
            getStates();
        }
        return stateMap.get(str);
    }
}
