package com.edebelzaakso.sinezya.Models;

public class Blog {
    private String IMAGE;
    private String likke;
    private String uid;
    private String identificador;

    public Blog(String IMAGE, String likke, String uid, String identificador) {
        this.IMAGE = IMAGE;
        this.likke = likke;
        this.uid = uid;
        this.identificador = identificador;
    }

    public Blog() {

    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getLikke() {
        return likke;
    }

    public void setLikke(String likke) {
        this.likke = likke;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

}
