package chess.piece;

import chess.domain.Color;
import chess.domain.piece.Bishop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BishopTest {

    @Test
    @DisplayName("color를 입력하면 Bishop 객체가 정상적으로 생성된다.")
    void shouldSuccessGeneratingBishop() {

        Color black = Color.BLACK;
        Color white = Color.WHITE;

        Assertions.assertAll(
                () -> assertDoesNotThrow(() -> new Bishop(black)),
                () -> assertDoesNotThrow(() -> new Bishop(white))
        );
    }
}