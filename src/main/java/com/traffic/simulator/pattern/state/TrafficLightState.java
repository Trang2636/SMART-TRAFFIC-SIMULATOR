
public interface TrafficLightState {
    void handle(TrafficLight trafficLight);
    String getName();
    int getDuration();
}