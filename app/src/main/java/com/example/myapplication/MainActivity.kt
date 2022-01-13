package com.example.myapplication

import android.app.usage.UsageEvents
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val dragXAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.drag,
            DynamicAnimation.X
        )
    }
    private val dragYAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.drag,
            DynamicAnimation.Y
        )
    }
    private val firstXAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.first,
            DynamicAnimation.X
        )
    }
    private val firstYAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.first,
            DynamicAnimation.Y
        )
    }
    private val secondXAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.second,
            DynamicAnimation.X
        )
    }
    private val secondYAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.second,
            DynamicAnimation.Y
        )
    }
    private val thirdXAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.third,
            DynamicAnimation.X
        )
    }
    private val thirdYAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.third,
            DynamicAnimation.Y
        )
    }
    private val fourthXAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.fourth,
            DynamicAnimation.X
        )
    }
    private val fourthYAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.fourth,
            DynamicAnimation.Y
        )
    }
    private val fifthXAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.fifth,
            DynamicAnimation.X
        )
    }
    private val fifthYAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.fifth,
            DynamicAnimation.Y
        )
    }
    private val sixthXAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.sixth,
            DynamicAnimation.X
        )
    }
    private val sixthYAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.sixth,
            DynamicAnimation.Y
        )
    }
    private val seventhXAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.seventh,
            DynamicAnimation.X
        )
    }
    private val seventhYAnim by lazy(UsageEvents.Event.NONE) {
        createSpringAnimation(
            binding.seventh,
            DynamicAnimation.Y
        )
    }

    private var dX = 0f
    private var dY = 0f
    private var positionX = 0f
    private var positionY = 0f

    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

        positionX =
            (this.resources.displayMetrics.widthPixels - (this.resources.displayMetrics.widthPixels / 5)).toFloat()
        positionY = (this.resources.displayMetrics.heightPixels / 2).toFloat()
        runBlocking {
            setupAnimation()
            dragXAnim.animateToFinalPosition(positionX)
            dragYAnim.animateToFinalPosition(positionY)
            job = GlobalScope.launch(Dispatchers.Main) {
                delay(1000)
                val maxY = positionY + 250
                val minY = positionY - 250
                var y = positionY
                var up = true
                while (true) {
                    if (up == true) {
                        y = y + 60
                        delay(34)
                        dragYAnim.animateToFinalPosition(y)
                    } else {
                        y = y - 60
                        delay(34)
                        dragYAnim.animateToFinalPosition(y)
                    }
                    if (y >= maxY)
                        up = false
                    if (y <= minY)
                        up = true
                }
            }
        }
        setStiffness(200f)
        setDampingRatio(0.65f)
    }

    private fun initView() {
        dragXAnim.onUpdate { value ->
            firstXAnim.animateToFinalPosition(value - binding.drag.width - 10)
        }
        dragYAnim.onUpdate { value ->
            firstYAnim.animateToFinalPosition(value + ((binding.drag.height - binding.first.height) / 2))
        }
        firstXAnim.onUpdate { value ->
            secondXAnim.animateToFinalPosition(value - binding.first.width - 10)
        }
        firstYAnim.onUpdate { value ->
            secondYAnim.animateToFinalPosition(value + ((binding.first.height - binding.second.height) / 2))
        }
        secondXAnim.onUpdate { value ->
            thirdXAnim.animateToFinalPosition(value - binding.second.width - 10)
        }
        secondYAnim.onUpdate { value ->
            thirdYAnim.animateToFinalPosition(value + ((binding.second.height - binding.third.height) / 2))
        }
        thirdXAnim.onUpdate { value ->
            fourthXAnim.animateToFinalPosition(value - binding.third.width - 10)
        }
        thirdYAnim.onUpdate { value ->
            fourthYAnim.animateToFinalPosition(value + ((binding.third.height - binding.fourth.height) / 2))
        }
        fourthXAnim.onUpdate { value ->
            fifthXAnim.animateToFinalPosition(value - binding.fourth.width - 10)
        }
        fourthYAnim.onUpdate { value ->
            fifthYAnim.animateToFinalPosition(value + ((binding.fourth.height - binding.fifth.height) / 2))
        }
        fifthXAnim.onUpdate { value ->
            sixthXAnim.animateToFinalPosition(value - binding.fifth.width - 10)
        }
        fifthYAnim.onUpdate { value ->
            sixthYAnim.animateToFinalPosition(value + ((binding.fifth.height - binding.sixth.height) / 2))
        }
        sixthXAnim.onUpdate { value ->
            seventhXAnim.animateToFinalPosition(value - binding.sixth.width - 10)
        }
        sixthYAnim.onUpdate { value ->
            seventhYAnim.animateToFinalPosition(value + ((binding.sixth.height - binding.seventh.height) / 2))
        }
    }

    private fun setupAnimation() {
        binding.drag.setOnTouchListener { view, event ->
            job?.cancel()
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    dX = view.x - event.rawX
                    dY = view.y - event.rawY
                }

                MotionEvent.ACTION_MOVE -> {
                    val newX = event.rawX + dX
                    val newY = event.rawY + dY
                    dragXAnim.animateToFinalPosition(newX)
                    dragYAnim.animateToFinalPosition(newY)
                }
                MotionEvent.ACTION_UP -> {
                    job = GlobalScope.launch(Dispatchers.Main) {
                        delay(5000)
                        dragXAnim.animateToFinalPosition(positionX)
                        dragYAnim.animateToFinalPosition(positionY)
                        delay(1000)
                        val maxY = positionY + 250
                        val minY = positionY - 250
                        var y = positionY
                        var up = true
                        while (true) {
                            if (up == true) {
                                y = y + 60
                                delay(34)
                                dragYAnim.animateToFinalPosition(y)
                            } else {
                                y = y - 60
                                delay(34)
                                dragYAnim.animateToFinalPosition(y)
                            }
                            if (y >= maxY)
                                up = false
                            if (y <= minY)
                                up = true
                        }
                    }
                }
            }
            return@setOnTouchListener true
        }
    }

    private fun <K> createSpringAnimation(
        obj: K,
        property: FloatPropertyCompat<K>
    ): SpringAnimation {
        return SpringAnimation(obj, property).setSpring(SpringForce())
    }

    private inline fun SpringAnimation.onUpdate(crossinline onUpdate: (value: Float) -> Unit): SpringAnimation {
        return this.addUpdateListener { _, value, _ ->
            onUpdate(value)
        }
    }

    private fun setStiffness(stiffness: Float) {
        firstXAnim.spring.stiffness = stiffness
        firstYAnim.spring.stiffness = stiffness
        secondXAnim.spring.stiffness = stiffness
        secondYAnim.spring.stiffness = stiffness
        thirdXAnim.spring.stiffness = stiffness
        thirdYAnim.spring.stiffness = stiffness
        fourthXAnim.spring.stiffness = stiffness
        fourthYAnim.spring.stiffness = stiffness
        fifthXAnim.spring.stiffness = stiffness
        fifthYAnim.spring.stiffness = stiffness
        sixthXAnim.spring.stiffness = stiffness
        sixthYAnim.spring.stiffness = stiffness
        seventhXAnim.spring.stiffness = stiffness
        seventhYAnim.spring.stiffness = stiffness
    }

    private fun setDampingRatio(dampingRatio: Float) {
        firstXAnim.spring.dampingRatio = dampingRatio
        firstYAnim.spring.dampingRatio = dampingRatio
        secondXAnim.spring.dampingRatio = dampingRatio
        secondYAnim.spring.dampingRatio = dampingRatio
        thirdXAnim.spring.dampingRatio = dampingRatio
        thirdYAnim.spring.dampingRatio = dampingRatio
        fourthXAnim.spring.dampingRatio = dampingRatio
        fourthYAnim.spring.dampingRatio = dampingRatio
        fifthXAnim.spring.dampingRatio = dampingRatio
        fifthYAnim.spring.dampingRatio = dampingRatio
        sixthXAnim.spring.dampingRatio = dampingRatio
        sixthYAnim.spring.dampingRatio = dampingRatio
        seventhXAnim.spring.dampingRatio = dampingRatio
        seventhYAnim.spring.dampingRatio = dampingRatio
    }
}