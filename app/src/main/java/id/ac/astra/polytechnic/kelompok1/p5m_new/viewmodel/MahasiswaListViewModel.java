package id.ac.astra.polytechnic.kelompok1.p5m_new.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import id.ac.astra.polytechnic.kelompok1.p5m_new.model.Mahasiswa;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginMahasiswaResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.model.response.LoginResponse;
import id.ac.astra.polytechnic.kelompok1.p5m_new.repository.MahasiswaRepository;

public class MahasiswaListViewModel extends ViewModel {

   private List<Mahasiswa> mMahasiswa;
   private MahasiswaRepository mMahasiswaRepository;

   public MahasiswaListViewModel() {
       mMahasiswaRepository = MahasiswaRepository.get();
   }
    public List<Mahasiswa> getMahasiswa(){
       if(mMahasiswa == null){
          mMahasiswa = new ArrayList<>();
          loadMahasiswa();
       }
       return mMahasiswa;
    }

    public LiveData<LoginMahasiswaResponse> getLoginMahasiswa(String nim){
        return mMahasiswaRepository.getLoginMhs(nim);
    }

    public LiveData<List<Mahasiswa>> getMahasiswaByKelas(String kelas){
        return mMahasiswaRepository.getMahasiswaByKelas(kelas);
    }

    public void loadMahasiswa(){

       for (int i=0; i <100; i++){
          Mahasiswa mahasiswa = new Mahasiswa();
          mahasiswa.setNama("03202100"+i + "-Nama MHS");
          mMahasiswa.add(mahasiswa);
       }
    }
}