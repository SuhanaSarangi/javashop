package se.systementor.supershoppen1.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import se.systementor.supershoppen1.shop.model.Product;
import se.systementor.supershoppen1.shop.services.ProductService;
import se.systementor.supershoppen1.shop.services.SubscriberService;

@Controller
public class HomeController {

    private final ProductService productService;
    private final SubscriberService subscriberService;

    @Autowired
    public HomeController(ProductService productService, SubscriberService subscriberService) {
        this.productService = productService;
        this.subscriberService = subscriberService;
    }

    /**
     * Startsida – laddas vid "/"
     * Visar om användaren är prenumerant (för att dölja/förhindra ny prenumeration)
     */
    @GetMapping("/")
    public String showHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object ud = auth.getPrincipal();

        if (ud instanceof UserDetails) {
            String username = ((UserDetails) ud).getUsername();
            boolean isSubscriber = subscriberService.isSubscriber(username);
            model.addAttribute("hideSubscription", isSubscriber);
        } else {
            model.addAttribute("hideSubscription", false);
        }

        return "home";
    }
    /**
     * Integritetspolicysida – visas vid "/privacy"
     */
    @GetMapping("/privacy")
    public String showPrivacyPolicy() {
        return "privacy";
    }

    /**
     * Testmetod för att returnera alla produkter (JSON)
     */
    @GetMapping("/test2")
    public List<Product> getAllProducts() {
        return productService.getAll();
    }
}