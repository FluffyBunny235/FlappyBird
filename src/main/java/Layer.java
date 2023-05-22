public class Layer {
    private final Node[] nodes;
    private final int layerNum;
    private final Brain parent;
    public Layer(int numNodes, int layerNum, Brain parent) {
        nodes = new Node[numNodes];
        this.layerNum = layerNum;
        this.parent = parent;
        for (int i = 0; i < numNodes; i++) {
            nodes[i] = new Node(this, i);
        }
    }
    public Layer(int layerNum, Brain parent, Node[] nodes) {
        this.layerNum = layerNum;
        this.parent = parent;
        this.nodes = nodes;
    }
    public int getLayerNum() {
        return this.layerNum;
    }
    public Brain getBrain(){
        return this.parent;
    }
    public Node getNode(int index) {
        return this.nodes[index];
    }
    public void setNode(Node node, int ind) {this.nodes[ind] = node;}
    public int getSize() {
        return this.nodes.length;
    }
    public void updateNodes() {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].updateValue();
        }
    }
    public void initializeVariables() {
        for (int i = 0; i < nodes.length; i++) {
            nodes[i].initializeWeightsAndBiases();
        }
    }
    public Layer copy() {

        Node[] n = new Node[nodes.length];
        System.arraycopy(nodes, 0, n, 0, nodes.length);
        return new Layer(layerNum, parent, n);
    }
}
