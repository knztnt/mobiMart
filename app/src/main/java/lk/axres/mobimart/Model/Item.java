package lk.axres.mobimart.Model;

public class Item {
    String id;
    String unit;
    String lowest;
    String name;
    String profilePic;
    int amount;

    public Item(String id, String unit, String name, String profilePic, int amount) {
        this.id = id;
        this.unit = unit;
        this.name = name;
        this.profilePic = profilePic;
        this.amount = amount;
    }

    public Item(String id, String unit, String lowest, String name, String profilePic, int amount) {
        this.id = id;
        this.unit = unit;
        this.lowest = lowest;
        this.name = name;
        this.profilePic = profilePic;
        this.amount = amount;
    }

    public Item() {
    }

    public String getLowest() {
        return lowest;
    }

    public void setLowest(String lowest) {
        this.lowest = lowest;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Item(String id, String unit, String name, String profilePic) {
        this.id = id;
        this.unit = unit;
        this.name = name;
        this.profilePic = profilePic;
    }

    public Item(String id, String name, String profilePic) {
        this.id = id;
        this.name = name;
        this.profilePic = profilePic;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
