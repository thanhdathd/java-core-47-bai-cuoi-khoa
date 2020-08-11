package data;
@FunctionalInterface
public interface Data {
	String getLine();
	default String getColumns() {
		return "# The column's name place here";
	}
}
