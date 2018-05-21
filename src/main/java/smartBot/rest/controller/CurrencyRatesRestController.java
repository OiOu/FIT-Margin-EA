package smartBot.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import smartBot.bean.CurrencyRates;
import smartBot.bussines.service.CurrencyRatesService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CurrencyRatesRestController {
    @Resource
    private CurrencyRatesService currencyRatesService;

    @RequestMapping(value = "/currency/rates/{shortname}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CurrencyRates> findAllByShortName(@PathVariable("shortname") String shortName) {
        return currencyRatesService.findAllByShortName(shortName);
    }

    @RequestMapping(value = "/currency/rates", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public CurrencyRates create(@RequestBody CurrencyRates currency) {
        return currencyRatesService.create(currency);
    }

    @RequestMapping(value = "/currency/rates/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public void deleteById(@PathVariable("id") Integer id) {
        currencyRatesService.deleteById(id);
    }

    @RequestMapping(value = "/currency/rates/{shortname}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public void deleteByShortName(@PathVariable("shortname") String shortname) {
        currencyRatesService.deleteByShortName(shortname);
    }
}
