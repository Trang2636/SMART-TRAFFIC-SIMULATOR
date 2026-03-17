
public class GreenState implements TrafficLightState {
    @Override
    public void handle(TrafficLight trafficLight) {
        trafficLight.setState(new YellowState());
    }
    @Override
    public String getName() {
        return "Den xanh";
    }
    @Override
    public int getDuration() {
        return 7;
    }
}