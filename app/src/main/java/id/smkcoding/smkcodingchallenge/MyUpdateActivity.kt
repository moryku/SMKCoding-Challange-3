package id.smkcoding.smkcodingchallenge2

import android.content.Intent.getIntent
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.isEmpty
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import id.smkcoding.smkcodingchallenge.R
import id.smkcoding.smkcodingchallenge.model.MyFriendModel


class MyUpdateActivity : AppCompatActivity() {

    //Deklarasi Variable
    private var namaBaru: EditText? = null
    private var emailBaru: EditText? = null
    private var telpBaru: EditText? = null
    private var alamatBaru: EditText? = null
    lateinit var update: Button
    private var database: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var cekNama: String? = null
    private var cekEmail: String? = null
    private var cekTelp: String? = null
    private var cekAlamat: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_update)

        getSupportActionBar()?.setTitle("Update Data")
        namaBaru = findViewById(R.id.new_nama)
        emailBaru = findViewById(R.id.new_email)
        telpBaru = findViewById(R.id.new_telp)
        alamatBaru = findViewById(R.id.new_alamat)
        update = findViewById(R.id.update)

        //Mendapatkan Instance autentikasi dan Referensi dari Database
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        getData();

        update.setOnClickListener {
            //Mendapatkan Data Mahasiswa yang akan dicek
            cekNama = namaBaru?.getText().toString()
            cekEmail = emailBaru?.getText().toString()
            cekTelp = telpBaru?.getText().toString()
            cekAlamat = alamatBaru?.getText().toString()

            //Mengecek agar tidak ada data yang kosong, saat proses update
            if (isEmpty(cekNama) || isEmpty(cekEmail) || isEmpty(cekTelp) || isEmpty(cekAlamat)) {
                Toast.makeText(this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
            } else {
                /*Menjalankan proses update data.
                  Method Setter digunakan untuk mendapakan data baru yang diinputkan User.
                */
                val temanBaru = MyFriendModel(cekNama!!, cekEmail!!, cekTelp!!, cekAlamat!!)
                val getUserID: String = auth?.getCurrentUser()?.getUid().toString()
                val getKey: String = getIntent().getStringExtra("getPrimaryKey").toString()
                database!!.child(getUserID).child("Teman")
                    .child(getKey).setValue(temanBaru)
                    .addOnCompleteListener {
                    Toast.makeText(this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show()
                            finish();
                        }
                    }
        }
    }

    //Menampilkan data yang akan di update
    private fun getData() {
        //Menampilkan data dari item yang dipilih sebelumnya
        val getNama: String  = getIntent().getStringExtra("dataNama").toString()
        val getEmail: String  = getIntent().getExtras().getString("dataEmail").toString()
        val getTelp: String  = getIntent().getExtras().getString("dataTelp").toString()
        val getAlamat: String  = getIntent().getExtras().getString("dataAlamat").toString()
        namaBaru?.setText(getNama);
        emailBaru?.setText(getEmail);
        telpBaru?.setText(getTelp);
        alamatBaru?.setText(getAlamat);
        Toast.makeText(this, getNama, Toast.LENGTH_SHORT).show()

    }
}

