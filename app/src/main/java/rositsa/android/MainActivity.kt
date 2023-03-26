package rositsa.android

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //the entry point of the program
    //code here
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.button)
        rollButton.setOnClickListener {
            rollDice()
//            val toast = Toast.makeText(this, "Dice Rolled!" , Toast.LENGTH_SHORT)
//            toast.show();
        }
    }

    //functions here, outside of onCreate
    private fun rollDice() {
        val dice = Dice(6);
        val result = dice.roll();
        val resultTextView: TextView = findViewById(R.id.textView);
        resultTextView.text = result.toString();
    }
//Log.d("TAG", myDice.roll().toString());
}


//classes here outside of the main class
class Dice(private val numSides : Int){
    fun roll() : Int{
        return (1..numSides).random();
    }
}


