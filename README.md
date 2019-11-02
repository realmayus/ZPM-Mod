# ZPM Mod
[![Build Status](https://travis-ci.org/MayusYT/ZPM-Mod.png)](https://travis-ci.org/MayusYT/ZPM-Mod)


ZPM-Mod is a power generating Mod. You can find (and craft) Zero Point Modules and extract Energy from them. Huge amounts of energy!

### Feature list
* TheOneProbe Integration
* OpenComputers Integration
* Automation through pipes and redstone modes

### OpenComputers Integration
You can query the energy amount left in a ZPM.
To do that, place an OpenComputers Adapter next to a **large** ZPM Controller.
Then, you can execute the following code on the computer that is connected to the Adapter.

Note: You can get the name of the component (it's "controllerlarge" below) if you execute the command `components` on the computer.

Example for the large controller to get the energy in a ZPM that is in a controller:
```lua
local component = require("component")

local controller = component.controllerlarge

print(controller.getZpmEnergy()[1]) -- [1] resembles the slot
```

These are all available methods:

* `setEnabled(True)` - Takes a boolean as argument and enables/disables the controller based on that.
* `setRedstoneBehaviour("ignore")` - Sets the redstone behaviour of the controller (available options: "ignore", "active_on_redstone", "not_active_on_redstone")
* `getZpmEnergy()[1]` - returns the energy of the specified ZPM in the controller (indexes in lua start at 1!)
* `getEnabled()` - returns a boolean that tells you whether the controller is enabled or not
* `getRedstoneBehaviour()` - returns a string that tells you the current redstone behaviour of the controller.

*Small ZPM controllers don't have OpenComputers Integration and won't ever have it.*

More information coming soon!

My Website: https://mayus.me
