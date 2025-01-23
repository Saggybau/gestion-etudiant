import java.io.Serializable;

public class Etudiant implements Serializable {
    private String nom;
    private double hören;
    private double lessen;
    private double vokabular;
    private double grammatik;
    private double schreiben;
    private double sprechen;
    private double med;

    public Etudiant(String nom, double hören, double lessen, double vokabular, double grammatik, double schreiben, double sprechen, double med) {
        this.nom = nom;
        this.hören = hören;
        this.lessen = lessen;
        this.vokabular = vokabular;
        this.grammatik = grammatik;
        this.schreiben = schreiben;
        this.sprechen = sprechen;
        this.med = med;
    }

    public String getNom() {
        return nom;
    }

    public double getHören() {
        return hören;
    }

    public void setHören(double hören) {
        this.hören = hören;
    }

    public double getLessen() {
        return lessen;
    }

    public void setLessen(double lessen) {
        this.lessen = lessen;
    }

    public double getVokabular() {
        return vokabular;
    }

    public void setVokabular(double vokabular) {
        this.vokabular = vokabular;
    }

    public double getGrammatik() {
        return grammatik;
    }

    public void setGrammatik(double grammatik) {
        this.grammatik = grammatik;
    }

    public double getSchreiben() {
        return schreiben;
    }

    public void setSchreiben(double schreiben) {
        this.schreiben = schreiben;
    }

    public double getSprechen() {
        return sprechen;
    }

    public void setSprechen(double sprechen) {
        this.sprechen = sprechen;
    }

    public double getMed() {
        return med;
    }

    public void setMed(double med) {
        this.med = med;
    }

    public double getX1() {
        return (vokabular + grammatik) / 2;
    }

    public double getX() {
        double x1 = getX1();
        return (hören + lessen + vokabular + x1 + med) / 5;
    }

    public double getMoyenne() {
        double x = getX();
        return (x + sprechen) / 2;
    }

    @Override
    public String toString() {
        return String.format("%s,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f",
                nom, hören, lessen, vokabular, grammatik, schreiben, sprechen, med, getMoyenne());
    }

    public static Etudiant fromString(String ligne) {
        String[] parts = ligne.split(",");
        String nom = parts[0];
        double hören = Double.parseDouble(parts[1]);
        double lessen = Double.parseDouble(parts[2]);
        double vokabular = Double.parseDouble(parts[3]);
        double grammatik = Double.parseDouble(parts[4]);
        double schreiben = Double.parseDouble(parts[5]);
        double sprechen = Double.parseDouble(parts[6]);
        double med = Double.parseDouble(parts[7]);
        return new Etudiant(nom, hören, lessen, vokabular, grammatik, schreiben, sprechen, med);
    }
}