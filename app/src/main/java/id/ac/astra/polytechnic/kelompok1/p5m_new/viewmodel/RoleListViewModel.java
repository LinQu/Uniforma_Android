package id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Role;

public class RoleListViewModel extends ViewModel {
    private List<Role> mRole;

    public void loadRole() {
        mRole = new ArrayList<>();

        Role koordinatorKelas = new Role("Koordinator Kelas");
        Role koordinatorTataTertib = new Role("Koordinator Tata Tertib");
        //Role sekretarisProdi = new Role("Sekretaris Prodi");

        mRole.add(koordinatorKelas);
        mRole.add(koordinatorTataTertib);
       // mRole.add(sekretarisProdi);
    }

    public List<Role> getRoleList() {
        loadRole();
        return mRole;
    }
}
