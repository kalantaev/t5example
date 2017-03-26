package com.example.t5.components.error;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

import java.util.List;

/**
 * Created by Калантаев Александр on 25.03.2017.
 */
@Import(stylesheet = "context:style/error.css")
public class Error {

    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.PROP)
    private List<String> errors;

    @Property
    private String value;

    public boolean hasError(){
        return !(errors == null || errors.isEmpty());
    }
}
