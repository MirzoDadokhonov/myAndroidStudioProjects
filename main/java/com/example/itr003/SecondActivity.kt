package com.example.itr003

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textView = findViewById<TextView>(R.id.textView)

        if (intent.hasExtra(MainActivity.MY_SPREF_TAG)) {
            val itemData = intent.getSerializableExtra(MainActivity.MY_SPREF_TAG) as ItemData
            textView.text = itemData.name + "\n\n\n" + itemData.phoneNum
        }

    }
}
