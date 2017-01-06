package at.jku.ce.bay.app;

import akka.actor.ActorRef;
import at.jku.ce.bay.helper.CEBayHelper;
import java.io.File;

public class App {

    public static void main(String[] args) {

        //liefert den für 'Publish' benötigten Hashwert
        CEBayHelper.GetHash(new File(""));

        //Liefert die Referenz zum CEBay Actor, der vom CE Inst. bereitgestellt wird
        CEBayHelper.GetRegistryActorRef();

        //Wird für die Umwandlung einer ActorRef in die zu versendende Stringrepräsentation benötigt
        CEBayHelper.GetRemoteActorRef(ActorRef.noSender());



    }

}
