package at.jku.ce.bay.app;

import akka.actor.*;
import akka.japi.Procedure;
import at.jku.ce.bay.api.FilesFound;
import at.jku.ce.bay.api.GetFileNames;
import at.jku.ce.bay.api.GetStatus;
import at.jku.ce.bay.api.Publish;
import at.jku.ce.bay.helper.CEBayHelper;

import java.io.File;
import java.io.Serializable;

/**
 * Created by michaelortner on 09.01.17.
 */
public class Client extends UntypedActor {

    private final String path;
    //private ActorRef seederActor = null;
    private ActorRef registryRef = null;

    public interface Connected extends Serializable { }

    public static Props props() {
        return Props.create(Client.class);
    }

    public Client(String path) {
        this.path = path;
        sendIdentifyRequest();
    }

    private void sendIdentifyRequest() {
        getContext().actorSelection(path).tell(new Identify(path), getSelf());
        System.out.println("sendRequest");
    }

    @Override
    public void onReceive(Object message) {

        if(message instanceof ActorIdentity) {
            System.out.println("ActorIdentity");
            registryRef = ((ActorIdentity) message).getRef();
            if(registryRef == null) {
                System.out.println("Remote Ce-Bay Actor ist not available: " + this.path);
            }
            else {

                final ActorRef seeder = getContext().actorOf(
                        Props.create(SeederActor.class,"test.txt"));

                getSelf().tell(new Publish(
                        "Seeder Actor",
                        CEBayHelper.GetHash(new File("/Users/michaelortner/Documents/ws_uni/ce-bay/ce-bay_client/src/test.txt")),
                        CEBayHelper.GetRemoteActorRef(seeder)), null);

                System.out.println(registryRef);
                getContext().watch(registryRef);
                getContext().become(active, true);
            }
        } else if (message instanceof ReceiveTimeout) {
            sendIdentifyRequest();

        }
        //Publish the seeder to the server container
        else if(message instanceof Publish) {
            System.out.println("Publish Seeder Actor" + registryRef);
            registryRef.tell(message, getSelf());
        }
        else if(message instanceof GetFileNames) {
            getContext().actorSelection(path).tell(new GetFileNames(), getSelf());
            System.out.println(message);
        }
        else {
            System.out.println("Not ready yet");

        }
    }

    Procedure<Object> active = new Procedure<Object>() {

        public void apply(Object message) {

            //Publish the seeder to the server container
            if(message instanceof Publish) {
                System.out.println("Publish Seeder Actor");
                registryRef.tell(message, getSelf());
            }

            if(message instanceof GetStatus) {
                System.out.println("" + message);
            }

            if(message instanceof FilesFound) {
                System.out.println("FilesFound");
            }
            else {
                System.out.println("test");
            }

        }
    };
}
