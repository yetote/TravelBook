package model;

/**
 * Created by TF on 2017/5/28.
 */

public class PlanNameModel {
    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PlanNameModel(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
