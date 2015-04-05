package org.mockserver.client.serialization.model;

import org.mockserver.matchers.Times;
import org.mockserver.mock.Expectation;
import org.mockserver.model.*;

/**
 * @author jamesdbloom
 */
public class ExpectationDTO extends ObjectWithReflectiveEqualsHashCodeToString {

    private HttpRequestDTO httpRequest;
    private HttpResponseDTO httpResponse;
    private HttpForwardDTO httpForward;
    private HttpCallbackDTO httpCallback;
    private TimesDTO times;

    public ExpectationDTO(Expectation expectation) {
        if (expectation != null) {
            if (expectation.getHttpRequest() != null) {
                httpRequest = new HttpRequestDTO(expectation.getHttpRequest(), false);
            }
            if (expectation.getHttpResponse(false) != null) {
                httpResponse = new HttpResponseDTO(expectation.getHttpResponse(false));
            }
            if (expectation.getHttpForward() != null) {
                httpForward = new HttpForwardDTO(expectation.getHttpForward());
            }
            if (expectation.getHttpCallback() != null) {
                httpCallback = new HttpCallbackDTO(expectation.getHttpCallback());
            }
            if (expectation.getTimes() != null) {
                times = new TimesDTO(expectation.getTimes());
            }
        }
    }

    public ExpectationDTO() {
    }

    public Expectation buildObject() {
        HttpRequest httpRequest = null;
        HttpResponse httpResponse = null;
        HttpForward httpForward = null;
        HttpCallback httpCallback = null;
        Times times;
        if (this.httpRequest != null) {
            httpRequest = this.httpRequest.buildObject();
        }
        if (this.httpResponse != null) {
            httpResponse = this.httpResponse.buildObject();
        }
        if (this.httpForward != null) {
            httpForward = this.httpForward.buildObject();
        }
        if (this.httpCallback != null) {
            httpCallback = this.httpCallback.buildObject();
        }
        if (this.times != null) {
            times = this.times.buildObject();
        } else {
            times = Times.once();
        }
        return new Expectation(httpRequest, times).thenRespond(httpResponse).thenForward(httpForward).thenCallback(httpCallback);
    }

    public HttpRequestDTO getHttpRequest() {
        return httpRequest;
    }

    public ExpectationDTO setHttpRequest(HttpRequestDTO httpRequest) {
        this.httpRequest = httpRequest;
        return this;
    }

    public TimesDTO getTimes() {
        return times;
    }

    public ExpectationDTO setTimes(TimesDTO times) {
        this.times = times;
        return this;
    }

    public HttpResponseDTO getHttpResponse() {
        return httpResponse;
    }

    public ExpectationDTO setHttpResponse(HttpResponseDTO httpResponse) {
        this.httpResponse = httpResponse;
        return this;
    }

    public HttpForwardDTO getHttpForward() {
        return httpForward;
    }

    public ExpectationDTO setHttpForward(HttpForwardDTO httpForward) {
        this.httpForward = httpForward;
        return this;
    }

    public HttpCallbackDTO getHttpCallback() {
        return httpCallback;
    }

    public ExpectationDTO setHttpCallback(HttpCallbackDTO httpCallback) {
        this.httpCallback = httpCallback;
        return this;
    }
}
