package com.bootcamp.event;


import com.bootcamp.entity.DataClient;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DataClientCreatedEvent extends Event<DataClient> {

}
