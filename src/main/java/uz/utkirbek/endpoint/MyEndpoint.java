package uz.utkirbek.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import uz.utkirbek.service.impl.TraineeServiceImpl;

@Component
@Endpoint(id = "myEndpoint")
public class MyEndpoint {

    private final TraineeServiceImpl customMetrics;

    public MyEndpoint(TraineeServiceImpl customMetrics) {
        this.customMetrics = customMetrics;
    }

    @ReadOperation
    public CustomMetricsResponse customMetrics() {
        return new CustomMetricsResponse(customMetrics.getMyCounter().count());
    }

    static class CustomMetricsResponse {
        private final double customCounterValue;

        public CustomMetricsResponse(double customCounterValue) {
            this.customCounterValue = customCounterValue;
        }
    }
}

