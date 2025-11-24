package model;

/**
 * Kelas yang merepresentasikan entitas Buku.
 */
public class Buku {
    private String kodeBuku;
    private String judul;
    private String penulis;
    private int stok;

    public Buku(String kodeBuku, String judul, String penulis, int stok) {
        this.kodeBuku = kodeBuku;
        this.judul = judul;
        this.penulis = penulis;
        this.stok = stok;
    }

    // Encapsulation: Getters dan Setters
    public String getKodeBuku() {
        return kodeBuku;
    }

    public String getJudul() {
        return judul;
    }

    public String getPenulis() {
        return penulis;
    }
    
    public int getStok() {
        return stok;
    }
    
    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }
    
    public void setStok(int stok) {
        this.stok = stok;
    }
    
    /**
     * Format tampilan buku untuk CLI.
     */
    @Override
    public String toString() {
        return String.format("| %-6s | %-40s | %-20s | %-6d |", 
            kodeBuku, judul, penulis, stok);
    }
}