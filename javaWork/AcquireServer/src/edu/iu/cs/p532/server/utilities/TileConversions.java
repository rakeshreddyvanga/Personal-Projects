package edu.iu.cs.p532.server.utilities;


public class TileConversions {

	public static String generateRowString(int rowIndex) {
		String rowString = "";
		switch (rowIndex) {
		case 0:
			rowString = "A";
			break;
		case 1:
			rowString = "B";
			break;
		case 2:
			rowString = "C";
			break;
		case 3:
			rowString = "D";
			break;
		case 4:
			rowString = "E";
			break;
		case 5:
			rowString = "F";
			break;
		case 6:
			rowString = "G";
			break;
		case 7:
			rowString = "H";
			break;
		case 8:
			rowString = "I";
			break;
		default:
			rowString = "";
			break;

		}
		if (!rowString.equals("")) {
			return rowString;
		} else {
			return "";
		}
	}

	public static int generateRowIndex(String rowString) {
		int rowIndex = -1;

		switch ((int) rowString.charAt(0) - 65) {
		case 0:
			rowIndex = 0;
			break;
		case 1:
			rowIndex = 1;
			break;
		case 2:
			rowIndex = 2;
			break;
		case 3:
			rowIndex = 3;
			break;
		case 4:
			rowIndex = 4;
			break;
		case 5:
			rowIndex = 5;
			break;
		case 6:
			rowIndex = 6;
			break;
		case 7:
			rowIndex = 7;
			break;
		case 8:
			rowIndex = 8;
			break;
		default:
			rowIndex = -1;
			break;

		}

		return rowIndex;
	}

	public static String getTileName(String rowString, int columnIndex) {
		String tileName = "";
		tileName = String.valueOf(columnIndex + 1).concat(rowString);
		return tileName;

	}

	public static String[] tileNameToIndex(String tilename) {
		String[] rowCloumn = new String[2];
		rowCloumn[0] = tilename.replaceAll("\\d+", "");
		rowCloumn[1] = tilename.replaceAll("\\D+", "");

		return rowCloumn;
	}

	public static String getTileName(int rowIndex, int columnIndex) {
		String tileName = "";
		tileName = String.valueOf(columnIndex + 1);
		String rowString = "";
		switch (rowIndex) {
		case 0:
			rowString = "A";
			break;
		case 1:
			rowString = "B";
			break;
		case 2:
			rowString = "C";
			break;
		case 3:
			rowString = "D";
			break;
		case 4:
			rowString = "E";
			break;
		case 5:
			rowString = "F";
			break;
		case 6:
			rowString = "G";
			break;
		case 7:
			rowString = "H";
			break;
		case 8:
			rowString = "I";
			break;
		default:
			rowString = "";
			break;

		}
		if (!rowString.equals("")) {
			tileName = tileName.concat(rowString);
		}

		return tileName;
	}

}
