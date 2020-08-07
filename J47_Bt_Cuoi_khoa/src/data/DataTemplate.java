package data;
@FunctionalInterface
public interface DataTemplate<T> {
	T retrieve(String line);
}
