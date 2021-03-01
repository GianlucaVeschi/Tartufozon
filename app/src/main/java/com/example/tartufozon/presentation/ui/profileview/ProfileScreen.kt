package com.example.tartufozon.presentation.ui.profileview

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tartufozon.R
import com.example.tartufozon.presentation.ui.Screens

private const val initialimageFloat = 170f
private val name = "Gianluca Veschi"
private val email = "gianluca.veschi00@gmail.com"
const val twitterUrl = "https://twitter.com/GianlucaVeschi"
const val linkedInUrl = "https://www.linkedin.com/in/gianlucaveschi/"
const val githubUrl = "https://github.com/GianlucaVeschi"
const val githubRepoUrl = "https://github.com/GianlucaVeschi/Tartufozon"

//NOTE: This stuff should usually be in a parent activity/Navigator
// We can pass callback to profileScreen to get the click.
private fun launchSocialActivity(context: Context, socialType: String) {
    val intent = when (socialType) {
        "github" -> Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl))
        "repository" -> Intent(Intent.ACTION_VIEW, Uri.parse(githubRepoUrl))
        "linkedin" -> Intent(Intent.ACTION_VIEW, Uri.parse(linkedInUrl))
        else -> Intent(Intent.ACTION_VIEW, Uri.parse(twitterUrl))
    }
    context.startActivity(intent)
}

@ExperimentalMaterialApi
@Composable
fun ProfileScreen() {

    val navController : NavHostController = rememberNavController()

    NavHost(navController, startDestination = Screens.ProfileScreen.route) {
        composable(Screens.ProfileScreen.route) { ProfileScreenContent(navController) }
    }
}

@ExperimentalMaterialApi
@Composable
fun ProfileScreenContent(navController: NavController){
    Scaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .semantics { testTag = "Profile Screen" }
        ) {
            val scrollState = rememberScrollState(0f)
            TopAppBarView(scrollState.value)
            TopBackground()
            Column(modifier = Modifier.fillMaxSize().verticalScroll(state = scrollState)) {
                Spacer(modifier = Modifier.height(100.dp))
                TopScrollingContent(scrollState)
                BottomScrollingContent(navController)
            }
        }
    }
}
@Composable
fun TopScrollingContent(scrollState: ScrollState) {
    val visibilityChangeFloat = scrollState.value > initialimageFloat - 20
    Row {
        AnimatedImage(scroll = scrollState.value)
        Column(
            modifier = Modifier
                .padding(start = 8.dp, top = 48.dp)
                .alpha(animateFloatAsState(if (visibilityChangeFloat) 0f else 1f).value)
        ) {
            Text(
                text = name,
                style = typography.h6.copy(fontSize = 18.sp),
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "Android developer at Thalia Gmbh",
                style = typography.subtitle2
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun BottomScrollingContent(navController: NavController) {
    Column(modifier = Modifier
        .background(MaterialTheme.colors.surface)
        .padding(8.dp)) {
        SocialRow()
        Text(
            text = "About Me",
            style = typography.h6,
            modifier = Modifier.padding(start = 8.dp, top = 12.dp)
        )
        Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
        Text(
            text = stringResource(id = R.string.about_me),
            style = typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        InterestsSection()
        MyPhotosSection()
        Text(
            text = "About Project",
            style = typography.h6,
            modifier = Modifier.padding(start = 8.dp, top = 16.dp)
        )
        Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
        Text(
            text = stringResource(id = R.string.about_project),
            style = typography.body1,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        )
        MoreInfoSection(navController)
    }
}

@Composable
fun SocialRow() {
    Card(elevation = 8.dp, modifier = Modifier.padding(8.dp)) {
        val context = LocalContext.current
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 16.dp)
        ) {
            IconButton(onClick = { launchSocialActivity(context, "github") }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_github_square_brands),
                    "github icon"
                )
            }
            IconButton(onClick = { launchSocialActivity(context, "twitter") }) {
                Icon(painter = painterResource(id = R.drawable.ic_twitter_square_brands),"twitter icon")
            }
            IconButton(onClick = { launchSocialActivity(context, "linkedin") }) {
                Icon(painter = painterResource(id = R.drawable.ic_linkedin_brands), "linkedin icon")
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MoreInfoSection(navController: NavController) {
    val context = LocalContext.current
    Text(
        text = "More Info",
        style = typography.h6,
        modifier = Modifier.padding(start = 8.dp, top = 16.dp)
    )
    Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
    ListItem(
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_github_square_brands),
                contentDescription = "item icon",
                modifier = Modifier.preferredSize(24.dp)
            )
        },
        text = {
            Text(
                text = "Compose Cookbook github",
                style = typography.body1.copy(fontWeight = FontWeight.Bold)
            )
        },
        secondaryText = { Text(text = "Tap to checkout the repo for the project") },
        modifier = Modifier
            .clickable(onClick = {
                launchSocialActivity(context, "repository")
            })
    )
    ListItem(
        icon = { Icon(imageVector = Icons.Rounded.Email, contentDescription = "item icon",) },
        text = {
            Text(
                text = "Contact Me",
                style = typography.body1.copy(fontWeight = FontWeight.Bold)
            )
        },
        secondaryText = { Text(text = "Tap to write me about any concern or info at $email") },
        modifier = Modifier
            .clickable(onClick = { launchSocialActivity(context, "repository") })
    )
    ListItem(
        icon = { Icon(imageVector = Icons.Rounded.Settings, contentDescription = "item icon",) },
        text = {
            Text(
                text = "Demo Settings",
                style = typography.body1.copy(fontWeight = FontWeight.Bold)
            )
        },
        secondaryText = { Text(text = "Not included yet. coming soon..") },
        modifier = Modifier.clickable(onClick = {})
    )
}

@Composable
fun InterestsSection() {
    Text(
        text = "My Interests",
        style = typography.h6,
        modifier = Modifier.padding(start = 8.dp, top = 16.dp)
    )
    Divider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp))
    Row(modifier = Modifier.padding(start = 8.dp, top = 8.dp)) {
        InterestTag("Android")
        InterestTag("Compose")
        InterestTag("Skateboarding")
        InterestTag("MTB")
    }
    Row(modifier = Modifier.padding(start = 8.dp)) {
        InterestTag("Girls")
        InterestTag("Programming")
        InterestTag("Finance")
    }
}

@Composable
fun TopAppBarView(scroll: Float) {
    if (scroll > initialimageFloat + 5) {
        TopAppBar(
            title = {
                Text(text = name)
            },
            navigationIcon = {
                Image(
                    painter = painterResource(id = R.drawable.p1),
                    contentDescription = "top bar profile image",
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .preferredSize(32.dp)
                        .clip(CircleShape)
                )
            },
            actions = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "actions icon",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        )
    }
}

@Composable
fun AnimatedImage(scroll: Float) {
    val dynamicAnimationSizeValue = (initialimageFloat - scroll).coerceIn(36f, initialimageFloat)
    Image(
        painter = painterResource(id = R.drawable.p1),
        contentDescription = "animated image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(start = 16.dp)
            .preferredSize(animateDpAsState(Dp(dynamicAnimationSizeValue)).value)
            .clip(CircleShape)
    )
}

@Composable
private fun TopBackground() {
    Spacer(
        modifier = Modifier
            .preferredHeight(150.dp)
            .fillMaxWidth()
    )
}

@ExperimentalMaterialApi
@Preview
@Composable
fun ShowProfileScreen() {
    ProfileScreen()
}
