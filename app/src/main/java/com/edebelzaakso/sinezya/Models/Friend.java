package com.edebelzaakso.sinezya.Models;

public class Friend {
    private String IMAGE;
    private String uid;
    private String identificador;
    private String keys;
    private String Smail;
    private String eu_voce;

    public Friend(String IMAGE, String uid, String identificador, String keys, String Smail, String eu_voce) {
        this.IMAGE = IMAGE;
        this.uid = uid;
        this.identificador = identificador;
        this.keys = keys;
        this.Smail = Smail;
        this.eu_voce = eu_voce;
    }

    public Friend() {

    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
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

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getSmail() {
        return Smail;
    }

    public void setSmail(String smail) {
        Smail = smail;
    }

    public String getEu_voce() {
        return eu_voce;
    }

    public void setEu_voce(String eu_voce) {
        this.eu_voce = eu_voce;
    }
}
