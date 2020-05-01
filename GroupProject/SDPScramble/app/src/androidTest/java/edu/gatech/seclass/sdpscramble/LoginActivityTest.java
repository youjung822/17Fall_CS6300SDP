package edu.gatech.seclass.sdpscramble;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest extends ActivityTest {

    @Rule
    public ActivityTestRule<MainMenuActivity> intentRule = new ActivityTestRule<>(MainMenuActivity.class);

    @Test
    public void userCreationAndLoginTest() {
        String username = createUserAndLogin();

        // Verify Main Menu reached with the created user logged in
        onView(allOf(withId(R.id.usernameMainMenu), withText(username), isDisplayed()));
    }

}
