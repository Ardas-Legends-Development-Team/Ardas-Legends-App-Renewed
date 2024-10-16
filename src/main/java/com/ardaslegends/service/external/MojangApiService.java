package com.ardaslegends.service.external;

import com.ardaslegends.service.dto.player.UUIDConverterDto;
import com.ardaslegends.service.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Service
public class MojangApiService {

    private static final String ignToUuidURL = "https://api.mojang.com/users/profiles/minecraft/%s";

    /**
     * Executes a blocking HTTP GET request to the Mojang API to fetch the UUID tied to the given in-game name (IGN).
     *
     * @param ign The in-game name of the user.
     * @return The {@link UUIDConverterDto} containing the UUID tied to the IGN.
     * @throws ServiceException         if there is an error fetching the UUID from the Mojang API.
     * @throws IllegalArgumentException if no user with the given IGN is found in the Mojang database.
     * @throws NullPointerException     if the provided IGN is null.
     */
    public UUIDConverterDto getUUIDByIgn(String ign) {
        log.debug("Fetching UUID tied to ign [{}]", ign);

        Objects.requireNonNull(ign);

        String url = ignToUuidURL.formatted(ign);
        RestTemplate restTemplate = new RestTemplate();
        UUIDConverterDto result;
        try {
            result = restTemplate.getForObject(url, UUIDConverterDto.class);
        } catch (RestClientException restClientException) {
            log.warn("Error fetching UUID [{}]", restClientException.getMessage());
            throw ServiceException.cannotReadEntityDueToExternalMojangError(restClientException);
        }

        if (result == null) {
            log.warn("No User found with IGN [{}] in Mojang Database!", ign);
            throw new IllegalArgumentException("No user with ign [%s] was found in Mojang's Database!".formatted(ign));
        }

        log.debug("Fetched UUID: result [{}]", result);

        return result;
    }

}