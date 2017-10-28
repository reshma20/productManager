package com.nambissians.billing.ui.login;

import com.nambissians.billing.ui.handlers.login.LoginButtonClickHandler;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

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


public class LaunchProductManager extends Application {
    private static final Logger logger = LoggerFactory.getLogger(LaunchProductManager.class);
    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "password";
    private static final String LOGIN = "login";

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource(LAUNCH_SCREEN));
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Text scenetitle = new Text(InternationalizationUtil.getString(Constants.TITLE));
        scenetitle.setFont(Font.font(Constants.TAHOMA_FONT, FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        Label userName = new Label(InternationalizationUtil.getString(USER_NAME));
        grid.add(userName, 0, 1);
        TextField txtUserName = new TextField();
        txtUserName.requestFocus();
        grid.add(txtUserName, 1, 1);
        Label pw = new Label(InternationalizationUtil.getString(PASSWORD));
        grid.add(pw, 0, 2);
        PasswordField pwPassword = new PasswordField();
        grid.add(pwPassword, 1, 2);
        Button btn = new Button(InternationalizationUtil.getString(LOGIN));
        Text actiontarget = new Text();
        btn.setOnAction(new LoginButtonClickHandler(txtUserName, pwPassword, actiontarget, primaryStage));
        HBox hbBtn = new HBox(10);
        btn.setDefaultButton(true);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        grid.add(actiontarget, 1, 6);
        primaryStage.setScene(new Scene(grid));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        try {
            BasicConfigurator.configure();
            Properties props = new Properties();
            InputStream input = new FileInputStream(args[0]);
            //System.out.println();
            props.load(input);
            props.putAll(System.getProperties());
            //props.put(Constants.LOG4J_CONFIGURATION_FILE, args[0]);
            System.setProperties(props);
            launch(args);
        } catch (Exception exp) {
            logger.error("Couldn't launch application properties", exp);
        }
    }
}