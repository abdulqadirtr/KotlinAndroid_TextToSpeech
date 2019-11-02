package app.easylink.kotlin_textchange

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var buttonSpeak: Button? = null
    private var editText: EditText? = null
    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS){
            // Double !! will also accept the null values
            val result=tts!!.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                button_speak!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tts = TextToSpeech(this, this)
        button_speak.setOnClickListener(View.OnClickListener {
            speakOut();
        })
        /**
         * @author Abdul Qadir
         * @
         * This Application is using addTextChangeListner on EditText using object TextWatcher
         * The Application is also used for TexToSpeeche
         */
        editTextSample.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                tvSample.setText("Text in EditText After : "+s)
            }


            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            //These are the onTexchanged method which we track of any changes happens in Edit Text

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
               // tvSample.setText("Text in EditText : "+s+"Count of words"+ count)
            }

        })
    }

    private fun speakOut() {
        val text=tvSample!!.text.toString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")

        };
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

}
