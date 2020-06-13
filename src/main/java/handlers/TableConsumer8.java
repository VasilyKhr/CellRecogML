// This source code was generated by TabbyXL (http://tabbydoc.github.io) from Rule8
package handlers;

import static ru.icc.td.tabbyxl.model.NerTag.*;
import static ru.icc.td.tabbyxl.model.TypeTag.*;

import java.util.Iterator;
import ru.icc.td.tabbyxl.crl2j.TableConsumer;
import ru.icc.td.tabbyxl.model.CCell;
import ru.icc.td.tabbyxl.model.CTable;

public class TableConsumer8 implements TableConsumer {
    public static final int ORDER = 8;

    public int getOrder() {
        return ORDER;
    }

    @Override
    public void accept(CTable table) {
        Iterator<CCell> cIterator = table.getCells();
        while (cIterator.hasNext()) {
            CCell c = cIterator.next();
            if ((c.getStyle().getFgColor() != null) && (c.getStyle().getFgColor().equals("#ffd965"))) {
                Iterator<CCell> c1Iterator = table.getCells();
                while (c1Iterator.hasNext()) {
                    CCell c1 = c1Iterator.next();
                    if ((c1.getCl() >= c.getCl()) && (c1.getCr() <= c.getCr()) && (c1.getRt() > c.getRb()) && (!c1.isBlank()) && (c1.getStyle().getFgColor() != null) && (!c1.getStyle().getFgColor().equals("#70ad47"))) {
                        c1.setTag("STUB2");
                    }
                }
            }
        }
    }
}