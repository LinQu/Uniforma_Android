package id.ac.astra.polytechnic.kelompok1.p5m_new.model;


public class Role {
    private String namaRole;

    public Role(String namaRole) {
        this.namaRole = namaRole;
    }

    public String getNamaRole() {
        return namaRole;
    }

    public void setNamaRole(String namaRole) {
        this.namaRole = namaRole;
    }

    @Override
    public String toString() {
        return namaRole;
    }
}
