import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.parimatchaso.theme.StatusBarColor
import com.example.parimatchaso.theme.SurfaceGradientFirst
import com.example.parimatchaso.theme.SurfaceGradientSecond
import com.example.parimatchaso.theme.SurfaceGradientThird
import com.example.teacher888.R
import kotlinx.coroutines.delay


@Composable
fun FakeScreen(navController:NavController= rememberNavController()) {
    val money = remember {
        mutableStateOf(500)
    }
    val showWinPage = remember {
        mutableStateOf(false)
    }
    val slotStates = remember { List(5) { mutableStateOf(R.drawable.slot_first) } }
    val context = LocalContext.current
    val spinState = remember { mutableStateOf(false) }
    val soundPool = remember {
        SoundPool.Builder()
            .setMaxStreams(1)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .build()
    }
    val enabled= remember {
        mutableStateOf(true)
    }
    val soundId = remember { soundPool.load(context, R.raw.button_sound, 1) }
    val soundWin = remember { soundPool.load(context, R.raw.machine_sound, 1) }


    Surface(modifier = Modifier.fillMaxSize()) {
        if (!showWinPage.value) {
            Image(
                painter = painterResource(id = R.drawable.fakescreen),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    color = Color.Transparent, modifier = Modifier
                        .height(200.dp)
                        .width(400.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.group), contentDescription = "")
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = money.value.toString(),
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 29.sp
                        )
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    SlotSurface(spinState)
                    SlotSurface(spinState)
                    SlotSurface(spinState)
                    SlotSurface(spinState)
                    SlotSurface(spinState)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(50.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.spin), contentDescription = "",
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp)
                            .clickable {
                                if (enabled.value) {
                                    money.value -= 100
                                    soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                                    spinState.value = true
                                    enabled.value=false
                                }
                            }
                    )
                    Image(painter = painterResource(id = R.drawable.stop), contentDescription = "",
                        modifier = Modifier
                            .height(100.dp)
                            .width(100.dp)
                            .clickable {
                                if (!enabled.value) {
                                    spinState.value = false
                                    soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
                                    checkWinning(slotStates, context, money, showWinPage)
                                    enabled.value=true
                                }
                            }
                    )
                }
            }
        }
        else{
            val counterTarget = remember { mutableStateOf(0) }
            soundPool.play(soundWin, 1f, 1f, 0, 0, 1f)
            val counter: Int by animateIntAsState(
                targetValue = counterTarget.value,
                animationSpec = tween(
                    durationMillis = 5000, // Здесь вы контролируете длительность анимации
                    easing = LinearEasing

                )
            )
            counterTarget.value = 500
            Image(painter = painterResource(id = R.drawable.win),
                contentDescription = "",
                contentScale = ContentScale.Crop)
            Column(verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Row(horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(10.dp)) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "", tint = Color.White,
                        modifier = Modifier
                            .clickable {
                                showWinPage.value = false
                            }
                            .size(30.dp)
                        )
                    Spacer(modifier = Modifier.fillMaxWidth(1f))
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.5f))
                Text(text =counter.toString() ,
                    fontSize = 100.sp,
                    fontWeight = FontWeight.Bold,
                    color = StatusBarColor
                )
                Spacer(modifier = Modifier.fillMaxHeight(0.5f))
            }

        }
    }
}


@Composable
fun SlotSurface(spinState: MutableState<Boolean>) {
    val slotImages = listOf(R.drawable.slot_first, R.drawable.slot_second, R.drawable.slot_third, R.drawable.slot_fourth, R.drawable.slot_fifth, R.drawable.slot_sixth,
        R.drawable.slot_seventh, R.drawable.slot_eigth, R.drawable.slot_ninth, R.drawable.slot_tenth, R.drawable.slot_eleven, R.drawable.slot_twelve, R.drawable.slot_theiertheen)
    val currentImages = remember { List(5) { mutableStateOf(slotImages.random()) } }

    LaunchedEffect(spinState.value) {
        while(spinState.value) {
            currentImages.forEach { it.value = slotImages.random() }
            delay(100)
        }
    }

    val boxSize = with(LocalDensity.current) { 600.dp.toPx() }
    Box(
        modifier = Modifier
            .height(274.dp)
            .width(55.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        SurfaceGradientFirst,
                        SurfaceGradientSecond,
                        SurfaceGradientSecond
                    ),
                    start = Offset(1f, 1f),
                    end = Offset(boxSize, boxSize)
                )
            )
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            currentImages.forEach { currentImage ->
                Image(
                    painter = painterResource(id = currentImage.value),
                    contentDescription = "",
                    modifier = Modifier.size(55.dp)
                )
            }
        }
    }
}


private var spinCounter = 0

private fun checkWinning(slotStates: List<MutableState<Int>>, context: Context, money: MutableState<Int>,
        showWin: MutableState<Boolean>
                         ) {
    money.value -= 25
    val slotImages = listOf(
        R.drawable.slot_first,
        R.drawable.slot_second,
        R.drawable.slot_third,
        R.drawable.slot_fourth,
        R.drawable.slot_fifth,
        R.drawable.slot_sixth,
        R.drawable.slot_seventh,
        R.drawable.slot_eigth,
        R.drawable.slot_ninth,
        R.drawable.slot_tenth,
        R.drawable.slot_eleven,
        R.drawable.slot_twelve,
        R.drawable.slot_theiertheen
    )

    val enhancedSlotImages = slotImages.toMutableList().apply {
        repeat(10) { add(R.drawable.slot_tenth) }
    }

    spinCounter++
    if(spinCounter == 10) {
        for (slotState in slotStates) {
            slotState.value = R.drawable.slot_tenth
        }
        spinCounter = 0
    } else {
        for (slotState in slotStates) {
            slotState.value = enhancedSlotImages.random()
        }
    }

    val winCount = slotStates.count { it.value == R.drawable.slot_tenth }
    when (winCount) {
        1 -> money.value += 100
        2 -> money.value += 200
        3 -> money.value += 300
        4 -> money.value += 400
        5 -> {
            showWin.value=true
            money.value += 500
        }
    }
}