package com.sudip.restwebservices.helloworld;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

// REST API
@RestController
public class HelloWorldController {

    private MessageSource messageSource;

    public HelloWorldController(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @GetMapping(path="/hello-world")
    public String helloWorld(){
        return "Hello world";
    }

    @GetMapping(path="/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World Bean");
    }

    // Path Variable : courses/{id}/description
    @GetMapping(path="/hello-world/path-variable/{name}")
    public HelloWorldBean helloWorldBeanPathVariable(@PathVariable String name){
        return new HelloWorldBean("Hello World , " + name);
    }


    @GetMapping(path="/hello-world-internationalized")
    public String helloWorldInternationalized(){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
//        return "Hello world v2";
    }
}

// Implementing i18n : internationalization
/*Example: `en` - English (Good Morning)
 * Example: `nl` - Dutch (Goedemorgen)
 * Example: `fr` -French (Bonjour)
 * Example: `de` -Deutsch (Guten Morgen)
 * */

/*Steps:
 * 1. Define these values : src/main/resources : name: messages.properties
 * 2. Write code to pick these values : use MessageSource from Spring
 *                                      inject through constructor,
 *                                      use localeContextHolder
 * */
