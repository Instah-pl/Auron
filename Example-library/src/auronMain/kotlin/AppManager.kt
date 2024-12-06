import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import pl.instah.auron.App

object AppManager {
    var counter by mutableStateOf(0)

    fun click() {
        if (counter == 9) {
            App.quit()
            return
        }

        counter++
    }
}