package sample.ui.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Класс анимации объектов
 */
public class ShakeField {
    private final TranslateTransition translateTransition;

    /**
     * Конструктор - Анимирование тряски объекта типа Node
     *
     * @param node - на вход подается
     */
    public ShakeField(Node node) {
        translateTransition = new TranslateTransition(Duration.millis(70), node);
        translateTransition.setFromX(0f);
        translateTransition.setByX(10f);
        translateTransition.setCycleCount(4);
        translateTransition.setAutoReverse(true);
    }

    /**
     * Метод старта анимации
     */
    public void playAnimation() {
        translateTransition.playFromStart();
    }
}
