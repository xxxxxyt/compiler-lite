package IRCode.Operand;

import java.util.HashSet;

public class Address extends Operand {

    public int offsetNumber = 0;
//    public int scale = 1;
    public Register base;
    public Register offsetReg;
    public String globalName;
    public String label;

    @Override
    public HashSet<Variable> colorable() {
        return new HashSet<Variable>();
    }

    @Override
    public HashSet<Variable> colorableInIndexOrMember() {
        return new HashSet<Variable>();
    }

    @Override
    public String getName() {
        if (label != null) {
            return label;
        } else if (globalName == null) {
            String offsetRegStr = "";
            if (offsetReg != null) offsetRegStr = "+" + offsetReg.getName() + "*8";
            String offsetStr = "";
            if (offsetNumber >= 0) offsetStr = "+" + offsetNumber;
            else offsetStr = String.valueOf(offsetNumber);
            return "qword [" + base.getName() + offsetRegStr + offsetStr + "]";
        } else return "qword [rel " + globalName + "]";
    }
}
