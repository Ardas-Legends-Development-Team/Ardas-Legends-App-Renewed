package com.ardaslegends.presentation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Abstract base class for REST controllers.
 * <p>
 * This class provides common functionality for all REST controllers in the application.
 * </p>
 * <p>
 * The {@link CrossOrigin} annotation is used to enable Cross-Origin Resource Sharing (CORS) for all methods in the derived controllers.
 * </p>
 */
@Slf4j
@CrossOrigin
public abstract class AbstractRestController {

}