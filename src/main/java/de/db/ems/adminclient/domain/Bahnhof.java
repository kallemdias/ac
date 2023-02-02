package de.db.ems.adminclient.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bahnhof.
 */
@Entity
@Table(name = "bahnhof")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Bahnhof implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bahnhofs_nr")
    private Integer bahnhofsNr;

    @Column(name = "bahnhofs_name")
    private String bahnhofsName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bahnhof id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBahnhofsNr() {
        return this.bahnhofsNr;
    }

    public Bahnhof bahnhofsNr(Integer bahnhofsNr) {
        this.setBahnhofsNr(bahnhofsNr);
        return this;
    }

    public void setBahnhofsNr(Integer bahnhofsNr) {
        this.bahnhofsNr = bahnhofsNr;
    }

    public String getBahnhofsName() {
        return this.bahnhofsName;
    }

    public Bahnhof bahnhofsName(String bahnhofsName) {
        this.setBahnhofsName(bahnhofsName);
        return this;
    }

    public void setBahnhofsName(String bahnhofsName) {
        this.bahnhofsName = bahnhofsName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bahnhof)) {
            return false;
        }
        return id != null && id.equals(((Bahnhof) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bahnhof{" +
            "id=" + getId() +
            ", bahnhofsNr=" + getBahnhofsNr() +
            ", bahnhofsName='" + getBahnhofsName() + "'" +
            "}";
    }
}
