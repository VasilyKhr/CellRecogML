// This source code was generated by TabbyXL (http://tabbydoc.github.io) from Rule14
package handlers;

import static ru.icc.td.tabbyxl.model.NerTag.*;
import static ru.icc.td.tabbyxl.model.TypeTag.*;

import java.util.Iterator;
import ru.icc.td.tabbyxl.crl2j.TableConsumer;
import ru.icc.td.tabbyxl.model.CCell;
import ru.icc.td.tabbyxl.model.CTable;

public class TableConsumer14 implements TableConsumer {
    public static final int ORDER = 14;

    public int getOrder() {
        return ORDER;
    }

    @Override
    public void accept(CTable table) {
        Iterator<CCell> cIterator = table.getCells();
        while (cIterator.hasNext()) {
            CCell c = cIterator.next();
            if ((c.getStyle().getFgColor() != null) && (c.getStyle().getFgColor().equals("#e2eeda")) && (!c.isBlank())) {
                c.setTag("CURRENCY");
            }
        }
    }
}
