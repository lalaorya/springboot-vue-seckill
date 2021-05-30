package com.hhj.seckill.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Good {

    private int id;
    private String name;
    private String img;
    private String introduce;
    private double price;
    private int stock;


}
