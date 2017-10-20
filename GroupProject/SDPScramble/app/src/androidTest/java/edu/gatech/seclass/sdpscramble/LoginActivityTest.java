package edu.gatech.seclass.sdpscramble;


import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<MainMenuActivity> intentRule = new ActivityTestRule<>(MainMenuActivity.class);

    @Test
    public void userCreationAndLoginTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.signup), withText("Sign Up"), isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.firstName), isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.firstName), isDisplayed()));
        appCompatEditText2.perform(replaceText("test-first-name"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.lastName), isDisplayed()));
        appCompatEditText3.perform(replaceText("test-last-name"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.email), isDisplayed()));
        appCompatEditText4.perform(replaceText("test@test.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.usernameInput), isDisplayed()));
        appCompatEditText5.perform(replaceText("test-username"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.addPlayer), withText("Submit"), isDisplayed()));
        appCompatButton2.perform(click());

        String returnedUsername = getText(withId(R.id.userName));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.okPlayerCreation), withText("OK"), isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.usernameLogin), isDisplayed()));
        appCompatEditText6.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.usernameLogin), isDisplayed()));
        appCompatEditText7.perform(replaceText(returnedUsername), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.usernameLogin), withText(returnedUsername), isDisplayed()));
        appCompatEditText8.perform(pressImeActionButton());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.login), withText("Login"), isDisplayed()));
        appCompatButton4.perform(click());

        // Verify Main Menu reached with the created user logged in
        onView(allOf(withId(R.id.usernameMainMenu), withText(returnedUsername), isDisplayed()));
    }

    /*
     * Sourced from https://stackoverflow.com/questions/23381459/how-to-get-text-from-textview-using-espresso
     * Written by https://stackoverflow.com/users/103258/haffax
     */
    private String getText(final Matcher<View> matcher) {
        final String[] stringHolder = { null };
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView)view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

}
