package com.simon.wang.soap;

public enum CalcType {
    PortCharges,
    PortRestrictions;

    private String value;
    private CalcType(){
        this.value = name();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
