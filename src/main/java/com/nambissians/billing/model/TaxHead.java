package com.nambissians.billing.model;

/**
 * Created by SajiV on 09/09/17.
 */
public enum TaxHead {
    CGST, SGST, NONE;
    public static TaxHead getTaxHead(String head) {
        if(head == null) {
            return NONE;
        } else if (CGST.toString().equalsIgnoreCase(head.trim())) {
            return CGST;
        } else if (SGST.toString().equalsIgnoreCase(head.trim())) {
            return SGST;
        } else {
            return NONE;
        }
    }
}
