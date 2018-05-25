package smartBot.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import smartBot.bean.Currency;
import smartBot.bussines.service.CurrencyService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class CurrencyRestController {
    @Resource
    private CurrencyService currencyService;

    @RequestMapping(value = "/currency/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Currency findOneById(@PathVariable("id") Integer id) {
        return currencyService.findById(id);
    }

    @RequestMapping(value = "/currency/shortname/{shortname}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Currency findOneByShortName(@PathVariable("shortname") String shortName) {
        return currencyService.findByShortName(shortName);
    }

    @RequestMapping(value = "/currency", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public Currency create(@RequestBody Currency currency) {
        return currencyService.create(currency);
    }

    @RequestMapping(value = "/currency/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public void delete(@PathVariable("id") Integer id) {
        currencyService.delete(id);
    }
}
