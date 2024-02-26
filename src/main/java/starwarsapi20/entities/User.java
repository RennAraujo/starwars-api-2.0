package starwarsapi20.entities;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private String idade;
    private String genero;
    private String Traidor;

    public User() {
    }

    public User(long id, String name, String idade, String genero, String traidor) {
        this.id = id;
        this.name = name;
        this.idade = idade;
        this.genero = genero;
        Traidor = traidor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTraidor() {
        return Traidor;
    }

    public void setTraidor(String traidor) {
        Traidor = traidor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() && Objects.equals(getTraidor(), user.getTraidor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTraidor());
    }
}
