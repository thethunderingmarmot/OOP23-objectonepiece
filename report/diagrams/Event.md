```mermaid
classDiagram

class View
View : -Consumer~int~ onExampleEvent

Event~T~ *-- View : subscribe

class Event~T~
Event : -boolean isValid
Event : -List~Consumer~T~~ listeners
Event : +subscribe(Consumer~T~ listener) void
Event : +unsubscribe(Consumer~T~ listener) void
Event : +invoke(T args) void
Event : +tryInvoke(T args) boolean
Event : +lastInvoke(T args) void
Event : +invalidate() void

Event~T~ *-- Model : invoke

class Model
Model : -exampleEvent Event~int~
Model : +getExampleEvent Event~int~
```
