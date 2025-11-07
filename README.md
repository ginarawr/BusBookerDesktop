# 💻 Panduan Kerja Tim Menggunakan GitHub

Panduan ini dibuat untuk membantu tim bekerja sama dalam satu proyek menggunakan Git dan GitHub dengan mudah.

---

## 🚀 Langkah-Langkah Kerja Tim

### 1. Install dan Siapkan Alat
- Install **Git**: [https://git-scm.com/](https://git-scm.com/)
- Buat akun di **GitHub**: [https://github.com/](https://github.com/)
- (Opsional) Install **VS Code** agar mudah mengelola proyek dan Git.

---

### 2. Buat Repository (Repo) Utama
1. Login ke GitHub
2. Klik tombol **New Repository**
3. Isi nama repo (contoh: `ProjekBusBooker`)
4. Centang “Add a README file”
5. Klik **Create Repository**

---

### 3. Tambahkan Anggota Tim
1. Masuk ke repo yang sudah dibuat
2. Klik **Settings → Collaborators**
3. Klik **Add people** dan masukkan username teman
4. Teman harus **Accept Invitation**

---

### 4. Clone Repo ke Laptop
Agar bisa mengerjakan proyek di laptop sendiri:
```bash



git clone https://github.com/username-kamu/nama-repo.git

Contoh:

git clone https://github.com/ginarhm/ProjekBusBooker.git

5. Masuk ke Folder Project
cd ProjekBusBooker

6. Buat Branch Baru

Buat cabang kerja agar tidak mengganggu kode utama:

git checkout -b nama-branch


Contoh:

git checkout -b fitur-login

7. Simpan dan Commit Perubahan

Setelah ngoding:

git add .
git commit -m "Menambahkan halaman login"

8. Push ke GitHub

Kirim hasil kerja ke GitHub:

git push origin nama-branch

9. Buat Pull Request

Buka GitHub

Klik Compare & Pull Request

Isi deskripsi perubahan

Klik Create Pull Request

10. Merge ke Branch Utama

Setelah disetujui oleh tim:

Klik Merge Pull Request

Branch utama (main) akan diperbarui

11. Update Repo di Laptop

Agar repo lokal selalu terbaru:

git pull origin main

12. Hapus Branch yang Sudah Selesai (Opsional)
git branch -d nama-branch

🔁 Alur Kerja Singkat

1️⃣ Buat repo
2️⃣ Tambah anggota
3️⃣ Clone repo
4️⃣ Buat branch
5️⃣ Ngoding
6️⃣ Commit
7️⃣ Push
8️⃣ Pull request
9️⃣ Merge
🔟 Pull update

✨ Tips

Selalu buat branch baru sebelum mengerjakan fitur.

Gunakan commit message yang jelas.

Rutin lakukan git pull origin main agar tidak ketinggalan update tim.

Jangan langsung ubah di branch main.

📚 Contoh Penamaan Branch

fitur-login

fitur-registrasi

fix-bug-navbar

update-ui-dashboard


---

## 🧩 **Ringkasan Singkat (Versi Tempel di Dinding Tim)**



🔹 1. Buat repo di GitHub
🔹 2. Tambah anggota (Settings → Collaborators)
🔹 3. Clone ke laptop → git clone <url>
🔹 4. Buat branch → git checkout -b <nama-branch>
🔹 5. Ngoding & simpan → git add . → git commit -m "pesan"
🔹 6. Kirim ke GitHub → git push origin <nama-branch>
🔹 7. Buat Pull Request di GitHub
🔹 8. Merge ke main setelah disetujui
🔹 9. Update lokal → git pull origin main
🔹 10. Hapus branch lama (opsional)
