package com.nambissians.billing.model;

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
public class Tax {
    private String tag;
    private float percentage;
    private String description;
    private TaxHead taxHead;
    private Timestamp createDate;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tax{");
        sb.append("tag='").append(tag).append('\'');
        sb.append(", percentage=").append(percentage);
        sb.append(", description='").append(description).append('\'');
        sb.append(", taxHead=").append(taxHead);
        sb.append(", createDate=").append(createDate);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public TaxHead getTaxHead() {
        return taxHead;
    }

    public void setTaxHead(TaxHead taxHead) {
        this.taxHead = taxHead;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    private long id;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
