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

public class PlanningTable extends JTable {

    public PlanningTable() {
	setModel(new PlanningTableModel());
	setSelectionModel(new PlanningSelectionModel());
	setAutoCreateRowSorter(true);
    }

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

    public void redrawTable() {
	repaint();
	revalidate();
    }

    private static class PlanningTableModel implements TableModel {
	// FIXME debug : remove last field of
	private final String[] boardTitle = { "Livreur", "Adresse", "Heure de passage", "Durée"/*, "ID intersection"*/ };

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
	    Pair<Tour, Delivery> pair = findTourAndDeliveryFromRowIndex(rowIndex);
	    Tour tour = pair.getKey();
	    Delivery delivery = pair.getValue();

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
	    case 3:
		return delivery.getDuration() + " seconde(s)";
//		return delivery.getAddress().getId();

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

	private Pair<Tour, Delivery> findTourAndDeliveryFromRowIndex(int rowIndex) {
	    List<Tour> tours = ModelInterface.getTourPlanning();

	    // XXX Let's make it dirty'n'easy
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

	private Delivery findDeliveryFromRowIndex(int rowIndex) {
	    List<Tour> tours = ModelInterface.getTourPlanning();

	    int i = 0;
	    for (Tour t : tours) {
		for (Delivery d : t.getDeliveryPoints()) {
		    if (rowIndex == i) {
			return d;
		    }
		    i++;
		}
	    }
	    return null;
	}
    }

    private static class PlanningSelectionModel extends DefaultListSelectionModel {
	private boolean shouldFireEvents = true;

	@Override
	protected void fireValueChanged(int firstIndex, int lastIndex, boolean isAdjusting) {
	    if (shouldFireEvents) {
		super.fireValueChanged(firstIndex, lastIndex, isAdjusting);
	    }
	}

	@Override
	protected void fireValueChanged(boolean isAdjusting) {
	    if (shouldFireEvents) {
		super.fireValueChanged(isAdjusting);
	    }
	}

	@Override
	protected void fireValueChanged(int firstIndex, int lastIndex) {
	    if (shouldFireEvents) {
		super.fireValueChanged(firstIndex, lastIndex);
	    }
	}

    }
}
