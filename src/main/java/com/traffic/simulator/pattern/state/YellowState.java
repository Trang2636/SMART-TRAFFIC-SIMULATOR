
public class YellowState implements TrafficLightState {
    @Override
    public void handle(TrafficLight trafficLight) {
        trafficLight.setState(new RedState());
    }
    @Override
    public String getName() {
        return "Den vang";
    }
    @Override
    public int getDuration() {
        return 5;
    }
}