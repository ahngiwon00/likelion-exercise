package com.bigdata;

public class Hospital {
    private String id;
    private String address;
    private String category;
    private Integer emergencyRoom;
    private String name;
    private String subdivision=null;
    private String district;

    public Hospital(String id, String address,String category, String emergencyRoom, String name) {
        this.id = id;
        this.address = address;
        this.category = category;
        this.emergencyRoom = Integer.parseInt(emergencyRoom);
        this.name = name;
        this.setSubdivision();
        this.setDistrict();
    }





    private void setDistrict(){
        String[] splitted = this.address.split(" ");
        this.district = splitted[0]+" "+splitted[1];
    }

    private void setSubdivision(){
        String[] subdivisionList = new String[]{
                    "치과", "성형외과", "한방병원", "한의원", "영상의학과", "이비인후과", "소아청소년과", "내과", "정형외과", "외과",
                    "가정의학과","피부과", "안과", "소아과", "요양병원", "비뇨기과", "정신건강의학과", "산부인과", "재활의학과",
                    "정신과", "마취통증의학과"};
        for(String subdivision : subdivisionList){
            if(this.name.contains(subdivision)){
                this.subdivision=subdivision;
            }
        }
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getCategory() {
        return category;
    }

    public Integer getEmergencyRoom() {
        return emergencyRoom;
    }

    public String getName() {
        return name;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public String getDistrict() {
        return district;
    }

    /*INSERT INTO `likerlion-db`.`seoul_hospital`(`id`,`address`,`district`,`category`,`emergency_room`,`name`,`subdivision`)
    "VALUES ('" + this.id + "','" + this.address + "','" + this.district + "','" + this.category + "'," +this.emergencyRoom + ",'" + this.name + "',";
    이런식으로 sql파일 생성*/
    public String toSqlString(){
        String sql= "INSERT INTO `likerlion-db`.`seoul_hospital` (`id`,`address`,`district`,`category`,`emergency_room`,`name`,`subdivision`) \n" +
                "VALUES ('" + this.id + "','" + this.address + "','" + this.district + "','" + this.category + "'," +
                this.emergencyRoom + ",'" + this.name + "',";
        if(subdivision==null)
            return sql+this.subdivision+");\n";
        else
            return sql+"'"+this.subdivision+"');\n";
    }

}
