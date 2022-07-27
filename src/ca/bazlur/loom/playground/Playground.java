package ca.bazlur.loom.playground;

import java.util.concurrent.CompletableFuture;

public class Playground {

    public static void main(String[] args) throws InterruptedException {

    }

    sealed class ChatCommand {
    }

    final class ProcessMessage extends ChatCommand {
        private final String sender;
        private final String content;

        public ProcessMessage(String sender, String content) {
            this.sender = sender;
            this.content = content;
        }

        public String getSender() {
            return sender;
        }

        public String getContent() {
            return content;
        }
    }

    final class AddNewUser extends ChatCommand {
    }

}
