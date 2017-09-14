package com.nambissians.billing.utils;

import java.util.Locale;
import java.util.ResourceBundle;
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

public class InternationalizationUtil {

    private static ResourceBundle resourceBundle;

    private InternationalizationUtil () {
        //Private constructor for singleton instance
    }

    private static final ResourceBundle getResourceBundle() {
        if(resourceBundle == null) {
            synchronized (InternationalizationUtil.class) {
                //Double lock to ensure single instance to avoid multi thread issues in future
                if(resourceBundle == null) {
                    Locale locale = Locale.getDefault();
                    resourceBundle = ResourceBundle.getBundle("resource", locale);
                }
            }
        }
        return resourceBundle;
    }

    public static final String getString(String str) {
        return getResourceBundle().getString(str);
    }

}
