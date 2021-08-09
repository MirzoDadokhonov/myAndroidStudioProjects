package com.example.itr003

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var MY_RQS_CODE = 911
    companion object {var MY_SPREF_TAG = "contactsData0014"}
    lateinit var spref : SharedPreferences
    var tipiMassiv = object : TypeToken<ArrayList<ItemData?>?>() {}.type
//    val  : Type = TypeToken<ArrayList<ItemData>>(){}.type

    override fun onClick(v: View?) {
        // взять контакт из списка контактов
        if (v?.id == R.id.floatingActionButton) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(intent, MY_RQS_CODE)
        } else if (v?.id == R.id.rViewId) { //
            val intent = Intent(this, SecondActivity :: class.java)
            val itemData = v.tag as ItemData
            intent.putExtra(MY_SPREF_TAG, itemData)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MY_RQS_CODE && resultCode == Activity.RESULT_OK) {
            val contactUri = data?.data ?: return
            val cols = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER)

            val rqs = contentResolver.query(contactUri, cols, null, null, null)

            if (rqs?.moveToFirst()!!) {
                val tempItemData = ItemData(rqs.getString(0), rqs.getString(1), true)
                val t = myContains(myContactsData, tempItemData)
                if (t != null) {
                    if (t.status) {
                        t.status = false
                    }
                } else {
                    myContactsData.add(tempItemData)
                    val str = Gson().toJson(tempItemData)
                    val edtr : SharedPreferences.Editor = spref.edit()

                    val str2 = spref.getString(MY_SPREF_TAG, "[]")
                    val str3 = str2?.length?.let { str2.substring(1, it - 1) }


                    val strAns = if (str3?.length == 0) {
                        "[$str]"
                    } else {
                        "[$str3,$str]"
                    }

                    edtr.putString(MY_SPREF_TAG, strAns)
                    edtr.apply()
                }
                rView.adapter?.notifyDataSetChanged()
            }

            rqs.close()
        }
    }

    var myContactsData : ArrayList<ItemData> = ArrayList()
    lateinit var rView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spref = getSharedPreferences("TestFileName", Context.MODE_PRIVATE)
        val ed: SharedPreferences.Editor = spref.edit()
        ed.apply()
        rView = findViewById(R.id.rViewId)
        findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
            startActivityForResult(intent, MY_RQS_CODE)
        }

        rView.layoutManager = LinearLayoutManager(this)

        myContactsData = Gson().fromJson(spref.getString(MY_SPREF_TAG, ""), tipiMassiv)
            ?: ArrayList()

        val adapter = MyAdapter(myContactsData, this)
        rView.adapter = adapter
    }

    fun myContains(arr : ArrayList<ItemData>, itemData: ItemData) : ItemData? {
        if (arr.size == 0) {
            return null
        } else {
            for (i  in arr) {
                if (i.name == itemData.name &&
                    i.phoneNum == itemData.phoneNum
                ) {
                    return i
                }
            }
            return null
        }
    }
}
