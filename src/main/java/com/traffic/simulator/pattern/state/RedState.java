
public class RedState implements TrafficLightState {
    @Override
    public void handle(TrafficLight trafficLight) {
        trafficLight.setState(new GreenState());
    }
    @Override
    public String getName() {
        return "Den do";
    }
    @Override
    public int getDuration() {
        return 7;
    }
}