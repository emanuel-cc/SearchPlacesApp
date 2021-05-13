package com.example.searchplacesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.searchplacesapp.Model.DetailsResponse
import com.example.searchplacesapp.Model.MainPojo
import com.example.searchplacesapp.Model.Prediction
import com.example.searchplacesapp.Model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var editText:EditText;
    lateinit var recyclerview:RecyclerView;
    lateinit var relativeLayout:RelativeLayout;
    lateinit var apiInterface: ApiInterface;
    lateinit var listaPredicts:List<Prediction>
    lateinit var prediction: Prediction;
    lateinit var result:Result;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.edittext)
        recyclerview = findViewById(R.id.recyclerview)
        relativeLayout = findViewById(R.id.notdata_found)

        relativeLayout.visibility = View.VISIBLE
        recyclerview.visibility = View.GONE

        var retrofit:Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .build()

        apiInterface = retrofit.create(ApiInterface::class.java)

        editText.addTextChangedListener(
            beforeTextChanged = { charSequence: CharSequence?, i: Int, i1: Int, i2: Int ->{

            } },
            /*onTextChanged={charSequence: CharSequence?, i: Int, i1: Int, i2: Int->
                run {
                    getData(charSequence.toString())
                }
            },*/
            afterTextChanged = {text: Editable? ->
                run {
                    getData(text.toString())
                }
            }
        )
        /*editText.setOnClickListener {

        }*/
    }

    private fun getData(text:String){
        apiInterface.getPlace(text, getString(R.string.api_key))
            .enqueue(object:Callback<MainPojo>{
                override fun onResponse(call: Call<MainPojo>, response: Response<MainPojo>) {
                    if(response.isSuccessful){
                        relativeLayout.visibility = View.GONE
                        recyclerview.visibility = View.VISIBLE

                        //print("Respuesta: ${response.body()?.predictions}")
                        listaPredicts = response.body()?.predictions as List<Prediction>
                        var recyclerAdapter= RecyclerAdapter(response.body()?.predictions as List<Prediction>)
                        recyclerview.adapter = recyclerAdapter
                        recyclerAdapter.addOnClickItem(object : OnClickItem{
                            override fun onAssignItemClick(position: Int) {
                                prediction = listaPredicts.get(position)
                                //println(prediction)
                                getLatLng(prediction.place_id)
                            }
                        })
                    }else{
                        relativeLayout.visibility = View.VISIBLE
                        recyclerview.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<MainPojo>, t: Throwable) {
                    relativeLayout.visibility = View.VISIBLE
                    recyclerview.visibility = View.GONE
                    //Toast.makeText(this@MainActivity,"Error ${t.message}", Toast.LENGTH_LONG).show()
                    println("${t.message}")
                }

            })
    }

    fun getLatLng(place_id:String){
        apiInterface.getLatLng(place_id, getString(R.string.api_key))
            .enqueue(object:Callback<DetailsResponse>{
                override fun onResponse(
                    call: Call<DetailsResponse>,
                    response: Response<DetailsResponse>
                ) {
                    recyclerview.visibility = View.GONE
                    result = response.body()?.result as Result
                    println("LatLng: ${result.geometry.location.lat}, ${result.geometry.location.lng}")
                    var latlng = "${result.geometry.location.lat}, ${result.geometry.location.lng}"
                    editText.setText("${latlng}")
                }

                override fun onFailure(call: Call<DetailsResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity,"${t.message}",Toast.LENGTH_LONG).show()
                }
            })
    }
}