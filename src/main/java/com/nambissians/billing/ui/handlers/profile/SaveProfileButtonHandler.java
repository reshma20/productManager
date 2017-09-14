package com.nambissians.billing.ui.handlers.profile;

import com.nambissians.billing.model.OwnerProfile;
import com.nambissians.billing.service.ProfileServiceImpl;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

public class SaveProfileButtonHandler implements EventHandler<ActionEvent> {
    private static final Logger logger = LoggerFactory.getLogger(SaveProfileButtonHandler.class);
    private ImageView logo;
    private TextArea address;
    private TextField company;
    private TextField gstin;
    private TextField email;
    private TextField mob;
    private TextField tel;
    private static final String PNG_FORMAT = "png";
    private ProfileServiceImpl profileService = new ProfileServiceImpl();

    public SaveProfileButtonHandler(TextField txtCompanyName, ImageView logo, TextArea txtAddress,
                                    TextField txtMobile, TextField txtTel, TextField txtEmail,
                                    TextField txtGSTIN) {
        this.logo = logo;
        this.address = txtAddress;
        this.company = txtCompanyName;
        this.gstin = txtGSTIN;
        this.email = txtEmail;
        this.mob = txtMobile;
        this.tel = txtTel;
    }

    @Override
    public void handle(ActionEvent event) {
        try {
            BufferedImage bImage = SwingFXUtils.fromFXImage(logo.getImage(), null);
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            ImageIO.write(bImage, PNG_FORMAT, s);
            byte[] res = s.toByteArray();
            s.close();
            OwnerProfile profile = new OwnerProfile();
            profile.setAddress(address.getText());
            profile.setCompanyName(company.getText());
            profile.setGstin(gstin.getText());
            profile.setMobile(mob.getText());
            profile.setTel(tel.getText());
            profile.setEmail(email.getText());
            profile.setLogo(res);
            profileService.saveOrUpdateProfile(profile);
        } catch (Exception exp) {
            logger.error("Couldn't save image", exp);
        }
    }
}
