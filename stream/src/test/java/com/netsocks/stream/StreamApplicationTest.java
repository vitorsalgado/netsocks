package com.netsocks.stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@SuppressWarnings("ConstantConditions")
@RunWith(JUnit4.class)
public class StreamApplicationTest {
    @Test
    public void itShouldReturn_e_whenTheString_aAbBABacafe_isProvided() {
        String input = "aAbBABacafe";
        Optional<Character> result = StreamApplication.firstChar(new StreamImpl(input));

        assertThat(result.isPresent(), equalTo(true));
        assertThat(result.get(), equalTo('e'));
    }

    @Test
    public void itShouldReturnTheFirstCharacterWithoutRepetition() {
        String input = "aAbBABacafeafuako";
        Optional<Character> result = StreamApplication.firstChar(new StreamImpl(input));

        assertThat(result.isPresent(), equalTo(true));
        assertThat(result.get(), equalTo('e'));
    }

    @Test
    public void itShouldReturnCorrectlyEvenIfThereAreNumbersOrSpecialCharsInString() {
        String input = "_aAb oJuB12aebJJkAB5d--))1dw6a#fe6ac%afezgh@1++=-";
        Optional<Character> result = StreamApplication.firstChar(new StreamImpl(input));

        assertThat(result.isPresent(), equalTo(true));
        assertThat(result.get(), equalTo('u'));
    }

    @Test
    public void itShouldReturnNoDataWhenEmptyStringIsProvided() {
        String input = "";
        Optional<Character> result = StreamApplication.firstChar(new StreamImpl(input));

        assertThat(result.isPresent(), equalTo(false));
    }

    @Test
    public void itShouldReturnNoDataWhenAnStringWithoutVowelsIsProvided() {
        String input = "bhgtWrLPPWqsdgg";
        Optional<Character> result = StreamApplication.firstChar(new StreamImpl(input));

        assertThat(result.isPresent(), equalTo(false));
    }

    @Test
    public void itShouldReturnNoDataWhenAnStringWithoutConsonantsIsProvided() {
        String input = "aUiiUUeEaaAA";
        Optional<Character> result = StreamApplication.firstChar(new StreamImpl(input));

        assertThat(result.isPresent(), equalTo(false));
    }
}
