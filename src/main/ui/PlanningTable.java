package main.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import main.model.Delivery;
import main.model.ModelInterface;
import main.model.Tour;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PlanningTable extends JTable {

    public PlanningTable() {
	setModel(new PlanningTableModel());
	setAutoCreateRowSorter(true);
    }

    public void selectRow(int rowIndex) {
	int sortedIndex = getRowSorter().convertRowIndexToView(rowIndex);
	setRowSelectionInterval(sortedIndex, sortedIndex);
    }

    public void redrawTable() {
	repaint();
	revalidate();
    }

    private static class PlanningTableModel implements TableModel {
	private final String[] boardTitle = { "Livreur", "Adresse", "Heure de passage" };

	@Override
	public void addTableModelListener(TableModelListener l) {
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    return Object.class;
	}

	@Override
	public int getColumnCount() {
	    return boardTitle.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
	    return boardTitle[columnIndex];
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

	    Tour tour = findTourFromRowIndex(rowIndex);
	    Delivery delivery = tour.getDeliveryPoints().get(rowIndex % tour.getDeliveryPoints().size());

	    SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
	    Calendar hour = delivery.getHour();
	    dateFormat.setTimeZone(hour.getTimeZone());

	    switch (columnIndex) {
	    case 0:
		return tour.getDeliveryManId();
	    case 1:
		return "(" + delivery.getAddress().getLat() + "; " + delivery.getAddress().getLon() + ")";
	    case 2:
		return dateFormat.format(hour.getTime());

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

	private Tour findTourFromRowIndex(int rowIndex) {
	    List<Tour> tours = ModelInterface.getTourPlanning();

	    int currentTourIndex = 0;
	    while (tours.get(currentTourIndex).getDeliveryPoints().size() < rowIndex) {
		currentTourIndex++;
	    }

	    return tours.get(currentTourIndex);
	}
    }
}
