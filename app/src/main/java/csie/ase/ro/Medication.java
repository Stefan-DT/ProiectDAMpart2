package csie.ase.ro;

public class Medication {
    private String id;
    private String name;
    private String dosage;
    private String instructions;

    public Medication() {}

    public Medication(String name, String dosage, String instructions) {
        this.name = name;
        this.dosage = dosage;
        this.instructions = instructions;
    }

    public String getId() {
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

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }


    @Override
    public String toString() {
        return "Medication{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dosage='" + dosage + '\'' +
                ", instructions='" + instructions + '\'' +
                '}';
    }
}
