package net.tannhauser.oxide;

import net.tannhauser.oxide.patches.NullPointer;

import java.util.Optional;

public class Variable<T> {
    public String Name;
    public String Type;
    public T Val;
    public T prevVal;

    public Variable(String name, String type, T val) {
        this.Name = name;
        this.Type = type;
//        if (val.isEmpty()) {
//            this.Val = new NullPointer();
//        } else {
//            this.Val = val;
//        }
//        this.prevVal = new NullPointer();
    }

    public void set(T val) {
        this.prevVal = this.Val;
        this.Val = val;
    }

    public T get() {
        return this.Val;
    }

    public void undo() {
        this.Val = this.prevVal;
        //this.prevVal = new NullPointer();
    }
}
