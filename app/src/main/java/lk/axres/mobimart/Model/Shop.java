package lk.axres.mobimart.Model;

public class Shop {

    String id;
    double bamount;
    String name;
    String dist;
    String lati;
    String longi;
    String dp;

    public Shop() {
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

    public Shop(String id, double bamount, String name, String dist, String lati, String longi, String dp) {
        this.id = id;
        this.bamount = bamount;
        this.name = name;
        this.dist = dist;
        this.lati = lati;
        this.longi = longi;
        this.dp = dp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBamount() {
        return bamount;
    }

    public void setBamount(double bamount) {
        this.bamount = bamount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }
}
