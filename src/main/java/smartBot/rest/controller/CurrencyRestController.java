package smartBot.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public Currency findOne(@PathVariable("id") Integer id) {
        return currencyService.findById(id);
    }

    @RequestMapping(value = "/currency", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Currency create(@RequestBody Currency currency) {
        return currencyService.create(currency);
    }

    @RequestMapping(value = "/currency", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Currency update(@RequestBody Currency currency) {
        return currencyService.save(currency);
    }

    @RequestMapping(value = "/currency/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void delete(@PathVariable("id") Integer id) {
        currencyService.delete(id);
    }
}
