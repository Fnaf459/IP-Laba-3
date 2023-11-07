package ru.ulstu.is.sbapp.student.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column()
    private String date;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToMany(mappedBy = "visits")
    private List<Procedure> procedurs = new ArrayList<>();

    public Visit() {

    }

    public Visit(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setClient(Client client) {
        this.client = client;
        if (!client.getVisits().contains(this)) {
            client.getVisits().add(this);
        }
    }

    public Client getClient() {
        return client;
    }

    public List<Procedure> getProcedurs() {
        return procedurs;
    }

    public void addProcedure(Procedure procedure){
        procedurs.add(procedure);
        if (!procedure.getVisits().contains(this)) {
            procedure.addVisits(this);
        }
    }

    public void setProcedurs(List<Procedure> procedurs) {
        this.procedurs = procedurs;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return Objects.equals(id, visit.id) && Objects.equals(date, visit.date)
                && Objects.equals(client, visit.client) && Objects.equals(procedurs, visit.procedurs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, client, procedurs);
    }
}
