package com.interticket.app;



import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.interticket.app.fragments.WalletFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testEditProfile() {
        onView(withId(R.id.menuProfile)).perform(click());
        onView(withId(R.id.btnEdit)).perform(click());
        String name = "Adil";
        String surname = "Damir";
        String card = "4400 2234 2323 8503";
        String phone = "+77015067882";
        onView(withId(R.id.etFirstName)).perform(ViewActions.clearText());
        onView(withId(R.id.etFirstName)).perform(ViewActions.typeText(name));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.etLastName)).perform(ViewActions.clearText());
        onView(withId(R.id.etLastName)).perform(ViewActions.typeText(surname));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.etICNumber)).perform(ViewActions.clearText());
        onView(withId(R.id.etICNumber)).perform(ViewActions.typeText(card));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.etPhoneNumber)).perform(ViewActions.clearText());
        onView(withId(R.id.etPhoneNumber)).perform(ViewActions.typeText(phone));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.btnEdit)).perform(click());
        onView(withText(name)).check(matches(isDisplayed()));
        onView(withText(surname)).check(matches(isDisplayed()));
        onView(withText(card)).check(matches(isDisplayed()));
        onView(withText(phone)).check(matches(isDisplayed()));
    }

    @Test
    public void testRechargeTheWallet() {
        onView(withId(R.id.menuWallet)).perform(click());
        onView(withId(R.id.btnRecharge)).perform(click());
        Integer money = 100;
        onView(withId(R.id.etAmount)).perform(ViewActions.typeText(money.toString()));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btnSave)).perform(click());;
    }
}