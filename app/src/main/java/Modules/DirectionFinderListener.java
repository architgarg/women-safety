package Modules;

import java.util.List;

/**
 * Created by Archit Garg on 02-Oct-16.
 */
public interface DirectionFinderListener {
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
