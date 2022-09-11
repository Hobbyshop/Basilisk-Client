package me.hobbyshop.basilisk.util

import net.minecraft.util.MathHelper
import kotlin.math.sin

object MathUtils {

    private val a = DoubleArray(65536)

    private val b = DoubleArray(360)

    init {
        var i = 0
        while (i < 65536) {
            a[i] = sin(i * Math.PI * 2.0 / 65536.0)
            ++i
        }

        i = 0
        while (i < 360) {
            b[i] = sin(Math.toRadians(i.toDouble()))
            ++i
        }
    }

    fun getAngle(paramInt: Int): Double {
        var paramInt = paramInt
        paramInt %= 360
        return b[paramInt]
    }

    fun getRightAngle(paramInt: Int): Double {
        var paramInt = paramInt
        paramInt += 90
        paramInt %= 360
        return b[paramInt]
    }

    private fun snapToStep(value: Float, valueStep: Float): Float {
        var value = value
        if (valueStep > 0.0f) value = valueStep * Math.round(value / valueStep)
        return value
    }

    fun normalizeValue(p_148266_1_: Float, valueMin: Float, valueMax: Float, valueStep: Float): Float {
        return MathHelper.clamp_float(
            (snapToStepClamp(
                p_148266_1_,
                valueMin,
                valueMax,
                valueStep
            ) - valueMin) / (valueMax - valueMin), 0.0f, 1.0f
        )
    }

    private fun snapToStepClamp(value: Float, valueMin: Float, valueMax: Float, valueStep: Float): Float {
        var value = value
        value = snapToStep(value, valueStep)
        return MathHelper.clamp_float(value, valueMin, valueMax)
    }

    fun denormalizeValue(value: Float, valueMin: Float, valueMax: Float, valueStep: Float): Float {
        return snapToStepClamp(
            valueMin + (valueMax - valueMin) * MathHelper.clamp_float(value, 0.0f, 1.0f),
            valueMin,
            valueMax,
            valueStep
        )
    }

}