package chess.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static chess.domain.Position.findPosition;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PositionTest {

    @ParameterizedTest
    @ValueSource(strings = {"a1", "b1", "c1", "d1", "e1", "h1", "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2"})
    @DisplayName("가로 세로 위치 정보를 문자열로 입력할 때 해당 위치를 가진 Position 객체가 반환된다.")
    void shouldSucceedFindPosition(String position) {

        assertThat(findPosition(position)).isInstanceOf(Position.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a9", "a10", "y1", "z2", "aa2", "b33"})
    @DisplayName("해당 Position 객체를 찾기 위해 잘못된 체스 좌표를 입력했을 때 예외 발생")
    void shouldFailFindPosition(String position) {

        Assertions.assertThatThrownBy(() -> Position.findPosition(position))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 위치값의 형식이 옳지 않습니다.");
    }

    @Test
    @DisplayName("현재 포지션과 동쪽 방향 객체를 입력하면 동쪽으로 한 칸 이동한 포지션이 반환된다.")
    void shouldSucceedToReturnEastPosition() {

        Position position = Position.findPosition("f2");
        Direction northDirection = Direction.EAST;
        Position expectedPosition = Position.findPosition("g2");

        assertThat(position.getMovingPosition(northDirection)).isEqualTo(expectedPosition);
    }

    @Test
    @DisplayName("현재 포지션과 서쪽 방향 객체를 입력하면 서쪽으로 한 칸 이동한 포지션이 반환된다.")
    void shouldSucceedToReturnWestPosition() {

        Position position = Position.findPosition("f2");
        Direction northDirection = Direction.WEST;
        Position expectedPosition = Position.findPosition("e2");

        assertThat(position.getMovingPosition(northDirection)).isEqualTo(expectedPosition);
    }


    @Test
    @DisplayName("현재 포지션과 남쪽 방향 객체를 입력하면 남쪽으로 한 칸 이동한 포지션이 반환된다.")
    void shouldSucceedToReturnSouthPosition() {

        Position position = Position.findPosition("f2");
        Direction northDirection = Direction.SOUTH;
        Position expectedPosition = Position.findPosition("f1");

        assertThat(position.getMovingPosition(northDirection)).isEqualTo(expectedPosition);
    }

    @Test
    @DisplayName("현재 포지션과 북쪽 방향 객체를 입력하면 북쪽으로 한 칸 이동한 포지션이 반환된다.")
    void shouldSucceedToReturnNorthPosition() {

        Position position = Position.findPosition("f2");
        Direction northDirection = Direction.NORTH;
        Position expectedPosition = Position.findPosition("f3");

        assertThat(position.getMovingPosition(northDirection)).isEqualTo(expectedPosition);
    }

    @Test
    @DisplayName("현재 포지션에서 이동한 위치가 체스판 범위를 넘어설 경우 예외가 발생한다.")
    void shouldFailToReturnMovingPosition() {
        Position position = Position.findPosition("a1");

        assertThatThrownBy(() -> position.getMovingPosition(Direction.WEST))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 포지션은 체스판 범위 밖입니다.");
    }

    @Test
    @DisplayName("두 포지션 객체가 입력될 때 column 간 거리를 반환한다.")
    void shouldSucceedGettingColumnDistance() {
        Position sourcePosition = Position.findPosition("d3");
        Position targetPosition = Position.findPosition("f6");
        int expectedColumnDistance = 2;

        assertThat(sourcePosition.getColumnDistanceFromTargetToSource(targetPosition)).isEqualTo(expectedColumnDistance);
    }

    @Test
    @DisplayName("두 포지션 객체가 입력될 때 rank 간 거리를 반환한다.")
    void shouldSucceedGettingRankDistance() {
        Position sourcePosition = Position.findPosition("d3");
        Position targetPosition = Position.findPosition("f6");
        int expectedRankDistance = 3;

        assertThat(sourcePosition.getRankDistanceFromTargetToSource(targetPosition)).isEqualTo(expectedRankDistance);
    }

    @Test
    @DisplayName("두 포지션 객체가 입력 될 때 대각선 방향으로 이동하면 true를 반환한다.")
    void shouldSucceedToCheckDiagonalMovement() {
        Position sourcePosition = Position.findPosition("d3");
        Position targetPosition = Position.findPosition("f5");

        assertThat(sourcePosition.isDiagonalMovement(targetPosition)).isTrue();
    }

    @Test
    @DisplayName("두 포지션 객체가 입력 될 때 대각선 방향으로 이동하지 않으면 false를 반환한다.")
    void shouldFailToCheckDiagonalMovement() {
        Position sourcePosition = Position.findPosition("d3");
        Position targetPosition = Position.findPosition("f6");

        assertThat(sourcePosition.isDiagonalMovement(targetPosition)).isFalse();
    }

    @Test
    @DisplayName("두 포지션 객체가 입력 될 때 십자 방향으로 이동하면 true를 반환한다.")
    void shouldSucceedToCheckCrossMovement() {
        Position sourcePosition = Position.findPosition("d3");
        Position targetPosition = Position.findPosition("f3");

        assertThat(sourcePosition.isCrossMovement(targetPosition)).isTrue();
    }

    @Test
    @DisplayName("두 포지션 객체가 입력 될 때 십자 방향으로 이동하지 않으면 false를 반환한다.")
    void shouldFailToCheckCrossMovement() {
        Position sourcePosition = Position.findPosition("d3");
        Position targetPosition = Position.findPosition("f4");

        assertThat(sourcePosition.isCrossMovement(targetPosition)).isFalse();
    }

    @Test
    @DisplayName("이동 방향이 대각선일 때 Column의 벡터를 반환한다.")
    void shouldSucceedToFindDiagonalColumnVector() {
        Position sourcePosition = Position.findPosition("d3");
        Position targetPosition = Position.findPosition("b1");
        int expectedVector = -1;

        assertThat(sourcePosition.calculateDiagonalColumnVector(targetPosition)).isEqualTo(expectedVector);
    }

    @Test
    @DisplayName("이동 방향이 대각선일 때 rank의 벡터를 반환한다.")
    void shouldSucceedToFindDiagonalRankVector() {
        Position sourcePosition = Position.findPosition("d3");
        Position targetPosition = Position.findPosition("b1");
        int expectedVector = -1;

        assertThat(sourcePosition.calculateDiagonalRankVector(targetPosition)).isEqualTo(expectedVector);
    }

    @Test
    @DisplayName("이동 방향이 십자 방향일 때 Column의 벡터를 반환한다.")
    void shouldSucceedToFindCrossColumnVector() {
        Position sourcePosition1 = Position.findPosition("d3");
        Position targetPosition1 = Position.findPosition("d1");
        int expectedVector1 = 0;

        Position sourcePosition2 = Position.findPosition("d3");
        Position targetPosition2 = Position.findPosition("b3");
        int expectedVector2 = -1;

        assertAll(
                () -> assertThat(sourcePosition1.calculateCrossColumnVector(targetPosition1)).isEqualTo(expectedVector1),
                () -> assertThat(sourcePosition2.calculateCrossColumnVector(targetPosition2)).isEqualTo(expectedVector2)
        );
    }

    @Test
    @DisplayName("이동 방향이 십자 방향일 때 Rank의 벡터를 반환한다.")
    void shouldSucceedToFindCrossRankVector() {
        Position sourcePosition1 = Position.findPosition("d3");
        Position targetPosition1 = Position.findPosition("d1");
        int expectedVector1 = -1;

        Position sourcePosition2 = Position.findPosition("d3");
        Position targetPosition2 = Position.findPosition("b3");
        int expectedVector2 = 0;

        assertAll(
                () -> assertThat(sourcePosition1.calculateCrossRankVector(targetPosition1)).isEqualTo(expectedVector1),
                () -> assertThat(sourcePosition2.calculateCrossRankVector(targetPosition2)).isEqualTo(expectedVector2)
        );
    }

    @Test
    @DisplayName("source포지션과 target포지션을 입력할 때 column벡터를 반환한다.")
    void shouldSucceedToFindColumnVector() {

        Position sourcePosition1 = Position.findPosition("d3");
        Position targetPosition1 = Position.findPosition("d1");
        int expectedVector1 = 0;

        Position sourcePosition2 = Position.findPosition("d3");
        Position targetPosition2 = Position.findPosition("b3");
        int expectedVector2 = -1;

        Position sourcePosition3 = Position.findPosition("d3");
        Position targetPosition3 = Position.findPosition("b1");
        int expectedVector3 = -1;

        Position sourcePosition4 = Position.findPosition("d3");
        Position targetPosition4 = Position.findPosition("f1");
        int expectedVector4 = 1;

        Position sourcePosition5 = Position.findPosition("d3");
        Position targetPosition5 = Position.findPosition("f2");
        int expectedVector5 = 2;

        assertAll(
                () -> assertThat(sourcePosition1.calculateColumnVector(targetPosition1)).isEqualTo(expectedVector1),
                () -> assertThat(sourcePosition2.calculateColumnVector(targetPosition2)).isEqualTo(expectedVector2),
                () -> assertThat(sourcePosition3.calculateColumnVector(targetPosition3)).isEqualTo(expectedVector3),
                () -> assertThat(sourcePosition4.calculateColumnVector(targetPosition4)).isEqualTo(expectedVector4),
                () -> assertThat(sourcePosition5.calculateColumnVector(targetPosition5)).isEqualTo(expectedVector5)
        );
    }

    @Test
    @DisplayName("source포지션과 target포지션을 입력할 때 rank벡터를 반환한다.")
    void shouldSucceedToFindRankVector() {

        Position sourcePosition1 = Position.findPosition("d3");
        Position targetPosition1 = Position.findPosition("d1");
        int expectedVector1 = -1;

        Position sourcePosition2 = Position.findPosition("d3");
        Position targetPosition2 = Position.findPosition("b3");
        int expectedVector2 = 0;

        Position sourcePosition3 = Position.findPosition("d3");
        Position targetPosition3 = Position.findPosition("b1");
        int expectedVector3 = -1;

        Position sourcePosition4 = Position.findPosition("d3");
        Position targetPosition4 = Position.findPosition("f5");
        int expectedVector4 = 1;

        Position sourcePosition5 = Position.findPosition("d3");
        Position targetPosition5 = Position.findPosition("e5");
        int expectedVector5 = 2;

        assertAll(
                () -> assertThat(sourcePosition1.calculateRankVector(targetPosition1)).isEqualTo(expectedVector1),
                () -> assertThat(sourcePosition2.calculateRankVector(targetPosition2)).isEqualTo(expectedVector2),
                () -> assertThat(sourcePosition3.calculateRankVector(targetPosition3)).isEqualTo(expectedVector3),
                () -> assertThat(sourcePosition4.calculateRankVector(targetPosition4)).isEqualTo(expectedVector4),
                () -> assertThat(sourcePosition5.calculateRankVector(targetPosition5)).isEqualTo(expectedVector5)
        );
    }
}
