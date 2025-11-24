package model;

/**
 * Kelas abstrak yang merepresentasikan pengguna sistem (Admin atau Anggota).
 */
public abstract class Pengguna {
    private String idPengguna;
    private String nama;
    private String username;
    private String password;

    public Pengguna(String idPengguna, String nama, String username, String password) {
        this.idPengguna = idPengguna;
        this.nama = nama;
        this.username = username;
        this.password = password;
    }

    // Abstraction & Polymorphism
    public abstract void tampilkanMenu(); 

    // Encapsulation: Getters
    public String getIdPengguna() {
        return idPengguna;
    }

    public String getNama() {
        return nama;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }

    // Encapsulation: Setter
    public void setNama(String nama) {
        this.nama = nama;
    }
}

