package com.kamilagcabay.xogame

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.kamilagcabay.xogame.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    //Binding Init
    private lateinit var binding: ActivityMainBinding

    private var flag = 0

    private var counter = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnReset.setOnClickListener{
                newScore(TAG_RESET)
                newGame()
            }
        }
    }


    fun oxClick(view : View) {
        val btn = view as ImageView

        binding.apply {
            //Kullanıcı tarafından seçilen kutucuk daha önce seçilmemiş olmalıdır

            if (btn.drawable == null) {

                //bir hareket
                counter ++
                //sırayı oyuncuya atama
                when(flag) {
                    0 -> {
                        //sıra 1. oyuncuda
                        btn.setImageResource(R.drawable.ic_o)
                        btn.tag = TAG_O
                        //Displaying the green border
                        cardO.strokeWidth = 0
                        cardX.strokeWidth = 2
                        //Sıra değişir
                        flag = 1
                    }
                    1 -> {
                        //sıra 1. oyuncuda
                        btn.setImageResource(R.drawable.ic_x)
                        btn.tag = TAG_X
                        //Displaying the green border
                        cardO.strokeWidth = 2
                        cardX.strokeWidth = 0
                        //Srıa değişir
                        flag = 0
                    }
                }
                lifecycleScope.launch {
                    //oyunda kimin kazandığını kontrol etmek
                    if (iv1.tag == iv2.tag && iv2.tag == iv3.tag && iv3.tag != null) {
                        //Kimin kazandığını bulmak
                        newScore(iv1.tag.toString())
                        withAnimation(iv1,iv2,iv3)
                    } else if (iv4.tag == iv5.tag && iv5.tag == iv6.tag && iv6.tag != null) {
                        newScore(iv4.tag.toString())
                        withAnimation(iv4,iv5,iv6)
                    } else if (iv7.tag == iv8.tag && iv8.tag == iv9.tag && iv9.tag != null) {

                        newScore(iv7.tag.toString())
                        withAnimation(iv7,iv8,iv9)
                    } else if (iv1.tag == iv5.tag && iv5.tag == iv9.tag && iv9.tag != null) {
                        newScore(iv1.tag.toString())
                        withAnimation(iv1, iv5,iv9)
                    } else if (iv3.tag == iv5.tag && iv5.tag == iv7.tag && iv7.tag!= null) {
                        newScore(iv3.tag.toString())
                        withAnimation(iv3,iv5,iv7)
                    } else if (iv1.tag == iv4.tag && iv4.tag == iv7.tag && iv7.tag != null) {
                        newScore(iv1.tag.toString())
                        withAnimation(iv1,iv4,iv7)
                    } else if (iv2.tag == iv5.tag && iv5.tag == iv8.tag && iv8.tag != null) {
                        newScore(iv2.tag.toString())
                        withAnimation(iv2,iv5,iv8)
                    } else if (iv3.tag == iv6.tag && iv6.tag == iv9.tag && iv9.tag != null) {
                        newScore(iv3.tag.toString())
                        withAnimation(iv3,iv6,iv9)
                    } else if (counter == 9) {
                        Toast.makeText(this@MainActivity, "This game has no winner", Toast.LENGTH_LONG).show()
                        newGame()
                    }
                }
            }
        }
    }

    private suspend fun withAnimation(viewOne : View,viewTwo : View,viewThree : View) {
        viewOne.setBackgroundResource(R.drawable.board_back_green)
        delay(200)
        viewTwo.setBackgroundResource(R.drawable.board_back_green)
        delay(200)
        viewThree.setBackgroundResource(R.drawable.board_back_green)
        newGame()
    }

    @SuppressLint("SetTextI18n")
    private fun newScore(tag :String) {
        when(tag) {
            TAG_X -> {
                val xPoint = binding.boardXCount.text.toString().toInt()
                //skor arttırma
                binding.boardXCount.text = (xPoint + 1).toString()
            }
            TAG_O -> {
                val oPoint = binding.boardOCount.text.toString().toInt()
                //skor arttırma
                binding.boardOCount.text = (oPoint +1).toString()
            }
            TAG_RESET -> {
                //skoru sıfırlama
                binding.boardOCount.text = "0"
                binding.boardXCount.text = "0"
            }
        }
    }

    private fun newGame() {
        //Sıra değişti
        flag = 0
        counter = 0
        //bütün kutucukları bul ve temizle
        binding.grid.children.filterIsInstance<ImageView>().forEach {
            it.setImageDrawable(null)
            it.tag = null
            it.setBackgroundResource(R.drawable.board_back)

        }
        binding.cardO.strokeWidth = 2
        binding.cardX.strokeWidth = 0
    }

}