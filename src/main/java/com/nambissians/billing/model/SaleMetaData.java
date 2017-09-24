package com.nambissians.billing.model;/**
 * Created by SajiV on 10/09/17.
 */

import java.sql.Timestamp;

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
public class SaleMetaData {

    private Long state;
    private String vehicle;
    private String supplyPlace;
    private float totalAmount;
    private float totalTaxes;
    private String uuid;
    private String transportationMode;
    private Timestamp timestamp;
    private String customerName;
    private Long id;
    private String printableInvoiceNumber;
    private Boolean gstBill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getSupplyPlace() {
        return supplyPlace;
    }

    public void setSupplyPlace(String supplyPlace) {
        this.supplyPlace = supplyPlace;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getTotalTaxes() {
        return totalTaxes;
    }

    public void setTotalTaxes(float totalTaxes) {
        this.totalTaxes = totalTaxes;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTransportationMode() {
        return transportationMode;
    }

    public void setTransportationMode(String transportationMode) {
        this.transportationMode = transportationMode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPrintableInvoiceNumber() {
        return printableInvoiceNumber;
    }

    public void setPrintableInvoiceNumber(String printableInvoiceNumber) {
        this.printableInvoiceNumber = printableInvoiceNumber;
    }

    public Boolean isGstBill() {
        return gstBill;
    }

    public void setGstBill(Boolean gstBill) {
        this.gstBill = gstBill;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SaleMetaData{");
        sb.append("state=").append(state);
        sb.append(", vehicle='").append(vehicle).append('\'');
        sb.append(", supplyPlace='").append(supplyPlace).append('\'');
        sb.append(", totalAmount=").append(totalAmount);
        sb.append(", totalTaxes=").append(totalTaxes);
        sb.append(", uuid='").append(uuid).append('\'');
        sb.append(", transportationMode='").append(transportationMode).append('\'');
        sb.append(", timestamp=").append(timestamp);
        sb.append(", customerName='").append(customerName).append('\'');
        sb.append(", id=").append(id);
        sb.append(", printableInvoiceNumber='").append(printableInvoiceNumber).append('\'');
        sb.append(", gstBill=").append(gstBill);
        sb.append('}');
        return sb.toString();
    }
}
