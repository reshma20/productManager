package com.nambissians.billing.model;/**
 * Created by SajiV on 12/09/17.
 */

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
public class SaleRecord {
    private SaleMetaData saleMetaData;
    private List<SaleReport> breakUp;
    private Customer customer;

    public SaleMetaData getSaleMetaData() {
        return saleMetaData;
    }

    public void setSaleMetaData(SaleMetaData saleMetaData) {
        this.saleMetaData = saleMetaData;
    }

    public List<SaleReport> getBreakUp() {
        return breakUp;
    }

    public void setBreakUp(List<SaleReport> breakUp) {
        this.breakUp = breakUp;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SaleRecord{");
        sb.append("saleMetaData=").append(saleMetaData);
        sb.append(", breakUp=").append(breakUp);
        sb.append(", customer=").append(customer);
        sb.append('}');
        return sb.toString();
    }
}
