package smartBot.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import smartBot.bean.MarginRates;
import smartBot.bussines.service.MarginRatesService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MarginRatesRestController {
    @Resource
    private MarginRatesService marginRatesService;

    @RequestMapping(value = "/currency/margins/{shortname}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<MarginRates> findAllByShortName(@PathVariable("shortname") String shortName) {
        return marginRatesService.findAllByShortName(shortName);
    }

    @RequestMapping(value = "/currency/margins", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public MarginRates create(@RequestBody MarginRates marginRates) {
        return marginRatesService.create(marginRates);
    }

    @RequestMapping(value = "/currency/margins/id/{id}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public void deleteById(@PathVariable("id") Integer id) {
        marginRatesService.delete(id);
    }

    @RequestMapping(value = "/currency/margins/{shortname}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @Transactional
    public void deleteByShortName(@PathVariable("shortname") String shortname) {
        marginRatesService.delete(shortname);
    }
}
