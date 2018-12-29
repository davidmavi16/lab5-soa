package soa.web;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class SearchController {

  private final ProducerTemplate producerTemplate;

  @Autowired
  public SearchController(ProducerTemplate producerTemplate) {
    this.producerTemplate = producerTemplate;
  }

  @RequestMapping("/")
  public String index() {
    return "index";
  }


  @RequestMapping(value = "/search")
  @ResponseBody
  public Object search(@RequestParam("q") String q) {
    int max = q.indexOf("max");
    String s = q.substring(max);
    String[] split = s.split(":");
    s = split[1];
    Integer count = Integer.parseInt(s);
    q = q.substring(0,max);

    Map<String,Object> headers = new HashMap<String,Object>();
    headers.put("CamelTwitterKeywords",q);
    headers.put("CamelTwitterCount",count);
    return producerTemplate.requestBodyAndHeader("direct:search", "", headers);
  }
}
