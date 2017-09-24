package com.nambissians.billing.ui.handlers.profile;/**
 * Created by SajiV on 20/09/17.
 */

import com.nambissians.billing.utils.InternationalizationUtil;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
public class FileBrowserButtonClickHandler implements EventHandler<ActionEvent> {

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
