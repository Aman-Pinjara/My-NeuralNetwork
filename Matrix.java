package neuralnetwork;

import java.util.ArrayList;

public class Matrix {
    public double[][] data;
    
    public Matrix(int row,int col){
        data = new double[row][col];
    }

    public Matrix(double[][] data){
        this.data = data;
    }

    //Matric scalar addition
    public void add(double n){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] += n;
            }
        }
    }

    //Matric scalar Subtraction
    public void subtract(double n){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] -= n;
            }
        }
    }

    //Matric scalar Multiplication
    public void multiply(double n){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] *= n;
            }
        }
    }

    //Matric scalar Division
    public void divide(double n){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] /= n;
            }
        }
    }

    //Matric Vector addition
    public void add(Matrix n){
        if(n.data.length!= data.length || n.data[0].length != data[0].length){
            throw new ArithmeticException("Not valid input matrix");
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] += n.data[i][j];
            }
        }
    }

    //Matric Vector Subtration
    public void subtract(Matrix n){
        if(n.data.length!= data.length || n.data[0].length != data[0].length){
            throw new ArithmeticException("Not valid input matrix");
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] -= n.data[i][j];
            }
        }
    }

    //Matric Vector Multiplication
    public void multiply(Matrix n){
        for (int i = 0; i < n.data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = n.data[i][j] * data[i][j];
            }
        }
    }

    //Print the Matrix
    public void printmatrix(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j]+", ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    //Randomise matrix between -1 and 1
    public void randomize(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = (Math.random()*2)-1;
            }
        }
    }
    public double[] toArray(){
        ArrayList<Double> a = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                a.add(data[i][j]);
            }
        }
        double[] array = new double[a.size()];
        for (int i = 0; i < a.size(); i++) {
            array[i] = a.get(i);
        }
        return array;
    }

    public void changeElements(MatrixMethod a){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[0].length; j++) {
                data[i][j] = a.change(data[i][j]);
            }
        }
    }


    //-------------------------Static Functions ------------------------

    public static Matrix subtract(Matrix a,Matrix b){
        double[][] ans = new double[a.data.length][a.data[0].length];
        for (int i = 0; i < a.data.length; i++) {
            for (int j = 0; j < a.data[i].length; j++) {
                ans[i][j] = a.data[i][j] - b.data[i][j];
            }
        }
        return new Matrix(ans);
    }
    public static Matrix multiply(Matrix n, Matrix m){
        double[][] data = new double[n.data.length][m.data[0].length];

        for (int i = 0; i < n.data.length; i++) {
            for (int j = 0; j < m.data[0].length; j++) {
                for (int k = 0; k < m.data.length; k++){
                    data[i][j] += n.data[i][k] * m.data[k][j];
                }
            }
        }
        return new Matrix(data);
    }
    public static Matrix changeElements(Matrix m, MatrixMethod a){
        double[][] ans = new double[m.data.length][m.data[0].length];
        for (int i = 0; i < m.data.length; i++) {
            for (int j = 0; j < m.data[0].length; j++) {
                ans[i][j] = a.change(m.data[i][j]);
            }
        }
        return new Matrix(ans);
    }
    public static Matrix fromArray(double[] arr){
        double[][] a = new double[arr.length][1];
        for (int i = 0; i < arr.length; i++) {
            a[i][0] = arr[i];
        }
        return new Matrix(a);
    }
    public static Matrix transpose(Matrix a){
        double[][] transpose = new double[a.data[0].length][a.data.length];
        for (int i = 0; i < a.data.length; i++) {
            for (int j = 0; j < a.data[i].length; j++) {
                transpose[j][i] = a.data[i][j];
            }
        }
        return new Matrix(transpose);
    }
}


