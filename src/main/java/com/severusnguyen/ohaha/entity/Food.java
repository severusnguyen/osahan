package com.severusnguyen.ohaha.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity(name = "food")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "image")
    private String image;

    @Column(name = "time_ship")
    private String timeShip;

    @Column(name = "price")
    private String price;

    @Column(name = "is_freeship")
    private boolean isFreeShip;

    @Column(name = "description")
    private String desc;

    @ManyToOne
    @JoinColumn(name = "cate_id")
    private Category category;

    @OneToMany(mappedBy = "food")
    private Set<RatingFood> listRatingFood;

    public Set<RatingFood> getListRatingFood() {
        return listRatingFood;
    }

    @OneToMany(mappedBy = "food")
    private Set<OrderItem> listOrderItem;

    public Set<OrderItem> getListOrderItem() {
        return listOrderItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTimeShip() {
        return timeShip;
    }

    public void setTimeShip(String timeShip) {
        this.timeShip = timeShip;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isFreeShip() {
        return isFreeShip;
    }

    public void setFreeShip(boolean freeShip) {
        isFreeShip = freeShip;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setListRatingFood(Set<RatingFood> listRatingFood) {
        this.listRatingFood = listRatingFood;
    }

    public void setListOrderItem(Set<OrderItem> listOrderItem) {
        this.listOrderItem = listOrderItem;
    }
}
