package com.enlight.demo.tmp;

import java.util.Objects;

public class Auto {

    private Long id;
    private String name;

    // kontsruktor (da se dodeli vrednost prilikom kreiranja instance)
    public Auto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // getteri - za iscitavanje vrednosti polja
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // setteri - da se naknadno izmeni/dodeli vrednost
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    // poredimo samo po id
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Auto auto)) return false;
        return Objects.equals(id, auto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    // za lepsi ispis u konzoli
    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
