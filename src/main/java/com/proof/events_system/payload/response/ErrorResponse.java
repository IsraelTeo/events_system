package com.proof.events_system.payload.response;

import java.util.List;

public record ErrorResponse(String description, List<String> reasons) {
}
