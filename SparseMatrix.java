
/* C1615033
 *
 * Optionally, if you have any comments regarding your submission, put them here. 
 * For instance, specify here if your program does not generate the proper output or does not do it in the correct manner.
 */

import java.security.KeyStore;
import java.util.*;
import java.io.*;

// A class that represents a dense vector and allows you to read/write its elements
class DenseVector {
	private int[] elements;

	public DenseVector(int n) {
		elements = new int[n];
	}

	public DenseVector(String filename) {
		File file = new File(filename);
		ArrayList<Integer> values = new ArrayList<Integer>();

		try {
			Scanner sc = new Scanner(file);

			while (sc.hasNextInt()) {
				values.add(sc.nextInt());
			}

			sc.close();

			elements = new int[values.size()];
			for (int i = 0; i < values.size(); ++i) {
				elements[i] = values.get(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Read an element of the vector
	public int getElement(int idx) {
		return elements[idx];
	}

	// Modify an element of the vector
	public void setElement(int idx, int value) {
		elements[idx] = value;
	}

	// Return the number of elements
	public int size() {
		return (elements == null) ? 0 : (elements.length);
	}

	// Print all the elements
	public void print() {
		if (elements == null) {
			return;
		}

		for (int i = 0; i < elements.length; ++i) {
			System.out.println(elements[i]);
		}
	}
}

// A class that represents a sparse matrix
public class SparseMatrix {
	// Auxiliary function that prints out the command syntax
	public static void printCommandError() {
		System.err.println("ERROR: use one of the following commands");
		System.err.println(" - Read a matrix and print information: java SparseMatrix -i <MatrixFile>");
		System.err.println(" - Read a matrix and print elements: java SparseMatrix -r <MatrixFile>");
		System.err.println(" - Transpose a matrix: java SparseMatrix -t <MatrixFile>");
		System.err.println(" - Add two matrices: java SparseMatrix -a <MatrixFile1> <MatrixFile2>");
		System.err.println(" - Matrix-vector multiplication: java SparseMatrix -v <MatrixFile> <VectorFile>");
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			printCommandError();
			System.exit(-1);
		}

		if (args[0].equals("-i")) {
			if (args.length != 2) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			System.out.println("Read matrix from " + args[1]);
			System.out.println("The matrix has " + mat.getNumRows() + " rows and " + mat.getNumColumns() + " columns");
			System.out.println("It has " + mat.numNonZeros() + " non-zeros");
		} else if (args[0].equals("-r")) {
			if (args.length != 2) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			System.out.println("Read matrix from " + args[1] + ":");
			mat.print();
		} else if (args[0].equals("-t")) {
			if (args.length != 2) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			System.out.println("Read matrix from " + args[1]);
			SparseMatrix transpose_mat = mat.transpose();
			System.out.println();
			System.out.println("Matrix elements:");
			mat.print();
			System.out.println();
			System.out.println("Transposed matrix elements:");
			transpose_mat.print();
		} else if (args[0].equals("-a")) {
			if (args.length != 3) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat1 = new SparseMatrix();
			mat1.loadEntries(args[1]);
			System.out.println("Read matrix 1 from " + args[1]);
			System.out.println("Matrix elements:");
			mat1.print();

			System.out.println();
			SparseMatrix mat2 = new SparseMatrix();
			mat2.loadEntries(args[2]);
			System.out.println("Read matrix 2 from " + args[2]);
			System.out.println("Matrix elements:");
			mat2.print();
			SparseMatrix mat_sum1 = mat1.add(mat2);

			System.out.println();
			mat1.multiplyBy(2);
			SparseMatrix mat_sum2 = mat1.add(mat2);

			mat1.multiplyBy(5);
			SparseMatrix mat_sum3 = mat1.add(mat2);

			System.out.println("Matrix1 + Matrix2 =");
			mat_sum1.print();
			System.out.println();

			System.out.println("Matrix1 * 2 + Matrix2 =");
			mat_sum2.print();
			System.out.println();

			System.out.println("Matrix1 * 10 + Matrix2 =");
			mat_sum3.print();
		} else if (args[0].equals("-v")) {
			if (args.length != 3) {
				printCommandError();
				System.exit(-1);
			}

			SparseMatrix mat = new SparseMatrix();
			mat.loadEntries(args[1]);
			DenseVector vec = new DenseVector(args[2]);
			DenseVector mv = mat.multiply(vec);

			System.out.println("Read matrix from " + args[1] + ":");
			mat.print();
			System.out.println();

			System.out.println("Read vector from " + args[2] + ":");
			vec.print();
			System.out.println();

			System.out.println("Matrix-vector multiplication:");
			mv.print();
		}
	}

	// Loading matrix entries from a text file
	// You need to complete this implementation
	public void loadEntries(String filename) {
		File file = new File(filename);

		try {
			Scanner sc = new Scanner(file);
			numRows = sc.nextInt();
			numCols = sc.nextInt();
			entries = new ArrayList<Entry>();


			while (sc.hasNextInt()) {
				// Read the row index, column index, and value of an element
				int row = sc.nextInt();
				int col = sc.nextInt();
				int val = sc.nextInt();
				int pos;

				// Add your code here to add the element into data member entries
//				convert the row-column numbers to the position in the matrix beginning with 0.
				pos = row * numCols + col;
				entries.add(new Entry(pos,val));

			}
//			entries.sort(Comparator.comparing(Entry::getPosition));



// Add your code here for sorting non-zero elements
//			calling the mergeSort procedure in order to sort the entries
			entries = mergeSort(entries);




		} catch (Exception e) {
			e.printStackTrace();
			numRows = 0;
			numCols = 0;
			entries = null;
		}
	}


	public static ArrayList< Entry > mergeSort(ArrayList< Entry > list) {

		ArrayList <Entry> sorted = new ArrayList<Entry>();
		if (list.size() == 1) {
			sorted = list;
		} else {
			int mid1 = list.size() /2;
//			create the temporary arrays
			ArrayList< Entry > left = new ArrayList<Entry>();
			ArrayList< Entry > right = new ArrayList<Entry>();
//			copy the data to the two sub arrays
			for ( int x = 0; x < mid1; x++) {
				left.add(list.get(x));
			}
			for ( int x = mid1; x < list.size(); x++) {
				right.add(list.get(x));
			}
//			sort the first and second halves
			left = mergeSort(left);
			right = mergeSort(right);
			sorted = mergeArray(left,right);
		}
		return sorted;
	}

	private static ArrayList<Entry> mergeArray(ArrayList<Entry> left, ArrayList<Entry> right) {
		ArrayList< Entry > merged = new ArrayList<Entry>();

		int i = 0;
		int leftElement = 0;// initial index of left sub array
		int rightElement = 0;// initial index of right sub array
		int comparison = 0;
//		merge the temp arrays
		while (leftElement < left.size() && rightElement < right.size()) {
			if ((left.get(leftElement).getPosition())<(right.get(rightElement).getPosition())) {
				merged.add(left.get(leftElement));
				leftElement++;
			}
			else {
				merged.add(right.get(rightElement));
				rightElement++;
			}
			i++;
		}
//		copy remaining elements of left sub array if they exist
		while (leftElement < left.size()) {
			merged.add(left.get(leftElement));
			leftElement++;
			i++;
		}
//		copy remaining elements of right sub array if they exist
		while (rightElement < right.size()) {
			merged.add(right.get(rightElement));
			rightElement++;
			i++;
		}
		return merged;
	}

	// Default constructor
	public SparseMatrix() {
		numRows = 0;
		numCols = 0;
		entries = null;
	}

	// A class representing a pair of column index and elements
	private class Entry {
		private int position; // Position within row-major full array representation
		private int value; // Element value

		// Constructor using the column index and the element value
		public Entry(int pos, int val) {
			this.position = pos;
			this.value = val;
		}

		// Copy constructor
		public Entry(Entry entry) {
			this(entry.position, entry.value);
		}

		// Read column index
		int getPosition() {
			return position;
		}

		// Set column index
		void setPosition(int pos) {
			this.position = pos;
		}

		// Read element value
		int getValue() {
			return value;
		}

		// Set element value
		void setValue(int val) {
			this.value = val;
		}
	}

	// Adding two matrices
	public SparseMatrix add(SparseMatrix M) {
		// Add your code here
		SparseMatrix result = new SparseMatrix();
		result.entries = new ArrayList<Entry>();
		int aPos = 0;
		int bPos = 0;

		result.numCols = numCols;
		result.numRows = numRows;
		int counter = 0;
		while (aPos < entries.size() && bPos < M.entries.size()) {
			int positionA = entries.get(aPos).getPosition();
			int valueA = entries.get(aPos).getValue();
			int positionB = M.entries.get(bPos).getPosition();
			int valueB = M.entries.get(bPos).getValue();
			int addResult = valueA + valueB;
//			if a's position is smaller
			if (positionA < positionB) {
				result.entries.add(new Entry(positionA, valueA));
				aPos++;
				counter++;
//			if b's position is smaller
			} else if (positionB < positionA) {
				result.entries.add(new Entry(positionB, valueB));
				bPos++;
				counter++;

			}
//			if they are equal add them
			else {
				result.entries.add(new Entry(positionA, addResult));
				aPos++;
				bPos++;
				counter++;

			}

		}
//		add the remaining elements
		while (aPos < entries.size()){
			result.entries.add(new Entry(entries.get(aPos).getPosition(), entries.get(aPos).getValue()));
			aPos++;
			counter++;

		}
		while (bPos < entries.size()){
			result.entries.add(new Entry(M.entries.get(bPos).getPosition(), M.entries.get(bPos).getValue()));
			bPos++;
			counter++;


		}

		return result;

	}

	// Transposing a matrix
	public SparseMatrix transpose() {
		// Add your code here
		SparseMatrix template = new SparseMatrix();
		template.entries = new ArrayList<Entry>();
//		swap the number of columns with number of rows for the new matrix
		int tmp = numCols;
		template.numCols = numRows;
		template.numRows = tmp;

		for (int i=0; i< entries.size(); i++) {
//			get the position and value for each non zero element
			int position = entries.get(i).getPosition();
			int val = entries.get(i).getValue();
//			find the new column and row
			int col = position % template.numRows;
			int row = (position - col)/ template.numRows;
//			translate the new column and row numbers to the position in  transpose matrix
			int posNew = col * template.numCols + row;
//			put the new position and value in the transpose matrix
			template.entries.add(new Entry(posNew,val));

		}
//		sort the transpose matrix
		template.entries = mergeSort(template.entries);
		return template;
	}

	// Matrix-vector multiplication
	public DenseVector multiply(DenseVector v) {
		// Add your code here
		DenseVector multipliedVector = new DenseVector(numRows);
//		array list with the position of entries
		ArrayList<Integer> positionEntry = new ArrayList<>();
		int sumOfRow;
		int valueEntry;
		int valueVector;

		for (int i =0; i< entries.size(); i++){
			positionEntry.add(i, entries.get(i).getPosition());
		}
//		double for loop in the number of columns and rows of the matrix
		for (int row = 0; row< numRows; row++){
			sumOfRow = 0;
			for (int col = 0; col<numCols; col++){
//				checks if the position is in the array with the non zero elements
				if (positionEntry.contains(row * numCols + col)){

					int index = positionEntry.indexOf(row * numCols + col);
//					get the non zero entry to multiply
					valueEntry = entries.get(index).getValue();
//					get the vector value to multiply
					valueVector = v.getElement(col);
					sumOfRow = sumOfRow + (valueEntry * valueVector);
				}
				multipliedVector.setElement(row, sumOfRow);
			}
		}


		return multipliedVector;


	}

	// Return the number of non-zeros
	public int numNonZeros() {
		// Add your code here
		return entries.size();
	}

	// Multiply the matrix by a scalar, and update the matrix elements
	public void multiplyBy(int scalar) {
		// Add your code here
//		for loop in the non zero elements and multiply them all by the scalar
		for (int i =0; i< entries.size(); i++){
			int scalarValue = entries.get(i).getValue() * scalar;
			entries.get(i).setValue(scalarValue);
		}
	}

	// Number of rows of the matrix
	public int getNumRows() {
		return this.numRows;
	}

	// Number of columns of the matrix
	public int getNumColumns() {
		return this.numCols;
	}

	// Output the elements of the matrix, including the zeros
	// Do not modify this method
	public void print() {
		int n_elem = numRows * numCols;
		int pos = 0;

		for (int i = 0; i < entries.size(); ++i) {
			int nonzero_pos = entries.get(i).getPosition();

			while (pos <= nonzero_pos) {
				if (pos < nonzero_pos) {
					System.out.print("0 ");
				} else {
					System.out.print(entries.get(i).getValue());
					System.out.print(" ");
				}

				if ((pos + 1) % this.numCols == 0) {
					System.out.println();
				}

				pos++;
			}
		}

		while (pos < n_elem) {
			System.out.print("0 ");
			if ((pos + 1) % this.numCols == 0) {
				System.out.println();
			}

			pos++;
		}
	}

	private int numRows; // Number of rows
	private int numCols; // Number of columns
	private ArrayList<Entry> entries; // Non-zero elements
}
