package com.nambissians.billing.ui.screen;


import com.nambissians.billing.model.OwnerProfile;
import com.nambissians.billing.service.ProfileServiceImpl;
import com.nambissians.billing.ui.handlers.CancelButtonTitledHandler;
import com.nambissians.billing.ui.handlers.profile.SaveProfileButtonHandler;
import com.nambissians.billing.utils.Constants;
import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

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

public class ProfilePane extends TitlePaneGenerator {

    public ProfilePane(Stage stage) {
        setStage(stage);
    }

    @Override
    public TitledPane generatePanel(Accordion root) {
        TitledPane profileTitledPane = new TitledPane();
        profileTitledPane.setText(InternationalizationUtil.getString(Constants.PROFILE));
        profileTitledPane.expandedProperty().addListener(new ProfileTitledPaneChangeListener(profileTitledPane, getStage()));
        return profileTitledPane;
    }
}


class FileBrowserButtonClickHandler implements EventHandler<ActionEvent> {

    private ImageView imageView;
    private Stage stage;
    private static final String CHOOSE_LOGO = "choose_logo";
    private static final String JPG_FILTER = "*.jpg";
    private static final String JPG_FILES = "JPG files (*.jpg)";
    private static final String PNG_FILES = "PNG files (*.png)";
    private static final String PNG_FILTER = "*.png";
    private static final Logger logger = LoggerFactory.getLogger(FileBrowserButtonClickHandler.class);

    public FileBrowserButtonClickHandler(Stage stage, ImageView view) {
        this.imageView = view;
        this.stage = stage;
    }

    private boolean loadFile(File file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imageView.setImage(image);
        } catch (Exception exp) {
            logger.error("Error while loading image from file path {}", file.getAbsoluteFile(), exp);
            return false;
        }
        return true;
    }

    @Override
    public void handle(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(JPG_FILES, JPG_FILTER);
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter(PNG_FILES, PNG_FILTER);
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
        fileChooser.setTitle(InternationalizationUtil.getString(CHOOSE_LOGO));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            loadFile(file);
        }
    }

}

class ProfileTitledPaneChangeListener extends AbstractTitledPaneChangeListener {
    private ProfileServiceImpl profileService = new ProfileServiceImpl();
    private Stage stage;

    private void loadImage(ImageView imgView, byte[] imageBytes) {
        if (imageBytes != null && imageBytes.length != 0) {
            Image img = new Image(new ByteArrayInputStream(imageBytes));
            imgView.setImage(img);
        }
    }

    public ProfileTitledPaneChangeListener(TitledPane pane, Stage stage) {
        super(pane);
        this.stage = stage;
    }

    @Override
    protected void populatePane(TitledPane mainPane) {
        ScrollPane pane = new ScrollPane();
        pane.setMinHeight(Constants.TITLED_HEIGHT/2);
        mainPane.setContent(pane);
        OwnerProfile profile = profileService.getOwnerProfile();
        GridPane profilePane = new GridPane();
        profilePane.setAlignment(Pos.CENTER);
        profilePane.setHgap(10);
        profilePane.setVgap(10);
        profilePane.setPadding(new Insets(25, 25, 25, 25));
        Text scenetitle = new Text(InternationalizationUtil.getString(Constants.TITLE));
        scenetitle.setFont(Font.font(Constants.TAHOMA_FONT, FontWeight.NORMAL, 20));
        profilePane.add(scenetitle, 0, 0, 1, 1);

        //Company Name
        Label lblCompanyName = new Label(InternationalizationUtil.getString(Constants.COMPANY_NAME));
        profilePane.add(lblCompanyName, 2, 0);
        TextField txtCompanyName = new TextField();
        if (profile.getCompanyName() != null) {
            txtCompanyName.setText(profile.getCompanyName());
        }
        profilePane.add(txtCompanyName, 3, 0);

        //LOGO
        Label lblLogo = new Label(InternationalizationUtil.getString(Constants.LOGO));
        profilePane.add(lblLogo, 0, 1);
        ImageView logo = new ImageView();
        logo.setFitHeight(300);
        logo.setFitWidth(200);
        logo.setPreserveRatio(true);
        loadImage(logo, profile.getLogo());
        profilePane.add(logo, 1, 1);
        Button btnChoose = new Button(InternationalizationUtil.getString(Constants.BROWSE));
        btnChoose.setOnAction(new FileBrowserButtonClickHandler(stage, logo));
        profilePane.add(btnChoose, 1, 2);

        //Address
        Label lblAddress = new Label(InternationalizationUtil.getString(Constants.ADDRESS));
        profilePane.add(lblAddress, 2, 1);
        TextArea txtAddress = new TextArea();
        txtAddress.setMaxHeight(50);
        if (profile.getAddress() != null) {
            txtAddress.setText(profile.getAddress());
        }
        profilePane.add(txtAddress, 3, 1);

        //Mobile
        Label lblMobile = new Label(InternationalizationUtil.getString(Constants.MOBILE));
        profilePane.add(lblMobile, 2, 3);
        TextField txtMobile = new TextField();
        if (profile.getMobile() != null) {
            txtMobile.setText(profile.getMobile());
        }
        profilePane.add(txtMobile, 3, 3);

        //Telephone
        Label lblTel = new Label(InternationalizationUtil.getString(Constants.TEL));
        profilePane.add(lblTel, 0, 3);
        TextField txtTel = new TextField();
        if (profile.getTel() != null) {
            txtTel.setText(profile.getTel());
        }
        profilePane.add(txtTel, 1, 3);

        //Email
        Label lblEmail = new Label(InternationalizationUtil.getString(Constants.EMAIL));
        profilePane.add(lblEmail, 0, 4);
        TextField txtEmail = new TextField();
        if (profile.getEmail() != null) {
            txtEmail.setText(profile.getEmail());
        }
        profilePane.add(txtEmail, 1, 4);

        //GSTIN
        Label lblGSTIN = new Label(InternationalizationUtil.getString(Constants.GSTIN));
        profilePane.add(lblGSTIN, 2, 4);
        TextField txtGSTIN = new TextField();
        if (profile.getGstin() != null) {
            txtGSTIN.setText(profile.getGstin());
        }
        profilePane.add(txtGSTIN, 3, 4);

        Button btnSave = new Button(InternationalizationUtil.getString(Constants.SAVE));
        btnSave.setOnAction(new SaveProfileButtonHandler(txtCompanyName, logo, txtAddress, txtMobile, txtTel, txtEmail, txtGSTIN));
        profilePane.add(btnSave, 1, 5);
        btnSave.setDefaultButton(true);

        Button btnCancel = new Button(InternationalizationUtil.getString(Constants.CANCEL));
        btnCancel.setOnAction(new CancelButtonTitledHandler(mainPane));
        btnCancel.setCancelButton(true);
        profilePane.add(btnCancel, 2, 5);

        pane.setContent(profilePane);
        mainPane.setMinWidth(Constants.TITLED_HEIGHT+100);
    }
}
