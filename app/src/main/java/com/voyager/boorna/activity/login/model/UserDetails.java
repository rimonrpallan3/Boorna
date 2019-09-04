package com.voyager.boorna.activity.login.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rimon on 18-03-2018.
 */

public class UserDetails implements IUserDetials, Parcelable {

    /**
     * franchise_id : 1
     * user_id : 1
     * company_name : Demo
     * domain_name : demo1.boorna.com
     * logo : 123
     * email : demo1@boorna.com
     * contact_num : 92333343434
     * inv_address :
     * inv_town :
     * inv_country_id : 0
     * inv_county :
     * inv_postcode :
     * off_address :
     * off_town :
     * off_country_id : 0
     * off_county :
     * off_postcode :
     * reg_number :
     * vat_number :
     * vat_registered : no
     * eu_vat_registered : no
     * currency_id : 1
     * currency_position : prefix
     * language_id : 3
     * language_type : LTR
     * franchise_addip : null
     * created_by : null
     * created_date : 0000-00-00 00:00:00
     * status : 0
     * user_addip : 178.138.32.192
     * user_status : 0
     * user_name : catalin
     * user_employee_name : Catalin Chirila
     */

    private int franchise_id;
    private int user_id;
    private String company_name;
    private String domain_name;
    private String logo;
    private String email;
    private String login_status;
    private String password;
    private String fcmToken;
    private String contact_num;
    private String inv_address;
    private String inv_town;
    private int inv_country_id;
    private String inv_county;
    private String inv_postcode;
    private String off_address;
    private String off_town;
    private int off_country_id;
    private String off_county;
    private String off_postcode;
    private String reg_number;
    private String vat_number;
    private String vat_registered;
    private String eu_vat_registered;
    private int currency_id;
    private String currency_position;
    private int language_id;
    private String language_type;
    private String created_date;
    private int status;
    private String user_addip;
    private String message;
    private int user_status;
    private String user_name;
    private String user_employee_name;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public String getLogin_status() {
        return login_status;
    }

