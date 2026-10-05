package com.CeritaBakmiBE.CB.request;

import com.CeritaBakmiBE.CB.Enum.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusRequest {

    private OrderStatus orderStatus;



}
