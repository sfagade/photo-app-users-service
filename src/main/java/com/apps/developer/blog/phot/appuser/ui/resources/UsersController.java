package com.apps.developer.blog.phot.appuser.ui.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author samsonfagade
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    @GetMapping("/status/check")
    public String status() {
        return "Working";
    }
}
