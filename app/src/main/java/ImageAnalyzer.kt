// ResNetでの判定処理ファイル

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.TensorImage
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import org.tensorflow.lite.DataType

class ImageAnalyzer(context: Context) {
    // TensorFlow Liteのインタープリター（モデルを実行するためのクラス）
    private val interpreter: Interpreter

    // クラス名（生物の名称）を保持するリスト
    private val labels: List<String>

    init {
        // モデルファイルをロード
        interpreter = try {
            val modelFile = loadModelFile(context, "resnet50_model.tflite")
            Log.i("ImageAnalyzer", "モデルをロードできました")
            Interpreter(modelFile)
        } catch (e: Exception) {
            Log.e("ImageAnalyzer", "モデルをロードできませんでした", e)
            throw RuntimeException("モデルをロードできませんでした", e)
        }

        // クラス名ファイルをロード(生物の名称)
        labels = try {
            loadLabels(context, "classes.txt")

        } catch (e: Exception) {
            Log.e("ImageAnalyzer", "クラス名ファイルをロードできませんでした", e)
            throw RuntimeException("クラス名ファイルをロードできませんでした", e)
        }
    }

    // モデルファイルをロードする関数
    private fun loadModelFile(context: Context, modelName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength

        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength).also {
            inputStream.close()
            fileChannel.close()
        }
    }

    // クラス名ファイルをロードする関数
    private fun loadLabels(context: Context, fileName: String): List<String> {
        return context.assets.open(fileName).bufferedReader().useLines { it.toList() }
    }

    // 画像を解析する関数
    fun analyzePhoto(bitmap: Bitmap): String {
        return try {
            // 画像の解析の開始を確認
            Log.d("ImageAnalyzer", "analyzePhoto_Start")

            // モデルの入力サイズに合わせて画像をリサイズ
            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
            val tensorImage = TensorImage(DataType.FLOAT32)
            tensorImage.load(resizedBitmap)
            Log.d("ImageAnalyzer", "analyzePhoto_1")

            // バッファの形式を確認
            val byteBuffer = tensorImage.tensorBuffer.buffer
            Log.d("ImageAnalyzer", "TensorImage buffer: $byteBuffer")
            Log.d("ImageAnalyzer", "Buffer capacity: ${byteBuffer.capacity()}")

            // 確認のためにバッファのサイズを出力
            val inputShape = interpreter.getInputTensor(0).shape()
            val inputSize = inputShape.reduce { acc, i -> acc * i }
            Log.d("ImageAnalyzer", "Expected input size: $inputSize")

            val output = Array(1) { FloatArray(labels.size) }
            Log.d("ImageAnalyzer", "analyzePhoto_2")

            // モデルを実行して結果を取得
            interpreter.run(byteBuffer, output)
            Log.d("ImageAnalyzer", "analyzePhoto_3")
            val maxIndex = output[0].indices.maxByOrNull { output[0][it] } ?: -1
            Log.d("ImageAnalyzer", "analyzePhoto_4")
            if (maxIndex != -1) labels[maxIndex] else "Unknown"

        } catch (e: Exception) {        // 画像の解析に失敗した場合(エラーログ)
            Log.e("ImageAnalyzer", "Error analyzing photo", e)
            "Error analyzing photo"
        }
    }
}