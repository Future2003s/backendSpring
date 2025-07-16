package com.shopdev.dto.response;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DataMultipart<T> implements Serializable {
    private List<T> result = new ArrayList<>();
}
