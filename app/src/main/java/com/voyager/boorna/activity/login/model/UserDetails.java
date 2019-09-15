package com.voyager.boorna.activity.login.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rimon on 18-03-2018.
 */

public class UserDetails implements IUserDetials, Parcelable {


    /**
     * franchise_id :
     * user_id : 1183
     * company_name :
     * domain_name :
     * logo :
     * email :
     * contact_num :
     * inv_address :
     * inv_town :
     * inv_country_id :
     * inv_county :
     * inv_postcode :
     * inv_person :
     * inv_telephone :
     * inv_email :
     * off_address :
     * off_town :
     * off_country_id :
     * off_county :
     * off_postcode :
     * reg_number :
     * language_id :
     * language_type :
     * add_ip :
     * add_id :
     * add_dt :
     * validto_date :
     * status :
     * user_addip :
     * user_status : 0
     * user_employee_name : Dean
     * level_code : 1130
     * level_type : TRANSPORT DRIVER
     * userlevelid : 5
     * login_status : success
     * message : You have succesfully logged in.!!
     */





    private String franchise_id;
    private int user_id;
    private String company_name;
    private String domain_name;
    private String logo;
    private String email;
    private String contact_num;
    private String inv_address;
    private String inv_town;
    private String inv_country_id;
    private String inv_county;
    private String inv_postcode;
    private String inv_person;
    private String inv_telephone;
    private String inv_email;
    private String off_address;
    private String off_town;
    private String off_country_id;
    private String off_county;
    private String off_postcode;
    private String reg_number;
    private String language_id;
    private String language_type;
    private String add_ip;
    private String add_id;
    private String add_dt;
    private String validto_date;
    private String status;
    private String user_addip;
    private String fcm;
    private String password;
    private int user_status;
    private String user_employee_name;
    private String level_code;
    private String level_type;
    private int userlevelid;
    private String login_status;
    private String message;

    /**
     * vehicle_id : 5889
     * user_email : driver2@boorna.com
     */

    private int vehicle_id;
    private String user_email;
    /**
     * homepage_picture :
     * scripts_ganalytics :
     * scripts_facepixel :
     * scripts_tawk :
     * boorna_vatrate_applied :
     */

