package csie.ase.ro;

import java.util.List;

public interface Callback<R> {
    void runOnUI(R rezultat);
}