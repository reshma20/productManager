package com.nambissians.billing.utils;

import java.text.SimpleDateFormat;

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

public interface Constants {

    String TITLE = "app_name";
    String TAHOMA_FONT = "Tahoma";
    String CANCEL = "cancel";
    String SAVE = "save";
    String ID = "id";
    String TAG = "tag";
    String DESCRIPTION = "description";
    String PERCENTAGE = "percentage";
    String CREATE_DATE = "createDate";
    String HSN_CODE = "hsn_code";
    String HSN_CODE_LABEL = "hsnCode";
    String TAXES = "taxes";
    String PRICE = "price";
    String EMPTY_STRING = "";
    String NEW = "new";
    String EDIT = "edit";
    String LIST = "list";
    String PRODUCT = "product";
    String TAX_SEPARATOR = ",\n";
    String APPLICABLE_TAXES = "applicableTaxes";
    String CUSTOMER = "customer";
    String NAME = "customerName";
    String NAME_FIELD = "name";
    String ADDRESS = "address";
    String GSTIN = "gstin";
    String TEL = "tel";
    String EMAIL = "email";
    String SALES = "sales";
    String EXISTING_CUSTOMER = "existingCustomer";
    String NEW_CUSTOMER = "newCustomer";
    String PROFILE = "profile";
    String COMPANY_NAME = "companyName";
    String MOBILE = "mob";
    String BROWSE = "browse";
    String LOGO = "logo";
    String SEARCH_BY="searchBy";
    String SEARCH = "search";
    String ERR_NON_NUMERIC_ID="ERR_NON_NUMERIC_ID";
    String ERR_SEACH_MECHANISM_NOT_IMPLEMENTED="ERR_SEACH_MECHANISM_NOT_IMPLEMENTED";
    String ERR_NO_MATCHING_CUSTOMER="ERR_NO_MATCHING_CUSTOMER";
    String ERR_COULD_NOT_GET_CUSTOMER_LIST="ERR_COULD_NOT_GET_CUSTOMER_LIST";
    String ERR_COULD_NOT_GET_PRODUCT_LIST="ERR_COULD_NOT_GET_PRODUCT_LIST";
    String MSG_SAVED_TAX_SUCCESSFULLY="MSG_SAVED_TAX_SUCCESSFULLY";
    String ERR_COULD_NOT_SAVE_TAX_DETAIL="ERR_COULD_NOT_SAVE_TAX_DETAIL";
    String ERR_COULD_NOT_SAVE_PRODUCT_DETAIL="ERR_COULD_NOT_SAVE_PRODUCT_DETAIL";
    String MSG_SAVED_PRODUCT_SUCCESSFULLY="MSG_SAVED_PRODUCT_SUCCESSFULLY";
    String ERR_QUANTITY_NOT_VALID = "ERR_QUANTITY_NOT_VALID";
    String ERR_COULD_NOT_SAVE_CUSTOMER = "ERR_COULD_NOT_SAVE_CUSTOMER";
    String ERR_COULD_NOT_SAVE_INVOICE = "ERR_COULD_NOT_SAVE_INVOICE";
    String ERR_COULD_NOT_GENERATE_INVOICE = "ERR_COULD_NOT_GENERATE_INVOICE";
    String ERR_COULD_NOT_GET_SALES_REPORT = "ERR_COULD_NOT_GET_SALES_REPORT";
    String ERR_NO_PRODUCT_SELECTED_FOR_INVOICE = "ERR_NO_PRODUCT_SELECTED";
    String ERR_COULD_NOT_GENERATE_REPORT = "ERR_COULD_NOT_GENERATE_REPORT";
    String ERR_GEN_INVOICE = "ERR_GEN_INVOICE";
    String ERR_PRINTING_INVOICE = "ERR_PRINTING_INVOICE";
    String ERR_REBATE_NOT_VALID = "ERR_REBATE_NOT_VALID";
    String MSG_SAVED_CUSTOMER="MSG_SAVED_CUSTOMER";
    String MSG_PROFILE_SAVED="MSG_PROFILE_SAVED";
    String ERR_PROFILE_SAVED="ERR_PROFILE_SAVED";
    String MSG_SAVED_INVOICE = "MSG_SAVED_INVOICE";
    String SELECT_CUSTOMER ="selectCustomer";
    String SELECT_PRODUCT ="selectProduct";
    String SELECTED_CUSTOMER="selectedCustomer";
    String REBATE="rebate";
    String QUANTITY="quantity";
    String WILD_CARD_FOR_ANY = "%";
    String ERR_NO_MATCHING_PRODUCT="ERR_NO_MATCHING_PRODUCT";
    String PRODUCT_TAG="productTag";
    int TITLED_HEIGHT = 600;
    int TITLED_WIDTH = 1200;
    int SALE_REPORT_HEIGHT=175;
    String P_CODE ="productId";
    String P_NAME ="productName";
    String AMOUNT = "amount";
    String TAXABLE_AMOUNT = "taxableAmount";
    String CGST = "cgst";
    String SGST = "sgst";
    String FINAL_AMOUNT = "finalAmount";
    String COST = "cost";
    String TAX_HEAD = "taxHead";
    String ADD = "add";
    String ZERO="0.00";
    String ONE="1";
    String GENERATE_INVOICE ="generateInvoice";
    String PRINT_AND_SAVE_INVOICE="printAndSaveInvoice";
    String CLEAR = "clear";
    String STATE = "state";
    String DEFAULT_STATE="default_state";
    String VEHICLE="vehicle";
    String SUPPLY_PLACE="supplyPlace";
    String SERIF_FONT = "Serif";
    String EMAIL_STR="Email: ";
    String TEL_STR="Tel: ";
    String MOBILE_STR = "Mob: ";
    String GST_IN_STR = "GST IN ";
    String GST_IN_CUSTOMER_STR = "GST IN: ";
    String CUSTOMER_NAME= "Name: ";
    String ADDRESS_STR = "Address: ";
    String INVOICE_TIME = "Sales Time: ";
    int LOGO_WIDTH=90;
    int LOGO_HEIGHT=120;
    int LEFT_MARGIN = 20;
    int TOP_MARGIN =60;
    int LOGO_BUFFER_SPACE = 10;
    int COMPANY_NAME_TEXT_HEIGHT=20;
    int AMOUNT_HEIGHT = 20;
    int COMPANY_NAME_FONT_SIZE = 20;
    int COMPANY_ADDRESS_FONT_SIZE = 15;
    int INVOICE_FONT_HEIGHT =15;
    int INVOICE_BREAKUP_HEIGHT = 15;
    int INVOICE_WIDTH = 1000;
    int COMPANY_NAME_LOWER_X = LEFT_MARGIN +LOGO_WIDTH+LOGO_BUFFER_SPACE;
    int COMPANY_NAME_Y = TOP_MARGIN +COMPANY_NAME_TEXT_HEIGHT;
    int COMPANY_ADDRESS_Y = COMPANY_NAME_Y+COMPANY_NAME_TEXT_HEIGHT;
    int COMPANY_EMAIL_Y = COMPANY_ADDRESS_Y+ INVOICE_FONT_HEIGHT;
    int INVOICE_CUSTOMER_SPACE = 100;
    String LINE_FEED_CHAR = "\n";
    SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String SNO = "SNo";
    String CODE = "Code";
    String DESCRIPTION_TAG = "Description";
    String PRICE_TAG = "Price";
    String GST_INVOICE = "GST INVOICE";
    String GST_BILL = "GST BILL";
    int TITLE_SPACE_Y = 50;
    int TITLE_SPACE_X = 50;
    String INVOICE_NUMBER = "Invoice Number : ";
    String COMMA = ",";
    String CUSTOMER_DETAILS = "Customer Details";
    String DATE_STR = "Date and Time: ";
    String TRANSPORTATION_MODE = "Trasportation Mode: ";
    String VEHICLE_STR = "Vehicle: ";
    String SUPPLY_PLACE_STR = "Supply Place: ";
    String STATE_STR = "State :";
    String HRS = " hrs";
    String QUANTITY_STR ="Quantity";
    String AMOUNT_STR = "Amount";
    String REBATE_STR = "Rebate";
    String TAXABLE_AMOUNT_STR = "Taxable Amount";
    String TAXES_STR = "Taxes";
    String CGST_STR = "CGST";
    String SGST_STR = "SGST";
    String FINAL_STR = "Total";
    String TOTAL_TAX_COLLECTED="%-17s : %12.2f";
    String TOTAL_TAX = "totalTaxes";
    String TOTAL_BILL_AMOUNT="%-17s : %12.2f";
    String TOTAL_BILL = "Total Bill Amount";
    String AUTHORISED_SIGNATORY = "Authorised Signatory";
    String GST_INVOICE_INITIAL = "A";
    String GST_BILL_INITIAL = "B";
    String PRINTABLE_INVOICE_FORMAT = "%s%06d";
    String TRANSPORTATION_MODE_LABEL="transportationMode";
    String FROM_DATE = "fromDate";
    String TO_DATE = "toDate";
    String TIMESTAMP = "timestamp";
    String Export = "export";
    String TRANSACTION_DATE = "transactionDate";
    String TOTAL_AMOUNT = "totalAmount";
    String TOTAL_TAXES_STR = "Total Taxes";
    String HSN_CODE_STR = "HSN";
    String PRINTABLE_INVOICE_NUMBER = "printableInvoiceNumber";
    String ERR_COULD_NOT_GET_RECORD = "ERR_COULD_NOT_GET_RECORD";
    String ERR_NOT_VALID_INVOICE_NUM = "ERR_NOT_VALID_INVOICE_NUM";
    String ERR_COULD_NOT_UPDATE_SALERECORD = "ERR_COULD_NOT_UPDATE_SALERECORD";
    String ERR_NO_APP_TO_OPEN = "ERR_NO_APP_TO_OPEN";
    String REMOVE = "remove";
    String LOG4J_CONFIGURATION_FILE = "log4j.configuration";
}
