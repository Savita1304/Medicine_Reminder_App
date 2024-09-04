package com.vapps.medi.reminder.Models;

public class DataModel
{
    String id,name,age,height,weight,gender,authority;
//Bitmap bmp;

    //String profilepic;

byte[] bmp;
    public DataModel() {
    }

    /*public DataModel(String id, String name, String age, String height, String weight, String gender, String profilepic) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.profilepic = profilepic;
    }*/
    public DataModel(String id, String name, String age, String height, String weight, String gender, byte[] bmp,String authority) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.bmp = bmp;
        this.authority = authority;

    }

//    public Bitmap getBmp() {
//        return bmp;
//    }
//
//    public void setBmp(Bitmap bmp) {
//        this.bmp = bmp;
//    }


    public byte[] getBmp() {
        return bmp;
    }

    public void setBmp(byte[] bmp) {
        this.bmp = bmp;
    }

  /*  public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
