# chatgpt-statemachine

./gradlew bootRun

http://localhost:8080/swagger-ui/index.html

http://localhost:8080/state_machine_diagram.png

Events
Event is the most-used trigger behavior to drive a state machine. There are other ways to trigger behavior in a state machine (such as a timer), but events are the ones that really let users interact with a state machine. Events are also called “signals”. They basically indicate something that can possibly alter a state machine state.

Transitions
A transition is a relationship between a source state and a target state. A switch from one state to another is a state transition caused by a trigger.

Internal Transition
Internal transition is used when an action needs to be run without causing a state transition. In an internal transition, the source state and the target state is always the same, and it is identical with a self-transition in the absence of state entry and exit actions.

Actions
Actions really glue state machine state changes to a user’s own code. A state machine can run an action on various changes and on the steps in a state machine (such as entering or exiting a state) or doing a state transition.

Actions usually have access to a state context, which gives running code a choice to interact with a state machine in various ways. State context exposes a whole state machine so that a user can access extended state variables, event headers (if a transition is based on an event), or an actual transition (where it is possible to see more detailed about where this state change is coming from and where it is going).

Hierarchical State Machines
The concept of a hierarchical state machine is used to simplify state design when particular states must exist together.

Hierarchical states are really an innovation in UML state machines over traditional state machines, such as Mealy or Moore machines. Hierarchical states lets you define some level of abstraction (parallel to how a Java developer might define a class structure with abstract classes). For example, with a nested state machine, you can define transition on a multiple level of states (possibly with different conditions). A state machine always tries to see if the current state is able to handle an event, together with transition guard conditions. If these conditions do not evaluate to TRUE, the state machine merely see what the super state can handle.

