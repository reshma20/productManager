package com.nambissians.billing.utils;/**
 * Created by SajiV on 13/09/17.
 */

import com.nambissians.billing.model.InvoicePrint;
import com.nambissians.billing.model.OwnerProfile;
import com.nambissians.billing.model.Product;
import com.nambissians.billing.model.SaleRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.security.acl.Owner;
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
public class PrintUtils {

    private static Logger logger = LoggerFactory.getLogger(PrintUtils.class);
    public static void printInvoice(SaleRecord saleRecord, OwnerProfile ownerProfile, List<Product> products) {
        PrinterJob job = PrinterJob.getPrinterJob();
        InvoicePrint printData = new InvoicePrint(saleRecord, ownerProfile, products);
        job.setPrintable(printData);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
              logger.error("Couldnt complete the print job for sr : {}", saleRecord);
              NotificationUtils.showError(Constants.ERR_PRINTING_INVOICE);
            }
        }
    }
}
