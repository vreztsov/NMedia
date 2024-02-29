package ru.netology.nmedia.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.util.AndroidUtils.focusAndShowKeyboard

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent?.let {
            val text : String? = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text == null) {
                binding.edit.hint = getString(R.string.post_text)
                binding.editPreview.text = ""
                binding.editGroup.visibility = View.GONE
                binding.edit.focusAndShowKeyboard()
            }
            else {
                binding.editMessage.setText(R.string.edit_message)
                binding.editPreview.text = text
                binding.editGroup.visibility = Group.VISIBLE
                binding.edit.setText(text)
                binding.edit.focusAndShowKeyboard()
            }
        }
        binding.ok.setOnClickListener {
            val text = binding.edit.text.toString()
            if (text.isNotBlank()) {
                setResult(RESULT_OK, Intent().apply { putExtra(Intent.EXTRA_TEXT, text) })
            } else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }
        binding.editClose.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
    object NewPostContract : ActivityResultContract<String?, String?>() {
        override fun createIntent(context: Context, input: String?) =
            Intent(context, NewPostActivity::class.java).apply { putExtra(Intent.EXTRA_TEXT, input) }

        override fun parseResult(resultCode: Int, intent: Intent?): String? =
            if (resultCode == RESULT_OK) {
                intent?.getStringExtra(Intent.EXTRA_TEXT)
            } else {
                null
            }
    }
}