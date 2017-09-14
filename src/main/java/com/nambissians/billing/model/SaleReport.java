package com.nambissians.billing.model;/**
 * Created by SajiV on 09/09/17.
 */

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
public class SaleReport {
    /**
     * Product identifier
     */
    private long productId;
    /**
     * Product name
     */
    private String productName;
    /**
     * Base price per unit
     */
    private float price;
    /**
     * Quantity ordered
     */
    private long quantity;
    /**
     * Cost of  (Base price per unit) * (Quantity ordered)
     */
    private float amount;
    /**
     * Percentage of rebate
     */
    private float rebate;
    /**
     * Taxable amount = amount (1-(rebate/100))
     */
    private float taxableAmount;
    /**
     * Amount of cgst
     */
    private float cgst;
    /**
     * Amount of sgst
     */
    private float sgst;
    /**
     * finalAmount = Taxable Amount + csgst + sgst
     */
    private float finalAmount;

    /**
     * Unique identifier of invoice
     */
    private String billUUID;

    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getRebate() {
        return rebate;
    }

    public void setRebate(float rebate) {
        this.rebate = rebate;
    }

    public float getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(float taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public float getCgst() {
        return cgst;
    }

    public void setCgst(float cgst) {
        this.cgst = cgst;
    }

    public float getSgst() {
        return sgst;
    }

    public void setSgst(float sgst) {
        this.sgst = sgst;
    }

    public float getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(float finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getBillUUID() {
        return billUUID;
    }

    public void setBillUUID(String billUUID) {
        this.billUUID = billUUID;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SaleReport{");
        sb.append("productId=").append(productId);
        sb.append(", productName='").append(productName).append('\'');
        sb.append(", price=").append(price);
        sb.append(", quantity=").append(quantity);
        sb.append(", amount=").append(amount);
        sb.append(", rebate=").append(rebate);
        sb.append(", taxableAmount=").append(taxableAmount);
        sb.append(", cgst=").append(cgst);
        sb.append(", sgst=").append(sgst);
        sb.append(", finalAmount=").append(finalAmount);
        sb.append(", billUUID='").append(billUUID).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
