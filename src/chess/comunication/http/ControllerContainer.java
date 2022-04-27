package chess.comunication.http;

import chess.comunication.http.controllers.Controller;
import com.sun.xml.internal.ws.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ControllerContainer {

    private Map<String, Controller> controllers;

    public ControllerContainer() {
        controllers = new HashMap<>();
    }

    public Controller get(String name) {
        if(!controllers.containsKey(name)) {
            controllers.put(name, this.getInstance(name));
        }
        return controllers.get(name);
    }

    private Controller getInstance(String name) {
        Controller controller = null;
        try {
            controller = (Controller)Class.forName(this.getFullName(name)).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return controller;
    }

    private String getFullName(String name) {
        return StringUtils.capitalize(name) + "Controller";
    }
}
