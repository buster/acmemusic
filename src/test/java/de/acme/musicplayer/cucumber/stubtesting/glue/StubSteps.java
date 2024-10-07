package de.acme.musicplayer.cucumber.stubtesting.glue;

import de.acme.musicplayer.application.usecases.LiedAbspielenUseCase;
import io.cucumber.java.de.Dann;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class StubSteps {

    @Autowired
    LiedAbspielenUseCase liedAbspielenUseCase;

    @Given("something exists")
    public void somethingExists() {
    }

    @When("something happens")
    public void somethingHappens() {
        liedAbspielenUseCase.playSong("1");
    }

    @Then("something else happened")
    public void somethingElseHappened() {
    }


}
