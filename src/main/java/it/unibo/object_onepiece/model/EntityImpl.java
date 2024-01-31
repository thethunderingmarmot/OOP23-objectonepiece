package it.unibo.object_onepiece.model;
import it.unibo.object_onepiece.model.Utils.Position;
import it.unibo.object_onepiece.model.events.Event;
import it.unibo.object_onepiece.model.events.EventArgs.*;
import it.unibo.object_onepiece.model.events.EventImpl;

public abstract class EntityImpl implements Entity {
    final protected Section section;
    protected Position position;

    public final Event<ValueChanged<Position>> onPositionChanged = new EventImpl<>();
    public final Event<Generic> onEntityRemoved = new EventImpl<>();

    protected EntityImpl(final Section s, final Position p) {
        this.section = s;
        this.setPosition(p);
    }

    @Override
    public Section getSection() {
       return this.section;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    protected void setPosition(Position newPosition) {
        onPositionChanged.invoke(new ValueChanged<Position>(this.position, newPosition));
        this.position = newPosition;
    }

    protected void remove() {
        onEntityRemoved.invoke(new Generic());
        this.getSection().removeEntityAt(this.position);
    }
}
