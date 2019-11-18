package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.*;
import java.util.List;

/**
 * Created by LaunchCode
 */
@Entity
public class Cheese {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @NotNull
    @Size(min=1, message = "Description must not be empty")
    private String description;

    //private CheeseType type;
    @ManyToOne
    private Category category;

    @ManyToMany(mappedBy = "cheeses")
    private List<Menu> menus;

//    private int cheeseId;
//    private static int nextId = 1;

    public Cheese(String name, String description) {
//        this();
        this.name = name;
        this.description = description;
        //this.category = category;
    }

    public Cheese() {
//        cheeseId = nextId;
//        nextId++;
    }

    public int getId() {
        return id;
    }
    //
//    public int getCheeseId() {
//        return cheeseId;
//    }
//
//    public void setCheeseId(int cheeseId) {
//        this.cheeseId = cheeseId;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //    public CheeseType getType() {
//        return type;
//    }
//
//    public void setType(CheeseType type) {
//        this.type = type;
//    }
}
