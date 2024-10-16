package com.ardaslegends.service;

import com.ardaslegends.domain.AbstractDomainObject;
import com.ardaslegends.presentation.discord.config.BotProperties;
import com.ardaslegends.presentation.discord.utils.ALColor;
import com.ardaslegends.service.exceptions.ServiceException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Abstract service class providing common operations for domain objects.
 *
 * @param <T> The type of the domain object.
 * @param <R> The type of the JPA repository.
 */
@Slf4j
public abstract class AbstractService<T extends AbstractDomainObject, R extends JpaRepository<T, ?>> {
    private TextChannel errorChannel;

    @Getter
    private ExecutorService executorService;

    /**
     * Securely finds an entity using the provided identifier and function.
     *
     * @param <G>        The type of the identifier.
     * @param <A>        The return type of the function.
     * @param identifier The identifier to use in the function.
     * @param func       The function to apply.
     * @return The result of the function.
     * @throws ServiceException if the function is null or a database error occurs.
     */
    public <G, A> A secureFind(G identifier, Function<G, A> func) {
        if (func == null) {
            log.warn("SecureFind Function parameter is null!");
            throw ServiceException.passedNullFunction();
        }
        try {
            return func.apply(identifier);
        } catch (Exception pEx) {
            log.warn("Encountered Database Error while searching for entity, parameter [{}]", identifier);
            recordMessageInErrorChannel(pEx);
            throw ServiceException.secureFindFailed(identifier, pEx);
        }
    }

    /**
     * Securely finds an entity using the provided supplier function.
     *
     * @param <A>  The return type of the function.
     * @param func The supplier function to apply.
     * @return The result of the function.
     * @throws ServiceException if the function is null or a database error occurs.
     */
    public <A> A secureFind(Supplier<A> func) {
        if (func == null) {
            log.warn("SecureFind Function parameter is null!");
            throw ServiceException.passedNullFunction();
        }
        try {
            return func.get();
        } catch (Exception pEx) {
            log.warn("Encountered Database Error while searching for entity, parameter [{}]", func);
            recordMessageInErrorChannel(pEx);
            throw ServiceException.secureFindFailed(func, pEx);
        }
    }

    /**
     * Securely finds an entity using the provided identifier, other parameter, and bi-function.
     *
     * @param <G>        The type of the identifier.
     * @param <T>        The type of the other parameter.
     * @param <A>        The return type of the function.
     * @param identifier The identifier to use in the function.
     * @param other      The other parameter to use in the function.
     * @param func       The bi-function to apply.
     * @return The result of the function.
     * @throws ServiceException if the function is null or a database error occurs.
     */
    public <G, T, A> A secureFind(G identifier, T other, BiFunction<G, T, A> func) {
        if (func == null) {
            log.warn("SecureFind Function parameter is null!");
            throw ServiceException.passedNullFunction();
        }
        try {
            return func.apply(identifier, other);
        } catch (Exception pEx) {
            log.warn("Encountered Database Error while searching for entity, parameter [{}]", identifier);
            recordMessageInErrorChannel(pEx);
            throw ServiceException.secureFindFailed(identifier, pEx);
        }
    }

    /**
     * Securely saves an entity using the provided repository.
     *
     * @param entity     The entity to save.
     * @param repository The repository to use for saving.
     * @return The saved entity.
     * @throws ServiceException if a database error occurs.
     */
    public T secureSave(T entity, R repository) {
        try {
            return repository.save(entity);
        } catch (Exception pEx) {
            log.warn("Encountered Database Error while saving entity [{}]", entity);
            recordMessageInErrorChannel(pEx);
            throw ServiceException.cannotSaveEntity(entity, pEx);
        }
    }

    /**
     * Securely saves a collection of entities using the provided repository.
     *
     * @param entities   The entities to save.
     * @param repository The repository to use for saving.
     * @return The list of saved entities.
     * @throws ServiceException if a database error occurs.
     */
    public List<T> secureSaveAll(Collection<T> entities, R repository) {
        try {
            return repository.saveAll(entities);
        } catch (Exception pEx) {
            log.warn("Encountered Database Error while saving entity [{}]", entities);
            recordMessageInErrorChannel(pEx);
            throw ServiceException.cannotSaveEntity(entities, pEx);
        }
    }

    /**
     * Securely deletes an entity using the provided repository.
     *
     * @param entity     The entity to delete.
     * @param repository The repository to use for deleting.
     * @throws ServiceException if a database error occurs.
     */
    public void secureDelete(T entity, R repository) {
        try {
            repository.delete(entity);
        } catch (Exception pEx) {
            log.warn("Encountered Database Error while deleting entity[{}]", entity);
            recordMessageInErrorChannel(pEx);
            throw ServiceException.cannotSaveEntity(entity, pEx);
        }
    }

    /**
     * Securely joins a CompletableFuture.
     *
     * @param <G>               The type of the result.
     * @param completableFuture The CompletableFuture to join.
     * @return The result of the CompletableFuture.
     * @throws ServiceException if an exception occurs during the join.
     */
    public <G> G secureJoin(CompletableFuture<G> completableFuture) {
        Objects.requireNonNull(completableFuture);
        try {
            return completableFuture.join();
        } catch (Exception ex) {
            log.warn("Unexpected exception in join [{}]", ex.getMessage());
            recordMessageInErrorChannel(ex);
            throw ServiceException.joinException(ex);
        }
    }

    /**
     * Records a message in the error channel.
     *
     * @param throwable The throwable to record.
     */
    protected void recordMessageInErrorChannel(Throwable throwable) {
        val embed = new EmbedBuilder()
                .setTitle(throwable.getClass().getSimpleName())
                .setDescription(throwable.getMessage())
                .setTimestampToNow()
                .setColor(ALColor.RED);

        errorChannel.sendMessage(embed);
    }

    /**
     * Sets the error channel using the provided BotProperties.
     *
     * @param properties The BotProperties to use for setting the error channel.
     */
    @Autowired
    public final void setErrorChannel(BotProperties properties) {
        this.errorChannel = properties.getErrorChannel();
    }

    /**
     * Sets the executor service.
     *
     * @param executorService The ExecutorService to set.
     */
    @Autowired
    public final void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }
}