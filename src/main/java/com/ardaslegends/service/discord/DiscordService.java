package com.ardaslegends.service.discord;

import com.ardaslegends.presentation.discord.config.BotProperties;
import com.ardaslegends.repository.exceptions.NotFoundException;
import com.ardaslegends.service.discord.messages.ALMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Service class for handling Discord-related operations such as retrieving roles and users, and sending messages to specific channels.
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class DiscordService {

    private final BotProperties botProperties;
    private final DiscordApi discordApi;

    /**
     * Retrieves a Discord role by its ID.
     *
     * @param roleId The ID of the Discord role.
     * @return An {@link Optional} containing the found role, or empty if not found.
     */
    public Optional<Role> getRoleById(Long roleId) {
        log.debug("Getting discord role with id [{}]", roleId);
        val foundRole = discordApi.getRoleById(roleId);

        if (foundRole.isPresent())
            log.debug("Found role [{}]", foundRole.get().getName());
        else
            log.debug("No role with id [{}] found", roleId);
        return foundRole;
    }

    /**
     * Retrieves a Discord role by its ID or throws a {@link NotFoundException} if the role is not found.
     *
     * @param roleId The ID of the Discord role.
     * @return The found Discord role.
     * @throws NotFoundException if the role is not found.
     */
    public Role getRoleByIdOrElseThrow(Long roleId) {
        log.debug("Getting discord role with id [{}]", roleId);
        val foundRole = discordApi.getRoleById(roleId);

        if (foundRole.isEmpty()) {
            log.warn("Could not find discord role [{}]", roleId);
            throw NotFoundException.genericNotFound("discord role", "id", roleId.toString());
        }
        return foundRole.get();
    }

    /**
     * Retrieves a Discord user by their ID.
     *
     * @param discordId The ID of the Discord user.
     * @return The found Discord user.
     */
    public User getUserById(String discordId) {
        log.debug("Getting discord user with id [{}]", discordId);
        return discordApi.getUserById(discordId).join();
    }

    /**
     * Sends a message to the Roleplay Channel.
     *
     * @param message The message to be sent.
     */
    public void sendMessageToRpChannel(ALMessage message) {
        log.debug("Trying to send message [{}] to Roleplay Channel", message);
        sendMessage(message, botProperties.getGeneralRpCommandsChannel());
    }

    /**
     * Sends a message to the Claimbuild Application Channel.
     *
     * @param message The message to be sent.
     * @return The sent message.
     */
    public Message sendMessageToClaimbuildAppChannel(ALMessage message) {
        log.debug("Trying to send message [{}] to Claimbuild Application Channel", message);
        return sendMessage(message, botProperties.getClaimbuildAppsChannel());
    }

    /**
     * Sends a message to the RpChar Application Channel.
     *
     * @param message The message to be sent.
     * @return The sent message.
     */
    public Message sendMessageToRpCharAppChannel(ALMessage message) {
        log.debug("Trying to send message [{}] to RpChar Application Channel", message);
        return sendMessage(message, botProperties.getRpAppsChannel());
    }

    /**
     * Sends a message to a specified channel.
     *
     * @param message The message to be sent.
     * @param channel The channel to which the message will be sent.
     * @return The sent message.
     * @throws NullPointerException if the message or channel is null.
     */
    private Message sendMessage(ALMessage message, TextChannel channel) {
        log.debug("Trying to send message [{}] to channel [{}]", message, channel.getIdAsString());

        Objects.requireNonNull(message, "Message was null!");
        Objects.requireNonNull(channel, "TextChannel was null!");

        Message returnMessage;
        if (message.hasMessage()) {
            log.debug("Message has a message [{}]", message.message());
            returnMessage = message.message().send(channel).join();
        } else {
            log.debug("Message only consists of {} embeds [{}]", message.embeds().size(), message.embeds());
            returnMessage = channel.sendMessage(message.embeds()).join();
        }

        return returnMessage;
    }
}