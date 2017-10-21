package edu.gatech.seclass.sdpscramble;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
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
import static org.hamcrest.Matchers.startsWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class InProgressScrambleTest extends ActivityTest {

    @Rule
    public ActivityTestRule<MainMenuActivity> mActivityTestRule = new ActivityTestRule<>(MainMenuActivity.class);

    @Test
    public void inProgressScrambleTest() {
        createUserAndLogin();

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.solveWordScrambles), isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(android.R.id.text1), withText(startsWith("[Scramble0]")),
                        childAtPosition(
                                withId(R.id.unsolvedWordScramblesList),
                                0),
                        isDisplayed()));
        String scramble = getText(
                allOf(withId(android.R.id.text1),
                        childAtPosition(
                                withId(R.id.unsolvedWordScramblesList),
                                0)));
        appCompatTextView.perform(click());

        String guess = "test-guess";

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.scrambleGuess), isDisplayed()));
        appCompatEditText6.perform(replaceText(guess), closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.submitGuess), withText("Submit"), isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.exitGame), withText("Back to Main Menu"), isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.solveWordScrambles), isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("IN PROGRESS - " + scramble),
                        childAtPosition(
                                withId(R.id.unsolvedWordScramblesList),
                                0),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        onView(allOf(withId(R.id.scrambleGuess), withText(guess)));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
