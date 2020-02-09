package com.camayla.githubkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_search_github_user.setOnClickListener {
            val intent: Intent = Intent(
                this,
                ListUserActivity::class.java
            )

            var userName : String = edit_user_name.text.toString()


            intent.putExtra("data", userName)

            this.startActivity(intent)
        }
    }
}
