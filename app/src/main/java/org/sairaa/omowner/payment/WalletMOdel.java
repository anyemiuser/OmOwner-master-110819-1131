package org.sairaa.omowner.payment;

public class WalletMOdel {


    /**
     * wallet_amount : 100000.00
     * status : Success
     * msg : Successfully send
     */

    private String wallet_amount;
    private String status;
    private String msg;

    public String getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(String wallet_amount) {
        this.wallet_amount = wallet_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
