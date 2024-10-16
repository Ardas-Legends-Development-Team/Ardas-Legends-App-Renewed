package com.ardaslegends.service.discord.messages;

import jakarta.validation.constraints.NotNull;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.List;

/**
 * Represents a message to be sent to Discord, containing a message builder and a list of embed builders.
 */
public record ALMessage(MessageBuilder message, @NotNull List<EmbedBuilder> embeds) {

    /**
     * Constructs an ALMessage record.
     *
     * @param message The message builder for the message.
     * @param embeds  The list of embed builders for the message.
     * @throws NullPointerException if the embeds list is null.
     */
    public ALMessage {
        if (message != null)
            message.addEmbeds(embeds);
    }

    /**
     * Checks if the message builder is not null.
     *
     * @return true if the message builder is not null, false otherwise.
     */
    public boolean hasMessage() {
        return message != null;
    }

    /**
     * Returns a string representation of the ALMessage.
     *
     * @return A string representation of the ALMessage.
     */
    @Override
    public String toString() {
        return "ALMessage{" +
                "message=" + message +
                ", embeds=" + embeds +
                '}';
    }
}