package csie.ase.ro;

import java.io.Serializable;

public class Ajutor implements Serializable {
    private String numeAjutor;
    private String descriereAjutor;


    public Ajutor(String numeAjutor, String descriereAjutor) {
        this.numeAjutor = numeAjutor;
        this.descriereAjutor = descriereAjutor;
    }

    public String getNumeAjutor() {
        return numeAjutor;
    }

    public void setNumeAjutor(String numeAjutor) {
        this.numeAjutor = numeAjutor;
    }

    public String getDescriereAjutor() {
        return descriereAjutor;
    }

    public void setDescriereAjutor(String descriereAjutor) {
        this.descriereAjutor = descriereAjutor;
    }

    @Override
    public String toString() {
        return "Ajutor{" +
                "numeAjutor='" + numeAjutor + '\'' +
                ", descriereAjutor='" + descriereAjutor + '\'' +
                '}';
    }


}
