package main.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.sun.xml.internal.org.jvnet.mimepull.MIMEConfig;

import main.model.Delivery;
import main.model.ModelInterface;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import sun.reflect.generics.visitor.Reifier;

public class PlanningTable extends JTable {
	
	
	public PlanningTable() {
		setModel(new PlanningTableModel());
		setAutoCreateRowSorter(true);
//		getSelectionModel().addListSelectionListener(new PlanningListener(this));
	}
	
	
	public void selectRow(int rowIndex) {
		int sortedIndex = getRowSorter().convertRowIndexToView(rowIndex);
		setRowSelectionInterval(sortedIndex, sortedIndex);
	}

	
	
	private static class PlanningTableModel implements TableModel {
		private final int columnsNumber = 3;
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
			return columnsNumber;
		}

		@Override
		public String getColumnName(int columnIndex) {
			return boardTitle[columnIndex];
		}

		@Override
		public int getRowCount() {
			return ModelInterface.getDeliveries().size();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Delivery delivery = ModelInterface.getDeliveries().get(rowIndex);
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH-mm-ss");
			Calendar hour = delivery.getHour();
			dateFormat.setTimeZone(hour.getTimeZone());

			
			
			switch (columnIndex) {
			case 0:
				return 1; // FIXME : calculate the matching id
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
	}
}
