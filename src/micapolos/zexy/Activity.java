package micapolos.zexy;

import static micapolos.Leo.*;

public class Activity extends Component {
  void advance(float seconds) {}

  public static final Activity noActivity = new Activity() {
    @Override
    public String toString() {
      return "no activity";
    }
  };

  public static Activity parallel(Activity... activities) {
    return new Activity() {
      @Override
      void advance(float seconds) {
        for (Activity activity : activities) {
          activity.advance(seconds);
        }
      }

      @Override
      void addRunners() {
        for (Activity activity : activities) {
          activity.addRunnersOnce();
        }
      }

      @Override
      public String toString() {
        return leo("parallel", activities);
      }
    };
  }

  @Override
  public String toString() {
    return "an activity";
  }

  static void main() {
    parallel(noActivity, noActivity).show();
  }
}
