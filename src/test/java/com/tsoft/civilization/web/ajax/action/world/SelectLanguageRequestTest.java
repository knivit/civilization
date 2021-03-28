package com.tsoft.civilization.web.ajax.action.world;

import com.tsoft.civilization.web.MockRequest;
import com.tsoft.civilization.web.request.Request;
import com.tsoft.civilization.web.response.ContentType;
import com.tsoft.civilization.web.response.Response;
import com.tsoft.civilization.web.response.ResponseCode;
import com.tsoft.civilization.web.state.ClientSession;
import com.tsoft.civilization.web.state.Sessions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.tsoft.civilization.L10n.L10nLanguage.EN;
import static com.tsoft.civilization.L10n.L10nLanguage.RU;
import static org.assertj.core.api.Assertions.assertThat;

public class SelectLanguageRequestTest {

    @BeforeEach
    public void before_each() {
        Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
    }

    @Test
    public void getJson_en() {
        assert_getJson(EN);
    }

    @Test
    public void getJson_ru() {
        assert_getJson(RU);
    }

    private void assert_getJson(String language) {
        Request request = MockRequest.newInstance("language", language);

        SelectLanguageRequest selectLanguageRequest = new SelectLanguageRequest();

        Response response = selectLanguageRequest.getJson(request);
        assertThat(response)
            .isNotNull()
            .returns(ResponseCode.OK, Response::getResponseCode)
            .returns(ContentType.TEXT_HTML, Response::getContentType);

        assertThat(Sessions.getCurrent())
            .isNotNull()
            .returns(language, ClientSession::getLanguage);
    }
}
