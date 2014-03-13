package com.augmentum.ams.web.vo.common;

public class LabelAndValue {

    private String value;
    private String label;

    public LabelAndValue() {

    }

    public LabelAndValue(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
