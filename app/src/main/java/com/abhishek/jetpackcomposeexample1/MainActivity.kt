package com.abhishek.jetpackcomposeexample1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhishek.jetpackcomposeexample1.data.datasource.Message
import com.abhishek.jetpackcomposeexample1.data.datasource.SampleData
import com.abhishek.jetpackcomposeexample1.ui.theme.JetpackComposeExample1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeExample1Theme {
                Conversation(messages = SampleData.conversationSample)
                //MessageCard(Message("Abhishek", "Jetpack Compose"))
            }
        }
    }

    @Composable
    fun MessageCard(message: Message) {
        /**
         * Column block to arrange elements vertically
         * Row block to arrange elements horizontally
         * Box block to stack elements
         */

        // Padding around Message
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.sachin),
                contentDescription = "Profile Picture",
                // image size to 40dp
                modifier = Modifier
                    .size(40.dp)
                    //clipped image for circle shape
                    .clip(RectangleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, RectangleShape)
            )
            // horizontal spacer between Image and Column block
            Spacer(modifier = Modifier.width(8.dp))

            // Composable functions can store local state in memory by using remember, and track changes to the value passed to mutableStateOf. Composables (and their children) using this state will get redrawn automatically when the value is updated. This is called recomposition
            var isExpanded by remember {
                mutableStateOf(false)
            }
// surfaceColor will be updated gradually from one color to the other
            val surfaceColor by animateColorAsState(
                if (isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface,
            )
            // We toggle the isExpanded variable when we click on this Column
            Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
                Text(
                    text = message.author,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )
                // vertical spacer between textAuthor and textBody
                Spacer(modifier = Modifier.height(3.dp))
                Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp,
                    // surfaceColor color will be changing gradually from primary to surface
                    color = surfaceColor,
                    // animateContentSize will change the Surface size gradually
                    modifier = Modifier.animateContentSize().padding(1.dp)) {
                    Text(
                        text = message.body,
                        modifier = Modifier.padding(all = 4.dp),
                        color = MaterialTheme.colors.secondaryVariant,
                        style = MaterialTheme.typography.body1,
                        // If the message is expanded, we display all its content
                        // otherwise we only display the first line
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1
                    )
                }
            }
        }
    }

    @Composable
    fun Conversation(messages: List<Message>) {
        LazyColumn {
            items(messages) { message ->
                MessageCard(message = message)
            }
        }
    }

    @Preview(name = "Light Mode")
    @Preview(
        name = "Dark Mode",
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true
    )
    @Composable
    fun MessageCardPreview() {
        JetpackComposeExample1Theme {
            Conversation(messages = SampleData.conversationSample)
            //MessageCard(Message("Abhishek", "Jetpack Compose"))
        }
    }
}