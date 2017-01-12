package at.jku.ce.bay.app;

import akka.actor.*;
import at.jku.ce.bay.helper.CEBayHelper;
import at.jku.ce.bay.api.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;

public class App {

    public static void main(String[] args) {

        startClientActorSystem();
    }

    public static void startClientActorSystem() {

        System.out.println("Starting Client Actor System");
        final ActorSystem system = ActorSystem.create("ClientActorSystem");

        final String path = CEBayHelper.GetRegistryActorRef();

        final ActorRef actor = system.actorOf(
                Props.create(Client.class, path), "ClientActor");

        actor.tell(new GetFileNames(),null);

        /*final ActorRef seeder = system.actorOf(
                Props.create(SeederActor.class,"test.txt"));

        actor.tell(new Publish(
                "Seeder Actor",
                CEBayHelper.GetHash(new File("/Users/michaelortner/Documents/ws_uni/ce-bay/ce-bay_client/src/test.txt")),
                CEBayHelper.GetRemoteActorRef(seeder)), null);*/
    }

}
