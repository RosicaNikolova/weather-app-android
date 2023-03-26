package rositsa.android

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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

        rollDice()
    }

    //functions here, outside of onCreate
    private fun rollDice() {
        val dice = Dice(6);
        val result = dice.roll();
        val diceImage: ImageView = findViewById(R.id.imageView)

        val drawableResource = when(result){
            1-> R.drawable.dice_1
            2-> R.drawable.dice_2
            3-> R.drawable.dice_3
            4-> R.drawable.dice_4
            5-> R.drawable.dice_5
            6-> R.drawable.dice_6
            else ->R.drawable.dice_6
        }
        diceImage.setImageResource(drawableResource)
    }
//Log.d("TAG", myDice.roll().toString());
}


//classes here outside of the main class
class Dice(private val numSides : Int){
    fun roll() : Int{
        return (1..numSides).random();
    }
}


