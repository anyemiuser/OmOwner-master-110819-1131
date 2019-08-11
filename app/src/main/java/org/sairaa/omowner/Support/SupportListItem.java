package org.sairaa.omowner.Support;

public class SupportListItem {

    public String helpdesk_number1;
    public String helpdesk_number2;
    public String helpdesk_number3;
    public String reconciliation;
    public String TechSupport;
    /*public String imageUrl;*/

    public SupportListItem(String helpdesk_number1, String helpdesk_number2, String helpdesk_number3, String reconciliation, String techSupport) {
        this.helpdesk_number1 = helpdesk_number1;
        this.helpdesk_number2 = helpdesk_number2;
        this.helpdesk_number3 = helpdesk_number3;
        this.reconciliation = reconciliation;
        TechSupport = techSupport;
    }

    public String getHelpdesk_number1() {
        return helpdesk_number1;
    }

    public void setHelpdesk_number1(String helpdesk_number1) {
        this.helpdesk_number1 = helpdesk_number1;
    }

    public String getHelpdesk_number2() {
        return helpdesk_number2;
    }

    public void setHelpdesk_number2(String helpdesk_number2) {
        this.helpdesk_number2 = helpdesk_number2;
    }

    public String getHelpdesk_number3() {
        return helpdesk_number3;
    }

    public void setHelpdesk_number3(String helpdesk_number3) {
        this.helpdesk_number3 = helpdesk_number3;
    }

    public String getReconciliation() {
        return reconciliation;
    }

    public void setReconciliation(String reconciliation) {
        this.reconciliation = reconciliation;
    }

    public String getTechSupport() {
        return TechSupport;
    }

    public void setTechSupport(String techSupport) {
        TechSupport = techSupport;
    }
}
