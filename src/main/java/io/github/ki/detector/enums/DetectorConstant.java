package io.github.ki.detector.enums;

public enum DetectorConstant {
    PREFIX("ki.detector"),
    HIBERNATE("hibernate-ddlauto-deactive");

    private String value;
    DetectorConstant(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public String getProperty(){
        return PREFIX.value.concat(".").concat(value);

    }
}
