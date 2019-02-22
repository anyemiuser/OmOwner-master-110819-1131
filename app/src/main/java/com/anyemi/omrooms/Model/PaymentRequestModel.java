package com.anyemi.omrooms.Model;

public class PaymentRequestModel {

    private String user_id;
    private String emi_ids;
    private String payment_type;
    private String serviceCharge;
    private String bankCharges;
    private String actualDueAmount;
    private String total_amount;
    private int checkno;
    private String bankname;
    private String branch;
    private String checkdate;
    private String rr_number;
    private String trsno;
    private String mobile_number;
    private String upi_id;
    private String assessment_id;
    private String credit_service_tax_;
    private String debit_service_tax_;
    private String gst_debit;
    private String gst_credit;
    private String payment_through;
    private String tax_type;
    private String extrafield;
    private String fine_amount;
    private String discount_amount;
    private String remarks;
    private String service_list_id;

    public PaymentRequestModel() {
    }

    public PaymentRequestModel(String user_id, String emi_ids, String actualDueAmount, String total_amount, String mobile_number, String discount_amount, String remarks) {
        this.user_id = user_id;
        this.emi_ids = emi_ids;
        this.actualDueAmount = actualDueAmount;
        this.total_amount = total_amount;
        this.mobile_number = mobile_number;
        this.discount_amount = discount_amount;
        this.remarks = remarks;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEmi_ids() {
        return emi_ids;
    }

    public void setEmi_ids(String emi_ids) {
        this.emi_ids = emi_ids;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public String getBankCharges() {
        return bankCharges;
    }

    public void setBankCharges(String bankCharges) {
        this.bankCharges = bankCharges;
    }

    public String getActualDueAmount() {
        return actualDueAmount;
    }

    public void setActualDueAmount(String actualDueAmount) {
        this.actualDueAmount = actualDueAmount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public int getCheckno() {
        return checkno;
    }

    public void setCheckno(int checkno) {
        this.checkno = checkno;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(String checkdate) {
        this.checkdate = checkdate;
    }

    public String getRr_number() {
        return rr_number;
    }

    public void setRr_number(String rr_number) {
        this.rr_number = rr_number;
    }

    public String getTrsno() {
        return trsno;
    }

    public void setTrsno(String trsno) {
        this.trsno = trsno;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getUpi_id() {
        return upi_id;
    }

    public void setUpi_id(String upi_id) {
        this.upi_id = upi_id;
    }

    public String getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(String assessment_id) {
        this.assessment_id = assessment_id;
    }

    public String getCredit_service_tax_() {
        return credit_service_tax_;
    }

    public void setCredit_service_tax_(String credit_service_tax_) {
        this.credit_service_tax_ = credit_service_tax_;
    }

    public String getDebit_service_tax_() {
        return debit_service_tax_;
    }

    public void setDebit_service_tax_(String debit_service_tax_) {
        this.debit_service_tax_ = debit_service_tax_;
    }

    public String getGst_debit() {
        return gst_debit;
    }

    public void setGst_debit(String gst_debit) {
        this.gst_debit = gst_debit;
    }

    public String getGst_credit() {
        return gst_credit;
    }

    public void setGst_credit(String gst_credit) {
        this.gst_credit = gst_credit;
    }

    public String getPayment_through() {
        return payment_through;
    }

    public void setPayment_through(String payment_through) {
        this.payment_through = payment_through;
    }

    public String getTax_type() {
        return tax_type;
    }

    public void setTax_type(String tax_type) {
        this.tax_type = tax_type;
    }

    public String getExtrafield() {
        return extrafield;
    }

    public void setExtrafield(String extrafield) {
        this.extrafield = extrafield;
    }

    public String getFine_amount() {
        return fine_amount;
    }

    public void setFine_amount(String fine_amount) {
        this.fine_amount = fine_amount;
    }

    public String getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(String discount_amount) {
        this.discount_amount = discount_amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getService_list_id() {
        return service_list_id;
    }

    public void setService_list_id(String service_list_id) {
        this.service_list_id = service_list_id;
    }
}