package database;

/**
 * 数据修改类型
 */

public enum ChangeType {

    country,university,school,program_name,homepage,location,email,phone_number,degree,deadline_with_aid,deadline_without_aid;

    public int getType(){
        switch (this){
            case country:return 2;
            case university:return 3;
            case school:return 4;
            case program_name:return 5;
            case homepage:return 6;
            case location:return 7;
            case email:return 8;
            case phone_number:return 9;
            case degree:return 10;
            case deadline_with_aid:return 11;
            case deadline_without_aid:return 12;
            default:return 0;
        }
    }
    public static ChangeType setType(int i){
        switch (i){
            case 2:return country;
            case 3:return university;
            case 4:return school;
            case 5:return program_name;
            case 6:return homepage;
            case 7:return location;
            case 8:return email;
            case 9:return phone_number;
            case 10:return degree;
            case 11:return deadline_with_aid;
            case 12:return deadline_without_aid;
            default:return null;
        }
    }
}
