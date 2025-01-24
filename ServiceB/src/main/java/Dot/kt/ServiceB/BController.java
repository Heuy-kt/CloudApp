package Dot.kt.ServiceB;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("b")
public class BController {

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    public BController(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
        this.discoveryClient = discoveryClient;
        this.restClient = restClientBuilder.build();
    }

    @GetMapping("callTuring")
    public String callA() {
        ServiceInstance serviceInstance = discoveryClient.getInstances("servicea").get(0);
        return restClient.get()
                .uri(serviceInstance.getUri()+"/a/hello")
                .retrieve()
                .body(String.class);
    }
}
