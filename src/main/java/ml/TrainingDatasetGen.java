package ml;

import ru.icc.td.tabbyxl.model.CCell;
import ru.icc.td.tabbyxl.model.CTable;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class TrainingDatasetGen {

    private Instances dataset;
    private Instances style;
    private Instances font;

    private Instances train;
    private Instances test;

    public TrainingDatasetGen() {
        this.dataset = new Instances(generateCellAttributes());
    }

    public void loadTables(CTable[] tables) throws Exception {

        int[] sheets = new int[] {10, 11, 15, 16, 20};

        for (int i: sheets) {
            fillInstances(tables[i]);
            System.out.println(dataset);
        }

        //dataset.setClassIndex(dataset.numAttributes() - 1);

        int trainSize = (int) Math.round(dataset.numInstances() * 0.5);
        train = new Instances(dataset, 0, trainSize - 1);
        test = new Instances(dataset, trainSize, dataset.numInstances() - trainSize);

        /*Standardize filter = new Standardize();
        filter.setInputFormat(train1);

        train = Filter.useFilter(train1, filter);
        test = Filter.useFilter(test1, filter);*/

        //System.out.println(train);
        //System.out.println(test);
    }

    private Instances generateCellAttributes() {

        ArrayList<Attribute> cellAttributes = new ArrayList<>();

        cellAttributes.add(new Attribute("text", true));

        cellAttributes.add(new Attribute("cl")); // a left column position
        cellAttributes.add(new Attribute("cr")); // a right column position
        cellAttributes.add(new Attribute("rt")); // a top row position
        cellAttributes.add(new Attribute("rb")); // a bottom row position

        cellAttributes.add(new Attribute("width"));
        cellAttributes.add(new Attribute("height"));

        cellAttributes.add(new Attribute("spaceIndent")); //  an amount of spaces in the beginning of the excel cell's text
        cellAttributes.add(new Attribute("indent"));

        String[] horzAlignments = new String[] {"GENERAL", "LEFT", "CENTER", "RIGHT", "FILL", "JUSTIFY", "CENTER_SELECTION", "NONE"};
        cellAttributes.add(new Attribute("horzAlignment", Arrays.asList(horzAlignments)));

        String[] vertAlignments = new String[] {"TOP", "CENTER", "BOTTOM", "JUSTIFY", "NONE"};
        cellAttributes.add(new Attribute("vertAlignment", Arrays.asList(vertAlignments)));

        cellAttributes.add(new Attribute("isBold", Arrays.asList(new String[] { "TRUE", "FALSE"})));

        cellAttributes.add(new Attribute("typeTag", true));
        cellAttributes.add(new Attribute("nerTag", true));

        //generateStyleAttributes();
        //cellAttributes.add(new Attribute("style", style));

        cellAttributes.add(new Attribute("tag", Arrays.asList(new String[] {"STUB", "STUB1", "STUB2", "STUB3", "HEAD", "CURRENCY", "DATA", "NONE"})));

        return new Instances("cells", cellAttributes, 0);
    }

    private void generateStyleAttributes() {
        ArrayList<Attribute> styleAttributes = new ArrayList<>();

        generateFontAttributes();
        styleAttributes.add(new Attribute("font", font));

        String[] horzAlignments = new String[] {"GENERAL", "LEFT", "CENTER", "RIGHT", "FILL", "JUSTIFY", "CENTER_SELECTION", "NONE"};
        styleAttributes.add(new Attribute("horzAlignment", Arrays.asList(horzAlignments)));

        String[] vertAlignments = new String[] {"TOP", "CENTER", "BOTTOM", "JUSTIFY", "NONE"};
        styleAttributes.add(new Attribute("vertAlignment", Arrays.asList(vertAlignments)));

        style = new Instances("style", styleAttributes, 0);
        //return new Instances("style", styleAttributes, 0);
    }

    private void generateFontAttributes() {
        ArrayList<Attribute> fontAttributes = new ArrayList<>();

        fontAttributes.add(new Attribute("name", true));
        fontAttributes.add(new Attribute("height"));
        fontAttributes.add(new Attribute("isBold", Arrays.asList(new String[] { "TRUE", "FALSE"})));

        font = new Instances("font", fontAttributes, 0);
        //return new Instances("font", fontAttributes, 0);
    }

    private void fillInstances(CTable table) {

        for (Iterator<CCell> cells = table.getCells(); cells.hasNext();) {
            CCell cell = cells.next();
            fillInstance(cell);
        }
    }

    private void fillInstance(CCell cell) {
        double[] cellInstance = new double[dataset.numAttributes()];

        cellInstance[0] = dataset.attribute("text").addStringValue(cell.getText());

        cellInstance[1] = cell.getCl();
        cellInstance[2] = cell.getCr();
        cellInstance[3] = cell.getRt();
        cellInstance[4] = cell.getRb();

        cellInstance[5] = cell.getWidth();
        cellInstance[6] = cell.getHeight();

        cellInstance[7] = cell.getSpaceIndent();
        cellInstance[8] = cell.getIndent();

        cellInstance[9] = dataset.attribute("horzAlignment").indexOfValue(cell.getStyle().getHorzAlignment().name());
        cellInstance[10] = dataset.attribute("vertAlignment").indexOfValue(cell.getStyle().getVertAlignment().name());
        cellInstance[11] = dataset.attribute("isBold").indexOfValue(cell.getStyle().getFont().isBold() ? "TRUE" : "FALSE");

        cellInstance[12] = dataset.attribute("typeTag").addStringValue(cell.getTypeTag() == null ? "NONE": cell.getTypeTag().toString());
        cellInstance[13] = dataset.attribute("nerTag").addStringValue(cell.getNerTag() == null ? "NONE": cell.getNerTag().toString());

        cellInstance[14] = dataset.attribute("tag").indexOfValue(cell.getTag() == null ? "NONE" : cell.getTag());

        //Instances style = generateStyleAttributes();
        /*double[] styleInstance = new double[style.numAttributes()];

        double[] fontInstance = new double[font.numAttributes()];
        fontInstance[0] = font.attribute("name").addStringValue(cell.getStyle().getFont().getName());
        fontInstance[1] = cell.getStyle().getFont().getHeight();
        fontInstance[2] = font.attribute("isBold").indexOfValue(cell.getStyle().getFont().isBold() ? "TRUE" : "FALSE");
        font.add(new DenseInstance(1, fontInstance));

        styleInstance[0] = style.attribute("font").addRelation(new Instances(font));
        font.remove(0);

        styleInstance[1] = style.attribute("horzAlignment").indexOfValue(cell.getStyle().getHorzAlignment() == null ? "NONE" : cell.getStyle().getHorzAlignment().name());
        styleInstance[2] = style.attribute("vertAlignment").indexOfValue(cell.getStyle().getVertAlignment() == null ? "NONE" : cell.getStyle().getVertAlignment().name());
        style.add(new DenseInstance(1, styleInstance));

        cellInstance[9] = dataset.attribute("style").addRelation(new Instances(style));
        style.remove(0);*/

        dataset.add(new DenseInstance(1, cellInstance));
    }

    public Instances getDataset() {
        return dataset;
    }

    public Instances getTrain() {
        return train;
    }

    public Instances getTest() {
        return test;
    }
}
