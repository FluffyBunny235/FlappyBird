public class Node {
    private final Layer parent;
    private final int index;
    private double bias;
    private double[] weights;
    private double value;
    public Node(Layer parent, int index) {
        this.parent = parent;
        this.index = index;
        this.value = 0;
        this.bias = Math.random()*2-1;
    }
    public void initializeWeightsAndBiases() {
        if (parent.getLayerNum() != parent.getBrain().getNumLayers()-1) {
            this.weights = new double[parent.getBrain().getLayer(parent.getLayerNum()+1).getSize()];
            for (int i = 0; i < weights.length; i++) {
                weights[i] = Math.random()*2-1;
            }
        }
    }
    public Node(Layer parent, int index, double[] weights, double bias)  {
        this.parent = parent;
        this.index= index;
        this.value = 0;
        this.weights = weights;
        this.bias = bias;
    }
    public void updateValue() {
        double x = 0;
        for (int i = 0; i < parent.getBrain().getLayer(parent.getLayerNum()-1).getSize(); i++){
            x += parent.getBrain().getLayer(parent.getLayerNum()-1).getNode(i).getWeightedValue(this.index);
        }
        x+= bias;
        value = Math.tanh(x);
    }
    public double getWeightedValue(int in){
        return this.value*this.weights[in];
    }
    public double getValue(){
        return value;
    }
    public void setValue(double value){
        this.value = value;
    }
    public double getBias() {
        return this.bias;
    }
    public double[] getWeights() {
        return this.weights;
    }
    public void setBias(double value) {
        this.bias = value;
    }
    public void setWeight(int index, double value) {
        this.weights[index] = value;
    }
}
