package ro.ubbcluj.map.Factory;

import ro.ubbcluj.map.container.Container;
import ro.ubbcluj.map.container.Strategy;

public interface Factory {

    Container createContainer(Strategy startegy);
}
