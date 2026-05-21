/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL
 */
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class SimpleMenuListener implements MenuListener {

    private Runnable action;

    public SimpleMenuListener(Runnable action) {
        this.action = action;
    }

    public void menuSelected(MenuEvent e) {
        action.run();
    }

    public void menuDeselected(MenuEvent e) {}
    public void menuCanceled(MenuEvent e) {}
}
