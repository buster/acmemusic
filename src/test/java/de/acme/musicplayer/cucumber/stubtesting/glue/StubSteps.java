package de.acme.musicplayer.cucumber.stubtesting.glue;

import de.acme.musicplayer.application.usecases.PlaySongUseCase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class StubSteps {

    @Autowired
    PlaySongUseCase playSongUseCase;

    @Given("something exists")
    public void somethingExists() {
    }

    @When("something happens")
    public void somethingHappens() {
        playSongUseCase.playSong("1");
    }

    @Then("something else happened")
    public void somethingElseHappened() {
    }
}
