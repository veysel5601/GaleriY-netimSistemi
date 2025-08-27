import java.text.NumberFormat;
import java.util.*;

class Musteri {
    private int id;
    private String ad;
    private String soyad;

    public Musteri(int id, String ad, String soyad) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
    }

    @Override
    public String toString() {
        return "#" + id + " - " + ad + " " + soyad;
    }
}

class Araba {
    int id;
    String marka;
    String model;
    int yil;
    double fiyat;

    public Araba(int id, String marka, String model, int yil, double fiyat) {
        this.id = id;
        this.marka = marka;
        this.model = model;
        this.yil = yil;
        this.fiyat = fiyat;
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("tr", "TR"));
        return String.format("ID:%-3d %-10s %-12s %d  Fiyat: %s",
                id, marka, model, yil, nf.format(fiyat));
    }
}

class Galeri {
    private List<Araba> mevcutArabalar = new ArrayList<>();
    private List<String> satisKayitlari = new ArrayList<>();
    private int musteriIdSayaci = 1;

    public void arabaEkle(Araba a) {
        mevcutArabalar.add(a);
    }

    public void arabalarıListele() {
        if (mevcutArabalar.isEmpty()) {
            System.out.println("Galeride araç kalmadı.");
            return;
        }
        System.out.println("==== Mevcut Arabalar ====");
        for (Araba a : mevcutArabalar) {
            System.out.println(a);
        }
    }

    public void arabaSat(int arabaId, String musteriAd, String musteriSoyad) {
        Araba secilen = null;
        for (Araba a : mevcutArabalar) {
            if (a.id == arabaId) {
                secilen = a;
                break;
            }
        }

        if (secilen == null) {
            System.out.println(" Bu ID'ye sahip araba bulunamadı.");
            return;
        }

        Musteri musteri = new Musteri(musteriIdSayaci++, musteriAd, musteriSoyad);

        mevcutArabalar.remove(secilen);

        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("tr", "TR"));
        String kayit = String.format("[%tF %<tR] Müşteri: %s | Satın aldığı: %s %s (%d)  Fiyat: %s",
                new Date(), musteri, secilen.marka, secilen.model, secilen.yil, nf.format(secilen.fiyat));

        satisKayitlari.add(kayit);
        System.out.println("✅ Satış tamamlandı!");
        System.out.println(kayit);
    }

    public void satislariGoruntule() {
        if (satisKayitlari.isEmpty()) {
            System.out.println("Henüz satış kaydı yok.");
            return;
        }
        System.out.println("==== Satış Kayıtları ====");
        for (String kayit : satisKayitlari) {
            System.out.println(kayit);
        }
    }

    public List<Araba> getMevcutArabalar() {
        return mevcutArabalar;
    }
}

public class GaleriYonetimSistemi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Galeri galeri = new Galeri();

        galeri.arabaEkle(new Araba(1, "Toyota", "Corolla", 2018, 575000));
        galeri.arabaEkle(new Araba(2, "Renault", "Clio", 2020, 615000));
        galeri.arabaEkle(new Araba(3, "BMW", "320i", 2017, 1350000));
        galeri.arabaEkle(new Araba(4, "Fiat", "Egea", 2022, 735000));

        while (true) {
            System.out.println();
            System.out.println("==== Galeri Yönetim Sistemi ====");
            System.out.println("1. Arabaları Listele");
            System.out.println("2. Araba Satın Al");
            System.out.println("3. Satışları Görüntüle");
            System.out.println("4. Çıkış");
            System.out.print("Seçiminizi girin: ");

            String secim = scanner.nextLine().trim();
            System.out.println();

            switch (secim) {
                case "1":
                    galeri.arabalarıListele();
                    break;

                case "2":
                    if (galeri.getMevcutArabalar().isEmpty()) {
                        System.out.println("Satın alınabilir araç kalmadı.");
                        break;
                    }
                    galeri.arabalarıListele();
                    System.out.print("Satın almak istediğiniz araba ID'sini girin: ");
                    int arabaId;
                    try {
                        arabaId = Integer.parseInt(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Geçersiz ID.");
                        break;
                    }

                    System.out.print("Müşteri adı: ");
                    String ad = scanner.nextLine().trim();
                    System.out.print("Müşteri soyadı: ");
                    String soyad = scanner.nextLine().trim();

                    if (ad.isEmpty() || soyad.isEmpty()) {
                        System.out.println("Ad ve soyad boş olamaz.");
                        break;
                    }

                    galeri.arabaSat(arabaId, ad, soyad);
                    break;

                case "3":
                    galeri.satislariGoruntule();
                    break;

                case "4":
                    System.out.println("Çıkış yapılıyor.");
                    return;

                default:
                    System.out.println("Lütfen 1-4 arasında bir seçim yapın.");
                    break;
            }
        }
    }
}