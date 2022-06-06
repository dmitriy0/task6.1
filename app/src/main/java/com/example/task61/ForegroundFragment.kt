package com.example.task61

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception
import java.lang.Math.pow


import java.math.BigDecimal
import kotlin.math.pow


class ForegroundFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_foreground, container, false)

        val textPi = view.findViewById<TextView>(R.id.text_pi)

        var k = 1.0
        var sum = BigDecimal(0.0)

        var thread = Thread()
        val handler = Handler()

        parentFragmentManager.setFragmentResultListener("requestKey", this) { key, bundle ->
            when (bundle.getString("state")!!) {

                "start" -> {
                    thread = Thread {
                        while(!Thread.currentThread().isInterrupted){

                            if (k % 2 != 0.0) {
                                sum += BigDecimal(4.0).divide(BigDecimal(k*2 - 1), 1000, 0)
                            } else {
                                sum -= BigDecimal(4.0).divide(BigDecimal(k*2 - 1), 1000, 0)
                            }

                            if (k % 1000 == 0.0) {
                                handler.post(Runnable {
                                    textPi.text = sum.toString()
                                })
                            }

                            k += 1.0
                        }
                    }
                    thread.start()
                }

                "stop" -> {
                    thread.interrupt()
                }

                "reset" -> {
                    thread.interrupt()
                    thread = Thread {
                        k = 1.0
                        sum = BigDecimal(0.0)
                        while(!Thread.currentThread().isInterrupted){

                            if (k % 2 != 0.0) {
                                sum += BigDecimal(4.0/(k*2 - 1))
                            } else {
                                sum -= BigDecimal(4.0/(k*2 - 1))
                            }

                            if (k % 1000 == 0.0) {
                                handler.post(Runnable {
                                    textPi.text = sum.toString()
                                })
                            }

                            k += 1.0
                        }
                    }
                    thread.start()
                }
            }
        }

        return view
    }

}