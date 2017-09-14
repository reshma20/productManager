package com.nambissians.billing.ui.handlers.login;/**
 * Created by SajiV on 14/09/17.
 */

import com.nambissians.billing.ui.screen.MainScreen;
import com.nambissians.billing.utils.DBConnectionUtils;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
public class LoginButtonClickHandler implements EventHandler<ActionEvent> {

    private TextField txtUserName;
    private PasswordField pwdField;
    private Text actiontarget;
    private Stage primaryStage;
    private static final String EMPTY_USER_NAME_PASSWORD_ERROR = "ERR_pswd_username_not_mentioned";
    private static final String USER_NAME_PASSWORD_INCORRECT_ERROR = "ERR_pswd_username_incorrect";

    public LoginButtonClickHandler(TextField txtUserName, PasswordField pwdField, Text actiontarget, Stage primaryStage) {
        this.txtUserName = txtUserName;
        this.pwdField = pwdField;
        this.actiontarget = actiontarget;
        this.primaryStage = primaryStage;
    }

    @Override
    public void handle(ActionEvent e) {
        actiontarget.setFill(Color.FIREBRICK);
        if (txtUserName.getText().isEmpty() || pwdField.getText().isEmpty()) {
            actiontarget.setText(InternationalizationUtil.getString(EMPTY_USER_NAME_PASSWORD_ERROR));
        } else {
            if (DBConnectionUtils.initializeDbConnections(txtUserName.getText(), pwdField.getText())) {
                MainScreen screen = new MainScreen(primaryStage);
                screen.init();
            } else {
                actiontarget.setText(InternationalizationUtil.getString(USER_NAME_PASSWORD_INCORRECT_ERROR));
            }
        }
    }
}