package com.CeritaBakmiBE.CB.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponse {

    private int status;

    private String message;

    private long timestamp;

}
