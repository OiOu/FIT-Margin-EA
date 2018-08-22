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

    @RequestMapping(value = "/currency/rates/{shortName}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CurrencyRates> findAllByShortName(@PathVariable("shortName") String shortName) {
        return currencyRatesService.findAllByShortName(shortName);
    }

    @RequestMapping(value = "/currency/rates", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public CurrencyRates create(@RequestBody CurrencyRates currencyRates) {
        return currencyRatesService.save(currencyRates);
    }

    @RequestMapping(value = "/currency/rates/id/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public void deleteById(@PathVariable("id") Integer id) {
        currencyRatesService.delete(id);
    }

    @RequestMapping(value = "/currency/rates/{shortName}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public void deleteByShortName(@PathVariable("shortName") String shortName) {
        currencyRatesService.delete(shortName);
    }
}
