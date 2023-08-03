package com.alphacircle.vroadway.ui.info

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlaylistAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.alphacircle.vroadway.R
import com.alphacircle.vroadway.ui.components.TopBar
import com.alphacircle.vroadway.ui.theme.KoreanTypography
import com.alphacircle.vroadway.ui.theme.VroadwayColors

@Composable
fun Info(
    onBackPress: () -> Unit
) {
    val surfaceColor = MaterialTheme.colors.surface

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val (
            backButton, bgImage, infoContainer
        ) = createRefs()

        AsyncImage(
            model = "https://newjeans.kr/imgs/getup/photos/NJ_GetUp_1.jpg",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .constrainAs(bgImage) {
                    start.linkTo(parent.start)
                },
//            colorFilter = ColorFilter.tint(colorResource(id = R.color.dark_filter), BlendMode.Darken)
        )

        BackButton(
            onBackPress = onBackPress,
            modifier = Modifier.constrainAs(backButton) {
            start.linkTo(parent.start, 8.dp)
            top.linkTo(parent.top, 32.dp)
            }
        )



        InfoSheet(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(40.dp, 40.dp, 0.dp, 0.dp))
                .background(MaterialTheme.colors.surface)
                .constrainAs(infoContainer) {
                    top.linkTo(bgImage.bottom, (-32).dp)
                }
                .fillMaxHeight()
        )



    }

}

@Composable
fun BackButton(onBackPress: () -> Unit, modifier: Modifier) {
    IconButton(
        onClick = onBackPress,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.cd_back),
            tint = Color.White
        )
    }
}

@Composable
fun InfoSheet(modifier: Modifier) {
    Column(
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(32.dp)) {
            Text(
                text = "Newjeans",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 16.dp, 0.dp, 8.dp),
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            )
            Text(
                text = "Super Shy",
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { heading() },
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface
            )

            Text(
                text = "2022년 7월 22일 데뷔한 뉴진스(NewJeans)는 데뷔 EP로 단숨에 국내외 차트를 석권했고, 이어 발표한 싱글 앨범으로 이어진 인기는, 한국 최대 음원 사이트인 멜론에서 역대 최장 기간 1위 기록을 비롯해 각종 글로벌 차트의 상위권을 점령하며 대세 인기를 증명했다. 두 장의 앨범 모두 밀리언셀러 반열에 올랐으며, 6곡의 스포티파이 합산 누적 스트리밍은 15억회를 넘어섰다. 전 세계 팬들은 다섯 멤버의 순수하고 자연스러운 매력, 에너지 넘치면서도 쿨한 퍼포먼스, 무엇보다 뉴진스가 추구하는 '좋은 음악'에 환호했다. 그리고 다시 여름이 돌아왔고, 뉴진스는 2nd EP 'Get Up'으로 돌아왔다. 'Get Up'에 담긴 6곡의 곡들은 프롤로그와 같은 'New Jeans'를 시작으로, 3개의 타이틀 곡 'Super Shy', 'ETA', 'Cool With You'로 이어지며, Interlude Track인 'Get Up'을 거쳐 에필로그처럼 'ASAP'로 마무리된다. 앨범 전체에 담긴 서사와 함께 각각의 곡이 담아내고 있는 이야기를 따라 전 곡을 감상해 보는 것을 추천한다.'Get Up'에서 보여지는 뉴진스는 더 성장한 모습으로, 여전히 자연스럽고 세련된 매력을 발산하며, 좋은 음악을 하고 있다. 뉴진스는 매일 찾게 되고 언제 입어도 질리지 않는 진(Jean)처럼 시대의 아이콘이 되겠다는 포부와 'New Gens'가 되겠다는 각오를 팀명에 담고 있다. 'Get Up'은 뉴진스의 포부와 각오가 그대로 담긴, 뉴진스의 본질을 담아낸 또 하나의 앨범이다. 1.New JeansNewJeans 특유의 트렌디하고 개성 있는 보컬에 기반하여 UK Garage 리듬과 Jersey Club 리듬을 오가는 독특한 구성이 인상적인 곡이다. 팀명인 NewJeans 와 동명의 곡임과 동시에 팀명을 활용한 독특한 가사와 멜로디 라인이 돋보인다.Composition: Jinsu Park, Frankie Scoca, Erika de Casier, Fine Glindvad Jensen Lyrics: Gigi, Erika de Casier, Fine Glindvad Jensen, HAERINInstrumental and Programming: Jinsu Park, Frankie Scoca2.Super ShyJersey Club 리듬과 Breakbeat에 기반한 독창적이고 실험적인 시도가 돋보이는 곡이다.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 40.dp),
                style = KoreanTypography.body2,
                color = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun InfoPreview() {
    Info {}
}