import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

class QuickStart {
    Scanner stdInput;
    int howMany;
    int[] nextTab;
    double[] weightOfElek;

    public void prepareGraph(int[] elekA, int[] elekB) {
        nextTab = new int[howMany];
        for (int i = 0; i < howMany; i++) {
            nextTab[elekB[i]] = elekA[i];
        }
    }

    public Double separeteCycles() {
        boolean[] considered = new boolean[howMany];
        int minAbsIndex = numberOfSmallElek();
        double totalCost = 0;

        for (int i = 0; i < howMany; i++) {
            considered[i] = false;
        }
        for (int i = 0; i < howMany; i++) {
            if (!considered[i]) {
                considered[i] = true;
                int startPoint = i;
                Queue<Integer> singleCycle = new LinkedList<>();
                singleCycle.add(startPoint);
                int currentPoint = nextTab[startPoint];
                while (true) {
                    if (startPoint == currentPoint) {
                        break;
                    } else {
                        singleCycle.add(currentPoint);
                        considered[currentPoint] = true;
                        currentPoint = nextTab[currentPoint];
                    }
                }
                double method1Cost = method1(singleCycle);
                double method2Cost = method2(singleCycle, minAbsIndex);
                if (method1Cost <= method2Cost) {
                    totalCost += method1Cost;
                } else {
                    totalCost += method2Cost;
                }
                singleCycle = null;
            }
        }
        return totalCost;
    }

    public static void main(String[] args) {
        QuickStart qs = new QuickStart();
        qs.mainProgram();
    }

    public void mainProgram() {
        readData();
        double totalCostIs = separeteCycles();
        System.out.printf("%.0f", totalCostIs);
    }

    public double method1(Queue<Integer> cycleX) {
        double method1Cost = 0;
        int minCnumber = getMinimumOfCycle(cycleX);
        method1Cost = sumOfCycle(cycleX) + ((double) (cycleX.size() - 2)) * weightOfElek[minCnumber];
        return method1Cost;
    }

    public double method2(Queue<Integer> cycleX, int minAbs) {
        double method2Cost = 0;
        int minCnumber = getMinimumOfCycle(cycleX);
        method2Cost = sumOfCycle(cycleX) + weightOfElek[minCnumber]
                + ((double) (cycleX.size() + 1) * weightOfElek[minAbs]);
        return method2Cost;
    }

    public int numberOfSmallElek() {
        double minWeight = weightOfElek[0];
        int minWeightIndex = 0;
        for (int i = 0; i < weightOfElek.length; i++) {
            if (weightOfElek[i] < minWeight) {
                minWeight = weightOfElek[i];
                minWeightIndex = i;
            }
        }
        return minWeightIndex;
    }

    public int getMinimumOfCycle(Queue<Integer> copiedCycle) {
        double minC = weightOfElek[copiedCycle.element()];
        int minCnumber = copiedCycle.element();
        for (int anObject : copiedCycle) {
            if (weightOfElek[anObject] < minC) {
                minC = weightOfElek[anObject];
                minCnumber = anObject;
            }
        }
        return minCnumber;
    }

    public double sumOfCycle(Queue<Integer> cycle) {
        double tempSum = 0;
        for (int anObject : cycle) {
            tempSum += weightOfElek[anObject];
        }
        return tempSum;
    }

    public static int[] loadIntArray(int size, Scanner stdInput) {
        int[] resultArray = new int[size];
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = stdInput.nextInt() - 1;
            // java indexing starts at 0, easier to maintain when elek numbers
            // starts at 1
        }
        return resultArray;
    }

    public static double[] loadSizeArray(int size, Scanner stdInput) {
        double[] resultArray = new double[size];
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = stdInput.nextDouble();
        }
        return resultArray;
    }

    public void readData() {
        stdInput = new Scanner(System.in);
        howMany = Integer.parseInt(stdInput.nextLine());
        weightOfElek = loadSizeArray(howMany, stdInput);
        int[] elekA = loadIntArray(howMany, stdInput);
        int[] elekB = loadIntArray(howMany, stdInput);
        prepareGraph(elekA, elekB);
    }
}