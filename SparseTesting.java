import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class SparseTesting {

    public static void main(String[] args) throws Exception {

        SparseTesting  mat = new SparseTesting();
        mat.loadEntries("data//MatrixTranspose.txt");

        SparseTesting transpose_mat = mat.transpose();

        System.out.println("Matrix elements:");
        mat.print();
        System.out.println();
        System.out.println("Transposed matrix elements:");
        transpose_mat.print();

    }


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

                // Add your code here to add the element into data member entries

                int pos;
                pos = row * numCols + col;

                Entry elemEntry = new Entry(pos, val);

                entries.add(elemEntry);

            }

            // Add your code here for sorting non-zero elements
            entries.sort(Comparator.comparing(Entry::getPosition));


        } catch (Exception e) {
            e.printStackTrace();
            numRows = 0;
            numCols = 0;
            entries = null;
        }
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

    // Transposing a matrix
    public SparseTesting transpose() {
        // Add your code here

        SparseTesting transpose_mat = new SparseTesting();
        int temp = numCols;
        numCols = numRows;
        numRows = temp;


        transpose_mat.numRows=numCols;
        transpose_mat.numCols=numRows;

        for (int i = 0; i < entries.size(); i++) {
            int pos = entries.get(i).getPosition();

            int col = pos % numRows;
            int row = (pos - col) / numRows;

            int NewPos = col * numCols + row;

            entries.get(i).setPosition(NewPos);
//            System.out.println(entries.get(i).getPosition());
        }

        return transpose_mat;
    }

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
    private ArrayList<SparseTesting.Entry> entries; // Non-zero elements
}
