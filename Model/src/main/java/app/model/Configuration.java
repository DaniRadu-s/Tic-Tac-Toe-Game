package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "Configurations")
public class Configuration extends Entity<Long>{
    private Integer b1;
    private Integer b2;
    private Integer b3;

    public Configuration() {}
    public Configuration(Integer b1, Integer b2, Integer b3) {
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
    }

    @Column(name="b1")
    public Integer getB1() {
        return b1;
    }

    public void setB1(Integer b1) {
        this.b1 = b1;
    }

    @Column(name="b2")
    public Integer getB2() {
        return b2;
    }

    public void setB2(Integer b2) {
        this.b2 = b2;
    }

    @Column(name="b3")
    public Integer getB3() {
        return b3;
    }

    public void setB3(Integer b3) {
        this.b3 = b3;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "b1=" + b1 +
                ", b2=" + b2 +
                ", b3=" + b3 +
                '}';
    }
}
