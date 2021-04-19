definition(
    name: "Momentary Simulator",
    namespace: "regulad",
    author: "smartthings",
    description: "Turn off device 1 second after it turns on to simulate a momentary switch. Momentary switches do not work well in V3+, so a tool like this is required",
    iconUrl: "https://upload.wikimedia.org/wikipedia/commons/a/a6/Knopka_8_ugolnik.jpg"
)

preferences {
	section("Select device to use.") {
		input "theSwitch", "capability.switch"
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	subscribe(theSwitch, "switch.on", switchOnHandler, [filterEvents: false])
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	subscribe(theSwitch, "switch.on", switchOnHandler, [filterEvents: false])
}

def switchOnHandler(evt) {
	log.debug "Device is on. Turning off."
	runIn(1, turnOffSwitch)
}

def turnOffSwitch() {
	theSwitch.off()
}