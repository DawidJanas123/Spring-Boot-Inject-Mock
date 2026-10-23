package com.example.animatedpotatorest.DTO;

import com.example.animatedpotatorest.DTO.Enums.Category;

public class SouvenirDTO {

    private String name;
    private Float price;
    private Integer quantity;
    private Category type;

    public SouvenirDTO() {
    }

    public SouvenirDTO(String name, Float price, Integer quantity, Category type) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Category getType() {
        return type;
    }

    public void setType(Category type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
