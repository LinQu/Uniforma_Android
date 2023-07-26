package id.ac.astra.polytechnic.kelompok1.p5m_new.helper;

import java.util.List;

public class BulanNavigator {

    private List<int[]> dataList;
    private int currentIndex;

    public BulanNavigator(List<int[]> dataList) {
        this.dataList = dataList;
        this.currentIndex = 0;
    }

    public int[] getCurrentData() {
        return dataList.get(currentIndex);
    }

    public int[] getPreviousData() {
        if (currentIndex > 0) {
            currentIndex--;
            return dataList.get(currentIndex);
        } else {
            // Kembali ke data terakhir jika sudah berada di data pertama
            currentIndex = dataList.size() - 1;
            return dataList.get(currentIndex);
        }
    }

    public int[] getNextData() {
        if (currentIndex < dataList.size() - 1) {
            currentIndex++;
            return dataList.get(currentIndex);
        } else {
            // Kembali ke data pertama jika sudah berada di data terakhir
            currentIndex = 0;
            return dataList.get(currentIndex);
        }
    }
}
