package club.mobile.d21.testmusic

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import club.mobile.d21.testmusic.data.Data
import club.mobile.d21.testmusic.databinding.ActivityMainBinding

class MainActivity:AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()
    private var mediaPlayer: MediaPlayer? = null
    private var selectedData: Data? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val musicAdapter = MusicAdapter(
            onPlayClick = { data ->
                selectedData = data // Cập nhật selectedData
                mediaPlayer?.release() // Giải phóng MediaPlayer hiện tại (nếu có)
                mediaPlayer = MediaPlayer.create(this, data.preview.toUri())
                mediaPlayer?.start()
            },
            onStopClick = {
                mediaPlayer?.stop() // Dừng MediaPlayer hiện tại (nếu có)
            }
        )
        binding.listMusic.adapter = musicAdapter
        binding.listMusic.layoutManager = LinearLayoutManager(this)
        mainViewModel.data.observe(this, Observer { myData->
            musicAdapter.submitList(myData.data)
        })
    }
}
