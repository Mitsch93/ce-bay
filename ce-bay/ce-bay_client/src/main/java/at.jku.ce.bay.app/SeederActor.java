package at.jku.ce.bay.app;

import akka.actor.Props;
import akka.actor.UntypedActor;
import at.jku.ce.bay.api.GetFile;
import at.jku.ce.bay.api.GetStatus;
import at.jku.ce.bay.api.SeederFound;

/**
 * Created by michaelortner on 09.01.17.
 */
public class SeederActor extends UntypedActor {

    private String filePath = "test.txt";

    public SeederActor(String filePath) {
       this.filePath = filePath;
    }

    public static Props props() {
        return Props.create(Client.class);
    }

    @Override
    public void onReceive(Object message) {

        if(message instanceof GetStatus) {
            System.out.print(message);
            getSender().tell(message, getSelf());
        }

        if(message instanceof GetFile) {
            System.out.println("GetFile" + message);
            getSender().tell(message, getSelf());
        }
        else {
            System.out.println("testdf");
        }
    }
}