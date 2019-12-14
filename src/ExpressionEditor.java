import javafx.application.Application;
import javafx.event.EventHandler;
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
    /**
     * Currently focused Expression
     */
    private Expression focusExpression;

    /**
     * The root Expression
     */
    private Expression rootExpression;

    /**
     * The Pane of the window
     */
    private Pane pane;

    /**
     * Node with border
     */

    /**
     * Click or drag flipflop
     */

    boolean shouldClick = true;
    Expression expressionWithborder;

    /**
     * Mouse event handler for the entire pane that constitutes the ExpressionEditor
     */
    private class MouseEventHandler implements EventHandler<MouseEvent> {
        /**
         * Finds the clicked expression
         *
         * @param X location of the click
         * @param Y location of the click
         * @return The expression that was clicked or null if nothing was clicked
         */
        private Expression findClickedExpression(double X, double Y) {
            if (focusExpression instanceof LiteralExpression) {
                return rootExpression;
            }
            CompoundExpressionAb currentFocusCompound = (CompoundExpressionAb) focusExpression;
            for (Expression expression : currentFocusCompound.getChildren()) {
                // Get the width and height of the hbox
                double tmpWidth = expression.getNode().getLayoutBounds().getWidth();
                double tmpHeight = expression.getNode().getLayoutBounds().getHeight();
                Point2D tmpSceneLocation = expression.getNode()
                        .localToScene(expression.getNode().getBoundsInLocal().getMinX(),
                                expression.getNode().getBoundsInLocal().getMinY());
                // Check if we are in those bounds
                if (tmpSceneLocation.getX() < X && X < (tmpSceneLocation.getX() + tmpWidth) &&
                        tmpSceneLocation.getY() < Y && Y < (tmpSceneLocation.getY() + tmpHeight)) {
                    return expression;
                }
            }
            return rootExpression;
        }

        double mouseLocationOnClickX, mouseLocationOnClickY;
//        double orgTranslateX, orgTranslateY;

        /**
         * This is called when there is a mouse event
         *
         * @param event
         */
        public void handle(MouseEvent event) {
            mouseLocationOnClickX = event.getSceneX();
            mouseLocationOnClickY = event.getSceneY();

            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                if (shouldClick) shouldClick = false;
                else {
                    shouldClick = true;
                    return;
                }
                if (focusExpression == null) {
                    focusExpression = rootExpression;
                }
                Expression tmpExpression = findClickedExpression(mouseLocationOnClickX, mouseLocationOnClickY);
                if (tmpExpression != null) {
                    focusExpression = tmpExpression;
                    setBorder(tmpExpression);
                }
            } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

                Expression tmpExpression = findClickedExpression(mouseLocationOnClickX, mouseLocationOnClickY);
                if (tmpExpression != null) {
                    focusExpression = tmpExpression;
                }
                double offsetX = event.getSceneX() - mouseLocationOnClickX;
                double offsetY = event.getSceneY() - mouseLocationOnClickY;
                focusExpression.getNode().setTranslateX(offsetX);
                focusExpression.getNode().setTranslateY(offsetY);
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Expression Editor");
        pane = new Pane();

        // Add the textbox and Parser button
        final Pane queryPane = new HBox();
        final TextField textField = new TextField(EXAMPLE_EXPRESSION);
        queryPane.getChildren().add(textField);
        final Button button = new Button("Parse");
        button.setOnMouseClicked(e -> {
            pane.getChildren().clear();
            // Try to parse the expression
            try {
                // Success! Add the expression's Node to the pane
                rootExpression = expressionParser.parse(textField.getText(), true);
                System.out.println(rootExpression.convertToString());
                pane.getChildren().add(rootExpression.getNode());
                // fixme What is the purpose of these next two lines? Shouldn't you center in window instead?
                rootExpression.getNode().setLayoutX((double) WINDOW_WIDTH / 4);
                rootExpression.getNode().setLayoutY((double) WINDOW_HEIGHT / 2);

                // If the parsed expression is a CompoundExpression, then register some callbacks
                if (rootExpression instanceof CompoundExpression) {
                    ((Pane) rootExpression.getNode()).setBorder(Expression.NO_BORDER);
                    final MouseEventHandler eventHandler = new MouseEventHandler();
                    pane.setOnMousePressed(eventHandler);
                    pane.setOnMouseDragged(eventHandler);
                    pane.setOnMouseReleased(eventHandler);
                }
            } catch (ExpressionParseException epe) {
                // If we can't parse the expression, then mark it in red
                textField.setStyle("-fx-text-fill: red");
            }
        });
        queryPane.getChildren().add(button);

        // Reset the color to black whenever the user presses a key
        // This is for resetting color in case an Expression failed to parse (and turned the text red)
        textField.setOnKeyPressed(e -> textField.setStyle("-fx-text-fill: black"));

        final BorderPane root = new BorderPane();
        root.setTop(queryPane);
        root.setCenter(pane);

        primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
        primaryStage.show();
    }

    /**
     * Sets the border to red
     *
     * @param exp
     */
    private void setBorder(Expression exp) {
        //TODO Work on this rootExpression
        if (expressionWithborder != null) expressionWithborder.getNode().setStyle(null);
        if (focusExpression != rootExpression && exp != null) {
            exp.getNode().setStyle("-fx-border-style: solid inside;"
                    + "-fx-border-width: 1;"
                    + "-fx-border-insets: 2;"
                    + "-fx-border-color: red;");
            expressionWithborder = exp;
        }
    }
}
