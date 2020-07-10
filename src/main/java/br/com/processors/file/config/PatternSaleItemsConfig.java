package br.com.processors.file.config;

public class PatternSaleItemsConfig {
    private String regex;
    private Integer groupId;
    private Integer groupQuantity;
    private Integer groupPrice;

    public String getRegex() {
        return regex;
    }

    public void setRegex(final String regex) {
        this.regex = regex;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(final Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupQuantity() {
        return groupQuantity;
    }

    public void setGroupQuantity(final Integer groupQuantity) {
        this.groupQuantity = groupQuantity;
    }

    public Integer getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(final Integer groupPrice) {
        this.groupPrice = groupPrice;
    }
}
