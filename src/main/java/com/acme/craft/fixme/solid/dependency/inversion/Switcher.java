package com.acme.craft.fixme.solid.dependency.inversion;

public interface Switcher {
				
	public static void pressSwitch() {
		Switch.pressed = !Switch.pressed;
		if (pressed) {
			lamp.setOn(true);
		} else {
			lamp.setOn(false);
		}
	}
}
