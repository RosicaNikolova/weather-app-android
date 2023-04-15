package rositsa.android

import android.media.RingtoneManager
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

class MainActivity : AppCompatActivity() {

    private lateinit var sunriseTextView: TextView
    private lateinit var sunsetTextView: TextView
    private lateinit var windTextView: TextView
    private lateinit var pressureTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var cityTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var conditionTextView: TextView
    private lateinit var temperatureTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Sound when the app is opened
        val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val ringtone = RingtoneManager.getRingtone(applicationContext, notification)
        ringtone.play()


        sunriseTextView = findViewById(R.id.sunrise)
        sunsetTextView = findViewById(R.id.sunset)
        windTextView = findViewById(R.id.wind)
        pressureTextView = findViewById(R.id.pressure)
        humidityTextView = findViewById(R.id.humidity)
        cityTextView = findViewById(R.id.city)
        dateTextView = findViewById(R.id.date)
        conditionTextView = findViewById(R.id.condition)
        temperatureTextView = findViewById(R.id.temperature)


        //Using the Volley library to make the request to the API
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/3.0/onecall?lat=51.44&lon=5.46&appid=2195873671d366dc1578964c1cca8761"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->

                //getting the current weather from the JSON response
                val current = response.getJSONObject("current")

                // Set the city name manually, since it is not provided by the API
                val city = "Eindhoven"
                cityTextView.text = city


                //Temperature
                val temperatureInKalvin = current.getDouble("temp")
                val temperatureInCel = round(temperatureInKalvin - 273.15)
                temperatureTextView.text = "$temperatureInCel°C"


                //Description weather
                val weather = current.getJSONArray("weather").getJSONObject(0)
                val condition = weather.getString("description")
                conditionTextView.text = condition


                //Date and Time
                val dateInSec = current.getLong("dt")
                // creating a Date object with the time value in milliseconds
                val dateInMilliSeconds = Date(dateInSec * 1000L)
                // creating an object with the desired format
                val formatterForDate = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                formatterForDate.timeZone = TimeZone.getDefault() // set the timezone to the device's default timezone
                val dateFormatted = formatterForDate.format(dateInMilliSeconds) // format the date and time string
                dateTextView.text = dateFormatted;

                //Sunrise
                val sunriseInSec = current.getLong("sunrise")
                val sunriseInMilliSec = Date(sunriseInSec * 1000) // convert to milliseconds
                val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
                val sunriseFormatted = formatter.format(sunriseInMilliSec).replace("am", "AM").replace("pm", "PM")
                sunriseTextView.text = sunriseFormatted;


                //Sunset
                val sunsetInSec = current.getLong("sunset")
                val sunsetInMilliSec = Date(sunsetInSec * 1000) // convert to milliseconds
                val sunsetFormatted = formatter.format(sunsetInMilliSec).replace("am", "AM").replace("pm", "PM")
                sunsetTextView.text = sunsetFormatted;


                //Wind
                val windMetersInSec = current.getDouble("wind_speed")
                val windKlmInHour = round(windMetersInSec * 3.6);
                windTextView.text = "$windKlmInHour km/h";


                //Pressure
                val pressure = current.getString("pressure")
                pressureTextView.text = "$pressure hPa";


                //Humidity
                val humidity = current.getInt("humidity")
                humidityTextView.text = "$humidity hPa"

            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            })
        queue.add(request)
    }

}


