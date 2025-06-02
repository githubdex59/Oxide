package net.tannhauser.oxide;

import net.tannhauser.oxide.patches.NullPointer;

public class Variable {
    public String Name;
    public String Type;
    public Object Val;
    public Object prevVal;

    public Variable(String name, String type, Object val) {
        this.Name = name;
        this.Type = type;
        this.Val = val;
        this.prevVal = new NullPointer();
    }

    public void set(Object val) {
        this.prevVal = this.Val;
        this.Val = val;
    }

    public Object get() {
        return this.Val;
    }

    public void undo() {
        this.Val = this.prevVal;
        this.prevVal = new NullPointer();
    }
}