    public void setLogin_status(String login_status) {
        this.login_status = login_status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getFranchise_id() {
        return franchise_id;
    }

    public void setFranchise_id(int franchise_id) {
        this.franchise_id = franchise_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getDomain_name() {
        return domain_name;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_num() {
        return contact_num;
    }

    public void setContact_num(String contact_num) {
        this.contact_num = contact_num;
    }

    public String getInv_address() {
        return inv_address;
    }

    public void setInv_address(String inv_address) {
        this.inv_address = inv_address;
    }

    public String getInv_town() {
        return inv_town;
    }

    public void setInv_town(String inv_town) {
        this.inv_town = inv_town;
    }

    public int getInv_country_id() {
        return inv_country_id;
    }

    public void setInv_country_id(int inv_country_id) {
        this.inv_country_id = inv_country_id;
    }

    public String getInv_county() {
        return inv_county;
    }

    public void setInv_county(String inv_county) {
        this.inv_county = inv_county;
    }

    public String getInv_postcode() {
        return inv_postcode;
    }

    public void setInv_postcode(String inv_postcode) {
        this.inv_postcode = inv_postcode;
    }

    public String getOff_address() {
        return off_address;
    }

    public void setOff_address(String off_address) {
        this.off_address = off_address;
    }

    public String getOff_town() {
        return off_town;
    }

    public void setOff_town(String off_town) {
        this.off_town = off_town;
    }

    public int getOff_country_id() {
        return off_country_id;
    }

    public void setOff_country_id(int off_country_id) {
        this.off_country_id = off_country_id;
    }

    public String getOff_county() {
        return off_county;
    }

    public void setOff_county(String off_county) {
        this.off_county = off_county;
    }

    public String getOff_postcode() {
        return off_postcode;
    }

    public void setOff_postcode(String off_postcode) {
        this.off_postcode = off_postcode;
    }

    public String getReg_number() {
        return reg_number;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
    }

    public String getVat_number() {
        return vat_number;
    }

    public void setVat_number(String vat_number) {
        this.vat_number = vat_number;
    }

    public String getVat_registered() {
        return vat_registered;
    }

    public void setVat_registered(String vat_registered) {
        this.vat_registered = vat_registered;
    }

    public String getEu_vat_registered() {
        return eu_vat_registered;
    }

    public void setEu_vat_registered(String eu_vat_registered) {
        this.eu_vat_registered = eu_vat_registered;
    }

    public int getCurrency_id() {
        return currency_id;
    }

    public void setCurrency_id(int currency_id) {
        this.currency_id = currency_id;
    }

    public String getCurrency_position() {
        return currency_position;
    }

    public void setCurrency_position(String currency_position) {
        this.currency_position = currency_position;
    }

    public int getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(int language_id) {
        this.language_id = language_id;
    }

    public String getLanguage_type() {
        return language_type;
    }

    public void setLanguage_type(String language_type) {
        this.language_type = language_type;
    }


    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUser_addip() {
        return user_addip;
    }

    public void setUser_addip(String user_addip) {
        this.user_addip = user_addip;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_employee_name() {
        return user_employee_name;
    }

    public void setUser_employee_name(String user_employee_name) {
        this.user_employee_name = user_employee_name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.franchise_id);
        dest.writeInt(this.user_id);
        dest.writeString(this.company_name);
        dest.writeString(this.domain_name);
        dest.writeString(this.logo);
        dest.writeString(this.message);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.fcmToken);
        dest.writeString(this.contact_num);
        dest.writeString(this.inv_address);
        dest.writeString(this.inv_town);
        dest.writeString(this.login_status);
        dest.writeInt(this.inv_country_id);
        dest.writeString(this.inv_county);
        dest.writeString(this.inv_postcode);
        dest.writeString(this.off_address);
        dest.writeString(this.off_town);
        dest.writeInt(this.off_country_id);
        dest.writeString(this.off_county);
        dest.writeString(this.off_postcode);
        dest.writeString(this.reg_number);
        dest.writeString(this.vat_number);
        dest.writeString(this.vat_registered);
        dest.writeString(this.eu_vat_registered);
        dest.writeInt(this.currency_id);
        dest.writeString(this.currency_position);
        dest.writeInt(this.language_id);
        dest.writeString(this.language_type);
        dest.writeString(this.created_date);
        dest.writeInt(this.status);
        dest.writeString(this.user_addip);
        dest.writeInt(this.user_status);
        dest.writeString(this.user_name);
        dest.writeString(this.user_employee_name);
    }

    public UserDetails() {
    }

    protected UserDetails(Parcel in) {
        this.franchise_id = in.readInt();
        this.user_id = in.readInt();
        this.company_name = in.readString();
        this.domain_name = in.readString();
        this.login_status = in.readString();
        this.message = in.readString();
        this.logo = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.fcmToken = in.readString();
        this.contact_num = in.readString();
        this.inv_address = in.readString();
        this.inv_town = in.readString();
        this.inv_country_id = in.readInt();
        this.inv_county = in.readString();
        this.inv_postcode = in.readString();
        this.off_address = in.readString();
        this.off_town = in.readString();
        this.off_country_id = in.readInt();
        this.off_county = in.readString();
        this.off_postcode = in.readString();
        this.reg_number = in.readString();
        this.vat_number = in.readString();
        this.vat_registered = in.readString();
        this.eu_vat_registered = in.readString();
        this.currency_id = in.readInt();
        this.currency_position = in.readString();
        this.language_id = in.readInt();
        this.language_type = in.readString();
        this.created_date = in.readString();
        this.status = in.readInt();
        this.user_addip = in.readString();
        this.user_status = in.readInt();
        this.user_name = in.readString();
        this.user_employee_name = in.readString();
    }

    public static final Parcelable.Creator<UserDetails> CREATOR = new Parcelable.Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel source) {
            return new UserDetails(source);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };

    @Override
    public int checkUserValidity(String name, String passwd) {
        if (email==null||passwd==null||!email.equals(getEmail())||!passwd.equals(getPassword())){
            return -1;
        }
        return 0;
    }

    @Override
    public int validateLoginResponseError(String errorMsg) {
        if(!errorMsg.equals("success")){
            return -2;
        }
        return 0;
    }

}
