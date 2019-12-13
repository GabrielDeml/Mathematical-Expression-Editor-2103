import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ExpressionEditor extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Currently focused Expression
     */
    //TODO this line
    private Expression focusExpression;
    /**
     * Orig focus Expression
     */
    private Expression origExpression;

    /**
     * Mouse event handler for the entire pane that constitutes the ExpressionEditor
     */
    private static class MouseEventHandler implements EventHandler<MouseEvent> {

        /**
         * rootExpression
         */
        CompoundExpression rootExpression;

        /**
         * pane
         */
        Pane pane;

        MouseEventHandler(Pane pane_, CompoundExpression rootExpression_) {
            this.rootExpression = rootExpression_;
            this.pane = pane_;
        }

        /**
         * Finds the clicked expression
         *
         * @param X location of the click
         * @param Y location of the click
         * @return The expression that was clicked or null if nothing was clicked
         */
        private Expression findClickedExpression(double X, double Y) {
            CompoundExpressionAb currentFocusCompound = (CompoundExpressionAb) rootExpression;
            for (Expression expression : currentFocusCompound.getChildren()) {
                // Get the width and height of the hbox
                double tmpWidth = expression.getNode().getLayoutBounds().getWidth();
                double tmpHeight = expression.getNode().getLayoutBounds().getHeight();
                Point2D tmpSceneLocation = expression.getNode().localToScene(expression.getNode().getBoundsInLocal().getMinX(), expression.getNode().getBoundsInLocal().getMinY());
                // Check if we are in those bounds
                if (tmpSceneLocation.getX() < X && X < (tmpSceneLocation.getX() + tmpWidth) && tmpSceneLocation.getY() < Y && Y < (tmpSceneLocation.getY() + tmpHeight)) {
                    System.out.println("We found it");
                    return expression;
                }
            }
            return null;
        }
        private void setBoarder(Expression expToFind){
            CompoundExpressionAb currentFocusCompound = (CompoundExpressionAb) rootExpression;
            for(Expression expression : currentFocusCompound.getChildren()){
                if(expression == expToFind){
                    expression.getNode().setStyle("-fx-border-style: solid inside;"
                            + "-fx-border-width: 1;" + "-fx-border-insets: 2;"
                            + "-fx-border-color: red;");
                }else expression.getNode().setStyle(null);
            }
        }


        double mouseLocationOnClickX, mouseLocationOnClickY;
        double orgTranslateX, orgTranslateY;

        public void handle(MouseEvent event) {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                mouseLocationOnClickX = event.getSceneX();
                mouseLocationOnClickY = event.getSceneY();
                //TODO make this expression update line 24
                Expression tmpExpression = findClickedExpression(mouseLocationOnClickX, mouseLocationOnClickY);

                if (tmpExpression != null) {
                    setBoarder(tmpExpression);
//                    rootExpression = tmpExpression
                    orgTranslateX = ((Pane) (event.getSource())).getTranslateX();
                    orgTranslateY = ((Pane) (event.getSource())).getTranslateY();
                }
            } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                mouseLocationOnClickX = event.getSceneX();
                mouseLocationOnClickY = event.getSceneY();
                Expression tmpExpression = findClickedExpression(mouseLocationOnClickX, mouseLocationOnClickY);
//                System.out.println("We are here");
//                double offsetX = event.getSceneX() - mouseLocationOnClickX;
//                double offsetY = event.getSceneY() - mouseLocationOnClickY;
//                double newTranslateX = orgTranslateX + offsetX;
//                double newTranslateY = orgTranslateY + offsetY;

//                ((Pane) (event.getSource())).setTranslateX(newTranslateX);
//                ((Pane) (event.getSource())).setTranslateY(newTranslateY);
            } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
                //TODO implement me
            }
        }
    }

    /**
     * Size of the GUI
     */
    private static final int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 250;

    /**
     * Initial expression shown in the textbox
     */
    private static final String EXAMPLE_EXPRESSION = "2*x+3*y+4*z+(7+6*z)";

    /**
     * Parser used for parsing expressions.
     */
    private final ExpressionParser expressionParser = new SimpleExpressionParser();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Expression Editor");

        // Add the textbox and Parser button
        final Pane queryPane = new HBox();
        final TextField textField = new TextField(EXAMPLE_EXPRESSION);
        final Button button = new Button("Parse");
        queryPane.getChildren().add(textField);

        final Pane expressionPane = new Pane();

        // Add the callback to handle when the Parse button is pressed
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                // Try to parse the expression
                try {
                    // Success! Add the expression's Node to the expressionPane
                    final Expression expression = expressionParser.parse(textField.getText(), true);
                    System.out.println(expression.convertToString(0));
                    expressionPane.getChildren().clear();
                    expressionPane.getChildren().add(expression.getNode());
                    expression.getNode().setLayoutX(WINDOW_WIDTH / 4);
                    expression.getNode().setLayoutY(WINDOW_HEIGHT / 2);

                    // If the parsed expression is a CompoundExpression, then register some callbacks
                    if (expression instanceof CompoundExpression) {
                        ((Pane) expression.getNode()).setBorder(Expression.NO_BORDER);
                        final MouseEventHandler eventHandler = new MouseEventHandler(expressionPane, (CompoundExpression) expression);
                        expressionPane.setOnMousePressed(eventHandler);
                        expressionPane.setOnMouseDragged(eventHandler);
                        expressionPane.setOnMouseReleased(eventHandler);
                    }
                } catch (ExpressionParseException epe) {
                    // If we can't parse the expression, then mark it in red
                    textField.setStyle("-fx-text-fill: red");
                }
            }
        });
        queryPane.getChildren().add(button);

        // Reset the color to black whenever the user presses a key
        textField.setOnKeyPressed(e -> textField.setStyle("-fx-text-fill: black"));

        final BorderPane root = new BorderPane();
        root.setTop(queryPane);
        root.setCenter(expressionPane);

        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.show();
    }

    public Expression getFocusExpression() {
        return focusExpression;
    }

    public Expression getOrigExpression() {
        return origExpression;
    }
}
