package com.example.controller.error;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.controller.AbstractController;

/**
 * Controller that demonstrates tiles mapping, reguest parameters and path
 * variables.
 *
 * @author KhangNT
 */
@Controller
public class ExceptionHandlerController extends AbstractController {

    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping(value = "/error-not-found", method = RequestMethod.GET)
    public String requestNotFound(Model model) {
        log.info("Request not found. Error code is 404");
        return "error-not-found";
    }

    @RequestMapping(value = "/error-confict", method = RequestMethod.GET)
    public String requestConflict(Model model) {
        log.info("Request conflict. Error code is 409");
        return "error-confict";
    }

    @RequestMapping(value = "/error-forbidden", method = RequestMethod.GET)
    public String requestForbidden(Model model) {
        log.info("Request forbiddent. Error code is 403");
        return "error-forbidden";
    }
}
