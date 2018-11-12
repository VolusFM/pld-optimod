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

	    /*
	     * Tour tour = findTourFromRowIndex(rowIndex); List<Delivery> deliveries =
	     * tour.getDeliveryPoints(); int currentDisplayedSize = deliveries.size();
	     * 
	     * Delivery delivery = deliveries.get(rowIndex - );
	     */

	    Tour tour = findTourFromRowIndex(rowIndex);
	    Delivery delivery = findDeliveryFromRowIndex(rowIndex);

	    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
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
	    int currentDisplayedSize = tours.get(currentTourIndex).getDeliveryPoints().size();
	    while (currentDisplayedSize < rowIndex) {
		currentDisplayedSize += tours.get(currentTourIndex).getDeliveryPoints().size();
		currentTourIndex++;
	    }
	    try {
		Tour t = tours.get(currentTourIndex);
	    } catch (Exception e) {
		System.err.println(e.getMessage());
		System.out.println(tours);
	    }

	    return tours.get(currentTourIndex);
	}

	private Delivery findDeliveryFromRowIndex(int rowIndex) {
	    List<Tour> tours = ModelInterface.getTourPlanning();

	    int currentTourIndex = 0;
	    int currentDisplayedSize = tours.get(currentTourIndex).getDeliveryPoints().size();
	    while (currentDisplayedSize < rowIndex) {
		currentDisplayedSize += tours.get(currentTourIndex).getDeliveryPoints().size();
		currentTourIndex++;
	    }
	    try {
		Tour t = tours.get(currentTourIndex);
	    } catch (Exception e) {
		System.err.println(e.getMessage());
		System.out.println(tours);
	    }

	    int drift = 0;

	    for (int i = 0; i < currentTourIndex; i++) {
		drift += tours.get(i).getDeliveryPoints().size();
	    }

	    System.out.println("currentTourIndex : " + currentTourIndex);
	    System.out.println("currentDisplayedSize : " + currentDisplayedSize);
	    System.out.println("row index : " + rowIndex);
	    System.out.println("drift : " + drift);

	    // FIXME : we need to remove drift (tour already displayed in table) from row
	    // index to get correct delivery

	    return tours.get(currentTourIndex).getDeliveryPoints().get(rowIndex - drift);
	}

    }
}
