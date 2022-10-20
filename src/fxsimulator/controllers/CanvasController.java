package fxsimulator.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import fxsimulator.classes.Arrow;
import fxsimulator.classes.DataMatrix;
import fxsimulator.classes.Edge;
import fxsimulator.classes.InputOuputNode;
import fxsimulator.classes.Node;
import fxsimulator.classes.Path;
import fxsimulator.classes.RightClickMenu;
import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.StrokeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class CanvasController implements Initializable, ChangeListener {

    // Mac Carthy
    // SMART
    public ArrayList tousLesChemins = new ArrayList<>();
    public ArrayList all_paths_and_cost = new ArrayList();

    public ArrayList all_shortest_path = new ArrayList<>();
    public ArrayList all_longest_path = new ArrayList<>();

    public static All_path g;

    @FXML
    private TitledPane informationDetails;

    // FIN SMART
    @FXML
    private Label matrixName;

    @FXML
    private JFXComboBox minOrmax;

    @FXML
    private ScrollPane myScrollPane;

    // @FXML
    // private GridPane matrice;
    @FXML
    private Pane resultPane;

    @FXML
    private Pane algorithmPane;

    @FXML
    private Pane dataGraphPane;

    @FXML
    private JFXButton imediatelyButton;

    @FXML
    private JFXButton imediatelyButtonRac;

    @FXML
    private JFXButton stepToStepButton;

    @FXML
    private Label resultLabel;

    @FXML
    private Label pathCost;

    @FXML
    private Label pathCount;

    @FXML
    private JFXButton btnClose;

    // Tous les noeuds liés
    private static ArrayList allLinkedNodes = new ArrayList();

    // Données de chaque matrice ;
    private ArrayList allMatrixDataSource = new ArrayList();
    private ArrayList allMatrixDataTarget = new ArrayList();
    private ArrayList allMatrixDataCrst = new ArrayList();

    // ID MATRICE
    private static int idMatrix = 1;

    // Liste des sommets constituant le chemin optimal
    private ArrayList cheminOptimal = new ArrayList();
    private ArrayList allMinDt = new ArrayList();

    // Liste des sommets constituant le chemin optimal
    private ArrayList cheminMaximal = new ArrayList();

    // CHEMIN LE PLUS COURT
    private String cheminPlusCourt = "";

    // CHEMIN LE PLUS LONG
    private String cheminPlusLong = "";

    // NOEUD DE DEBUT
    NodeFX beginNode;

    // NOEUD DE FIN
    NodeFX endNode;

    //ZONE DE L'ALGORITHME
    @FXML
    private Label valueOfK;

    @FXML
    private JFXButton nextStepButton;

    @FXML
    private JFXButton prevStepButton;

    // Compteur de 
    private static int valeurDeK = 1;

    // Pas 
    private static int pas = 0;

    // Un flèche
    private static Edge myEdge;

    //@FXML
    //private HiddenSidesPane hiddenPane;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private StackPane stackRoot;
    @FXML
    private JFXButton editButton, resetButton;
    @FXML
    private JFXToggleButton addNodeButton, addEdgeButton;
    /*, bfsButton, dfsButton, topSortButton, dijkstraButton,
            articulationPointButton, mstButton;*/
    @FXML
    private ToggleGroup algoToggleGroup;
    @FXML
    private Pane viewer;
    @FXML
    private Group canvasGroup;
    @FXML
    private Line edgeLine;
    @FXML
    private Label sourceText = new Label("Source"), weight;
    @FXML
    private Pane border;
    @FXML
    private Arrow arrow;
    /*@FXML
    private JFXNodesList nodeList;*/
    @FXML
    private JFXSlider slider = new JFXSlider();
    //@FXML
    //private ImageView openHidden;

    boolean menuBool = false;
    ContextMenu globalMenu;

    static int nNode = 2;
    int time = 500;
    NodeFX selectedNode = null;
    List<NodeFX> circles = new ArrayList<>();
    List<Edge> mstEdges = new ArrayList<>(), realEdges = new ArrayList<>();
    List<Shape> edges = new ArrayList<>();
    boolean addNode = true, addEdge = false, calculate = false,
            calculated = false, playing = false, paused = false, pinned = false;
    List<Label> distances = new ArrayList<Label>(), visitTime = new ArrayList<>(), lowTime = new ArrayList<Label>();

    private boolean weighted = true, unweighted = false,
            directed = true, undirected = false,
            bfs = true, dfs = true, dijkstra = true, articulationPoint = true, mst = true, topSortBool = true;

    Algorithm algo = new Algorithm();

    public SequentialTransition st;

    public AnchorPane hiddenRoot = new AnchorPane();

    public static TextArea textFlow = new TextArea();
    public ScrollPane textContainer = new ScrollPane();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ResetHandle(null);

        addEdgeButton.setDisable(false);
        addNodeButton.setDisable(false);
        editButton.setDisable(false);

        slider = new JFXSlider(10, 1000, 500);
        slider.setPrefWidth(150);
        slider.setPrefHeight(80);
        slider.setSnapToTicks(true);
        slider.setMinorTickCount(100);
        slider.setIndicatorPosition(JFXSlider.IndicatorPosition.RIGHT);
        slider.setBlendMode(BlendMode.MULTIPLY);
        slider.setCursor(Cursor.CLOSED_HAND);
        slider.toFront();
        slider.valueProperty().addListener(this);

        hiddenRoot.setPrefWidth(220);
        hiddenRoot.setPrefHeight(581);

        hiddenRoot.setCursor(Cursor.DEFAULT);

        //Set Label "Detail"
        Label detailLabel = new Label("Detail");
        detailLabel.setPrefSize(hiddenRoot.getPrefWidth() - 20, 38);
        detailLabel.setAlignment(Pos.CENTER);
        detailLabel.setFont(new Font("Roboto", 20));
        detailLabel.setPadding(new Insets(7, 40, 3, -10));
        detailLabel.setStyle("-fx-background-color: #dcdde1;");
        detailLabel.setLayoutX(35);

        //Set TextFlow pane properties
        textFlow.setPrefSize(hiddenRoot.getPrefWidth(), hiddenRoot.getPrefHeight() - 2);
        // textFlow.prefHeightProperty().bind(hiddenRoot.heightProperty());
        textFlow.setStyle("-fx-background-color: #dfe6e9;");
        textFlow.setLayoutY(39);
        textContainer.setLayoutY(textFlow.getLayoutY());
        textFlow.setPadding(new Insets(5, 0, 0, 5));
        textFlow.setEditable(false);
        textContainer.setContent(textFlow);

        //Set Pin/Unpin Button
        JFXButton pinUnpin = new JFXButton();
        pinUnpin.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        ImageView imgPin = new ImageView(new Image(getClass().getResourceAsStream("/fxsimulator/res/pinned.png")));
        imgPin.setFitHeight(20);
        imgPin.setFitWidth(20);
        ImageView imgUnpin = new ImageView(new Image(getClass().getResourceAsStream("/fxsimulator/res/unpinned.png")));
        imgUnpin.setFitHeight(20);
        imgUnpin.setFitWidth(20);
        pinUnpin.setGraphic(imgPin);

        pinUnpin.setPrefSize(20, 39);
        pinUnpin.setButtonType(JFXButton.ButtonType.FLAT);
        pinUnpin.setStyle("-fx-background-color: #dcdde1;");
        pinUnpin.setOnMouseClicked(e -> {
            if (pinned) {
                pinUnpin.setGraphic(imgPin);
                //hiddenPane.setPinnedSide(null);
                pinned = false;
            } else {
                pinUnpin.setGraphic(imgUnpin);
                //hiddenPane.setPinnedSide(Side.RIGHT);
                pinned = true;
            }
        });

        //Add Label and TextFlow to hiddenPane
        hiddenRoot.getChildren().addAll(pinUnpin, detailLabel, textContainer);
        //hiddenPane.setRight(hiddenRoot);
        hiddenRoot.setOnMouseEntered(e -> {
            //hiddenPane.setPinnedSide(Side.RIGHT);
            //openHidden.setVisible(false);
            e.consume();
        });
        hiddenRoot.setOnMouseExited(e -> {
            if (!pinned) {
                //hiddenPane.setPinnedSide(null);
                //openHidden.setVisible(true);
            }
            e.consume();
        });

        dataGraphPane.toFront();

        // Type de l'algorithme
        minOrmax.getItems().add("Minimisation");
        minOrmax.getItems().add("Maximisation");

        // Step to Step Button
        stepToStepButton.setDisable(false);

    }

    public void createBeginNode_EndNode() {

        // NOEUD DE DEBUT 
        System.out.println("Noeud début");
        beginNode = new NodeFX(30, 140, 1.2, "D");

        canvasGroup.getChildren().add(beginNode);

        beginNode.setOnMousePressed(mouseHandler);
        beginNode.setOnMouseReleased(mouseHandler);
        beginNode.setOnMouseDragged(mouseHandler);
        beginNode.setOnMouseExited(mouseHandler);
        beginNode.setOnMouseEntered(mouseHandler);

        // beginNode.setStyle("-fx-fill:  #21759b;");
        //beginNode.setStyle("-fx-background-color : white ;");
        ScaleTransition tr = new ScaleTransition(Duration.millis(100), beginNode);
        tr.setByX(10f);
        tr.setByY(10f);
        tr.setInterpolator(Interpolator.EASE_OUT);
        tr.play();

        // NOEUD DE FIN 
        System.out.println("Noeud fin");
        endNode = new NodeFX(750, 140, 1.2, "F");
        canvasGroup.getChildren().add(endNode);

        endNode.setOnMousePressed(mouseHandler);
        endNode.setOnMouseReleased(mouseHandler);
        endNode.setOnMouseDragged(mouseHandler);
        endNode.setOnMouseExited(mouseHandler);
        endNode.setOnMouseEntered(mouseHandler);

        //endNode.setStyle("-fx-fill:  white;");
        ScaleTransition tr2 = new ScaleTransition(Duration.millis(100), endNode);
        tr2.setByX(10f);
        tr2.setByY(10f);
        tr2.setInterpolator(Interpolator.EASE_OUT);
        tr2.play();

    }

    /**
     * Change Listener for change in speed slider values.
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        int temp = (int) slider.getValue();

        if (temp > 500) {
            int diff = temp - 500;
            temp = 500;
            temp -= diff;
            temp += 10;
        } else if (temp < 500) {
            int diff = 500 - temp;
            temp = 500;
            temp += diff;
            temp -= 10;
        }
        time = temp;
        //** System.out.println(time);
    }

    /**
     * Handles events for mouse clicks on the canvas. Adds a new node on the
     * drawing canvas where mouse is clicked.
     *
     * @param ev
     */
    @FXML
    public void handle(MouseEvent ev) {
        if (addNode) {
            /*
            if (nNode == 1) {
                addNodeButton.setDisable(false);
                
                // Mac Carthy
                // Step to Step Button
                stepToStepButton.setDisable(true);
            }
            if (nNode == 2) {
                addEdgeButton.setDisable(false);
                AddNodeHandle(null);
                
                // Mac Carthy
                // Step to Step Button
                //stepToStepButton.setDisable(false);
            }*/

            if (!ev.getSource().equals(canvasGroup)) {
                if (ev.getEventType() == MouseEvent.MOUSE_RELEASED && ev.getButton() == MouseButton.PRIMARY) {
                    if (menuBool == true) {
                        //** System.out.println("here" + ev.getEventType());
                        menuBool = false;
                        return;
                    }
                    nNode++;

                    System.out.println("nNode = " + nNode);

                    String node_id = "x" + nNode;

                    NodeFX circle = new NodeFX(ev.getX(), ev.getY(), 1.2, node_id);

                    canvasGroup.getChildren().add(circle);

                    circle.setOnMousePressed(mouseHandler);
                    circle.setOnMouseReleased(mouseHandler);
                    circle.setOnMouseDragged(mouseHandler);
                    circle.setOnMouseExited(mouseHandler);
                    circle.setOnMouseEntered(mouseHandler);

                    ScaleTransition tr = new ScaleTransition(Duration.millis(100), circle);
                    tr.setByX(10f);
                    tr.setByY(10f);
                    tr.setInterpolator(Interpolator.EASE_OUT);
                    tr.play();

                }
            }

        }
    }

    /**
     * Checks if an edge already exists between two nodes before adding a new
     * edge.
     *
     * @param u = selected node
     * @param v = second selected node
     * @return True if edge already exists. Else false.
     */
    boolean edgeExists(NodeFX u, NodeFX v) {
        for (Edge e : realEdges) {
            if (e.source == u.node && e.target == v.node) {
                return true;
            }
        }
        return false;
    }

    boolean edgeExistForNode(NodeFX u) {
        for (Edge e : realEdges) {
            if (e.source == u.node || e.target == u.node) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an edge between two selected nodes. Handles events for mouse clicks
     * on a node.
     */
    EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent mouseEvent) {
            NodeFX circle = (NodeFX) mouseEvent.getSource();
            Shape line_arrow = null;
            Edge temp = null;

            // Mac
            double xWeightLabel = 0, yWeightLabel = 0;

            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED && mouseEvent.getButton() == MouseButton.PRIMARY) {

                if (!circle.isSelected) {
                    if (selectedNode != null) {

                        if (addEdge && !edgeExists(selectedNode, circle)) {

//                            if (circle.equals(beginNode)) {
//                                alertError("Le noeud " + beginNode.id.getText() + " ne peut pas être un noeud de destination car c'est le noeud de départ !");
//                            } else if (selectedNode.equals(endNode)) {
//                                alertError("Le noeud " + endNode.id.getText() + " ne peut pas être un noeud source car c'est le noeud d'arrivée ! ");
                            //                      } else {
                            weight = new Label();
                            if (directed) {

                                // Check reverse arrow between two nodes
                                if (edgeExists(circle, selectedNode)) {

                                    // 2nd arrow
                                    arrow = new Arrow(selectedNode.point.x - 8, selectedNode.point.y + 8, circle.point.x - 8, circle.point.y + 8);
                                    
                                    xWeightLabel = ((selectedNode.point.x - 8) + (circle.point.x - 8)) / 2;
                                    yWeightLabel = ((selectedNode.point.y + 8) + (circle.point.y + 8)) / 2;

                                    System.out.println("2nd arrow");

                                } else {
                                    // Mahazatra hatrizay
                                    // 1st arrow
                                    // arrow = new Arrow(selectedNode.point.x , selectedNode.point.y-5, circle.point.x, circle.point.y-5);

                                    arrow = new Arrow(selectedNode.point.x + 8, selectedNode.point.y - 8, circle.point.x + 8, circle.point.y - 8);

                                    xWeightLabel = ((selectedNode.point.x + 8) + (circle.point.x + 8)) / 2;
                                    yWeightLabel = ((selectedNode.point.y - 8) + (circle.point.y - 8)) / 2;

                                    System.out.println("1st arrow");

                                }

                                canvasGroup.getChildren().add(arrow);
                                arrow.setId("arrow");

                            }

                            //Adds weight between two selected nodes
                            if (weighted) {
                                weight.setLayoutX(xWeightLabel);
                                weight.setLayoutY(yWeightLabel);

                                TextInputDialog dialog = new TextInputDialog("0");
                                dialog.setTitle(null);
                                dialog.setHeaderText("Enter Weight of the Edge :");
                                dialog.setContentText(null);

                                Optional<String> result = dialog.showAndWait();

                                if (result.isPresent()) {
                                    weight.setText(result.get());
                                } else {
                                    weight.setText("0");
                                }

                                if (weight.getText().equals("")) {
                                    weight.setText("0");
                                }

                                if (isIntegerNumber(weight.getText())) {

                                    temp = new Edge(selectedNode.node, circle.node, Double.valueOf(weight.getText()), arrow, weight);
                                    selectedNode.node.adjacents.add(temp);
                                    canvasGroup.getChildren().add(weight);

                                    edges.add(arrow);
                                    line_arrow = arrow;
                                    realEdges.add(temp);

                                } else {

                                    alertError("Distance invalide");
                                    removeArrow(selectedNode, circle);

                                    return;
                                }

                            }

                            RightClickMenu rt = new RightClickMenu(temp);
                            ContextMenu menu = rt.getMenu();
                            if (weighted) {
                                rt.changeId.setText("Change Weight");
                            }
                            final Shape la = line_arrow;
                            line_arrow.setOnContextMenuRequested(e -> {
                                //**  System.out.println("In Edge Menu :" + menuBool);

                                if (menuBool == true) {
                                    globalMenu.hide();
                                    menuBool = false;
                                }
                                if (addEdge || addNode) {
                                    globalMenu = menu;
                                    menu.show(la, e.getScreenX(), e.getScreenY());
                                    menuBool = true;
                                }
                            });
                            menu.setOnAction(e -> {
                                menuBool = false;
                            });
                            //}
                        }
                        if (addNode || (calculate && !calculated) || addEdge) {
                            selectedNode.isSelected = false;
                            FillTransition ft1 = new FillTransition(Duration.millis(300), selectedNode, Color.RED, Color.BLACK);
                            ft1.play();
                        }
                        selectedNode = null;
                        return;
                    }

                    FillTransition ft = new FillTransition(Duration.millis(300), circle, Color.BLACK, Color.RED);
                    ft.play();
                    circle.isSelected = true;
                    selectedNode = circle;

                    // WHAT TO DO WHEN SELECTED ON ACTIVE ALGORITHM
                    if (calculate && !calculated) {
                        if (bfs) {
                            algo.newBFS(circle.node);
                        } else if (dfs) {
                            algo.newDFS(circle.node);
                        } else if (dijkstra) {
                            algo.newDijkstra(circle.node);
                        }

                        calculated = true;
                    } else if (calculate && calculated && !articulationPoint & !mst && !topSortBool) {

                        for (NodeFX n : circles) {
                            n.isSelected = false;
                            FillTransition ft1 = new FillTransition(Duration.millis(300), n);
                            ft1.setToValue(Color.BLACK);
                            ft1.play();
                        }
                        List<Node> path = algo.getShortestPathTo(circle.node);

                        for (Node n : path) {
                            FillTransition ft1 = new FillTransition(Duration.millis(300), n.circle);
                            ft1.setToValue(Color.BLUE);
                            ft1.play();
                        }
                    }
                } else {
                    circle.isSelected = false;
                    FillTransition ft1 = new FillTransition(Duration.millis(300), circle, Color.RED, Color.BLACK);
                    ft1.play();
                    selectedNode = null;
                }

            }
        }

    };

    /**
     * Get a random node to start Articulation Point.
     *
     * @return A node from the current node list.
     */
    private Node getRandomStart() {
        return circles.get(0).node;
    }

    @FXML
    public void editGraphHandle(ActionEvent event) {

        //***** Mac Carthy *****//
        fctSpecific();

        dataGraphPane.toFront();
		
        canvasGroup.setDisable(false);
		
        imediatelyButton.setDisable(false);
        imediatelyButtonRac.setDisable(false);
		
        addNodeButton.setDisable(false);
        addEdgeButton.setDisable(false);

        minOrmax.getSelectionModel().clearSelection();

        nextStepButton.setDisable(false);
        prevStepButton.setDisable(true);

        // RESET THE COLOR OF THE GRAPH
        String str;

        // Noeud
        for (Object e : canvasGroup.getChildren()) {

            str = e.getClass().toString();

            if (str.equals("class fxsimulator.controllers.CanvasController$NodeFX")) {

                NodeFX nd = (NodeFX) e;

                nd.setStyle("-fx-fill:  gray");
                nd.id.setStyle("-fx-text-fill: gray; -fx-font-weight: bold;");

            }

        }

        // ARC (flèche)
        for (Object e : canvasGroup.getChildren()) {

            str = e.getClass().toString();

            if (str.equals("class fxsimulator.classes.Arrow")) {

                Arrow arc = (Arrow) e;

                arc.setStyle("-fx-fill:  gray");

            }

        }

        // DISTANCE
        for (Object e : canvasGroup.getChildren()) {

            str = e.getClass().toString();

            if (str.equals("class javafx.scene.control.Label")) {

                Label lb = (Label) e;

                lb.setStyle("-fx-text-fill: gray; -fx-font-weight: bold;");

            }

        }

    }

    @FXML
    public void ResetHandle(ActionEvent event) {
        ClearHandle(null);
        nNode = 2;

        canvasGroup.getChildren().clear();
        canvasGroup.getChildren().addAll(viewer);
        selectedNode = null;
        circles = new ArrayList<NodeFX>();
        distances = new ArrayList<Label>();
        visitTime = new ArrayList<Label>();
        lowTime = new ArrayList<Label>();
        addNode = true;
        addEdge = false;
        calculate = false;
        calculated = false;
        addNodeButton.setSelected(true);
        addEdgeButton.setSelected(false);
        addEdgeButton.setDisable(true);
        addNodeButton.setDisable(false);
        editButton.setDisable(false);
        algo = new Algorithm();

        playing = false;
        paused = false;

        //***** Mac Carthy *****//
        fctSpecific();

        beginNode = null;
        endNode = null;

        // Reset the number of node
        Node.setCompteur(0);

        // Create begin node & end node
        createBeginNode_EndNode();

        dataGraphPane.toFront();

        canvasGroup.setDisable(false);
        imediatelyButton.setDisable(false);
        addNodeButton.setDisable(false);
        addEdgeButton.setDisable(false);
        imediatelyButtonRac.setDisable(false);

        minOrmax.getSelectionModel().clearSelection();

        realEdges.clear();

        nextStepButton.setDisable(false);
        prevStepButton.setDisable(true);

    }

    public void fctSpecific() {

        // 
        if (g != null) {
            g.adjList = null;
        }

        resultLabel.setText("GRAPH EDITOR");

        tousLesChemins.clear();
        all_paths_and_cost.clear();
        all_shortest_path.clear();
        all_longest_path.clear();

        allLinkedNodes.clear();

        allMatrixDataSource.clear();
        allMatrixDataTarget.clear();
        allMatrixDataCrst.clear();

        idMatrix = 1;

        cheminOptimal.clear();
        cheminMaximal.clear();

        cheminPlusCourt = "";
        cheminPlusLong = "";

        valeurDeK = 1;
        pas = 0;

        valueOfK.setText("k = " + valeurDeK);
        matrixName.setText("Matrice D" + idMatrix);

    }

    /**
     * Event handler for the Clear button. Re-initiates the distance and node
     * values and labels.
     *
     * @param event
     */
    @FXML
    public void ClearHandle(ActionEvent event) {
        if (st != null && st.getStatus() != Animation.Status.STOPPED) {
            st.stop();
        }
        if (st != null) {
            st.getChildren().clear();
        }
        menuBool = false;
        selectedNode = null;
        calculated = false;
        //** System.out.println("IN CLEAR:" + circles.size());
        for (NodeFX n : circles) {
            n.isSelected = false;
            n.node.visited = false;
            n.node.previous = null;
            n.node.minDistance = Double.POSITIVE_INFINITY;
            n.node.DAGColor = 0;

            FillTransition ft1 = new FillTransition(Duration.millis(300), n);
            ft1.setToValue(Color.BLACK);
            ft1.play();
        }
        for (Shape x : edges) {
            /*
            if (undirected) {
                StrokeTransition ftEdge = new StrokeTransition(Duration.millis(time), x);
                ftEdge.setToValue(Color.BLACK);
                ftEdge.play();
            } else */ if (directed) {
                FillTransition ftEdge = new FillTransition(Duration.millis(time), x);
                ftEdge.setToValue(Color.BLACK);
                ftEdge.play();
            }
        }
        canvasGroup.getChildren().remove(sourceText);
        for (Label x : distances) {
            x.setText("Distance : INFINITY");
            canvasGroup.getChildren().remove(x);
        }
        for (Label x : visitTime) {
            x.setText("Visit : 0");
            canvasGroup.getChildren().remove(x);
        }
        for (Label x : lowTime) {
            x.setText("Low Value : NULL");
            canvasGroup.getChildren().remove(x);
        }
        textFlow.clear();

        //Image image = new Image(getClass().getResourceAsStream("/pause_black_48x48.png"));
        //playPauseImage.setImage(image);
        distances = new ArrayList<>();
        visitTime = new ArrayList<>();
        lowTime = new ArrayList<>();
        addNodeButton.setDisable(false);
        addEdgeButton.setDisable(false);
        AddNodeHandle(null);
        bfs = false;
        dfs = false;
        articulationPoint = false;
        dijkstra = false;
        mst = false;
        topSortBool = false;
        playing = false;
        paused = false;
    }

    /**
     * Event handler for the Add Edge button.
     *
     * @param event
     */
    @FXML
    public void AddEdgeHandle(ActionEvent event) {
        addNode = false;
        addEdge = true;
        calculate = false;
        addNodeButton.setSelected(false);
        addEdgeButton.setSelected(true);

    }

    @FXML
    public void AddNodeHandle(ActionEvent event) {
        addNode = true;
        addEdge = false;
        calculate = false;
        addNodeButton.setSelected(true);
        addEdgeButton.setSelected(false);
        selectedNode = null;

    }

    /**
     * Changes the current Name/ID of a node.
     *
     * @param source Node reference of selected node
     */
    public void changeID(NodeFX source) {
        //** System.out.println("Before-------");
        for (NodeFX u : circles) {
            //** System.out.println(u.node.name + " - ");
            for (Edge v : u.node.adjacents) {
                //** System.out.println(v.source.name + " " + v.target.name);
            }
        }
        selectedNode = null;

        System.out.println("source.id = " + source.id);

        TextInputDialog dialog = new TextInputDialog(Integer.toString(nNode));
        dialog.setTitle(null);
        dialog.setHeaderText("Enter Node ID :");
        dialog.setContentText(null);

        String res = null;
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            res = result.get();
        }

        if (res.equals("")) {
            alertError("Le nom du noeud ne peut pas être vide !");
            return;
        }
        if (!NodeIdExist(res)) {

            circles.get(circles.indexOf(source)).id.setText("");
            circles.get(circles.indexOf(source)).id.setText(res);
            circles.get(circles.indexOf(source)).node.name = res;

            System.out.println("source.id = " + source.id);
            System.out.println("source.name = " + source.node.name);

        } else {

            alertError("Noeud déjà existant");

        }

        //** System.out.println("AFTER----------");
        for (NodeFX u : circles) {
            //** System.out.println(u.node.name + " - ");
            for (Edge v : u.node.adjacents) {
                //** System.out.println(v.source.name + " " + v.target.name);
            }
        }
    }

    /**
     * Deletes the currently selected node.
     *
     * @param sourceFX
     */
    public void deleteNode(NodeFX sourceFX) {

        if (!sourceFX.equals(beginNode) && !sourceFX.equals(endNode)) {

            selectedNode = null;
            System.out.println("Before-------");
            for (NodeFX u : circles) {
                System.out.println(u.node.name + " - ");
                for (Edge v : u.node.adjacents) {
                    System.out.println(v.source.name + " " + v.target.name);
                }
            }

            Node source = sourceFX.node;
            circles.remove(sourceFX);

            List<Edge> tempEdges = new ArrayList<>();
            List<Node> tempNodes = new ArrayList<>();
            for (Edge e : source.adjacents) {
                Node u = e.target;
                for (Edge x : u.adjacents) {
                    if (x.target == source) {
                        x.target = null;
                        tempNodes.add(u);
                        tempEdges.add(x);
                    }
                }
                edges.remove(e.getLine());
                canvasGroup.getChildren().remove(e.getLine());
                mstEdges.remove(e);
            }
            for (Node q : tempNodes) {
                q.adjacents.removeAll(tempEdges);
            }
            List<Edge> tempEdges2 = new ArrayList<>();
            List<Shape> tempArrows = new ArrayList<>();
            List<Node> tempNodes2 = new ArrayList<>();
            for (NodeFX z : circles) {
                for (Edge s : z.node.adjacents) {
                    if (s.target == source) {
                        tempEdges2.add(s);
                        tempArrows.add(s.line);
                        tempNodes2.add(z.node);
                        canvasGroup.getChildren().remove(s.line);
                    }
                }
            }
            for (Node z : tempNodes2) {
                z.adjacents.removeAll(tempEdges2);
            }
            realEdges.removeAll(tempEdges);
            realEdges.removeAll(tempEdges2);
            canvasGroup.getChildren().remove(sourceFX.id);
            canvasGroup.getChildren().remove(sourceFX);

            System.out.println("AFTER----------");
            for (NodeFX u : circles) {
                System.out.println(u.node.name + " - ");
                for (Edge v : u.node.adjacents) {
                    System.out.println(v.source.name + " " + v.target.name);
                }
            }

        } else {
            if (sourceFX.equals(beginNode)) {
                alertError("Impossible de suprrimer le noeud " + sourceFX.id.getText() + " car c'est le noeud de départ.");
            } else if (sourceFX.equals(endNode)) {
                alertError("Impossible de suprrimer le noeud " + sourceFX.id.getText() + " car c'est le noeud d'arrivée.");
            }
        }

    }

    /**
     * Deletes the currently selected Edge.
     *
     * @param sourceEdge
     */
    public void deleteEdge(Edge sourceEdge) {

        List<Edge> ls1 = new ArrayList<>();
        List<Shape> lshape2 = new ArrayList<>();
        for (Edge e : sourceEdge.source.adjacents) {
            if (e.target == sourceEdge.target) {
                ls1.add(e);
                lshape2.add(e.line);
            }
        }
        for (Edge e : sourceEdge.target.adjacents) {
            if (e.target == sourceEdge.source) {
                ls1.add(e);
                lshape2.add(e.line);
            }
        }

        // DELETE WEIGHT
        String str;

        for (Object obj : canvasGroup.getChildren()) {

            str = obj.getClass().toString();

            if (str.equals("class javafx.scene.control.Label")) {

                Label lb = (Label) obj;

                if ((lb.getLayoutX() == (((getNoeudFromName(sourceEdge.source.name).point.x) + (getNoeudFromName(sourceEdge.target.name).point.x)) / 2)) && (lb.getLayoutY() == (((getNoeudFromName(sourceEdge.source.name).point.y) + (getNoeudFromName(sourceEdge.target.name).point.y)) / 2))) {

                    canvasGroup.getChildren().remove(lb);
                    break;

                }
            }

        }

        // DELETE WEIGHT
        sourceEdge.source.adjacents.removeAll(ls1);
        sourceEdge.target.adjacents.removeAll(ls1);
        realEdges.removeAll(ls1);

        edges.removeAll(lshape2);
        canvasGroup.getChildren().removeAll(lshape2);

    }

    /**
     * Change weight of the currently selected edge. Disabled for unweighted
     * graphs.
     *
     * @param sourceEdge
     */
    public void changeWeight(Edge sourceEdge) {
        //** System.out.println("Before-------");
        for (NodeFX u : circles) {
            //** System.out.println(u.node.name + " - ");
            for (Edge v : u.node.adjacents) {
                //** System.out.println(v.source.name + " " + v.target.name + " weight: " + v.weight);
            }
        }

        TextInputDialog dialog = new TextInputDialog("0");
        dialog.setTitle(null);
        dialog.setHeaderText("Enter Weight of the Edge :");
        dialog.setContentText(null);

        String res = null;
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            res = result.get();
        }

        if (isIntegerNumber(res)) {

            for (Edge e : sourceEdge.source.adjacents) {
                if (e.target == sourceEdge.target) {
                    e.weight = Double.valueOf(res);
                    e.weightLabel.setText(res);
                }
            }
            for (Edge e : sourceEdge.target.adjacents) {
                if (e.target == sourceEdge.source) {
                    e.weight = Double.valueOf(res);
                }
            }
            for (Edge e : mstEdges) {
                if (e.source == sourceEdge.source && e.target == sourceEdge.target) {
                    e.weight = Double.valueOf(res);
                }
            }

            //** System.out.println("AFTER----------");
            for (NodeFX p : circles) {
                //** System.out.println(p.node.name + " - ");
                for (Edge q : p.node.adjacents) {
                    //** System.out.println(q.source.name + " " + q.target.name + " weigh: " + q.weight);
                }
            }
        } else {
            alertError("Distance invalide");
            return;
        }

    }

    /**
     * Shape class for the nodes.
     */
    public class NodeFX extends Circle {

        Node node;
        Point point;
        Label distance = new Label("Dist. : INFINITY");
        Label visitTime = new Label("Visit : 0");
        Label lowTime = new Label("Low : 0");
        Label id;
        boolean isSelected = false;

        public NodeFX(double x, double y, double rad, String name) {
            super(x, y, rad);
            node = new Node(name, this);
            point = new Point((int) x, (int) y);
            id = new Label(name);
            canvasGroup.getChildren().add(id);
            //id.setLayoutX(x - 18);
            //id.setLayoutY(y - 18);
            id.setLayoutX(x - 8);
            id.setLayoutY(y - 30);
            id.setTextAlignment(TextAlignment.CENTER);

            this.setOpacity(0.5);
            this.setBlendMode(BlendMode.MULTIPLY);
            this.setId("node");

            //this.setFill(Color.WHITE);
            RightClickMenu rt = new RightClickMenu(this);
            ContextMenu menu = rt.getMenu();
            globalMenu = menu;
            this.setOnContextMenuRequested(e -> {
                if (addEdge || addNode) {
                    menu.show(this, e.getScreenX(), e.getScreenY());
                    menuBool = true;
                }
            });
            menu.setOnAction(e -> {
                menuBool = false;
            });

            System.out.println(id.getText());

            circles.add(this);

        }
    }

    // CHECK ID OF NODE
    boolean NodeIdExist(String nodeId) {

        for (NodeFX p : circles) {

            if (p.id.getText().equals(nodeId)) {

                System.out.println("EQUALS");
                return true;
            }
        }
        return false;
    }

    /*
     * Algorithm Declarations ------------------------------------------
     */
    public class Algorithm {

        //<editor-fold defaultstate="collapsed" desc="Dijkstra">    
        public void newDijkstra(Node source) {
            new Dijkstra(source);
        }

        class Dijkstra {

            Dijkstra(Node source) {

                //<editor-fold defaultstate="collapsed" desc="Animation Control">
                for (NodeFX n : circles) {
                    distances.add(n.distance);
                    n.distance.setLayoutX(n.point.x + 20);
                    n.distance.setLayoutY(n.point.y);
                    canvasGroup.getChildren().add(n.distance);
                }
                sourceText.setLayoutX(source.circle.point.x + 20);
                sourceText.setLayoutY(source.circle.point.y + 10);
                canvasGroup.getChildren().add(sourceText);
                SequentialTransition st = new SequentialTransition();
                source.circle.distance.setText("Dist. : " + 0);
                //</editor-fold>

                source.minDistance = 0;
                PriorityQueue<Node> pq = new PriorityQueue<Node>();
                pq.add(source);
                while (!pq.isEmpty()) {
                    Node u = pq.poll();
                    //** System.out.println(u.name);
                    //<editor-fold defaultstate="collapsed" desc="Animation Control">
                    FillTransition ft = new FillTransition(Duration.millis(time), u.circle);
                    ft.setToValue(Color.CHOCOLATE);
                    st.getChildren().add(ft);
                    String str = "";
                    str = str.concat("Popped : Node(" + u.name + "), Current Distance: " + u.minDistance + "\n");
                    final String str2 = str;
                    FadeTransition fd = new FadeTransition(Duration.millis(10), textFlow);
                    fd.setOnFinished(e -> {
                        textFlow.appendText(str2);
                    });
                    fd.onFinishedProperty();
                    st.getChildren().add(fd);
                    //</editor-fold>
                    //** System.out.println(u.name);
                    for (Edge e : u.adjacents) {
                        if (e != null) {
                            Node v = e.target;
                            //** System.out.println("HERE " + v.name);
                            if (u.minDistance + e.weight < v.minDistance) {
                                pq.remove(v);
                                v.minDistance = u.minDistance + e.weight;
                                v.previous = u;
                                pq.add(v);


                                //<editor-fold defaultstate="collapsed" desc="Node visiting animation">
                                //<editor-fold defaultstate="collapsed" desc="Change Edge colors">
                                /*
                                if (undirected) {
                                    StrokeTransition ftEdge = new StrokeTransition(Duration.millis(time), e.line);
                                    ftEdge.setToValue(Color.FORESTGREEN);
                                    st.getChildren().add(ftEdge);
                                } else*/ if (directed) {
                                    FillTransition ftEdge = new FillTransition(Duration.millis(time), e.line);
                                    ftEdge.setToValue(Color.FORESTGREEN);
                                    st.getChildren().add(ftEdge);
                                }
                                //</editor-fold>
                                FillTransition ft1 = new FillTransition(Duration.millis(time), v.circle);
                                ft1.setToValue(Color.FORESTGREEN);
                                ft1.setOnFinished(ev -> {
                                    v.circle.distance.setText("Dist. : " + v.minDistance);
                                });
                                ft1.onFinishedProperty();
                                st.getChildren().add(ft1);

                                str = "\t";
                                str = str.concat("Pushing : Node(" + v.name + "), (" + u.name + "--" + v.name + ") Distance : " + v.minDistance + "\n");
                                final String str1 = str;
                                FadeTransition fd2 = new FadeTransition(Duration.millis(10), textFlow);
                                fd2.setOnFinished(ev -> {
                                    textFlow.appendText(str1);
                                });
                                fd2.onFinishedProperty();
                                st.getChildren().add(fd2);
                                //</editor-fold>
                            }
                        }
                    }
                    //<editor-fold defaultstate="collapsed" desc="Animation Control">
                    FillTransition ft2 = new FillTransition(Duration.millis(time), u.circle);
                    ft2.setToValue(Color.BLUEVIOLET);
                    st.getChildren().add(ft2);
                    //</editor-fold>
                }

                //<editor-fold defaultstate="collapsed" desc="Animation Control">
                st.setOnFinished(ev -> {
                    for (NodeFX n : circles) {
                        FillTransition ft1 = new FillTransition(Duration.millis(time), n);
                        ft1.setToValue(Color.BLACK);
                        ft1.play();
                    }
                    if (directed) {
                        for (Shape n : edges) {
                            n.setFill(Color.BLACK);
                        }
                    }
                    /*else if (undirected) {
                        for (Shape n : edges) {
                            n.setStroke(Color.BLACK);
                        }
                    } */
                    FillTransition ft1 = new FillTransition(Duration.millis(time), source.circle);
                    ft1.setToValue(Color.RED);
                    ft1.play();
                    //Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
                    //playPauseImage.setImage(image);
                    paused = true;
                    playing = false;
                    textFlow.appendText("---Finished--\n");
                });
                st.onFinishedProperty();
                st.play();
                playing = true;
                paused = false;
                //</editor-fold>
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="BFS">
        public void newBFS(Node source) {
            new BFS(source);
        }

        class BFS {

            BFS(Node source) {

                //<editor-fold defaultstate="collapsed" desc="Set labels and distances">
                for (NodeFX n : circles) {
                    distances.add(n.distance);
                    n.distance.setLayoutX(n.point.x + 20);
                    n.distance.setLayoutY(n.point.y);
                    canvasGroup.getChildren().add(n.distance);
                }
                sourceText.setLayoutX(source.circle.point.x + 20);
                sourceText.setLayoutY(source.circle.point.y + 10);
                canvasGroup.getChildren().add(sourceText);
                st = new SequentialTransition();
                source.circle.distance.setText("Dist. : " + 0);
                //</editor-fold>

                source.minDistance = 0;
                source.visited = true;
                LinkedList<Node> q = new LinkedList<Node>();
                q.push(source);
                while (!q.isEmpty()) {
                    Node u = q.removeLast();
                    //<editor-fold defaultstate="collapsed" desc="Node Popped Animation">
                    FillTransition ft = new FillTransition(Duration.millis(time), u.circle);
                    if (u.circle.getFill() == Color.BLACK) {
                        ft.setToValue(Color.CHOCOLATE);
                    }
                    st.getChildren().add(ft);

                    String str = "";
                    str = str.concat("Popped : Node(" + u.name + ")\n");
                    final String str2 = str;
                    FadeTransition fd = new FadeTransition(Duration.millis(10), textFlow);
                    fd.setOnFinished(e -> {
                        textFlow.appendText(str2);
                    });
                    fd.onFinishedProperty();
                    st.getChildren().add(fd);
                    //</editor-fold>
                    System.out.println(u.name);
                    for (Edge e : u.adjacents) {
                        if (e != null) {
                            Node v = e.target;

                            if (!v.visited) {
                                v.minDistance = u.minDistance + 1;
                                v.visited = true;
                                q.push(v);
                                v.previous = u;

                                //<editor-fold defaultstate="collapsed" desc="Node visiting animation">
                                //<editor-fold defaultstate="collapsed" desc="Change Edge colors">
                                /*
                                if (undirected) {
                                    StrokeTransition ftEdge = new StrokeTransition(Duration.millis(time), e.line);
                                    ftEdge.setToValue(Color.FORESTGREEN);
                                    st.getChildren().add(ftEdge);
                                } else if (directed) {
                                    FillTransition ftEdge = new FillTransition(Duration.millis(time), e.line);
                                    ftEdge.setToValue(Color.FORESTGREEN);
                                    st.getChildren().add(ftEdge);
                                }
                                 */
                                //</editor-fold>
                                FillTransition ft1 = new FillTransition(Duration.millis(time), v.circle);
                                ft1.setToValue(Color.FORESTGREEN);
                                ft1.setOnFinished(ev -> {
                                    v.circle.distance.setText("Dist. : " + v.minDistance);
                                });
                                ft1.onFinishedProperty();
                                st.getChildren().add(ft1);

                                str = "\t";
                                str = str.concat("Pushing : Node(" + v.name + ")\n");
                                final String str1 = str;
                                FadeTransition fd2 = new FadeTransition(Duration.millis(10), textFlow);
                                fd2.setOnFinished(ev -> {
                                    textFlow.appendText(str1);
                                });
                                fd2.onFinishedProperty();
                                st.getChildren().add(fd2);
                                //</editor-fold>
                            }
                        }
                    }
                    //<editor-fold defaultstate="collapsed" desc="Animation Control">
                    FillTransition ft2 = new FillTransition(Duration.millis(time), u.circle);
                    ft2.setToValue(Color.BLUEVIOLET);
                    st.getChildren().add(ft2);
                    //</editor-fold>
                }

                //<editor-fold defaultstate="collapsed" desc="Animation Control">
                st.setOnFinished(ev -> {
                    for (NodeFX n : circles) {
                        FillTransition ft1 = new FillTransition(Duration.millis(time), n);
                        ft1.setToValue(Color.BLACK);
                        ft1.play();
                    }
                    if (directed) {
                        for (Shape n : edges) {
                            n.setFill(Color.BLACK);
                        }
                    }
                    /*else if (undirected) {
                        for (Shape n : edges) {
                            n.setStroke(Color.BLACK);
                        }
                    }*/
                    FillTransition ft1 = new FillTransition(Duration.millis(time), source.circle);
                    ft1.setToValue(Color.RED);
                    ft1.play();
                    Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
                    //playPauseImage.setImage(image);
                    paused = true;
                    playing = false;
                    textFlow.appendText("---Finished--\n");
                });
                st.onFinishedProperty();
                st.play();
                playing = true;
                paused = false;
                //</editor-fold>

            }

        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="TopSort">
        public void newTopSort() {
            new TopSort();
        }

        class TopSort {

            String reverse = "";
            List<String> topSort = new ArrayList<>();
            boolean cycleFound = false;

            TopSort() {

                //<editor-fold defaultstate="collapsed" desc="Animation Setup Distances">
                st = new SequentialTransition();
                //</editor-fold>

                cycleFound = false;
                for (NodeFX n : circles) {
                    if (n.node.DAGColor == 0) {
                        cycleExists(n.node, 0);
                    }
                }
                if (cycleFound == false) {
                    for (NodeFX source : circles) {
                        if (source.node.visited == false) {
                            topsortRecursion(source.node, 0);
                        }
                    }

                    //** System.out.println("Hello World " + topSort);
                    Collections.reverse(topSort);
                    for (String s : topSort) {
                        reverse += " -> " + s;
                    }
                    reverse = reverse.replaceFirst(" -> ", "");
                    //** System.out.println(reverse);

                    //<editor-fold defaultstate="collapsed" desc="Animation after algorithm is finished">
                    st.setOnFinished(ev -> {
                        for (NodeFX n : circles) {
                            FillTransition ft1 = new FillTransition(Duration.millis(time), n);
                            ft1.setToValue(Color.BLACK);
                            ft1.play();
                        }
                        if (directed) {
                            for (Shape n : edges) {
                                n.setFill(Color.BLACK);
                            }
                        }
                        /*else if (undirected) {
                            for (Shape n : edges) {
                                n.setStroke(Color.BLACK);
                            }
                        }
                         */

                        Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
                        // playPauseImage.setImage(image);
                        paused = true;
                        playing = false;
                        textFlow.appendText("---Finished--\n\n");
                        textFlow.appendText("Top Sort: " + reverse + "\n");

                    });
                    st.onFinishedProperty();
                    st.play();

                    playing = true;
                    paused = false;
                    //</editor-fold>
                } else {
                    //** System.out.println("Cycle");
                    BoxBlur blur = new BoxBlur(3, 3, 3);

                    JFXDialogLayout dialogLayout = new JFXDialogLayout();
                    dialogLayout.setStyle("-fx-background-color:#dfe6e9");
                    JFXDialog dialog = new JFXDialog(stackRoot, dialogLayout, JFXDialog.DialogTransition.TOP);

                    JFXButton button = new JFXButton("OK");
                    button.setPrefSize(50, 30);
                    button.getStyleClass().add("dialog-button");
                    button.setButtonType(JFXButton.ButtonType.RAISED);
                    dialogLayout.setActions(button);
                    Label message = new Label("     Cycle Detected!\n"
                            + "Cannot run TopSort on a  Directed Cyclic Graph!");
                    message.setId("message");
                    dialogLayout.setBody(message);
                    button.setOnAction(e -> {
                        dialog.close();
                        anchorRoot.setEffect(null);
                    });
                    dialog.setOnDialogClosed(e -> {
                        stackRoot.toBack();
                        anchorRoot.setEffect(null);
                        ClearHandle(null);
                    });

                    stackRoot.toFront();
                    dialog.toFront();
                    dialog.show();
                    anchorRoot.setEffect(blur);
                    dialogLayout.setPadding(new Insets(0, 0, 0, 0));
                }
            }

            void cycleExists(Node source, int level) {
                source.DAGColor = 1;
                for (Edge e : source.adjacents) {
                    if (e != null) {
                        Node v = e.target;
                        if (v.DAGColor == 1) {
                            cycleFound = true;
                        } else if (v.DAGColor == 0) {
                            v.previous = source;
                            cycleExists(v, level + 1);
                        }
                    }
                }
                source.DAGColor = 2;
            }

            public void topsortRecursion(Node source, int level) {
                //<editor-fold defaultstate="collapsed" desc="Animation Control">
                FillTransition ft = new FillTransition(Duration.millis(time), source.circle);
                if (source.circle.getFill() == Color.BLACK) {
                    ft.setToValue(Color.FORESTGREEN);
                }
                st.getChildren().add(ft);

                String str = "";
                for (int i = 0; i < level; i++) {
                    str = str.concat("\t");
                }
                str = str.concat("Recursion(" + source.name + ") Enter\n");
                final String str2 = str;
                FadeTransition fd = new FadeTransition(Duration.millis(10), textFlow);
                fd.setOnFinished(e -> {
                    textFlow.appendText(str2);
                });
                fd.onFinishedProperty();
                st.getChildren().add(fd);
                //</editor-fold>
                source.visited = true;
                for (Edge e : source.adjacents) {
                    if (e != null) {
                        Node v = e.target;
                        if (!v.visited) {
                            v.minDistance = source.minDistance + 1;
                            v.previous = source;


                            //<editor-fold defaultstate="collapsed" desc="Change Edge colors">
                            /*
                            if (undirected) {
                                StrokeTransition ftEdge = new StrokeTransition(Duration.millis(time), e.line);
                                ftEdge.setToValue(Color.FORESTGREEN);
                                st.getChildren().add(ftEdge);
                            } else */ if (directed) {
                                FillTransition ftEdge = new FillTransition(Duration.millis(time), e.line);
                                ftEdge.setToValue(Color.FORESTGREEN);
                                st.getChildren().add(ftEdge);
                            }
                            //</editor-fold>
                            topsortRecursion(v, level + 1);


                            //<editor-fold defaultstate="collapsed" desc="Animation Control">
                            //<editor-fold defaultstate="collapsed" desc="Change Edge colors">
                            /*if (undirected) {
                                StrokeTransition ftEdge = new StrokeTransition(Duration.millis(time), e.line);
                                ftEdge.setToValue(Color.BLUEVIOLET);
                                st.getChildren().add(ftEdge);
                            } else */ if (directed) {
                                FillTransition ftEdge = new FillTransition(Duration.millis(time), e.line);
                                ftEdge.setToValue(Color.BLUEVIOLET);
                                st.getChildren().add(ftEdge);
                            }
                            //</editor-fold>
                            FillTransition ft1 = new FillTransition(Duration.millis(time), v.circle);
                            ft1.setToValue(Color.BLUEVIOLET);
                            ft1.onFinishedProperty();
                            st.getChildren().add(ft1);
                            //</editor-fold>
                        }
                    }
                }
                str = "";
                for (int i = 0; i < level; i++) {
                    str = str.concat("\t");
                }
                topSort.add(source.name);

                //<editor-fold defaultstate="collapsed" desc="Recursion exit text">
                str = str.concat("Recursion(" + source.name + ") Exit\n");
                final String str1 = str;
                fd = new FadeTransition(Duration.millis(10), textFlow);
                fd.setOnFinished(e -> {
                    textFlow.appendText(str1);
                });
                fd.onFinishedProperty();
                st.getChildren().add(fd);
                //</editor-fold>
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Articulation Point">
        public void newArticulationPoint(Node s) {
            new ArticulationPoint(s);
        }

        class ArticulationPoint {

            int timeCnt = 0;

            ArticulationPoint(Node source) {

                //<editor-fold defaultstate="collapsed" desc="Animation Setup Distances">
                for (NodeFX n : circles) {
                    visitTime.add(n.visitTime);
                    n.visitTime.setLayoutX(n.point.x + 20);
                    n.visitTime.setLayoutY(n.point.y);
                    canvasGroup.getChildren().add(n.visitTime);

                    lowTime.add(n.lowTime);
                    n.lowTime.setLayoutX(n.point.x + 20);
                    n.lowTime.setLayoutY(n.point.y + 13);
                    canvasGroup.getChildren().add(n.lowTime);

                    n.node.isArticulationPoint = false;
                }

                st = new SequentialTransition();
                source.circle.lowTime.setText("Low : " + source.name);
                source.circle.visitTime.setText("Visit : " + source.visitTime);
                //</editor-fold>

                timeCnt = 0;
                RecAP(source);

                for (NodeFX n : circles) {
                    if (n.node.isArticulationPoint) {
                        System.out.println(n.node.name);
                    }
                }

                //<editor-fold defaultstate="collapsed" desc="Animation after algorithm is finished">
                st.setOnFinished(ev -> {
                    for (NodeFX n : circles) {
                        FillTransition ft1 = new FillTransition(Duration.millis(time), n);
                        ft1.setToValue(Color.BLACK);
                        ft1.play();
                    }
                    if (directed) {
                        for (Shape n : edges) {
                            n.setFill(Color.BLACK);
                        }
                    }
                    /* else if (undirected) {
                        for (Shape n : edges) {
                            n.setStroke(Color.BLACK);
                        }
                       
                    }*/
                    for (NodeFX n : circles) {
                        if (n.node.isArticulationPoint) {
                            FillTransition ft1 = new FillTransition(Duration.millis(time), n);
                            ft1.setToValue(Color.CHARTREUSE);
                            ft1.play();
                        }
                    }
                    //Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
                    //playPauseImage.setImage(image);
                    paused = true;
                    playing = false;
                });
                st.onFinishedProperty();
                st.play();
                playing = true;
                //</editor-fold>
            }

            void RecAP(Node s) {
                //<editor-fold defaultstate="collapsed" desc="Animation Control">
                FillTransition ft = new FillTransition(Duration.millis(time), s.circle);
                if (s.circle.getFill() == Color.BLACK) {
                    ft.setToValue(Color.FORESTGREEN);
                }
                ft.setOnFinished(ev -> {
                    s.circle.lowTime.setText("Low : " + s.lowTime);
                    s.circle.visitTime.setText("Visit : " + s.visitTime);
                });
                st.getChildren().add(ft);
                //</editor-fold>
                s.visited = true;
                s.visitTime = timeCnt;
                s.lowTime = timeCnt;

                timeCnt++;
                int childCount = 0;

                for (Edge e : s.adjacents) {
                    if (e != null) {
                        Node v = e.target;
                        if (s.previous == v) {
                            continue;
                        }
                        if (!v.visited) {
                            v.previous = s;
                            childCount++;
                            //<editor-fold defaultstate="collapsed" desc="Change Edge colors">
                            /*if (undirected) {
                                StrokeTransition ftEdge = new StrokeTransition(Duration.millis(time), e.line);
                                ftEdge.setToValue(Color.FORESTGREEN);
                                st.getChildren().add(ftEdge);
                            } else */
                            if (directed) {
                                FillTransition ftEdge = new FillTransition(Duration.millis(time), e.line);
                                ftEdge.setToValue(Color.FORESTGREEN);
                                st.getChildren().add(ftEdge);
                            }
                            //</editor-fold>
                            RecAP(v);

                            s.lowTime = Math.min(s.lowTime, v.lowTime);
                            if (s.visitTime <= v.lowTime && s.previous != null) {
                                s.isArticulationPoint = true;
                            }


                            //<editor-fold defaultstate="collapsed" desc="Animation Control">
                            ///<editor-fold defaultstate="collapsed" desc="Change Edge colors">
                            /*if (undirected) {
                                StrokeTransition ftEdge = new StrokeTransition(Duration.millis(time), e.line);
                                ftEdge.setToValue(Color.BLUEVIOLET);
                                st.getChildren().add(ftEdge);
                            } else */ if (directed) {
                                FillTransition ftEdge = new FillTransition(Duration.millis(time), e.line);
                                ftEdge.setToValue(Color.BLUEVIOLET);
                                st.getChildren().add(ftEdge);
                            }
                            //</editor-fold>
                            FillTransition ft1 = new FillTransition(Duration.millis(time), v.circle);
                            ft1.setToValue(Color.BLUEVIOLET);
                            ft1.setOnFinished(ev -> {
                                s.circle.lowTime.setText("Low : " + s.lowTime);
                                s.circle.visitTime.setText("Visit : " + s.visitTime);
                            });
                            ft1.onFinishedProperty();
                            st.getChildren().add(ft1);
                            //</editor-fold>
                        } else {
                            s.lowTime = Math.min(s.lowTime, v.visitTime);
                        }
                    }
                }
                if (childCount > 1 && s.previous == null) {
                    s.isArticulationPoint = true;
                }
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="MST">
        public void newMST() {
            new MST();
        }

        class MST {

            int mstValue = 0;

            Node findParent(Node x) {
                if (x == x.previous) {
                    return x;
                }
                x.previous = findParent(x.previous);
                return x.previous;
            }

            void unionNode(Node x, Node y) {
                Node px = findParent(x);
                Node py = findParent(y);
                if (px == py) {
                    return;
                }
                if (Integer.valueOf(px.name) < Integer.valueOf(py.name)) {
                    px.previous = py;
                } else {
                    py.previous = px;
                }
            }

            public MST() {

                st = new SequentialTransition();
                for (NodeFX x : circles) {
                    x.node.previous = x.node;
                }

                //<editor-fold defaultstate="collapsed" desc="Detail Information">
                String init = "Intially : \n";
                for (NodeFX x : circles) {
                    final String s = "Node : " + x.node.name + " , Parent: " + x.node.previous.name + "\n";
                    FadeTransition fd = new FadeTransition(Duration.millis(10), textFlow);
                    fd.setOnFinished(e -> {
                        textFlow.appendText(s);
                    });
                    fd.onFinishedProperty();
                    st.getChildren().add(fd);
                }
                final String s = "Start Algorithm :---\n";
                FadeTransition fdss = new FadeTransition(Duration.millis(10), textFlow);
                fdss.setOnFinished(ev -> {
                    textFlow.appendText(s);
                });
                fdss.onFinishedProperty();
                st.getChildren().add(fdss);
                //</editor-fold>
                Collections.sort(mstEdges, new Comparator<Edge>() {
                    public int compare(Edge o1, Edge o2) {
                        if (o1.weight == o2.weight) {
                            return 0;
                        }
                        return o1.weight > o2.weight ? 1 : -1;
                    }
                });

                for (Edge e : mstEdges) {

                    StrokeTransition ft1 = new StrokeTransition(Duration.millis(time), e.line);
                    ft1.setToValue(Color.DARKORANGE);
                    st.getChildren().add(ft1);

                    //<editor-fold defaultstate="collapsed" desc="Detail Information">
                    final String se = "Selected Edge:- (" + e.source.name.trim() + "--" + e.target.name.trim() + ") Weight: " + String.valueOf(e.weight) + " \n";
                    FadeTransition fdx = new FadeTransition(Duration.millis(10), textFlow);
                    fdx.setOnFinished(evx -> {
                        textFlow.appendText(se);
                    });
                    fdx.onFinishedProperty();
                    st.getChildren().add(fdx);

                    final String s1 = "\t-> Node :" + e.source.name.trim() + "  Parent: " + findParent(e.source.previous).name.trim() + "\n";
                    FadeTransition fdx2 = new FadeTransition(Duration.millis(10), textFlow);
                    fdx2.setOnFinished(evx -> {
                        textFlow.appendText(s1);
                    });
                    fdx2.onFinishedProperty();
                    st.getChildren().add(fdx2);

                    final String s2 = "\t-> Node :" + e.target.name.trim() + "  Parent: " + findParent(e.target.previous).name.trim() + "\n";
                    FadeTransition fdx3 = new FadeTransition(Duration.millis(10), textFlow);
                    fdx3.setOnFinished(evx -> {
                        textFlow.appendText(s2);
                    });
                    fdx3.onFinishedProperty();
                    st.getChildren().add(fdx3);
                    //</editor-fold>

                    if (findParent(e.source.previous) != findParent(e.target.previous)) {
                        unionNode(e.source, e.target);
                        mstValue += e.weight;

                        //<editor-fold defaultstate="collapsed" desc="Detail Information">
                        final String sa = "\t---->Unioned\n";
                        final String sa1 = "\t\t->Node :" + e.source.name.trim() + "  Parent: " + findParent(e.source.previous).name.trim() + "\n";
                        final String sa2 = "\t\t->Node :" + e.target.name.trim() + "  Parent: " + findParent(e.target.previous).name.trim() + "\n";
                        FadeTransition fdx4 = new FadeTransition(Duration.millis(10), textFlow);
                        fdx4.setOnFinished(evx -> {
                            textFlow.appendText(sa);
                        });
                        fdx4.onFinishedProperty();
                        st.getChildren().add(fdx4);
                        FadeTransition fdx5 = new FadeTransition(Duration.millis(10), textFlow);
                        fdx5.setOnFinished(evx -> {
                            textFlow.appendText(sa1);
                        });
                        fdx5.onFinishedProperty();
                        st.getChildren().add(fdx5);
                        FadeTransition fdx6 = new FadeTransition(Duration.millis(10), textFlow);
                        fdx6.setOnFinished(evx -> {
                            textFlow.appendText(sa2);
                        });
                        fdx6.onFinishedProperty();
                        st.getChildren().add(fdx6);

                        StrokeTransition ft2 = new StrokeTransition(Duration.millis(time), e.line);
                        ft2.setToValue(Color.DARKGREEN);
                        st.getChildren().add(ft2);

                        FillTransition ft3 = new FillTransition(Duration.millis(time), e.source.circle);
                        ft3.setToValue(Color.AQUA);
                        st.getChildren().add(ft3);

                        ft3 = new FillTransition(Duration.millis(time), e.target.circle);
                        ft3.setToValue(Color.AQUA);
                        st.getChildren().add(ft3);
                        //</editor-fold>
                    } else {
                        //<editor-fold defaultstate="collapsed" desc="Detail Info">
                        final String sa = "\t---->Cycle Detected\n";
                        FadeTransition fdx7 = new FadeTransition(Duration.millis(10), textFlow);
                        fdx7.setOnFinished(evx -> {
                            textFlow.appendText(sa);
                        });
                        fdx7.onFinishedProperty();
                        st.getChildren().add(fdx7);
                        //</editor-fold>
                        StrokeTransition ft2 = new StrokeTransition(Duration.millis(time), e.line);
                        ft2.setToValue(Color.DARKRED);
                        st.getChildren().add(ft2);

                        ft2 = new StrokeTransition(Duration.millis(time), e.line);
                        ft2.setToValue(Color.web("#E0E0E0"));
                        st.getChildren().add(ft2);

                    }
                }

                //<editor-fold defaultstate="collapsed" desc="Animation after algorithm is finished">
                st.setOnFinished(ev -> {
                    //Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
                    //playPauseImage.setImage(image);
                    paused = true;
                    playing = false;
                    textFlow.appendText("Minimum Cost of the Graph " + mstValue);
                });
                st.onFinishedProperty();
                st.play();
                playing = true;
                //</editor-fold>
                System.out.println("" + mstValue);
            }
        }
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="DFS">
        public void newDFS(Node source) {
            new DFS(source);
        }

        class DFS {

            DFS(Node source) {

                //<editor-fold defaultstate="collapsed" desc="Animation Setup Distances">
                for (NodeFX n : circles) {
                    distances.add(n.distance);
                    n.distance.setLayoutX(n.point.x + 20);
                    n.distance.setLayoutY(n.point.y);
                    canvasGroup.getChildren().add(n.distance);
                }
                sourceText.setLayoutX(source.circle.point.x + 20);
                sourceText.setLayoutY(source.circle.point.y + 10);
                canvasGroup.getChildren().add(sourceText);
                st = new SequentialTransition();
                source.circle.distance.setText("Dist. : " + 0);
                //</editor-fold>

                source.minDistance = 0;
                source.visited = true;
                DFSRecursion(source, 0);

                //<editor-fold defaultstate="collapsed" desc="Animation after algorithm is finished">
                st.setOnFinished(ev -> {
                    for (NodeFX n : circles) {
                        FillTransition ft1 = new FillTransition(Duration.millis(time), n);
                        ft1.setToValue(Color.BLACK);
                        ft1.play();
                    }
                    if (directed) {
                        for (Shape n : edges) {
                            n.setFill(Color.BLACK);
                        }
                    }
                    /*else if (undirected) {
                        for (Shape n : edges) {
                            n.setStroke(Color.BLACK);
                        }
                    }*/
                    FillTransition ft1 = new FillTransition(Duration.millis(time), source.circle);
                    ft1.setToValue(Color.RED);
                    ft1.play();
                    //Image image = new Image(getClass().getResourceAsStream("/play_arrow_black_48x48.png"));
                    //playPauseImage.setImage(image);
                    paused = true;
                    playing = false;
                    textFlow.appendText("---Finished--\n");
                });
                st.onFinishedProperty();
                st.play();
                playing = true;
                paused = false;
                //</editor-fold>
            }

            public void DFSRecursion(Node source, int level) {
                //<editor-fold defaultstate="collapsed" desc="Animation Control">
                FillTransition ft = new FillTransition(Duration.millis(time), source.circle);
                if (source.circle.getFill() == Color.BLACK) {
                    ft.setToValue(Color.FORESTGREEN);
                }
                st.getChildren().add(ft);

                String str = "";
                for (int i = 0; i < level; i++) {
                    str = str.concat("\t");
                }
                str = str.concat("DFS(" + source.name + ") Enter\n");
                final String str2 = str;
                FadeTransition fd = new FadeTransition(Duration.millis(10), textFlow);
                fd.setOnFinished(e -> {
                    textFlow.appendText(str2);
                });
                fd.onFinishedProperty();
                st.getChildren().add(fd);
                //</editor-fold>
                for (Edge e : source.adjacents) {
                    if (e != null) {
                        Node v = e.target;
                        if (!v.visited) {
                            v.minDistance = source.minDistance + 1;
                            v.visited = true;
                            v.previous = source;
//                        v.circle.distance.setText("Dist. : " + v.minDistance);
                            //<editor-fold defaultstate="collapsed" desc="Change Edge colors">
                            /*if (undirected) {
                                StrokeTransition ftEdge = new StrokeTransition(Duration.millis(time), e.line);
                                ftEdge.setToValue(Color.FORESTGREEN);
                                st.getChildren().add(ftEdge);
                            } else */
                            if (directed) {
                                FillTransition ftEdge = new FillTransition(Duration.millis(time), e.line);
                                ftEdge.setToValue(Color.FORESTGREEN);
                                st.getChildren().add(ftEdge);
                            }
                            //</editor-fold>
                            DFSRecursion(v, level + 1);


                            //<editor-fold defaultstate="collapsed" desc="Animation Control">
                            //<editor-fold defaultstate="collapsed" desc="Change Edge colors">
                            /*if (undirected) {
                                StrokeTransition ftEdge = new StrokeTransition(Duration.millis(time), e.line);
                                ftEdge.setToValue(Color.BLUEVIOLET);
                                st.getChildren().add(ftEdge);
                            } else */ if (directed) {
                                FillTransition ftEdge = new FillTransition(Duration.millis(time), e.line);
                                ftEdge.setToValue(Color.BLUEVIOLET);
                                st.getChildren().add(ftEdge);
                            }
                            //</editor-fold>
                            FillTransition ft1 = new FillTransition(Duration.millis(time), v.circle);
                            ft1.setToValue(Color.BLUEVIOLET);
                            ft1.onFinishedProperty();
                            ft1.setOnFinished(ev -> {
                                v.circle.distance.setText("Dist. : " + v.minDistance);
                            });
                            st.getChildren().add(ft1);
                            //</editor-fold>
                        }
                    }
                }
                str = "";
                for (int i = 0; i < level; i++) {
                    str = str.concat("\t");
                }
                str = str.concat("DFS(" + source.name + ") Exit\n");
                final String str1 = str;
                fd = new FadeTransition(Duration.millis(10), textFlow);
                fd.setOnFinished(e -> {
                    textFlow.appendText(str1);
                });
                fd.onFinishedProperty();
                st.getChildren().add(fd);
            }
        }

        //</editor-fold>
        public List<Node> getShortestPathTo(Node target) {
            List<Node> path = new ArrayList<Node>();
            for (Node i = target; i != null; i = i.previous) {
                path.add(i);
            }
            Collections.reverse(path);
            return path;
        }
    }

    // Mac Carthy
    // CRUD DATAGRAPH TABLE
    // Add a data in the table of datagraph
    public void getAllEdges() {

        // System.out.println("LISTES DES ARCS\n");
        for (Edge e : realEdges) {
            //Add linked nodes 
            addLinkedNodes(e.source.name);
            addLinkedNodes(e.target.name);
        }

        // Ajouter le noeud fin
        if (!allLinkedNodes.contains(endNode.id.getText())) {

            allLinkedNodes.add(endNode.id.getText());

        }

        /* ZONE DE TEST */
        // Create a sample graph 
        System.out.println("allNodes.size() = " + Node.getCompteur());

        g = new All_path(Node.getCompteur());

        for (Edge e : realEdges) {

            //TEST//
            g.addEdge(e.source.getNodeId(), e.target.getNodeId());

            // FIN TEST //
        }

        // System.out.println("\nLISTES DES NOEUDS LIES\n");
        for (int j = 0; j < allLinkedNodes.size(); j++) {

            String str = (String) allLinkedNodes.get(j);
            //System.out.println("Noeud " + str + " \n");

        }

        /// TEST //
        System.out.println("Following are all different paths from " + beginNode.id.getText() + " to " + endNode.id.getText());
        g.printAllPaths(beginNode.node.getNodeId(), endNode.node.getNodeId());

        // Reset
        System.out.println("ZONE COST : ");
        afficherPathCost(tousLesChemins);

        // FIN TEST //
    }

    boolean isBeginNodeLinked() {

        for (Edge e : realEdges) {

            if (e.source.name.equals(beginNode.id.getText())) {
                return true;
            }

        }

        return false;

    }

    boolean isEndNodeLinked() {

        for (Edge e : realEdges) {

            if (e.target.name.equals(endNode.id.getText())) {
                return true;
            }

        }

        return false;

    }

    public void addLinkedNodes(String nodeName) {

        if (!nodeName.equals(endNode.id.getText())) {

            if (allLinkedNodes.isEmpty()) {
                allLinkedNodes.add(beginNode.id.getText());
            } else {

                if (!allLinkedNodes.contains(nodeName)) {
                    allLinkedNodes.add(nodeName);
                }

            }

        }

    }

    public void createArrayOfDataMatrix(int numMatrice) {

        for (int i = 0; i < allLinkedNodes.size(); i++) {

            String valeur = new String().valueOf(allLinkedNodes.get(i));

            /// Zone SOURCE && TARGET
            DataMatrix zs = new DataMatrix("D" + numMatrice, i + 1, 0, valeur);
            DataMatrix zt = new DataMatrix("D" + numMatrice, 0, i + 1, valeur);

            // VERIFIE S'IL EXISTE DANS LA ZONE SOURCE
            if (!checkNodeNameExistInSource(numMatrice, valeur)) {
                allMatrixDataSource.add(zs);
            }

            if (!checkNodeNameExistInTarget(numMatrice, valeur)) {
                allMatrixDataTarget.add(zt);
            }

        }

    }

    public void createArrayOfDataMatrixCrst(int numMatrice) {

        int sl, sc, tl, tc;

        for (Edge e : realEdges) {

            // source ligne & colonne
            sl = getDataMatrixLineSource(numMatrice, e.source.name);
            sc = getDataMatrixColumnSource(numMatrice, e.source.name);

            // target ligne & colonne
            tl = getDataMatrixLineTarget(numMatrice, e.target.name);
            tc = getDataMatrixColumnTarget(numMatrice, e.target.name);

            //** System.out.println("SOURCE : " + e.source.name + " ==> sl= " + sl + " / sc=" + sc + " \n TARGET : " + e.target.name + " ==> tl=" + tl + " / tc=" + tc);
            if (sl != -1 && sc != -1 && tl != -1 && tc != -1) {
                // Position de la valeur
                DataMatrix val = new DataMatrix("D" + numMatrice, sl + tl, sc + tc, new String().valueOf(e.weight));

                allMatrixDataCrst.add(val);
            }
        }

    }

    public int getDataMatrixLineSource(int numMatrice, String str) {

        String matrixName = "D" + numMatrice;
        DataMatrix d;

        for (int y = 0; y < allMatrixDataSource.size(); y++) {
            d = (DataMatrix) allMatrixDataSource.get(y);

            if ((d.getMatrixName().equals(matrixName)) && (d.getValeur().equals(str))) {
                return d.getLigne();
            }

        }
        return -1;

    }

    public int getDataMatrixColumnSource(int numMatrice, String str) {

        String matrixName = "D" + numMatrice;
        DataMatrix d;

        for (int y = 0; y < allMatrixDataSource.size(); y++) {
            d = (DataMatrix) allMatrixDataSource.get(y);

            if ((d.getMatrixName().equals(matrixName)) && (d.getValeur().equals(str))) {
                return d.getColonne();
            }

        }
        return -1;

    }

    public int getDataMatrixLineTarget(int numMatrice, String str) {

        String matrixName = "D" + numMatrice;
        DataMatrix d;

        for (int y = 0; y < allMatrixDataTarget.size(); y++) {
            d = (DataMatrix) allMatrixDataTarget.get(y);

            if ((d.getMatrixName().equals(matrixName)) && (d.getValeur().equals(str))) {
                return d.getLigne();
            }

        }
        return -1;

    }

    public int getDataMatrixColumnTarget(int numMatrice, String str) {

        String matrixName = "D" + numMatrice;
        DataMatrix d;

        for (int y = 0; y < allMatrixDataTarget.size(); y++) {
            d = (DataMatrix) allMatrixDataTarget.get(y);

            if ((d.getMatrixName().equals(matrixName)) && (d.getValeur().equals(str))) {
                return d.getColonne();
            }

        }
        return -1;

    }

    public boolean checkNodeNameExistInSource(int numMatrice, String str) {

        String matrixName = "D" + numMatrice;
        DataMatrix d;

        for (int y = 0; y < allMatrixDataSource.size(); y++) {
            d = (DataMatrix) allMatrixDataSource.get(y);

            if ((d.getMatrixName().equals(matrixName)) && (d.getValeur().equals(str))) {
                return true;
            }

        }
        return false;
    }

    public boolean checkNodeNameExistInTarget(int numMatrice, String str) {

        String matrixName = "D" + numMatrice;
        DataMatrix d;

        for (int y = 0; y < allMatrixDataTarget.size(); y++) {
            d = (DataMatrix) allMatrixDataTarget.get(y);

            if ((d.getMatrixName().equals(matrixName)) && (d.getValeur().equals(str))) {
                return true;
            }

        }
        return false;
    }

    public void getAllLinkedNodes() {

        //** System.out.println("La liste des sommets liés :\n");
        for (int i = 0; i < allLinkedNodes.size(); i++) {
            //** System.out.println("Sommet : " + allLinkedNodes.get(i) );
        }

    }

    //****** VERIFIER NOMBRE REEL *****\\
    @FXML
    public boolean isDoubleNumber(String str) {
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    //****** VERIFIER NOMBRE ENTIER POSITIF *****\\
    @FXML
    public boolean isIntegerNumber(String str) {
        try {
            Integer.parseUnsignedInt(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    // ALERTE ERROR
    public void alertError(String str) {
        Alert alert;
        alert = new Alert(AlertType.ERROR);

        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(str);

        alert.showAndWait();
    }

    public void alertWarning(String str) {
        Alert alert;
        alert = new Alert(AlertType.WARNING);

        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(str);

        alert.showAndWait();
    }

    // Pane management
    @FXML
    public void SteptoStepHandle() {

        if (minOrmax.getSelectionModel().getSelectedItem().toString().equals("Minimisation")) {

            resultLabel.setText("MINIMIZATION");

        } else if (minOrmax.getSelectionModel().getSelectedItem().toString().equals("Maximisation")) {

            resultLabel.setText("MAXIMIZATION");

        }
        // Liste de tous les arcs et récupère tous les noeuds liés
        getAllEdges();

        // Initialise la matrice contenant la source et le destination
        createArrayOfDataMatrix(idMatrix);

        // Enregistre les valeurs croisés
        createArrayOfDataMatrixCrst(idMatrix);

        algorithmPane.toFront();

        // CREATE MATRIX
        createMatrixInView();

    }

    public void createMatrixInView() {

        myScrollPane.setContent(null);

        GridPane matrice = new GridPane();
        String val = "";

        int size = allLinkedNodes.size() + 1;

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                // Create a new TextField in each Iteration
                TextField tf = new TextField();
                tf.setPrefHeight(40);
                tf.setPrefWidth(40);
                tf.setAlignment(Pos.CENTER);
                tf.setEditable(true);

                // Zone SOMMET
                if ((x == 0) || (y == 0)) {

                    if (y == 0) {

                        val = getDataMatrixLineColumnTarget(1, y, x);

                    } else if (x == 0) {

                        val = getDataMatrixLineColumnSource(1, y, x);

                    }

                    tf.setStyle("-fx-border-color: blue;  -fx-font-weight: bold");

                } else {
                    //val =  getDataMatrixLineColumnCrst(idMatrix , y, x);
                    val = getDataMatrixLineColumnCrstView(minOrmax.getSelectionModel().getSelectedItem().toString(), idMatrix, y, x);

                    if (val.equals("+∞") || val.equals("0")) {

                        //tf.setStyle("-fx-border-color: blue;");
                        tf.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

                    }
                }

                tf.setText(val);

                matrice.setRowIndex(tf, y);
                matrice.setColumnIndex(tf, x);
                matrice.getChildren().add(tf);
            }
        }
        matrice.setDisable(true);
        matrice.setAlignment(Pos.BASELINE_CENTER);
        myScrollPane.setContent(matrice);

        // MANAGE THE MATRIX CREATED 
        //manageNextMatrix();
    }

    public String getDataMatrixLineColumnTarget(int numMatrice, int l, int c) {

        String matrixName = "D" + numMatrice;
        DataMatrix d;

        for (int i = 0; i < allMatrixDataTarget.size(); i++) {
            d = (DataMatrix) allMatrixDataTarget.get(i);

            if ((d.getMatrixName().equals(matrixName)) && (d.getLigne() == l) && (d.getColonne() == c)) {
                return d.getValeur();
            }

        }

        return "";

    }

    public String getDataMatrixLineColumnSource(int numMatrice, int l, int c) {

        String matrixName = "D" + numMatrice;
        DataMatrix d;

        for (int i = 0; i < allMatrixDataSource.size(); i++) {
            d = (DataMatrix) allMatrixDataSource.get(i);

            if ((d.getMatrixName().equals(matrixName)) && (d.getLigne() == l) && (d.getColonne() == c)) {
                return d.getValeur();
            }

        }

        return "";

    }

    public String getDataMatrixLineColumnCrst(int numMatrix, int l, int c) {

        DataMatrix d;
        String matrixName = "D" + numMatrix;

        for (int i = 0; i < allMatrixDataCrst.size(); i++) {
            d = (DataMatrix) allMatrixDataCrst.get(i);

            if ((d.getMatrixName().equals(matrixName)) && (d.getLigne() == l) && (d.getColonne() == c)) {
                return d.getValeur();
            }

        }

        return "";

    }

    public String getDataMatrixLineColumnCrstView(String algorithmType, int numMatrix, int l, int c) {

        DataMatrix d;
        String matrixName = "D" + numMatrix;

        for (int i = 0; i < allMatrixDataCrst.size(); i++) {
            d = (DataMatrix) allMatrixDataCrst.get(i);

            if ((d.getMatrixName().equals(matrixName)) && (d.getLigne() == l) && (d.getColonne() == c)) {
                return d.getValeur();
            }

        }

        if (algorithmType.equals("Minimisation")) {

            return "+∞";

        } else if (algorithmType.equals("Maximisation")) {

            return "0";

        }
        return "";

    }

    // LANCEMENT DE L'ALGORITHME
    // ****************** MINIMISATION ************************\\
    // SUIVANT OU PRECEDENT
    @FXML
    public void NextOrPrevious(String str) {

        if (str.equals("next")) {

            valeurDeK++;

        } else if (str.equals("previous")) {

            valeurDeK--;

        }

        valueOfK.setText("k = " + valeurDeK);

    }

    public void managePreviousMatrixHandle(ActionEvent event) {

        if (!cheminOptimal.isEmpty()) {

            // ResetCheminOptimal 
            cheminOptimal.clear();
            cheminMaximal.clear();

        }

        // Décrémente idMatrix
        idMatrix--;

        // Décrémente le nombre de pas 
        pas--;

        System.out.println("PREV");
        System.out.println("Valeur idMatrix ====> " + idMatrix);
        System.out.println("Valeur de pas ==== >" + pas);

        if (idMatrix == 0 || pas < 0) {

            prevStepButton.setDisable(true);

            idMatrix++;
            pas++;

        } else {

            nextStepButton.setDisable(false);

            // Decremente k
            NextOrPrevious("previous");

            // CreateMatrix in the View
            createMatrixInView();

            // Change the matrice ID
            matrixName.setText("Matrice D" + idMatrix);

            // Croisement
            /*
           DataMatrix dt ;
           String str = "D" + idMatrix ;
           
           for(int k = 0; k < allMatrixDataCrst.size(); k++)
            {
                dt = (DataMatrix) allMatrixDataCrst.get(k)  ;

                if(dt.getMatrixName().equals(str)){

                    System.out.println("MatrixName = " + dt.getMatrixName() + " / Ligne => " + dt.getLigne() + " / Colonne => " + dt.getColonne() + " / Valeur => " + dt.getValeur());
                    
                }  

            }
             */
        }

    }

    public void manageNextMatrixHandle(ActionEvent event) {

        // Incrémente le nombre de pas 
        pas++;

        int taille = allMatrixDataSource.size();

        System.out.println("NEXT");
        System.out.println("Valeur de pas ==== >" + pas);

        if (pas > taille - 2) {

            nextStepButton.setDisable(true);

            // Get the chemin optimal
            int lastMatrixId = allMatrixDataSource.size() - 1;
            int lastIdColumn = allMatrixDataSource.size();

            if (minOrmax.getSelectionModel().getSelectedItem().toString().equals("Minimisation")) {

                //getCheminOptimal(lastMatrixId, lastIdColumn);
                drawAllOptimalPath();

            } else if (minOrmax.getSelectionModel().getSelectedItem().toString().equals("Maximisation")) {

                //fordAlgorithmMax();
                drawAllMaximalPath();

            }

            pas--;

        } else {

            prevStepButton.setDisable(false);

            //ArrayList allData = new ArrayList();
            ArrayList entree = new ArrayList();
            ArrayList sortie = new ArrayList();
            InputOuputNode ION;

            String val;

            //** System.out.println("SOURCE LIST");
            DataMatrix ds, dt, tmp;

            if ((pas > 0) && (pas <= taille - 2)) {

                ds = (DataMatrix) allMatrixDataSource.get(pas);
                dt = (DataMatrix) allMatrixDataTarget.get(pas);

                sortie = outputNode(ds);
                entree = inputNode(dt);

                ION = new InputOuputNode(ds, dt, entree, sortie);

                //allData.add(ION);
                createNextMatrix(ION);

            }

            // Increment k
            NextOrPrevious("next");

            // CreateMatrix in the View
            createMatrixInView();

            // Change the matrice ID
            matrixName.setText("Matrice D" + idMatrix);

        }

    }

    public boolean checkIfNodesIsSource(NodeFX noeud) {

        //if(!noeud.equals(beginNode) && !noeud.equals(endNode)){
        for (Edge e : realEdges) {

            if (e.source.name.equals(noeud.id.getText())) {

                return true;

            }

        }

        //}
        return false;

    }

    public boolean checkIfNodesIsTarget(NodeFX noeud) {

        //if(!noeud.equals(beginNode) && !noeud.equals(endNode)){
        for (Edge e : realEdges) {

            if (e.target.name.equals(noeud.id.getText())) {

                return true;

            }

        }

        //}
        return false;

    }

    public boolean isAllNodeOK() {

        System.out.println("Zone DE TEST");
        boolean OK = true;

        System.out.println("beginNode ==> " + beginNode.id.getText());
        System.out.println("endNode ==> " + endNode.id.getText());

        for (NodeFX noeud : circles) {

            if (!noeud.id.getText().equals(beginNode.id.getText()) && !noeud.id.getText().equals(endNode.id.getText())) {

                System.out.println("Noeud ==> " + noeud.id.getText());

                if (!checkIfNodesIsTarget(noeud) || !checkIfNodesIsSource(noeud)) {

                    OK = false;

                }

            }

        }

        return OK;

    }

    public void checkIfPathIsOK(ActionEvent event) {

        if (!isBeginNodeLinked()) {

            alertError("Attention ! Le noeud " + beginNode.id.getText() + " n'est pas inclus dans le chemin !");

        } else if (!isEndNodeLinked()) {

            alertError("Attention ! Le noeud " + endNode.id.getText() + " n'est pas inclus dans le chemin !");

        } else if (isEndNodeLinked() && isBeginNodeLinked()) {

            System.out.println("\n isAllNodeOK() = " + isAllNodeOK());

            if (isAllNodeOK()) {

                if (isSelectedMinOrMax()) {

                    if (event.getSource() == imediatelyButton) {

                        manageNextMatrixHandleDirectly();

                    } else if (event.getSource() == stepToStepButton) {

                        SteptoStepHandle();

                    } else if (event.getSource() == imediatelyButtonRac) {
                        fctSpecific();
                        manageNextMatrixHandleDirectly();
                    }

                    // DISABLE GRAPH EDITOR
                    canvasGroup.setDisable(true);
                    imediatelyButton.setDisable(true);
                    addNodeButton.setDisable(true);
                    addEdgeButton.setDisable(true);

                    //viewer.setDisable(true);
                }

            } else {
                alertError("Chemin crée invalide !");
            }

        }

        //test();
    }

    // Check combobox min and max selected
    public boolean isSelectedMinOrMax() {
        if (minOrmax.getSelectionModel().isEmpty()) {

            alertWarning("Veuillez sélectionnez le type de recherche !");

            return false;
        } else {

            return true;
        }
    }

    public void manageNextMatrixHandleDirectly() {

        // Liste de tous les arcs et récupère tous les noeuds liés
        getAllEdges();

        // Initialise la matrice contenant la source et le destination
        createArrayOfDataMatrix(idMatrix);

        // Enregistre les valeurs croisés
        createArrayOfDataMatrixCrst(idMatrix);

        // Incrémente le nombre de pas 
        pas++;

        int taille = allMatrixDataSource.size();

        ArrayList entree = new ArrayList();
        ArrayList sortie = new ArrayList();
        InputOuputNode ION;

        // System.out.println("NEXT");
        // System.out.println("Valeur de pas ==== >" + pas);
        //else
        System.out.println("pas = " + pas);

        System.out.println("taille = " + taille);

        while (pas <= taille - 2) {

            System.out.println("pas = " + pas);
            //prevStepButton.setDisable(false); 

            //ArrayList allData = new ArrayList();
            String val;

            //** System.out.println("SOURCE LIST");
            DataMatrix ds, dt, tmp;

            if ((pas > 0) && (pas <= taille - 2)) {

                ds = (DataMatrix) allMatrixDataSource.get(pas);
                dt = (DataMatrix) allMatrixDataTarget.get(pas);

                sortie = outputNode(ds);
                entree = inputNode(dt);

                ION = new InputOuputNode(ds, dt, entree, sortie);

                //allData.add(ION);
                createNextMatrix(ION);

            }

            // Incrémente le nombre de pas 
            pas++;

            // Increment k
            //NextOrPrevious("next");
            // CreateMatrix in the View
            //createMatrixInView();
            // Change the matrice ID
            //matrixName.setText("Matrice D" + idMatrix);
        }

        if (pas > taille - 2) {

            nextStepButton.setDisable(true);

            // Get the chemin optimal
            int lastMatrixId = allMatrixDataSource.size() - 1;
            int lastIdColumn = allMatrixDataSource.size();

            if (minOrmax.getSelectionModel().getSelectedItem().toString().equals("Minimisation")) {

                //getCheminOptimal(lastMatrixId, lastIdColumn);
                drawAllOptimalPath();

            } else if (minOrmax.getSelectionModel().getSelectedItem().toString().equals("Maximisation")) {

                //getCheminMaximal(lastMatrixId, lastIdColumn);
                //fordAlgorithmMax();
                drawAllMaximalPath();

            }

            //pas--;
        }

    }

    public ArrayList outputNode(DataMatrix source) {

        ArrayList output = new ArrayList();
        DataMatrix target;
        String val;

        for (int j = 1; j < allMatrixDataTarget.size(); j++) {

            target = (DataMatrix) allMatrixDataTarget.get(j);

            if (!source.getValeur().equals(target.getValeur())) {

                val = getDataMatrixLineColumnCrst(idMatrix, source.getLigne() + target.getLigne(), source.getColonne() + target.getColonne());

                if (!val.equals("")) {
                    output.add(target);
                }
            }
        }
        return output;
    }

    public ArrayList inputNode(DataMatrix target) {

        ArrayList input = new ArrayList();
        DataMatrix source;
        String val;

        for (int j = 0; j < allMatrixDataSource.size(); j++) {

            source = (DataMatrix) allMatrixDataSource.get(j);

            if (!target.getValeur().equals(source.getValeur())) {

                val = getDataMatrixLineColumnCrst(idMatrix, source.getLigne() + target.getLigne(), source.getColonne() + target.getColonne());

                if (!val.equals("")) {
                    //DataMatrix tmp = new DataMatrix("tmp",source.getLigne() + target.getLigne(),source.getColonne() + target.getColonne(),val) ;
                    input.add(source);
                }

            }
        }

        return input;
    }

    public void createNextMatrix(InputOuputNode ion) {

        double w, v, minVal, maxVal;

        String val, valIn, valOut, matrixName;
        DataMatrix dm;

        // CREATE THE NEXT MATRIX
        System.out.println("CREATE THE NEXT MATRIX\n");

        idMatrix++;

        System.out.println("ID_MATRIX : " + idMatrix);

        if (!existMatrixIdInCrst(idMatrix)) {

            for (int j = 0; j < ion.getInput().size(); j++) {

                if (minOrmax.getSelectionModel().getSelectedItem().toString().equals("Minimisation")) {

                    DataMatrix src = (DataMatrix) ion.getInput().get(j);

                    // Get the value between the two nodes
                    valIn = getDataMatrixLineColumnCrstView("Minimisation", idMatrix - 1, src.getLigne() + ion.getTarget().getLigne(), src.getColonne() + ion.getTarget().getColonne());

                    //System.out.println("Source : " + src.getValeur() + " / Target : " + ion.getTarget().getValeur() + " ==> valeurIn : " + valIn);
                    if (!valIn.equals("+∞")) {

                        // Parcours des SORTIES
                        for (int k = 0; k < ion.getOutput().size(); k++) {

                            DataMatrix tgt = (DataMatrix) ion.getOutput().get(k);
                            // Get the value between the two nodes
                            valOut = getDataMatrixLineColumnCrstView("Minimisation", idMatrix - 1, ion.getSource().getLigne() + tgt.getLigne(), ion.getSource().getColonne() + tgt.getColonne());
                            //getDataMatrixLineColumnCrstView(String algorithmType, int numMatrix, int l , int c)
                            //System.out.println("Source : " + ion.getSource().getValeur() + " / Target : " + tgt.getValeur() + " ==> valeurOut : " + valOut );

                            if (!valOut.equals("+∞")) {

                                //DataMatrix dm = new DataMatrix(,int ligne, int colonne, String valeur)
                                w = Double.parseDouble(valIn) + Double.parseDouble(valOut);

                                // GET THE DATA OF THE PREVIOUS MATRIX
                                val = getDataMatrixLineColumnCrstView("Minimisation", idMatrix - 1, src.getLigne() + tgt.getLigne(), src.getColonne() + tgt.getColonne());

                                matrixName = "D" + idMatrix;

                                if (val.equals("+∞")) {

                                    dm = new DataMatrix(matrixName, src.getLigne() + tgt.getLigne(), src.getColonne() + tgt.getColonne(), new String().valueOf(w));

                                } else {

                                    v = Double.parseDouble(val);

                                    // CHOOSE THE MINIMUM VALUE
                                    minVal = minimumValue(w, v);

                                    dm = new DataMatrix(matrixName, src.getLigne() + tgt.getLigne(), src.getColonne() + tgt.getColonne(), new String().valueOf(minVal));

                                }
                                // System.out.println("MATRICE D" + idMatrix);
                                // System.out.println("Valeur : " + dm.getValeur() + " / Ligne : " + dm.getLigne() + " / Colonne : " + dm.getColonne());

                                //System.out.println("Source : " + ion.getSource().getValeur() + " / Target : " + tgt.getValeur() + " ==> valeurOut : " + valOut );
                                allMatrixDataCrst.add(dm);

                            } else {
                                System.out.println("valOut == +∞");
                            }

                        }
                    } else {
                        System.out.println("valIn == +∞");
                    }

                }

                if (minOrmax.getSelectionModel().getSelectedItem().toString().equals("Maximisation")) {

                    DataMatrix src = (DataMatrix) ion.getInput().get(j);

                    // Get the value between the two nodes
                    valIn = getDataMatrixLineColumnCrstView("Maximisation", idMatrix - 1, src.getLigne() + ion.getTarget().getLigne(), src.getColonne() + ion.getTarget().getColonne());

                    //System.out.println("Source : " + src.getValeur() + " / Target : " + ion.getTarget().getValeur() + " ==> valeurIn : " + valIn);
                    if (!valIn.equals("0")) {

                        // Parcours des SORTIES
                        for (int k = 0; k < ion.getOutput().size(); k++) {

                            DataMatrix tgt = (DataMatrix) ion.getOutput().get(k);
                            // Get the value between the two nodes
                            valOut = getDataMatrixLineColumnCrstView("Maximisation", idMatrix - 1, ion.getSource().getLigne() + tgt.getLigne(), ion.getSource().getColonne() + tgt.getColonne());
                            //getDataMatrixLineColumnCrstView(String algorithmType, int numMatrix, int l , int c)
                            //System.out.println("Source : " + ion.getSource().getValeur() + " / Target : " + tgt.getValeur() + " ==> valeurOut : " + valOut );

                            if (!valOut.equals("0")) {

                                //DataMatrix dm = new DataMatrix(,int ligne, int colonne, String valeur)
                                w = Double.parseDouble(valIn) + Double.parseDouble(valOut);

                                // GET THE DATA OF THE PREVIOUS MATRIX
                                val = getDataMatrixLineColumnCrstView("Maximisation", idMatrix - 1, src.getLigne() + tgt.getLigne(), src.getColonne() + tgt.getColonne());

                                matrixName = "D" + idMatrix;

                                if (val.equals("0")) {
                                    //maxVal = maximumValue(w,v);
                                    maxVal = maximumValue(w, Double.parseDouble(val));
                                    dm = new DataMatrix(matrixName, src.getLigne() + tgt.getLigne(), src.getColonne() + tgt.getColonne(), new String().valueOf(maxVal));

                                } else {

                                    v = Double.parseDouble(val);

                                    // CHOOSE THE MINIMUM VALUE
                                    maxVal = maximumValue(w, v);

                                    dm = new DataMatrix(matrixName, src.getLigne() + tgt.getLigne(), src.getColonne() + tgt.getColonne(), new String().valueOf(maxVal));

                                }
                                // System.out.println("MATRICE D" + idMatrix);
                                // System.out.println("Valeur : " + dm.getValeur() + " / Ligne : " + dm.getLigne() + " / Colonne : " + dm.getColonne());

                                //System.out.println("Source : " + ion.getSource().getValeur() + " / Target : " + tgt.getValeur() + " ==> valeurOut : " + valOut );
                                allMatrixDataCrst.add(dm);

                            } else {
                                System.out.println("valOut == 0");
                            }

                        }
                    } else {
                        System.out.println("valIn == 0");
                    }

                }

                System.out.println("\n");

            }

            // NEXT MATRIX DATA
            dataForNextMatrix(idMatrix);

        }

    }

    // CHECK THE MINIMUM VALUE
    double minimumValue(double a, double b) {

        if (a <= b) {
            return a;
        } else {
            return b;
        }

    }

    // CHECK THE MINIMUM VALUE
    double maximumValue(double a, double b) {

        if (a >= b) {
            return a;
        } else {
            return b;
        }

    }

    public void dataForNextMatrix(int numMatrice) {

        // Create source
        // Next Matrix 
        String nextMatrixName = "D" + numMatrice;
        DataMatrix olds, news;

        // Previous Matrix
        int prevMatrixId = numMatrice - 1;
        String prevMatrixName = "D" + prevMatrixId;

        // Croisement
        for (int k = 0; k < allMatrixDataCrst.size(); k++) {
            olds = (DataMatrix) allMatrixDataCrst.get(k);

            if (olds.getMatrixName().equals(prevMatrixName)) {

                if (!existInNextMatrix(numMatrice, olds.getLigne(), olds.getColonne())) {

                    news = new DataMatrix(nextMatrixName, olds.getLigne(), olds.getColonne(), olds.getValeur());
                    // public DataMatrix(String matrixName,int ligne, int colonne, String valeur) {
                    //news.setMatrixName(nextMatrixName);

                    allMatrixDataCrst.add(news);
                }

            }

        }
    }

    public boolean existInNextMatrix(int numMatrice, int l, int c) {

        DataMatrix tmp;
        String matrixName = "D" + numMatrice;

        // Croisement
        for (int y = 0; y < allMatrixDataCrst.size(); y++) {
            tmp = (DataMatrix) allMatrixDataCrst.get(y);

            if ((tmp.getMatrixName().equals(matrixName)) && (tmp.getLigne() == l) && (tmp.getColonne() == c)) {

                return true;

            }
        }

        return false;

    }

    public boolean existMatrixIdInCrst(int numMatrice) {

        DataMatrix tmp;
        String matrixName = "D" + numMatrice;

        // Croisement
        for (int y = 0; y < allMatrixDataCrst.size(); y++) {
            tmp = (DataMatrix) allMatrixDataCrst.get(y);

            if (tmp.getMatrixName().equals(matrixName)) {

                return true;

            }
        }

        return false;

    }

    /**
     *
     * @param id
     */
    public void getAllMinData(int id) {

        double min = 0, val;
        DataMatrix tmp, minDt = null;

        for (int i = 0; i < allMatrixDataCrst.size(); i++) {

            tmp = (DataMatrix) allMatrixDataCrst.get(i);

            if (tmp.getMatrixName().equals(matrixName) && tmp.getColonne() == id) {

                if (!tmp.getValeur().equals("")) {

                    if (min == 0) {

                        min = Double.parseDouble(tmp.getValeur());
                        //minDt = tmp;

                        // TEST
                        //allMinDt.add(tmp);
                    } else {

                        val = Double.parseDouble(tmp.getValeur());

                        if (min >= val) {
                            min = val;
                            //minDt = tmp;
                        }
                    }

                }

            }
        }

        // Get all noeuds associé à la valeur la plus petite 
        for (int j = 0; j < allMatrixDataCrst.size(); j++) {

            tmp = (DataMatrix) allMatrixDataCrst.get(j);

            if (tmp.getMatrixName().equals(matrixName) && tmp.getColonne() == id) {

                if (!tmp.getValeur().equals("")) {

                    if (min == Double.parseDouble(tmp.getValeur())) {

                        allMinDt.add(tmp);

                    }

                }

            }
        }
    }

    public DataMatrix getPreviousNode(DataMatrix dm) {

        DataMatrix src, tgt;
        int l, c;

        for (int i = 0; i < allMatrixDataSource.size(); i++) {

            src = (DataMatrix) allMatrixDataSource.get(i);

            for (int j = 0; j < allMatrixDataTarget.size(); j++) {

                tgt = (DataMatrix) allMatrixDataTarget.get(j);

                if (!src.getValeur().equals(tgt.getValeur())) {

                    l = src.getLigne() + tgt.getLigne();
                    c = src.getColonne() + tgt.getColonne();

                    if ((dm.getLigne() == l) && (dm.getColonne() == c)) {

                        return src;

                    }
                }

            }

        }

        return null;

    }

    void colorerNoeud(String nodeName) {

        String str;

        for (Object e : canvasGroup.getChildren()) {

            str = e.getClass().toString();

            if (str.equals("class fxsimulator.controllers.CanvasController$NodeFX")) {

                NodeFX nd = (NodeFX) e;

                if (nd.id.getText().equals(nodeName)) {

                    nd.setStyle("-fx-fill:  red");
                    nd.id.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

                }

            }

        }
    }

    NodeFX getNoeudFromName(String name) {

        for (NodeFX nfx : circles) {

            if (nfx.id.getText().equals(name)) {
                return nfx;
            }

        }

        return null;
    }

    void colorerArc(NodeFX source, NodeFX target) {

        String str;
        for (Object e : canvasGroup.getChildren()) {

            str = e.getClass().toString();

            if (str.equals("class fxsimulator.classes.Arrow")) {

                Arrow arc = (Arrow) e;

                if ((arc.getStartX() == source.point.x + 8) && (arc.getStartY() == source.point.y - 8) && (arc.getEndX() == target.point.x + 8) && (arc.getEndY() == target.point.y - 8)) {

                    arc.setStyle("-fx-fill:  red");

                    return;

                } else if ((arc.getStartX() == source.point.x - 8) && (arc.getStartY() == source.point.y + 8) && (arc.getEndX() == target.point.x - 8) && (arc.getEndY() == target.point.y + 8)) {

                    arc.setStyle("-fx-fill:  red");

                    return;
                }
            }

        }
    }

    /*
    void colorerDistance(NodeFX source, NodeFX target) {

        String str;
        for (Object e : canvasGroup.getChildren()) {

            str = e.getClass().toString();

            if (str.equals("class javafx.scene.control.Label")) {

                Label lb = (Label) e;

                if (
                        ((lb.getLayoutX() == (((source.point.x + 8) + (target.point.x + 8)) / 2 )) && (lb.getLayoutY() == (((source.point.y - 8) + (target.point.y - 8)) / 2)))
                                                                                            ||
                        ((lb.getLayoutX() == (((source.point.x - 8) + (target.point.x - 8)) / 2)) && (lb.getLayoutY() == (((source.point.y + 8) + (target.point.y + 8)) / 2 )))
                    ) {

                    lb.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    
                    System.out.println("Source : " + source.id.getText());
                    System.out.println("Target : " + target.id.getText());
                    System.out.println("Label => " + lb.getText());

                }

            }

        }

    }
     */
    void colorerDistance(NodeFX source, NodeFX target, double weight) {
        
        int taille = (int) weight ;
        
        String str;
        for (Object e : canvasGroup.getChildren()) {

            str = e.getClass().toString();

            if (str.equals("class javafx.scene.control.Label")) {

                Label lb = (Label) e;
                
                if ((lb.getLayoutX() == (((source.point.x + 8) + (target.point.x + 8)) / 2)) && (lb.getLayoutY() == (((source.point.y - 8) + (target.point.y - 8)) / 2)) 
                       && lb.getText().equals(new String().valueOf(taille)) ) {

                        
                    lb.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    System.out.println("lb.getText() = " + lb.getText());
                   
                    
                    //lb.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

                    System.out.println("Source : " + source.id.getText());
                    System.out.println("Target : " + target.id.getText());
                    System.out.println("Label => " + lb.getText());

                    //return;

                } else if ((lb.getLayoutX() == (((source.point.x - 8) + (target.point.x - 8)) / 2)) && (lb.getLayoutY() == (((source.point.y + 8) + (target.point.y + 8)) / 2))
                         && lb.getText().equals(new String().valueOf(taille))) {

                    // && (lb.getText().equals(new String().valueOf(weight)))
                    
                    System.out.println("lb.getText() = " + lb.getText());
                    lb.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    

                    //return;

                }

            }

        }

    }

    void removeArrow(NodeFX source, NodeFX target) {

        String str;
        for (Object e : canvasGroup.getChildren()) {

            str = e.getClass().toString();

            if (str.equals("class fxsimulator.classes.Arrow")) {

                Arrow arc = (Arrow) e;

                if ((arc.getStartX() == source.point.x + 8) && (arc.getStartY() == source.point.y - 8) && (arc.getEndX() == target.point.x + 8) && (arc.getEndY() == target.point.y - 8)) {

                    canvasGroup.getChildren().remove(arc);
                    break;

                }
                else if ((arc.getStartX() == source.point.x - 8) && (arc.getStartY() == source.point.y + 8) && (arc.getEndX() == target.point.x - 8) && (arc.getEndY() == target.point.y + 8)) {

                    canvasGroup.getChildren().remove(arc);
                    break;

                }
            }

        }
    }

    // CLOSE THE APP
    public void closeHandle(ActionEvent aev) {
        System.exit(0);
    }

    // ****** MODE SMART ***** //
    // GET ALL EDGES COST
    public void afficherPathCost(ArrayList allPaths) {

        ArrayList onePath;

        for (int j = 0; j < allPaths.size(); j++) {

            onePath = (ArrayList) allPaths.get(j);

            // TEST
            // Chemin & coût
            Path p = new Path(onePath, pathCost(onePath));
            all_paths_and_cost.add(p);
        }

        // GET ALL PATH
        getAllExistedPath();

    }

    public double pathCost(ArrayList aPath) {

        double cost = 0;
        int s, d;

        for (int i = 0; i < aPath.size() - 1; i++) {

            s = (int) aPath.get(i);

            for (Edge e : realEdges) {

                d = (int) aPath.get(i + 1);

                // getNodeNameFormNodeId(
                if (e.source.name.equals(getNodeNameFormNodeId(s)) && e.target.name.equals(getNodeNameFormNodeId(d))) {

                    cost = cost + e.weight;

                }
            }

        }

        return cost;

    }

    public void getAllExistedPath() {

        System.out.println("LISTES DE TOUS LES CHEMINS DISPONIBLES AINSI QUE LEURS COUTS :");

        Path pt = null;

        for (int g = 0; g < all_paths_and_cost.size(); g++) {

            pt = (Path) all_paths_and_cost.get(g);

            System.out.println("Chemin : " + pt.getPath() + " / Coût : " + pt.getCost());

        }

        // MINIMUM PATH COST
        System.out.println("MINIMUM PATH COST : " + getMinimumPathCost());

        showAllOptimalPath();

        // MAXIMUM PATH COST
        System.out.println("MAXIMUM PATH COST : " + getMaximumPathCost());

        showAllMaximalPath();

    }

    public void showAllOptimalPath() {

        System.out.println("LISTES DE TOUS LES CHEMINS OPTIMAL :");

        all_shortest_path = getAllOptimalPath(getMinimumPathCost());

        Path pt = null;

        for (int g = 0; g < all_shortest_path.size(); g++) {

            pt = (Path) all_shortest_path.get(g);

            System.out.println("Chemin : " + pt.getPath() + " / Coût : " + pt.getCost());

        }

    }

    public void showAllMaximalPath() {

        System.out.println("LISTES DE TOUS LES CHEMINS MAXIMAL :");

        all_longest_path = getAllMaximalPath(getMaximumPathCost());

        Path pt = null;

        for (int g = 0; g < all_longest_path.size(); g++) {

            pt = (Path) all_longest_path.get(g);

            System.out.println("Chemin : " + pt.getPath() + " / Coût : " + pt.getCost());

        }

    }

    // Get the minimum value of all path cost
    public double getMinimumPathCost() {

        Path pt = null;

        double minCost = 0;

        for (int g = 0; g < all_paths_and_cost.size(); g++) {

            pt = (Path) all_paths_and_cost.get(g);

            if (minCost == 0) {

                minCost = pt.getCost();

            } else if (minCost >= pt.getCost()) {

                minCost = pt.getCost();

            }

        }

        return minCost;

    }

    // Get the maximum value of all path cost
    public double getMaximumPathCost() {

        Path pt = null;

        double maxCost = 0;

        for (int g = 0; g < all_paths_and_cost.size(); g++) {

            pt = (Path) all_paths_and_cost.get(g);

            if (maxCost == 0) {

                maxCost = pt.getCost();

            } else if (maxCost <= pt.getCost()) {

                maxCost = pt.getCost();

            }

        }

        return maxCost;

    }

    // Get all paths associated to maximum path cost
    public ArrayList getAllMaximalPath(double maxCost) {

        Path pt = null;
        ArrayList all_maximal_path = new ArrayList();

        for (int g = 0; g < all_paths_and_cost.size(); g++) {

            pt = (Path) all_paths_and_cost.get(g);

            if (pt.getCost() == maxCost) {

                all_maximal_path.add(pt);

            }

        }

        return all_maximal_path;

    }

    // Get all paths associated to minimum path cost
    public ArrayList getAllOptimalPath(double minCost) {

        Path pt = null;
        ArrayList all_optimal_path = new ArrayList();

        for (int g = 0; g < all_paths_and_cost.size(); g++) {

            pt = (Path) all_paths_and_cost.get(g);

            if (pt.getCost() == minCost) {

                all_optimal_path.add(pt);

            }

        }

        return all_optimal_path;

    }

    // DRAW ALL OPTIMAL
    public void drawAllOptimalPath() {

        Path pt = null;

        int prev, next;

        // Voir tous les chemins optimal
        for (int r = 0; r < all_shortest_path.size(); r++) {

            pt = (Path) all_shortest_path.get(r);

            // Colorer le chemin du path optimal
            for (int e = 0; e < pt.getPath().size() - 1; e++) {

                prev = (int) pt.getPath().get(e);
                next = (int) pt.getPath().get(e + 1);

                // COLORIAGE
                //colorerNoeud(new String().valueOf(prev));
                colorerNoeud(getNodeNameFormNodeId(prev));

                //colorerNoeud(new String().valueOf(next));
                colorerNoeud(getNodeNameFormNodeId(next));

                //colorerArc(getNoeudFromName(new String().valueOf(prev)), getNoeudFromName(new String().valueOf(next)));
                colorerArc(getNoeudFromName(getNodeNameFormNodeId(prev)), getNoeudFromName(getNodeNameFormNodeId(next)));

                //colorerDistance(getNoeudFromName(new String().valueOf(prev)), getNoeudFromName(new String().valueOf(next)));
                colorerDistance(getNoeudFromName(getNodeNameFormNodeId(prev)), getNoeudFromName(getNodeNameFormNodeId(next)),
                        getEdgeWeight(prev, next)
                        );

            }

        }

        // INFORMATION DETAILS
        int cost = (int) getMinimumPathCost();
        informationDetails("OPTIMAL",
                "The optimal path cost is " + cost + ".",
                "There are " + all_shortest_path.size() + " optimal path(s)");

    }

    public void drawAllMaximalPath() {

        Path pt = null;

        int prev, next;

        // Voir tous les chemins optimal
        for (int r = 0; r < all_longest_path.size(); r++) {

            pt = (Path) all_longest_path.get(r);

            // Colorer le chemin du path optimal
            for (int e = 0; e < pt.getPath().size() - 1; e++) {

                prev = (int) pt.getPath().get(e);
                next = (int) pt.getPath().get(e + 1);

                // COLORIAGE
                //colorerNoeud(new String().valueOf(prev));
                colorerNoeud(getNodeNameFormNodeId(prev));

                //colorerNoeud(new String().valueOf(next));
                colorerNoeud(getNodeNameFormNodeId(next));

                //colorerArc(getNoeudFromName(new String().valueOf(prev)), getNoeudFromName(new String().valueOf(next)));
                colorerArc(getNoeudFromName(getNodeNameFormNodeId(prev)), getNoeudFromName(getNodeNameFormNodeId(next)));

                //colorerDistance(getNoeudFromName(new String().valueOf(prev)), getNoeudFromName(new String().valueOf(next)));
                // colorerDistance(getNoeudFromName(getNodeNameFormNodeId(prev)), getNoeudFromName(getNodeNameFormNodeId(next)));
                
                colorerDistance(getNoeudFromName(getNodeNameFormNodeId(prev)), getNoeudFromName(getNodeNameFormNodeId(next)),
                        getEdgeWeight(prev, next)
                        );

            }

        }

        // INFORMATION DETAILS
        int cost = (int) getMaximumPathCost();
        informationDetails("MAXIMAL",
                "The maximum path cost is " + cost + ".",
                "There are " + all_longest_path.size() + " maximal path(s).");

    }

    public void informationDetails(String typeLabel, String pathCostLabel, String pathCountLabel) {

        // FINALISATION
        resultPane.toFront();

        pathCost.setText(pathCostLabel);
        pathCount.setText(pathCountLabel);

        resultLabel.setText("ALL " + typeLabel + " PATH");

        informationDetails.setText(typeLabel + " path between \"" + beginNode.id.getText() + "\" and \"" + endNode.id.getText() + "\"");

    }

    public String getNodeNameFormNodeId(int nodeId) {

        for (Edge e : realEdges) {

            if (e.source.getNodeId() == nodeId) {

                return e.source.name;

            } else if (e.target.getNodeId() == nodeId) {

                return e.target.name;

            }

        }

        return "";

    }
    
    public double getEdgeWeight(int nodeStartId, int nodeEndId) {

        
        for (Edge e : realEdges) {

            if ( (e.source.getNodeId() == nodeStartId) && (e.target.getNodeId() == nodeEndId) ) {

                return e.weight ;

            } 

        }

        return 0 ;

    }

    public void getAllNodes() {

        System.out.println("TOUS LES SOMMETS :");

        NodeFX noeud;

        for (int i = 0; i < circles.size(); i++) {

            noeud = (NodeFX) circles.get(i);
            System.out.println(i + "- " + noeud.id);

        }

    }

}
