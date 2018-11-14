package main.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import javafx.util.Pair;
import main.model.Delivery;
import main.model.ModelInterface;
import main.model.Tour;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Class extending JTable as a planning.
 * 
 * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
 *         Léo, THOLOT Cassandre
 */
public class PlanningTable extends JTable {

    /* Id */
    private static final long serialVersionUID = 1L;

    /**
     * Create a new PlanningTable;
     */
    public PlanningTable() {
	setModel(new PlanningTableModel());
	setSelectionModel(new PlanningSelectionModel());
	setAutoCreateRowSorter(true);
    }

    /**
     * Set the selection of row on the table.
     * 
     * @param indexes is the list of index of row lines to select.
     */
    public void selectRow(List<Integer> indexes) {
	((PlanningSelectionModel) getSelectionModel()).shouldFireEvents = false;
	clearSelection();
	int sortedIndex;
	for (Integer index : indexes) {
	    sortedIndex = getRowSorter().convertRowIndexToView(index);
	    addRowSelectionInterval(sortedIndex, sortedIndex);
	}
	((PlanningSelectionModel) getSelectionModel()).shouldFireEvents = true;

    }

    /**
     * Force the table to redraw.
     */
    public void redrawTable() {
	repaint();
	revalidate();
    }

    /**
     * Private model of table for the planning usage.
     * 
     * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
     *         Léo, THOLOT Cassandre
     */
    private static class PlanningTableModel implements TableModel {

	/* Board columns titles */
	private final String[] boardTitle = { "Livreur", "Adresse", "Heure de passage", "Durée" };

	public PlanningTableModel() {
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    return Object.class;
	}

	@Override
	public int getColumnCount() {
	    return this.boardTitle.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
	    return this.boardTitle[columnIndex];
	}

	@Override
	public int getRowCount() {
	    int count = 0;
	    for (Tour tour : ModelInterface.getTourPlanning()) {
		count += tour.getDeliveryPoints().size();
	    }
	    return count;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
	    /* Getting row line values */
	    Pair<Tour, Delivery> pair = findTourAndDeliveryFromRowIndex(rowIndex);
	    Tour tour = pair.getKey();
	    Delivery delivery = pair.getValue();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
	    Calendar hour = delivery.getHour();
	    dateFormat.setTimeZone(hour.getTimeZone());
	    /* Value to return in case of the columns number */
	    switch (columnIndex) {
	    case 0:
		return tour.getDeliveryManId();
	    case 1:
		return "(" + delivery.getAddress().getLat() + "; " + delivery.getAddress().getLon() + ")";
	    case 2:
		return dateFormat.format(hour.getTime());
	    case 3:
		return delivery.getDuration() + " seconde(s)";
	    default:
		throw new RuntimeException();
	    }
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
	    return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	    throw new NotImplementedException();
	}

	/**
	 * Get the Tour and Delivery from a planning row line index;
	 * 
	 * @param rowIndex is the planning's row line index.
	 * @return Pair, the couple of Tour and Delivery.
	 */
	private static Pair<Tour, Delivery> findTourAndDeliveryFromRowIndex(int rowIndex) {
	    List<Tour> tours = ModelInterface.getTourPlanning();
	    int i = 0;
	    for (Tour t : tours) {
		for (Delivery d : t.getDeliveryPoints()) {
		    if (rowIndex == i) {
			return new Pair<Tour, Delivery>(t, d);
		    }
		    i++;
		}
	    }
	    return null;
	}

    }

    /**
     * Private class of selecting list for the planning use.
     * 
     * @author H4204 - DURAFFOURG Maud, MONTIGNY François, SILVESTRI Lisa, STERNER
     *         Léo, THOLOT Cassandre
     */
    private static class PlanningSelectionModel extends DefaultListSelectionModel {
	/* Id */
	private static final long serialVersionUID = 1L;

	/* Authorize event */
	protected boolean shouldFireEvents = true;

	public PlanningSelectionModel() {
	    super();
	}

	@Override
	protected void fireValueChanged(int firstIndex, int lastIndex, boolean isAdjusting) {
	    if (this.shouldFireEvents) {
		super.fireValueChanged(firstIndex, lastIndex, isAdjusting);
	    }
	}

	@Override
	protected void fireValueChanged(boolean isAdjusting) {
	    if (this.shouldFireEvents) {
		super.fireValueChanged(isAdjusting);
	    }
	}

	@Override
	protected void fireValueChanged(int firstIndex, int lastIndex) {
	    if (this.shouldFireEvents) {
		super.fireValueChanged(firstIndex, lastIndex);
	    }
	}

    }

}
