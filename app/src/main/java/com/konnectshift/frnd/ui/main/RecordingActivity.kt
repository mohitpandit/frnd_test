package com.konnectshift.frnd.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.konnectshift.frnd.R
import com.konnectshift.frnd.data.retrofit.ApiErrorResponse
import com.konnectshift.frnd.data.retrofit.ApiSuccessResponse
import com.konnectshift.frnd.model.RecordingObject
import com.konnectshift.frnd.ui.main.adapter.RecordingListAdapter
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_recording.*
import java.io.File
import java.io.IOException
import javax.inject.Inject


class RecordingActivity : DaggerAppCompatActivity(), View.OnClickListener, RecordingListAdapter.OnItemClickListener {

    private var TAG = RecordingActivity::class.java.simpleName
    @Inject
    lateinit var mRecordingViewModel: RecordingViewModel
    @Inject
    lateinit var mRecorder: MediaRecorder
    @Inject
    lateinit var mediaPlayer: MediaPlayer

    private var isRecording = false
    private var REQ_AUDIO_PERMISSION = 10
    private lateinit var mRecordingListAdapter: RecordingListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recording)
        AndroidInjection.inject(this)
        initView()

    }


    private fun initView() {
        btnRecordAudio.setOnClickListener(this)
        mRecordingListAdapter = RecordingListAdapter(this)
        rvRecordings.adapter = mRecordingListAdapter
        rvRecordings.layoutManager = LinearLayoutManager(this)
        mRecordingViewModel.getRecording().observe(this, Observer {
            mRecordingListAdapter.submitList(it as MutableList<RecordingObject>)
            Handler().postDelayed({
                rvRecordings.smoothScrollToPosition(0)
            }, 200)
            Log.d(TAG, it.size.toString())
        })
    }


    override fun onItemClick(position: Int, item: RecordingObject) {
        playAudio(item.media)

    }

    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()
        mRecorder.release()
        mediaPlayer.release()
    }
    private fun recordAudio() {
        if (!isRecording) {
            isRecording = true
            btnRecordAudio.text = "Stop Recording"
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mRecorder.setOutputFile(
                externalCacheDir?.absolutePath.toString() + "/recordingFile.mp3"
            )
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            try {
                mRecorder.prepare()

            } catch (e: IOException) {
                e.printStackTrace()
            }

            mRecorder.start()
        } else {
            btnRecordAudio.text = "Start Recording"
            mRecorder.stop()
            isRecording = false
            uploadRecording()

        }
    }

    private fun uploadRecording() {
        mRecordingViewModel.uploadFile(File(externalCacheDir?.absolutePath.toString() + "/recordingFile.mp3"))?.observe(this, Observer {
            when (it) {
                is ApiSuccessResponse -> {
                    Log.d(TAG, it.body.toResponseModel(RecordingObject::class.java).media)
                    mRecordingViewModel.saveToLocal(it.body.toResponseModel(RecordingObject::class.java)) {
                        Log.d(TAG, "saved to local")
                    }
                }

                is ApiErrorResponse -> {
                    Log.d(TAG, it.errorMessage)

                }
            }
        })
    }

    private fun playAudio(url: String) {
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
            mediaPlayer.setOnPreparedListener {
                it.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnRecordAudio -> {
                if (checkAudioPermission()) recordAudio()
                else requestPermission()
            }
        }
    }

    private fun checkAudioPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED

    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), REQ_AUDIO_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQ_AUDIO_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recordAudio()
            } else {
                Toast.makeText(this, "Please allow permission to record audio", Toast.LENGTH_SHORT).show()
            }
        }

    }

}
