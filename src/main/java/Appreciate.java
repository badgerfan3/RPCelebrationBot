import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Appreciate implements MessageCreateListener {

    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        int loopBrokeAt = 0;
        String command = "";
        String arg1 = "";
        String arg2 = "";

        String idString = "";

        long commendeeID;

        for (int i = 0; i < message.getContent().length(); i++) {
            if (message.getContent().charAt(i) == ' ') {
                loopBrokeAt = i + 1;
                break;
            } else {
                command = command + message.getContent().charAt(i);
            }
        }
        if(command.equals(RPCelebrationBot.commandPrefix+"appreciate")){
            for(int i=loopBrokeAt; i <message.getContent().length(); i++){
                if(message.getContent().charAt(i) == ' '){
                    loopBrokeAt = i + 1;
                    break;
                } else if(i== message.getContent().length()-1){
                    loopBrokeAt = message.getContent().length();
                }
                else {
                    arg1 = arg1 + message.getContent().charAt(i);
                }
            }
            if(!arg1.equals("") && loopBrokeAt != message.getContent().length()){
                for(int i=loopBrokeAt; i<message.getContent().length(); i++){
                    arg2 = arg2 + message.getContent().charAt(i);
                }

                if(!arg2.equals("")) {
                    event.getChannel().sendMessage("Command: " + command + "\nArgument One: " + arg1 + "\nArgument Two: " + arg2);


                    if(arg1.charAt(0)=='<'&&arg1.charAt(1)=='@' && arg1.charAt(2)=='!'&& arg1.charAt(arg1.length()-1)=='>'){
                        System.out.println("Arg1 Type: ID");

                        for(int i=3; i<arg1.length()-1; i++){
                            idString = idString + arg1.charAt(i);
                        }

                        System.out.println("Arg1 ID: " + idString);

                        commendeeID = Long.parseLong(idString);

                        event.getChannel().sendMessage("ID of Commendee: " + commendeeID);


                        DatabaseInteraction.CounterDBInsert(commendeeID,message.getAuthor().getName().toString());
                    }
                }
            }
        }

    }
}
