package nextstep.subway.acceptance;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import net.minidev.json.parser.JSONParser;
import org.assertj.core.api.Assertions;
import org.json.JSONArray;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지하철 노선 관리 기능")
class LineAcceptanceTest extends AcceptanceTest {
    /**
     * When 지하철 노선 생성을 요청 하면
     * Then 지하철 노선 생성이 성공한다.
     */
    @DisplayName("지하철 노선 생성")
    @Test
    void createLine() {
        // when
        HashMap <Object, Object> params = new HashMap <>();

        params.put("name", "신분당선");
        params.put("color", "red");

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .body(params).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/lines")
                .then().log().all().extract();
        
        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    /**
     * Given 지하철 노선 생성을 요청 하고
     * Given 새로운 지하철 노선 생성을 요청 하고
     * When 지하철 노선 목록 조회를 요청 하면
     * Then 두 노선이 포함된 지하철 노선 목록을 응답받는다
     */
    @DisplayName("지하철 노선 목록 조회")
    @Test
    void getLines() {

        // given
        String 일호선 = "1호선";
        String 이호선 = "2호선";
        HashMap <Object, Object> params1 = new HashMap <>();
        params1.put("name", 일호선);
        params1.put("color", "blue");

        ExtractableResponse<Response> response1 = RestAssured
                .given().log().all()
                .body(params1).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/lines")
                .then().log().all().extract();

        HashMap <Object, Object> params2 = new HashMap <>();
        params2.put("name", 이호선);
        params2.put("color", "green");
        ExtractableResponse<Response> response2 = RestAssured
                .given().log().all()
                .body(params2).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/lines")
                .then().log().all().extract();

        // when

        ExtractableResponse<Response> response3 = RestAssured
                .when()
                .get("/lines")
                .then().log().all().extract();

        Assertions.assertThat(response3.statusCode()).isEqualTo(HttpStatus.OK.value());

        List <Object> res = response3.jsonPath().getList("name");
        assertThat(res).contains(일호선, 이호선);
    }

    /**
     * Given 지하철 노선 생성을 요청 하고
     * When 생성한 지하철 노선 조회를 요청 하면
     * Then 생성한 지하철 노선을 응답받는다
     */
    @DisplayName("지하철 노선 조회")
    @Test
    void getLine() {
    }

    /**
     * Given 지하철 노선 생성을 요청 하고
     * When 지하철 노선의 정보 수정을 요청 하면
     * Then 지하철 노선의 정보 수정은 성공한다.
     */
    @DisplayName("지하철 노선 수정")
    @Test
    void updateLine() {
    }

    /**
     * Given 지하철 노선 생성을 요청 하고
     * When 생성한 지하철 노선 삭제를 요청 하면
     * Then 생성한 지하철 노선 삭제가 성공한다.
     */
    @DisplayName("지하철 노선 삭제")
    @Test
    void deleteLine() {
    }
}
