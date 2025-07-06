import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {

    private Graph graph;
    private Map<Integer, double[]> nodePositions = new HashMap<>();
    private List<Edge> mst = new ArrayList<>();
    private Canvas canvas;
    private GraphicsContext gc;

    private int nodeCount;

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new javafx.geometry.Insets(10));

        // Inputs
        TextField nodesInput = new TextField();
        nodesInput.setPromptText("Enter total nodes");

        TextField srcInput = new TextField();
        srcInput.setPromptText("Source node");

        TextField destInput = new TextField();
        destInput.setPromptText("Destination node");

        TextField weightInput = new TextField();
        weightInput.setPromptText("Weight");

        Button initGraphBtn = new Button("Init Graph");
        Button addEdgeBtn = new Button("Add Edge");
        Button drawBtn = new Button("Draw Graph");
        Button kruskalBtn = new Button("Run Kruskal");

        HBox inputs = new HBox(5, nodesInput, initGraphBtn);
        HBox edgeInputs = new HBox(5, srcInput, destInput, weightInput, addEdgeBtn);
        HBox buttons = new HBox(10, drawBtn, kruskalBtn);

        canvas = new Canvas(800, 500);
        gc = canvas.getGraphicsContext2D();

        root.getChildren().addAll(inputs, edgeInputs, buttons, canvas);

        // Initialize Graph
        initGraphBtn.setOnAction(e -> {
            nodeCount = Integer.parseInt(nodesInput.getText());
            graph = new Graph(nodeCount);
            generatePositions();
            clearCanvas();
            drawNodes();
        });

        // Add edge
        addEdgeBtn.setOnAction(e -> {
            int src = Integer.parseInt(srcInput.getText());
            int dest = Integer.parseInt(destInput.getText());
            int weight = Integer.parseInt(weightInput.getText());
            graph.addEdge(src, dest, weight);
            clearCanvas();
            drawGraph(graph.getEdges(), Color.GRAY);
        });

        // Draw only graph
        drawBtn.setOnAction(e -> {
            clearCanvas();
            drawGraph(graph.getEdges(), Color.GRAY);
        });

        // Run Kruskal
        kruskalBtn.setOnAction(e -> {
            Kruskal k = new Kruskal();
            mst = k.runKruskal(graph);
            drawGraph(graph.getEdges(), Color.GRAY); // background
            drawGraph(mst, Color.RED); // MST
        });

        Scene scene = new Scene(root, 820, 600);
        stage.setTitle("Kruskal Algorithm GUI");
        stage.setScene(scene);
        stage.show();
    }

    private void generatePositions() {
        nodePositions.clear();
        double centerX = 400, centerY = 250, radius = 180;
        for (int i = 0; i < nodeCount; i++) {
            double angle = 2 * Math.PI * i / nodeCount;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            nodePositions.put(i, new double[]{x, y});
        }
    }

    private void clearCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawNodes();
    }

    private void drawNodes() {
        gc.setFill(Color.LIGHTBLUE);
        gc.setStroke(Color.BLACK);
        for (int i = 0; i < nodeCount; i++) {
            double[] pos = nodePositions.get(i);
            gc.fillOval(pos[0] - 15, pos[1] - 15, 30, 30);
            gc.strokeText(String.valueOf(i), pos[0] - 5, pos[1] + 5);
        }
    }

    private void drawGraph(List<Edge> edges, Color color) {
        clearCanvas();
        gc.setStroke(color);
        gc.setLineWidth(color == Color.RED ? 4 : 2);
        for (Edge edge : edges) {
            double[] p1 = nodePositions.get(edge.src);
            double[] p2 = nodePositions.get(edge.dest);
            gc.strokeLine(p1[0], p1[1], p2[0], p2[1]);
            double midX = (p1[0] + p2[0]) / 2;
            double midY = (p1[1] + p2[1]) / 2;
            gc.strokeText(String.valueOf(edge.weight), midX, midY);
        }
        drawNodes();
    }

    public static void main(String[] args) {
        launch();
    }
}
