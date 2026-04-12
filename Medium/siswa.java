package Medium;
import java.util.Scanner;
import java.util.ArrayList;

abstract class Kendaraan {
    private String kode;
    private String nama;
    private int hargaSewa;
    private boolean tersedia;
    private String tipe;

    public Kendaraan(String kode, String nama, int hargaSewa, String tipe) {
        this.kode = kode;
        this.nama = nama;
        this.hargaSewa = hargaSewa;
        this.tipe = tipe;
        this.tersedia = true;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public int getHargaSewa() {
        return hargaSewa;
    }

    public String getTipe() {
        return tipe;
    }

    public boolean isTersedia() {
        return tersedia;
    }

    public void setTersedia(boolean tersedia) {
        this.tersedia = tersedia;
    }

    public abstract int hitungSewa(int hari, boolean pakaiPromo);
}

class Mobil extends Kendaraan {
    public Mobil(String kode, String nama, int hargaSewa) {
        super(kode, nama, hargaSewa, "CAR");
    }

    @Override
    public int hitungSewa(int hari, boolean pakaiPromo) {
        int total = getHargaSewa() * hari;
        if (pakaiPromo == true) {
            total = total - 20000;
        }
        if (total < 0) {
            total = 0;
        }
        return total;
    }
}

class Motor extends Kendaraan {
    public Motor(String kode, String nama, int hargaSewa) {
        super(kode, nama, hargaSewa, "BIKE");
    }

    @Override
    public int hitungSewa(int hari, boolean pakaiPromo) {
        int total = getHargaSewa() * hari;
        if (pakaiPromo == true) {
            total = total - 10000;
        }
        if (total < 0) {
            total = 0;
        }
        return total;
    }
}

public class siswa {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Kendaraan> daftarKendaraan = new ArrayList<>();

        int n = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < n; i++) {
            String baris = sc.nextLine();
            String[] p = baris.split(" ");
            String perintah = p[0];

            if (perintah.equals("ADD")) {
                String tipe = p[1];
                String kode = p[2];
                String nama = p[3];
                int harga = Integer.parseInt(p[4]);

                boolean status = false;
                for (int j = 0; j < daftarKendaraan.size(); j++) {
                    if (daftarKendaraan.get(j).getKode().equals(kode)) {
                        status = true;
                    }
                }

                if (status == true) {
                    System.out.println("Kendaraan sudah terdaftar");
                } else {
                    if (tipe.equals("CAR")) {
                        daftarKendaraan.add(new Mobil(kode, nama, harga));
                    } else {
                        daftarKendaraan.add(new Motor(kode, nama, harga));
                    }
                    System.out.println(tipe + " " + kode + " berhasil ditambahkan");
                }

            } else if (perintah.equals("RENT")) {
                String kode = p[1];
                int hari = Integer.parseInt(p[2]);
                boolean pakaiPromo = false;
                
                if (p.length > 3) {
                    if (p[3].equals("PROMO")) {
                        pakaiPromo = true;
                    }
                }

                boolean status = false;
                for (int j = 0; j < daftarKendaraan.size(); j++) {
                    Kendaraan k = daftarKendaraan.get(j);
                    if (k.getKode().equals(kode)) {
                        status = true;
                        if (k.isTersedia() == false) {
                            System.out.println("Kendaraan sedang disewa");
                        } else {
                            int total = k.hitungSewa(hari, pakaiPromo);
                            k.setTersedia(false);
                            System.out.println("Total sewa " + kode + ": " + total);
                        }
                    }
                }

                if (status == false) {
                    System.out.println("Kendaraan tidak ditemukan");
                }

            } else if (perintah.equals("RETURN")) {
                String kode = p[1];
                boolean status = false;

                for (int j = 0; j < daftarKendaraan.size(); j++) {
                    Kendaraan k = daftarKendaraan.get(j);
                    if (k.getKode().equals(kode)) {
                        status = true;
                        if (k.isTersedia() == true) {
                            System.out.println("Kendaraan belum disewa");
                        } else {
                            k.setTersedia(true);
                            System.out.println(kode + " berhasil dikembalikan");
                        }
                    }
                }

                if (status == false) {
                    System.out.println("Kendaraan tidak ditemukan");
                }

            } else if (perintah.equals("DETAIL")) {
                String kode = p[1];
                boolean status = false;

                for (int j = 0; j < daftarKendaraan.size(); j++) {
                    Kendaraan k = daftarKendaraan.get(j);
                    if (k.getKode().equals(kode)) {
                        status = true;
                        String labelStatus = "TERSEDIA";
                        if (k.isTersedia() == false) {
                            labelStatus = "DISEWA";
                        }
                        System.out.println(k.getKode() + " | " + k.getTipe() + " | " + k.getNama() + " | harga: " + k.getHargaSewa() + " | status: " + labelStatus);
                    }
                }

                if (status == false) {
                    System.out.println("Kendaraan tidak ditemukan");
                }

            } else if (perintah.equals("COUNT")) {
                System.out.println("Total kendaraan: " + daftarKendaraan.size());
            }
        }
        sc.close();
    }
}