    private String homepage_picture;
    private String scripts_ganalytics;
    private String scripts_facepixel;
    private String scripts_tawk;
    private String boorna_vatrate_applied;


    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public int checkUserValidity(String name, String passwd) {
        if (email==null||passwd==null){
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

    public String getFranchise_id() {
        return franchise_id;
    }

    public void setFranchise_id(String franchise_id) {
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

    public String getInv_country_id() {
        return inv_country_id;
    }

    public void setInv_country_id(String inv_country_id) {
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

    public String getInv_person() {
        return inv_person;
    }

    public void setInv_person(String inv_person) {
        this.inv_person = inv_person;
    }

    public String getInv_telephone() {
        return inv_telephone;
    }

    public void setInv_telephone(String inv_telephone) {
        this.inv_telephone = inv_telephone;
    }

    public String getInv_email() {
        return inv_email;
    }

    public void setInv_email(String inv_email) {
        this.inv_email = inv_email;
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

    public String getOff_country_id() {
        return off_country_id;
    }

    public void setOff_country_id(String off_country_id) {
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

    public String getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }

    public String getLanguage_type() {
        return language_type;
    }

    public void setLanguage_type(String language_type) {
        this.language_type = language_type;
    }

    public String getAdd_ip() {
        return add_ip;
    }

    public void setAdd_ip(String add_ip) {
        this.add_ip = add_ip;
    }

    public String getAdd_id() {
        return add_id;
    }

    public void setAdd_id(String add_id) {
        this.add_id = add_id;
    }

    public String getAdd_dt() {
        return add_dt;
    }

    public void setAdd_dt(String add_dt) {
        this.add_dt = add_dt;
    }

    public String getValidto_date() {
        return validto_date;
    }

    public void setValidto_date(String validto_date) {
        this.validto_date = validto_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getUser_employee_name() {
        return user_employee_name;
    }

    public void setUser_employee_name(String user_employee_name) {
        this.user_employee_name = user_employee_name;
    }

    public String getLevel_code() {
        return level_code;
    }

    public void setLevel_code(String level_code) {
        this.level_code = level_code;
    }

    public String getLevel_type() {
        return level_type;
    }

    public void setLevel_type(String level_type) {
        this.level_type = level_type;
    }

    public int getUserlevelid() {
        return userlevelid;
    }

    public void setUserlevelid(int userlevelid) {
        this.userlevelid = userlevelid;
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



    public UserDetails() {
    }



    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.franchise_id);
        dest.writeInt(this.user_id);
        dest.writeString(this.company_name);
        dest.writeString(this.domain_name);
        dest.writeString(this.logo);
        dest.writeString(this.email);
        dest.writeString(this.contact_num);
        dest.writeString(this.inv_address);
        dest.writeString(this.inv_town);
        dest.writeString(this.inv_country_id);
        dest.writeString(this.inv_county);
        dest.writeString(this.inv_postcode);
        dest.writeString(this.inv_person);
        dest.writeString(this.inv_telephone);
        dest.writeString(this.inv_email);
        dest.writeString(this.off_address);
        dest.writeString(this.off_town);
        dest.writeString(this.off_country_id);
        dest.writeString(this.off_county);
        dest.writeString(this.off_postcode);
        dest.writeString(this.reg_number);
        dest.writeString(this.language_id);
        dest.writeString(this.language_type);
        dest.writeString(this.add_ip);
        dest.writeString(this.add_id);
        dest.writeString(this.add_dt);
        dest.writeString(this.validto_date);
        dest.writeString(this.status);
        dest.writeString(this.user_addip);
        dest.writeString(this.fcm);
        dest.writeString(this.password);
        dest.writeInt(this.user_status);
        dest.writeString(this.user_employee_name);
        dest.writeString(this.level_code);
        dest.writeString(this.level_type);
        dest.writeInt(this.userlevelid);
        dest.writeString(this.login_status);
        dest.writeString(this.message);
        dest.writeInt(this.vehicle_id);
        dest.writeString(this.user_email);
    }

    protected UserDetails(Parcel in) {
        this.franchise_id = in.readString();
        this.user_id = in.readInt();
        this.company_name = in.readString();
        this.domain_name = in.readString();
        this.logo = in.readString();
        this.email = in.readString();
        this.contact_num = in.readString();
        this.inv_address = in.readString();
        this.inv_town = in.readString();
        this.inv_country_id = in.readString();
        this.inv_county = in.readString();
        this.inv_postcode = in.readString();
        this.inv_person = in.readString();
        this.inv_telephone = in.readString();
        this.inv_email = in.readString();
        this.off_address = in.readString();
        this.off_town = in.readString();
        this.off_country_id = in.readString();
        this.off_county = in.readString();
        this.off_postcode = in.readString();
        this.reg_number = in.readString();
        this.language_id = in.readString();
        this.language_type = in.readString();
        this.add_ip = in.readString();
        this.add_id = in.readString();
        this.add_dt = in.readString();
        this.validto_date = in.readString();
        this.status = in.readString();
        this.user_addip = in.readString();
        this.fcm = in.readString();
        this.password = in.readString();
        this.user_status = in.readInt();
        this.user_employee_name = in.readString();
        this.level_code = in.readString();
        this.level_type = in.readString();
        this.userlevelid = in.readInt();
        this.login_status = in.readString();
        this.message = in.readString();
        this.vehicle_id = in.readInt();
        this.user_email = in.readString();
    }

    public static final Creator<UserDetails> CREATOR = new Creator<UserDetails>() {
        @Override
        public UserDetails createFromParcel(Parcel source) {
            return new UserDetails(source);
        }

        @Override
        public UserDetails[] newArray(int size) {
            return new UserDetails[size];
        }
    };

    public String getHomepage_picture() {
        return homepage_picture;
    }

    public void setHomepage_picture(String homepage_picture) {
        this.homepage_picture = homepage_picture;
    }

    public String getScripts_ganalytics() {
        return scripts_ganalytics;
    }

    public void setScripts_ganalytics(String scripts_ganalytics) {
        this.scripts_ganalytics = scripts_ganalytics;
    }

    public String getScripts_facepixel() {
        return scripts_facepixel;
    }

    public void setScripts_facepixel(String scripts_facepixel) {
        this.scripts_facepixel = scripts_facepixel;
    }

    public String getScripts_tawk() {
        return scripts_tawk;
    }

    public void setScripts_tawk(String scripts_tawk) {
        this.scripts_tawk = scripts_tawk;
    }

    public String getBoorna_vatrate_applied() {
        return boorna_vatrate_applied;
    }

    public void setBoorna_vatrate_applied(String boorna_vatrate_applied) {
        this.boorna_vatrate_applied = boorna_vatrate_applied;
    }
}
