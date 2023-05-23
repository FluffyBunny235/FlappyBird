public class Brain {
    /*
Inputs for AI:
- y pos of bird
- bird velocity
- leftX of first pipe
- leftX of second pipe
- topY of bottom first pipe
- topY of bottom second pipe
- frames since last jump
 */
    private final int numLayers = 10;
    private double score;
    private double mutabilityNewLayer = 0.005;
    private double mutabilityNewNode = 0.1;
    private final int nodesPerLayer = 20;
    private Bird body;
    private double tieBreaker;
    private Layer[] layers;
    public Brain(Bird body){
        score = 0;
        tieBreaker = 0;
        this.body = body;
        layers = new Layer[numLayers];
        layers[0] = new Layer(7, 0, this);
        layers[numLayers-1] = new Layer(1, numLayers-1, this);
        for (int i = 1; i < numLayers-1; i++) {
            layers[i] = new Layer(nodesPerLayer, i,this);
        }
        for (int i = 0; i < numLayers; i++) {
            layers[i].initializeVariables();
        }
    }
    public Layer getLayer(int index) {
        return layers[index];
    }
    public boolean computeWillJump() {
        return layers[numLayers-1].getNode(0).getValue() > 0;
    }
    public int getNumLayers() {
        return this.numLayers;
    }
    public void update() {
        layers[0].getNode(0).setValue(Math.tanh(body.getY()));
        layers[0].getNode(1).setValue(Math.tanh(body.getVelocity()));
        Pipe p1 = Main.pipes[0];
        int indexOfP1 = 0;
        for (int i = 1; i < 3; i++) {
            if (Main.pipes[i].getDisplayX()<p1.getDisplayX()) {
                p1 = Main.pipes[i];
                indexOfP1 = i;
            }
        }
        Pipe p2 = Main.pipes[(indexOfP1+1)%3];
        for (int i = 0; i < 3; i++) {
            if (i == indexOfP1) {continue;}
            if (Main.pipes[i].getDisplayX() < p2.getDisplayX()) {
                p2 = Main.pipes[i];
            }
        }
        layers[0].getNode(2).setValue(Math.tanh(p1.getDisplayX()));
        layers[0].getNode(3).setValue(Math.tanh(p2.getDisplayX()));
        layers[0].getNode(4).setValue(Math.tanh(p1.getTopOfLowerPipe()));
        layers[0].getNode(5).setValue(Math.tanh(p2.getTopOfLowerPipe()));
        layers[0].getNode(6).setValue(Math.tanh(body.getFramesSinceJumped()));
        for (int i=1; i < numLayers; i++) {
            layers[i].updateNodes();
        }
    }
    public double getScore() {
        return this.score;
    }
    public void setScore(double score, double tieBreaker) {
        this.score = score;
        this.tieBreaker = tieBreaker;
    }
    public double getTieBreaker() {
        return this.tieBreaker;
    }
    public Brain getMutation() {
        Brain b = new Brain(null);
        for (int i = 0; i < numLayers; i++) {
            b.setLayer(i, layers[i].copy());
        }
        for (int i = 0; i < numLayers; i++) {
            if (Math.random() < mutabilityNewLayer) {
                Layer l = new Layer(layers[i].getSize(), i, b);
                b.setLayer(i, l);
            }
            else {
                for (int k = 0; k < b.getLayer(i).getSize(); k++) {
                    if (Math.random() < mutabilityNewNode) {
                        Node n = new Node(b.getLayer(i), k);
                        b.getLayer(i).setNode(n, k);
                    }
                }
            }
        }
        for (int i = 0; i < numLayers; i++) {
            b.getLayer(i).initializeVariables();
        }
        return b;
    }
    public void setBody(Bird b) {
        this.body = b;
    }
    public void setLayer(int index, Layer l) {
        layers[index] = l;
    }
}
