package edu.gatech.seclass.sdpscramble;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class CreateValidWordScrambleTest extends ActivityTest{

    @Rule
    public ActivityTestRule<MainMenuActivity> mActivityTestRule = new ActivityTestRule<>(MainMenuActivity.class);

    @Test
    public void createValidWordScrambleActivityTest() {
        createUserAndLogin();

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.createWordScramble), isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.phraseCreation), isDisplayed()));
        appCompatEditText7.perform(replaceText("The cat is loud :-)."), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.clueCreation), isDisplayed()));
        appCompatEditText8.perform(replaceText("Test clue"), closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.scramble), withText("Scramble"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.submitWordScramble), withText("Submit"), isDisplayed()));
        appCompatButton6.perform(click());

        // Asserts that the scramble was created successfully, thus reaching the success page
        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.okWordScrambleCreation), withText("OK"), isDisplayed()));
        appCompatButton7.perform(click());
    }

}
