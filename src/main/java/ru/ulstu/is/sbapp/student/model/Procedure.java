package ru.ulstu.is.sbapp.student.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Procedure {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column()
    private String procedureName;
    private String fioMaster;
    private int price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "procedurs_visits",
            joinColumns = @JoinColumn(name = "procedur_fk"),
            inverseJoinColumns = @JoinColumn(name = "visit_fk"))
    private List<Visit> visits = new ArrayList<>();

    public Procedure() {

    }

    public Procedure(String procedureName, String fioMaster, int price) {
        this.procedureName = procedureName;
        this.fioMaster = fioMaster;
        this.price = price;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public String getFioMaster() {
        return fioMaster;
    }

    public void setFioMaster(String fioMaster) {
        this.fioMaster = fioMaster;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;

    }

    public void addVisits(Visit visit) {
        visits.add(visit);
        if (!visit.getProcedurs().contains(this)) {
            visit.addProcedure(this);
        }
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Procedure procedure = (Procedure) o;
        return price == procedure.price && Objects.equals(id, procedure.id)
                && Objects.equals(procedureName, procedure.procedureName)
                && Objects.equals(fioMaster, procedure.fioMaster) && Objects.equals(visits, procedure.visits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, procedureName, fioMaster, price, visits);
    }
}
