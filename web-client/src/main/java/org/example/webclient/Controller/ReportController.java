package org.example.webclient.Controller;

import org.example.webclient.Model.TestReportDto;
import org.example.webclient.Payload.Response.ReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ReportController {
    private final WebClient webClient; // Inject WebClient bean

    public ReportController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081").build();
    }

    @GetMapping("/report")
    public Flux<ReportResponse> getReportById(@RequestParam("id") String reportId) {
        return webClient.get()
                .uri("/api/reports?id={id}", reportId) // Use path variable for the parameter
                .retrieve()
                .bodyToMono(ReportResponse.class).flux();
    }
}
